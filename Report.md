# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   ```java
   // your code here
   public class EqualsVsDoubleEquals {
    public static void main(String[] args) {
        String str1 = new String("Hello");
        String str2 = new String("Hello");

        System.out.println(str1 == str2);  // false (different memory locations)
        System.out.println(str1.equals(str2));  // true (same content)

        Integer num1 = 128;
        Integer num2 = 128;
        System.out.println(num1 == num2);  // false (different Integer objects)
        System.out.println(num1.equals(num2));  // true (same integer value)
    }
   }
   ```
== compares object references (memory locations).
.equals() compares the actual content of objects (if properly overridden).



2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

   
      By default, Java’s Collections.sort() and compareTo() are case-sensitive, meaning uppercase letters (e.g., "Banana") come before lowercase letters (e.g., "apple"). To sort a list of strings in a case-insensitive manner, we can use a custom comparator.



3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point.


      The order of conditions in getOperatorFromStr(String str) does matter because some operators contain substrings of other operators. If we check them in the wrong order, we might match an incorrect operation.
Example:
```java
public static Operations getOperatorFromStr(String str) {
    if (str.contains(">")) {
        return Operations.GREATER_THAN;
    } else if (str.contains(">=")) {
        return Operations.GREATER_THAN_EQUALS;
    }
    return null;
}

```
      If the input string is "x >= 5", the first condition (str.contains(">")) matches first, returning Operations.GREATER_THAN.
      
      This is incorrect because "x >= 5" should return Operations.GREATER_THAN_EQUALS.


4. What is the difference between a List and a Set in Java? When would you use one over the other? 

         A List is an ordered collection that allows duplicate elements, meaning items appear in a specific sequence and can be accessed by index. In contrast, a Set is an unordered collection that ensures all elements are unique, preventing duplicates.
         
         A List is useful when maintaining order is important, such as tracking a sequence of user actions. A Set is better when uniqueness is required, like storing a list of registered usernames. Additionally, Set offers faster lookups and removals compared to List, which is optimized for indexed access.


5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 

         A Map in Java is a collection that stores key-value pairs, where each key is unique and maps to a specific value. Unlike a List or Set, a Map allows quick lookups by key, making it efficient for retrieving values without needing to search through the entire collection.
         
         In GamesLoader.java, a Map is used to associate column names with their corresponding indices in the data file. This allows the program to efficiently identify and extract the correct values for each BoardGame property without relying on fixed column positions. Using a Map here improves flexibility, ensuring that even if the column order changes in the file, the correct data can still be retrieved by referencing the column names instead of hardcoded indices.


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

         An enum in Java is a special data type used to define a fixed set of constants. Unlike regular classes, enums provide a type-safe way to represent predefined values, making the code more readable and maintainable. Enums can also have additional properties and methods, allowing them to store extra information beyond just their names.
         
         In GameData.java, we use an enum to map column names to their corresponding data attributes. This helps ensure that column names are referenced consistently throughout the program, reducing the risk of typos or mismatches. By using an enum instead of hardcoded strings, we make the code more robust, improving maintainability and flexibility when handling game data





7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
   if (ct == CMD_QUESTION || ct == CMD_HELP) {
    processHelp();
   } else if (ct == INVALID) {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   } else {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   }

    // your code here, don't forget the class name that is dropped in the switch block..
    
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
******* NI HAO. *******

ZHE GE GONG JU BANG ZHU REN MEN ZHAO YOU XI

To get started, enter your first command below, or type ? or help for command options.

```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

```text
Localization is the process of adapting software for different languages and cultures.  While expanding to more users is a key reason, localization also makes a program easier to use by ensuring that text, symbols, and formats feel natural to the audience.  This includes translating words, changing date formats, adjusting currency symbols, and even modifying content to fit cultural expectations.

If a program is not properly localized, it can cause confusion or even offend users.  For example, a poorly translated message might change the meaning of a sentence, making the software harder to understand.  Some companies have faced issues when product names or slogans had unintended meanings in other languages.  Mistakes in localization can hurt a brand’s reputation and lead to a loss of users.

To avoid these problems, developers should test their programs in different languages before release.  Using tools like pseudolocalization—where text is replaced with longer or special characters—helps catch layout issues early.  Involving native speakers in the translation process ensures that the meaning stays correct.  By planning for localization from the start, developers can create software that works well for people all over the world.
```