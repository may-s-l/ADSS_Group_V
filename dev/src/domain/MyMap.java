package dev.src.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMap<K, V> {

    private final Map<K, V> map;

    public MyMap() {
        this.map = new HashMap<>();
    }

    // הוספת ערך למיפוי
    public void put(K key, V value) {
        map.put(key, value);
    }

    // קבלת ערך מהמיפוי לפי מפתח
    public V get(K key) {
        return map.get(key);
    }

    // האם המיפוי מכיל את המפתח
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    // מחיקת ערך מהמיפוי לפי מפתח
    public void remove(K key) {
        map.remove(key);
    }

    // קבלת גודל המיפוי
    public int size() {
        return map.size();
    }

    // ריקון המיפוי
    public void clear() {
        map.clear();
    }
}