/**
Student Name: Ashna Mittal
Student ID: 251206758
CS 3307: Individual Assignment
*/

#include "gameboard.h"
#include <algorithm>
#include <random>
#include <QGridLayout>
#include <QSpacerItem>
#include <QSet>
#include <QTimer>

// Constructor for the GameBoard class
GameBoard::GameBoard(unsigned int nRows, unsigned int nCols, unsigned int nMines, QWidget* parent /*= nullptr*/)
    : QFrame(parent), m_nRows(nRows), m_nCols(nCols), m_nMines(nMines), explosionTimer(new QTimer(this))
{
    setUp(); // Initializing the game board layout
    createBlocks(); // Creating Blocks (tiles) for the game board
    adjNeighbours(); // Establishing relationships between adjacent Blocks

    // Setting up connections to handle victory or defeat
    connect(this, &GameBoard::victory, [this]() { explosionTimer->setProperty("victory", true); });
    connect(this, &GameBoard::defeat, [this]() { explosionTimer->setProperty("victory", false); });


    // Configuring explosionTimer to handle animations based on game outcome
    connect(explosionTimer, &QTimer::timeout, [this]()
            {
                if (m_mines.isEmpty())
                {
                    explosionTimer->stop();
                    return;
                }

                Blocks* mine = m_mines.values().front();
                m_mines.remove(mine);

                if (explosionTimer->property("victory").toBool())
                    mine->setIcon(mine->blankIcon());
                else
                {
                    if (!m_rightFlags.contains(mine))
                        mine->setIcon(mine->explosionIcon());
                }
            });

}


// Setting up the layout for the Minesweeper ame board
void GameBoard::setUp()
{
    this->setAttribute(Qt::WA_LayoutUsesWidgetRect);
    this->setSizePolicy(QSizePolicy::Preferred, QSizePolicy::Preferred);
    auto layout = new QGridLayout;
    layout->setSpacing(0);
    layout->setContentsMargins(0, 0, 0, 0);
    this->setLayout(layout);
}

// Creating Blocks (tiles) and configuring them
void GameBoard::createBlocks()
{
    for (unsigned int r = 0; r < m_nRows; ++r)
    {
        m_Blocks += QList<Blocks*>{};
        for (unsigned int c = 0; c < m_nCols; ++c)
        {
            // adding new Blocks to the row
            m_Blocks[r] += new Blocks({ r, c }, this);
            static_cast<QGridLayout*>(this->layout())->addWidget(m_Blocks[r][c], r, c);
            // Connecting signals and slots for game logic
            connect(m_Blocks[r][c], &Blocks::firstClick, this, &GameBoard::MinePlacement);
            connect(m_Blocks[r][c], &Blocks::flagged, [this, Blocks = m_Blocks[r][c]](bool isMine)
                    {
                        if (isMine)
                            m_rightFlags.insert(Blocks);
                        else
                            m_wrongFlags.insert(Blocks);
                        winCheck();
                    });
            connect(m_Blocks[r][c], &Blocks::unFlagged, [this, Blocks = m_Blocks[r][c]](bool isMine)
                    {
                        if (isMine)
                            m_rightFlags.remove(Blocks);
                        else
                            m_wrongFlags.remove(Blocks);
                        winCheck();
                    });
            connect(m_Blocks[r][c], &Blocks::revealed, [this, Blocks = m_Blocks[r][c]]()
                    {
                        m_visibleBlocks.insert(Blocks);
                        winCheck();
                    });
            connect(m_Blocks[r][c], &Blocks::detonated, this, &GameBoard::defeatLogicHandling);
            connect(this, &GameBoard::defeat, m_Blocks[r][c], &Blocks::disable);
            connect(this, &GameBoard::victory, m_Blocks[r][c], &Blocks::disable);
        }
    }
    m_Blocks[0][0]->setDown(true);
}

// Assigning neighbors to each Blocks for game logic (e.g., mine checking)
void GameBoard::adjNeighbours()
{
    for (unsigned int r = 0; r < m_nRows; ++r)
    {
        for (unsigned int c = 0; c < m_nCols; ++c)
        {
            // add a new Blocks to the row
            auto* Blocks = m_Blocks[r][c];
            auto lastRow = m_nRows - 1;
            auto lastCol = m_nCols - 1;

            // Determining neighbors based on position and adding them
            if (r && c)						Blocks->addNeighbor(m_Blocks[r - 1][c - 1]);				// top left
            if (r)							Blocks->addNeighbor(m_Blocks[r - 1][c]);					// top
            if (r && c < lastCol)			Blocks->addNeighbor(m_Blocks[r - 1][c + 1]);				// top right
            if (c < lastCol)				Blocks->addNeighbor(m_Blocks[r][c + 1]);					// right
            if (r < lastRow && c < lastCol)	Blocks->addNeighbor(m_Blocks[r + 1][c + 1]);				// bottom right
            if (r < lastRow)				Blocks->addNeighbor(m_Blocks[r + 1][c]);					// bottom
            if (r < lastRow && c)			Blocks->addNeighbor(m_Blocks[r + 1][c - 1]);				// bottom left
            if (c)							Blocks->addNeighbor(m_Blocks[r][c - 1]);					// left
        }
    }
}

// Checking if victory conditions are met
void GameBoard::winCheck()
{
    if (!m_victory)
    {
        emit flagCountChanged(m_rightFlags.size() + m_wrongFlags.size());
        if ((m_visibleBlocks.size() == m_nCols * m_nRows - m_nMines) && m_wrongFlags.isEmpty())
        {
            emit victory();
            m_victory = true;
            QTimer::singleShot(0, explosionTimer, [this]()
                               {
                                   explosionTimer->start(25);
                               });
        }
    }
}


// Resetting the game to its initial state
void GameBoard::resetGame()
{
    // Resets game state variables
    m_defeat = false;
    m_victory = false;
    m_gameOver = false;
    setProperty("gameOver", m_gameOver);

    // Stopping any running timers and clearing tracking sets
    explosionTimer->stop();
    m_mines.clear();
    m_rightFlags.clear();
    m_wrongFlags.clear();
    m_visibleBlocks.clear();

    // Resetting all Blocks to their initial state
    for (auto &row : m_Blocks)
    {
        for (auto *Blocks : row)
        {

            Blocks->reset(); // resetting the individual Blocks to an unrevealed state
            connect(Blocks, &Blocks::firstClick, this, &GameBoard::MinePlacement);
        }
    }

    // Removing previous connections to avoid multiple connections to the same slot
    disconnect(this, &GameBoard::defeat, m_Blocks[0][0], &Blocks::disable);
    disconnect(this, &GameBoard::victory, m_Blocks[0][0], &Blocks::disable);

    for (unsigned int r = 0; r < m_nRows; ++r)
    {
        for (unsigned int c = 0; c < m_nCols; ++c)
        {
            connect(this, &GameBoard::defeat, m_Blocks[r][c], &Blocks::disable);
            connect(this, &GameBoard::victory, m_Blocks[r][c], &Blocks::disable);
        }
    }

    // Randomly placing mines, with no initial clicked Blocks
    MinePlacement(nullptr);
}


// Handles the animation and logic when defeat is detected
void GameBoard::defeatLogicHandling()
{
    if (m_gameOver) return; // Avoid multiple triggers
    m_gameOver = true;
    setProperty("gameOver", m_gameOver);
    Blocks* sender = dynamic_cast<Blocks*>(this->sender());
    QTimer::singleShot(350, this, [sender]()
                       {
                           sender->setIcon(sender->explosionIcon());
                       });
    QTimer::singleShot(500, this, [this]()
                       {
                           for (auto wrong : m_wrongFlags)
                           {
                               wrong->setIcon(wrong->blankIcon());
                           }
                           for (auto mine : m_mines)
                           {
                               disconnect(mine, &Blocks::detonated, this, &GameBoard::defeatLogicHandling);
                               if (!mine->isFlagged())
                                   mine->reveal();
                           }
                           emit defeat();
                       });

    QTimer::singleShot(1000, explosionTimer, [this]()
                       {
                           explosionTimer->start(25);
                       });
}

// Randomly places mines on the game board, excluding the first clicked Blocks and its neighbors
void GameBoard::MinePlacement(Blocks* firstClicked)
{

    QList<Blocks*> Blockss;
    m_mines.clear();
    if (firstClicked != nullptr)
    {
        QSet<Blocks*> doneUse;
        doneUse.insert(firstClicked);
        QList<Blocks*> list = firstClicked->neighbors();
        for (Blocks* Blocks : list) {
            doneUse.insert(Blocks);
        }

        for (unsigned int r = 0; r < m_nRows; ++r)
        {
            for (unsigned int c = 0; c < m_nCols; ++c)
            {
                if (!doneUse.contains(m_Blocks[r][c])) {
                    Blockss.append(m_Blocks[r][c]);
                }
            }
        }
    }
    else
    {
        for (unsigned int r = 0; r < m_nRows; ++r)
        {
            for (unsigned int c = 0; c < m_nCols; ++c)
            {
                Blockss.append(m_Blocks[r][c]);
            }
        }
    }

    // shuffling the list of Blocks to randomize mine placement
    std::random_device rd;
    std::mt19937 g(rd());
    std::shuffle(Blockss.begin(), Blockss.end(), g);
    for (unsigned int i = 0; i < m_nMines; ++i) {
        Blockss[i]->MinePlacements(true);
        m_mines.insert(Blockss[i]);
    }

    emit initialized();
}
