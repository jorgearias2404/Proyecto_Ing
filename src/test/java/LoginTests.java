import org.junit.Assert;
import org.junit.Test;

import Services.AuthService;

public class LoginTests {

    private AuthService authService;

    @Test
    public void testLogin() {

        this.authService = new AuthService();

        Assert.assertEquals(false, this.authService.validarLogin("", "", true));
        Assert.assertEquals(false, this.authService.validarLogin("juan", "123", true));
        Assert.assertEquals(true, this.authService.validarLogin("test", "123", false));
        Assert.assertEquals(false, this.authService.validarLogin("admin", "", true));
        Assert.assertEquals(false, this.authService.validarLogin("test", "123", true));

    }
}
