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
        assertAll(
            () -> assertFalse(Hoover.isInitialPositionValid("1w,2,N", new int[]{7,7})),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2,Y", new int[]{7,7})),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2,N,N", new int[]{7,7})),
            () -> assertFalse(Hoover.isInitialPositionValid("1:2:N:N", new int[]{7,7})),
            () -> assertFalse(Hoover.isInitialPositionValid("1,2777777777777,Y", new int[]{7,7})),
            () -> assertFalse(Hoover.isInitialPositionValid("1,22,N", new int[]{7,7}))
        );
    }

    @Test
    void shouldValidateInitialPosition() {
        assertAll(
                () -> assertTrue(Hoover.isInitialPositionValid("4,2,N", new int[]{7,7})),
                () -> assertTrue(Hoover.isInitialPositionValid("0,0,N", new int[]{1,1})),
                () -> assertTrue(Hoover.isInitialPositionValid("4      ,2,N           ", new int[]{7,7})),
                () -> assertTrue(Hoover.isInitialPositionValid("1,22,N", new int[]{7,77}))
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
                () -> assertEquals(finalPosition.x, initialPosition.x),
                () -> assertEquals(finalPosition.y, initialPosition.y+1),
                () -> assertEquals(finalPosition.orientation, initialPosition.orientation)
        );
    }
}