package com.vk.firebaselisteners.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IndexedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private ArrayList<K> keysList = new ArrayList<>();

    public void add(K key, V val) {
        super.put(key, val);
        keysList.add(key);
    }

    public void update(K key, V val) {
        super.put(key, val);
    }

    public void removeItemByKey(K key) {
        super.remove(key);
        keysList.remove(key);
    }

    public void removeItemByIndex(int index) {
        super.remove(keysList.get(index));
        keysList.remove(index);
    }

    public V getItemByIndex(int i) {
        return (V) super.get(keysList.get(i));
    }

    public int getIndexByKey(K key) {
        return keysList.indexOf(key);
    }
}