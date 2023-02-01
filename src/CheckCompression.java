import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

public class CheckCompression {
    public byte[] getByteArray(String filename){
        byte[] arr;
        try {
            File file = new File(filename);
            FileInputStream input = new FileInputStream(file);
            arr = new byte[(int) file.length()];
            input.read(arr);
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return arr;
    }


    public boolean CompareFiles(String file1,String file2){
        byte[] arr1 = getByteArray(file1);
        byte[] arr2 = getByteArray(file2);

        if(Arrays.equals(arr1,arr2))
            return true;
        else
            return false;
    }
}
