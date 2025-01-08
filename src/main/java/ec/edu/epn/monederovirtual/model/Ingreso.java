package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import jakarta.persistence.*;

@Entity
@Table(name = "ingresos")
public class Ingreso extends Movimiento {

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;

    // Constructores
    public Ingreso() {
    }

    public Ingreso(Cuenta cuentaDestino, double valor, String concepto) {
        super(valor, concepto);
        this.cuentaDestino = cuentaDestino;
    }

    // Métodos
    @Override
    public void realizarTransaccion() throws ValorNoValido {
        validarValor();
        cuentaDestino.depositarDinero(this.valor);
    }

    // Getters y Setters
    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}
