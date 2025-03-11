package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class GameList implements IGameList {
    /** List to store the game names.*/
    private Set<String> list;

    /** Delimiter for adding by range. */
    private static final String ADD_DELIM = "-";

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        list = new HashSet<>();
    }

    @Override
    public List<String> getGameNames() {
        return list.stream()
                .sorted(String.CASE_INSENSITIVE_ORDER.thenComparing(String::compareTo)) // ✅ Ensures correct sorting order
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int count() {
        return list.size();
    }

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

        // add  by string - all
        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            list.addAll(filteredList.stream().map(BoardGame::getName).toList());
            return;
        }

        // add by string - name
        for (BoardGame game : filteredList) {
            if (game.getName().equalsIgnoreCase(str)) {
                list.add(game.getName());
                return; // added
            }
        }

        // add by number - range
        if (str.contains(ADD_DELIM)) {
            String[] range = str.split(ADD_DELIM);
            if (range.length != 2) {
                throw new IllegalArgumentException();
            }

            try {
                int l = Integer.parseInt(range[0]);  // start index
                int r = Integer.parseInt(range[1]);  // end index

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

        // add by number - index
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

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        str = str.trim();
        List<String> gameNames = getGameNames();

        //remove by string - all
        if (str.equalsIgnoreCase(IGameList.ADD_ALL)) {
            clear();
            return;
        }

        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // remove by string - single name
        for (String name : gameNames) {
            if (name.equalsIgnoreCase(str)) {
                list.remove(name);
                return;
            }
        }

        // remove by number - range
        if (str.contains(ADD_DELIM)) {
            String[] range = str.split(ADD_DELIM);
            if (range.length != 2) {
                throw new IllegalArgumentException();
            }

            try {
                int l = Integer.parseInt(range[0]);  // start index
                int r = Integer.parseInt(range[1]);  // end index

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

        // remove by number - index
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
