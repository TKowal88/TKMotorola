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
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        Load data = new Load();   
        System.out.println(data.wordArray[0]);
}
}