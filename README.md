# Connect-Four-MinMax
Project Description
This project implements a basic, functional version of Connect 4. After launching the program, you will be prompted to choose the type of each player (human or AI):

Enter 1 to select a human player.
Enter 2 to select an AI player.
Repeat this process for both players.
Gameplay Modes

Human Player: The current game board is displayed in the console. The player selects a column to drop their token (from 1 to 7 in the default version).
AI Player: The AI plays automatically, requiring no user input.
Configurable Parameters

In orgVfinal/Game/Board.java, you can modify the instance variables rows and column to change the size of the game board. Increasing the number of columns will also increase the computational time required by the AI to make a move.
In orgVfinal/Game/AI.java, you can adjust the depth variable, which defines the search depth of the AI. A higher value improves the AI's strategy but also increases the computational time for each move.
AI: MinMax Algorithm
The AI is powered by the MinMax algorithm, a standard approach for strategic games. MinMax is a multi-agent algorithm that simulates decisions from two opposing players:

Min represents the opponent, aiming to minimize Max’s chances of winning.
Max represents the AI, always choosing the most advantageous move.
The AI evaluates all possible outcomes up to the specified depth, simulating the opponent’s choices to maximize its own chances of success.
