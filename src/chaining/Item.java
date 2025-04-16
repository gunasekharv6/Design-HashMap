package chaining;

class Item<K, V> {
    K key;
    V value;

    public Item(K key, V value) {
        this.key = key;
        this.value = value;
    }
}