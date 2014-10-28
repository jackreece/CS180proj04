public class Sudoku {
    
    private int[][] board;
    
    public Sudoku() {
        this.board = new int[9][9];
    }
    
    public Sudoku(int[][] board) {
        this.board = board;
    }
    
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
     * Arguments to row and column are integers numbered 1 - 9 (NOT 0 - 8)
     */
    public boolean[] candidates(int row, int column) {
        
        boolean[] ans = new boolean[9];
        if (row > 9 || row < 1) {
            return ans;
        }
        if (column > 9 || column < 1) {
            return ans;
        }
        for (int i = 0; i < ans.length; i++) {
            ans[i] = true;
        }
        // Initialize ans to have every entry of value "true" -- indicates all numbers 1 - 9 are candidates
        
        // For array indexing purposes, change row and column to be numbers within range 0 - 8 instead of 1 - 9
        row--;
        column--;
        
        int boxR = row / 3; // The row which marks the corner of the box to iterate over
        int boxC = column / 3; // The column which ""
        
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
    
    public String encodeBoard() {
        String ans = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                ans += board[r][c];
            }
        }
        return ans;
    }
    
    public void decodeStringToBoard(String s) {
        
    }
    
    public int[][] decodedBoard(String s) {
        int[][] ans = new int[9][9];
        char[] a = s.toCharArray();
        java.util.Scanner sc = new java.util.Scanner(s);
        
    }
    
}