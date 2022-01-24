package me.kar.connectfour;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static void askForWins(ArrayList<String> board, String symbol) throws IOException {
        System.out.println("would you like to check for each symbols wins?(y/n)");
        if (new Scanner(System.in).nextLine().toLowerCase().startsWith("y")) {
            System.out.println("X wins: " + Files.readString(Path.of("xWins.txt")));
            System.out.println("O wins: " + Files.readString(Path.of("oWins.txt")));
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
                return setPos(board, symbol);
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
    public static boolean checkWin(ArrayList<String> board, String symbol) {
        //check horizontal win
        for (int i = 0; i < 6; i++) {
            for (int n = 0; n < 4; n++) {
                if (board.get(n + 7 * i).equals(board.get(n + 1 + 7 * i)) &&
                        board.get(n + 1 + 7 * i).equals(board.get(n + 2 + 7 * i)) &&
                        board.get(n + 2 + 7 * i).equals(board.get(n + 3 + 7 * i)) &&
                        !board.get(n + 7 * i).equals("-")) return false;
            }
        }
        //check vertical win
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

    public static void main(String[] args) throws IOException {
        String symbol = "X";

        File xWins = new File("xWins.txt");
        File oWins = new File("oWins.txt");

        if (!xWins.exists()) {
            Files.writeString(Path.of("xWins.txt"), "0");
        }
        if(!oWins.exists()) {
            Files.writeString(Path.of("oWins.txt"), "0");
        }

        int xWinNum = Integer.parseInt(Files.readString(Path.of("xWins.txt")));
        int oWinNum = Integer.parseInt(Files.readString(Path.of("oWins.txt")));;

        //create the array which contains the elements for the board
        ArrayList<String> board = new ArrayList<>();
        for (int i = 0; i < 43; i++) {
            board.add("-");
        }
        //runs while game hasn't ended
        askForWins(board, symbol);

        drawBoard(board);
        while (checkWin(board, symbol)) {

            board.set(setPos(board, symbol), symbol);
            drawBoard(board);
            checkWin(board, symbol);

            //switch symbol
            if (symbol.equals("X") && checkWin(board, symbol)) symbol = "O";
            else if (checkWin(board, symbol)) symbol = "X";
        }

        if (symbol.equals("X")) {
            xWinNum++;
            Files.writeString(Path.of("xWins.txt"), String.valueOf(xWinNum));
        }
        else {
            oWinNum++;
            Files.writeString(Path.of("oWins.txt"), String.valueOf(oWinNum));
        }

        System.out.println(symbol + " Wins!");
    }
}