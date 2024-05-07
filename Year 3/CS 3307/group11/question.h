/**
 * @file question.h
 * @brief Declaration of the abstract Question class for quiz questions.
 * @authors Ashna Mittal, Manpreet Saini
 *
 * Defines the Question class used in the quiz application. This class provides the base
 * structure for creating, managing, and displaying quiz questions, including their context,
 * answers, associated deck name, and difficulty rating. It is designed to be extended by
 * specific question types.
 */

#ifndef QUESTION_H
#define QUESTION_H

#include "ui_mainwindow.h"
#include <string>
#include <cereal/access.hpp>
#include <cereal/cereal.hpp>

/**
 * @class Question
 * @brief Abstract base class for quiz questions.
 *
 * Question is an abstract class that defines the basic structure and functionality of a quiz
 * question, including getters and setters for the question text, answer, deck name, and difficulty
 * rating. It requires implementation of specific methods for displaying the question and updating
 * forms in the UI.
 */

class Question {

protected:
    std::string _question; ///< content of the question
    std::string _answer; ///< content of the answer to the question
    std::string _deckName;///< the deck name, question belongs to.
    int _difficultyRating = 0; ///< The difficulty rating of the question.

public:
    Question(); ///< Default constructor
    Question(const std::string &q); ///< Constructor with content of question
    ///< Constructor with content of the question, answer to the question, Deck name question belongs to and difficulty rating of the question.
    Question(const std::string &q, const std::string &a, const std::string &deckName, int difficultyRating = 0);
    std::string question() const; ///< Getter for the question content.
    std::string answer() const; ///< Getter for the content of answer to the question.
    std::string deckName() const; ///< Getter for name of the deck question belongs to.
    virtual std::string label() const = 0;
    void setQuestion(const std::string &q); ///< Setter for the content of the question.
    void setAnswer(const std::string &a); ///< Setter for the content of the answer to the question
    void setDeckName(const std::string &deckName); ///< Setter for name of the deck, the question belongs to.
    /**
     * @brief Pure virtual method to display the question on a QLabel.
     *
     * @param qlbl Pointer to the QLabel where the question should be displayed.
     */
    virtual void showQuestion(QLabel *qlbl) const = 0;
    /**
     * @brief Pure virtual method to update the form in the UI based on the question details.
     *
     * @param win Pointer to the UI::MainWindow to be updated.
     */
    virtual void updateForm(Ui::MainWindow *win) const = 0;
    void setDifficultyRating(int rating); ///< Setter for the difficulty rating
    int difficultyRating() const; ///< Getter for the difficulty rating.
    friend class cereal::access; ///< This allows Cereal to access private members
    /**
     * @brief Serializes the question data for persistence.
     *
     * @param ar The archive (input or output) used for serialization.
     */
    template<class Archive>
    void serialize(Archive & ar)
    {
        ar(CEREAL_NVP(_question), CEREAL_NVP(_answer), CEREAL_NVP(_deckName), CEREAL_NVP(_difficultyRating));
    }

};



#endif // QUESTION_H
