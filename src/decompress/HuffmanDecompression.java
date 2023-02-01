package decompress;
import treeNode.Node;
import java.io.*;
import java.util.ArrayList;

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

    public void decompression() {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);

            Node root = (Node) in.readObject();
            int paddedZeros = in.readInt();
            byte[] compressedString = (byte[]) in.readObject();

            in.close();
            fin.close();

            StringBuilder bitStr=new StringBuilder();
            for(byte b: compressedString){
                String curByte = String.format("%8s", Integer.toBinaryString((b+256)%256)).replace(' ', '0');
                bitStr.append(curByte);
            }

            Node temp=root;
            int i=0;
//            ArrayList<Byte> byteArr = new ArrayList<>();
            StringBuilder decompressedStr = new StringBuilder();
            while(i < bitStr.length()-paddedZeros)
            {
                while(temp.left != null && temp.right != null){
                    if(bitStr.charAt(i) == '0')
                        temp=temp.left;

                    if(bitStr.charAt(i) == '1')
                        temp=temp.right;
                    i++;
                }
//                byteArr.add((byte)temp.value);
                decompressedStr.append((char) temp.value);
                temp=root;
            }

//            byte[] res = new byte[byteArr.size()];
//            int k=0;
//            for(byte b: byteArr){
//                res[k++]=b;
//            }

//            FileOutputStream f=new FileOutputStream("decompress.txt");
            FileWriter f  = new FileWriter("decompress.txt");
            f.write(decompressedStr.toString());
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
