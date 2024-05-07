/**
Student Name: Ashna Mittal
Student ID: 251206758
CS 3307: Individual Assignment
*/

//including the various Qt module headers necessary for the application
#include <QApplication>
#include <QDataStream>
#include <QGuiApplication>
#include <QMetaType>
#include <QStyleFactory>
//including the header file for the MainWindow class for the central widget for the Minesweeper game's GUI
#include "mainwindow.h"

int main(int argc, char* argv[])
{
    // ensuring that the GUI elements appear at a reasonable size on high DPI displays
    QGuiApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
    QApplication app(argc, argv);
    QCoreApplication::setApplicationName("Minesweeper"); //setting the name of the application
    MainWindow mw;

    mw.show(); //displaying the game window on the screen.
    return app.exec();
}
