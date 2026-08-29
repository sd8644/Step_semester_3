public class TrafficSignalAnalyzer {

    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.length() == 0) {
            System.out.println("Log is empty.");
            return;
        }

        char maxChar = signalLog.charAt(0);
        int maxStreak = 1;

        char currentChar = signalLog.charAt(0);
        int currentStreak = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            char c = signalLog.charAt(i);

            if (c == currentChar) {
                currentStreak++;
            } else {
                currentChar = c;
                currentStreak = 1;
            }

            if (currentStreak > maxStreak) {
                maxStreak = currentStreak;
                maxChar = currentChar;
            }
        }

        System.out.println("Longest Streak: '" + maxChar + "' repeated " + maxStreak + " times");
    }

    public static void main(String[] args) {
        String log1 = "RRGGGYRR";
        findLongestStreak(log1);

        String log2 = "RRRRYYGG";
        findLongestStreak(log2);
    }
}