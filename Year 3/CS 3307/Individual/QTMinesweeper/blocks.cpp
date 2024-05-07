/**
Student Name: Ashna Mittal
Student ID: 251206758
CS 3307: Individual Assignment
*/


#include "blocks.h"
#include <QDebug>
#include <QState>
#include <QFinalState>
#include <QMouseEvent>
#include <QSizePolicy>

bool Blocks::m_firstClick = false;
// Style sheets for different states of a block
const QString Blocks::unrevealedStyleSheet =
    "Blocks"
    "{"
    "   border: 1px solid #CCCCCC;"
    "   background: qradialgradient(cx : 0.4, cy : -0.1, fx : 0.4, fy : -0.1, radius : 1.35, stop : 0 #FFFFFF, stop: 1 #EEEEEE);" // Changed gradient colors
    "   border-radius: 1px;"
    "}";
const QString Blocks::revealedStyleSheet =
    "Blocks"
    "{"
    "   border: 1px solid #BBBBBB;"
    "}";
const QString Blocks::revealedWithNumberStylesheet =
    "Blocks"
    "{"
    "	color: %1;"
    "	font-weight: bold;"
    "	border: 1px solid lightgray;"
    "}";

QIcon Blocks::blankIcon()
{
    static QIcon icon = QIcon();
    return icon;
}

QIcon Blocks::flagIcon()
{
    static QIcon icon = QIcon(QPixmap(":/images/mine_flag").scaled(QSize(20, 20)));
    return icon;
}

QIcon Blocks::mineIcon()
{
    static QIcon icon = QIcon(QPixmap(":/images/bomb").scaled(QSize(20, 20)));
    return icon;
}

QIcon Blocks::explosionIcon()
{
    static QIcon icon = QIcon(QPixmap(":/images/bomb_explode").scaled(QSize(20, 20)));
    return icon;
}

QIcon Blocks::questionMarkIcon() {
    static QIcon icon = QIcon(QPixmap(":/images/question_mark").scaled(QSize(20, 20)));
    return icon;
}

//constructor that initializes a block with its location and parent widget
Blocks::Blocks(BlocksLocation location, QWidget* parent /*= nullptr*/)
    : QPushButton(parent)
    , m_MineCheck(false)
    , m_adjMineCount(0)
    , m_adjFlagCount(0)
    , m_location(location)
{
    createStateMachine();
    this->setSizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
    setCheckable(true);
    setMouseTracking(true);
}

// Destructor: Cleans up dynamically allocated states
Blocks::~Blocks()
{
    m_firstClick = false;
    delete unrevealedState;
    delete previewState;
    delete flaggedState;
    delete revealedState;
    delete disabledState;
}

void Blocks::addNeighbor(Blocks* Blocks)
{
    m_neighbors += Blocks;
    connect(this, &Blocks::revealNeighbors, Blocks, &Blocks::reveal, Qt::QueuedConnection);
    connect(this, &Blocks::unPreviewNeighbors, Blocks, &Blocks::unPreview, Qt::QueuedConnection);
}

// Resets the block to its initial state
void Blocks::reset()
{
    if (m_machine.isRunning()) {
        m_machine.stop();
    }

    // Reset Blocks state
    m_MineCheck = false;
    m_adjMineCount = 0;
    m_adjFlagCount = 0;
    m_firstClick = false;

    // Reset visual state
    setIcon(blankIcon());
    setStyleSheet(unrevealedStyleSheet);
    QAbstractButton::setText("");
    setEnabled(true);
    setChecked(false);
    update(); // Ensure the widget is updated immediately


    // Reset state machine to initial state
    m_machine.setInitialState(unrevealedState);
    m_machine.start();
}


BlocksLocation Blocks::location() const
{
    return m_location;
}

void Blocks::MinePlacements(bool val)
{
    m_MineCheck = val;
    for (auto neighbor : m_neighbors)
        neighbor->incAdjMineCount();
}

bool Blocks::isMine() const
{
    return m_MineCheck;
}

bool Blocks::hasAdjacentMines() const
{
    return m_adjMineCount;
}

unsigned int Blocks::adjacentMineCount() const
{
    return m_adjMineCount;
}

void Blocks::incAdjMineCount()
{
    ++m_adjMineCount;
}

bool Blocks::isFlagged() const
{
    return m_machine.configuration().contains(flaggedState);
}

bool Blocks::isRevealed() const
{
    return m_machine.configuration().contains(revealedState);
}

bool Blocks::isUnrevealed() const
{
    return m_machine.configuration().contains(unrevealedState);
}

unsigned int Blocks::adjacentFlaggedCount() const
{
    return m_adjFlagCount;
}

void Blocks::incAdjFlagCount()
{
    ++m_adjFlagCount;
}

void Blocks::decAdjFlagCount()
{
    --m_adjFlagCount;
}

QList<Blocks*>& Blocks::neighbors()
{
    return m_neighbors;
}

void Blocks::mousePressEvent(QMouseEvent* e)
{
    if (parentWidget()->property("gameOver").toBool()) return;
    if (!m_firstClick)
    {
        m_firstClick = true;
        emit firstClick(this);
    }

    m_bothClicked = false;

    if (e->buttons() == (Qt::LeftButton | Qt::RightButton))
    {
        emit bothClicked();
        m_buttons = Qt::LeftButton | Qt::RightButton;
    }
    else if (e->buttons() == Qt::LeftButton)
        m_buttons = Qt::LeftButton;
    else if (e->buttons() == Qt::RightButton)
        m_buttons = Qt::RightButton;
}

void Blocks::mouseReleaseEvent(QMouseEvent* e) {
    if (parentWidget()->property("gameOver").toBool()) return;
    if (e->button() == Qt::RightButton) {
        // Right-click cycles through the marking states
        switch (m_marking) {
        case Unmarked:
            m_marking = Flagged;
            this->setIcon(flagIcon());
            break;
        case Flagged:
            m_marking = Questioned;
            this->setIcon(questionMarkIcon());  // You need to create this icon
            break;
        case Questioned:
            m_marking = Unmarked;
            this->setIcon(blankIcon());
            break;
        }
    } else if (e->button() == Qt::LeftButton) {
        if (m_marking == Unmarked) {
            // Reveal the Blocks
            emit leftClicked();
        }
    }
}


void Blocks::mouseMoveEvent(QMouseEvent* e)
{
    if (e->buttons() == (Qt::LeftButton | Qt::RightButton))
    {
        if (!this->rect().contains(this->mapFromGlobal(QCursor::pos())))
            emit unPreview();
    }
}

QSize Blocks::sizeHint() const
{
    return QSize(20, 20);
}

//Defines the state machine for a block, handling different states like unrevealed, flagged, revealed, etc.
void Blocks::createStateMachine()
{
    unrevealedState = new QState;
    previewState = new QState;
    previewNeighborsState = new QState;
    flaggedState = new QState;
    revealedState = new QState;
    revealNeighborsState = new QState;
    disabledState = new QFinalState;

    unrevealedState->addTransition(this, &Blocks::leftClicked, revealedState);
    unrevealedState->addTransition(this, &Blocks::rightClicked, flaggedState);
    unrevealedState->addTransition(this, &Blocks::reveal, revealedState);
    unrevealedState->addTransition(this, &Blocks::preview, previewState);
    unrevealedState->addTransition(this, &Blocks::disable, disabledState);

    previewState->addTransition(this, &Blocks::reveal, revealedState);
    previewState->addTransition(this, &Blocks::unPreview, unrevealedState);
    previewState->addTransition(this, &Blocks::disable, disabledState);

    flaggedState->addTransition(this, &Blocks::rightClicked, unrevealedState);

    revealedState->addTransition(this, &Blocks::bothClicked, previewNeighborsState);

    previewNeighborsState->addTransition(this, &Blocks::unClicked, revealNeighborsState);
    previewNeighborsState->addTransition(this, &Blocks::unPreview, revealedState);

    revealNeighborsState->addTransition(this, &Blocks::reveal, revealedState);

    connect(unrevealedState, &QState::entered, [this]()
            {
                this->setIcon(blankIcon());
                this->setStyleSheet(unrevealedStyleSheet);

            });

    connect(previewState, &QState::entered, [this]()
            {
                this->setStyleSheet(revealedStyleSheet);
            });

    connect(previewNeighborsState, &QState::entered, [this]()
            {
                for (auto neighbor : m_neighbors)
                    neighbor->preview();
            });

    connect(revealNeighborsState, &QState::entered, [this]()
            {
                if (m_adjFlagCount == m_adjMineCount && m_adjMineCount)
                    revealNeighbors();
                else
                    unPreviewNeighbors();
                emit reveal();
            });

    connect(revealedState, &QState::entered, [this]()
            {
                unPreviewNeighbors();
                this->setIcon(blankIcon());
                this->setChecked(true);
                if (!isMine())
                {
                    setText();
                    if (!hasAdjacentMines())
                        revealNeighbors();
                    emit revealed();
                }
                else
                {
                    emit detonated();
                    this->setStyleSheet(revealedStyleSheet);
                    QPushButton::setText("");
                    setIcon(mineIcon());
                }
            });

    connect(flaggedState, &QState::entered, [this]()
            {
                this->setIcon(flagIcon());
                for (auto neighbor : m_neighbors)
                    neighbor->incAdjFlagCount();
                emit flagged(m_MineCheck);
            });

    connect(flaggedState, &QState::exited, [this]()
            {
                for (auto neighbor : m_neighbors)
                    neighbor->decAdjFlagCount();
                emit unFlagged(m_MineCheck);
            });

    connect(disabledState, &QState::entered, [this]()
            {

            });

    m_machine.addState(unrevealedState);
    m_machine.addState(previewState);
    m_machine.addState(previewNeighborsState);
    m_machine.addState(flaggedState);
    m_machine.addState(revealedState);
    m_machine.addState(revealNeighborsState);
    m_machine.addState(disabledState);

    m_machine.setInitialState(unrevealedState);
    m_machine.start();
}

// Updates the block's text based on adjacent mine count, applying appropriate styling
void Blocks::setText()
{
    QString color = "#000000";

    QPushButton::setStyleSheet(revealedWithNumberStylesheet.arg(color));
    if(m_adjMineCount > 0  && m_adjMineCount <= 8)
        QPushButton::setText(QString::number(m_adjMineCount));
    else
        QPushButton::setText("");
}
