package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import jakarta.persistence.*;

@Entity
@Table(name = "egresos")
public class Egreso extends Movimiento {

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    // Constructores
    public Egreso() {
    }

    public Egreso(Cuenta cuentaOrigen, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaOrigen = cuentaOrigen;
    }

    // Métodos
    @Override
    public void realizarTransaccion() throws ValorNoValido, BalanceInsuficiente {
        if(validarValor() && cuentaOrigen.validarRetiro(valor)) {
            cuentaOrigen.retirarDinero(valor);
        }
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

}
