public class HashST {

    private static final int CAPACITY = 16;
    Node<String, Player>[] table;

    @SuppressWarnings("unchecked")
    public HashST() {
        table = (Node<String, Player>[]) new Node[CAPACITY];
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % CAPACITY;
    }

    public void insert(Player p) {
        String key = p.Name;
        Player value = p;

        int idx = hash(key);
        Node<String, Player> node = table[idx];

        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.right;
        }

        Node<String, Player> newNode = new Node<>(key, value);
        newNode.right = table[idx];
        table[idx] = newNode;
    }

    public Player get(String key) {
        int idx = hash(key);
        Node<String, Player> node = table[idx];

        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.right;
        }

        return null;
    }

}