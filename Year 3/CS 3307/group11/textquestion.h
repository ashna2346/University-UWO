/**
 * @file textquestion.h
 * @brief Declaration of the TextQuestion class.
 * @author Manpreet Saini
 *
 * This file declares the TextQuestion class, which is a derived class from Question designed
 * specifically for text-based questions. It includes functionality to display the question,
 * update a form with its details, and serialize the question for storage.
 */

#ifndef TEXTQUESTION_H
#define TEXTQUESTION_H
#include "question.h"
#include <cereal/types/polymorphic.hpp>

/**
 * @class TextQuestion
 * @brief Specialized class for text-based questions.
 *
 * TextQuestion extends the Question class to provide implementations specific to text-based
 * questions and answers. It includes methods for displaying the question on a QLabel, updating
 * the UI form with the question and answer text, and providing a unique label for the question.
 */

class TextQuestion : public Question
{
public:
    TextQuestion(); ///< Defaullt constructor
    TextQuestion(const std::string &q); ///< Consructor with question content
    TextQuestion(const std::string &q, const std::string &a, const std::string &deckName);///< Constructor with qestion content, answer content and deck name the question belongs to.
    /**
     * @brief Displays the question text on the provided QLabel.
     * @param qlbl Pointer to the QLabel where the question should be displayed.
     */
    void showQuestion(QLabel *qlbl) const override;
    /**
     * @brief Updates the UI form with the question and answer text.
     * @param win Pointer to the UI::MainWindow to be updated with the question and answer.
     */
    void updateForm(Ui::MainWindow *win) const override;
    /**
     * @brief Returns a label that uniquely identifies the question.
     * @return A string that represents the question label.
     */
    std::string label() const override;
    /**
     * @brief Serializes the TextQuestion object.
     *
     * This method is used by Cereal to serialize and deserialize TextQuestion objects, allowing them to be saved to or loaded from storage.
     *
     * @param ar The archive (either input or output) used for serialization.
     */
    template <class Archive>
    void serialize(Archive &ar);

};
#endif // TEXTQUESTION_H
