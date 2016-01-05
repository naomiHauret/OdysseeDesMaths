package com.odysseedesmaths.pathfinding.util;

import java.util.HashMap;
import java.util.Map;


public class PriorityQueue<E> {

    private Map<E,Integer> queue;

    public PriorityQueue() {
        queue = new HashMap<E,Integer>();
    }

    public E get() {
        E res = null;
        Integer lowest = Integer.MAX_VALUE;

        for (Map.Entry<E,Integer> entry : queue.entrySet()) {
            int priority = entry.getValue();
            if (priority < lowest) {
                res = entry.getKey();
                lowest = priority;
            }
        }

        queue.remove(res);
        return res;
    }

    public void put(E e, Integer p) {
        queue.put(e,p);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean contains(E e) {
        return queue.keySet().contains(e);
    }

}
