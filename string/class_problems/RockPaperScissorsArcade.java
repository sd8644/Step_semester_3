import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsArcade {

    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};
    private static final Random random = new Random();

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }
        
        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        } else {
            return "Computer Wins";
        }
    }

    public static String getRandomMove() {
        return MOVES[random.nextInt(MOVES.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalRounds = 5;

        String[][] roundLogs = new String[totalRounds][4];

        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (int i = 0; i < totalRounds; i++) {
            System.out.print("Round " + (i + 1) + " - Enter move (Rock, Paper, Scissors): ");
            String playerMove = scanner.nextLine().trim();

            if (!playerMove.equalsIgnoreCase("Rock") && 
                !playerMove.equalsIgnoreCase("Paper") && 
                !playerMove.equalsIgnoreCase("Scissors")) {
                System.out.println("Invalid move! Defaulting to Rock.");
                playerMove = "Rock";
            }

            String computerMove = getRandomMove();
            String result = playRound(playerMove, computerMove);

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            roundLogs[i][0] = String.valueOf(i + 1);
            roundLogs[i][1] = playerMove;
            roundLogs[i][2] = computerMove;
            roundLogs[i][3] = result;

            System.out.println("Computer: " + computerMove + " | Result: " + result + "\n");
        }

        double winPercentage = ((double) wins / totalRounds) * 100;

        System.out.println("=================================================");
        System.out.println("                  MATCH SUMMARY                  ");
        System.out.println("=================================================");
        System.out.printf("%-7s | %-12s | %-13s | %-13s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < totalRounds; i++) {
            System.out.printf("%-7s | %-12s | %-13s | %-13s%n", 
                              roundLogs[i][0], roundLogs[i][1], roundLogs[i][2], roundLogs[i][3]);
        }

        System.out.println("-------------------------------------------------");
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n", 
                          wins, losses, draws, winPercentage);
        System.out.println("=================================================");

        scanner.close();
    }
}