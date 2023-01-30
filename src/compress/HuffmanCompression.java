package compress;

import treeNode.CharComparator;
import treeNode.Node;

import java.io.*;
import java.util.*;

public class HuffmanCompression implements Compression {
    String path;
    StringBuilder s = new StringBuilder();
    Map<Character, Integer> mp = new HashMap<>();
    PriorityQueue<Node> pq = new PriorityQueue<>(new CharComparator());
    Map<Character, String> table = new HashMap<>();


    public HuffmanCompression(String path){
        this.path=path;
    }

    public void generateCharFreq(){
        try {
          FileReader fr=new FileReader(path);
            int val = fr.read();
            while (val != -1){

                char ch = (char) val;
                mp.put(ch,(mp.getOrDefault(ch,0)+1));
                s.append(ch);
                val = fr.read();
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Node generateTree(){
        for(Map.Entry<Character, Integer> e : mp.entrySet()) {
//            System.out.println(e.getKey()+" : "+ e.getValue());
            Node temp = new Node(e.getKey(), e.getValue());
            pq.add(temp);
        }

        // if single node
        if(pq.size()==1){
            Node single = pq.poll();
            Node temp = new Node(single.value,single.weight);
            temp.left=single;
            temp.right=new Node(0,0);
            pq.add(temp);
        }

        while (pq.size() != 1){
            Node l = pq.poll();
            Node r = pq.poll();
            Node temp = new Node(l.value+r.value,l.weight+ r.weight);
            temp.left=l;
            temp.right=r;
            pq.add(temp);
        }

        return pq.peek();
    }

    void getTable(Node root, String str) {
        if(root==null)
            return;
        if (root.left == null && root.right == null) {
            table.put((char) root.value, str);
            return;
        }
        getTable(root.left, str + "0");
        getTable(root.right,str + "1");
    }

    void generateTable(Node root){
        String temp="";
        getTable(root, temp);

//        for(Map.Entry<Character, String> e : table.entrySet()) {
//            System.out.println(e.getKey()+" | "+e.getValue());
//        }
    }

    public void compress(){
        generateCharFreq();
        Node root = generateTree();
        generateTable(root);

        File f = new File("compress.txt");

        StringBuilder bitStr=new StringBuilder();
        try {
            for (char c : s.toString().toCharArray()) {
                String bit = table.get(c);
                bitStr.append(bit);
            }

            int paddedZeros=7-(bitStr.length()%7);
            bitStr.append("0".repeat(paddedZeros));

            StringBuilder CompressedStr=new StringBuilder();

            for(int i=0; i< bitStr.length(); i=i+7) {
                String t = bitStr.substring(i, i+7);
                char ch = (char) Integer.parseInt(t, 2);
                CompressedStr.append(ch);
            }

            FileOutputStream fout = new FileOutputStream("compress.txt");

            ObjectOutputStream out =new ObjectOutputStream(fout);
            out.writeObject(root);
            out.writeInt(paddedZeros);
            out.writeObject(CompressedStr.toString());
            out.close();
            fout.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
