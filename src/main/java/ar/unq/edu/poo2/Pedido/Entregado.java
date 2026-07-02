package ar.unq.edu.poo2.Pedido;

public class Entregado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "ENTREGADO"; }

    @Override
    public boolean esEntregado() { return true; }
    // Estado terminal: ninguna operación válida.
}
