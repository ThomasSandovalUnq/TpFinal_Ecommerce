package ar.unq.edu.poo2.Pedido;

public class Confirmado extends EstadoBase {

    @Override
    protected String nombreEstado() { return "CONFIRMADO"; }

    @Override
    public void iniciarPreparacion(Pedido pedido) {
        pedido.setEstado(new EnPreparacion());
    }

    @Override
    public void cancelar(Pedido pedido) {
        // CONSULTAR (1): La tabla nueva NO menciona reembolso ni reposición de stock
        // al cancelar desde CONFIRMADO. Pero el stock YA se decrementó al confirmar
        // (en Borrador.confirmar). Si acá no se repone, queda stock perdido.
        // ¿Se debe reponer stock y/o generar nota de crédito al cancelar desde CONFIRMADO?
        pedido.setEstado(new Cancelado());
    }
}
