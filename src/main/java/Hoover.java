import java.util.Scanner;

import org.apache.log4j.Logger;

public final class Hoover {

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

    static final String MOVE_INSTRUCTION = "Quelles instructions souhaitez vous donner?\n" +
            "Voici la liste des possibilités: A (Avancer), G (Gauche), D (Droite)\n" +
            "Tape X pour arrêter";
    static final String FINISH = "######################\n" +
            "####### THE END ######\n" +
            "######################\n";

    private static final Logger LOGGER = Logger.getLogger(Hoover.class);

    void play() {
        try {
            LOGGER.info(GENERAL_RULES);
            final Scanner scanner = new Scanner(System.in);
            final Room room = setRoomSize(scanner);
            final Position initialPosition = setInitialPosition(scanner, room);
            outputNewPosition(scanner, room, initialPosition);
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    private void outputNewPosition(final Scanner scanner, final Room room, final Position initialPosition) {
        Position newPosition = null;

        while (true) {
            if (null == newPosition) {
                newPosition = initialPosition;
            }
            LOGGER.info(MOVE_INSTRUCTION);

            String instruction = scanner.nextLine().replace(" ", "").toUpperCase();
            LOGGER.debug("\nInstructions received " + instruction + "\n\n");

            if (instruction.equalsIgnoreCase("X")) {
                LOGGER.info(FINISH);
                break;
            }

            if (!isValidInstructions(instruction)) {
                LOGGER.warn("Invalid instructions: " + instruction);
                continue;
            }

            newPosition = updateForSeveralInstructions(room, newPosition, instruction);
        }

    }

    static Position updateForSeveralInstructions(final Room room, Position initialPosition, final String instruction) {
        Position newPosition = initialPosition;
        for (char c : instruction.toUpperCase().toCharArray()) {
            try {
                newPosition = updatePosition(newPosition, Instruction.valueOf(String.valueOf(c)));
            } catch (final IllegalArgumentException e) {
                LOGGER.warn("Invalid instruction " + c);
                newPosition = initialPosition;
                break;
            }
            if (!isNewPositionValid(newPosition, room)) {
                LOGGER.warn(
                    "Invalid position " + newPosition +
                    "Restoring previous position: " + initialPosition);
                newPosition = initialPosition;
                break;
            } else {
                LOGGER.info(
                    "The instructions are valid: " + newPosition +
                    "\n############################ \nNew Position: \n" +
                    "x= " + newPosition.getX() +
                    " y= " + newPosition.getY() +
                    " orientation= " + newPosition.getOrientation() + "\n"
                );
            }
        }
        return newPosition;
    }


    static boolean isNewPositionValid(final Position newPosition, final Room room) {
        return
                newPosition.getX() >= 0 &&
                        newPosition.getY() >= 0 &&
                        newPosition.getX() < room.getX() &&
                        newPosition.getY() < room.getY();
    }

    static boolean isValidInstructions(final String instruction) {
        final String toCheck = instruction.replace(" ", "").toUpperCase();
        if (!toCheck.matches("[AGD]+")) {
            LOGGER.warn("Invalid instructions: " + toCheck);
            return false;
        }
        return true;
    }

    static Room setRoomSize(final Scanner scn) {
        while (true) {
            System.out.println(ROOM_SIZE_INSTRUCTION);
            String input = scn.nextLine();
            if (input.equalsIgnoreCase("H")) {
                System.out.println(GENERAL_RULES);
                continue;
            }
            input = input.replaceAll(" ", "").toUpperCase();
            if (!isRoomSizeValid(input)) {
                LOGGER.warn(
                    "\n###\nBoard size is invalid: " + input + "\n###\n\n");
                continue;
            }
            final int x = Integer.parseInt(input.split("X")[0]);
            final int y = Integer.parseInt(input.split("X")[1]);
            return new Room(x, y);
        }
    }

    static Position setInitialPosition(final Scanner scanner, final Room room) {
        while (true) {
                LOGGER.info(INITIAL_POSITION_INSTRUCTION + " (Dimension de la pièce: " + room.getX() + " X " + room.getY() + ")");
                String input = scanner.nextLine().replace(" ", "").toUpperCase();

                if (!isInitialPositionValid(input, room)) {
                    LOGGER.warn("\n###\nValeurs invalides: " + input + "\n###\n\n");
                    continue;
                }
                final String[] values = input.split(",");
                final int x = Integer.parseInt(values[0]);
                final int y = Integer.parseInt(values[1]);
                final Orientation o = Orientation.valueOf(values[2]);
                LOGGER.info("Your initial position is: " +
                        "x=" + x + " y= " + y + " orientation= " + o);
                return new Position(x, y, o);

        }
    }

    static boolean isInitialPositionValid(final String position, final Room room) {
        try {
            final String[] values = position.toUpperCase().replace(" ", "").split(",");
            if (values.length != 3) {
                return false;
            }
            final int x = Integer.parseInt(values[0]);
            final int y = Integer.parseInt(values[1]);
            Orientation.valueOf(values[2]);
            return
                x >= 0 &&
                x <= room.getY() &&
                y >= 0 &&
                y <= room.getY();
        } catch (final Throwable e) {
            LOGGER.warn("Invalid position " + position);
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
        switch (value) {

            case G:
                return new Position(currentPosition.getX(), currentPosition.getY(), Orientation.valueOf(currentPosition.getOrientation().getLeft()));
            case D:
                return new Position(currentPosition.getX(), currentPosition.getY(), Orientation.valueOf(currentPosition.getOrientation().getRight()));
            default:
                return moveForward(currentPosition);
        }
    }

    static Position moveForward(final Position currentPosition) {
        final int x;
        final int y;
        switch (currentPosition.getOrientation()) {
            case E:
                x = currentPosition.getX() + 1;
                y = currentPosition.getY();
                break;
            case W:
                x = currentPosition.getX() - 1;
                y = currentPosition.getY();
                break;
            case N:
                x = currentPosition.getX();
                y = currentPosition.getY() + 1;
                break;
            default:
                x = currentPosition.getX();
                y = currentPosition.getY() - 1;
        }
        return new Position(x, y, currentPosition.getOrientation());
    }
}
