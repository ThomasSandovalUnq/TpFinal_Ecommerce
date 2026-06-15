package ar.unq.edu.poo2.Pedido;

public class Entregado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "ENTREGADO"; }
    // Estado terminal: ninguna operación válida.
}
