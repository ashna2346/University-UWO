/**
 * @file card.h
 * @brief Declaration of the Card class, a part of the quiz application.
 * @author Ashna Mittal
 *
 * This file declares the Card class, which is responsible for presenting questions and answers to the user,
 * allowing them to navigate through questions, set difficulty ratings, and view a summary of their session.
 */


#ifndef CARD_H
#define CARD_H

#include <QDialog>
#include <memory>
#include "question.h"


namespace Ui {
class Card;
}

/**
 * @class Card
 * @brief For displaying quiz questions and interacting with them.
 *
 * Card displays individual questions to the user, allows them to answer, navigate through questions,
 * rate the difficulty of each question, and see a summary of their session at the end.
 */

class Card : public QDialog
{
    Q_OBJECT

public:
    /**
     * @brief Constructs a Card dialog with a parent.
     *
     * This constructor initializes the dialog with no questions loaded.
     *
     * @param parent The parent widget of this dialog, typically nullptr.
     */
    explicit Card(QWidget *parent = 0);
    /**
     * @brief Constructs a Card dialog with a list of questions.
     *
     * This constructor initializes the dialog with a list of questions and displays the first one.
     *
     * @param parent The parent widget of this dialog, typically nullptr.
     * @param test A vector of shared pointers to Question objects.
     */
    explicit Card(QWidget *parent = 0, std::vector<std::shared_ptr<Question>> test = *(new std::vector<std::shared_ptr<Question>>()));
    /**
     * @brief Destroys the Card dialog.
     */
    ~Card();

public slots:
    void setVeryEasy(); ///< Sets the current question's difficulty to Very Easy.
    void setEasy(); ///< Sets the current question's difficulty to Easy.
    void setModerate(); ///< Sets the current question's difficulty to Moderate.
    void setDifficult(); ///< Sets the current question's difficulty to Difficult.
    void setVeryDifficult(); ///< Sets the current question's difficulty to Very Difficult.

private slots:
    void on_show_button_clicked(); ///< Displays the answer to the current question.
    void on_next_button_clicked(); ///< Moves to the next question or shows the summary.
    void on_back_button_clicked(); ///< Moves back to the previous question.

signals:
    /**
     * @brief Emitted when any question's difficulty rating is updated.
     */
    void questionsUpdated();

private:
    Ui::Card *ui; ///< UI elements for the Card dialog.
    std::vector<std::shared_ptr<Question>> test_; ///< Questions to be displayed.
    unsigned int index; ///< Index of the current question.
    int veryEasyCount = 0; ///< Count of Very Easy questions.
    int easyCount = 0; ///< Count of Easy questions.
    int moderateCount = 0; ///< Count of Moderate questions.
    int difficultCount = 0; ///< Count of Difficult questions.
    int veryDifficultCount = 0; ///< Count of Very Difficult questions.
    bool showingSummary = false; ///< Flag indicating whether the summary is being displayed.
    /**
     * @brief Adjusts the count of questions based on their difficulty rating.
     *
     * @param oldRating The previous difficulty rating of the question.
     * @param newRating The new difficulty rating to be set for the question.
     */
    void adjustDifficultyCount(int oldRating, int newRating);
    /**
     * @brief Resets the quiz session for a new start.
     *
     * Resets the counts, index, and other relevant state to begin a new session.
     */
    void resetSession();
};





#endif // CARD_H
