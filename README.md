# Design-HashMap
### Constraints and assumptions:
1. For simplicity, are the keys integers only?
   - Yes
2. For collision resolution, can we use chaining?
   - Yes
3. Do we have to worry about load factors?
   - No
4. Can we assume inputs are valid or do we have to validate them?
   - Assume they're valid
5. Can we assume this fits memory?
   - Yes

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

Let’s go deep with a **visual diagram**, then explore **collision handling methods** including **open addressing**.

---

## 🧠 HashMap Storage – Visual Diagram

Let's say you have a HashMap of size `5` and insert the following key-value pairs:

```text
(1, "Apple")
(6, "Banana")
(11, "Cherry")
```

Assume your hash function is:
```java
hashIndex = key % 5
```

### 🔢 Hash Calculations:
- `1 % 5 = 1` → Index 1
- `6 % 5 = 1` → Index 1 (Collision!)
- `11 % 5 = 1` → Index 1 (Another Collision!)

---

### 🖼️ Diagram with Chaining

```
Index     Bucket (LinkedList)
-------------------------------
0         []
1         [(1, "Apple") → (6, "Banana") → (11, "Cherry")]
2         []
3         []
4         []
```

> 🔗 This method is **Chaining**, where each bucket is a list of key-value pairs.

---

## 🛡️ Collision Handling Techniques

### 1. **Chaining** (what you're already using)

**How it works**:
- Each bucket is a **list**.
- All colliding entries are stored in that list.
- On lookup, scan the list to find the right key.

✅ Easy to implement  
❌ Lookup time increases if many items fall into the same bucket.

---

### 2. **Open Addressing** (No chaining!)

Instead of using buckets/lists, if a collision occurs, you **probe (search)** for the next available spot in the array itself.

---

### 2.1 **Linear Probing**

If `hash(key)` is occupied, check the next one: `(hash + 1) % size`, then `(hash + 2) % size`, and so on.

### 📦 Example:

You want to insert `(6, "Banana")`, but index 1 is taken.

```
Attempt index 1 → taken
Try index 2 → empty → insert here
```

#### 🖼️ Diagram:
```
Index     Value
--------------------
0         null
1         (1, "Apple")
2         (6, "Banana")
3         null
4         null
```

✅ Saves memory (no extra lists)  
❌ Clustering: large groups of filled slots slow things down

---

### 2.2 **Quadratic Probing**

Instead of linearly checking `(hash + 1)`, it checks `(hash + 1^2)`, `(hash + 2^2)`, `(hash + 3^2)`...

This spreads out entries more, reducing clustering.

---

### 2.3 **Double Hashing**

Use a **second hash function** to calculate the step size for probing:
```java
index = (hash1(key) + i * hash2(key)) % size
```

✅ Very effective at spreading entries  
❌ Slightly more complex

---

### 🔍 Comparison Summary:

| Method              | Pros                         | Cons                          |
|---------------------|------------------------------|-------------------------------|
| **Chaining**         | Easy, avoids full-table scan | Uses extra space (lists)      |
| **Linear Probing**   | Simple, cache-friendly        | Clustering issues             |
| **Quadratic Probing**| Reduces clustering            | Can skip some slots           |
| **Double Hashing**   | Best distribution             | More complex hash math        |

---

Let's get into the topic of **HashMap resizing**, which is **critical** for maintaining performance as the table fills up.

---

## 🧯 What happens if all indices are filled?

If **all the slots are full**, you **can’t insert new key-value pairs** using open addressing. Even chaining becomes inefficient if buckets are too long.

That's where **resizing (rehashing)** comes in.

---

## 🔁 Resizing a HashMap (a.k.a Rehashing)

### ✨ How it works:
1. **Create a bigger array** (usually double the current size).
2. **Recalculate hash indexes** for all existing keys based on the **new size**.
3. **Insert each old key-value pair** into the new array.

> 📌 This process is called **rehashing** because you run the hash function again for each key using the new table size.

---

### 🔢 Example:

Original table size: 5  
You insert until it’s full. Now double it to 10:

```
Before resizing (size = 5):
Index     Bucket
---------------------
0         []
1         [(1, "Apple"), (6, "Banana"), (11, "Cherry")]
2         []
3         []
4         []

After resizing (size = 10):
Recalculate: 
1 % 10 = 1
6 % 10 = 6
11 % 10 = 1

Index     Bucket
---------------------
0         []
1         [(1, "Apple"), (11, "Cherry")]
2         []
3         []
4         []
5         []
6         [(6, "Banana")]
7         []
8         []
9         []
```

---

## 📊 When should a HashMap resize?

Usually based on **load factor**:

```text
loadFactor = number of items / table size
```

- Default threshold: **0.75**
- When `loadFactor > 0.75`, resize the table.

### 🧠 Why 0.75?
It’s a balance between:
- Space efficiency
- Speed of lookup
- Frequency of resizing

---

## ⚙️ Resizing with Open Addressing

With **open addressing**, resizing is especially important:

- Once the table is full, **no further insertions** are possible without resizing.
- After resizing, all items must be **re-inserted** (not just copied), because the probing sequence depends on the table size.

---

## ✅ Summary

| Condition                  | What happens?                      |
|---------------------------|------------------------------------|
| Table gets full           | Trigger **resizing**               |
| Resize means              | Create larger table + rehash keys  |
| Without resizing          | No space → insertions fail         |
| With resizing             | Room for growth + better performance |

---

