package ar.unq.edu.poo2.Pedido;

public class OperacionInvalidaException extends RuntimeException {

    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}