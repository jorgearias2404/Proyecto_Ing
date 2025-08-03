package Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ComparadorImagen {
    public static boolean areJpgsIdentical(String filePath1, String filePath2) throws IOException {
        try (InputStream is1 = new FileInputStream(filePath1);
                InputStream is2 = new FileInputStream(filePath2)) {

            // Compare file sizes first for a quick check
            if (is1.available() != is2.available()) {
                return false;
            }

            byte[] buffer1 = new byte[4096];
            byte[] buffer2 = new byte[4096];
            int bytesRead1;
            int bytesRead2;

            while ((bytesRead1 = is1.read(buffer1)) != -1) {
                bytesRead2 = is2.read(buffer2);

                // If one file ends before the other, they are not identical
                if (bytesRead1 != bytesRead2) {
                    return false;
                }

                // Compare the content of the buffers
                if (!Arrays.equals(buffer1, 0, bytesRead1, buffer2, 0, bytesRead2)) {
                    return false;
                }
            }
            // If both files have been read completely and all bytes matched
            return true;
        }
    }
}
