package student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Unit tests for the GameList class, ensuring that all methods function correctly.
 */
public class GameListTest {
    private GameList gameList;
    private Set<BoardGame> games;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    public void setup() {
        gameList = new GameList();
        games = new HashSet<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 3.5, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 2, 2, 6, 30, 120, 2.0, 200, 6.0, 1995));
        games.add(new BoardGame("Catan", 3, 3, 4, 60, 120, 4.0, 50, 8.2, 2002));
    }

    /**
     * Tests the getGameNames method to ensure it retrieves the correct game names.
     */
    @Test
    public void testGetGameNames() {
        gameList.addToList("ALL", games.stream());
        List<String> names = gameList.getGameNames();
        assertEquals(3, names.size(), "Should return 3 game names.");
        assertTrue(names.contains("Chess"), "Chess should be in the list.");
        assertTrue(names.contains("Monopoly"), "Monopoly should be in the list.");
        assertTrue(names.contains("Catan"), "Catan should be in the list.");
    }

    /**
     * Tests the clear method to ensure it removes all games.
     */
    @Test
    public void testClear() {
        gameList.addToList("ALL", games.stream());
        gameList.clear();
        assertEquals(0, gameList.count(), "Game list should be empty after clearing.");
    }

    /**
     * Tests the count method to ensure it returns the correct number of games.
     */
    @Test
    public void testCount() {
        assertEquals(0, gameList.count(), "Initially, count should be 0.");
        gameList.addToList("Chess", games.stream());
        assertEquals(1, gameList.count(), "Count should be 1 after adding a game.");
    }

    /**
     * Tests the saveGame method to ensure it correctly writes game names to a file.
     */
    @Test
    public void testSaveGame() throws IOException {
        String filename = "test_games.txt";
        gameList.addToList("ALL", games.stream());
        gameList.saveGame(filename);

        Path filePath = Path.of(filename);
        assertTrue(Files.exists(filePath), "File should exist after saving.");
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(3, lines.size(), "Saved file should contain 3 game names.");
        assertTrue(lines.contains("Chess"), "Chess should be in the saved file.");
        assertTrue(lines.contains("Monopoly"), "Monopoly should be in the saved file.");
        assertTrue(lines.contains("Catan"), "Catan should be in the saved file.");

        // Cleanup after test
        Files.deleteIfExists(filePath);
    }

    /**
     * Tests the addToList method to ensure games can be added by name.
     */
    @Test
    public void testAddToList() {
        gameList.addToList("Chess", games.stream());
        assertEquals(1, gameList.count(), "Chess should be added.");
        assertTrue(gameList.getGameNames().contains("Chess"), "Chess should exist in the list.");
    }

    /**
     * Tests the removeFromList method to ensure games can be removed by name.
     */
    @Test
    public void testRemoveFromList() {
        gameList.addToList("ALL", games.stream());
        gameList.removeFromList("Chess");
        assertEquals(2, gameList.count(), "Chess should be removed.");
        assertFalse(gameList.getGameNames().contains("Chess"), "Chess should not exist in the list.");
    }
}
