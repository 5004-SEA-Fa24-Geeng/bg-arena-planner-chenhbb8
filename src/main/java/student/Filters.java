package student;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The Filters class provides methods to filter board games based on user-specified criteria.
 * It applies filtering strategies dynamically based on provided conditions.
 */
public final class Filters {

    /**
     * Private constructor to prevent instantiation.
     */
    private Filters() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
    /**
     * Applies a filter to a stream of board games based on the given criteria.
     *
     * @param filter The filter condition as a string (e.g., "rating >= 7").
     * @param games The stream of board games to filter.
     * @return A filtered stream of board games that match the given condition.
     */
    public static Stream<BoardGame> applyFilter(String filter, Stream<BoardGame> games) {
        if (filter == null || filter.isEmpty()) {
            return games;
        }

        String[] conditions = filter.split(",");
        for (String condition : conditions) {
            condition = condition.trim();
            Predicate<BoardGame> predicate = createPredicate(condition);
            if (predicate != null) {
                games = games.filter(predicate);
            }
        }

        return games;
    }

    /**
     * Creates a predicate for filtering board games based on a given condition.
     *
     * @param condition The filtering condition as a string.
     * @return A predicate that evaluates whether a board game meets the condition.
     */
    private static Predicate<BoardGame> createPredicate(String condition) {
        Operations operator = Operations.getOperatorFromStr(condition);
        if (operator == null) {
            return null;
        }

        String[] parts = condition.split(operator.getOperator());
        if (parts.length != 2) {
            return null;
        }

        String attribute = parts[0].trim();
        String value = parts[1].trim();

        GameData column;
        try {
            column = GameData.fromString(attribute);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return createGamePredicate(column, operator, value);
    }

    /**
     * Creates a predicate for filtering board games based on a specific attribute, operator, and value.
     *
     * @param column The attribute to filter on.
     * @param operator The comparison operator.
     * @param value The value to compare against.
     * @return A predicate for filtering board games.
     */
    private static Predicate<BoardGame> createGamePredicate(GameData column, Operations operator, String value) {
        switch (column) {
            case MIN_PLAYERS:
                return game -> compare(game.getMinPlayers(), Integer.parseInt(value), operator);
            case MAX_PLAYERS:
                return game -> compare(game.getMaxPlayers(), Integer.parseInt(value), operator);
            case RATING:
                return game -> compare(game.getRating(), Double.parseDouble(value), operator);
            case DIFFICULTY:
                return game -> compare(game.getDifficulty(), Double.parseDouble(value), operator);
            case YEAR:
                return game -> compare(game.getYearPublished(), Integer.parseInt(value), operator);
            case NAME:
                return game -> compareString(game.getName(), value, operator);
            default:
                return null;
        }
    }

    /**
     * Compares integer values based on the given operator.
     *
     * @param gameValue The board game's attribute value.
     * @param filterValue The filter value to compare against.
     * @param operator The comparison operator.
     * @return True if the comparison is valid, false otherwise.
     */
    private static boolean compare(int gameValue, int filterValue, Operations operator) {
        switch (operator) {
            case GREATER_THAN:
                return gameValue > filterValue;
            case LESS_THAN:
                return gameValue < filterValue;
            case GREATER_THAN_EQUALS:
                return gameValue >= filterValue;
            case LESS_THAN_EQUALS:
                return gameValue <= filterValue;
            case EQUALS:
                return gameValue == filterValue;
            case NOT_EQUALS:
                return gameValue != filterValue;
            default:
                return false;
        }
    }

    /**
     * Compares double values based on the given operator.
     *
     * @param gameValue The board game's attribute value.
     * @param filterValue The filter value to compare against.
     * @param operator The comparison operator.
     * @return True if the comparison is valid, false otherwise.
     */
    private static boolean compare(double gameValue, double filterValue, Operations operator) {
        switch (operator) {
            case GREATER_THAN:
                return gameValue > filterValue;
            case LESS_THAN:
                return gameValue < filterValue;
            case GREATER_THAN_EQUALS:
                return gameValue >= filterValue;
            case LESS_THAN_EQUALS:
                return gameValue <= filterValue;
            case EQUALS:
                return gameValue == filterValue;
            case NOT_EQUALS:
                return gameValue != filterValue;
            default:
                return false;
        }
    }

    /**
     * Compares string values based on the given operator.
     *
     * @param gameValue The board game's attribute value.
     * @param filterValue The filter value to compare against.
     * @param operator The comparison operator.
     * @return True if the comparison is valid, false otherwise.
     */
    private static boolean compareString(String gameValue, String filterValue, Operations operator) {
        switch (operator) {
            case EQUALS:
                return gameValue.equalsIgnoreCase(filterValue);
            case NOT_EQUALS:
                return !gameValue.equalsIgnoreCase(filterValue);
            case CONTAINS:
                return gameValue.toLowerCase().contains(filterValue.toLowerCase());
            default:
                return false;
        }
    }
}
