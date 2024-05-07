/**
 * @file textquestion.cpp
 * @brief Implementation of the TextQuestion class for text-based quiz questions.
 * @author Manpreet Saini
 *
 * Provides the implementation for TextQuestion, a specialized type of question that includes
 * text for both the question and answer. This file also includes serialization functions for
 * saving and loading questions to and from a file.
 */

#include <string>
#include <iostream>
#include "question.h"
#include "textquestion.h"
#include <cereal/archives/xml.hpp>
#include <cereal/types/polymorphic.hpp>
#include <QLineEdit>
#include <QPlainTextEdit>
#include <QTabWidget>
#include "ui_mainwindow.h"
#include <cereal/archives/json.hpp>
#include <cereal/types/vector.hpp>
#include <cereal/types/memory.hpp>
#include <fstream>
#include <vector>
#include <memory>

/**
 * @brief Default constructor for TextQuestion.
 *
 * Initializes a new instance of TextQuestion with default values inherited from Question.
 */
TextQuestion::TextQuestion() :
    Question(){}

/**
 * @brief Constructs a TextQuestion with the question text.
 *
 * @param q The text of the question.
 */

TextQuestion::TextQuestion(const std::string &q) :
    Question(q) {}

/**
 * @brief Constructs a TextQuestion with question text, answer text, and the deck name.
 *
 * @param q The text of the question.
 * @param a The text of the answer.
 * @param deckName The name of the deck this question belongs to.
 */

TextQuestion::TextQuestion(const std::string &q, const std::string &a, const std::string &deckName) :
    Question(q, a, deckName) {}

/**
 * @brief Displays the question text on a QLabel.
 *
 * @param qlbl Pointer to the QLabel where the question should be displayed.
 */

void TextQuestion::showQuestion(QLabel *qlbl) const {
    qlbl->setText(QString::fromStdString(_question));
}

/**
 * @brief Updates the UI form with the question and answer text.
 *
 * This method sets the text of UI elements in the MainWindow to reflect the current
 * question and answer.
 *
 * @param win Pointer to the MainWindow UI where the question and answer should be displayed.
 */

void TextQuestion::updateForm(Ui::MainWindow *win) const {
    win->tquestion_text->setText(QString::fromStdString(_question));
    win->tanswer_text->setPlainText(QString::fromStdString(_answer));
    win->tabWidget->setCurrentIndex(0);
}

/**
 * @brief Generates a label for the TextQuestion.
 *
 * @return A string representing the label of the question.
 */

std::string TextQuestion::label() const {
    return "Question: " + _question;
}

/**
 * @brief Serializes the TextQuestion object.
 *
 * @param ar The archive (input or output) used for serialization.
 */

template <class Archive>
void TextQuestion:: serialize(Archive &ar) {
    ar(cereal::base_class<Question>(this));
}

/**
 * @brief Saves a collection of questions to a JSON file.
 *
 * @param questions: A vector of shared pointers to Question objects.
 * @param filePath: The path to the JSON file where questions will be saved.
 */

void saveQuestions(const std::vector<std::shared_ptr<Question>>& questions, const std::string& filePath) {
    std::ofstream os(filePath);
    cereal::JSONOutputArchive archive(os);
    archive(cereal::make_nvp("questions", questions));
}

/**
 * @brief Loads a collection of questions from a JSON file.
 *
 * @param questions: A vector of shared pointers to Question objects to be populated.
 * @param filePath: The path to the JSON file from which questions will be loaded.
 */

void loadQuestions(std::vector<std::shared_ptr<Question>>& questions, const std::string& filePath) {
    std::ifstream is(filePath);
    if (is) {
        cereal::JSONInputArchive archive(is);
        archive(cereal::make_nvp("questions", questions));
    }
}

///< Register TextQuestion with Cereal for polymorphic serialization
CEREAL_REGISTER_TYPE(TextQuestion)

CEREAL_REGISTER_POLYMORPHIC_RELATION(Question, TextQuestion)
