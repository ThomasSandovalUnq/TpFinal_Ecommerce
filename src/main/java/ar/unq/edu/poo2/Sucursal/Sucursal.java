package ar.unq.edu.poo2.Sucursal;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sucursal {
    private String nombre;
    // El map guarda cuántas unidades hay de cada ítem en esta sucursal específica
    private Map<ItemCatalogo, Integer> stockLocal;

    public Sucursal(String nombre) {
        this.nombre = nombre;
        this.stockLocal = new HashMap<>();
    }

    public void registrarStock(ItemCatalogo item, int cantidad) {
        this.stockLocal.put(item, this.stockLocal.getOrDefault(item, 0) + cantidad);
    }

    public boolean tieneStockLocal(ItemCatalogo item, int cantidadNecesaria) {
        return this.stockLocal.getOrDefault(item, 0) >= cantidadNecesaria;
    }

    public void decrementarStock(ItemCatalogo item, int cantidad) {
        if (!tieneStockLocal(item, cantidad)) {
            throw new RuntimeException("Stock insuficiente en la sucursal: " + this.nombre);
        }
        this.stockLocal.put(item, this.stockLocal.get(item) - cantidad);
    }

    public void incrementarStock(ItemCatalogo item, int cantidad) {
        registrarStock(item, cantidad);
    }
    
    
    public int estimarDiasDeRetiro(ItemCatalogo item, int cantidad, DepositoGeneral depositoGeneral) {
        if (this.tieneStockLocal(item, cantidad)) {
            return 0;
        } 
        else if (depositoGeneral != null && depositoGeneral.tieneStock(item, cantidad)) {
            return 3;
        } 
        else {
            throw new RuntimeException("No hay stock en ninguna sucursal del país.");
        }
    }
}