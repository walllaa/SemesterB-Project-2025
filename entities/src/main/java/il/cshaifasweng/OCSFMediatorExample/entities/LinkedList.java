package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.*;
import java.util.Objects; // used for safe equals in remove

// NOTE: These helpers are top-level, package-private classes in the `entities` package.
// If you're using JPA entity scanning on this package, it's fine since they have no JPA annotations,
// but consider moving them to a `util` package later to avoid mixing with entities.

// Class 1
// Helper Class (Generic LinkedList node)
//  Name clash warning: this class is named LinkedList and can be confused with java.util.LinkedList.
// Keep the name for compatibility, but avoid importing java.util.LinkedList in the same file.
class LinkedList<T> {

    // Data members
    T data;              // package-private on purpose (kept for compatibility)
    LinkedList<T> next;  // next node reference

    LinkedList(T data) {
        this.data = data;
        this.next = null;
    }
}

// Class 2
// Helper class ( Generic singly linked list )
// Java convention suggests class names start with uppercase (List). Kept as `list` for compatibility.
class list<T> {

    LinkedList<T> head; // kept package-private for compatibility
    private int length = 0; // tracks list size

    list() { this.head = null; }

    // Append to tail
    void add(T data) {
        LinkedList<T> temp = new LinkedList<>(data);
        if (this.head == null) { // empty list
            head = temp;
        } else {
            LinkedList<T> x = head;
            while (x.next != null) { x = x.next; }
            x.next = temp; // link new node at tail
        }
        length++; //  maintain size
    }

    // Insert at 1-based position
    void add(int position, T data) {
        if (position < 1 || position > length + 1) { //  guard invalid range
            System.out.println("Position Unavailable in LinkedList");
            return;
        }
        if (position == 1) { // insert at head
            LinkedList<T> newHead = new LinkedList<>(data);
            newHead.next = head;
            head = newHead;
            length++; // size++ for head insert
            return;
        }
        // traverse to (position-1)
        LinkedList<T> prev = head;
        for (int i = 1; i < position - 1; i++) { // stop at node before insertion point
            prev = prev.next; // safe because of bounds check above
        }
        LinkedList<T> node = new LinkedList<>(data);
        node.next = prev.next; // may be null when inserting at tail
        prev.next = node;
        length++; //  size++ for middle/tail insert
    }

    // Remove first occurrence of key
    void remove(T key) {
        if (head == null) { //  handle empty list early
            System.out.println("List is empty");
            return;
        }
        // check head match
        if (Objects.equals(head.data, key)) { //  null-safe comparison
            head = head.next;
            length--; //  maintain size
            return;
        }
        // traverse keeping prev pointer
        LinkedList<T> prev = head;
        LinkedList<T> curr = head.next;
        while (curr != null) {
            if (Objects.equals(curr.data, key)) { // compare by equals, not String.valueOf
                prev.next = curr.next; // unlink
                length--; //  maintain size
                return; // stop after first removal
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("Given value is not present in linked list"); // not found
    }

    // Clear entire list
    void clear() {
        head = null; // GC will reclaim nodes
        length = 0;
    }

    // Is list empty?
    boolean empty() { // could be named isEmpty() by convention; kept for compatibility
        return head == null;
    }

    // Current length
    int length() { return this.length; }

    // Display the list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ "); //  faster than repeated string concatenation
        LinkedList<T> x = head;
        while (x != null) {
            sb.append(String.valueOf(x.data));
            x = x.next;
            if (x != null) sb.append(" -> ");
        }
        sb.append(" }");
        return sb.toString();
    }
}
