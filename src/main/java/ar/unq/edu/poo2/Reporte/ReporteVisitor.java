package ar.unq.edu.poo2.Reporte;

import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Catalogo.Paquete;

public interface ReporteVisitor {
    void visitar(Producto producto);
    void visitar(Paquete paquete);
    String obtenerResultado();
}

