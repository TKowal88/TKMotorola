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
    public static class Gameplay {
        static String[] rowA;
        static String[] rowB;
        static String[] rowXA;
        static String[] rowXB;
        static int [] columns;
        static String level;
        static int chances;
        static int matches;
        static String options;  
        static int moves;
        static int size;

        public Gameplay(String[] array){
            Gameplay.rowA = new String[Gameplay.size];
            Gameplay.rowB = new String[Gameplay.size];
            Gameplay.rowXA = new String[Gameplay.size];
            Gameplay.rowXB = new String[Gameplay.size];
            Gameplay.columns = new int[Gameplay.size];
            for (int i = 0; i < Gameplay.size; i++) {
                Gameplay.rowXA[i] = "X";  
                Gameplay.rowXB[i] = "X";
                Gameplay.columns[i] = i + 1;
            }           
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
            for (int i = 0; i < Gameplay.size; i++) {
                rowA[i] = array[list100.get(i)];
            }
            for (int i = 0; i < Gameplay.size; i++) {
                rowB[i] = rowA[list.get(i)];                
            } 
            }
            public static void printLine() {
                for (int i = 0; i < 30; i++) {
                    System.out.print("-");
                }
                System.out.println();
            }  

            public static void print() {                
                Gameplay.printLine();
                System.out.println("Level: " + Gameplay.level);
                System.out.println("Guess chances: " + Gameplay.chances);
                System.out.print("  ");
                for (int i = 0; i < Gameplay.size; i++){
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
                Gameplay.printLine();
            }

                public static int play() {
                String answer = enterAnswer();
                boolean validate = false;
                char row = answer.charAt(0);
                int column = Character.getNumericValue(answer.charAt(1));
                while (!validate) {               
                if (row == 'A' && rowXA[column - 1] == "X") {                    
                    rowXA[column - 1] = rowA[column - 1];
                    Gameplay.options = "B";
                    validate = true;
                } else if (row == 'B' && rowXB[column - 1] == "X") {
                    rowXB[column - 1] = rowB[column - 1];
                    Gameplay.options = "A";
                    validate = true;
                } else {
                    System.out.println("Invalid input");
                    validate = false;
                    answer = enterAnswer();
                    row = answer.charAt(0);
                    column = Character.getNumericValue(answer.charAt(1));
                }
                }
                Gameplay.moves++;
                if (Gameplay.moves % 2 != 0) {
                    return 1;               
                } else if (Gameplay.options == "B") {
                    for (String word : rowXB) {
                        if (rowXA[column - 1] == word) { 
                            return Gameplay.match();                                                 
                        }
                    }                   
                    noMatch();
                    if (Gameplay.chances == 1) {
                        System.out.println("Game Over");
                        return 2;
                    }                    
                } else {
                    
                    for (String word : rowXA) {
                        if (rowXB[column - 1] == word) {
                            return Gameplay.match();
                        }
                    }                   
                    noMatch();
                    if (Gameplay.chances == 0) {
                        System.out.println("Game Over");
                        return 2;
                    }                     
                }
                return 1;                           
            }
            public static String enterAnswer() {
                System.out.println();
                Scanner guess = new Scanner(System.in);
                        try {
                            System.out.println("Enter " + Gameplay.options.charAt(0) + " or " + Gameplay.options.charAt(2) + " coordinates: ");
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Enter " + Gameplay.options + " coordinates: ");
                        }
                         String answer = guess.next().toUpperCase();
                         boolean b = Pattern.matches("["+ Gameplay.options +"][1-" + Gameplay.size + "]", answer);                       
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
        public static int match() {
            Gameplay.matches++;
            Gameplay.options = "A-B";                           
            if (Gameplay.matches == Gameplay.size) {
                Gameplay.clearConsole();
                Gameplay.print();
                System.out.println("Congratulations. You win!");
                return 0;
            }
            return 1; 
        }
        public static void noMatch() {
            System.out.println("no match");
                    Gameplay.options = "A-B";
                    Gameplay.chances--;
                    Gameplay.matches = 0;
                    System.out.println(chances + "chances");
                    Gameplay.clearConsole();                   
                    Gameplay.print();
                    try {
                    Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {}
                    for (int i = 0; i < Gameplay.size; i++) {
                        Gameplay.clearConsole();
                        rowXA[i] = "X";
                        rowXB[i] = "X";
                    }
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
        Gameplay.clearConsole();
        int size;      
            if (level.difficulty == 1) {
            Gameplay.size = 4;
            Gameplay Game1 = new Gameplay(data.wordArray);
            Gameplay.chances = 10;
            Gameplay.level = "easy";
        } else {
            Gameplay.size = 8;
            Gameplay Game1 = new Gameplay(data.wordArray);
            Gameplay.level = "hard";
            Gameplay.chances = 15;

        }           
        Gameplay.options = "A-B";
        Gameplay.print();
        int status = Gameplay.play();                        
            while (status == 1) {
                Gameplay.clearConsole();
                Gameplay.print();
                status = Gameplay.play();                
            }
            if (status == 0 || status == 2) {
                Scanner input = new Scanner(System.in);
                System.out.println("Do you want to play again? Y/N: ");
                restart = input.next().charAt(0);
            }           
            } while (restart == 'Y' || restart == 'y'); 
        }     
    } 
