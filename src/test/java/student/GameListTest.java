package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.GameList;
import student.IGameList;

import java.util.List;
import java.util.stream.Stream;
import java.util.Set;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameListTest {
    private IGameList gameList;
    private Set<BoardGame> games;

    @BeforeEach
    public void setup() {
        gameList = new GameList();
        games = new HashSet<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 3.5, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 2, 2, 6, 30, 120, 2.0, 200, 6.0, 1995));
        games.add(new BoardGame("Catan", 3, 3, 4, 60, 120, 4.0, 50, 8.2, 2002));
    }

    @Test
    public void testGetGameNames() {
        gameList.addToList("Catan", games.stream());
        gameList.addToList("Chess", games.stream());
        gameList.addToList("Monopoly", games.stream());
        List<String> names = gameList.getGameNames();
        assertEquals(List.of("Catan", "Chess", "Monopoly"), names);
    }

    @Test
    public void testClear() {
        gameList.addToList("Catan", games.stream());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    public void testCount() {
        assertEquals(0, gameList.count());
        gameList.addToList("Chess", games.stream());
        assertEquals(1, gameList.count());
    }

    @Test
    public void testSaveGame() throws Exception {
        gameList.addToList("Catan", games.stream());
        gameList.addToList("Chess", games.stream());
        gameList.saveGame("test_save.txt");

        List<String> lines = Files.readAllLines(Paths.get("test_save.txt"));
        assertTrue(lines.contains("Catan"));
        assertTrue(lines.contains("Chess"));
    }

    @Test
    public void testAddToList() {
        // Convert the stream to a list for reuse
        List<BoardGame> gameListCopy = games.stream().collect(Collectors.toList());

        // Test adding a valid game
        gameList.addToList("Chess", gameListCopy.stream());
        assertEquals(1, gameList.count(), "Chess should be added.");

        // Test adding another valid game
        gameList.addToList("Monopoly", gameListCopy.stream());
        assertEquals(2, gameList.count(), "Monopoly should be added.");

        // Test adding a game that does not exist
        gameList.addToList("Catan", gameListCopy.stream());
        assertEquals(3, gameList.count(), "Catan should NOT be added.");
    }


    @Test
    public void testRemoveFromList() {
        gameList.addToList("Monopoly", games.stream());
        gameList.removeFromList("Monopoly");
        assertEquals(0, gameList.count());
    }

    /**
     * Tests adding a game by name.
     */
    @Test
    public void testAddToListByName() {
        gameList.addToList("Catan", games.stream());
        assertEquals(1, gameList.count());
        assertEquals("Catan", gameList.getGameNames().get(0));

        // Ensure an exception is thrown when adding a non-existent game
        assertThrows(IllegalArgumentException.class, () -> gameList.addToList("Tucano", games.stream()));
        System.out.println(gameList.getGameNames());
    }

    /**
     * Tests adding a single game by index.
     */
    @Test
    public void testAddSingleGameToListByIndex() {
        gameList.addToList("1", games.stream());
        assertEquals(1, gameList.count());
        System.out.println(gameList.getGameNames());
    }

    /**
     * Tests adding games using a range.
     */
    @Test
    public void testAddGameByRange() {
        gameList.addToList("1-2", games.stream());
        assertEquals(2, gameList.count());

        gameList.addToList("3-3", games.stream());
        assertEquals(3, gameList.count());

        gameList.addToList("1-10", games.stream()); // Only 3 games available
        assertEquals(3, gameList.count());
    }

    /**
     * Tests adding all games.
     */
    @Test
    public void testAddAll() {
        gameList.addToList("ALL", games.stream());
        assertEquals(3, gameList.count());
        System.out.println(gameList.getGameNames());
    }
}