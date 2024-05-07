
/**
 * @file question.cpp
 * @brief Implementation of the Question class.
 * @authors AShna Mittal, Manpreet Saini
 *
 * This file provides the implementation for the Question class, including constructors,
 * getters, and setters for managing question properties such as the question text,
 * answer, deck name, and difficulty rating.
 */

#include <string>
#include "question.h"
#include <cereal/access.hpp> ///< included for cearalization

/**
 * @brief Default constructor for the Question class.
 *
 * Initializes a new Question object with default values.
 */

Question::Question() {
    _question = "";
    _answer = "";
    _difficultyRating = 0; ///< Initialize difficulty rating
}

/**
 * @brief Overloaded constructor for initializing a Question with text.
 *
 * Initializes the question text while leaving other properties with default values.
 *
 * @param q The text of the question.
 */

Question::Question(const std::string &q) : Question() { // Delegate to default constructor
    _question = q;
}

/**
 * @brief Overloaded constructor for initializing a Question with full details.
 *
 * Initializes a new Question object with question text, answer, deck name, and difficulty rating.
 *
 * @param q The text of the question.
 * @param a The text of the answer.
 * @param deckName The name of the deck this question belongs to.
 * @param difficultyRating The difficulty rating of the question.
 */

Question::Question(const std::string &q, const std::string &a, const std::string &deckName, int difficultyRating) {
    _question = q;
    _answer = a;
    _deckName = deckName;
    _difficultyRating = difficultyRating; // Set difficulty rating
}

/**
 * @brief Getter for the question text.
 *
 * @return The context of question.
 */

std::string Question::question() const {
    return _question;
}

/**
 * @brief Getter for the answer text.
 *
 * @return The context of the answer to the question.
 */

std::string Question::answer() const {
    return _answer;
}

/**
 * @brief Getter for the deck name.
 *
 * @return The name of the deck this question belongs to.
 */

std::string Question::deckName() const {
    return _deckName;
}

/**
 * @brief Setter for the question text.
 *
 * @param q The context of the question.
 */

void Question::setQuestion(const std::string &q) {
    _question = q;
}

/**
 * @brief Setter for the answer text.
 *
 * @param a The context of the answer to the question.
 */

void Question::setAnswer(const std::string &a) {
    _answer = a;
}

/**
 * @brief Setter for the deck name.
 *
 * @param deckName The new name for the deck.
 */

void Question::setDeckName(const std::string &deckName) {
    _deckName = deckName;
}

/**
 * @brief Setter for the difficulty rating.
 *
 * @param the difficulty level of the question.
 */

void Question::setDifficultyRating(int rating) {
    _difficultyRating = rating; // Implementation of setter
}

/**
 * @brief Getter for the difficulty rating.
 *
 * @return The difficulty rating of the question.
 */

int Question::difficultyRating() const {
    return _difficultyRating;
}
