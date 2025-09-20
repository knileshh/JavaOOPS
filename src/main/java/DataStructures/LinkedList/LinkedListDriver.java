package DataStructures.LinkedList;

public class LinkedListDriver {
    public static void main(String[] args) {
        System.out.println("Linked list driver initialised!");

        SinglyLinkedList sl = new SinglyLinkedList();


        System.out.println("Insert at tail");
        sl.insertAtTail(555);
        System.out.println("Is empty? : " + sl.isEmpty());
        sl.insertAtTail(444);

        System.out.println("Insert at Head");
        sl.insertAtHead(100);

        System.out.println("Traverse");
        sl.traverse();

        System.out.println("Size: " + sl.size());

        System.out.println("Contains: 555? : " + sl.contains(555));
        System.out.println("Contains: 5? : " + sl.contains(5));

        System.out.println("Let's print the linked list: " + sl);

        //Intermediate calls

        sl.insertAtIndex(2, 50);

        System.out.println(sl.deleteAtIndex(0));

        System.out.println("Is empty? : " + sl.isEmpty());
        System.out.println(sl);
    }
}

class SinglyLinkedList {
    class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    Node head;

    void insertAtTail(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            // since, current.next points is at last node. CZ, current.next == null;
            current.next = newNode;
        }
    }

    void traverse() {
        Node current = head;

        if (current == null) {
            System.out.println("Linked List empty");
        }

        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        Node current = head;

        if (current == null) {
            System.out.println("Linked List is empty!");
        }

        while (current != null) {
            int val = current.value;
            res.append(" ").append(val);
            current = current.next;
        }

        // feels like too much work for returning string.
        return res.toString();
    }

    // logical operators return boolean value
    boolean isEmpty() {
        return head == null;
    }

    int size() {
        Node current = head;
        int count = 0;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    void insertAtHead(int value) {
        Node newNode = new Node(value);

        if (head != null) {
            newNode.next = head;
        }

        head = newNode;
    }

    boolean contains(int value) {
        Node current = head;

        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;

        }
        return false;
    }

    // Intermediate Methods (they say)

    void insertAtIndex(int index, int value) {
        if (index == 0) {
            insertAtHead(value);
        } else if (index == this.size()) {
            insertAtTail(value);
        } else {
            Node current = head;

            // Since index will be before size or after 0;
            for (int i = 1; i < this.size(); i++) {
                if (i == index) {
                    Node newNode = new Node(value);
                    newNode.next = current.next;
                    current.next = newNode;
                    break;
                }
                current = current.next;
            }
        }

        System.out.println("0 based indexing, inserted at " + index + ": " + this);
    }


    int deleteAtIndex(int index) {
       if (index < 0 || index >= this.size()) {
           throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + this.size());
       } else {
           Node t1 = head;
           Node t2 = head.next;
           int i = 0;

           int res = 0;

           if (t2 == null) {
               res = t1.value;
               head = null;
           }

           while (t2 != null) {
               if (i == index-1) {
                   res = t2.value;
                   t1.next = t2.next;
                   break;
               }
               t1 = t1.next;
               t2 = t2.next;
               i++;
           }

           return res;
       }

    }
}