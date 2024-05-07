/**
 * @file summarydialog.cpp
 * @brief Implementation of the SummaryDialog class.
 * @author Ashna Mittal
 *
 * Provides the detailed implementation of the SummaryDialog class, including the setup of its UI
 * and functionalities. This class is used to display a summary of a session in a modal dialog.
 */

#include "summarydialog.h"

/**
 * @brief Constructs a SummaryDialog with the given parent.
 *
 * This constructor initializes the SummaryDialog with a title and a fixed size. It creates
 * a QLabel for displaying the session summary and a QPushButton for closing the dialog. The
 * layout for these widgets is also set here.
 *
 * @param parent The parent widget of this dialog, typically nullptr.
 */

SummaryDialog::SummaryDialog(QWidget *parent) : QDialog(parent)
{
    ///< Set up the dialog's properties
    setWindowTitle("Session Summary"); ///< Sets the window title for the dialog
    resize(400, 300); ///< Sets a fixed size for the dialog

    ///< Create the summary label, default text and word wrapping enaabled
    summaryLabel = new QLabel(this);
    summaryLabel->setText("Summary will appear here");
    summaryLabel->setWordWrap(true);

    ///< Create a layout and add the label to it
    QVBoxLayout *layout = new QVBoxLayout(this);
    layout->addWidget(summaryLabel);

    ///< Optionally add a close button
    QPushButton *closeButton = new QPushButton("Close", this);
    connect(closeButton, &QPushButton::clicked, this, &SummaryDialog::accept);
    layout->addWidget(closeButton);

    ///< Set the dialog's main layout
    setLayout(layout);
}

/**
 * @brief Sets the text of the summary label to the provided summary.
 *
 * This method allows updating the text of the summary label within the dialog, typically called
 * to display the results or summary of a session before showing the dialog.
 *
 * @param text The summary text to be displayed.
 */

void SummaryDialog::setSummaryText(const QString &text)
{
    summaryLabel->setText(text); ///< Update the summary label with the provided text
}
