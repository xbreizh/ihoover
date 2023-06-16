import model.Instruction;
import model.Orientation;
import model.Position;
import model.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HooverTest {

    private final Hoover hoover = new Hoover();
    @Test
    void shouldValidateRoomSize() {
        assertAll(
            () -> assertTrue(hoover.isRoomSizeValid("3X4")),
            () -> assertTrue(hoover.isRoomSizeValid(" 2          x 3 0 ")),
            () -> assertTrue(hoover.isRoomSizeValid("111111111X333333333"))
        );
    }

    @Test
    void shouldNotValidateRoomSize() {
        assertAll(
            () -> assertFalse(hoover.isRoomSizeValid("asaxa")),
            () -> assertFalse(hoover.isRoomSizeValid("@3X2")),
            () -> assertFalse(hoover.isRoomSizeValid("1.2x2")),
            () -> assertFalse(hoover.isRoomSizeValid("2x9999999999")),
            () -> assertFalse(hoover.isRoomSizeValid("0x9999999999")),
            () -> assertFalse(hoover.isRoomSizeValid("0x0")),
            () -> assertFalse(hoover.isRoomSizeValid("2x-1"))
        );
    }

    @Test
    void shouldNotValidateInitialPosition() {
        final Room room = new Room(7,7);
        assertAll(
            () -> assertFalse(hoover.isInitialPositionValid("1w,2,N", room)),
            () -> assertFalse(hoover.isInitialPositionValid("1,2,Y", room)),
            () -> assertFalse(hoover.isInitialPositionValid("1,2,N,N", room)),
            () -> assertFalse(hoover.isInitialPositionValid("1:2:N:N", room)),
            () -> assertFalse(hoover.isInitialPositionValid("1,2777777777777,Y", room)),
            () -> assertFalse(hoover.isInitialPositionValid("-5,s", room)),
            () -> assertFalse(hoover.isInitialPositionValid("1,22,N", room))
        );
    }

    @Test
    void shouldValidateInitialPosition() {
        final Room room = new Room(7,7);
        assertAll(
            () -> assertTrue(hoover.isInitialPositionValid("4,2,N", room)),
            () -> assertTrue(hoover.isInitialPositionValid("1,1,N", room)),
            () -> assertTrue(hoover.isInitialPositionValid("4      ,2,N           ", room)),
            () -> assertTrue(hoover.isInitialPositionValid("1,2,s", room))
        );
    }

    @Test
    void checkOrientation(){

    }

    @Test
    void moveForward() {
        final Position initialPosition = new Position(2,2, Orientation.N);
        final Position finalPosition = hoover.moveForward(initialPosition);
        assertAll(
            () -> assertEquals(finalPosition.getX(), initialPosition.getX()),
            () -> assertEquals(finalPosition.getY(), initialPosition.getY()+1),
            () -> assertEquals(finalPosition.getOrientation(), initialPosition.getOrientation())
        );
    }

    @Test
    void isValidInstructions() {
        assertAll(
            ()-> assertTrue(hoover.isValidInstructions("agdGAddg G")),
            ()-> assertTrue(hoover.isValidInstructions("AGG g dAAA"))
        );
    }

    @Test
    void isInValidInstructions() {
        assertAll(
            ()-> assertFalse(hoover.isValidInstructions("agdGA-ddg G")),
            ()-> assertFalse(hoover.isValidInstructions("AGG g cdAAA"))
        );
    }

    @Test
    void isNewPositionValid() {
        final Room room = new Room(7,7);
        assertAll(
            ()-> assertTrue(hoover.isNewPositionValid(new Position(7,1,Orientation.E), room)),
            ()-> assertTrue(hoover.isNewPositionValid(new Position(6,1,Orientation.E), room)),
            ()-> assertTrue(hoover.isNewPositionValid(new Position(7,1,Orientation.E), room))
        );
    }

    @Test
    void isNewPositionInValid() {
        final Room room = new Room(7,7);
        assertAll(
            ()-> assertFalse(hoover.isNewPositionValid(new Position(0,-1,Orientation.E), room)),
            ()-> assertFalse(hoover.isNewPositionValid(new Position(0,1, Orientation.E), room)),
            ()-> assertFalse(hoover.isNewPositionValid(new Position(8,1,Orientation.E), room))
        );
    }

    @Test
    void updatePositionForSingleInstruction() {
        final Position position = new Position(0,0,Orientation.N);
        assertAll(
            ()-> assertEquals(Orientation.W, hoover.updatePosition(position, Instruction.G).getOrientation()),
            ()-> assertEquals(Orientation.E, hoover.updatePosition(position, Instruction.D).getOrientation()),
            ()-> assertEquals(Orientation.N, hoover.updatePosition(position, Instruction.A).getOrientation())
        );
    }

    @Test
    void updatePositionForSeveralInstructions() {
        final Position initialPosition = new Position(0,0,Orientation.N);
        Position newPosition = hoover.updatePosition(initialPosition, Instruction.D);
        newPosition = hoover.updatePosition(newPosition, Instruction.G);
        newPosition = hoover.updatePosition(newPosition, Instruction.A);
        newPosition = hoover.updatePosition(newPosition, Instruction.A);
        newPosition = hoover.updatePosition(newPosition, Instruction.D);
        assertEquals(0, newPosition.getX());
        assertEquals(2, newPosition.getY());
        assertEquals(Orientation.E, newPosition.getOrientation());
    }

    @Test
    void updateForSeveralInstructions() {
        final Position initialPosition = new Position(5,5,Orientation.N);
        final Position expectedPosition = new Position(5,6,Orientation.N);
        final String instruction = "DADADADAA";
        final Position newPosition = hoover.updateForSeveralInstructions(new Room(10,10), initialPosition, instruction);
        assertAll(
                () -> assertEquals(expectedPosition.getX(), newPosition.getX()),
                () -> assertEquals(expectedPosition.getY(), newPosition.getY()),
                () -> assertEquals(expectedPosition.getOrientation(), newPosition.getOrientation())
        );
    }

    @Test
    void shouldNotUpdateIfInvalidInstructions() {
        final Position initialPosition = new Position(0,0,Orientation.N);
        final Position expectedPosition = new Position(0,0,Orientation.N);
        final String instruction = "AAAggggaaya";
        final Position newPosition = hoover.updateForSeveralInstructions(new Room(7,7), initialPosition, instruction);
        assertAll(
                () -> assertEquals(expectedPosition.getX(), newPosition.getX()),
                () -> assertEquals(expectedPosition.getY(), newPosition.getY()),
                () -> assertEquals(expectedPosition.getOrientation(), newPosition.getOrientation())
        );
    }

    @Test
    void shouldNotUpdateIfOutOfBoard() {
        final Position initialPosition = new Position(0,0,Orientation.N);
        final Position expectedPosition = new Position(0,0,Orientation.N);
        final String instruction = "AAA";
        final Position newPosition = hoover.updateForSeveralInstructions(new Room(2,2), initialPosition, instruction);
        assertAll(
                () -> assertEquals(expectedPosition.getX(), newPosition.getX()),
                () -> assertEquals(expectedPosition.getY(), newPosition.getY()),
                () -> assertEquals(expectedPosition.getOrientation(), newPosition.getOrientation())
        );
    }
}