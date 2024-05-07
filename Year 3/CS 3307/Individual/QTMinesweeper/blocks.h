/**
Student Name: Ashna Mittal
Student ID: 251206758
CS 3307: Individual Assignment
*/

#pragma once
#include <QPushButton>
#include <QList>
#include <QStateMachine>
#include <QState>
#include <QFinalState>
#include <QSignalMapper>

// Structure to define the location of a block on the game board
struct BlocksLocation
{
    unsigned int row;
    unsigned int column;
};

class Blocks : public QPushButton
{
    Q_OBJECT
public:

    // Enum to define the marking state of a block (flagged, questioned, or unmarked)
    enum Marking {
        Unmarked,
        Flagged,
        Questioned
    };

    Blocks(BlocksLocation location, QWidget* parent = nullptr); //constructor
    virtual ~Blocks() override; //deconstructor

    void addNeighbor(Blocks* Blocks);
    BlocksLocation location() const;
    void MinePlacements(bool val);

    // Various state checks for the block
    bool isMine() const;
    bool isFlagged() const;
    bool isRevealed() const;
    bool isUnrevealed() const;
    bool hasAdjacentMines() const;

    // Counters for adjacent mines and flagged blocks
    unsigned int adjacentMineCount() const;
    unsigned int adjacentFlaggedCount() const;
    // Accessor for neighbors.
    QList<Blocks*>& neighbors();

    // Event handlers for mouse interactions.
    virtual void mousePressEvent(QMouseEvent* e) override;
    virtual void mouseReleaseEvent(QMouseEvent* e) override;
    virtual void mouseMoveEvent(QMouseEvent* e) override;
    virtual QSize sizeHint() const override;

     // Static functions to get icons for different block states
    static QIcon blankIcon();
    static QIcon flagIcon();
    static QIcon mineIcon();
    static QIcon explosionIcon();
    static QIcon questionMarkIcon();


public slots:

    // Slot functions to handle adjacent flag and mine count adjustments
    void incAdjFlagCount();
    void decAdjFlagCount();
    void incAdjMineCount();
    void reset();

signals:
    // Signals emitted based on user actions and block state changes
    void firstClick(Blocks*);
    void leftClicked();
    void rightClicked();
    void bothClicked();
    void unClicked();
    void detonated();
    void reveal();
    void revealed();
    void revealNeighbors();
    void preview();
    void unPreview();
    void unPreviewNeighbors();
    void flagged(bool);
    void unFlagged(bool);
    void disable();

private:

    void createStateMachine();
    void setText();
    Marking m_marking;

private:

    // Member variables to store the block's state
    bool m_MineCheck;
    unsigned int m_adjMineCount;
    unsigned int m_adjFlagCount;
    BlocksLocation m_location;
    QList<Blocks*> m_neighbors;
    bool m_bothClicked;
    static bool m_firstClick;
    Qt::MouseButtons m_buttons;

    // Style sheets for different block states
    static const QString unrevealedStyleSheet;
    static const QString revealedStyleSheet;
    static const QString revealedWithNumberStylesheet;

    // State machine and states to manage block interactions and appearances
    QStateMachine m_machine;
    QState* unrevealedState;
    QState* previewState;
    QState* previewNeighborsState;
    QState* flaggedState;
    QState* revealedState;
    QState* revealNeighborsState;
    QFinalState* disabledState;
};
