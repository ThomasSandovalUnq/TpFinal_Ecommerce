package ar.unq.edu.poo2.Reporte;

import java.util.Locale;
import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Catalogo.Paquete;

public class ReporteCSV implements ReporteVisitor {

    private StringBuilder sb = new StringBuilder();

    public ReporteCSV() {
        // Fila de encabezado del CSV.
        sb.append("Nombre,UnidadesVendidas,PrecioPromedio\n");
    }

    @Override
    public void visitar(Producto producto) {
        agregarFila(producto.getNombre(),
                    producto.getUnidadesVendidas(),
                    producto.getPrecioPromedio());
    }

    @Override
    public void visitar(Paquete paquete) {
        agregarFila(paquete.getNombre(),
                    paquete.getUnidadesVendidas(),
                    paquete.getPrecioPromedio());
    }

    private void agregarFila(String nombre, int unidades, double precioPromedio) {
        sb.append(nombre).append(",")
          .append(unidades).append(",")
          .append(String.format(java.util.Locale.US, "%.2f", precioPromedio))
          .append("\n");
    }

    @Override
    public String obtenerResultado() {
        return sb.toString();
    }
}
