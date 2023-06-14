import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HooverTest {


    @Test
    void shouldValidateBoard() {
        assertAll(
                () -> assertTrue(Hoover.validateRoomSize("3X4")),
                () -> assertTrue(Hoover.validateRoomSize("2 x 30")),
                () -> assertTrue(Hoover.validateRoomSize("111111111X333333333"))
        );
    }

    @Test
    void shouldNotValidateIfNotInteger() {
        assertAll(
                () -> assertFalse(Hoover.validateRoomSize("asaxa")),
                () -> assertFalse(Hoover.validateRoomSize("@3X2")),
                () -> assertFalse(Hoover.validateRoomSize("1.2x2")),
                () -> assertFalse(Hoover.validateRoomSize("2x9999999999")),
                () -> assertFalse(Hoover.validateRoomSize("0x9999999999")),
                () -> assertFalse(Hoover.validateRoomSize("0x0")),
                () -> assertFalse(Hoover.validateRoomSize("2x-1"))
        );
    }

}