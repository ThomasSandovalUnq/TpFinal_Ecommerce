package ar.unq.edu.poo2.Pedido;

public class Cancelado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "CANCELADO"; }
    // Estado terminal: ninguna operación válida.

    @Override
    public boolean esCancelado() { return true; }
}