package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import student.GameData;
import student.Sorts;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class SortsTest {
    private List<BoardGame> games;

    @BeforeEach
    public void setup() {
        games = new ArrayList<>();
        games.add(new BoardGame("Chess", 1, 2, 2, 10, 20, 3.5, 100, 7.5, 2000));
        games.add(new BoardGame("Monopoly", 2, 2, 6, 30, 120, 2.0, 200, 6.0, 1995));
        games.add(new BoardGame("Catan", 3, 3, 4, 60, 120, 4.0, 50, 8.2, 2002));
    }

    @Test
    public void testSortByRatingAscending() {
        Sorts.sortBy(games, GameData.RATING, true);
        assertEquals("Monopoly", games.get(0).getName());
        assertEquals("Chess", games.get(1).getName());
        assertEquals("Catan", games.get(2).getName());
    }

    @Test
    public void testSortByYearDescending() {
        Sorts.sortBy(games, GameData.YEAR, false);
        assertEquals("Catan", games.get(0).getName());
        assertEquals("Chess", games.get(1).getName());
        assertEquals("Monopoly", games.get(2).getName());
    }
}