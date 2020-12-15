package Queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    private IndexStack stack;

    private class Node {
        int index;
        Node next;

        Node(int item, Node next) {
            this.index = item;
            this.next = next;
        }
    }

    private class IndexStack {
        Node pointer = null;

        public void push(int index) {
            Node node = new Node(index, pointer);
            pointer = node;
        }

        public int pop() {
            int index = pointer.index;
            pointer = pointer.next;
            return index;
        }

        public boolean isEmpty() {
            return this.pointer == null;
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffled;
        private int p = 0;

        public RandomizedQueueIterator() {
            shuffled = (Item[]) new Object[size];
            int i = 0, j = 0, count = 0;
            while (count < size) {
                if (array[i] == null) {
                    i++;
                } else {
                    shuffled[j++] = array[i++];
                    count++;
                }
            }
            StdRandom.shuffle(shuffled);
        }

        public boolean hasNext() {
            return  p < shuffled.length;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffled[p++];
        }
    }

    private void grow(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            copy[i] = this.array[i];
        }
        this.array = copy;
    }

    private void shrink(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0, j = 0, count = 0;
        while (count < this.size) {
            if (this.array[i] == null) {
                i++;
            } else {
                copy[j++] = this.array[i++];
                count++;
            }
        }
        stack = new IndexStack();
        this.array = copy;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.array = (Item[]) new Object[1];
        this.stack = new IndexStack();
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the index
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (!stack.isEmpty()) {
            array[stack.pop()] = item;
            this.size++;
            return;
        }
        if (this.size == array.length) grow(2*array.length);
        this.array[this.size++] = item;
    }

    // remove and return a random index
    public Item dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(this.array.length);
        while (array[index] == null) index = StdRandom.uniform(this.array.length);

        Item item = array[index];
        stack.push(index);
        array[index] = null;
        this.size--;
        if (this.size > 0 && this.size == array.length/4) shrink(array.length/2);
        return item;
    }

    // return a random index (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(this.array.length);
        while (array[index] == null) index = StdRandom.uniform(this.array.length);
        return this.array[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void print() {
        for (Item i : this.array) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("size: " + this.size);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(1);
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(2);
    }

}

