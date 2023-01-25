package decompress;

import treeNode.Node;

import java.io.*;
import java.util.*;

/**
 * The type Huffman decompression.
 */
public class HuffmanDecompression implements Decompression {

    /**
     * The Path.
     */
    String path;
    /**
     * The Str.
     */
    StringBuilder str = new StringBuilder();

    /**
     * Instantiates a new Huffman decompression.
     *
     * @param path the path
     */
    public HuffmanDecompression(String path){
        this.path=path;
    }

     public void getContent(){
        String curLine=null;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            while ((curLine = bf.readLine()) != null){
                str.append(curLine);
            }
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recreate tree node.
     *
     * @param s the s
     * @return the node
     */
    Node recreateTree(String s){
        Stack<Node> st = new Stack<>();

        int i=0;
        while(i<s.length()){
            if(s.charAt(i)=='1'){
                Node temp = new Node(s.charAt(++i), 0);
                st.push(temp);
            }else{
                if(st.size() == 1)
                    return st.pop();
                Node r = st.pop();
                Node l = st.pop();
                Node temp = new Node(0, 0);
                temp.left=l;
                temp.right=r;
                st.push(temp);
            }
            i++;
        }
        return null;
    }

    /**
     * Get bit string.
     *
     * @param bits  the bits
     * @param start the start
     * @param end   the end
     */
    void getBitString(StringBuilder bits, int start, int end){
        for(char c:str.substring(start, end).toString().toCharArray()){
            StringBuilder binary = new StringBuilder();
            while (c > 0 ) {
                binary.append( c % 2 );
                c >>= 1;
            }
            binary.append("0".repeat(Math.max(0, 8 - binary.length())));
            bits.append(binary.reverse().toString());
        }
    }

    public void decompression(){
        getContent();
        System.out.println(str.toString());
        System.out.println("Total Size : "+str.toString().length());

        int n = str.charAt(0);
        int postSize = str.charAt(1);
        String postOrder = str.substring(2, postSize+2);

        System.out.println("n : "+n);
        System.out.println("postSize : "+postSize);
        System.out.println("postOrder : "+postOrder);

        Node root = recreateTree(postOrder);

        StringBuilder bits = new StringBuilder();
        getBitString(bits, postSize+2, str.length());

        try {
            int i=0, charCnt=0;
            FileWriter fw = new FileWriter("decompress.txt");

            while (charCnt<n) {
                Node temp = root;
                while(temp.left != null && temp.right!= null){
                    if(bits.charAt(i) == '0'){
                        temp=temp.left;
                        i++;
                    }
                    else{
                        temp=temp.right;
                        i++;
                    }
                }
                System.out.print((char) temp.value);
                fw.write(temp.value);
                charCnt++;
            }
            System.out.println();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
