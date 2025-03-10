package student;

import java.util.Comparator;
import java.util.List;

/**
 * The Sorts class provides methods to sort board games based on various attributes.
 * It applies different sorting strategies dynamically based on user input.
 */
public class Sorts {

    /**
     * Private constructor to prevent instantiation
     */
    private Sorts() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
    /**
     * Sorts a list of board games based on the specified attribute and order.
     *
     * @param games The list of board games to be sorted.
     * @param attribute The attribute to sort by (e.g., rating, difficulty, year, players, name).
     * @param ascending True if sorting should be in ascending order, false for descending order.
     */
    public static void sortBy(List<BoardGame> games, GameData attribute, boolean ascending) {
        Comparator<BoardGame> comparator = createComparator(attribute);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        games.sort(comparator);
    }

    /**
     * Retrieves a comparator for sorting board games based on the given attribute.
     *
     * @param attribute The attribute to sort by.
     * @return A comparator for sorting board games.
     */
    public static Comparator<BoardGame> getComparator(GameData attribute) {
        return createComparator(attribute);
    }

    /**
     * Creates a comparator for sorting board games based on the specified attribute.
     *
     * @param attribute The attribute to sort by.
     * @return A comparator for sorting board games according to the specified attribute.
     * @throws IllegalArgumentException if the sorting attribute is not supported.
     */
    public static Comparator<BoardGame> createComparator(GameData attribute) {
        switch (attribute) {
            case RATING:
                return Comparator.comparingDouble(BoardGame::getRating);
            case DIFFICULTY:
                return Comparator.comparingDouble(BoardGame::getDifficulty);
            case YEAR:
                return Comparator.comparingInt(BoardGame::getYearPublished);
            case MIN_PLAYERS:
                return Comparator.comparingInt(BoardGame::getMinPlayers);
            case MAX_PLAYERS:
                return Comparator.comparingInt(BoardGame::getMaxPlayers);
            case NAME:
                return Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
            default:
                throw new IllegalArgumentException("Sorting by this attribute is not supported.");
        }
    }

}
