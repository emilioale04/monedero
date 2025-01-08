package ec.edu.epn.monederovirtual.model.exceptions;

public class BalanceInsuficiente extends RuntimeException {
    public BalanceInsuficiente(String message) {
        super(message);
    }
}
