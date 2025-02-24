# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    class IGameList {
        +getGameNames()
        +clear()
        +count()
        +saveGame()
        +addToList()
        +removeFromList()
    }
    class IPlanner {
        +filter()
        +reset()
    }
    class BoardGame {
        -name: String
        -id: int
        -minPlayers: int
        -maxPlayers: int
        -maxPlayTime: int
        -minPlayTime: int
        -difficulty: double
        -rank: int
        -averageRating: double
        -yearPublished: int
        +Getters()
    }
    class GameData {
        +fromColumnName()
    }
    class Operations {
        +getOperator()
    }
    class GamesLoader {
        +loadGamesFile()
    }
    class ConsoleApp {
        +start()
    }
    class BGArenaPlanner {
        +main()
    }

    IGameList <|-- GameList
    IPlanner <|-- Planner
    GamesLoader --> BoardGame
    Planner --> BoardGame
    GameList --> BoardGame
    ConsoleApp --> IGameList
    ConsoleApp --> IPlanner
    BGArenaPlanner --> ConsoleApp
    BGArenaPlanner --> Planner
    BGArenaPlanner --> GameList
```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 
```mermaid
classDiagram
    class GameList {
        -selectedGames: Set<BoardGame>
        +getGameNames()
        +clear()
        +count()
        +saveGame()
        +addToList()
        +removeFromList()
    }
```
```mermaid
classDiagram
    class Planner {
        -masterList: Set<BoardGame>
        -filteredGames: Set<BoardGame>
        +filter()
        +reset()
    }
```



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

### Planner Tests
1. Filtering by name (name == "Go").
2. Filtering by minimum players (minPlayers > 2).
3. Filtering by difficulty (difficulty <= 6.5).
4. Filtering with multiple conditions (minPlayers > 2, maxPlayers < 5).
5. Resetting filters (reset()).

### GameList Tests
1. Adding one game by name.

2. Adding multiple games using a range (e.g., 1-3).

3. Removing a game.

4. Ensuring no duplicate games.

5. Saving and loading the list from a file.



## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
