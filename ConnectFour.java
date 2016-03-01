import java.util.Random;
import java.util.Scanner;
public class ConnectFour {
    private static final int GAME_WIDTH = 7;
    private static final int GAME_HEIGHT = 6;
    private static final String TOKEN1 = "X";
    private static final String TOKEN2 = "O";
    private static String[][] board = new String[GAME_HEIGHT][GAME_WIDTH];
    private enum GameStatus {
        ONE, TWO, TIE, ONGOING, QUIT
    }
    public static void main(String[] args) {
        boolean singlePlayer = true;
        boolean playerOnesTurn = true;
        for (int row = 0; row < GAME_HEIGHT; row++) {
            for (int col = 0; col < GAME_WIDTH; col++) {
                board[row][col] = null;
            }
        }
        String mode = " ";
        if (args.length != 0) {
            if (args[0].equals("2")) {
                mode = args[0];
            } else if (args[0].equals("1")) {
                mode = args[0];
            }
        } else {
            System.out.print("Would you like to play");
            System.out.print(" single player or two");
            System.out.print(" player mode? (Enter 1 or 2)");
            Scanner scan = new Scanner(System.in);
            mode = scan.nextLine();
        }
        int[] count = {0, 0, 0, 0, 0, 0, 0, 0};
        String myToken;
        if (mode.equals("2")) {
            System.out.println("Welcome to 2-player mode of Connect Four!");
            System.out.println("Choose where to go by entering the slot");
            System.out.println(" number.");
            System.out.println("Type \'q\' if you would like to quit.");
            printBoard();
            System.out.println("Player 1, where would you like to go?");
            singlePlayer = false;
        } else if (mode.equals("1")) {
            System.out.println("Welcome to 1-player mode of Connect Four!");
            System.out.println("Choose where to go by entering the slot");
            System.out.println(" number.");
            System.out.println("Type \'q\' if you would like to quit.");
            printBoard();
            System.out.println("Player, where would you like to go?");
        }
        Random myran = new Random();
        int randomSlot;
        GameStatus b = GameStatus.ONGOING;
        Scanner move = new Scanner(System.in);
        String slot = move.nextLine();
        String a = "ONGOING";
        while (b.equals(GameStatus.ONGOING) && !slot.equals("q")) {
            int numericSlot = (Integer.parseInt(slot)) - 1;
            if (count[numericSlot] < 6) {
                count[numericSlot]++;
                if (playerOnesTurn || singlePlayer) {
                    myToken = TOKEN1;
                } else {
                    myToken = TOKEN2;
                }
                board[6 - (count[numericSlot])][numericSlot] = myToken;
                printBoard();
                b = findWinner();
                if (!singlePlayer) {
                    if (playerOnesTurn && b.equals(GameStatus.ONGOING)) {
                        System.out.print("Player 2, where would you like");
                        System.out.println(" to go?");
                        playerOnesTurn = false;
                        slot = move.nextLine();
                    } else if (b.equals(GameStatus.ONGOING)) {
                        System.out.print("Player 2, where would you like");
                        System.out.println(" to go?");
                        playerOnesTurn = true;
                        slot = move.nextLine();
                    }
                } else {
                    numericSlot = myran.nextInt(7);
                    board[5 - (count[numericSlot])][numericSlot] = TOKEN2;
                    printBoard();
                    if (b.equals(GameStatus.ONGOING)) {
                        System.out.print("Player 2, where would you like");
                        System.out.println(" to go?");
                        slot = move.nextLine();
                    }
                }
            } else {
                System.out.println("That column is full. Please pick another!");
                slot = move.nextLine();
            }
            if (b.equals(GameStatus.ONE)) {
                System.out.println("Woohoo! Player 1 won!");
            } else if (b.equals(GameStatus.TWO)) {
                System.out.println("Woohoo! Player 2 won!");
            } else if (b.equals(GameStatus.TIE)) {
                System.out.println("There was a tie!");
            } else if (b.equals(GameStatus.QUIT)) {
                System.out.println("Goodbye!");
            }
        }
        if (slot.equals("q")) {
            System.out.println("Goodbye!");
        }
    }
    private static void printBoard() {
        for (int i1 = 0; i1 < 6; i1++) {
            for (int i2 = 0; i2 < 7; i2++) {
                if (board[i1][i2] == null) {
                    System.out.print("|" + "   ");
                } else {
                    System.out.print("| " + board[i1][i2] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
        System.out.println("  1   2   3   4   5   6   7  ");
    }
    private static GameStatus findWinner() {
        if (isColumnVictory(TOKEN1) || isRowVictory(TOKEN1)
            || isTopLeftDiagonalVictory(TOKEN1)
            || isTopRightDiagonalVictory(TOKEN1)) {
            return GameStatus.ONE;
        } else if (isColumnVictory(TOKEN2) || isRowVictory(TOKEN2)
                || isTopLeftDiagonalVictory(TOKEN2)
                || isTopRightDiagonalVictory(TOKEN2)) {
            return GameStatus.TWO;
        } else if (isBoardFull()) {
            return GameStatus.TIE;
        } else {
            return GameStatus.ONGOING;
        }
    }
    private static boolean isBoardFull() {
        for (int col = 0; col < GAME_WIDTH; col++) {
            for (int row = 0; row < GAME_HEIGHT; row++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean isColumnVictory(String token) {
        for (int col = 0; col < GAME_WIDTH; col++) {
            int count = 0;
            for (int row = 0; row < GAME_HEIGHT; row++) {
                if (board[row][col] != null) {
                    if (board[row][col].equals(token)) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isRowVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            int count = 0;
            for (int col = 0; col < GAME_WIDTH; col++) {
                if (board[row][col] != null) {
                    if (board[row][col].equals(token)) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    count = 0;
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isTopLeftDiagonalVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            for (int col = 0; col < GAME_WIDTH; col++) {
                int count = 0;
                for (int delta = 0; delta < 5; delta++) {
                    if (withinBounds(row + delta, col + delta)
                            && board[row + delta][col + delta] != null) {
                        if (board[row + delta][col + delta].equals(token)) {
                            count++;
                        } else {
                            count = 0;
                        }
                    } else {
                        count = 0;
                    }
                    if (count == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean isTopRightDiagonalVictory(String token) {
        for (int row = 0; row < GAME_HEIGHT; row++) {
            for (int col = GAME_WIDTH - 1; col >= 0; col--) {
                int count = 0;
                for (int delta = 0; delta < 5; delta++) {
                    if (withinBounds(row + delta, col - delta)
                            && board[row + delta][col - delta] != null) {
                        if (board[row + delta][col - delta].equals(token)) {
                            count++;
                        } else {
                            count = 0;
                        }
                    } else {
                        count = 0;
                    }
                    if (count == 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean withinBounds(int row, int col) {
        return (row < GAME_HEIGHT && row >= 0)
            && (col < GAME_WIDTH && col >= 0);
    }
}