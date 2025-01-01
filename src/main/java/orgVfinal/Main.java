package orgVfinal;

import orgVfinal.Exception.ColumnFullException;
import orgVfinal.Exception.EndGameException;
import orgVfinal.Game.AI;
import orgVfinal.Game.Board;
import orgVfinal.Game.Player;

import java.util.Scanner;

public class Main {

    public static Board board = new Board();
    public static Player player1;
    public static Player player2;
    public static int difficulty;

    public static void main(String[] args) {
        player1 = choosePlayer(1);
        player2 = choosePlayer(2);
        setAI(player1, player2);


        boolean finished = false;
        while(!finished){
            finished = turno(player1);
            if(finished){
                break;
            }
            finished = turno(player2);
            if(player1 instanceof AI && player2 instanceof AI) {
                board.printBoard();
            }
        }
    }

    private static void setAI(Player player1, Player player2) {
        if(player1 instanceof AI){
            ((AI) player1).setOpponent(player2);
        }

        if(player2 instanceof AI){
            ((AI) player2).setOpponent(player1);
        }
    }

    private static Player choosePlayer(int color) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose player " + color + " type: 1 for human, 2 for AI");
        byte playerType = scanner.nextByte();

        switch (playerType) {
            case 1:
                return new Player((byte) color);
            case 2:
                return new AI((byte) color);
            default:
                System.out.println("Invalid option, try again");
                return choosePlayer(color);
        }
    }

    public static boolean turno(Player player){
        boolean finished = false;
        int col;
        try{
            if(player instanceof AI) {
                col = ((AI) player).makeMove(board);
            }else{
                board.printBoard();
                System.out.println("Player " + player.getColor() + " turn, choose a column to add a piece");
                Scanner scanner = new Scanner(System.in);
                col = scanner.nextInt()-1;
            }
            finished = board.addPiece(player, col);
            if(finished){
                System.out.println("Player " + player.getColor() + " wins!");
                board.printBoard();
            }
        }catch (ColumnFullException e) {//This exception can be thrown ONLY by human players
            if(player instanceof AI){
                throw new IllegalArgumentException("AI tried to add a piece to a full column, this should not happen");
            }else{
                System.out.println("Column is full, try again");
                turno(player);
            }
        }catch (EndGameException e){
            System.out.println("Draw");
            finished = true;
        }
        return finished;
    }
}
