/**
 * @file mainwindow.cpp
 * @brief Implementation of the MainWindow class for the application's main interface.
 * @authors Ashna Mittal, Manpreet Saini, Sara Mehravar, Parth Bhandari
 *
 * This file includes the implementation of the MainWindow class, which manages the main interface
 * of the application, including displaying, adding, removing, and updating text questions within a deck.
 * It also handles saving and loading questions to and from a JSON file.
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QMessageBox>
#include <string>
#include "textquestion.h"
#include "deckmainwindow.h"
#include <vector>
#include "card.h"
#include <iostream>
#include <fstream>
#include <QFileDialog>
#include <QFile>
#include <QDebug>
#include <cereal/archives/json.hpp>
#include <cereal/types/polymorphic.hpp>
#include <cereal/types/vector.hpp>
#include <QList>
#include <algorithm>
#include <random>
#include <iterator>
#include <iostream>
#include <algorithm> // For std::shuffle   // For std::default_random_engine
#include <chrono>

/**
 * @brief Constructs the MainWindow with a specified deck and parent widget.
 *
 * Initializes the main window, sets up the UI components, and loads questions from
 * the specified deck file.
 *
 * @param currDeck Name of the current deck to be loaded and displayed.
 * @param parent Parent widget, typically nullptr for the main application window.
 */
MainWindow::MainWindow(const QString &currDeck, QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{

    ///< Setup UI and initial state
    setCurrDeck(currDeck);

    ui->setupUi(this);
    ui->remove_tquestion_button->setEnabled(false);
    ui->currDeckLabel->setText(currDeck);
    ui->update_tquestion_button->setEnabled(false);

    currDeckFile = _currDeck.toStdString() + ".json";

    if (currDeck != "") {
        std::cout << "Deck Name: " << _currDeck.toStdString() << std::endl;
    }
    else{
        std::cout << "No deck name assigned\n";
    }

    readQuestionsFromJson(currDeckFile);

    // Populate the list widget with loaded questions
    for (const auto& question : _test) {
        ui->listWidget->addItem(QString::fromStdString(question->label()));
    }

    connect(ui->take_test_button, &QPushButton::clicked, this, &MainWindow::on_take_test_button_clicked);


    updateButtonState();

}


/**
 * @brief Destructor for MainWindow.
 *
 * Cleans up allocated resources, ensuring no memory leaks.
 */

MainWindow::~MainWindow()
{
    if (jsonFile.is_open()) {
        jsonFile.close();
    }

    delete ui;
}

/**
 * @brief Sets the current deck name.
 *
 * @param currDeck The new name of the current deck.
 */
void MainWindow::setCurrDeck(const QString& currDeck) {
    _currDeck = currDeck;
};

/**
 * @brief Returns a constant reference to the list of questions in the test.
 *
 * @return Constant reference to the vector of shared_ptr to Question objects.
 */
const std::vector<std::shared_ptr<Question>> & MainWindow::test() const {
    return _test;
}

/**
 * @brief Slot triggered by the "Add Question" button click.
 *
 * Creates a new TextQuestion instance with the input from the UI, adds it to the current deck,
 * and updates the UI accordingly. This includes adding the question to the internal list of questions,
 * displaying it in the list widget, and clearing the input fields for further input.
 */
void MainWindow::on_add_tquestion_button_clicked()
{
    ///< Create a new TextQuestion with inputs from the UI
    TextQuestion *q = new TextQuestion(ui->tquestion_text->text().toStdString(), ui->tanswer_text->toPlainText().toStdString(), _currDeck.toStdString());
    ///< Ensure the current deck is set (redundant if already set)
    setCurrDeck(_currDeck);
    ///<explicitly set the deck name for question
    q->setDeckName(_currDeck.toStdString());
    ///< Wrap the raw pointer in a shared-ptr and add to the list of questions.
    std::shared_ptr<Question> p(q);
    _test.push_back(p);
    ///< Update the UI with the new question
    ui->listWidget->addItem(QString::fromStdString(q->label()));
    ui->listWidget->setCurrentRow(_test.size()-1); ///< Highlight the newly added question
    ///< Refresh the UI with the reflect changes and prepare for next input
    updateButtonState();
    clearFields(); ///< Clear the input fields for question and answer
    printVector(); ///< Optionally print the current list of questions for debbuging
}

/**
 * @brief Removes the selected question from the current deck.
 *
 * This slot is triggered when the remove button is clicked. It removes the selected question
 * from the list of questions in the current deck, updates the UI to reflect the change, and clears any
 * input fields related to the question.
 */
void MainWindow::on_remove_tquestion_button_clicked()
{
    int index = ui->listWidget->currentRow();
    qDeleteAll(ui->listWidget->selectedItems());
    ui->listWidget->setCurrentItem(0);
    _test.erase(_test.begin()+index);
    updateButtonState();
    clearFields();
    printVector();
}

/**
 * @brief Updates the selected text question in the current deck.
 *
 * This slot is triggered when the update text question button is clicked. It updates the selected question's
 * text and answer with the user's input, refreshes the UI to show the updated question, and clears the input fields.
 */
void MainWindow::on_update_tquestion_button_clicked()
{
    int index = ui->listWidget->currentRow();
    _test[index]->setQuestion(ui->tquestion_text->text().toStdString());
    _test[index]->setAnswer(ui->tanswer_text->toPlainText().toStdString());
    ui->listWidget->item(index)->setText(QString::fromStdString(_test[index]->label()));
    updateButtonState();
    clearFields();
    printVector();
}

/**
 * @brief Initiates the test-taking process.
 *
 * This slot is triggered when the take test button is clicked. It starts the process for the user to take a test,
 * which involves selecting and ordering the questions based on difficulty, and displaying them one at a time for
 * the user to answer.
 */

void MainWindow::on_take_test_button_clicked()
{
    startTest();
}

/**
 * @brief Starts the test with the current set of questions.
 *
 * Prepares and displays the questions for the user to take the test. The questions are sorted by their difficulty
 * rating before the test starts. The method also handles the creation and display of the test interface.
 */
void MainWindow::startTest() {
    std::vector<std::shared_ptr<Question>> weightedQuestions;

    ///< Generate weighted list of questions based on their difficulty ratings
    for (auto& question : _test) {
        int weight = std::max(question->difficultyRating(), 1); ///< Ensure every question appears at least once
        for (int i = 0; i < weight; ++i) {
            weightedQuestions.push_back(question);
        }
    }

    ///< Shuffle the weightedQuestions list to randomize their order
    std::random_device rd;
    std::mt19937 g(rd());
    std::shuffle(weightedQuestions.begin(), weightedQuestions.end(), g);

    ///< Pass the weighted list to the Card dialog
    Card* card = new Card(this, weightedQuestions);
    connect(card, &Card::questionsUpdated, this, &MainWindow::saveUpdatedQuestions);
    card->exec(); ///< Display the dialog modally and wait for it to finish
    delete card; ///< Clean up the card dialog after it closes

    updateButtonState(); ///< Refresh the UI based on the current state
}

/**
 * @brief Creates a new set of questions.
 *
 * Triggered by the New action in the UI, this method clears the current question list, input fields,
 * and any other relevant state to allow the user to start creating a new deck from scratch.
 */
void MainWindow::on_actionNew_triggered()
{
    ui->listWidget->clear();
    ui->tquestion_text->clear();
    ui->tanswer_text->clear();
}

/**
 * @brief Saves the current set of questions to a JSON file.
 *
 * Opens a file dialog for the user to choose the save location and filename, then serializes the current
 * set of questions into JSON format and saves them to the selected file.
 */

void MainWindow::on_actionSave_triggered()
{
    QString fileName = QFileDialog::getSaveFileName(this, tr("Save Questions"), "", tr("JSON Files (*.json)"));
    if (!fileName.isEmpty()) {
        writeQuestionsToJson(fileName.toStdString());
    }
}

/**
 * @brief Loads questions from a JSON file.
 *
 * Opens a file dialog for the user to choose a JSON file containing questions. It then deserializes the
 * questions from the file and loads them into the application, updating the UI to reflect the loaded questions.
 */
void MainWindow::on_actionOpen_triggered()
{
    QString fileName = QFileDialog::getOpenFileName(this, tr("Open Questions"), "", tr("JSON Files (*.json)"));
    if (!fileName.isEmpty()) {
        readQuestionsFromJson(fileName.toStdString());
        ui->listWidget->clear();
        for (const auto& question : _test) {
            ui->listWidget->addItem(QString::fromStdString(question->label()));
        }
        updateButtonState();
    }
}

/**
 * @brief Reads questions from a specified JSON file and loads them into the application.
 *
 * @param filename The path to the JSON file containing the questions to be loaded.
 * @return Returns true if the questions were successfully read; otherwise, false.
 */

bool MainWindow::readQuestionsFromJson(const std::string& filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        return false;
    }

    try {
        ///< Clear the existing content in _test vector
        _test.clear();

        ///< Deserialize from file
        cereal::JSONInputArchive archive(file);
        archive(cereal::make_nvp("questions", _test));

        return true;
    } catch (const std::exception& e) {
        QMessageBox::critical(this, "Error", "Failed to read from JSON file: " + QString::fromStdString(e.what()));
        return false;
    }
}

/**
 * @brief Writes the current list of questions to a JSON file.
 *
 * Attempts to open the specified file for writing and serializes the list of questions (_test)
 * to JSON format using Cereal. Displays an error message if the file cannot be opened or written to.
 *
 * @param filename Path to the file where questions should be saved.
 * @return True if writing was successful, False otherwise.
 */
bool MainWindow::writeQuestionsToJson(const std::string& filename) {
    try {
        std::ofstream file(filename);
        if (!file.is_open()) {
            QMessageBox::critical(this, "Error", "Failed to open file for writing.");
            return false;
        }

        ///< Serialize to file
        cereal::JSONOutputArchive archive(file);
        archive(cereal::make_nvp("questions", _test));

        return true;
    } catch (const std::exception& e) {
        QMessageBox::critical(this, "Error", "Failed to write to JSON file: " + QString::fromStdString(e.what()));
        return false;
    }
}


/**
 * @brief Updates the state of UI components based on the current state of questions.
 *
 * Enables or disables UI components (e.g., buttons) based on whether there are questions
 * selected or available. It also saves the updated list of questions to JSON.
 */

void MainWindow::updateButtonState()
{
    writeQuestionsToJson(currDeckFile);

    connect(ui->listWidget, &QListWidget::currentRowChanged, [=](int x) {
        if(x >= 0) {    ///< Check if item selected has valid index
            _test[x]->updateForm(ui);
            ui->remove_tquestion_button->setEnabled(true);
            ui->update_tquestion_button->setEnabled(true);
        }
        else {  ///< If not, then disable remove button
            ui->remove_tquestion_button->setEnabled(false);
            ui->update_tquestion_button->setEnabled(false);
        }
    });

    ui->listWidget->setFocusPolicy(Qt::NoFocus);
    ui->take_test_button->setEnabled(!_test.empty());
    clearFields();
    printVector();
}

/**
 * @brief Clears the input fields for question and answer text.
 *
 * This method resets the text fields used for entering new questions and their answers,
 * preparing them for the next input.
 */
void MainWindow::clearFields()
{
    ui->tquestion_text->clear();
    ui->tanswer_text->clear();
}

/**
 * @brief Prints details of all questions in the current test to the console.
 *
 * Iterates through the collection of questions and prints each question's details,
 * including the question content, answer, associated deck name, and difficulty rating.
 * This function is primarily useful for debugging purposes.
 */

void MainWindow::printVector() {
    const std::vector<std::shared_ptr<Question>> & questions = _test;

    ///< Loop through the vector and print information about each question
    for (const auto &questionPtr : questions) {
        std::shared_ptr<Question> question = questionPtr;


        ///< Print information about the question
        std::cout << "Question: " << question->question() << std::endl;
        std::cout << "Answer: " << question->answer() << std::endl;
        std::cout << "Deck: " << question->deckName() << std::endl;
        std::cout << "Difficulty Rating: " << question->difficultyRating() << std::endl;
        ///< Add additional details specific to the TextQuestion class if needed
        if (auto textQuestion = std::dynamic_pointer_cast<TextQuestion>(question)) {
            ///< Additional details for TextQuestion

        }

        ///< Add a separator between questions for clarity
        std::cout << "----------------------" << std::endl;
    }
}

void MainWindow::closeEvent(QCloseEvent *event)
{

    QMainWindow::closeEvent(event);
}


void MainWindow::saveUpdatedQuestions() {
    writeQuestionsToJson(currDeckFile); ///< Use the existing function to save
}
