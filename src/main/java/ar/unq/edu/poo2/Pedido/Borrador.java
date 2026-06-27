package ar.unq.edu.poo2.Pedido;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

public class Borrador extends EstadoBase {

    @Override
    protected String nombreEstado() { return "BORRADOR"; }

    @Override
    public void agregarItem(Pedido pedido, ItemCatalogo item) {
        pedido.agregarItemInterno(item);
    }

    @Override
    public void quitarItem(Pedido pedido, ItemCatalogo item) {
        pedido.quitarItemInterno(item);
    }

    @Override
    public void confirmar(Pedido pedido) {
        pedido.capturarPrecios();
        pedido.decrementarStock();
         EstadoPedido estadoViejo = this;
        pedido.setEstado(new Confirmado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }

    @Override
    public void cancelar(Pedido pedido) {
    	EstadoPedido estadoViejo = this;
        pedido.setEstado(new Cancelado());
        pedido.notificarObservers(estadoViejo, pedido.getEstado());
    }
}