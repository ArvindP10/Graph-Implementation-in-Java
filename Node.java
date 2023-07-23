
public class Node {
    
    int val;
    int weight;
    Node next;

    public Node() {

        val = -1;
        weight = 0;
        next = null;
    }

    public Node(int val, int weight) {

        this.val = val;
        this.weight = weight;
        next = null;
    }
}
