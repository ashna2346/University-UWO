/**
Student Name: Ashna Mittal
Student ID: 251206758
CS 3307: Individual Assignment
*/

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "gameBoard.h" // Including the MSgameBoard class header for composition

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

// MainWindow class inherits QMainWindow and provides the main application window
class MainWindow : public QMainWindow
{
    Q_OBJECT // Enabling Qt's meta-object features, including signals and slots

public:
    explicit MainWindow(QWidget *parent = nullptr); // Constructor
    ~MainWindow(); // Destructor

private slots:
    void onVictory(); // function to handle the victory condition
    void onDefeat(); // function to handle the defeat condition

private:
    Ui::MainWindow *ui; // Pointer to the UI class for accessing elements
    GameBoard *gameBoard; // Pointer to the GameBoard
};

#endif // MAINWINDOW_H
