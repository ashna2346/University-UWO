/**
 * @file deckmainwindow.h
 * @brief Declaration of the DeckMainWindow class for managing deck windows.
 * @authors Ashna Mittal, Manpreet Saini, Sara Mehravar, Parth Bhandari
 *
 * This file declares the DeckMainWindow class, which is responsible for the user interface that
 * allows users to manage their decks. This includes creating new decks, editing existing decks,
 * and deleting decks.
 */
#ifndef DECKMAINWINDOW_H
#define DECKMAINWINDOW_H

#include <QWidget>
#include <QVBoxLayout>
#include <QPushButton>
#include <QToolButton>
#include <QMap>
#include <QList>
#include <QLabel>
#include "question.h"
#include "mainwindow.h"
#include "ui_deckmainwindow.h"


/**
 * @class DeckMainWindow
 * @brief The DeckMainWindow class provides a UI for managing decks.
 *
 * DeckMainWindow extends QWidget to provide a user interface where users can add, edit, and delete decks.
 * It interacts with the MainWindow to reflect these changes across the application.
 */
class DeckMainWindow : public QWidget {
    Q_OBJECT

public slots:
    void saveDeckNamesToJson(); ///< Saves the current list of deck names to a JSON file.

public:
    QStringList decksList; ///< List of deck names.
    bool fileDeleteExecuted; ///< Flag to check if file deletion was executed.
    /**
     * @brief Constructs a DeckMainWindow with a reference to the main window and an optional parent.
     *
     * @param mainWindow A pointer to the main application window for inter-window communication.
     * @param parent The parent widget, typically nullptr for top-level windows.
     */
    explicit DeckMainWindow(MainWindow* mainWindow, QWidget *parent = nullptr);
    /**
     * @brief Loads the list of deck names from a JSON file.
     */
    void loadDeckNamesFromJson();
    /**
     * @brief Updates the UI with the current list of decks.
     */
    void updateUIWithDecks();
    /**
     * @brief Deletes the specified deck name inside Json file.
     * @param deckName The name of the deck whose file is to be deleted.
     */
    void deleteDeckFile(const QString& deckName);
    /**
     * @brief Clears all widgets from the deck layout.
     */
    void clearDeckLayout();

private slots:
    void writeToDeckJson(); ///< Writes the current list of decks to a JSON file.
    void onAddDeckClicked(); ///< Slot triggered when the add deck button is clicked.
    void onEditDeckClicked(const QString& deckName); ///< Slot triggered when an edit deck button is clicked.
    void onRenameDeckClicked(const QString& oldName); ///< Slot triggered when a rename deck action is initiated.
    void onDeleteDeckClicked(const QString& deckName, QWidget *deckWidget); ///< Slot triggered when a delete deck action is initiated.
    void onMainWindowClosed(); ///< Slot triggered when the main window is closed.
    void createNewDeck(const QString &name); ///< Creates a new deck with the given name.
    void printDecksList(); ///< Prints the current list of decks for debugging purposes.

private:
    MainWindow* mainWindow; ///< Reference to the main application window.
    const std::string deckJsonFile = "deck_names.json";
    QPushButton *addButton; ///< Button to add new deck.
    QVBoxLayout *deckLayout; ///< /layout to hold deck widgets.
    QList<QWidget*> deckWidgets; ///< List to hold all deck row widgets
    QMap<QToolButton*, QLabel*> editButtonsToDeckNames; ///< Maps edit button to their espective deck names.
};


#endif // DECKMAINWINDOW_H
