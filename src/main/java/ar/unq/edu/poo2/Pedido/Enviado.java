package ar.unq.edu.poo2.Pedido;

public class Enviado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "ENVIADO"; }

    @Override
    public void entregar(Pedido pedido) {
    	EstadoPedido estadoViejo = this;
        pedido.registrarEntrega();
        pedido.setEstado(new Entregado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }
    
    @Override
    public void cancelar(Pedido pedido) {
    	 // Cancelar desde ENVIADO: reembolso parcial (solo producto, sin envío).
        // CONSULTAR (2): ¿Se repone stock al cancelar desde ENVIADO?
    	EstadoPedido estadoViejo = this;
        pedido.reembolsarParcial();
        pedido.setEstado(new Cancelado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }
}