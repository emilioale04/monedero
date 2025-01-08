package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransferenciaTest {

    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Transferencia transferencia;

    @BeforeEach
    void setUp() {
        cuentaOrigen = new Cuenta("Cuenta Origen", 200.0);
        cuentaDestino = new Cuenta("Cuenta Destino", 100.0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {50.0, 100.0, 150.0})
    void testRealizarTransaccion(double valorTransferencia) throws ValorNoValido, BalanceInsuficiente {
        transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valorTransferencia, "Transferencia");
        double balanceInicialOrigen = cuentaOrigen.getBalance();
        double balanceInicialDestino = cuentaDestino.getBalance();

        transferencia.realizarTransaccion();

        assertEquals(balanceInicialOrigen - valorTransferencia, cuentaOrigen.getBalance(),
                "El balance de la cuenta origen debe disminuir correctamente.");
        assertEquals(balanceInicialDestino + valorTransferencia, cuentaDestino.getBalance(),
                "El balance de la cuenta destino debe incrementarse correctamente.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.0, 0.0})
    void testValidarValorNoValido(double valorTransferencia) {
        transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valorTransferencia, "Transferencia");

        ValorNoValido exception = assertThrows(ValorNoValido.class, () -> transferencia.validarValor());
        assertEquals("El valor de la transacción no puede ser menor o igual a 0.", exception.getMessage());
    }

    @Test
    void testBalanceInsuficiente() {
        double valorTransferencia = 300.0; // Mayor al balance de la cuenta origen
        transferencia = new Transferencia(cuentaOrigen, cuentaDestino, valorTransferencia, "Transferencia");

        BalanceInsuficiente exception = assertThrows(BalanceInsuficiente.class, () -> transferencia.realizarTransaccion());
        assertEquals("El balance de la cuenta es insuficiente.", exception.getMessage());
    }
}
