
/**
 * @file main.cpp
 * @brief Main entry point for the application.
 * @authors Siddhant Saraf, PArth Bhandari, Manpreet Saini, Sara Mehravar, AShna Mittal
 *
 * This file contains the main function, which initializes the QApplication, creates the main
 * window, and enters the application's main event loop. It sets up the initial state of the
 * application and displays the primary window to the user.
 */

#include <QApplication>
#include "mainwindow.h"
#include "deckmainwindow.h"

/**
 * @brief Main function of the application.
 *
 * Initializes the QApplication with the command line arguments, creates the main application
 * window, and starts the event loop. It also sets up the main window of the application and
 * displays it.
 *
 * @param argc Number of command line arguments.
 * @param argv Array of command line arguments.
 * @return Exit status of the application.
 */

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    MainWindow* mainWin = new MainWindow("");

    ///< Create an instance of DeckMainWindow and pass the MainWindow instance to it.
    DeckMainWindow w(mainWin);
    w.show(); ///< Show the DeckMainWindow
    return a.exec(); ///< Enter the main event loop and wait for exit signal.
}
