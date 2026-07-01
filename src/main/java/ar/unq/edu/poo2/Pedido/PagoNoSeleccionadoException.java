package ar.unq.edu.poo2.Pedido;

public class PagoNoSeleccionadoException extends RuntimeException {
    public PagoNoSeleccionadoException(String mensaje) {
        super(mensaje);
    }
}