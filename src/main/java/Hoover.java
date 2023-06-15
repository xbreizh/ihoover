import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
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
        // TODO Validate instruction
        // TODO Validate position against board size
        Position newPosition = initialPosition;
        final List<Character> instructionList = new ArrayList<>();
        String instruction = scanner.nextLine();
        for (char c : instruction.toUpperCase().toCharArray()) {
            instructionList.add(c);
        }

        for (char c : instructionList) {
            newPosition = updatePosition(newPosition, Instruction.valueOf(String.valueOf(c)));
        }
        System.out.println("############################ \nPosition finale: \n" +
                "x= " + newPosition.x +
                " y= " + newPosition.y +
                " orientation= " + newPosition.orientation
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
            try {
                System.out.println(INITIAL_POSITION_INSTRUCTION + " (Dimension de la pièce: " + roomSize[0] + " X " + roomSize[1] + ")");
                String input = scanner.nextLine().replace(" ", "").toUpperCase();

                if (!isInitialPositionValid(input, roomSize)) {
                    System.out.println("\n###\nValeurs invalides: " + input + "\n###\n\n");
                    continue;
                }
                final String[] values = input.split(",");
                final int x = Integer.parseInt(values[0]);
                final int y = Integer.parseInt(values[1]);
                final Orientation o = Orientation.valueOf(values[2]);
                System.out.println("Your initial position is: " +
                        "x=" + x + " y= " + y + " orientation= " + o);
                return new Position(x, y, o);
            } catch (final Throwable ignore){}
        }
    }

    static boolean isInitialPositionValid(final String position, final int[] roomSize) {
        try {
            final String[] values = position.toUpperCase().replace(" ", "").split(",");
            if (values.length != 3) {
                return false;
            }
            final int x = Integer.parseInt(values[0]);
            final int y = Integer.parseInt(values[1]);
            final Orientation o = Orientation.valueOf(values[2]);
            return  x >= 0 &&
                    x <= roomSize[0] &&
                    y >= 0 &&
                    y <= roomSize[1] ;
        } catch (final Throwable e) {
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

    static Position updatePosition(final Position currentPosition, final Instruction value) {
       // final String currentOrientation = currentPosition.orientation.getName();

        switch (value) {

            case G:
                return new Position(currentPosition.x, currentPosition.y, Orientation.valueOf(currentPosition.orientation.getLeft()));
            case D:
                return new Position(currentPosition.x, currentPosition.y, Orientation.valueOf(currentPosition.orientation.getRight()));
            default:
        }
        return moveForward(currentPosition);
    }

    static Position moveForward(final Position currentPosition) {
        System.out.println("Initial position x " + currentPosition.x + " / y " + currentPosition.y);
        final int x;
        final int y;
        switch (currentPosition.orientation){
            case E:
                x = currentPosition.x + 1;
                y = currentPosition.y;
                break;
            case W:
                x = currentPosition.x - 1;
                y = currentPosition.y;
                break;
            case N:
                x = currentPosition.x;
                y = currentPosition.y + 1;
                break;
            default:
                x = currentPosition.x;
                y = currentPosition.y - 1 ;
        }
        System.out.println("Position updated: x " + x + " / y " + y);
        return new Position(x, y, currentPosition.orientation);
    }
}
