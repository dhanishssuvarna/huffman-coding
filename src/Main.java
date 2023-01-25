import compress.HuffmanCompression;
import decompress.HuffmanDecompression;

public class Main {

    public static void main(String[] args) {
//        String s = "go go gophers";
//        String s = "streets are stone stars are not";
//        String s = "T’ enjoy æ thy banish’d lord and this great land!";


        String originalFile = "test.txt";
        String compressedFile = "compress.txt";

        HuffmanCompression hc = new HuffmanCompression(originalFile);
        hc.compress();
        HuffmanDecompression hd = new HuffmanDecompression(compressedFile);
        hd.decompression();

    }
}