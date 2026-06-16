package ar.unq.edu.poo2.Reporte;

import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Catalogo.Paquete;

public class ReporteHTML implements ReporteVisitor {

    private StringBuilder sb = new StringBuilder();

    public ReporteHTML() {
        // Apertura de la tabla con su fila de encabezado.
        sb.append("<table>\n")
          .append("  <tr><th>Nombre</th><th>Unidades vendidas</th><th>Precio promedio</th></tr>\n");
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
        sb.append("  <tr>")
          .append("<td>").append(nombre).append("</td>")
          .append("<td>").append(unidades).append("</td>")
          .append("<td>$").append(String.format(java.util.Locale.US, "%.2f", precioPromedio)).append("</td>")
          .append("</tr>\n");
    }

    @Override
    public String obtenerResultado() {
        // Cierre de la tabla.
        return sb.toString() + "</table>\n";
    }
}
