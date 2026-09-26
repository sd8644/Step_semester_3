public abstract class Shape {
    private static int counter = 0;
    private final String shapeId;

    public Shape() {
        this.shapeId = "SHAPE-" + (++counter);
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        scale(factor, factor);
    }

    public abstract void scale(double xFactor, double yFactor);

    public String getShapeId() {
        return shapeId;
    }

    public static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }
}