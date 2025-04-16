package openaddressing.linearprobing;// openaddressinglinearprobing.OpenAddressingHashMap.java

public class OpenAddressingHashMap<K extends Integer, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public OpenAddressingHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return key % capacity;
    }

    public void put(K key, V value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        int originalIndex = index;
        while (table[index] != null && !table[index].isDeleted && !table[index].key.equals(key)) {
            index = (index + 1) % capacity;
            if (index == originalIndex) {
                throw new RuntimeException("HashMap is full");
            }
        }

        if (table[index] == null || table[index].isDeleted) {
            table[index] = new Entry<>(key, value);
            size++;
        } else {
            table[index].value = value; // Update existing
        }
    }

    public V get(K key) {
        int index = hash(key);
        int originalIndex = index;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % capacity;
            if (index == originalIndex) break;
        }
        throw new IllegalArgumentException("Key not found");
    }

    public void remove(K key) {
        int index = hash(key);
        int originalIndex = index;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                table[index].isDeleted = true;
                size--;
                return;
            }
            index = (index + 1) % capacity;
            if (index == originalIndex) break;
        }
        throw new IllegalArgumentException("Key not found");
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] oldTable = table;

        table = new Entry[newCapacity];
        size = 0;
        capacity = newCapacity;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !entry.isDeleted) {
                put(entry.key, entry.value);
            }
        }
    }

    public void printTable() {
        System.out.println("Hash Table:");
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            if (entry != null && !entry.isDeleted) {
                System.out.println(i + ": (" + entry.key + ", " + entry.value + ")");
            } else {
                System.out.println(i + ": null");
            }
        }
    }
}
