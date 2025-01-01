package orgVfinal.Game;

import orgVfinal.Exception.ColumnFullException;
import orgVfinal.Exception.EndGameException;

import java.util.ArrayList;

public class Board {

    private final int rows = 5;
    private final int columns = 7;

    private final byte [][] board = new byte[rows][columns];
    /**
     * List of columns that are not full
     */
    private final ArrayList<Integer> notFullColumns = new ArrayList<>();

    public Board() {
        for (int i = 0; i < columns; i++) {
            notFullColumns.add(i);
        }
    }

    /**
     * Add a piece to the board
     * @param player the player that is adding the piece
     * @param column the column where the piece is added
     * @return true if the player has won the game
     * @throws EndGameException if the game is endend in a draw (the board is full)
     * @throws ColumnFullException if the column is full
     */
    public boolean addPiece(Player player, int column) throws EndGameException, ColumnFullException, IllegalArgumentException {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Invalid column");
        }
        //System.out.println("Column: " + column);
        int row = findFreeRow(column);
        board[row][column] = player.getColor();

        boolean isWin = isWin(row, column);
        if(!isWin){
            isDraw();
        }

        return isWin;
    }

    /**
     * Check if the game is a draw/finished
     * @throws EndGameException if the game is finished
     */
    private void isDraw() throws EndGameException {
        if(notFullColumns.isEmpty()){
            throw new EndGameException("Draw");
        }
    }

    /**
     * @return The list of columns that are not full
     */
    public ArrayList<Integer> getNotFullColumns() {
        return notFullColumns;
    }

    /**
     * Print the board
     */
    public void printBoard() {
        for (int i = 0; i < board[0].length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 1) {
                    System.out.print("\u001B[31m" + 0 + " \u001B[0m"); // Red
                } else if (board[i][j] == 2) {
                    System.out.print("\u001B[33m" + 0 + " \u001B[0m"); // Yellow
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Find the first free row in a column
     * @param column the column to check
     * @return the row index of the first free row
     * @throws ColumnFullException if the column is full
     */
    private int findFreeRow(int column) throws ColumnFullException {
        if(notFullColumns.contains(column)) {
            for (int i = 0; i < rows; i++) {
                if (board[i][column] == 0) {
                    if (i == rows - 1) {
                        notFullColumns.remove(Integer.valueOf(column));
                    }
                    return i;
                }
            }
        }else {
            throw new ColumnFullException("Column " + column + " is full");
        }
        throw new IllegalArgumentException("Column " + column + " is full, managing the board incorrectly");
    }

    /**
     * Check if player has won the game horizontally
     * @param row the row of last inserted the piece
     * @param col the column of last inserted the piece
     * @return the number of pieces in a row horizontally
     */
    private int horizontalWin(int row, int col) {
        // Check horizontal win
        byte piece = board[row][col];
        int count = 1;
        for (int i = col - 1; i >= 0 && board[row][i] != 0 && board[row][i] == piece; i--) count++;
        for (int i = col + 1; i < board[row].length && board[row][i] != 0 && board[row][i] == piece; i++) count++;
        return count;
    }

    /**
     * Check if player has won the game vertically
     * @param row the row of last inserted the piece
     * @param col the column of last inserted the piece
     * @return the number of pieces in a row vertically
     */
    private int verticalWin(int row, int col) {
        // Check vertical win
        byte piece = board[row][col];
        int count = 1;
        for (int i = row - 1; i >= 0 && board[i][col] != 0 && board[i][col] == piece; i--) count++;
        for (int i = row + 1; i < board.length && board[i][col] != 0 && board[i][col] == piece; i++) count++;
        return count;
    }

    /**
     * Check if player has won the game diagonally from bottom-left to top-right
     * @param row the row of last inserted the piece
     * @param col the column of last inserted the piece
     * @return the number of pieces in a row diagonally from bottom-left to top-right
     */
    private int diagonalWinBottomLeftTopRight(int row, int col) {
        // Check diagonal win (bottom-left to top-right)
        byte piece = board[row][col];
        int count = 1;
        for (int i = 1; row - i >= 0 && col - i >= 0 && board[row - i][col - i] != 0 && board[row - i][col - i] == piece; i++) count++;
        for (int i = 1; row + i < board.length && col + i < board[row].length && board[row + i][col + i] != 0 && board[row + i][col + i] == piece; i++) count++;
        return count;
    }

    /**
     * Check if player has won the game diagonally from top-left to bottom-right
     * @param row the row of last inserted the piece
     * @param col the column of last inserted the piece
     * @return the number of pieces in a row diagonally from top-left to bottom-right
     */
    private int diagonalWinTopLeftBottomRight(int row, int col) {
        // Check diagonal win (top-left to bottom-right)
        byte piece = board[row][col];
        int count = 1;
        for (int i = 1; row - i >= 0 && col + i < board[row].length && board[row - i][col + i] != 0 && board[row - i][col + i] == piece; i++) count++;
        for (int i = 1; row + i < board.length && col - i >= 0 && board[row + i][col - i] != 0 && board[row + i][col - i] == piece; i++) count++;
        return count;
    }

    /**
     * Check if the player has won the game
     * @param row the row of last inserted the piece
     * @param col the column of last inserted the piece
     * @return true if the player has won the game
     */
    private boolean isWin(int row, int col) {
        int winningCondition = 4;

        int count = horizontalWin(row, col);
        if (count >= winningCondition) return true;

        count = verticalWin(row, col);
        if (count >= winningCondition) return true;

        count = diagonalWinBottomLeftTopRight(row, col);
        if (count >= winningCondition) return true;

        count = diagonalWinTopLeftBottomRight(row, col);
        return count >= winningCondition;
    }

    /**
     * Copy the board
     * @return a copy of the board
     */
    public Board copyBoard() {
        Board copy = new Board();
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, copy.board[i], 0, columns);
        }
        copy.notFullColumns.clear();
        copy.notFullColumns.addAll(notFullColumns);
        return copy;
    }

    /**
     * @return the number of rows in the board
     */
    public int getColumns() {
        return columns;
    }
}
