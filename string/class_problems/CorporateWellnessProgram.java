import java.util.Random;

public class CorporateWellnessProgram {

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("======================================================================");
        System.out.println("                     EMPLOYEE WELLNESS REPORT                         ");
        System.out.println("======================================================================");
        System.out.printf("%-8s | %-10s | %-11s | %-8s | %-12s%n", "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double height = heights[i];
            double weight = weights[i];
            double bmi = weight / (height * height);
            String status = getBmiStatus(bmi);

            System.out.printf("%-8s | %-10.2f | %-11.2f | %-8.2f | %-12s%n", 
                              "Person " + (i + 1), height, weight, bmi, status);
        }

        System.out.println("======================================================================");
    }

    public static void main(String[] args) {
        int teamSize = 10;
        double[] heights = new double[teamSize];
        double[] weights = new double[teamSize];

        Random random = new Random();

        for (int i = 0; i < teamSize; i++) {
            heights[i] = 1.50 + (1.95 - 1.50) * random.nextDouble();
            weights[i] = 45.0 + (110.0 - 45.0) * random.nextDouble();
        }

        printWellnessReport(heights, weights);
    }
}