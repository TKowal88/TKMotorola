import java.io.File;
import java.io.FileNotFoundException;
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
        int chances = 10;
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
            public void play(String rows, int tries) {
                int moves = tries;
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
                String options = rows;
                String answer = enterAnswer(options);
                char row = answer.charAt(0);
                int column = Character.getNumericValue(answer.charAt(1));
                if (row == 'A') {                    
                    rowXA[column - 1] = rowA[column - 1];
                    options = "B";

                }
                else {
                    rowXB[column - 1] = rowB[column - 1];
                    options = "A";
                }
                moves++;
                if (moves % 2 != 0){
                play(options, moves); 
                } else if (options == "B") {
                    System.out.println("compare A");
                    options = "A-B";
                    play(options,moves);
                }
                else {
                    System.out.println("compare B");
                    options = "A-B";
                    play(options, moves);

                }            
            }
            public static String enterAnswer(String options) {
                Scanner guess = new Scanner(System.in);
                        try {
                            System.out.println("Enter " + options.charAt(0) + " or " + options.charAt(2) + " coordinates: ");
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Enter " + options + " coordinates: ");
                        }
                         String answer = guess.next().toUpperCase();
                         boolean b = Pattern.matches("["+ options +"][1-4]", answer);                       
                        if (!b) {
                            System.out.println("Invalid input");
                            answer = enterAnswer(options);
                        }
                        return answer;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        Load data = new Load(); 
        Level level = new Level();
        while (level.difficulty != 1 && level.difficulty != 2) {
            System.out.println("\nEnter 1 or 2 only\n");
            level = new Level();
        }
          
        
        if (level.difficulty == 1) {
            Game1 game = new Game1(data.wordArray);
            game.play("A-B", 0);
        }
        else {

        }
        }
}