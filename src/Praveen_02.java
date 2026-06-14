import java.util.Random;
import java.util.Scanner;

public class Praveen_02 {

    static int totalScore = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("===================================");
        System.out.println("     WELCOME TO NUMBER GUESSING GAME");
        System.out.println("===================================");

        int totalRounds = 3;
        int maxAttempts = 7;

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("\n--- Round " + round + " of " + totalRounds + " ---");

            int targetNumber = random.nextInt(100) + 1; // Random number from 1 to 100
            int attemptsLeft = maxAttempts;
            boolean guessedCorrectly = false;

            System.out.println("I have generated a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            while (attemptsLeft > 0) {
                System.out.print("\nAttempts left: " + attemptsLeft + " → Enter your guess: ");

                int userGuess;
                try {
                    userGuess = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    continue;
                }

                if (userGuess < 1 || userGuess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }

                attemptsLeft--;

                if (userGuess == targetNumber) {
                    int pointsEarned = attemptsLeft * 10 + 10; // More points for fewer attempts used
                    totalScore += pointsEarned;
                    System.out.println("🎉 Correct! The number was " + targetNumber + ".");
                    System.out.println("You earned " + pointsEarned + " points this round!");
                    guessedCorrectly = true;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Too Low! Try a higher number.");
                } else {
                    System.out.println("Too High! Try a lower number.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\n❌ Out of attempts! The correct number was: " + targetNumber);
                System.out.println("Better luck next round!");
            }

            System.out.println("Current Total Score: " + totalScore);
        }

        System.out.println("\n===================================");
        System.out.println("         GAME OVER!");
        System.out.println("   Your Final Score: " + totalScore);
        System.out.println("===================================");

        scanner.close();
    }
}