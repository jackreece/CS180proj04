/**
 * CS180 - Project 04 - Sudoku Solver
 * 
 * This is a program that both implements games of Sudoku and contains methods to solve it. The Sudoku board is
 * represented by a 2-dimensional array of integers. The value of the 2-D array at a given row/column cross-section
 * represents the number on the Sudoku board in the Sudoku game's current state.
 * 
 * The methods for solving are implementations of 2 heuristic approaches: "Naked Singles" and "Hidden Singles."
 * Solving a Sudoku game using the first method, "Naked Singles," involves, for a given empty cell, determining the
 * values it *could* contain based on the values of filled-in cells around it. If every number except for one is used
 * elsewhere in the same row, column, or box of the board as the given cell resides in, then there is only one value
 * that the given cell could possibly contain. Set the given empty cell's value accordingly.
 * 
 * For the second method, "Hidden Singles," for any given cell, like before, determine the values it *could* contain.
 * Call these values the "candidates" of the cell. Compare the candidates of a given cell to the candidates of the
 * cells in its same row, column, or box. If the given cell has a candidate that is unique among the candidates of the
 * cells it is compared to, set the empty cell's value to the unique candidate.
 * 
 * Both methods for solving, when called, execute their solution approach on every cell in the 81-cell game board
 * 
 * @author 
 * @author Jack Reece, <reece0@purdue.edu>
 * 
 * @lab 
 * @lab 802
 * 
 * @date November 2, 2014
 */
import java.util.Scanner;

public class Sudoku {
    
    private int[][] board;
    
    /**
     * Default constructor. Initialize the board field to an empty, 9x9 2-d integer array (i.e. the value of every
     * element in the array is 0)
     */
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    /**
     * Constructor that takes one argument, a 2-d integer array that will represent the board. 
     * Assign the field board to this object.
     * 
     * @arg board 2-d integer array that will represent the board
     */
    public Sudoku(int[][] board) {
        this.board = board;
    }
    
    /**
     * Create and return a copy of the board by iterating over the board and assigning each cell
     * in the copy to the corresponding value.
     * 
     * @return ans the copy of the board
     */
    public int[][] board() {
        int[][] ans = new int[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                ans[r][c] = board[r][c];
            }
        }
        return ans;
    }

    /**
     * Print a text representation of the board in its current state to standard out
     */
    public void printBoard() {

        char letter = 65;
        System.out.println("    1 2 3   4 5 6   7 8 9 ");
        // System.out.println("  +-------+-------+-------+");
        for (int row = 0; row < board.length; row++) {
            if (row % 3 == 0) {
                System.out.println("  +-------+-------+-------+");
            }
            System.out.print(letter + " |");
            letter++;
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    System.out.print("  ");
                }
                else {
                    System.out.print(" " + board[row][col]);
                }
                if (col % 3 == 2) {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        System.out.println("  +-------+-------+-------+");

    }
    
    /**
     * Returns a boolean array representing candidates  of the cell represented by the arguments passed in to row and
     * column. Arguments to row and column are integers numbered 1 - 9 (NOT 0 - 8)
     */
    public boolean[] candidates(int row, int column) {
        
        boolean[] ans = new boolean[9];
        if (row > 9 || row < 1) {
            return ans;
        }
        if (column > 9 || column < 1) {
            return ans;
        }
        // For array indexing purposes, change row and column to be numbers within range 0 - 8 instead of 1 - 9
        row--;
        column--;
        
        if (board[row][column] != 0) {
            return ans;
        }
        for (int i = 0; i < ans.length; i++) {
            ans[i] = true;
        }
        // Initialize ans to have every entry of value "true" -- indicates all numbers 1 - 9 are candidates
        
        int boxR = (row / 3) * 3; // The row which marks the corner of the box to iterate over
        int boxC = (column / 3) * 3; // The column which ""
        
        // Find candidates by iterating over the box in which the cell resides
        for (int r = boxR; r < boxR + 3; r++) {
            for (int c = boxC; c < boxC + 3; c++) {
                if (board[r][c] > 0) {
                    ans[board[r][c] - 1] = false;
                }
            }
        }
        
        // Find candidates by iterating over the row in which the cell resides
        for (int c = 0; c < board[0].length; c++) {
            if (board[row][c] > 0) {
                ans[board[row][c] - 1] = false;
            }
        }
        
        /**
         * Find candidates by iterating over the column in which the cell resides. This is done by checking the
         * cross-section of the column with every row on the board.
         */
        for (int r = 0; r < board.length; r++) {
            if (board[r][column] > 0) {
                ans[board[r][column] - 1] = false;
            }
        }
        
        return ans;
        
    }
    
    /**
     * This method attempts to solve the Sudoku board using the "Naked Singles" approach. Naked singles checks all
     * cells on the board to see if any have only one possible candidate. If any cells do, these cells are assigned
     * that value.
     * 
     * @return true if the method made any changes to the board, false otherwise
     */
    public boolean nakedSingles() {
        boolean ans = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                boolean[] candidates = candidates(i + 1, j + 1);
                int trueCount = 0;
                int trueIndex = 0;
                for (int k = 0; k < candidates.length; k++) {
                    if (candidates[k] == true) {
                        trueCount++;
                        trueIndex = k;
                    }
                }
                if (trueCount == 1) {
                    board[i][j] = trueIndex + 1;
                    ans = true;
                }
            }
        }
        return ans;
    }
    
    /**
     * This method attempts to solve the board using the "hidden singles" approach. For every cell in the board, this
     * method determines the candidates of the cell, and compares them to the candidates of the cells in its subunit,
     * i.e. its row, column, or box. If a cell has a unique candidate among the candidates of the rest of the cells in
     * its subunit, set the cell's value to that candidate.
     * 
     * @return true if any changes are made to the board, false otherwise
     */
    public boolean hiddenSingles() {
        boolean ans = false;
        
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                
                boolean[] candidates = candidates(row + 1, column + 1); // store candidates of the present cell
                
                // Find hidden singles within subunit: row
                // Use 2 for loops to avoid checking candidates array of this cell *against itself.* Only compare
                // candidates array of this cell to candidates array of *other cells* within this cell's subunit
                for (int c = 0; c < column; c++) {
                    boolean[] rowCandidates = candidates(row + 1, c + 1);
                    for (int i = 0; i < candidates.length; i++) {
                        if (candidates[i] && rowCandidates[i]) {
                            candidates[i] = false;
                        }
                    }
                }
                for (int c = column + 1; c < 9; c++) {
                    boolean[] rowCandidates = candidates(row + 1, c + 1);
                    for (int i = 0; i < candidates.length; i++) {
                        if (candidates[i] && rowCandidates[i]) {
                            candidates[i] = false;
                        }
                    }
                }
                
                // Count occurrences of "true" in boolean[] candidates to determine if there is only one occurrence
                // of "true." If there is, there is only one candidate for this cell, in which case the board is altered
                // accordingly
                int trueCount = 0;
                int trueIndex = 0;
                for (int i = 0; i < candidates.length; i++) {
                    if (candidates[i]) {
                        trueCount++;
                        trueIndex = i;
                    }
                }
                if (trueCount == 1) {
                    board[row][column] = trueIndex + 1; // Alter board
                    ans = true;
                    break; // break out of for loop, continue to next cell in the board
                }
                
                // Find hidden singles within subunit: column
                // Use 2 for loops as before
                
                candidates = candidates(row + 1, column + 1);
                
                for (int r = 0; r < row; r++) {
                    boolean[] columnCandidates = candidates(r + 1, column + 1);
                    for (int i = 0; i < candidates.length; i++) {
                        if (candidates[i] && columnCandidates[i]) {
                            candidates[i] = false;
                        }
                    }
                }
                for (int r = row + 1; r < board[0].length; r++) {
                    boolean[] columnCandidates = candidates(r + 1, column + 1);
                    for (int i = 0; i < candidates.length; i++) {
                        if (candidates[i] && columnCandidates[i]) {
                            candidates[i] = false;
                        }
                    }
                }
                
                // Count occurrences of "true" in boolean[] candidates to determine if there is only one occurrence
                // of "true." If there is, there is only one candidate for this cell, in which case the board is altered
                // accordingly
                trueCount = 0;
                trueIndex = 0;
                for (int i = 0; i < candidates.length; i++) {
                    if (candidates[i]) {
                        trueCount++;
                        trueIndex = i;
                    }
                }
                if (trueCount == 1) {
                    board[row][column] = trueIndex + 1; // Alter board
                    ans = true;
                    break; // break out of for loop, continue to next cell in the board
                }
                
                candidates = candidates(row + 1, column + 1);
                
                int boxR = (row * 3) / 3;
                int colR = (column * 3) / 3;
                
                for (int r = boxR; r < boxR + 3; r++) {
                    for (int c = colR; c < colR + 3; c++) {
                        if (!(r == row && c == column)) {
                            boolean[] boxCandidates = candidates(r + 1, c + 1);
                            for (int i = 0; i < candidates.length; i++) {
                                if (candidates[i] && boxCandidates[i]) {
                                    candidates[i] = false;
                                }
                            }
                        }
                    }
                }
                
                trueCount = 0;
                trueIndex = 0;
                for (int i = 0; i < candidates.length; i++) {
                    if (candidates[i]) {
                        trueCount++;
                        trueIndex = i;
                    }
                }
                if (trueCount == 1) {
                    board[row][column] = trueIndex + 1; // Alter board
                    ans = true;
                }
            }
        }
        return ans;
    }
        
    
    /**
     * Determines if the board is in a solved state by visiting every cell in the board and checking that its value
     * is greater than 0.
     * 
     * @return true if board is in a solved state, false otherwise
     */
    public boolean isSolved() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Attempt to solve the board using the methods nakedSingles() and hiddenSingles()
     */
    public void solve() {
        while (!isSolved() && (nakedSingles() || hiddenSingles()));
    }
    
    /**
     * Utility method
     * Encode the integer values in the current state of the game board into an 81-character String
     * 
     * @return 81-character String representing values in cells on the board
     */
    public String encodeBoard() {
        String ans = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                ans += board[r][c];
            }
        }
        return ans;
    }
    
    /**
     * Utility method
     * Decode a string of the type returned by the method encodeBoard() and set the board to reflect these values
     * @param s the String to be decoded
     */
    public void decodeStringToBoard(String s) {
        char[] a = s.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = Integer.parseInt("" + a[(9 * i) + j]);
            }
        }
    }
    
    /**
     * Utility method
     * Decodes an 81-character String into a nine-by-nine 2-dimensional array of integers, which is returned
     * 
     * @param s a String that is decoded into a 2-dimensional array of integers
     * @return a 2-dimensional array of integers
     */
    public int[][] decodedBoard(String s) {
        int[][] ans = new int[9][9];
        char[] a = s.toCharArray();
        java.util.Scanner sc = new java.util.Scanner(s);
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                ans[i][j] = Integer.parseInt("" + a[(9 * i) + j]);
            }
        }
        return ans;
    }
    
}