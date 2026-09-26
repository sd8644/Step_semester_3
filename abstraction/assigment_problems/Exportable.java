public interface Exportable {
    class Counter {
        private static int count = 0;
    }

    String exportData();

    static void incrementExportCount() {
        Counter.count++;
    }

    static int getTotalExports() {
        return Counter.count;
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }
}