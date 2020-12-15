package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node pointer = head;

        public boolean hasNext() {
            return pointer != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = pointer.item;
            pointer = pointer.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
        this.head = null;
        this.tail = this.head;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the index to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node node = new Node(item, this.head, null);
        // corner case
        if (this.size == 0) {
            this.head = node;
            this.tail = node;
            this.size++;
            return;
        }
        this.head.prev = node;
        this.head = node;
        this.size++;
    }

    // add the index to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node node = new Node(item, null, tail);
        // corner case
        if (this.size == 0) {
            this.head = node;
            this.tail = node;
            this.size++;
            return;
        }
        this.tail.next = node;
        this.tail = node;
        this.size++;
    }

    // remove and return the index from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
        Item toReturn = this.head.item;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        this.size--;
        return toReturn;
    }

    // remove and return the index from the back
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        Item toReturn = this.tail.item;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        this.size--;
        return toReturn;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void print() {
        for (Item item: this) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.println("size: " + this.size());
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.print();

        deque.removeLast();
        deque.print();
        deque.removeLast();
        deque.print();
        deque.removeLast();
        deque.print();
    }

}
