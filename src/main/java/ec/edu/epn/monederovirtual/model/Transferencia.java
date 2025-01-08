package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import jakarta.persistence.*;

@Entity
@Table(name = "transferencias")
public class Transferencia extends Movimiento {

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;

    // Constructores
    public Transferencia() {
    }

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    // Métodos
    @Override
    public void realizarTransaccion() throws ValorNoValido, BalanceInsuficiente {
        if(validarValor() && cuentaOrigen.validarRetiro(valor)) {
            cuentaOrigen.retirarDinero(valor);
            cuentaDestino.depositarDinero(valor);
        }
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}