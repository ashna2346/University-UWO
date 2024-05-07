# Minesweeper Game

This project is a Qt-based implementation of the classic Minesweeper game in C++. It features a customizable grid size and mine count, providing a challenging puzzle for users to navigate through. The goal of the game is to clear a rectangular board containing hidden "mines" without detonating any of them, with help from clues about the number of neighboring mines in each field.

## Features

- Customizable game settings including grid size (16x30) and total mines (99).
- First-click safety, ensuring the first tile clicked is never a mine.
- Victory and defeat detection with options to restart the game or exit.
- Reveals adjacent tiles automatically when a tile with no adjacent mines is revealed.
- Right-click to flag tiles suspected of hiding mines, with an additional marking state to denote uncertainty.
- Scalable UI that adapts to different screen resolutions and sizes.

## Requirements

To run this game, you will need:
- Qt 5.12 or higher
- C++11 compatible compiler

# Structure
The game has its own folder (QTMineSweeper) with the following files:
- A few header(.h) and class(.cpp) files.
- A few output output files
- A .app file
- A .pro file.
- A .qrc file.
- An Images Folder: Contains the images needed to build the project.

# Execution
- The project is executed in QTcreator. The files are user-friendly, with clear comments and instructions throughout.
- type in 'open QTMinesweepera.app' in the command line tool or terminal to run the project.
- You can also run the project by clicking on the QTMinesweeper.app 

## Usage
- Left-click on a tile to reveal it.
- Right-click on a tile to cycle through flags and question marks.
- Try to clear all non-mine tiles to win the game.
- Clicking on a mine will trigger a defeat, but you can restart the game or exit from the dialog prompt.

