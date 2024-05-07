/**
 * @file deckmainwindow.cpp
 * @brief Implementation of the DeckMainWindow class.
 * @authors Ashna Mittal, Manpreet Saini, Sara Mehravar, Parth Bhandari
 *
 * This file includes the implementation of DeckMainWindow, which handles the main window
 * interface for managing decks in a quiz or flashcard application. It includes functionalities
 * to add, edit, rename, and delete decks, as well as saving and loading deck configurations to and from json files.
 */

#include "deckmainwindow.h"
#include "deckwidget.h"
#include "mainwindow.h"
#include "card.h"
#include <QLabel>
#include <QDialog>
#include <QDialogButtonBox>
#include <QMessageBox>
#include <QHBoxLayout>
#include <QToolButton>
#include <QIcon>
#include <QMap>
#include <QJsonObject>
#include <QJsonDocument>
#include <QJsonArray>
#include <QFile>
#include <QDebug>
#include <QStandardPaths>
#include <QDir>
#include "ui_deckmainwindow.h"
#include <QInputDialog>


/**
 * @brief Constructs a DeckMainWindow with a specified main window and parent widget.
 *
 * Sets up the main layout, including the instruction label and add button, and
 * initializes the deck management functionalities.
 *
 * @param mainWindow Pointer to the main application window for interaction.
 * @param parent Parent widget, typically nullptr for top-level windows.
 */
DeckMainWindow::DeckMainWindow(MainWindow* mainWindow,QWidget *parent) : QWidget(parent), mainWindow(mainWindow), deckLayout(new QVBoxLayout()), editButtonsToDeckNames() {
    ///< Instruction label


    loadDeckNamesFromJson();

    QLabel *instructionLabel = new QLabel("Press + to add a new deck");
    instructionLabel->setFont(QFont("Arial", 12, QFont::Bold));
    instructionLabel->setAlignment(Qt::AlignCenter); ///< Center the text

    ///<Add button
    addButton = new QPushButton("+");
    addButton->setFont(QFont("Arial", 18, QFont::Bold));
    addButton->setStyleSheet("background-color: #E6E6E6; color: black;");
    addButton->setFixedSize(50, 50);
    connect(addButton, &QPushButton::clicked, this, &DeckMainWindow::onAddDeckClicked);


    ///<Main layout
    QVBoxLayout *mainLayout = new QVBoxLayout(this);
    mainLayout->addWidget(instructionLabel); ///< Add the instruction label to the layout
    mainLayout->addWidget(addButton, 0, Qt::AlignCenter);
    mainLayout->addLayout(deckLayout);

    setLayout(mainLayout);
    setWindowTitle("Intellecto");
    resize(600, 600);
}

/**
 * @brief Writes the current list of deck names to a JSON file.
 *
 * Serializes the deck names list to JSON format and saves it to the specified file,
 * ensuring persistence of deck information across application sessions.
 */
void DeckMainWindow::writeToDeckJson() {
    QJsonObject jsonObject;
    jsonObject["decksList"] = QJsonArray::fromStringList(decksList);

    ///< Convert JSON object to a document
    QJsonDocument jsonDocument(jsonObject);

    ///< Write JSON document to file
    QFile file(QString::fromStdString(deckJsonFile)); ///< Ensure deckJsonFile is of type QString or convert it if necessary
    if (file.open(QIODevice::WriteOnly)) {
        file.write(jsonDocument.toJson());
        file.close();
    }
}
/**
 * @brief Slot triggered by clicking the 'Add Deck' button.
 *
 * Opens a dialog allowing the user to input a new deck name and handles the creation
 * of the deck based on user input.
 */

void DeckMainWindow::onAddDeckClicked() {
    QDialog *newDeckDialog = new QDialog(this);
    newDeckDialog->setWindowTitle("Create New Deck");

    auto *deckWidget = new DeckWidget(newDeckDialog);

    ///< Set up the dialog buttons
    QDialogButtonBox *buttonBox = new QDialogButtonBox(QDialogButtonBox::Ok | QDialogButtonBox::Cancel);
    QVBoxLayout *dialogLayout = new QVBoxLayout(newDeckDialog);
    dialogLayout->addWidget(deckWidget->getNameEdit());
    dialogLayout->addWidget(buttonBox);

    newDeckDialog->setLayout(dialogLayout);

    ///< Connect the Ok button to a lambda that emits a signal with the deck name
    connect(buttonBox, &QDialogButtonBox::accepted, [this, deckWidget, newDeckDialog]() {
        ///< Emit the signal with the deck name
        emit deckWidget->createDeck(deckWidget->getDeckName());
        newDeckDialog->accept();
    });

    ///< Connect the Cancel button to just close the dialog
    connect(buttonBox, &QDialogButtonBox::rejected, newDeckDialog, &QDialog::reject);

    ///< Connect the createDeck signal from DeckWidget to the createNewDeck slot in MainWindow
    connect(deckWidget, &DeckWidget::createDeck, this, &DeckMainWindow::createNewDeck);

    ///< Execute the dialog and check the result
    int result = newDeckDialog->exec();
    if (result == QDialog::Accepted) {
        ///< The dialog was accepted, no need to create a new deck here because the signal is connected
    }

    ///< Make sure to clean up the dialog after it's closed
    newDeckDialog->deleteLater();


}

/**
 * @brief Creates a new deck with the given name.
 *
 * Adds the new deck name to the list, updates the UI to include the new deck, and
 * persists the updated deck list to a JSON file.
 *
 * @param name Name of the new deck to be added.
 */

void DeckMainWindow::createNewDeck(const QString &name) {
    if (name.trimmed().isEmpty()) {
        // Handle empty deck name
        return;
    }

    decksList.append(name.trimmed());
    printDecksList();


    QWidget *deckRowWidget = new QWidget(this);
    QLabel *deckNameLabel = new QLabel(name, deckRowWidget);
    deckNameLabel->setFont(QFont("Arial", 11));

    QToolButton* renameButton = new QToolButton();
    renameButton->setIcon(QIcon(":/new/prefix1/Deckname.png"));

    QToolButton *editButton = new QToolButton(deckRowWidget);
    editButton->setIcon(QIcon(":/new/prefix1/pencil.png"));

    QToolButton *deleteButton = new QToolButton(deckRowWidget);
    deleteButton->setIcon(QIcon(":/new/prefix1/bin.png"));
    deleteButton->setStyleSheet("background-color: red;");

    QHBoxLayout *deckRowLayout = new QHBoxLayout(deckRowWidget);
    deckRowLayout->addWidget(deckNameLabel);

    ///<initializeStartTestButton(deckRowLayout);
    deckRowLayout->addWidget(renameButton);
    deckRowLayout->addWidget(editButton);
    deckRowLayout->addWidget(deleteButton);


    deckLayout->addWidget(deckRowWidget);

    connect(renameButton, &QToolButton::clicked, this, [this, name]() {
        onRenameDeckClicked(name);
    });

    connect(editButton, &QToolButton::clicked, this, [=]() { onEditDeckClicked(name); });

    connect(deleteButton, &QPushButton::clicked, this, [this, name, deckRowWidget]() {
        onDeleteDeckClicked(name, deckRowWidget);
    });




    editButtonsToDeckNames[editButton] = deckNameLabel;
    deckRowWidget->show();

    MainWindow* w = new MainWindow(name, this);

    ///< Connect the MainWindow's destroyed signal to a slot that removes the modal attribute
    connect(w, &MainWindow::destroyed, this, &DeckMainWindow::onMainWindowClosed);

    w->setAttribute(Qt::WA_DeleteOnClose); // Ensure it gets deleted on close
    w->show();

    saveDeckNamesToJson();
}

/**
 * @brief Slot triggered when a deck's edit button is clicked.
 *
 * Opens the MainWindow in edit mode for the selected deck, allowing for question
 * management within the deck.
 *
 * @param deckName Name of the deck to be edited.
 */
void DeckMainWindow::onEditDeckClicked(const QString& deckName) {
    qDebug() << "Edit button clicked for deck:" << deckName;
    QToolButton* senderButton = qobject_cast<QToolButton*>(sender());
    if (!senderButton) return;

    ///< Create and show MainWindow as modal to the current window/dialog
    MainWindow* w = new MainWindow(deckName, this);

    ///< Connect the MainWindow's destroyed signal to a slot that removes the modal attribute
    connect(w, &MainWindow::destroyed, this, &DeckMainWindow::onMainWindowClosed);

    w->setAttribute(Qt::WA_DeleteOnClose); ///< Ensure it gets deleted on close
    w->show();
}

/**
 * @brief Slot called when the MainWindow is closed.
 *
 * Handles cleanup and UI updates when the MainWindow is closed.
 */
void DeckMainWindow::onMainWindowClosed() {
    ///< Remove the modal attribute when the MainWindow is closed
    MainWindow* w = qobject_cast<MainWindow*>(sender());
    if (w) {

    }

}

/**
 * @brief Prints the current list of deck names to the debug console.
 *
 * Useful for debugging purposes, this method iterates through the deck names list and
 * outputs each name to the console.
 */
void DeckMainWindow::printDecksList() {
    qDebug() << "Decks List:";
    for (const QString& deckName : decksList) {
        qDebug() << deckName;
    }
}

/**
 * @brief Saves the current list of deck names to a JSON file.
 *
 * Iterates over the decksList, serializes it to JSON, and writes it to a file,
 * ensuring the current state of decks is saved.
 */
void DeckMainWindow::saveDeckNamesToJson() {
    qDebug() << "Saving";

    ///< Relative path to the 'data' directory from the project root (adjust accordingly)
    QDir dataDir("data");
    if (!dataDir.exists()) {
        dataDir.mkpath(".");
    }

    QString filePath = dataDir.filePath("deck_names.json");
    qDebug() << "Saving JSON to:" << filePath; ///< Log the file path for verification

    QFile file(filePath);
    if (!file.open(QIODevice::WriteOnly)) {
        qWarning() << "Couldn't open file for writing at:" << filePath;
        return;
    }

    QJsonObject jsonObject;
    QJsonArray decksArray;
    for (const QString& deckName : decksList) {
        decksArray.append(deckName);
    }
    jsonObject["decks"] = decksArray;

    QJsonDocument doc(jsonObject);
    if (file.write(doc.toJson()) == -1) {
        qWarning() << "Failed to write to file:" << filePath;
    } else {
        qDebug() << "Successfully saved JSON to:" << filePath; ///< Confirm success
    }
    file.close();
}

/**
 * @brief Loads the list of deck names from a JSON file.
 *
 * Reads a JSON file containing deck names, deserializes it, and updates the application's
 * deck list to match the loaded data.
 */
void DeckMainWindow::loadDeckNamesFromJson() {
    QDir dataDir("data"); ///< Adjust to match save logic
    if (!dataDir.exists()) {
        qDebug() << "Data directory does not exist. No decks to load.";
        return;
    }

    QString filePath = dataDir.filePath("deck_names.json");
    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly)) {
        qWarning() << "Couldn't open file for reading deck names at:" << filePath;
        return;
    }

    QByteArray saveData = file.readAll();
    QJsonDocument doc(QJsonDocument::fromJson(saveData));
    QJsonObject jsonObject = doc.object();
    QJsonArray decksArray = jsonObject["decks"].toArray();

    decksList.clear();
    for (const QJsonValue& value : decksArray) {
        decksList.append(value.toString().trimmed()); ///< Ensure trimmed names
    }

    updateUIWithDecks(); ///< Refresh UI with loaded decks
}

/**
 * @brief Updates the UI to reflect the current list of decks.
 *
 * Clears any existing deck display elements from the UI and repopulates them based on
 * the current list of decks.
 */
void DeckMainWindow::updateUIWithDecks() {
    clearDeckLayout();
    qDeleteAll(deckLayout->children()); ///< Clear existing UI elements in deckLayout

    for (const QString& deckName : decksList) {
        QWidget* deckRowWidget = new QWidget();
        QHBoxLayout* deckRowLayout = new QHBoxLayout(deckRowWidget);

        ///< Deck name label
        QLabel* deckNameLabel = new QLabel(deckName);
        deckNameLabel->setFont(QFont("Arial", 11));
        deckRowLayout->addWidget(deckNameLabel);

        ///< Add Rename button
        QToolButton* renameButton = new QToolButton();
        renameButton->setIcon(QIcon(":/new/prefix1/Deckname.png"));
        deckRowLayout->addWidget(renameButton);

        connect(renameButton, &QToolButton::clicked, this, [this, deckName]() {
            onRenameDeckClicked(deckName);
        });


        ///< Edit button
        QToolButton* editButton = new QToolButton();
        editButton->setIcon(QIcon(":/new/prefix1/pencil.png"));
        deckRowLayout->addWidget(editButton);

        ///< Delete button
        QToolButton* deleteButton = new QToolButton();
        deleteButton->setIcon(QIcon(":/new/prefix1/bin.png"));
        deleteButton->setStyleSheet("background-color: red;");
        deckRowLayout->addWidget(deleteButton);

        connect(editButton, &QToolButton::clicked, this, [=]() { onEditDeckClicked(deckName); });
        connect(deleteButton, &QPushButton::clicked, this, [this, deckName, deckRowWidget]() {
            onDeleteDeckClicked(deckName, deckRowWidget);
        });

        ///< Add the deck row to the main layout
        deckLayout->addWidget(deckRowWidget);

    }
}

/**
 * @brief Slot triggered when a deck's delete button is clicked.
 *
 * Confirms with the user before removing the selected deck from the UI and list, and
 * deletes any associated files.
 *
 * @param deckName Name of the deck to be deleted.
 * @param deckWidget Widget associated with the deck in the UI.
 */
void DeckMainWindow::onDeleteDeckClicked(const QString& deckName, QWidget *deckWidget) {
    QMessageBox msgBox;
    msgBox.setWindowTitle("Confirm Deletion");
    msgBox.setText("Are you sure you want to delete the deck: " + deckName + "?");
    msgBox.setStandardButtons(QMessageBox::Yes | QMessageBox::No);
    msgBox.setDefaultButton(QMessageBox::No);
    ///< Set custom icon
    msgBox.setIconPixmap(QPixmap(":/new/prefix1/deleteDeck.png").scaled(64, 64, Qt::KeepAspectRatio, Qt::SmoothTransformation));

    if (msgBox.exec() == QMessageBox::Yes) {
        ///< Remove the deck widget from the UI
        deckLayout->removeWidget(deckWidget);
        deckWidgets.removeOne(deckWidget);
        deckWidget->deleteLater();

        ///< Remove the deck name from the list and update the JSON file
        decksList.removeAll(deckName); ///< Assuming deckName is unique
        saveDeckNamesToJson(); ///< Call the function to save the updated list to JSON

        if (deckWidgets.isEmpty()) {
            addButton->show();
        }

        qDebug() << "Attempting to delete deck:" << deckName;

        ///< Proceed to delete the deck's specific JSON file
        deleteDeckFile(deckName.trimmed());
    } else {
        ///< Do nothing if user clicks 'No'
        qDebug() << "Deletion cancelled for deck:" << deckName;
    }
}

/**
 * @brief Deletes the JSON file associated with a deck.
 *
 * Attempts to locate and delete the file corresponding to the given deck name. Logs
 * messages based on the outcome of the deletion attempt.
 *
 * @param deckName Name of the deck whose file should be deleted.
 */
void DeckMainWindow::deleteDeckFile(const QString& deckName) {
    QString filePath = deckName.trimmed() + ".json";

    QFile file(filePath);
    if (!file.exists()) {
        qDebug() << "File does not exist, skipping deletion:" << filePath;
        return; ///< Exit if file doesn't exist to avoid unnecessary warning.
    }

    if (file.remove()) {
        qDebug() << "File successfully deleted:" << filePath;
    } else {
        qWarning() << "Failed to delete file:" << filePath << ". Error:" << file.errorString();
    }
}

/**
 * @brief Slot triggered when a deck's rename button is clicked.
 *
 * Prompts the user for a new deck name and handles the renaming of the deck, including
 * updating the UI and persisting the change.
 *
 * @param oldName The current name of the deck to be renamed.
 */
void DeckMainWindow::onRenameDeckClicked(const QString& oldName) {
    bool ok;
    QString newName = QInputDialog::getText(this, tr("Rename Deck"),
                                            tr("New name:"), QLineEdit::Normal,
                                            oldName, &ok);
    if (ok && !newName.isEmpty() && newName != oldName) {
        if (decksList.contains(newName)) {
            QMessageBox::warning(this, tr("Name Exists"),
                                 tr("Another deck with this name already exists. Please choose a different name."));
            return;
        }

        QString oldFilePath = QDir::current().absoluteFilePath(oldName + ".json");
        QString newFilePath = QDir::current().absoluteFilePath(newName + ".json");
        if (QFile::exists(newFilePath)) {
            QMessageBox::warning(this, tr("File Exists"), tr("A file with the new name already exists."));
            return;
        }
        if (!QFile::rename(oldFilePath, newFilePath)) {
            QMessageBox::critical(this, tr("Error"), tr("Failed to rename the deck file."));
            return;
        }

        int index = decksList.indexOf(oldName);
        if (index != -1) {
            decksList[index] = newName; ///< Update the name in the list
            saveDeckNamesToJson(); ///< Save changes to JSON
            updateUIWithDecks(); ///< Immediately refresh the UI
        }
    }
}

/**
 * @brief Clears all widgets from the deck layout.
 *
 * Iterates through and deletes all widgets currently displayed in the deckLayout,
 * preparing it for an update or refresh.
 */
void DeckMainWindow::clearDeckLayout() {
    QLayoutItem* item;
    while ((item = deckLayout->takeAt(0)) != nullptr) {
        if (item->widget()) {
            delete item->widget(); ///< Delete the widget
        }
        delete item; ///< Delete the layout item
    }
}

