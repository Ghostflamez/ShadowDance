# Project 1: ShadowDance

This project is a rhythm-based game where players need to hit arrows corresponding to the rhythm of the music. The goal is to achieve a high score by accurately hitting the arrows in time with the music.

## Features

- Display a title and game instructions on the screen.
- Draw lanes on the screen.
- Read the world file and draw notes on the screen.
- Implement note logic:
    - Normal notes:
        - Move the notes according to the rhythm.
        - Score points when the corresponding arrow key is pressed.
        - Display the score on the screen.
    - Hold notes:
        - Move the notes according to the rhythm.
        - Score points when the corresponding arrow key is pressed and held.
        - Display the score on the screen.
- Implement win condition and display a victory message on the screen.
- Implement loss condition and display a failure message on the screen.
- Allow the player to pause the game by pressing the Tab key.

## World File

The world file is a comma-separated values (CSV) file that contains information about the lanes and notes in the game. Each line in the file has one of the following two formats:

1. Lane, type of lane, x-coordinate
2. Type of lane, type of note, frame number

The type of lane corresponds to the arrow keys (Left, Right, Up, Down).

The frame number indicates when the note appears on the screen.

For this project, you can assume that there are always four lanes and at least one note in each direction.

## Implementation Checklist

To help you get started, here is a checklist of the game features, suggested in the following order:

1. Draw the title and game instructions on the screen.
2. Draw the lanes on the screen.
3. Read the world file and draw the notes on the screen.
4. Implement note logic for normal notes:
    - Move the notes according to the rhythm.
    - Score points when the corresponding arrow key is pressed.
    - Display the score on the screen.
5. Implement note logic for hold notes:
    - Move the notes according to the rhythm.
    - Score points when the corresponding arrow key is pressed and held.
    - Display the score on the screen.
6. Implement win condition and display a victory message on the screen.
7. Implement loss condition and display a failure message on the screen.

## Usage

To run the game, execute the `ShadowDance` class, which contains the main method.

## Dependencies

This project requires the Bagel library to handle graphics and user input. The necessary dependencies are included in the project's `pom.xml` file.

## Resources

The `res/` folder contains all the graphics and fonts required for the game.
