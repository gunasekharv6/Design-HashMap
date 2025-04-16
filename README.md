# Design-HashMap
Absolutely! Let's break it down step by step.

---

### 🧠 What is a HashMap?

A **HashMap** is a data structure that stores **key-value pairs**. You can think of it like a dictionary: each key is unique, and each key maps to a value. It provides **fast access** to values based on their keys—typically in constant time: `O(1)`.

---

### 🏗️ How does it store data?

1. **Hash Function**:
   - When you add a key, the HashMap uses a **hash function** to convert the key into an **index** in an internal array (or list).
   - Example: `hashIndex = key % size`
   - This index tells the map **where to store** the key-value pair.

2. **Buckets**:
   - The HashMap maintains an **array of buckets**. Each bucket is usually a list (like a `LinkedList`).
   - A **bucket** holds all key-value pairs that are assigned the same index.

---

### 🔁 What is a Hash Collision?

A **hash collision** happens when **two different keys hash to the same index**.

#### Example:
```python
key1 = 11 % 10 = 1
key2 = 21 % 10 = 1
```
Both `key1` and `key2` would go into bucket at index 1 — that’s a collision.

---

### 🧩 How does HashMap handle collisions?

There are several techniques, but the most common is **Chaining**:

#### 🔗 Chaining:
- Each bucket is a list of entries (key-value pairs).
- If a collision happens, the new pair is just **added to the list** at that index.
- When retrieving a value, the HashMap **scans the list** to find the matching key.

So, even if multiple keys land in the same bucket, we can still find the correct value.

---

### 🧨 Why are collisions important?

- **Collisions impact performance**. Ideally, keys are spread evenly across buckets.
- If too many keys end up in the same bucket (due to a poor hash function or small size), lookup time can degrade to **O(n)** instead of **O(1)**.

---

### 🛠️ Summary of Key Concepts:

| Concept             | Description |
|---------------------|-------------|
| **Key**             | Unique identifier for data |
| **Value**           | The data you're storing |
| **Hash Function**   | Converts key to index |
| **Bucket**          | A list at a specific index in the array |
| **Collision**       | Two keys with the same hash index |
| **Chaining**        | Storing multiple items in a bucket via a list |

---

Let me know if you’d like a visual diagram or want to explore other collision handling methods like **open addressing**.
