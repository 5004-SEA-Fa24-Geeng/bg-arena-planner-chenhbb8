package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Manages a collection of board game names, allowing users to add, remove, and retrieve games.
 * Supports various ways to add or remove games, including by name, index, and range.
 */

public class GameList implements IGameList {

    /**
     * List to store the game names.
     */
    private Set<String> list;

    /**
     * Delimiter for adding by range.
     */
    private static final String ADD_DELIM = "-";

    /**
     * Constructs a new GameList with an empty set of selected board games.
     */
    public GameList() {
        list = new HashSet<>();
    }

    /**
     * Returns a list of all game names currently in the GameList.
     *
     * @return An unmodifiable list of game names.
     */
    @Override
    public List<String> getGameNames() {
        return List.copyOf(list);
    }

    /**
     * Clears all games from the GameList.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Returns the number of games currently in the GameList.
     *
     * @return The number of games in the list.
     */
    @Override
    public int count() {
        return list.size();
    }

    /**
     * Saves the current list of game names to a file.
     *
     * @param filename The name of the file to save the game list.
     */
    @Override
    public void saveGame(String filename) {
        Path filePath = Path.of(filename);
        try {
            Files.write(filePath, getGameNames());
            System.out.println("File saved!");
        } catch (IOException e) {
            System.out.println("File not saved!");
            e.printStackTrace();
        }
    }

    /**
     * Adds a game to the list based on the given input string.
     * Supports adding games by:
     * - Exact name match
     * - Range (e.g., "1-3" adds games from index 1 to 3)
     * - Index (e.g., "2" adds the second game in the list)
     * - "ALL" adds all games from the filtered stream.
     *
     * @param str      The identifier for the game(s) to be added.
     * @param filtered A stream of board games to select from.
     * @throws IllegalArgumentException If the input is invalid or no games are available.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || filtered == null) {
            throw new IllegalArgumentException();
        }

        str = str.trim();
        List<BoardGame> filteredList = filtered.toList();

        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // Add all games in the list
        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            list.addAll(filteredList.stream().map(BoardGame::getName).toList());
            return;
        }

        // Add by name
        for (BoardGame game : filteredList) {
            if (game.getName().equalsIgnoreCase(str)) {
                list.add(game.getName());
                return; // Stop after adding
            }
        }

        // Add by range
        if (str.contains(ADD_DELIM)) {
            String[] range = str.split(ADD_DELIM);
            if (range.length != 2) {
                throw new IllegalArgumentException();
            }

            try {
                int l = Integer.parseInt(range[0]);  // Start index
                int r = Integer.parseInt(range[1]);  // End index

                if (l > r || l <= 0) {
                    throw new IllegalArgumentException();
                }
                if (l > filteredList.size()) {
                    throw new IllegalArgumentException();
                }
                if (r > filteredList.size()) {
                    r = filteredList.size();
                }

                for (int i = l; i <= r; i++) {
                    list.add(filteredList.get(i - 1).getName());
                }
                return;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }

        // Add by index
        try {
            int idx = Integer.parseInt(str);
            if (idx <= 0 || idx > filteredList.size()) {
                throw new IllegalArgumentException();
            }

            list.add(filteredList.get(idx - 1).getName());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes a game from the list based on the given input string.
     * Supports removing games by:
     * - Exact name match
     * - Range (e.g., "1-3" removes games from index 1 to 3)
     * - Index (e.g., "2" removes the second game in the list)
     * - "ALL" clears the entire list.
     *
     * @param str The identifier for the game(s) to be removed.
     * @throws IllegalArgumentException If the input is invalid or no matching games exist.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        str = str.trim();
        List<String> gameNames = getGameNames();

        // Remove all
        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            clear();
            return;
        }

        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // Remove by name
        for (String name : gameNames) {
            if (name.equalsIgnoreCase(str)) {
                list.remove(name);
                return;
            }
        }

        // Remove by range
        if (str.contains(ADD_DELIM)) {
            String[] range = str.split(ADD_DELIM);
            if (range.length != 2) {
                throw new IllegalArgumentException();
            }

            try {
                int l = Integer.parseInt(range[0]);  // Start index
                int r = Integer.parseInt(range[1]);  // End index

                if (l > r || l <= 0) {
                    throw new IllegalArgumentException();
                }
                if (l > count()) {
                    throw new IllegalArgumentException();
                }
                if (r > count()) {
                    r = count();
                }

                for (int i = l; i <= r; i++) {
                    list.remove(gameNames.get(i - 1));
                }
                return;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }

        // Remove by index
        try {
            int idx = Integer.parseInt(str);
            if (idx <= 0 || idx > count()) {
                throw new IllegalArgumentException();
            }

            list.remove(gameNames.get(idx - 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
