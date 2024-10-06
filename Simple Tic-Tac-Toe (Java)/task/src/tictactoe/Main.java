package tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String input = "         ";
        gridBoard(input);

        Scanner sc = new Scanner(System.in);
        boolean isXTurn = true;


        while (true) {
            //Prompt the current player to make a move
            input = ticTacToeGame(input, sc, isXTurn);

            //Display the updated grid
            gridBoard(input);

            //Check the status of the game (Win, draw or not finished)
            if (analyzeGame(input)) {
                break; //The game comes to an end once there is a winner
            }

            //Switch turn between X and O
            isXTurn = !isXTurn;
        }

        sc.close();
    }

    public static String ticTacToeGame(String input, Scanner sc, boolean isXTurn) {
        String userInput = input;
        while (true) {
            //Determine whose turn it is
            char playerSymbol = isXTurn ? 'X' : 'O';
            System.out.println("Enter the coordinates for (" + playerSymbol + "): ");

            //Get coordinates
            String coordinates = sc.nextLine();

            //Split and validate coordinate
            String[] coords = coordinates.split(" ");
            if (coords.length != 2 || !isNumeric(coords[0]) || !isNumeric(coords[1])) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);

            //Validating the row and column to be between 1 and 3
            if (row < 1 || row > 3 || col < 1 || col > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            //Converting 2D coordinates (row and column) to corresponding index in 1D array
            int index = (row - 1) * 3 + col - 1;

            // Check if the chosen cell is occupied
            if (input.charAt(index) != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            // If valid, update the board with the player's move
            userInput = input.substring(0, index) + playerSymbol + input.substring(index + 1);
            break;
        }

        return userInput;
    }



    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static void gridBoard(String input) {
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + input.charAt(i) + " " + input.charAt(i + 1) +
                    " " + input.charAt(i + 2) + " |");
        }
        System.out.println("---------");
    }

    public static boolean analyzeGame(String input) {
        //Count the number of X and O
        int xCount = 0;
        int oCount = 0;
        for (char c : input.toCharArray()) {
            if (c == 'X') {
                xCount++;
            }
            if (c == 'O') {
                oCount++;
            }
        }

        //Check for winners
        boolean oWins = checkWinner(input, 'O');
        boolean xWins = checkWinner(input, 'X');

        //Game result check
        if (Math.abs(xCount - oCount) > 1 || xWins && oWins) {
            impossible();
            return true;
        } else if (oWins) {
            oWins();
            return  true;
        } else if (xWins) {
            xWins();
            return  true;
        } else if (!input.contains(" ")) {
            draw();
            return  true;
        } else {
            return false;
        }

    }



    public static boolean checkWinner(String input, char player) {
        int[][] winningCombinations = {
                {0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {1,4,7},
                {2,5,8},
                {0,4,8},
                {2,4,6}
        };

        for (int[] combination : winningCombinations) {
            if (input.charAt(combination[0]) == player &&
                input.charAt(combination[1]) == player &&
                input.charAt(combination[2]) == player) {
                return true;
            }
        }
        return false;
    }

    public static void xWins() {
        System.out.println("X wins");
    }

    public static void oWins() {
        System.out.println("O wins");
    }

    public static void draw() {
        System.out.println("Draw");
    }

//    public static void notFinished() {
//        System.out.println("Game not finished");
//    }

    public static void impossible() {
        System.out.println("Impossible");
    }
}


