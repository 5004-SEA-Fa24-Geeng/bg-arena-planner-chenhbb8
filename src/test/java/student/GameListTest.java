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
        gameList.addToList("Chess", games.stream());  // First stream
        gameList.addToList("Catan", games.stream());  // Second stream (fixes issue)
        assertEquals(2, gameList.count());
    }

    @Test
    public void testRemoveFromList() {
        gameList.addToList("Monopoly", games.stream());
        gameList.removeFromList("Monopoly");
        assertEquals(0, gameList.count());
    }
}