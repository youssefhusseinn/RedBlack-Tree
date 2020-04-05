import java.util.*;

public class RBTree {

    Node root;

    public RBTree(Node root){
        this.root = root;
    }

    public void insert(String word){
       Node node = new Node(word);
       this.root = findNewPlace(root,node);
       fixTree(node);
    }

    private void fixTree(Node node){

        while(node != this.root && node.color.equals("red") && node.parent.color.equals("red")){
            //case 1: parent of node is the left child of the node's grandparent
            if(node.parent == node.parent.parent.left){
                Node uncle = node.parent.parent.right;
                //case 1.1: uncle is also red, then only recolor.
                if(uncle != null && uncle.color.equals("red")){
                    node.parent.parent.color = "red";
                    node.parent.color = "black";
                    uncle.color = "black";
                    node = node.parent.parent; //recheck the parent now.
                }
                else{
                    if(node == node.parent.right){
                        //case 1.2: if node is right child of it's parent (left->right), then double rotation is required.
                        node = node.parent;
                        leftRotate(node);
                    }
                    //case 1.3: node is left child of its parent (left->left), then right rotation is required.
                    //swap parent and grandparent color
                    node.parent.color = "black";
                    node.parent.parent.color = "red";
                    rightRotate(node.parent.parent);
                }
            }
            else{
                Node uncle = node.parent.parent.left;
                //case 1.1: uncle is also red, then only recolor.
                if(uncle != null && uncle.color.equals("red")){
                    node.parent.parent.color = "red";
                    node.parent.color = "black";
                    uncle.color = "black";
                    node = node.parent.parent; //recheck grandparent
                }
                else{
                    if(node == node.parent.left){
                        //case 1.2: if node is right child of it's parent (right->left), then double rotation is required.
                        node = node.parent;
                        rightRotate(node);
                    }
                    //case 1.3: node is left child of its parent (right->right), then right rotation is required.
                    //swap parent and grandparent colors.
                    node.parent.color = "black";
                    node.parent.parent.color = "red";
                    leftRotate(node.parent.parent);
                }
            }
        }
        this.root.color = "black";
    }

    private void leftRotate(Node node){
        Node tempRight = node.right;
        node.right = tempRight.left;
        if(node.right != null)
            node.right.parent = node;
        tempRight.parent = node.parent;
        if(node.parent == null)
            this.root = tempRight;
        else if(node == node.parent.right)
            node.parent.right = tempRight;
        else
            node.parent.left = tempRight;
        tempRight.left = node;
        node.parent = tempRight;
    }

    private void rightRotate(Node node){
        Node tempLeft = node.left;
        node.left = tempLeft.right;
        if(node.left != null)
            node.left.parent = node;
        tempLeft.parent = node.parent;
        if(node.parent == null)
            this.root = tempLeft;
        else if(node == node.parent.left)
            node.parent.left = tempLeft;
        else
            node.parent.right = tempLeft;
        tempLeft.right = node;
        node.parent = tempLeft;
    }

    private Node findNewPlace(Node root, Node node){
        if(root == null)
            return node;
        if(node.val.compareToIgnoreCase(root.val) > 0){
            root.right = findNewPlace(root.right,node);
            root.right.parent = root;
        }
        else if(node.val.compareToIgnoreCase(root.val) < 0){
            root.left = findNewPlace(root.left,node);
            root.left.parent = root;
        }
        return root;
    }

    public boolean search(Node root, String key){
        if(root == null)
            return false;
        if(key.compareToIgnoreCase(root.val) == 0)
            return true;
        if(key.compareToIgnoreCase(root.val) > 0)
            return search(root.right,key);
        return search(root.left,key);
    }

    public int treeHeight(Node root){ return root == null? -1 : 1 + Math.max(treeHeight(root.left),treeHeight(root.right)); }

    public int treeSize(Node root){
        return root == null? 0 : 1 + treeSize(root.left) + treeSize(root.right);
    }

    public void printTree(Node root){
        System.out.println(" 1 - Breadth First Traversal\n 2 - Depth First Traversal\n 3 - In-Order Traversal\n 4 - Pre-Order Traversal\n 5 - Post-Order Traversal\n");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        List<String> list = new ArrayList<>();

        switch(choice){
            case 1:
                System.out.println("\n"+"Breadth First Traversal");
                bfs(root,list);
                break;
            case 2:
                System.out.println("\n"+"Depth First Traversal");
                dfs(root,list);
                break;
            case 3:
                System.out.println("\n"+"In-Order Traversal");
                inorder(root, list);
                break;
            case 4:
                System.out.println("\n"+"Pre-Order Traversal");
                preorder(root, list);
                break;
            case 5:
                System.out.println("\n"+"Post-Order Traversal");
                postorder(root, list);
                break;
            default:
                System.out.println("Invalid choice.");
                printTree(root);
        }

        System.out.println(list+"\n");
        list.clear();
    }

    private void bfs(Node root,List<String> list){
        if(root == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            list.add(cur.val+"  "+cur.color);
            if (cur.left != null)
                queue.offer(cur.left);
            if(cur.right != null)
                queue.offer(cur.right);
        }
    }

    private void dfs(Node root, List<String> list){
        if(root == null)
            return;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            list.add(cur.val+"  "+cur.color);
            if (cur.right != null)
                stack.push(cur.right);
            if(cur.left != null)
                stack.push(cur.left);
        }
    }

    private void inorder(Node root,List<String> list){
        if(root == null)
            return;
        inorder(root.left,list);
        list.add(root.val+"  "+root.color);
        inorder(root.right,list);
    }

    private void preorder(Node root,List<String> list){
        if(root == null)
            return;
        list.add(root.val+"  "+root.color);
        inorder(root.left,list);
        inorder(root.right,list);
    }

    private void postorder(Node root,List<String> list){
        if(root == null)
            return;
        inorder(root.left,list);
        inorder(root.right,list);
        list.add(root.val+"  "+root.color);
    }
}