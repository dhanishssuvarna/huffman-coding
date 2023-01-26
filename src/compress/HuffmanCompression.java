package compress;

import treeNode.Node;

import java.io.*;
import java.util.*;

/**
 * The type Huffman compression.
 */
public class HuffmanCompression implements Compression {
    /**
     * The Path of the file
     */
    String path;
    /**
     * The S is used to store the file content
     */
    StringBuilder s = new StringBuilder();


    /**
     * Instantiates a new Huffman compression.
     *
     * @param path the path of input file
     */
    public HuffmanCompression(String path){
        this.path=path;
    }

    public void getContent(){
        String curLine;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            while ((curLine = bf.readLine()) != null){
                s.append(curLine);
                s.append("\n");
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate char freq.
     *
     * @param mp the mp contains the frequency of character in the file
     */
    void generateCharFreq(Map<Character, Integer> mp){
        for (int i=0; i<s.length(); i++) {
            char c=s.charAt(i);
            if(mp.containsKey(c))
                mp.put(c,mp.get(c)+1);
            else
                mp.put(c,1);
        }
    }

    /**
     * Generate tree and return the root node of the tree
     *
     * @param mp the char freq
     * @param pq the pq
     * @return the root node
     */
    Node generateTree(Map<Character, Integer> mp, PriorityQueue<Node> pq){
        for(Map.Entry<Character, Integer> e : mp.entrySet()) {
            System.out.println(e.getKey()+" : "+ e.getValue());
            Node temp = new Node(e.getKey(), e.getValue());
            pq.add(temp);
        }

        while (pq.size() != 1){
            Node l = pq.poll();
            Node r = pq.poll();
            Node temp = new Node(0,l.weight+ r.weight);
            temp.left=l;
            temp.right=r;
            pq.add(temp);
        }

        return pq.peek();
    }

    /**
     * Gets table.
     *
     * @param root  the root
     * @param table the table
     * @param str   the str
     */
    void getTable(Node root, Map<Character, String> table, String str) {
        if(root==null)
            return;
        if (root.left == null && root.right == null) {
            table.put((char) root.value, str);
            return;
        }
        getTable(root.left, table, str + "0");
        getTable(root.right, table,str + "1");
    }

    /**
     * Generate table.
     *
     * @param root  the root
     * @param table the table
     */
    void generateTable(Node root, Map<Character, String> table){
        String temp="";
        getTable(root, table, temp);

        for(Map.Entry<Character, String> e : table.entrySet()) {
            System.out.println(e.getKey()+" | "+e.getValue());
        }
    }

    /**
     * Post order.
     *
     * @param n  the node
     * @param ls the ls
     */
    void postOrder(Node n, List<String> ls){
        if(n == null){
            return;
        }
        postOrder(n.left, ls);
        postOrder(n.right, ls);
        if ((n.value != 0)) {
            ls.add("1" + (char) n.value);
        } else {
            ls.add("0");
        }
    }

    /**
     * Huff post order string.
     *
     * @param n the n
     * @return the string
     */
    String HuffPostOrder(Node n){
        String res="";
        List<String> ls = new ArrayList<>();

        postOrder(n, ls);
        for(String str:ls){
            res+=str;
        }
        res+="0";
        return res;
    }

    public void compress(){
        Map<Character, Integer> mp = new HashMap<Character, Integer>();
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        Map<Character, String> table = new HashMap<Character, String>();

        getContent();
        generateCharFreq(mp);
        Node root = generateTree(mp, pq);
        generateTable(root, table);

        int n = s.length();
        String postOrderStr = HuffPostOrder(root);
        int postOrderStrSize = postOrderStr.length();
        File f=new File("compress.txt");

        System.out.println("n : "+n);
        System.out.println("postSize : "+postOrderStrSize);
        System.out.println("postOrder : "+postOrderStr);

        String bitStr="";
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(Integer.toString(n)+","+Integer.toString(postOrderStrSize)+System.lineSeparator());
            fw.write(postOrderStr);
            for(char c:s.toString().toCharArray()){
                String bit = table.get(c);
                bitStr+=bit;

                if(bitStr.length() > 8) {
                    String t = bitStr.substring(0, 8);
                    char ch = (char) Integer.parseInt(t, 2);
                    fw.write(ch);
                    bitStr = bitStr.substring(8, bitStr.length());
                }
            }
            int size = 8-bitStr.length();
            for (int i=0; i<size; i++){
                bitStr+='0';
            }
            char ch = (char) Integer.parseInt(bitStr, 2);
            fw.write(ch);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
