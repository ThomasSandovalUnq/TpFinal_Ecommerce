package ar.unq.edu.poo2.Pedido;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

public class LineaDePedido {

    private ItemCatalogo item;
    private int cantidad;
    private double precioCobrado; // precio UNITARIO congelado al confirmar la venta

    public LineaDePedido(ItemCatalogo item, int cantidad) {
        this.item = item;
        this.cantidad = cantidad;
        this.precioCobrado = 0.0; // se captura al confirmar
    }

    public ItemCatalogo getItem() {
        return this.item;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public double getPrecioCobrado() {
        return this.precioCobrado;
    }

    // Congela el precio final UNITARIO del ítem al momento de confirmar.
    // Así un cambio posterior de precio no afecta lo ya vendido.
    public void capturarPrecio() {
        this.precioCobrado = this.item.precioFinal();
    }

    // Total de la línea: precio unitario cobrado por la cantidad.
    public double getSubtotal() {
        return this.precioCobrado * this.cantidad;
    }
    
    public void incrementarCantidad() {
        this.cantidad++;
    }

    public void decrementarCantidad() {
        this.cantidad--;
    }
}