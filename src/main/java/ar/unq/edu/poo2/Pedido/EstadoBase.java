package ar.unq.edu.poo2.Pedido;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

public abstract class EstadoBase implements EstadoPedido {

    protected abstract String nombreEstado();

    private OperacionInvalidaException invalida(String operacion) {
        return new OperacionInvalidaException(
            "No se puede " + operacion + " un pedido en estado " + nombreEstado() + ".");
    }

    @Override
    public void confirmar(Pedido pedido) { throw invalida("confirmar"); }

    @Override
    public void cancelar(Pedido pedido) { throw invalida("cancelar"); }

    @Override
    public void iniciarPreparacion(Pedido pedido) { throw invalida("iniciar la preparación de"); }

    @Override
    public void enviar(Pedido pedido) { throw invalida("enviar"); }

    @Override
    public void entregar(Pedido pedido) { throw invalida("entregar"); }

    @Override
    public void agregarItem(Pedido pedido, ItemCatalogo item) { throw invalida("agregar ítems a"); }

    @Override
    public void quitarItem(Pedido pedido, ItemCatalogo item) { throw invalida("quitar ítems de"); }
}
