package ec.edu.epn.monederovirtual.model;

import ec.edu.epn.monederovirtual.model.exceptions.BalanceInsuficiente;
import jakarta.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructores
    public Cuenta() {
        this.balance = 0;
    }

    public Cuenta(String nombre, double balance) {
        this.nombre = nombre;
        this.balance = balance;
    }

    // Métodos
    public void retirarDinero(double valor) {
        this.balance -= valor;
    }

    public void depositarDinero(double valor) {
        this.balance += valor;
    }

    public boolean validarRetiro(double valor) throws BalanceInsuficiente {
        if (this.balance < valor) {
            throw new BalanceInsuficiente("El balance de la cuenta es insuficiente.");
        }
        return true;
    }


    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}