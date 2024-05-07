
/**
 * @file DeckWidget.h
 * @brief Declaration of the DeckWidget class for creating new decks.
 * @Author: Sara Mehravar
 *
 * This file declares the DeckWidget class, which provides a graphical interface
 * for users to create new decks by entering a name and either canceling the creation
 * process or proceeding to create the deck.
 */


#ifndef DECKWIDGET_H
#define DECKWIDGET_H

#include <QWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QString>

/**
 * @class DeckWidget
 * @brief Provides a widget for creating new decks.
 *
 * DeckWidget allows users to input a name for a new deck and offers options to either
 * cancel the operation or create the deck with the given name.
 */

class DeckWidget : public QWidget {
    Q_OBJECT

public:
    /**
     * @brief Constructs a DeckWidget with an optional parent.
     *
     * Initializes the widget components necessary for deck creation, including input fields
     * and control buttons.
     *
     * @param parent The parent widget of this DeckWidget, typically nullptr for top-level widgets.
     */
    explicit DeckWidget(QWidget *parent = nullptr);
    /**
     * @brief Provides access to the QLineEdit widget used for deck name input.
     *
     * @return A pointer to the QLineEdit widget where the user can enter the deck name.
     */
    QLineEdit *getNameEdit() const;
    /**
     * @brief Retrieves the current text entered in the deck name QLineEdit.
     *
     * @return The deck name as a QString entered by the user.
     */
    QString getDeckName() const;

signals:
    /**
     * @brief Emitted when the user decides to cancel deck creation.
     */
    void cancelDeckCreation();
    /**
     * @brief Emitted when the user confirms the creation of a deck with a specified name.
     *
     * @param name The name of the deck to be created.
     */
    void createDeck(const QString &name);  // Declare your signal here

private:
    QLineEdit *nameEdit; ///< Where the user enters the deck name.
    QPushButton *createButton; ///< create button for creation.
    QPushButton *cancelButton; ///< cancel button for cancellation
};

#endif // DECKWIDGET_H
