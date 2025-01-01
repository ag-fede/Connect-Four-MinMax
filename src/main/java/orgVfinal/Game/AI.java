package orgVfinal.Game;
import orgVfinal.Exception.ColumnFullException;
import orgVfinal.Exception.EndGameException;

import java.util.HashMap;
import java.util.Map;

public class AI extends Player {
    Player opponent;
    private final int depth = 6;
    public AI(byte color) {
        super(color);
    }

    /**
     * Set the opponent of this AI
     * @param opponent the other player
     */
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     * Make a move based on the current board
     * @param board the current board
     * @return the column where the AI wants to play
     * @throws IllegalArgumentException if the column is full or invalid
     * @throws EndGameException if the game is finished
     */
    public int makeMove(Board board) throws IllegalArgumentException, EndGameException {
        Map<Integer, Integer> scores = new HashMap<>();
        for(int col : board.getNotFullColumns()) {
            Board copy = board.copyBoard();
            try {
                boolean win = copy.addPiece(this, col);
                if(win){
                    scores.put(col, Integer.MAX_VALUE);
                }else{
                    scores.put(col, min(copy, depth, Integer.MIN_VALUE, Integer.MAX_VALUE));
                }
            } catch (ColumnFullException e) {
                throw new IllegalArgumentException("Column is full, IA should not try to play here");
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid column, IA should not try to play here");
            }
        }

        int choice = max(scores, board.getColumns()).getKey();
        System.out.println("Player " + this.getColor() + " choice: " + (choice+1));
        return choice;
    }

    /**
     * Simulate the opponent's move
     * @param board the board to simulate the move
     * @param depth the current depth of the simulation
     * @param alpha the alpha value
     * @param beta the beta value
     * @return the score of the move based on the future possible moves
     */
    private int min(Board board, int depth, int alpha, int beta) {
        if (depth == 0) {
            return 0;
        }
        int count = Integer.MAX_VALUE;
        for(int col : board.getNotFullColumns()) {
            Board copy = board.copyBoard();
            try {
                boolean win = copy.addPiece(opponent, col);
                if (win) {
                    return -depth;
                } else {
                    count = Math.min(max(copy, depth - 1, alpha, beta), count);
                    beta = Math.min(beta, count);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }catch (EndGameException e){
                //Draw
                return 0;
            }
        }
        return count;
    }

    /**
     * Simulate the AI's future move
     * @param board the board to simulate the move
     * @param depth the current depth of the simulation
     * @param alpha the alpha value
     * @param beta the beta value
     * @return the score of the move based on the future possible moves
     */
    private int max(Board board, int depth, int alpha, int beta) {
        if (depth == 0) {
            return 0;
        }
        int count = Integer.MIN_VALUE;
        for(int col : board.getNotFullColumns()) {
            Board copy = board.copyBoard();
            try {
                boolean win = copy.addPiece(this, col);
                if (win) {
                    return depth;
                } else {
                    count = Math.max(min(copy, depth - 1, alpha, beta), count);
                    alpha = Math.max(alpha, count);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }catch (EndGameException e){
                return 0;
            }
        }
        return count;
    }

    /**
     * Get the column with the highest score
     * @param scores the scores of the possible moves
     * @param columns the total number of columns
     * @return the column with the highest score and the closest to the center
     */
    private Map.Entry<Integer, Integer> max(Map<Integer, Integer> scores, int columns) {
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            int value = entry.getValue();
            if (maxEntry == null || value >= maxEntry.getValue()){
                if(maxEntry == null || value > maxEntry.getValue() ||closestTo(columns/2, entry.getKey(), maxEntry.getKey()) == entry.getKey()){
                    maxEntry = entry;
                }
            }
        }
        return maxEntry;
    }

    /**
     * Get the closest column to a number
     * @param n the number to compare
     * @param i the first column
     * @param j the second column
     * @return the closest column (i or j) to the number (n)
     */
    private int closestTo(int n, int i, int j){
        if(Math.abs(n-j) < Math.abs(n-i)) {
            return j;
        }else{
            return i;
        }
    }
}
