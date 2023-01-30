import compress.HuffmanCompression;
import decompress.HuffmanDecompression;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the file name : ");
        String originalFile = sc.nextLine();
        sc.close();

        String compressedFile = "compress.txt";

        long start = System.currentTimeMillis();
        HuffmanCompression hc = new HuffmanCompression(originalFile);
        hc.compress();
        long end = System.currentTimeMillis();
        System.out.println("Compress : "+(end - start) + " ms");

        start = System.currentTimeMillis();
        HuffmanDecompression hd = new HuffmanDecompression(compressedFile);
        hd.decompression();
        end = System.currentTimeMillis();
        System.out.println("Decompress : "+(end - start) + " ms");

    }
}