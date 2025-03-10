import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.Filters;
import student.Sorts;
import student.GameData;
import student.Planner;
import student.IPlanner;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


/**
 * JUnit test for the Planner class.
 * <p>
 * Just a sample test to get you started, also using
 * setup to help out.
 */
public class TestPlanner {
    static Set<BoardGame> games;
    private IPlanner planner;

    @BeforeAll
    public static void setupClass() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @BeforeEach
    public void setup() {
        planner = new Planner(games);
    }

    @Test
    public void testFilterByName() {
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
    }

    @Test
    public void testFilterByMinPlayers() {
        List<BoardGame> filtered = planner.filter("minPlayers > 2").collect(Collectors.toList());
        assertEquals(3, filtered.size());
        assertTrue(filtered.stream().anyMatch(game -> game.getName().equals("GoRami")));
        assertTrue(filtered.stream().anyMatch(game -> game.getName().equals("Monopoly")));
        assertTrue(filtered.stream().anyMatch(game -> game.getName().equals("Tucano")));
    }

    @Test
    public void testFilterByRatingAndSortByYear() {
        List<BoardGame> filtered = planner.filter("rating >= 7", GameData.YEAR, true)
                .collect(Collectors.toList());
        assertEquals(6, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
        assertEquals("GoRami", filtered.get(1).getName());
    }

    @Test
    public void testResetFilters() {
        planner.filter("minPlayers > 2");
        planner.reset();
        List<BoardGame> filtered = planner.filter("", GameData.NAME, true).collect(Collectors.toList());
        assertEquals(8, filtered.size());
    }


}