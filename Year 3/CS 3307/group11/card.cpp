/**
 * @file card.cpp
 * @brief Implementation of the Card class for the quiz application.
 * @author Ashna Mittal
 *
 * This file provides the implementation of the Card class, which includes functionalities
 * for displaying quiz questions and answers, navigating through questions, setting and updating
 * difficulty ratings, and summarizing the quiz session.
 */


#include "card.h"
#include "ui_card.h"
#include "question.h"
#include <QDebug>
#include "summarydialog.h"

/**
 * @brief Default constructor for the Card class.
 *
 * Initializes a new Card instance. Sets up the user interface and initializes
 * the index to start from the first question.
 *
 * @param parent The parent widget of this dialog, usually nullptr.
 */

Card::Card(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Card)
{
    ui->setupUi(this);
    index = 0;
}

// In the constructor of Card class in card.cpp

/**
 * @brief Overloaded constructor for the Card class with a list of questions.
 *
 * Initializes a new Card instance with a list of questions. Sets up the user interface,
 * displays the first question, and connects the difficulty rating buttons to their slots.
 *
 * @param parent The parent widget of this dialog, usually nullptr.
 * @param test A vector of shared_ptr to Question objects to be displayed.
 */

Card::Card(QWidget *parent, std::vector<std::shared_ptr<Question>> test) :
    QDialog(parent),
    ui(new Ui::Card) {
    ui->setupUi(this);
    test_ = test;
    index = 0;
    ui->qlabel->adjustSize();
    test_[index]->showQuestion(ui->qlabel);

    // Connect the difficulty buttons to the slots
    connect(ui->VE, &QPushButton::clicked, this, &Card::setVeryEasy);
    connect(ui->E, &QPushButton::clicked, this, &Card::setEasy);
    connect(ui->M, &QPushButton::clicked, this, &Card::setModerate);
    connect(ui->D, &QPushButton::clicked, this, &Card::setDifficult);
    connect(ui->VD, &QPushButton::clicked, this, &Card::setVeryDifficult);
}


/**
 * @brief Destructor for the Card class.
 */

Card::~Card()
{
    delete ui;
}

/**
 * @brief Slot to display the answer to the current question.
 */

void Card::on_show_button_clicked()
{
    ui->alabel->setText(QString::fromStdString(test_[index]->answer()));
}

/**
 * @brief Slot to advance to the next question or display the summary if at the end.
 */

void Card::on_next_button_clicked()
{
    if(showingSummary) {
        this->close();
        return;
    }

    index++;
    if(index < test_.size()) {
        ui->qlabel->clear();
        ui->alabel->clear();
        test_[index]->showQuestion(ui->qlabel);
    }
    else {
        // Prepare and display the summary
        QString summaryText = QString("Test Session Summary:\n\n"
                                      "Very Difficult Questions: %1\n"
                                      "Difficult Questions: %2\n"
                                      "Moderate Questions: %3\n"
                                      "Easy Questions: %4\n"
                                      "Very Easy Questions: %5")
                                  .arg(veryDifficultCount)
                                  .arg(difficultCount)
                                  .arg(moderateCount)
                                  .arg(easyCount)
                                  .arg(veryEasyCount);

        // Create and show the summary dialog
        SummaryDialog summaryDialog(this);
        summaryDialog.setSummaryText(summaryText);
        summaryDialog.exec(); // Display as modal dialog
        this->close();
    }
}

/**
 * @brief Slot to return to the previous question.
 */

void Card::on_back_button_clicked()
{
    if(index > 0){
        index--;
        ui->qlabel->clear();
        ui->alabel->clear();
        test_[index]->showQuestion(ui->qlabel);
    }

}

/**
 * @brief Slot to set the current question's difficulty rating to Very Easy.
 */

void Card::setVeryEasy() {
    if (index < test_.size()) {
        int oldRating = test_[index]->difficultyRating();
        if(oldRating != 1) {
            adjustDifficultyCount(oldRating, 1);
            test_[index]->setDifficultyRating(1);
            qDebug() << "Rating set to Very Easy for question" << index;
            emit questionsUpdated();
        }
    }
}

/**
 * @brief Slot to set the current question's difficulty rating to Easy.
 */

void Card::setEasy() {
    if (index < test_.size()) {
        int oldRating = test_[index]->difficultyRating();
        if(oldRating != 2) {
            adjustDifficultyCount(oldRating, 2);
            test_[index]->setDifficultyRating(2);
            qDebug() << "Rating set to Easy for question" << index;
            emit questionsUpdated();
        }
    }
}

/**
 * @brief Slot to set the current question's difficulty rating to Moderate.
 */

void Card::setModerate() {
    if (index < test_.size()) {
        int oldRating = test_[index]->difficultyRating();
        if(oldRating != 3) {
            adjustDifficultyCount(oldRating, 3);
            test_[index]->setDifficultyRating(3);
            qDebug() << "Rating set to Moderate for question" << index;
            emit questionsUpdated();
        }
    }
}

/**
 * @brief Slot to set the current question's difficulty rating to Difficult.
 */

void Card::setDifficult() {
    if (index < test_.size()) {
        int oldRating = test_[index]->difficultyRating();
        if(oldRating != 4) {
            adjustDifficultyCount(oldRating, 4);
            test_[index]->setDifficultyRating(4);
            qDebug() << "Rating set to Difficult for question" << index;
            emit questionsUpdated();
        }
    }
}

/**
 * @brief Slot to set the current question's difficulty rating to Very Difficult.
 */

void Card::setVeryDifficult() {
    if (index < test_.size()) {
        int oldRating = test_[index]->difficultyRating();
        if(oldRating != 5) {
            adjustDifficultyCount(oldRating, 5);
            test_[index]->setDifficultyRating(5);
            qDebug() << "Rating set to Very Difficult for question" << index;
            emit questionsUpdated();
        }
    }
}

/**
 * @brief Adjusts the count of questions for each difficulty rating.
 *
 * This method is called internally to update the count of questions based on their difficulty rating.
 *
 * @param oldRating The old difficulty rating of the question.
 * @param newRating The new difficulty rating to be set for the question.
 */


void Card::adjustDifficultyCount(int oldRating, int newRating) {
    // Decrease count for the old rating
    switch(oldRating) {
    case 1: if (veryEasyCount > 0) veryEasyCount--; break;
    case 2: if (easyCount > 0) easyCount--; break;
    case 3: if (moderateCount > 0) moderateCount--; break;
    case 4: if (difficultCount > 0) difficultCount--; break;
    case 5: if (veryDifficultCount > 0) veryDifficultCount--; break;
    }

    // Increase count for the new rating
    switch(newRating) {
    case 1: veryEasyCount++; break;
    case 2: easyCount++; break;
    case 3: moderateCount++; break;
    case 4: difficultCount++; break;
    case 5: veryDifficultCount++; break;
    }
}

/**
 * @brief Resets the quiz session to its initial state.
 *
 * This method is called to reset the quiz session, including clearing previous answers, resetting question counters,
 * and preparing the interface for a new session or test.
 */

void Card::resetSession() {
    veryEasyCount = 0;
    easyCount = 0;
    moderateCount = 0;
    difficultCount = 0;
    veryDifficultCount = 0;
    index = 0; // Resetting the question index to start from the first question
    showingSummary = false; // Ready to start a new session without showing the summary initially

    // Optionally, clear and reset other state as needed, for example:
    ui->qlabel->clear();
    ui->alabel->clear();
    if (!test_.empty()) {
        test_[index]->showQuestion(ui->qlabel); // Show the first question of the new session
    }

    // You might also need to re-enable any buttons that were disabled at the end of the previous session
    ui->show_button->setEnabled(true);
    ui->VE->setEnabled(true);
    ui->E->setEnabled(true);
    ui->M->setEnabled(true);
    ui->D->setEnabled(true);
    ui->VD->setEnabled(true);
    ui->next_button->setEnabled(true);
    ui->back_button->setEnabled(true);
}


