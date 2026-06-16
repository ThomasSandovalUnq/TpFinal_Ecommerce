package ar.unq.edu.poo2.Reporte;

import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Catalogo.Paquete;

public class ReporteTXT implements ReporteVisitor {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visitar(Producto producto) {
        agregarLinea(producto.getNombre(),
                     producto.getUnidadesVendidas(),
                     producto.getPrecioPromedio());
    }

    @Override
    public void visitar(Paquete paquete) {
        agregarLinea(paquete.getNombre(),
                     paquete.getUnidadesVendidas(),
                     paquete.getPrecioPromedio());
    }

    private void agregarLinea(String nombre, int unidades, double precioPromedio) {
        sb.append(nombre)
          .append(" - Unidades vendidas: ").append(unidades)
          .append(" - Precio promedio: $").append(String.format(java.util.Locale.US, "%.2f", precioPromedio))
          .append("\n");
    }

    @Override
    public String obtenerResultado() {
        return sb.toString();
    }
}
