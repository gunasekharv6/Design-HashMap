package openaddressing.linearprobing;

public class Main {
    public static void main(String[] args) {
        OpenAddressingHashMap<Integer, String> map = new OpenAddressingHashMap<>(5);

        map.put(1, "One");
        map.put(6, "Six");
        map.put(11, "Eleven");

        map.printTable();

        System.out.println("Get key 6: " + map.get(6));

        map.remove(6);
        map.printTable();

        map.put(16, "Sixteen"); // Will go to same place 6 was
        map.printTable();
    }
}

