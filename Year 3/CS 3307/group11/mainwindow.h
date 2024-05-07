/**
 * @file mainwindow.h
 * @brief Declaration of the MainWindow class.
 * @authors Ashna Mittal, Manpreet Saini, Parth Bhandari, Sara Mehravar
 *
 * The MainWindow class extends QMainWindow and manages the main interface of the application.
 * It handles user interactions for creating, editing, and managing text questions within decks.
 * This class also provides functionalities for loading and saving questions to a JSON file.
 */

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "question.h"
#include "card.h"
#include <memory>
#include <fstream>
#include <cereal/archives/json.hpp>

namespace Ui {
class MainWindow;
}
/**
 * @class MainWindow
 * @brief Main window class for the application.
 *
 * Manages the application's main window and user interactions. It is responsible for
 * displaying questions, handling user inputs, and managing the test lifecycle.
 */
class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    QString _currDeck;
    explicit MainWindow(const QString &currDeck, QWidget *parent = 0); ///< Constructor
    const std::vector<std::shared_ptr<Question> > &test() const; ///< Returns the current test question
    void setCurrDeck(const QString& deckName); ///< Sets the current deck name.
    void on_take_test_button_clicked(); ///< Initiates the test-taking process.
    ~MainWindow();


public slots:
    void saveUpdatedQuestions(); ///< Slot to save questions to JSON

private slots:
    void on_add_tquestion_button_clicked(); ///< Addes a new text question.
    void on_remove_tquestion_button_clicked(); ///< Removes a selected text question.
    void on_update_tquestion_button_clicked(); ///< Updates the selected text question
    void on_actionNew_triggered(); ///< Resets the form for a new question entry
    void on_actionSave_triggered(); ///< saves current question to a file.
    void on_actionOpen_triggered(); ///< Opens the start test window
    bool readQuestionsFromJson(const std::string& filename); ///< Reads questions from Json file
    bool writeQuestionsToJson(const std::string& filename); ///< Writes quesions inside a Json file
    void startTest(); ///< Starts the test in the current deck name with the current questions and answers available.
    void updateButtonState(); ///< Updates the UI
    void clearFields(); ///<clears the input fields
    void printVector(); ///< Prints the current list of questions for debugging.

private:
    Ui::MainWindow *ui; ///< UI components
    std::string currDeckFile; ///< current deck name
    std::vector<std::shared_ptr<Question>> _test; ///< List of questions in the current test
    Card* cardInstance = nullptr;
    std::ostringstream jsonBuffer; ///< Buffer for Json serialization
    std::ofstream jsonFile;  ///< Member variable for writing
    std::ifstream jsonFileForReading;  ///< Member variable for reading

protected:
    void closeEvent(QCloseEvent *event) override; ///< Handles when window gets closed


};

#endif // MAINWINDOW_H
