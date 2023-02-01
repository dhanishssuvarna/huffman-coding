import compress.HuffmanCompression;
import decompress.HuffmanDecompression;

import java.io.*;
import java.util.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the file name : ");
        String originalFile = sc.nextLine();
        sc.close();

        String compressedFile = "compress.txt";
        String decompressedFile = "decompress.txt";

        System.out.println("\nCompressing File........");
        HuffmanCompression hc = new HuffmanCompression(originalFile);
        long start = System.currentTimeMillis();
        hc.compress();
        long end = System.currentTimeMillis();
        long compressionTime = end - start;

        System.out.println("Decompressing File........");
        HuffmanDecompression hd = new HuffmanDecompression(compressedFile);
        start = System.currentTimeMillis();
        hd.decompression();
        end = System.currentTimeMillis();
        long decompressionTime = end - start;

        File of = new File(originalFile);
        long ofl = of.length();

        File cf = new File(compressedFile);
        long cfl = cf.length();

        File df = new File(decompressedFile);
        long dfl = df.length();

        CheckCompression cc = new CheckCompression();
        boolean isEqual = cc.CompareFiles(originalFile, decompressedFile);

        if(isEqual){
            System.out.println("\n********** Operation Successful **********\n");
            System.out.println("Compress Time : " + compressionTime + " ms");
            System.out.println("Decompress Time : " + decompressionTime + " ms");
            System.out.println("Original File Size : " + (float) ofl/(1024*1024) + " MB");
            System.out.println("Compressed File Size : " + (float) cfl/(1024*1024) + " MB");
            System.out.println("Decompressed File Size : " + (float) dfl/(1024*1024) + " MB");
            System.out.println("Compression Ratio : " + ((float)(ofl-cfl)/ofl)*100 + " %");
        }else {
            System.out.println("\n********** Operation Failed **********\n");
        }
    }
}