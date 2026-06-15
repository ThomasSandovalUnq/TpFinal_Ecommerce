package ar.unq.edu.poo2.Pedido;

public class Enviado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "ENVIADO"; }

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstado(new Entregado());
    }

    @Override
    public void cancelar(Pedido pedido) {
    	 // Cancelar desde ENVIADO: reembolso parcial (solo producto, sin envío).
        // CONSULTAR (2): ¿Se repone stock al cancelar desde ENVIADO?
        pedido.reembolsarParcial();
        pedido.setEstado(new Cancelado());
    }
}