import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Collections;

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
                System.out.println(rowA[i]);
            }
            for (int i = 0; i < 4; i++) {
                rowB[i] = rowA[list4.get(i)];
                System.out.println(rowB[i]);
            }
            
            
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
        }
        else {

        }
        }
}