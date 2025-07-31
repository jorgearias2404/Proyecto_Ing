package Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ModeloImagen {
    String imgPath;
    InputStream imgInput_;
    boolean couldLoad;

    public ModeloImagen(String path) throws IOException {
        this.imgPath = path;
        try(InputStream imgInput = new FileInputStream(imgPath)) {
            imgInput_ = imgInput;
        }
    }
}
