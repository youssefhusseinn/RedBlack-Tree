public class Node {
    String val;
    Node parent;
    Node left;
    Node right;
    String color;

    public Node(String val){
        this.color = "red";
        this.val = val;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
