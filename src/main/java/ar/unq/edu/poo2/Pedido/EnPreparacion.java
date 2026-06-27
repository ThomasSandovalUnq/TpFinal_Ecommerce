package ar.unq.edu.poo2.Pedido;

public class EnPreparacion extends EstadoBase {

    @Override
    protected String nombreEstado() { return "EN_PREPARACION"; }

    @Override
    public void enviar(Pedido pedido) {
    	EstadoPedido estadoViejo = this;
        pedido.setEstado(new Enviado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        // Cancelar desde EN_PREPARACION (versión nueva del enunciado):
        // repone stock + reembolsa producto Y envío (nota de crédito total).
    	EstadoPedido estadoViejo = this;
        pedido.reembolsarTotal();
        pedido.incrementarStock();
        pedido.setEstado(new Cancelado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }
}