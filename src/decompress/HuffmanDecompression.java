package decompress;
import treeNode.Node;
import java.io.*;

/**
 * The type Huffman decompression.
 */
public class HuffmanDecompression implements Decompression {
    /**
     * The Path of the compressed file
     */
    String path;

    /**
     * Instantiates a new Huffman decompression.
     *
     * @param path the path
     */
    public HuffmanDecompression(String path){
        this.path=path;
    }

    /**
     * Generates the bit string for each char
     *
     * @param ch the ch
     * @return the string
     */
    String getBitString(char ch){
        int c=ch;
        StringBuilder binary = new StringBuilder();
        while (c > 0 ) {
            binary.append( c & 1 );
            c = c >> 1;
        }
        binary.append("0".repeat(7 - binary.length()));
        return binary.reverse().toString();
    }

    public void decompression() {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);

            Node root = (Node) in.readObject();
            int paddedZeros = in.readInt();
            String compressedString = (String) in.readObject();

            in.close();
            fin.close();

            StringBuilder bitStr=new StringBuilder();
            for(char c:compressedString.toCharArray()) {
                bitStr.append(getBitString(c));
            }

            Node temp=root;
            int i=0;
            StringBuilder decompressedStr=new StringBuilder();
            while(i < bitStr.length()-paddedZeros)
            {
                while(temp.left != null && temp.right != null){
                    if(bitStr.charAt(i) == '0')
                        temp=temp.left;

                    if(bitStr.charAt(i) == '1')
                        temp=temp.right;
                    i++;
                }
                decompressedStr.append((char)temp.value);
                temp=root;
            }

            FileWriter fw = new FileWriter("decompress.txt");
            fw.write(decompressedStr.toString());
            fw.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
