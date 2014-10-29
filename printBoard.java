public void printBoard() {
        //returns a COPY of the current state of the board
        char letter = 64;
        System.out.println("    1 2 3   4 5 6   7 8 9 ");
        for (int j = 0; j < 13; j++) {
            letter++;
            if (j % 4 == 0) {
                System.out.println("  +-------+-------+-------+");
                letter--;
            } else {
                System.out.print(letter + " |");
                for (int i = 0; i < 9; i++) {
                    if (board[j][i] == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(" " + board[j][i]);
                    }
                    if (i == 2 || i == 5 || i == 8) {
                        System.out.print(" |");
                    }
                    
                }
                System.out.println();
            }
        }
    }
