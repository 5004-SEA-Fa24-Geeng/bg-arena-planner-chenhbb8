package student;

import java.util.Set;
import java.util.stream.Stream;

/**
 * The Planner class manages the filtering and sorting of board games.
 * It applies various filtering and sorting strategies to return a customized game list.
 */
public class Planner implements IPlanner {
    private Set<BoardGame> masterList;
    private Stream<BoardGame> filteredGames;

    /**
     * Constructs a Planner with a given set of board games.
     *
     * @param games The set of board games to be managed.
     */
    public Planner(Set<BoardGame> games) {
        this.masterList = games;
        this.filteredGames = games.stream();
    }

    /**
     * Filters the list of games based on the specified criteria.
     *
     * @param filter The filter criteria (e.g., "rating >= 7").
     * @return A stream of board games that match the filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        filteredGames = Filters.applyFilter(filter, masterList.stream());
        return filteredGames;
    }

    /**
     * Filters and sorts the list of games based on the specified criteria and sorting attribute.
     *
     * @param filter The filter criteria (e.g., "rating >= 7").
     * @param sortOn The attribute to sort by (e.g., "year").
     * @return A stream of board games that match the filter and are sorted by the given attribute.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Filters and sorts the list of games based on the specified criteria, sorting attribute, and order.
     *
     * @param filter The filter criteria (e.g., "rating >= 7").
     * @param sortOn The attribute to sort by (e.g., "year").
     * @param ascending True if sorting should be in ascending order, false otherwise.
     * @return A stream of board games that match the filter and are sorted in the specified order.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        Stream<BoardGame> filteredStream = Filters.applyFilter(filter, masterList.stream());
        return ascending ? filteredStream.sorted(Sorts.getComparator(sortOn))
                : filteredStream.sorted(Sorts.getComparator(sortOn).reversed());
    }

    /**
     * Resets the filtered game list to the full master list.
     */
    @Override
    public void reset() {
        filteredGames = masterList.stream();
    }
}
