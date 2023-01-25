package treeNode;

/**
 * The type Node.
 */
public class Node implements Comparable<Node>{
    /**
     * The Value of the node
     */
    public int value;
    /**
     * The Weight is the freq of value
     */
    public int weight;
    /**
     * The Left pointer
     */
    public Node left;
    /**
     * The Right pointer
     */
    public Node right;

    /**
     * Instantiates a new Node.
     *
     * @param value  the value
     * @param weight the weight
     */
    public Node(int value, int weight) {
        this.value = value;
        this.weight=weight;
        right = null;
        left = null;
    }

    public int compareTo(Node n) {
        if(this.weight != n.weight)
            return this.weight - n.weight;
        else{
            // both leaf
            if(this.left == null && this.right == null && n.left == null && n.right == null)
                return this.value - n.value;
            // both non leaf
            else if(this.left != null && this.right != null && n.left != null && n.right != null)
                return 0;
            // one leaf - one non leaf
            else{
                if(this.left == null && this.right == null)
                    return -1;
                else
                    return 1;
            }
        }
    }
}