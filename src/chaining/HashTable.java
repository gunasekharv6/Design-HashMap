package chaining;// HashMap.java

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable<K extends Integer, V> {
    private int size;
    private List<LinkedList<Item<K, V>>> table;

    public HashTable(int size) {
        this.size = size;
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }
    }

    private int hashFunction(K key) {
        return key % size;
    }

    public void set(K key, V value) {
        int hashIndex = hashFunction(key);
        LinkedList<Item<K, V>> bucket = table.get(hashIndex);

        for (Item<K, V> item : bucket) {
            if (item.key.equals(key)) {
                item.value = value;
                return;
            }
        }

        bucket.add(new Item<>(key, value));
    }

    public V get(K key) {
        int hashIndex = hashFunction(key);
        LinkedList<Item<K, V>> bucket = table.get(hashIndex);

        for (Item<K, V> item : bucket) {
            if (item.key.equals(key)) {
                return item.value;
            }
        }

        throw new IllegalArgumentException("Key not found");
    }

    public void remove(K key) {
        int hashIndex = hashFunction(key);
        LinkedList<Item<K, V>> bucket = table.get(hashIndex);

        for (Item<K, V> item : bucket) {
            if (item.key.equals(key)) {
                bucket.remove(item);
                return;
            }
        }

        throw new IllegalArgumentException("Key not found");
    }

    // Optional: utility to print table contents
    public void printTable() {
        for (int i = 0; i < size; i++) {
            LinkedList<Item<K, V>> bucket = table.get(i);
            System.out.print("Index " + i + ": ");
            for (Item<K, V> item : bucket) {
                System.out.print("(" + item.key + ", " + item.value + ") ");
            }
            System.out.println();
        }
    }
}
