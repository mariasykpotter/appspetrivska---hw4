package ua.edu.ucu.autocomplete;
import java.util.Arrays;

final public class ImmutableLinkedList  implements ImmutableList {
    private int size;
    private Node head;

    private static class Node {
        private Object val;
        private Node next;

        private Node(Object val) {
            this.val = val;
            this.next = null;
        }

        private Node(Object val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public ImmutableLinkedList(){
        head = null;
        size = 0;
    }

    public ImmutableLinkedList(Object[] arr) {
        if (arr.length > 0) {
            head = new Node(arr[0]);
            Node curNode = head;
            for (int i = 1; i < arr.length; i++) {
                curNode.next = new Node(arr[i]);
                curNode = curNode.next;
            }
        }
        size = arr.length;
    }

    private ImmutableLinkedList copy() {
        if (size > 0) {
            ImmutableLinkedList lst = new ImmutableLinkedList();
            lst.size = size;
            lst.head = new Node(head.val);
            Node current = head.next;
            Node newCurr = lst.head;
            while (current != null) {
                newCurr.next = new Node(current.val);
                newCurr = newCurr.next;
                current = current.next;
            }
            return lst;
        }
        return new ImmutableLinkedList();
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        ImmutableLinkedList newLinkedList = copy();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index > 0) {
            Node before = newLinkedList.getByIndex(index - 1);
            Node after = newLinkedList.getByIndex(index);
            before.next = new Node(e, after);
        } else {
            newLinkedList.head = new Node(e, newLinkedList.head);
        }
        newLinkedList.size += 1;
        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    private Node getByIndex(int index) {
        if (index < 0 || index > size || size == 0) {
            throw new IndexOutOfBoundsException();
        }
        int i;
        Node curNode = head;
        for (i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newLinkedList = copy();
        Node after = newLinkedList.getByIndex(index);
        if (c.length > 0) {
            Node before = newLinkedList.getByIndex(index - 1);
            Node curNode = before;
            for (Object el : c) {
                curNode.next = new Node(el, curNode.next);
                curNode = curNode.next;
            }
            if (after != null) {
                curNode.next = new Node(after.val, after.next);
            }
        }
        newLinkedList.size += c.length;
        return newLinkedList;
    }

    @Override
    public Object get(int index) {
        return getByIndex(index).val;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        Node cur = head;
        ImmutableLinkedList newLinkedList = copy();
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            newLinkedList.head = newLinkedList.head.next;
        } else {
            Node before = newLinkedList.getByIndex(index - 1);
            before.next = before.next.next;
        }
        newLinkedList.size--;
//        cur = head;
//        while (cur != null) {
//            System.out.println(cur.val);
//            cur = cur.next;
//        }
        return newLinkedList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        ImmutableLinkedList newLinkedList = copy();
        newLinkedList.getByIndex(index).val = e;
        return newLinkedList;
    }

    @Override
    public int indexOf(Object e) {
        Node curNode = head;
        int count = 0;
        while (curNode != null) {
            if (curNode.val.equals(e)) {
                return count;
            }
            curNode = curNode.next;
            count++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        int currentPos = 0;
        Node curNode = head;
        while (curNode != null) {
            newArray[currentPos] = curNode.val;
            curNode = curNode.next;
            currentPos++;
        }
        return newArray;
    }

    @Override
    public String toString() {
        String str = Arrays.toString(toArray());
        return str.substring(1, str.length() - 1);
    }

    //додає елемент у початок зв'язаного списку
    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    //додає елемент у кінець зв'язаного списку
    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    //видаляє перший елемент
    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    //видаляє останній елемент
    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }
}