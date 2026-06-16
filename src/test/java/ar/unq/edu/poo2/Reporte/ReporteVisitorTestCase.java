package ar.unq.edu.poo2.Reporte;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Catalogo.Paquete;

@ExtendWith(MockitoExtension.class)
public class ReporteVisitorTestCase {

    @Mock
    private Producto tecladoMock;
    @Mock
    private Paquete packMock;

    // Helper: configura el mock del teclado. Lo llaman solo los tests que lo usan.
    private void configurarTeclado() {
        when(tecladoMock.getNombre()).thenReturn("Teclado");
        when(tecladoMock.getUnidadesVendidas()).thenReturn(10);
        when(tecladoMock.getPrecioPromedio()).thenReturn(4500.0);
    }

    // ---------- ReporteTXT ----------

    @Test
    public void testTxtIncluyeNombreUnidadesYPrecio() {
        configurarTeclado();
        ReporteTXT reporte = new ReporteTXT();
        reporte.visitar(tecladoMock);

        String resultado = reporte.obtenerResultado();
        System.out.println(resultado);   
        assertTrue(resultado.contains("Teclado"));
        assertTrue(resultado.contains("10"));
        assertTrue(resultado.contains("4500.00"));
    }

    // ---------- ReporteCSV ----------

    @Test
    public void testCsvTieneEncabezado() {
        ReporteCSV reporte = new ReporteCSV();
        String resultado = reporte.obtenerResultado();

        assertTrue(resultado.startsWith("Nombre,UnidadesVendidas,PrecioPromedio"),
                "El CSV debe arrancar con la fila de encabezado.");
    }

    @Test
    public void testCsvAgregaUnaFilaPorItem() {
        configurarTeclado();
        ReporteCSV reporte = new ReporteCSV();
        reporte.visitar(tecladoMock);

        String resultado = reporte.obtenerResultado();
        assertTrue(resultado.contains("Teclado,10,4500.00"),
                "Debe contener la fila del ítem con sus valores separados por comas.");
    }

    // ---------- ReporteHTML ----------

    @Test
    public void testHtmlAbreYCierraLaTabla() {
        ReporteHTML reporte = new ReporteHTML();
        String resultado = reporte.obtenerResultado();

        assertTrue(resultado.contains("<table>"));
        assertTrue(resultado.contains("</table>"));
    }

    @Test
    public void testHtmlAgregaUnaFilaPorItem() {
        configurarTeclado();
        ReporteHTML reporte = new ReporteHTML();
        reporte.visitar(tecladoMock);

        String resultado = reporte.obtenerResultado();
        assertTrue(resultado.contains("<td>Teclado</td>"));
        assertTrue(resultado.contains("<td>10</td>"));
        assertTrue(resultado.contains("$4500.00"));
    }

    // ---------- visitar(Paquete) ----------

    @Test
    public void testTxtTambienFuncionaConPaquetes() {
        when(packMock.getNombre()).thenReturn("Pack Audio");
        when(packMock.getUnidadesVendidas()).thenReturn(5);
        when(packMock.getPrecioPromedio()).thenReturn(8755.0);

        ReporteTXT reporte = new ReporteTXT();
        reporte.visitar(packMock);

        String resultado = reporte.obtenerResultado();
        assertTrue(resultado.contains("Pack Audio"));
        assertTrue(resultado.contains("5"));
        assertTrue(resultado.contains("8755.00"));
    }
}