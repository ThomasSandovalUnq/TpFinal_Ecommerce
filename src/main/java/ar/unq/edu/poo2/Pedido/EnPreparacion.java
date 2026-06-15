package ar.unq.edu.poo2.Pedido;

public class EnPreparacion extends EstadoBase {

    @Override
    protected String nombreEstado() { return "EN_PREPARACION"; }

    @Override
    public void enviar(Pedido pedido) {
        pedido.setEstado(new Enviado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        // Cancelar desde EN_PREPARACION (versión nueva del enunciado):
        // repone stock + reembolsa producto Y envío (nota de crédito total).
        pedido.reembolsarTotal();
        pedido.incrementarStock();
        pedido.setEstado(new Cancelado());
    }
}