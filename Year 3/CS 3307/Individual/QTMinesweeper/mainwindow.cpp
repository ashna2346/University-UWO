/**
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 * Course: CS 3307 - Individual Assignment
 *
 * Description: This file defines the main window for the Minesweeper game, including the setup of the game board,
 * handling victory and defeat scenarios with appropriate messages and options for the user.
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QMessageBox>
#include <QPixmap>

// Constructor for setting up the game board and connecting signals
MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    // Setting game board dimensions and mine count for Minesweeper
    const unsigned int nRows = 16; // Number of rows in the game board
    const unsigned int nCols = 30; // Number of columns in the game board
    const unsigned int nMines = 99; // Number of mines on the game board

    // Initializing the game board with specified dimensions and mine count
    gameBoard = new GameBoard(nRows, nCols, nMines, this);
    setCentralWidget(gameBoard);

    // Connecting the victory and defeat signals from the game board to the onVictory and onDefeat slot
    connect(gameBoard, &GameBoard::victory, this, &MainWindow::onVictory);
    connect(gameBoard, &GameBoard::defeat, this, &MainWindow::onDefeat);
    setWindowTitle("Minesweeper"); // Setting the window title
}

// Destructor for MainWindow, cleans up the UI component
MainWindow::~MainWindow()
{
    delete ui;
}

// function to handle the victory case
void MainWindow::onVictory()
{
    QMessageBox vbox;
    vbox.setWindowTitle("Victory!"); // title of the message box
    QPixmap TrophyIcon(":/images/trophy"); // icon for victory
    vbox.setIconPixmap(TrophyIcon.scaled(64, 64, Qt::KeepAspectRatio)); // Set icon with adjusted size
    vbox.setText("You have won the game!"); // Main message text
    vbox.setInformativeText("Do you want to play again?");
    QPushButton *playAgainButton = vbox.addButton(tr("Play Again?"), QMessageBox::AcceptRole); // Option to replay
    vbox.addButton(tr("Exit"), QMessageBox::RejectRole); // Option to exit

    vbox.exec(); // Display the message box to the user
    if (vbox.clickedButton() == playAgainButton)
    {
        gameBoard->resetGame(); // Reset the game if the user chooses to play again
    } else {
        QApplication::quit(); // Exit the application if the user chooses to exit
    }
}

// function to handle the defeat case
void MainWindow::onDefeat()
{
    QMessageBox dbox;
    dbox.setWindowTitle("Uh Oh!"); // title of the message box
    QPixmap bombIcon(":/images/bomb_explode"); // icon for defeat
    dbox.setIconPixmap(bombIcon.scaled(64, 64, Qt::KeepAspectRatio)); // Set icon with adjusted size
    dbox.setText("You have hit a mine and exploded."); // Main message text
    dbox.setInformativeText("Do you want to try again?");
    QPushButton *tryAgainButton = dbox.addButton(tr("Play Again?"), QMessageBox::AcceptRole); // Option to replay
    dbox.addButton(tr("Exit"), QMessageBox::RejectRole); // Option to exit

    dbox.exec(); // Display the message box to the user
    if (dbox.clickedButton() == tryAgainButton)
    {
        gameBoard->resetGame(); // Reset the game if the user chooses to play again
    } else {
        QApplication::quit(); // Exit the application if the user chooses to exit
    }
}
