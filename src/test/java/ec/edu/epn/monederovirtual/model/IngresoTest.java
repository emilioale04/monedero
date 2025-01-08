package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class IngresoTest {

    private Cuenta cuentaDestino;
    private Ingreso ingreso;

    @BeforeEach
    void setUp() {
        cuentaDestino = new Cuenta("Cuenta Destino", 100.0);
    }

    @Test
    void testConstructor() {
        ingreso = new Ingreso(cuentaDestino, 50.0, "Depósito");
        assertEquals(50.0, ingreso.getValor());
        assertEquals("Depósito", ingreso.getConcepto());
        assertEquals("Cuenta Destino", ingreso.getCuentaDestino().getNombre());
    }

    @ParameterizedTest
    @ValueSource(doubles = {50.0, 100.0, 200.0})
    void testRealizarTransaccion(double valorIngreso) throws ValorNoValido {
        ingreso = new Ingreso(cuentaDestino, valorIngreso, "Depósito");
        double balanceInicial = cuentaDestino.getBalance();

        ingreso.realizarTransaccion();

        assertEquals(balanceInicial + valorIngreso, cuentaDestino.getBalance(),
                "El balance de la cuenta destino debe incrementarse correctamente.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.0, 0.0})
    void testValidarValorNoValido(double valorIngreso) {
        ingreso = new Ingreso(cuentaDestino, valorIngreso, "Depósito");

        ValorNoValido exception = assertThrows(ValorNoValido.class, () -> ingreso.validarValor());
        assertEquals("El valor de la transacción no puede ser menor o igual a 0.", exception.getMessage());
    }
}