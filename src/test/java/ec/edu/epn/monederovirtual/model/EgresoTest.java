package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EgresoTest {

    private Cuenta cuentaOrigen;
    private Egreso egreso;

    @BeforeEach
    void setUp() {
        cuentaOrigen = new Cuenta("Cuenta Origen", 200.0);
    }

    @Test
    void testConstructor() {
        egreso = new Egreso(cuentaOrigen, 50.0, "Pago de servicios");
        assertEquals(50.0, egreso.getValor());
        assertEquals("Pago de servicios", egreso.getConcepto());
        assertEquals("Cuenta Origen", egreso.getCuentaOrigen().getNombre());
    }

    @ParameterizedTest
    @ValueSource(doubles = {50.0, 100.0, 150.0})
    void testRealizarTransaccion(double valorEgreso) throws ValorNoValido, BalanceInsuficiente {
        egreso = new Egreso(cuentaOrigen, valorEgreso, "Compra");
        double balanceInicial = cuentaOrigen.getBalance();

        egreso.realizarTransaccion();

        assertEquals(balanceInicial - valorEgreso, cuentaOrigen.getBalance(),
                "El balance de la cuenta origen debe disminuir correctamente.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.0, 0.0})
    void testValidarValorNoValido(double valorEgreso) {
        egreso = new Egreso(cuentaOrigen, valorEgreso, "Compra");

        ValorNoValido exception = assertThrows(ValorNoValido.class, () -> egreso.validarValor());
        assertEquals("El valor de la transacción no puede ser menor o igual a 0.", exception.getMessage());
    }

    @Test
    void testBalanceInsuficiente() {
        egreso = new Egreso(cuentaOrigen, 300.0, "Transferencia");

        BalanceInsuficiente exception = assertThrows(BalanceInsuficiente.class, () -> egreso.realizarTransaccion());
        assertEquals("El balance de la cuenta es insuficiente.", exception.getMessage());
    }
}
