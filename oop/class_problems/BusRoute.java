public class BusRoute implements Comparable<BusRoute> {
    private static final int DEFAULT_PRIORITY = 1;

    private final String routeCode;
    private final String routeName;
    private final int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, DEFAULT_PRIORITY);
    }

    public String getRouteCode() {
        return this.routeCode;
    }

    public String getRouteName() {
        return this.routeName;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) return -1;
        int cmp = Integer.compare(other.priority, this.priority);
        if (cmp != 0) return cmp;
        cmp = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (cmp != 0) return cmp;
        return this.routeName.compareToIgnoreCase(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null || routes.length <= 1) return routes;
        BusRoute[] sorted = routes.clone();
        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }
}