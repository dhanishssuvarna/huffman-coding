package treeNode;

import java.io.Serializable;

/**
 * The type Node.
 */
public class Node implements Serializable {
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
     * @param value  the ascii of the char
     * @param weight the frequency of char
     */
    public Node(int value, int weight) {
        this.value = value;
        this.weight=weight;
        right = null;
        left = null;
    }
}