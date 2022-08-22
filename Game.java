import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Character;
import java.util.regex.Pattern;
import java.lang.StringIndexOutOfBoundsException; 

public class Game {
    public static class Load {
        String[] wordArray = new String[100];
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
    public static class Level {
        int difficulty = 0;
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
    public static class Game1 {
        String[] rowA = new String[4];
        String[] rowB = new String[4];
        String[] rowXA = {"X", "X", "X", "X"};
        String[] rowXB = {"X", "X", "X", "X"};
        int [] columns = {1, 2, 3, 4};
        String level = "easy";
        static int chances = 0;
        static int matches = 0;
        static String options;  
        static int moves;
        public Game1(String[] array){
            ArrayList<Integer> list100 = new ArrayList<Integer>();
            ArrayList<Integer> list4 = new ArrayList<Integer>();          

            for (int i = 0; i < 100; i++) {
                list100.add(i);
            }

            for (int i = 0; i < 4; i++) {
                list4.add(i);
            }
            Collections.shuffle(list100);
            Collections.shuffle(list4);

            for (int i = 0; i < 4; i++) {
                rowA[i] = array[list100.get(i)];
            }
            for (int i = 0; i < 4; i++) {
                rowB[i] = rowA[list4.get(i)];                
            }  
            }
            public static void printLine() {
                for (int i = 0; i < 30; i++) {
                    System.out.print("-");
                }
                System.out.println();
            }           
            public int play(String options, int moves, int chances, int matches) {
                
                
                Game1.printLine();
                System.out.println("Level: " + level);
                System.out.println("Guess chances: " + chances);
                System.out.print("  ");
                for (int i = 0; i < 4; i++){
                    int length = rowXA[i].length() + 1;
                    System.out.printf("%" + -length + "d", columns[i]);                   
                }
                System.out.println();
                System.out.print("A ");
                for (String word : rowXA) {
                    System.out.print(word + " ");                    
                }
                System.out.println();
                System.out.print("B ");
                for (String word : rowXB) {
                    System.out.print(word + " ");                    
                }
                System.out.println();
                Game1.printLine();
                
                String answer = enterAnswer();

                boolean validate = false;
                char row = answer.charAt(0);
                int column = Character.getNumericValue(answer.charAt(1));
                while (!validate) {
                
                if (row == 'A' && rowXA[column - 1] == "X") {                    
                    rowXA[column - 1] = rowA[column - 1];
                    Game1.options = "B";
                    validate = true;
                } else if (row == 'B' && rowXB[column - 1] == "X") {
                    rowXB[column - 1] = rowB[column - 1];
                    Game1.options = "A";
                    validate = true;
                } else {
                    System.out.println("Invalid input");
                    validate = false;
                    answer = enterAnswer();
                    row = answer.charAt(0);
                    column = Character.getNumericValue(answer.charAt(1));
                }

                }
                Game1.moves++;
                if (Game1.moves % 2 != 0) {
                    return 1;
                 
                } else if (Game1.options == "B") {
                    System.out.println("compare A");
                    for (String word : rowXB) {
                        if (rowXA[column - 1] == word) {
                            System.out.println("match");
                            Game1.matches++;
                            System.out.println(matches + " matches"); 
                            Game1.options = "A-B";
                            
                            if (Game1.matches == 4) {
                                System.out.println("Congratulations. You win!");
                                return 0;
                            }
                            return 1; 
                        }
                    }
                    
                    noMatch();
                    if (Game1.chances == 1) {
                        System.out.println("Game Over");
                        return 2;
                    } 
                    
                    
                } else {
                    System.out.println("compare B");
                    for (String word : rowXA) {
                        if (rowXB[column - 1] == word) {
                            System.out.println("match");
                            Game1.matches++;
                            System.out.println(Game1.matches + " matches");
                            Game1.options = "A-B";
                            
                            
                            if (Game1.matches == 4) {
                                System.out.println("Congratulations. You win!");
                                return 0;
                            } 
                            return 1;
                        }
                    }
                    
                    noMatch();
                    if (Game1.chances == 0) {
                        System.out.println("Game Over");
                        return 2;
                    } 
                    
                }
                return 1;
                           
            }
            public static String enterAnswer() {
                Scanner guess = new Scanner(System.in);
                        try {
                            System.out.println("Enter " + Game1.options.charAt(0) + " or " + Game1.options.charAt(2) + " coordinates: ");
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Enter " + Game1.options + " coordinates: ");
                        }
                         String answer = guess.next().toUpperCase();
                         boolean b = Pattern.matches("["+ Game1.options +"][1-4]", answer);                       
                        if (!b) {
                            System.out.println("Invalid input");
                            answer = enterAnswer();
                        }
                        return answer;
            
        }
        public static void clearConsole() {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }
                else {
                    System.out.print("\033\143");
                }
            } catch (IOException | InterruptedException ex) {}
        }
        public void noMatch() {
            System.out.println("no match");
                    Game1.options = "A-B";
                    chances--;
                    matches = 0;
                    System.out.println(chances + "chances");
                    for (int i = 0; i < 4; i++) {
                        rowXA[i] = "X";
                        rowXB[i] = "X";
                    }
                    
                    //clearConsole();
                    
        }
    }
    
    public static void main(String[] args) {
        char restart = 'N';
        do {
        System.out.println("Welcome to the game!");
        Load data = new Load(); 
        Level level = new Level();
        while (level.difficulty != 1 && level.difficulty != 2) {
            System.out.println("\nEnter 1 or 2 only\n");
            level = new Level();
        }      
        if (level.difficulty == 1) {
            Game1 game = new Game1(data.wordArray);
            Game1.chances = 10;
            Game1.options = "A-B";
            
            int status = game.play(Game1.options, 0, Game1.chances, Game1.matches);
            
            while (status == 1) {
                System.out.println(status + " status");
                status = game.play(Game1.options, Game1.moves, Game1.chances, Game1.matches);
            }
            if (status == 0 || status == 2) {
                Scanner input = new Scanner(System.in);
                System.out.println("Do you want to play again? Y/N: ");
                restart = input.next().charAt(0);
            }
            }
            } while (restart == 'Y' || restart == 'y'); 
        }       
}