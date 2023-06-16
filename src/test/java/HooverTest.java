import model.Instruction;
import model.Orientation;
import model.Position;
import model.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HooverTest {


    @Test
    void shouldValidateRoomSize() {
        assertAll(
            () -> assertTrue(Hoover.isRoomSizeValid("3X4")),
            () -> assertTrue(Hoover.isRoomSizeValid(" 2          x 3 0 ")),
            () -> assertTrue(Hoover.isRoomSizeValid("111111111X333333333"))
        );
    }

    @Test
    void shouldNotValidateRoomSize() {
        assertAll(
            () -> assertFalse(Hoover.isRoomSizeValid("asaxa")),
            () -> assertFalse(Hoover.isRoomSizeValid("@3X2")),
            () -> assertFalse(Hoover.isRoomSizeValid("1.2x2")),
            () -> assertFalse(Hoover.isRoomSizeValid("2x9999999999")),
            () -> assertFalse(Hoover.isRoomSizeValid("0x9999999999")),
            () -> assertFalse(Hoover.isRoomSizeValid("0x0")),
            () -> assertFalse(Hoover.isRoomSizeValid("2x-1"))
        );
    }

    @Test
    void shouldNotValidateInitialPosition() {
        final Room room = new Room(7,7);
        assertAll(
            () -> assertFalse(Hoover.isInitialPositionValid("1w,2,N", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2,Y", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2,N,N", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("1:2:N:N", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2777777777777,Y", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("-5,s", room)),
            () -> assertFalse(Hoover.isInitialPositionValid("1,22,N", room))
        );
    }

    @Test
    void shouldValidateInitialPosition() {
        final Room room = new Room(7,7);
        assertAll(
            () -> assertTrue(Hoover.isInitialPositionValid("4,2,N", room)),
            () -> assertTrue(Hoover.isInitialPositionValid("1,1,N", room)),
            () -> assertTrue(Hoover.isInitialPositionValid("4      ,2,N           ", room)),
            () -> assertTrue(Hoover.isInitialPositionValid("1,2,s", room))
        );
    }

    @Test
    void checkOrientation(){

    }

    @Test
    void moveForward() {
        final Position initialPosition = new Position(2,2, Orientation.N);
        final Position finalPosition = Hoover.moveForward(initialPosition);
        assertAll(
            () -> assertEquals(finalPosition.getX(), initialPosition.getX()),
            () -> assertEquals(finalPosition.getY(), initialPosition.getY()+1),
            () -> assertEquals(finalPosition.getOrientation(), initialPosition.getOrientation())
        );
    }

    @Test
    void isValidInstructions() {
        assertAll(
            ()-> assertTrue(Hoover.isValidInstructions("agdGAddg G")),
            ()-> assertTrue(Hoover.isValidInstructions("AGG g dAAA"))
        );
    }

    @Test
    void isInValidInstructions() {
        assertAll(
            ()-> assertFalse(Hoover.isValidInstructions("agdGA-ddg G")),
            ()-> assertFalse(Hoover.isValidInstructions("AGG g cdAAA"))
        );
    }

    @Test
    void isNewPositionValid() {
        final Room room = new Room(7,7);
        assertAll(
            ()-> assertTrue(Hoover.isNewPositionValid(new Position(7,1,Orientation.E), room)),
            ()-> assertTrue(Hoover.isNewPositionValid(new Position(6,1,Orientation.E), room)),
            ()-> assertTrue(Hoover.isNewPositionValid(new Position(7,1,Orientation.E), room))
        );
    }

    @Test
    void isNewPositionInValid() {
        final Room room = new Room(7,7);
        assertAll(
            ()-> assertFalse(Hoover.isNewPositionValid(new Position(0,-1,Orientation.E), room)),
            ()-> assertFalse(Hoover.isNewPositionValid(new Position(0,1, Orientation.E), room)),
            ()-> assertFalse(Hoover.isNewPositionValid(new Position(8,1,Orientation.E), room))
        );
    }

    @Test
    void updatePositionForSingleInstruction() {
        final Position position = new Position(0,0,Orientation.N);
        assertAll(
            ()-> assertEquals(Orientation.W, Hoover.updatePosition(position, Instruction.G).getOrientation()),
            ()-> assertEquals(Orientation.E, Hoover.updatePosition(position, Instruction.D).getOrientation()),
            ()-> assertEquals(Orientation.N, Hoover.updatePosition(position, Instruction.A).getOrientation())
        );
    }

    @Test
    void updatePositionForSeveralInstructions() {
        final Position initialPosition = new Position(0,0,Orientation.N);
        Position newPosition = Hoover.updatePosition(initialPosition, Instruction.D);
        newPosition = Hoover.updatePosition(newPosition, Instruction.G);
        newPosition = Hoover.updatePosition(newPosition, Instruction.A);
        newPosition = Hoover.updatePosition(newPosition, Instruction.A);
        newPosition = Hoover.updatePosition(newPosition, Instruction.D);
        assertEquals(0, newPosition.getX());
        assertEquals(2, newPosition.getY());
        assertEquals(Orientation.E, newPosition.getOrientation());
    }

    @Test
    void updateForSeveralInstructions() {
        final Position initialPosition = new Position(5,5,Orientation.N);
        final Position expectedPosition = new Position(5,6,Orientation.N);
        final String instruction = "DADADADAA";
        final Position newPosition = Hoover.updateForSeveralInstructions(new Room(10,10), initialPosition, instruction);
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
        final Position newPosition = Hoover.updateForSeveralInstructions(new Room(7,7), initialPosition, instruction);
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
        final Position newPosition = Hoover.updateForSeveralInstructions(new Room(2,2), initialPosition, instruction);
        assertAll(
                () -> assertEquals(expectedPosition.getX(), newPosition.getX()),
                () -> assertEquals(expectedPosition.getY(), newPosition.getY()),
                () -> assertEquals(expectedPosition.getOrientation(), newPosition.getOrientation())
        );
    }
}