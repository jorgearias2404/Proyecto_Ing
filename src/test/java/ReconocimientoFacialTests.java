import org.junit.Assert;
import org.junit.Test;

import controller.ControladorImagen;


public class ReconocimientoFacialTests {

    @Test
    public void testComparacion() {
        ControladorImagen controller = new ControladorImagen(false);
        Assert.assertTrue(controller.hacerComparacion("Database/imagenes/jorge.jpg"));
        Assert.assertTrue(controller.hacerComparacion("Database/imagenes/sergio.jpg"));
        Assert.assertFalse(controller.hacerComparacion("Database/imagenes/fotoNoExistente.jpg"));

    }
}
