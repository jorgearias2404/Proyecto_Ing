import org.junit.Assert;
import org.junit.Test;
import controller.CalculadoraCCB;

public class CargaCostosTest {

    @Test
    public void costoTest() {
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("abc", "1", ".32", "23"), 1.0);
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("13", "a", "53", "42"), 1.0);
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("24", "125", "ses", "52"), 1.0);
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("42", "1", "42", "aaaa"), 1.0);
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("21", "1", "53", "101"), 1.0);
        Assert.assertEquals(0.0, CalculadoraCCB.calcularCCB("4221", "1234", "53", "-1"), 1.0);
    }
}
