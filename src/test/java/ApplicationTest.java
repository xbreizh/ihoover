import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ApplicationTest {


    @Test
    void shouldValidateBoardSizeWhenUsingSpaces() {
        assertTrue(Application.validateBoardSize("3   x 4"));
    }

    @Test
    void shouldValidateBoardWithBothCases() {
        assertAll(
                () -> assertTrue(Application.validateBoardSize("3X4")),
                () -> assertTrue(Application.validateBoardSize("2x2")),
                () -> assertTrue(Application.validateBoardSize("3x4"))
        );
    }

    @Test
    void shouldNotValidateIfNotInteger() {
        assertAll(
                () -> assertFalse(Application.validateBoardSize("asaxa")),
                () -> assertFalse(Application.validateBoardSize("@3X2")),
                () -> assertFalse(Application.validateBoardSize("1.2x2")),
                () -> assertFalse(Application.validateBoardSize("2x9999999999")),
                () -> assertFalse(Application.validateBoardSize("2x-1"))
        );
    }

    @Test
    void testee() {
        System.out.println("3X2".split("X")[1]);
        System.out.println("3X2".split("X")[0]);
    }
}