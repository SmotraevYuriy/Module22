package medium1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {
    @Test
    void testUserChoice_Valid1() {
        assertEquals(1, ConsoleApp.userChoice("1"));
    }

    @Test
    void testUserChoice_Valid2() {
        assertEquals(2, ConsoleApp.userChoice("2"));
    }

    @Test
    void testUserChoice_Valid3() {
        assertEquals(3, ConsoleApp.userChoice("3"));
    }

    @Test
    void testUserChoice_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice(""));
    }

    @Test
    void testUserChoice_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice(null));
    }

    @Test
    void testUserChoice_NotNumber() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice("abc"));
    }

    @Test
    void testUserChoice_OutOfRangeLow() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice("0"));
    }

    @Test
    void testUserChoice_OutOfRangeHigh() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice("10"));
    }

    @Test
    void testUserChoice_Negative() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice("-5"));
    }

    @Test
    void testUserChoice_SpacesOnly() {
        assertThrows(IllegalArgumentException.class, () -> ConsoleApp.userChoice(" "));
    }
}
