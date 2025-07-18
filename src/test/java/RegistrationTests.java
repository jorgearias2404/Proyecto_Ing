import org.junit.Assert;
import org.junit.Test;

import controller.ValidarFormularioRegistro;

public class RegistrationTests {
    
    @Test
    public void testForm() {
        controller.ValidarFormularioRegistro val = new ValidarFormularioRegistro();
        //caso base valido, para las demas pruebas se utilizaran casos que sabemos que son validos para rellenar los demas campos
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "usuarOo123", "sergiogo", "30142272", "Estudiante"));

        //pruebas email :== nulo/vacio, invalido
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail", "usuarOo123", "sergiogo", "30142272", "Estudiante"));
        Assert.assertEquals(false, val.validateForm("", "usuarOo123", "sergiogo", "30142272", "Estudiante"));

        //pruebas contraseña :== valido > 15 caracteres, 8 con minus y num
        //nulo/vacio, menos de 15 caracteres, 8 caracteres pero sin minuscula o mayuscula, muy pocos caracteres
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contrasenamuylarga", "sergiogo", "30142272", "Estudiante")); 
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contra123", "sergiogo", "30142272", "Estudiante")); 
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "contraer", "sergiogo", "30142272", "Estudiante")); 
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "a1", "sergiogo", "30142272", "Estudiante")); 
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "", "sergiogo", "30142272", "Estudiante")); 


        //pruebas username
        //nulo/vacio, sin caracteres alfanumericos
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "contra123", "sergiogo!", "30142272", "Estudiante"));
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "30142272", "Estudiante"));
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "contra123", "", "30142272", "Estudiante"));

        //Pruebas id
        //nulo/vacio, sin caracteres numericos
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "juan", "Estudiante"));
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "12", "Estudiante"));

        //pruebas rol
        //posibles casos: Estudiante, Profesor, Empleado, Admin y Rol (que deberia retornar falso)
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "30142272", "Estudiante"));
        Assert.assertEquals(true, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "30142272", "Empleado"));
        Assert.assertEquals(false, val.validateForm("sergioferg.2003@gmail.com", "contra123", "123456", "30142272", "Rol"));

        Assert.assertEquals("Debe seleccionar un rol", val.errorMessage);
    }
}
