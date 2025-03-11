package student;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The GameList class manages a collection of selected board games.
 * It provides methods to add, remove, count, and retrieve games,
 * as well as saving and clearing the list.
 */

public class GameList implements IGameList {

    /**
     * Constructor for the GameList.
     */
    private Set<BoardGame> selectedGames;

    /**
     * Constructs a new GameList with an empty set of selected board games.
     */
    public GameList() {
        this.selectedGames = new HashSet<>();
    }

    /**
     * Retrieves the names of all selected games in a sorted order.
     *
     * @return A list of game names sorted in case-insensitive order.
     * @throws UnsupportedOperationException if the game list is empty.
     */
    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
        if (selectedGames.isEmpty()) {
            throw new UnsupportedOperationException("No games in the list.");
        }
        return selectedGames.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Clears all games from the list.
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        selectedGames.clear();
    }

    /**
     * Returns the number of games currently in the list.
     *
     * @return The count of games in the list.
     */
    @Override
    public int count() {
        // TODO Auto-generated method stub
        return selectedGames.size();
    }

    /**
     * Saves the current list of game names to a specified file.
     *
     * @param filename The name of the file to save the game list to.
     * @throws UnsupportedOperationException if an I/O error occurs during saving.
     */
    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(filename)))) {
            for (BoardGame game : selectedGames) {
                writer.println(game.getName());
            }
        } catch (IOException e) {
            throw new UnsupportedOperationException("Error saving game list.");
        }
    }

    /**
     * Adds a game to the list if it exists in the provided stream.
     *
     * @param str      The name of the game to add.
     * @param filtered The stream of board games to search for the game.
     * @throws UnsupportedOperationException if the game is not found in the stream.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || filtered == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        List<BoardGame> gameList = filtered.toList();

        if (gameList.isEmpty()) {
            throw new IllegalArgumentException("Game list cannot be empty.");
        }

        // Add all games
        if (str.equalsIgnoreCase("ALL")) {
            selectedGames.addAll(gameList);
            return;
        }

        // Add by index
        try {
            int index = Integer.parseInt(str);
            if (index <= 0 || index > gameList.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }
            selectedGames.add(gameList.get(index - 1));
            return;
        } catch (NumberFormatException ignored) {
            // If not a number, continue to name matching
        }

        // Add by range
        if (str.contains("-")) {
            String[] parts = str.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid range format.");
            }

            try {
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);

                if (start > end || start <= 0 || end > gameList.size()) {
                    throw new IllegalArgumentException("Invalid range.");
                }

                for (int i = start; i <= end; i++) {
                    selectedGames.add(gameList.get(i - 1));
                }
                return;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid range values.");
            }
        }

        // Add by name
        Optional<BoardGame> game = gameList.stream()
                .filter(g -> g.getName().trim().equalsIgnoreCase(str.trim()))
                .findFirst();

        if (game.isPresent()) {
            selectedGames.add(game.get());
        } else {
            throw new IllegalArgumentException("Game not found.");
        }
    }

    /**
     * Removes a game from the list if it exists.
     *
     * @param str The name of the game to remove.
     * @throws UnsupportedOperationException if the game is not found in the list.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        boolean removed = selectedGames.removeIf(game -> game.getName().equalsIgnoreCase(str));
        if (!removed) {
            throw new UnsupportedOperationException("Game not found in the list.");
        }
    }
}
