package treeNode;

import java.util.Comparator;

/**
 * The type CharComparator used to compare 2 nodes */
public class CharComparator implements Comparator<Node> {
    public int compare(Node a, Node b){
        if(a.weight == b.weight){
            return  a.value - b.value;
        }
        return a.weight - b.weight;
    }
}
