package chaining;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer, String> hashTable = new HashTable<>(10);

        hashTable.set(1, "Apple");
        hashTable.set(11, "Banana");
        hashTable.set(21, "Cherry");

        System.out.println("Get 11: " + hashTable.get(11));

        hashTable.printTable();

        hashTable.remove(11);

        hashTable.printTable();
    }
}