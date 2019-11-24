package ua.edu.ucu.autocomplete;

import ua.edu.ucu.autocomplete.ImmutableLinkedList;

public class Dequeue {
    private ImmutableLinkedList linkedList;

    public Dequeue() {
        linkedList = new ImmutableLinkedList();
    }

    //-Returns the object at the beginning of the Queue without removing it
    public Object peek() {
        return linkedList.getFirst();
    }

    //- Removes and returns the object at the beginning of the Queue.

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    //- Adds an object to the end of the Queue.
    public void enqueue(Object e) {
        linkedList = linkedList.add(e);
    }

    public void enqueue(Object[] e) {
        linkedList = linkedList.add(e);
    }

    public Object dequeue(int i) {
        Object firstEl = linkedList.getFirst();
        linkedList = linkedList.removeFirst();
        return firstEl;
    }

    public Object[] dequeue(double i) {
        Object[] firstEl = (Object[]) linkedList.getFirst();
        linkedList = linkedList.removeFirst();
        return firstEl;
    }

    public void addFirst(Object e) {
        linkedList.addFirst(e);
    }
}