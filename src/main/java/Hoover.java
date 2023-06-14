import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Hoover {

    static final String GENERAL_RULES =
            "Vous pilotez un aspirateur automatique. \n" +
                    "Vous allez devoir péciser:\n" +
                    "- la dimension de la pièce\n" +
                    "- la position de départ de l'aspirateur\n" +
                    "- les instructions de déplacement\n" +
                    "Vous pouvez réafficher ce message à tout moment en tapant \"H\"";

    static final String ROOM_SIZE_INSTRUCTION =
            "Veuillez entrer les dimensions de la pièce au format longueur X largeur\n" +
                    "La valeur doit être comprise entre 1 et 999999999\n" +
                    "Exemple: 23X44\n\n" +
                    "Veuillez entrer les dimensions de la pièce: \n";

    static final String INITIAL_POSITION_INSTRUCTION =
            "Veuillez entrer les coordonnées de la position initiale de l'aspirateur.\n" +
                    "Celle-ci se compose de:" +
                    "- Position en longueur (entier compris entre 1 et 999999999)\n" +
                    "- Position en largeur (entier compris entre 1 et 999999999)\n" +
                    "- Orientation (N (Nord), E (Est), W (Ouest), S (Sud))\n" +
                    "Chaque valeur sera séparée par une virgule\n" +
                    "Note: les coordonnées devront bien être valides par rapport aux dimensions de la pièce fournis: \n" +
                    "Exemple: 3,5,N\n" +
                    "Veuillez entrer les coordonnées de la position initiale: ";

    static final int MAX_SIZE = 999999999;

    static final List<String> VALID_ORIENTATION = Arrays.asList("N", "E", "W", "S");
    static final List<String> VALID_INSTRUCTIONS = Arrays.asList("D", "G", "A");

    void play() {
        System.out.println(GENERAL_RULES);
        final Scanner scn = new Scanner(System.in);
        final int[] roomSize = setRoomSize(scn);
        final Position initialPosition = setInitialPosition(scn, roomSize);
        outputNewPosition(scn, roomSize, initialPosition);
    }

    private void outputNewPosition(final Scanner scanner, final int[] roomSize, final Position initialPosition) {

        final Position newPosition = new Position(1, 34, "N");
        System.out.println("###### TODO #### \nPosition finale: " +
                "x= " + newPosition.x +
                "y= " + newPosition.x +
                "orientation= " + newPosition.orientation
                );
    }

    static int[] setRoomSize(final Scanner scn) {
        while (true) {
            System.out.println(ROOM_SIZE_INSTRUCTION);
            String input = scn.nextLine();
            if (input.equalsIgnoreCase("H")) {
                System.out.println(GENERAL_RULES);
                continue;
            }
            input = input.replaceAll(" ", "").toUpperCase();
            if (!isRoomSizeValid(input)) {
                System.out.println(
                        "\n###\nBoard size is invalid: " + input + "\n###\n\n");
                continue;
            }
            final int x = Integer.parseInt(input.split("X")[0]);
            final int y = Integer.parseInt(input.split("X")[1]);
            return new int[]{x, y};
        }
    }

    static Position setInitialPosition(final Scanner scanner, final int[] roomSize) {
        while (true) {
            System.out.println(INITIAL_POSITION_INSTRUCTION + " (Dimension de la pièce: " + roomSize[0] + " X " + roomSize[1] + ")");
            String input = scanner.nextLine();

            if (!isInitialPositionValid(input, roomSize)) {
                System.out.println("\n###\nValeurs invalides: " + input+ "\n###\n\n");
                continue;
            }
            final String[] values = input.split(",");
            final int x = Integer.parseInt(values[0]);
            final int y = Integer.parseInt(values[1]);
            final String o = values[2];
            System.out.println("Your initial position is: " +
                    "x=" + x + " y= " + y + " orientation=" + o);
            return new Position(x, y, o);
        }
    }

    static boolean isInitialPositionValid(final String position, final int[] roomSize){
        try{
            final String[] values = position.replace(" ", "").split(",");
            if (values.length != 3) {
                return false;
            }
            final int x = Integer.parseInt(values[0]);
            final int y = Integer.parseInt(values[1]);
            final String o = values[2];
            return  x >= 1 &&
                    x <= roomSize[0] &&
                    y >= 1 &&
                    y <= roomSize[1] &&
                    VALID_ORIENTATION.contains(o);
        } catch (final Exception e) {
            return false;
        }
    }

    static boolean isRoomSizeValid(final String boardSize) {
        try {
            final String roomSizeWithNoSpace = boardSize.replace(" ", "").toUpperCase();
            final String[] size = roomSizeWithNoSpace.split("X");
            final int x = Integer.parseInt(size[0]);
            final int y = Integer.parseInt(size[1]);
            return size.length == 2 && x > 0 && y > 0;
        } catch (final Exception e) {
            return false;
        }
    }
}
