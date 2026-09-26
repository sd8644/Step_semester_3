public abstract class ServiceableVehicle {
    private double mileage;

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km >= 0) {
            this.mileage += km;
        }
    }

    public abstract String performMaintenance();
}