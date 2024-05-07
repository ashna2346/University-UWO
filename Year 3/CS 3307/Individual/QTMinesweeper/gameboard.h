/*
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 * CS 3307: Individual Assignment
 * Description: Defines the GameBoard class for the Minesweeper game. This class manages the game's logic, including the layout of mines, game state (victory, defeat, ongoing), and interactions with individual blocks on the game board.
*/

#pragma once
#include <QList>
#include <QFrame>
#include <QSet>
#include "blocks.h"

class GameBoard : public QFrame
{
    Q_OBJECT // Enabling the class to use signals and slots

public:
    // Constructor for creating game board with specified rows, columns, and mines
    GameBoard(unsigned int nRows, unsigned int nCols, unsigned int nMines, QWidget* parent = nullptr);

    // Accessor methods for the number of columns, rows, and mines
    unsigned int nCols() const { return m_nCols; }
    unsigned int nRows() const { return m_nRows; }
    unsigned int nMines() const { return m_nMines; }

public slots:

    void MinePlacement(Blocks* firstClicked);
    void resetGame();

signals:

    void initialized();
    void victory();
    void defeat();
    void flagCountChanged(unsigned int flagCount);

private:

    void createBlocks();
    void defeatLogicHandling();
    void setUp();
    void adjNeighbours();
    void winCheck();


private:

    // Game board dimensions and mine count
    unsigned int m_nRows;
    unsigned int m_nCols;
    unsigned int m_nMines;

    QList<QList<Blocks*>> m_Blocks;
    QSet<Blocks*> m_mines;
    QSet<Blocks*> m_rightFlags;
    QSet<Blocks*> m_wrongFlags;
    QSet<Blocks*> m_visibleBlocks;

    QTimer* explosionTimer; // Timer for handling animations or timed events

    // State flags for the game
    bool m_defeat = false;
    bool m_victory = false;
    bool m_gameOver;
};
