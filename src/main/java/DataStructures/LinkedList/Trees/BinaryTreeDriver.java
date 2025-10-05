package DataStructures.LinkedList.Trees;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryTreeDriver {
    public static void main (String[] args) {
        System.out.println("You're in binary tree driver!");
        BinaryTree bt = new BinaryTree();
        bt.createSampleTree();

        System.out.println("PreOrder traversal: ");
        bt.preOrder(bt.root);
        System.out.println("\n");

        System.out.println("PostOrder traversal: ");
        bt.postOrder(bt.root);
        System.out.println("\n");

        System.out.println("InOrder traversal: ");
        bt.inOrder(bt.root);
        System.out.println("\n");

        System.out.println("LevelOrder traversal: ");
        bt.levelOrder(bt.root);
        System.out.println("\n");
    }
}

class BinaryTree {

    Node root;

    // Node will only be used with binary tree only, therefore we made it static.
    static class Node {

        // Pointers for left, right nodes
        Node left;
        Node right;

        int value;

        // Node constructor will receive a primitive integer value;
        Node(int value) {
            this.value = value;
        }

    }

    // Binary Tree doesn't have order constraints
    void createSampleTree() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);
        Node n9 = new Node(9);
        Node n10 = new Node(10);

        root = n1;

        n1.left = n2;
        n1.right = n3;

        n2.left = n4;
        n2.right = n5;

        n3.left = n6;
        n3.right = n7;

        n4.left = n8;
        n4.right = n9;

        n5.left  = n10;

    }

    // Process parent; root -> left -> right; usage: Copy a tree, Serialize a tree, build prefix expression tree
    void preOrder(Node node) {
        if (node == null) return;

        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // Process children; left -> right -> root;
    // usage: 1. tree deletion (delete children before parents)
    //        2. Expression Trees: It naturally evaluates expressions in postfix (Reverse Polish Notation)
    //        3. Dependency Resolution: if a node depends on it's children, postorder ensures that all dependencies are handled first
    void postOrder(Node node) {
        if (node == null) return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }

    // Process: left subtree, current node , right subtree
    // Usage: 1. Print in sorted order
    //        2. Validate a BST
    //        3. When converting a tree to a sorted list or array
    void inOrder(Node node) {
        if (node == null) return;

        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);

    }

    // BFS
    // To solve: Tree Structure Visibility, Layered Processing, Shortest Path Logic (unweighted), ZigZag or Spiral Traversal
    void levelOrder(Node node) {
        Deque<Node> dq = new ArrayDeque<>();
        // Remember: You can't putt null in deque so the null, insert implementation will fail.

        dq.addLast(node);

        while (!dq.isEmpty()) {

            int size = dq.size();

            for (int i = 0; i < size; i++) {

                Node n = dq.removeFirst();

                System.out.print(n.value + " ");

                if (n.left != null) {
                    dq.addLast(n.left);
                }

                if (n.right != null) {
                    dq.addLast(n.right);
                }
            }
            System.out.println();
        }
    }



    // Reverse Inorder (Right, Root, Left), Gives descending order in a BST

    // Spiral / Zig-Zag level order, Like Zig-Zag, but alternates to left-and-right

    // Boundary traversal, left edges, leaves, right edge

    // Diagonal Traversal, groups nodes based on diagonals slanting down the tree



}
