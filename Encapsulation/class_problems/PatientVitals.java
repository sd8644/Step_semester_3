import java.util.Arrays;

public class PatientVitals {
    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        this.readings = new double[10];
        this.count = 0;
        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45.0) {
            return;
        }
        if (count == readings.length) {
            readings = Arrays.copyOf(readings, readings.length * 2);
        }
        readings[count++] = reading;
    }

    public double getAverage() {
        if (count == 0) {
            return 0.0;
        }
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }
        return sum / count;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
}