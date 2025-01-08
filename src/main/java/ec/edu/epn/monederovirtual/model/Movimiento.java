package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.ValorNoValido;
import ec.edu.epn.monederovirtual.util.DateFormatter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected double valor;
    protected String concepto;
    protected LocalDateTime fecha;

    @Transient
    protected String fechaFormateada;

    // Constructores
    public Movimiento() {
    }

    public Movimiento(double valor, String concepto) {
        this.valor = valor;
        this.concepto = concepto;
    }

    // Métodos

    public abstract void realizarTransaccion();

    protected boolean validarValor() throws ValorNoValido {
        if (this.valor <= 0) {
            throw new ValorNoValido("El valor de la transacción no puede ser menor o igual a 0.");
        }
        return true;
    }

    public void formatearFecha(String pattern) {
        this.fechaFormateada = DateFormatter.formatLocalDateTime(this.fecha, pattern);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }
}
