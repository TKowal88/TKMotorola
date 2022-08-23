import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Character;
import java.util.regex.Pattern;
import java.lang.StringIndexOutOfBoundsException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

// Top level Game class
public class Game {

    // Class Load to save the text file with words into an array
    public static class Load {
        String[] wordArray = new String[100];
        // Constructor method
        public Load() {
            try {
            File words = new File("Words.txt");
            Scanner input = new Scanner(words);
            int i = 0;
            while (input.hasNextLine()) {
                wordArray[i] = input.nextLine();
                i++;
            }            
            input.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error");
            }              
        }
    }

    // Level class to obtain user input to set the game difficulty
    public static class Level {
        int difficulty = 0;
        // Level constructor
        public Level () {
        Scanner digit = new Scanner(System.in);
        System.out.println("Choose your difficulty level\n Press 1 for EASY\n Press 2 for HARD");
        try {
            difficulty = digit.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\n \nInput numbers only\n");
        }        
        }
    }

    // Class Gameplay defines game variables, that are set and modified by the class methods
    public static class Gameplay {

        // Two subarrays of randomized words
        String[] rowA;
        String[] rowB;

        // Two subarrays to be displayed, initially X's, with words from rowA and rowB replacing the X's
        String[] rowXA;
        String[] rowXB;

        // An Array of column numbers, 1-4 or 1-8
        int [] columns;

        // Difficulty level of the game, "easy" or "hard"
        String level;

        // Chances counter
        int chances;

        // Chances used counter
        int chancesUsed;

        // Matches counter
        int matches;

        // Variable of possible input options, A-B, A or B
        String options;  
        
        // Number of moves executed
        int moves;
        
        // Size of the game rows 4 (easy) or 8 (hard)
        int size;

        // An Instant object that will be used to demarcate the beginning of the game
        Instant beginning;

        // The name of the player
        String player;

        // The seconds of game elapsed
        long seconds;

        // The constructor obtains the words in a randomized order and sets up the subarrays of level-dependent size
        public Gameplay(String[] array, int gamesize){
            size = gamesize;
            rowA = new String[size];
            rowB = new String[size];
            rowXA = new String[size];
            rowXB = new String[size];
            columns = new int[size];

            // Fills the display subarrays with X's
            for (int i = 0; i < size; i++) {
                rowXA[i] = "X";  
                rowXB[i] = "X";
                columns[i] = i + 1;
            } 

            // Two ArrayLists are used to get randomized, unique, integer collections          
            ArrayList<Integer> list100 = new ArrayList<Integer>();
            ArrayList<Integer> list = new ArrayList<Integer>();          
            for (int i = 0; i < 100; i++) {
                list100.add(i);
            }
            for (int i = 0; i < size; i++) {
                list.add(i);
            }
            Collections.shuffle(list100);
            Collections.shuffle(list);

            // The ArrayLists are used as index numbers to randomly assign words to a sub array and
            // make a shuffled copy of the sub array
            for (int i = 0; i < size; i++) {
                rowA[i] = array[list100.get(i)];
            }
            for (int i = 0; i < size; i++) {
                rowB[i] = rowA[list.get(i)];                
            }            
            }

            // Method prints a separator line
            public static void printLine() {
                for (int i = 0; i < 30; i++) {
                    System.out.print("-");
                }
                System.out.println();
            } 
            
            // Method print displays the game board, including difficulty and remaining guess chances
            public void print() {                
                printLine();
                System.out.println("Level: " + level);
                System.out.println("Guess chances: " + chances);
                System.out.print("  ");

                // Prints columns, adjusting the whitespace before the numbers to match the length of 
                // the words in subarray XA
                for (int i = 0; i < size; i++){
                    int length = rowXA[i].length() + 1;
                    System.out.printf("%" + -length + "d", columns[i]);                   
                }
                System.out.println();

                // Prints the subarray XA, preceded by letter A
                System.out.print("A ");
                for (String word : rowXA) {
                    System.out.print(word + " ");                    
                }
                System.out.println();

                // Prints the subarray XB, preceded by letter B
                System.out.print("B ");
                for (String word : rowXB) {
                    System.out.print(word + " ");                    
                }
                System.out.println();
                printLine();
                }

                // This method reads the user answer, validates whether it refers to a covered word,
                // and returns 3 possible status codes. 1 to keep playing, 0 for game win and 2 for game over,
                // based on how many succesful matches have been made and how many chances remaining does the user have
                public int play() {
                
                // User answer    
                String answer = enterAnswer();

                // A Boolean to validate user input
                boolean validate = false;
                char row = answer.charAt(0);
                int column = Character.getNumericValue(answer.charAt(1));

                while (!validate) {     

                // Checks whether the input is for A or B and whether it doesn't refer to an 
                // uncovered word, then updates the options and validate variable     
                if (row == 'A' && rowXA[column - 1] == "X") {   
                    rowXA[column - 1] = rowA[column - 1];
                    options = "B";
                    validate = true;

                } else if (row == 'B' && rowXB[column - 1] == "X") {
                    rowXB[column - 1] = rowB[column - 1];
                    options = "A";
                    validate = true;

                } else {
                    System.out.println("Invalid input");
                    validate = false;
                    answer = enterAnswer();
                    row = answer.charAt(0);
                    column = Character.getNumericValue(answer.charAt(1));
                }
                }

                // The moves variable assures that the answer is only checked after an even number of entries
                moves++;

                if (moves % 2 != 0) {
                    return 1; 

                // Checks whether the answer is correct, calls the match method if so
                } else if (options == "B") {
                    for (String word : rowXB) {
                        if (rowXA[column - 1] == word) { 
                            return match();                                                 
                        }
                    }

                    // If incorrect calls the noMatch method              
                    noMatch();

                    // If no more chances, exits the game
                    if (chances == 0) {
                        System.out.println("Game Over");
                        return 2;
                    }

                // Checks for correct answer if options == A
                } else {                   
                    for (String word : rowXA) {
                        if (rowXB[column - 1] == word) {
                            return match();
                        }
                    }                   
                    noMatch();
                    if (chances == 0) {
                        System.out.println("Game Over");
                        return 2;
                    }                     
                }
                return 1;                           
            }

            // This method prompts for an answer with the available options (A-B, A or B) then checks
            // whether the input matches the row letter and does not exceed the column count
            public String enterAnswer() {
                System.out.println();
                Scanner guess = new Scanner(System.in);
                        try {
                            System.out.println("Enter " + options.charAt(0) + " or " + options.charAt(2) + " coordinates: ");
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Enter " + options + " coordinates: ");
                        }
                         String answer = guess.next().toUpperCase();
                         boolean b = Pattern.matches("["+ options +"][1-" + size + "]", answer);

                         // Reprompts for input                      
                        if (!b) {
                            System.out.println("Invalid input");
                            answer = enterAnswer();
                        }
                        return answer;            
            }

            // This method increments the match counter and checks for winning condition,
            // then returns the status code back to play method        
            public int match() {
                matches++;
                options = "A-B";                           
                if (matches == size) {

                    // Marks the end of the game
                    Instant end = Instant.now();
                    clearConsole();
                    print();                                     
                    System.out.println("Congratulations. You win!");
                    
                    // Displays the amount of moves taken, chances used
                    System.out.println("You solved the memory game in " + moves + " moves.");
                    if (chancesUsed == 1) {
                        System.out.println("You used " + chancesUsed + " chance.");
                    }
                    else {
                        System.out.println("You used " + chancesUsed + " chances.");
                    }

                    // Gets the game duration and displays it
                    seconds = Duration.between(beginning, end).getSeconds();
                    System.out.println("It took you " + seconds + " seconds to solve the puzzle.");

                    // Prompts the user for a name to enter into high score
                    Scanner name = new Scanner(System.in);
                    System.out.println("Enter your name to save the score: ");
                    if (name.hasNext()) {
                        player = name.nextLine();
                        enterHighscore();
                    }
                    return 0;
                }
                return 1; 
            }

            // This method decrements the chance counter, resets the match counter, briefly shows the
            // wrong answer, then resets the display sub-arrays back to cover all of the words
            public void noMatch() {
                System.out.println("no match");
                options = "A-B";
                chances--;
                chancesUsed++;
                matches = 0;
                System.out.println(chances + "chances");
                clearConsole();                   
                print();
                if (chances != 0) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                    }
                    for (int i = 0; i < size; i++) {
                        clearConsole();
                        rowXA[i] = "X";
                        rowXB[i] = "X";
                    }
                    }
                }

            // This method clears the display between game rounds    
            public void clearConsole() {
                try {
                    if (System.getProperty("os.name").contains("Windows")) {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    } else {
                        System.out.print("\033\143");
                    }
                    } catch (IOException | InterruptedException ex) {}
                }
            
            // This method combines the data into a comma-separated string, 
            // that is then appended to the high score csv file
            public void enterHighscore () {
                String localDate = LocalDate.now().toString();
                String entry = player + "," + localDate + "," + seconds + " seconds," + moves + " moves.";
                try {
                FileWriter highscoreWriter = new FileWriter("highscore.csv", true);
                highscoreWriter.write(entry); 
                highscoreWriter.write(System.lineSeparator());             
                highscoreWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occured");
                }
            }
            // The complete csv file is read into an ArrayList, then sorted by time,
            // then the top 10 scores are displayed
            public void displayHighscore () {
                clearConsole();
                ArrayList<Winner> winners = new ArrayList<Winner>();
                try {
                File highscoreReader = new File("highscore.csv");
                Scanner reader = new Scanner(highscoreReader);               
                while (reader.hasNextLine()) {
                    String fields = reader.nextLine();
                    String [] splitFields = fields.split(",");
                    String nameField = splitFields[0];
                    String dateField = splitFields[1];
                    String timeField = splitFields[2];
                    String movesField = splitFields[3];
                    winners.add(new Winner(nameField, dateField, timeField, movesField));                                       
                }
                winners.sort((o1, o2) -> o1.time.compareTo(o2.time));
                String[] headers = { "Name", "Date", "Time", "Moves" };           
                System.out.println("High Scores\n");                                            
                for (int i = 0; i < 4; i++) {
                System.out.printf("%8s", headers[i]);
                }
                System.out.println();
                System.out.println();
                for (int i = 0; i < 10; i++) {
                    try {
                    System.out.print(winners.get(i).name + " ");
                    System.out.print(winners.get(i).date + " ");
                    System.out.print(winners.get(i).time + " ");
                    System.out.println(winners.get(i).moves);
                    System.out.println();
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
                } catch (FileNotFoundException e) {
                    System.out.println("Error reading the file");
                }
            }
        }

        // This is a class that is used to create Winner objects that are then loaded into
        // the ArrayList by the displayHighscore function, and used to print the High Score list
        public static class Winner {
            String name;
            String date;
            String time;
            String moves;

            public Winner(String nameIn, String dateIn, String timeIn, String movesIn) {
                name = nameIn;
                date = dateIn;
                time = timeIn;
                moves = movesIn;
            };

        }
       
    public static void main(String[] args) {
        // Char restart is a condition in the do-while loop that is changed based on user input
        char restart = 'N';
        do {

            // Initial message
            System.out.println("Welcome to the game!");

            // Loading the word array
            Load data = new Load(); 

            // Getting the game level from the user
            Level level = new Level();

            // Reprompts for correct level input
            while (level.difficulty != 1 && level.difficulty != 2) {
                System.out.println("\nEnter 1 or 2 only\n");
                level = new Level();
            }
            
            // Sets up the new game by sending in the wordArray, size of the game (4 or 8) to the constructor
            // and sets level name and chances counter
            Gameplay game;
            if (level.difficulty == 1) {
                game = new Gameplay(data.wordArray, 4);
                game.level = "easy";
                game.chances = 10;           
            } else {           
                game = new Gameplay(data.wordArray, 8);
                game.level = "hard";
                game.chances = 15;
            }                      
            game.options = "A-B";

            // Clears the console, begins gameplay
            game.clearConsole();

            // Marks the beginning of the game
            game.beginning = Instant.now();
            game.print();    
            int status = game.play();

            // Repeats the gameplay if status returned is 1                        
            while (status == 1) {
                game.clearConsole();
                game.print();
                status = game.play();                
                }

            // Ends the game, prompts for game restart
            if (status == 0 || status == 2) {
                game.displayHighscore();
                Scanner input = new Scanner(System.in);
                System.out.println("Do you want to play again? Y/N: ");
                restart = input.next().charAt(0);
                game.clearConsole();
            }           
        } while (restart == 'Y' || restart == 'y'); 
    }     
} 
