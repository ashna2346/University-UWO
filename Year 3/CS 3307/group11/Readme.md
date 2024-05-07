# Intellecto App

Welcome to Intellecto, your ultimate companion on the journey to mastering new topics through efficient and personalized study sessions. Intellecto utilizes the power of spaced repetition, a scientifically proven learning method, to help you memorize more information in less time. With all required features completed, including the spaced repetition algorithm and data storage, our application is now fully equipped to provide a comprehensive and enriching learning experience.

## Features

- Spaced Repetition Algorithm: This algorithm schedules reviews based on the user's memory strength of each item, optimizing learning efficiency.
- Flashcards: Users can create flashcards with a front (question or prompt) and a back (answer or explanation), offering a flexible study tool adaptable to any subject. Users can edit or remove flashcards to keep their study material relevant and up-to-date.
- Deck Organization:  Users can create multiple decks to categorize their flashcards by topic, subject, or any preferred system, with the option to delete decks that are no longer in use.
- Review Session: Users can test their knowledge by reviewing flashcards within a specific deck. This feature allows them to gauge their understanding and readiness on a particular subject.
- Data Storage: A robust system(JSON) is in place for storing user data, including flashcards, difficulty rating, and deck name, ensuring a seamless and personalized learning experience. The JSON files can be found in the build folder.
- Graphical User Interface: Our application features a user-friendly interface that simplifies navigation, making it easy to create, edit, and organize flashcards with minimal effort.
- Advanced Statistics and Reporting: This feature provides detailed analytics on user performance and learning progress at the end of each session. It offers valuable insights into their learning journey.

## Requirements

To ensure the best experience with Intellecto, the following are required:
- Qt 5.12 or higher
- C++11 compatible compiler

# Structure

The app has its own folder (group11) with the following files:
- header(.h) and class(.cpp) files
- UI(.ui) files
- A Makefile file
- .pro files
- .qrc file
- Images
- A Cereal Folder: Serialization library for advanced data handling

# Execution

- The project is executed in QTcreator. The files are user-friendly, with clear comments and instructions throughout.
- type in 'open Flashcards.app' in the command line tool or terminal to run the project.
- You can also run the project by clicking on the Flashcards.app in the 'build-FlashCards' folder.

## Usage

- Click the '+' icon to create a new deck, enter the deck's name in the dialog, and click Ok, or Cancel to stop.
- After creating a deck, the Deck editor window appears. Fill in the Question and Answer fields and click Add. To edit or delete an existing flashcard, select it, make changes, and click Update or Delete, respectively.
- To start a review session, click Start Test. The session displays flashcards in a random shuffled order. Use the Show Answer button to reveal answers and navigate with the back and next buttons.
- To rate a flashcard and schedule it for the next review session, assign one of the difficulty ratings to a flashcard. Flashcards rated 'very easy' would appear once in a shuffled order for the next session, 'easy' rated ones would appear twice, 'moderate' rated ones would appear three times and so on, ensuring that the user has a chance to revisit the most difficult questions more often.
- At the end of each review session, the user would also get a Session summary report telling them how they did in the Test. If a user mostly found questions to be easy to moderate, then the user is well prepared. However, if the user is finding questions to be difficult, then the user needs more practice.
- Decks can be deleted using the trash (third) button or renamed using the pencil (first) button.
- Flashcards in a particular deck can be edited by clicking on the view (second) button to open the Deck editor window.


Thank you for choosing Intellecto. We're excited to be a part of your learning journey. Your feedback is invaluable to us as we strive to make Intellecto the best it can be.