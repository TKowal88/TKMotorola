import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;

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
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        Load data = new Load(); 
        Level level = new Level();
        while (level.difficulty < 1 || level.difficulty > 2) {
            System.out.println("\nPlease enter 1 or 2 only\n");
            level = new Level();
        }  
        System.out.println(level.difficulty);  
        }
    }