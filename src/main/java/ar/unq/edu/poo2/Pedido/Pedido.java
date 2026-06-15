package ar.unq.edu.poo2.Pedido;

import java.util.ArrayList;
import java.util.List;
import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

public class Pedido {

    private EstadoPedido estado;
    private List<ItemCatalogo> items;

    public Pedido() {
        this.estado = new Borrador();
        this.items = new ArrayList<>();
    }

    // --- Delegación al estado (Patrón State) ---
    public void confirmar()            { this.estado.confirmar(this); }
    public void cancelar()             { this.estado.cancelar(this); }
    public void iniciarPreparacion()   { this.estado.iniciarPreparacion(this); }
    public void enviar()               { this.estado.enviar(this); }
    public void entregar()             { this.estado.entregar(this); }
    public void agregarItem(ItemCatalogo item) { this.estado.agregarItem(this, item); }
    public void quitarItem(ItemCatalogo item)  { this.estado.quitarItem(this, item); }

    // --- Acceso al estado ---
    public void setEstado(EstadoPedido nuevoEstado) { this.estado = nuevoEstado; }
    public EstadoPedido getEstado() { return this.estado; }

    // --- Manejo interno de ítems (lo usan los estados que lo permiten) ---
    public void agregarItemInterno(ItemCatalogo item) { this.items.add(item); }
    public void quitarItemInterno(ItemCatalogo item)  { this.items.remove(item); }
    public List<ItemCatalogo> getItems() { return this.items; }

    // --- Acciones con efecto colateral (tercera vía: lógica mínima por ahora) ---
    public void decrementarStock() { /* TODO: delegar a Stock cuando se defina */ }
    public void incrementarStock() { /* TODO: delegar a Stock cuando se defina */ }
    public void reembolsarTotal()  { /* TODO: delegar a pago cuando se defina */ }
    public void reembolsarParcial(){ /* TODO: reembolso solo del producto, sin envío */ }
}