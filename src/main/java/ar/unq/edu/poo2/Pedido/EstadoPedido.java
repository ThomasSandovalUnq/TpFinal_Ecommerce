package ar.unq.edu.poo2.Pedido;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

public interface EstadoPedido {
    void confirmar(Pedido pedido);
    void cancelar(Pedido pedido);
    void iniciarPreparacion(Pedido pedido);
    void enviar(Pedido pedido);
    void entregar(Pedido pedido);
    void agregarItem(Pedido pedido, ItemCatalogo item);
    void quitarItem(Pedido pedido, ItemCatalogo item);

    // Queries para evitar instanceof
    default boolean esCancelado() { return false; }
    default boolean esEntregado() { return false; }
    default boolean esConfirmado() { return false; }
    default boolean esEnviado() { return false; }
}