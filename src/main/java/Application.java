import java.util.Scanner;

public class Application {


    final int dimension = 2;

    public static void main(String[] args) {
        int length;
        int width;
        boolean boardSizeValid = false;
        String boardSize="";
        while (!boardSizeValid) {
            System.out.println("Enter board size: ");
            final Scanner scn = new Scanner(System.in);
            boardSize = scn.nextLine();
            boardSize = boardSize.replaceAll(" ", "").toUpperCase();
            boardSizeValid = Application.validateBoardSize(boardSize);
            if (!boardSizeValid){
                System.out.println("Board size is invalid " + boardSize);
            }
        }
        System.out.println("dded " + boardSize.split("X")[0]);
        length = Integer.parseInt(boardSize.split("X")[0]);
        width = Integer.parseInt(boardSize.split("X")[1]);
        System.out.println("board size is now valid: " + length + "X" + width);
    }

    static boolean validateBoardSize(final String boardSize) {
        final String pattern = "^[1-9][0-9]{0,8}X[0-9]{0,8}$";

        return boardSize.matches(pattern);
    }

    static boolean validateInitialPosition(final String initialPosition) {
        return false;
    }
}
