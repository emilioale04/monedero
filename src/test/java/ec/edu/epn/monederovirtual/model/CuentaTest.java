package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta("Cuenta de prueba", 100.0);
    }

    @Test
    void testConstructorConParametros() {
        assertEquals("Cuenta de prueba", cuenta.getNombre());
        assertEquals(100.0, cuenta.getBalance());
    }

    @Test
    void testRetirarDinero() {
        cuenta.retirarDinero(50.0);
        assertEquals(50.0, cuenta.getBalance());
    }

    @Test
    void testDepositarDinero() {
        cuenta.depositarDinero(30.0);
        assertEquals(130.0, cuenta.getBalance());
    }

    @Test
    void testValidarRetiroSuficiente() {
        assertDoesNotThrow(() -> cuenta.validarRetiro(50.0));
    }

    @Test
    void testValidarRetiroInsuficiente() {
        BalanceInsuficiente exception = assertThrows(BalanceInsuficiente.class, () -> cuenta.validarRetiro(150.0));
        assertEquals("El balance de la cuenta es insuficiente.", exception.getMessage());
    }
}
