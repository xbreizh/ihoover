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
                    "Exemple: 23X44\n";

    static final String INITIAL_POSITION_INSTRUCTION =
            "Veuillez entrer les coordonnées de la position initiale de l'aspirateur.\n" +
                    "Celle-ci se compose de:" +
                    "- Position en longueur (entier compris entre 1 et 999999999)\n" +
                    "- Position en largeur (entier compris entre 1 et 999999999)\n" +
                    "- Orientation (N (Nord), E (Est), W (Ouest), S (Sud))\n" +
                    "Chaque valeur sera séparée par une virgule\n" +
                    "Note: les coordonnées devront bien être valides par rapport aux dimensions de la pièce fournis: ";

    static final int MAX_SIZE = 999999999;

    static final List<String> VALID_ORIENTATION = Arrays.asList("N", "E", "W", "S");

    void play() {
        System.out.println(GENERAL_RULES);
        final Scanner scn = new Scanner(System.in);
        final int[] roomSize = setRoomSize(scn);
        Position position = setInitialPosition(scn, roomSize);

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
            if (!validateRoomSize(input)) {
                System.out.println(
                        "\n###\nBoard size is invalid: " + input + "\n###\n\n");
                continue;
            }
            final int x = Integer.parseInt(input.split("X")[0]);
            final int y = Integer.parseInt(input.split("X")[1]);
            return new int[]{x, y};
        }
    }

    static boolean validateRoomSize(final String boardSize) {
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

    static Position setInitialPosition(final Scanner scanner, final int[] roomSize) {
        while (true) {
            System.out.println(INITIAL_POSITION_INSTRUCTION + roomSize[0] + " X " + roomSize[1]);
            String input = scanner.nextLine();
            String[] values = input.split(",");
            if (values.length != 3) {
                System.out.println("Invalid values provided: " + input);
                continue;
            }
            int x = Integer.parseInt(values[0]);
            int y = Integer.parseInt(values[1]);
            String o = values[2];
            if (x > MAX_SIZE || y > MAX_SIZE || !VALID_ORIENTATION.contains(o)) {
                System.out.println("Invalid values provided: " + input);
                continue;
            }

            return new Position(x, y, o);
        }
    }
}
