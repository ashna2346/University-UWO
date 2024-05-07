/**
 * @file deckwidget.cpp
 * @brief Implementation of the DeckWidget class.
 * @author Sara Mehravar
 *
 * Provides the implementation for the DeckWidget class which allows users to input
 * and submit a new deck name for a card game or quiz application.
 */


#include "deckwidget.h"
#include <QVBoxLayout>
#include <QPushButton>
#include <QLineEdit>



/**
 * @brief Constructs a DeckWidget object with a parent.
 *
 * This constructor initializes the DeckWidget, creating a QLineEdit for deck name input
 * and setting up the layout of the widget.
 *
 * @param parent The parent widget of this DeckWidget, typically nullptr if it's a top-level widget.
 */
DeckWidget::DeckWidget(QWidget *parent) : QWidget(parent) {
    nameEdit = new QLineEdit(this); ///< Initialize the QLineEdit and set placeholder text
    nameEdit->setPlaceholderText("Enter deck name");

    QVBoxLayout *layout = new QVBoxLayout(this);
    layout->addWidget(nameEdit); ///< Add the QLineEdit to the layout

    setLayout(layout);
}

/**
 * @brief Returns the QLineEdit widget used for deck name input.
 *
 * This method provides access to the QLineEdit widget, allowing further customization
 * or retrieval of its properties outside the DeckWidget class.
 *
 * @return A pointer to the QLineEdit widget used for inputting the deck name.
 */

QLineEdit *DeckWidget::getNameEdit() const {
    return nameEdit;
}

/**
 * @brief Retrieves the current deck name input by the user.
 *
 * This method returns the text currently entered in the QLineEdit widget, which
 * represents the name of the deck as input by the user.
 *
 * @return A QString containing the current text of the nameEdit QLineEdit.
 */

QString DeckWidget::getDeckName() const {
    return nameEdit->text();
}
