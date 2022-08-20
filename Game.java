import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Character;

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
            System.out.println("\nInput numbers only");
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
            public void play() {
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
                String answer = Game1.enterAnswer();
                System.out.println(answer);
            }
            public static String enterAnswer() {
                Scanner guess = new Scanner(System.in);
                        System.out.println("Enter coordinates: ");
                         String answer = guess.next().toUpperCase();
                        
                        int compareA = Character.compare(answer.charAt(0), 'A');
                        int compareB = Character.compare(answer.charAt(0), 'B');
                        int columnNumber = Character.getNumericValue(answer.charAt(1));
                        System.out.println(columnNumber);
                        if ((compareA != 0 && compareB != 0) || (columnNumber < 1 || columnNumber > 4)) {
                            System.out.println("Invalid input");
                            answer = Game1.enterAnswer();
                        }
                        return answer;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        Load data = new Load(); 
        Level level = new Level();
        while (level.difficulty < 1 || level.difficulty > 2) {
            System.out.println("\nPlease enter 1 or 2 only\n");
            level = new Level();
        }   
        
        if (level.difficulty == 1) {
            Game1 game = new Game1(data.wordArray);
            game.play();
        }
        else {

        }
        }
}