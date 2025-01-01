# Connect 4 with MinMax AI  

This project implements a basic, functional version of Connect 4, featuring an AI powered by the MinMax algorithm.  

## How to Play  
After launching the program, you will be prompted to choose the type of each player:  
- Enter **1** for a human player.  
- Enter **2** for an AI player.  
Repeat this process for both players to set up the game.  

### Gameplay Modes  
- **Human Player**: The current game board is displayed in the console. The player selects a column (from 1 to 7) to drop their token.  
- **AI Player**: The AI plays automatically, with no input required from the user.  

## Configurable Parameters  
### Board Size  
In `orgVfinal/Game/Board.java`, you can modify the following instance variables:  
- `rows` – Adjusts the number of rows in the game board.  
- `column` – Adjusts the number of columns in the game board.  

> **Note**: Increasing the number of columns will increase the computational time required by the AI.  

### AI Depth  
In `orgVfinal/Game/AI.java`, you can adjust the `depth` variable, which controls the AI's search depth:  
- A higher value improves the AI's strategic capabilities.  
- However, it also increases the computational time required for the AI to make decisions.  

## AI: MinMax Algorithm  
The AI uses the MinMax algorithm, a common approach for strategic games. This algorithm simulates decisions from two opposing agents:  
- **Min** – Represents the opponent, aiming to minimize the AI's chances of success.  
- **Max** – Represents the AI, always choosing the most advantageous move.  

The AI explores all possible outcomes up to the specified `depth`, simulating the opponent's choices to ensure it selects the best possible move.  
