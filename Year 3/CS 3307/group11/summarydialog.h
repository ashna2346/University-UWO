/**
 * @file summarydialog.h
 * @brief Declaration of the SummaryDialog class for displaying session summaries.
 * @author Ashna Mittal
 *
 * The SummaryDialog class extends QDialog to provide a modal dialog window that displays
 * a summary of the session's results or statistics. It is designed for use in applications
 * that require end-of-session reporting, such as quizzes or tasks.
 */


#ifndef SUMMARYDIALOG_H
#define SUMMARYDIALOG_H

#include <QDialog>
#include <QLabel>
#include <QPushButton>
#include <QVBoxLayout>

/**
 * @class SummaryDialog
 * @brief A dialog window for displaying session summaries.
 *
 * SummaryDialog is a specialized QDialog that presents a summary or conclusion text
 * to the user at the end of a session. It contains a QLabel for the summary text and
 * provides a method to set this text.
 */

class SummaryDialog : public QDialog
{
    Q_OBJECT


public:
    /**
     * @brief Constructs a SummaryDialog with an optional parent.
     *
     * Initializes a new instance of SummaryDialog, setting up the UI components
     * including a QLabel for displaying summary text and a close button.
     *
     * @param parent The parent widget of the dialog, typically nullptr.
     */
    explicit SummaryDialog(QWidget *parent = nullptr);
    /**
     * @brief Sets the summary text to be displayed in the dialog.
     *
     * This method updates the text of the internal QLabel to display the summary or
     * conclusion of a session. It can be called before showing the dialog to the user.
     *
     * @param text The summary text to be displayed.
     */
    void setSummaryText(const QString &text);

private:
    QLabel *summaryLabel;///< The label used to display the summary text.
};

#endif // SUMMARYDIALOG_H
