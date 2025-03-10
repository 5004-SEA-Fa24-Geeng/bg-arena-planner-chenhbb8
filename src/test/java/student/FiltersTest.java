package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.Filters;
import student.GameData;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    private Set<BoardGame> games;

    @BeforeEach
    public void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 3.5, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 2, 2, 6, 30, 120, 2.0, 200, 6.0, 1995));
        games.add(new BoardGame("Catan", 3, 3, 4, 60, 120, 4.0, 50, 8.2, 2002));
    }

    @Test
    public void testFilterByMinPlayers() {
        List<BoardGame> filtered = Filters.applyFilter("minPlayers > 2", games.stream())
                .collect(Collectors.toList());
        assertEquals(1, filtered.size());
        assertEquals("Catan", filtered.get(0).getName());
    }

    @Test
    public void testFilterByRating() {
        List<BoardGame> filtered = Filters.applyFilter("rating >= 7", games.stream())
                .collect(Collectors.toList());
        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(game -> game.getName().equals("Chess")));
        assertTrue(filtered.stream().anyMatch(game -> game.getName().equals("Catan")));
    }
}