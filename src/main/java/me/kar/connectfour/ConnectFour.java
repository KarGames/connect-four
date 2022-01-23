package me.kar.connectfour;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
    //draws board to terminal
    public static void drawBoard(ArrayList<String> board) {
        for (int i = 1; i < board.size(); i++) {
            System.out.printf("%s | ", board.get(i));
            if (i % 7 == 0) {
                System.out.println();
            }
        }
    }

    //ask for input and sets symbol on specified location
    public static int setPos(ArrayList<String> board, String symbol) {
        int availablePos = 35;
        System.out.printf("Which column would you like to drop your %s?(1-7)", symbol);
        System.out.println();

        try {
            int pos = new Scanner(System.in).nextInt();
            if (pos < 1 || pos > 7) {
                System.out.println("Invalid number!");
                setPos(board, symbol);
            }

            while (!board.get(pos + availablePos).equals("-")) {
                availablePos -= 7;
            }
            return availablePos + pos;

        } catch (InputMismatchException e) {
            System.out.println("Not a number!");
            return setPos(board, symbol);
        }
    }

    //checks for any type of win
    public static boolean checkWin(ArrayList<String> board) {
        //check horizontal win
        for (int i = 0; i < 6; i++) {
            for (int n = 0; n < 4; n++) {
                if (board.get(n + 7 * i).equals(board.get(n + 1 + 7 * i)) &&
                        board.get(n + 1 + 7 * i).equals(board.get(n + 2 + 7 * i)) &&
                        board.get(n + 2 + 7 * i).equals(board.get(n + 3 + 7 * i)) &&
                        !board.get(n + 7 * i).equals("-")) return false;
            }
        }
        // check vertical win
        for (int i = 0; i < 7; i++) {
            for (int n = 0; n < 21; n += 7) {
                if (board.get(n + i).equals(board.get(n + 7 + i)) &&
                        board.get(n + 7 + i).equals(board.get(n + 14 + i)) &&
                        board.get(n + 14 + i).equals(board.get(n + 21 + i)) &&
                        !board.get(n + i).equals("-")) return false;
            }
        }
        //check right down diagonal win
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n < 21; n += 7) {
                if (board.get(n + i).equals(board.get(n + 8 + i)) &&
                        board.get(n + 8 + i).equals(board.get(n + 16 + i)) &&
                        board.get(n + 16 + i).equals(board.get(n + 24 + i)) &&
                        !board.get(n + i).equals("-")) return false;
            }
        }
        //check left down diagonal win
        for (int i = 3; i < 8; i++) {
            for (int n = 0; n < 21; n += 7) {
                if (board.get(n + i).equals(board.get(n + 6 + i)) &&
                        board.get(n + 6 + i).equals(board.get(n + 12 + i)) &&
                        board.get(n + 12 + i).equals(board.get(n + 18 + i)) &&
                        !board.get(n + i).equals("-")) return false;
            }
        }
        //if no win, continue game
        return true;
    }

    public static void main(String[] args) {
        String symbol = "X";
        //create the array which contains the elements for the board
        ArrayList<String> board = new ArrayList<>();
        for (int i = 0; i < 43; i++) {
            board.add("-");
        }
        //runs while game hasn't ended
        drawBoard(board);
        while (checkWin(board)) {

            board.set(setPos(board, symbol), symbol);
            drawBoard(board);
            checkWin(board);

            //switch symbol
            if (symbol.equals("X") && checkWin(board)) symbol = "O";
            else if (checkWin(board)) symbol = "X";
        }

        System.out.println(symbol + " Wins!");
    }
}