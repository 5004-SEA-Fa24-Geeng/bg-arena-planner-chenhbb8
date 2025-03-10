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
     * @param str The name of the game to add.
     * @param filtered The stream of board games to search for the game.
     * @throws UnsupportedOperationException if the game is not found in the stream.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        if (filtered == null) {
            throw new UnsupportedOperationException("Provided game stream is null.");
        }

        Optional<BoardGame> game = filtered
                .filter(g -> g.getName().equalsIgnoreCase(str))
                .findFirst();

        if (game.isPresent()) {
            selectedGames.add(game.get());
        } else {
            throw new UnsupportedOperationException("Game not found in the provided stream.");
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
