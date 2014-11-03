import junit.framework.TestCase;
import java.util.Arrays;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class SudokuTests extends TestCase {
    
    Sudoku mySudoku = new Sudoku(); // Sudoku instance for use in tests
    
    public void testBoard() {
        assertTrue(Arrays.deepEquals(mySudoku.board(), new int[9][9]));
    }
    
    public void testPrintBoard() {
        mySudoku = new Sudoku();
    }
    
    public void testCandidates() {
        mySudoku = new Sudoku();
        boolean[] candidates = mySudoku.candidates(1, 1);
        for (int i = 0; i < candidates.length; i++) {
            assertTrue(candidates[i]);
        }
    }
    
    public void testIsSolved() {
        mySudoku.decodeStringToBoard(
                                     "123456780140000670080002400063070010900000003010090520007200080026000035000409000"
                                    ); // decode a String representation of a board that contains empty spaces
        assertFalse(mySudoku.isSolved()); // mySudoku's board is not solved -- isSolved() should return "false"
        mySudoku.decodeStringToBoard(
                                     "672145398" +
                                     "145983672" +
                                     "389762451" +
                                     "263574819" +
                                     "958621743" +
                                     "714398526" +
                                     "597236184" +
                                     "426817935" +
                                     "831459267"
                                    ); // decode a String representation of a solved board
        assertTrue(mySudoku.isSolved()); // mySudoku's board is solved -- isSolved() should return "true"
    }
    
    public void testNakedSingles() {
        mySudoku.decodeStringToBoard(
                                     "123456780" +
                                     "140000670" +
                                     "080002400" +
                                     "063070010" +
                                     "900000003" +
                                     "010090520" +
                                     "007200080" +
                                     "026000035" +
                                     "000409000"
                                    );
        assertTrue(mySudoku.nakedSingles());
        assertEquals(9, mySudoku.board()[0][8]);
    }
    
    /**
     * Create a Sudoku instance with a board that has a hidden single at row 3, column 4. The hidden single at this
     * row/column cross-section is 6. Execute a call to hiddenSingles(), and then check that the value of the board at
     * row 3, column 4 is equal to 6 -- this means that hiddenSingles() executed correctly.
     */
    public void testHiddenSingles() {
        mySudoku.decodeStringToBoard("028007000" +
                                     "016083070" +
                                     "000020851" +
                                     "137290000" +
                                     "000730000" +
                                     "000046307" +
                                     "290070000" +
                                     "000860140" +
                                     "000300700"
                                    ); // this board has a hidden single (6) at row 3, column 4
        assertTrue(mySudoku.hiddenSingles()); // hiddenSingles() returns "true" if it alters the board. Test for this.
        assertEquals(6, mySudoku.board()[2][3]); // test if the board was altered to reflect the hidden single
    }
    
    /**
     * Initialize mySudoku to represent a game in an unsolved state. A call to mySudoku.solve() should leave the board
     * in a solved state.
     */
    public void testSolve() {
        mySudoku.decodeStringToBoard(
                                     "000105000140000670080002400063070010900000003010090520007200080026000035000409000"
                                    );
        mySudoku.solve();
        assertTrue(mySudoku.isSolved());
    }
    
    public void testEncodeBoard() {
        mySudoku = new Sudoku();
        assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                     mySudoku.encodeBoard());
        mySudoku.decodeStringToBoard("028007000" +
                                     "016083070" +
                                     "000020851" +
                                     "137290000" +
                                     "000730000" +
                                     "000046307" +
                                     "290070000" +
                                     "000860140" +
                                     "000300700"
                                    );
        assertEquals("028007000016083070000020851137290000000730000000046307290070000000860140000300700",
                     mySudoku.encodeBoard());
    }
    
    public void testDecodeStringToBoard() {
        mySudoku.decodeStringToBoard("028007000" +
                                     "016083070" +
                                     "000020851" +
                                     "137290000" +
                                     "000730000" +
                                     "000046307" +
                                     "290070000" +
                                     "000860140" +
                                     "000300700"
                                    );
        assertTrue(Arrays.deepEquals(new int[][]{
            { 0, 2, 8, 0, 0, 7, 0, 0, 0 },
            { 0, 1, 6, 0, 8, 3, 0, 7, 0 },
            { 0, 0, 0, 0, 2, 0, 8, 5, 1 },
            { 1, 3, 7, 2, 9, 0, 0, 0, 0 },
            { 0, 0, 0, 7, 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 4, 6, 3, 0, 7 },
            { 2, 9, 0, 0, 7, 0, 0, 0, 0 },
            { 0, 0, 0, 8, 6, 0, 1, 4, 0 },
            { 0, 0, 0, 3, 0, 0, 7, 0, 0 } 
        }, 
                                     mySudoku.board()
        ));
    }
    
    public void testDecodedBoard() {
        assertTrue(Arrays.deepEquals(new int[][]{
            { 0, 2, 8, 0, 0, 7, 0, 0, 0 },
            { 0, 1, 6, 0, 8, 3, 0, 7, 0 },
            { 0, 0, 0, 0, 2, 0, 8, 5, 1 },
            { 1, 3, 7, 2, 9, 0, 0, 0, 0 },
            { 0, 0, 0, 7, 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 4, 6, 3, 0, 7 },
            { 2, 9, 0, 0, 7, 0, 0, 0, 0 },
            { 0, 0, 0, 8, 6, 0, 1, 4, 0 },
            { 0, 0, 0, 3, 0, 0, 7, 0, 0 } 
        }, mySudoku.decodedBoard("028007000" +
                                 "016083070" +
                                 "000020851" +
                                 "137290000" +
                                 "000730000" +
                                 "000046307" +
                                 "290070000" +
                                 "000860140" +
                                 "000300700"
                                )));
    }
    
}