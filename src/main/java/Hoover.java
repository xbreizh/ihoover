
import model.Messages;
import model.Position;
import model.Instruction;
import model.Orientation;
import model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static model.Messages.*;

public final class Hoover {

    private static final Logger LOGGER = LogManager.getLogger(Hoover.class);
    private final Scanner scanner;

    public Hoover() {
        this.scanner = new Scanner(System.in);
    }

    void play() {
        try {
            LOGGER.info(Messages.GENERAL_RULES);
            final Room room = setRoomSize();
            final Position initialPosition = setInitialPosition(room);
            outputNewPosition(room, initialPosition);
        } catch (final Exception e) {
            LOGGER.warn(e);
        }
    }

    private void outputNewPosition(final Room room, final Position initialPosition) {
        Position newPosition = null;

        while (true) {
            if (null == newPosition) {
                newPosition = initialPosition;
            }
            LOGGER.info(MOVE_INSTRUCTION);

            String instruction = getMessage();
            LOGGER.debug("\nInstructions received " + instruction + "\n");

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
        LOGGER.info("La position final est: " + newPosition);
    }

    private String getMessage() {
        return this.scanner.nextLine().replace(" ", "").toUpperCase();
    }

    Position updateForSeveralInstructions(final Room room, Position initialPosition, final String instruction) {
        LOGGER.debug("Instructions received: " + instruction);
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
                LOGGER.debug(
                        "The instructions are valid: " + newPosition +
                                "\n############################ \nNew model.Position: \n" +
                                "x= " + newPosition.getX() +
                                " y= " + newPosition.getY() +
                                " orientation= " + newPosition.getOrientation() + "\n"
                );
            }
        }
        LOGGER.info("\nLa nouvelle position est: " + newPosition + "\n");
        return newPosition;
    }


    boolean isNewPositionValid(final Position newPosition, final Room room) {
        return
                newPosition.getX() > 0 &&
                        newPosition.getY() > 0 &&
                        newPosition.getX() <= room.getX() &&
                        newPosition.getY() <= room.getY();
    }

    boolean isValidInstructions(final String instruction) {
        final String toCheck = instruction.replace(" ", "").toUpperCase();
        if (!toCheck.matches("[AGD]+")) {
            LOGGER.warn("Invalid instructions: " + toCheck);
            return false;
        }
        return true;
    }

    Room setRoomSize() {
        while (true) {
            LOGGER.info(ROOM_SIZE_INSTRUCTION);
            String input = this.getMessage();
            if (input.equalsIgnoreCase("H")) {
                LOGGER.info(Messages.GENERAL_RULES);
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
            System.out.flush();
            return new Room(x, y);
        }
    }

    Position setInitialPosition(final Room room) {
        while (true) {
            LOGGER.info(INITIAL_POSITION_INSTRUCTION + " (Dimension de la pièce: " + room.getX() + " X " + room.getY() + ")");
            String input = getMessage();

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

    boolean isInitialPositionValid(final String position, final Room room) {
        try {
            final String[] values = position.toUpperCase().replace(" ", "").split(",");
            if (values.length != 3) {
                return false;
            }
            final int x = Integer.parseInt(values[0]);
            final int y = Integer.parseInt(values[1]);
            Orientation.valueOf(values[2]);
            return
                    x > 0 &&
                            x < room.getY() &&
                            y > 0 &&
                            y < room.getY();
        } catch (final Throwable e) {
            LOGGER.warn("Invalid position " + position);
            return false;
        }
    }

    boolean isRoomSizeValid(final String boardSize) {
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

    Position updatePosition(final Position currentPosition, final Instruction value) {
        switch (value) {

            case G:
                return new Position(currentPosition.getX(), currentPosition.getY(), Orientation.valueOf(currentPosition.getOrientation().getLeft()));
            case D:
                return new Position(currentPosition.getX(), currentPosition.getY(), Orientation.valueOf(currentPosition.getOrientation().getRight()));
            default:
                return moveForward(currentPosition);
        }
    }

    Position moveForward(final Position currentPosition) {
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
