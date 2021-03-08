package com.vitoarts.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EightQueens {
    public static int boardSize = 8;
    public static boolean random = true;
    public static boolean draw = true;
    public static int counter = 0;

    public static void main(String[] args) {
        boardSize = Options();
        if (random) {
            RandomPlaceQueens();
            System.out.print("The amount of attempted solutions is: " + counter);
        } else {
            PlaceQueens(0, 0);
            System.out.print("The amount of times a placement was rejected: " + counter);
        }
    }

    public static int Options() {
        System.out.println("Random(r)/Algorithm(a) solution?");
        Scanner input = new Scanner(System.in);
        String answer = input.next();
        if (answer.equals("a")) {
            random = false;
        }

        System.out.println("Size of board?");
        return input.nextInt();
    }

    public static void DrawBoard(List queenStorage, int size) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (SearchQueens(queenStorage, row, column)) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.print('\n');
        }
    }

    //My attempt at using an algorithmic solution to the eight queens problem
    //This uses a recursive utility to find a solution to the problem.
    //Backtracking algorithm
    public static void PlaceQueens(int row, int column) {
        for (int i = 0; i < boardSize; i++) {
            List<Queen> queenList = new ArrayList<>();
            if (!QueenControl(queenList, i, column)) {
                Queen queen = new Queen(i, column);
                queenList.add(queen);
                if (SolveQueens(queenList, column + 1)) {
                    return;
                }
            }
        }
    }

    public static boolean SolveQueens(List queenList, int column) {
        if (column >= boardSize) {
            if (draw){
                DrawBoard(queenList, boardSize);
            }
            return true;
        }
        for (int i = 0; i < boardSize; i++) {
            if (!QueenControl(queenList, i, column)) {
                Queen queen = new Queen(i, column);
                queenList.add(queen);
                if (SolveQueens(queenList, column + 1)) {
                    return true;
                }
                else {
                    queenList.remove(queenList.size()-1);
                }
            }
        }
        counter += 1;
        return false;
    }

    //This is where the random placement method is declared.
    public static void RandomPlaceQueens() {
        List<Queen> queenList = new ArrayList<>();
        int row = 0;
        while (row < boardSize){
            int randomColumn = (int) (Math.random() * boardSize);
            Queen queen = new Queen(row,randomColumn);
            if (!QueenControl(queenList,row,randomColumn) || queenList.size()==0){
                    queenList.add(queen);
            }
            row += 1;
            if (queenList.size()!=row) {
                counter += 1;
                queenList.clear();
                row = 0;
            }
        }
        DrawBoard(queenList, boardSize);
    }

    public static boolean QueenControl(List queenStorage, int row, int column){
        for (Object o : queenStorage) {
            Queen queen = (Queen) o;

            //row and column check
            if (queen.getRow() == row || queen.getColumn() == column) {
                return true;
            }

            //Major diagonal: column + row = z
            if (queen.getRow() + queen.getColumn() == row + column){
                return true;
            }

            //Minor diagonal: column - row = x
            if (queen.getColumn() - queen.getRow() == column - row){
                return true;
            }
        }
        return false;
}

    private static boolean SearchQueens(List queenStorage, int row, int column) {
        for (Object o : queenStorage) {
            Queen queen = (Queen) o;
            if (queen.getRow() == row && queen.getColumn() == column) {
                return true;
            }
        }
        return false;
    }
}
