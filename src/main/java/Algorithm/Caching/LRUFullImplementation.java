package Algorithm.Caching;

import java.util.HashMap;

class LRUCache<K, V> {

    // Doubly linked list node
    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final HashMap<K, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    // Get value from cache
    public V get(K key) {
        if (!map.containsKey(key)) return null;

        Node node = map.get(key);
        moveToHead(node); // mark as most recently used
        return node.value;
    }

    // Put value into cache
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value);
            if (map.size() == capacity) {
                map.remove(tail.key); // evict least recently used
                removeNode(tail);
            }
            addNodeToHead(newNode);
            map.put(key, newNode);
        }
    }

    // Helper: remove a node
    private void removeNode(Node node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
    }

    // Helper: add node to head
    private void addNodeToHead(Node node) {
        node.prev = null;
        node.next = head;

        if (head != null) head.prev = node;
        head = node;

        if (tail == null) tail = head;
    }

    // Helper: move node to head
    private void moveToHead(Node node) {
        removeNode(node);
        addNodeToHead(node);
    }

    // Debug: print cache state
    public void printCache() {
        Node curr = head;
        System.out.print("Cache: ");
        while (curr != null) {
            System.out.print(curr.key + "=" + curr.value + " ");
            curr = curr.next;
        }
        System.out.println();
    }
}

public class LRUFullImplementation {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printCache(); // 3=C 2=B 1=A

        cache.get(1); // Access 1 → most recent
        cache.printCache(); // 1=A 3=C 2=B

        cache.put(4, "D"); // Evicts 2 (least recent)
        cache.printCache(); // 4=D 1=A 3=C
    }
}