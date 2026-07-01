import java.util.ArrayList;
import java.util.List;

public class BST {

    private Node<Integer, String> root;

    public void insert(Player p) {
        root = insertRec(root, p.Wins, p.Name);
    }

    private Node<Integer, String> insertRec(Node<Integer, String> node, int key, String value) {
        if (node == null) {
            return new Node<>(key, value);
        }

        if (key < node.key) {
            node.left = insertRec(node.left, key, value);
        } else if (key > node.key) {
            node.right = insertRec(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

    public String get(int key) {
        Node<Integer, String> current = root;
        while (current != null) {
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public Node<Integer, String> getRoot() {
        return root;
    }

    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node<Integer, String> deleteRec(Node<Integer, String> node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = deleteRec(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRec(node.right, key);
        } else {
            // Caso 1: sin hijos
            if (node.left == null && node.right == null) {
                return null;
            }
            // Caso 2: un solo hijo
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Caso 3: dos hijos, reemplazar por el mínimo del subárbol derecho
            Node<Integer, String> min = findMin(node.right);
            node.key = min.key;
            node.value = min.value;
            node.right = deleteRec(node.right, min.key);
        }

        return node;
    }

    private Node<Integer, String> findMin(Node<Integer, String> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> keysInRange(int lo, int hi) {
        List<Integer> result = new ArrayList<>();
        collectKeysInRange(root, lo, hi, result);
        return result;
    }

    private void collectKeysInRange(Node<Integer, String> node, int lo, int hi, List<Integer> result) {
        if (node == null) return;

        if (lo < node.key) collectKeysInRange(node.left, lo, hi, result);
        if (lo <= node.key && node.key <= hi) result.add(node.key);
        if (hi > node.key) collectKeysInRange(node.right, lo, hi, result);
    }

    public Integer successor(int key) {
        Node<Integer, String> current = root;
        Integer succ = null;

        while (current != null) {
            if (key < current.key) {
                succ = current.key;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return succ;
    }



}