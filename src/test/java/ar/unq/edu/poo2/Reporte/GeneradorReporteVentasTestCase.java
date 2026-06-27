package ar.unq.edu.poo2.Reporte;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.Pedido.LineaDePedido;
import ar.unq.edu.poo2.Pedido.Entregado;

public class GeneradorReporteVentasTestCase {

    @Mock private ReporteVisitor visitor;
    @Mock private Pedido pedidoEntregado;
    @Mock private Pedido pedidoNoEntregado;
    @Mock private ItemCatalogo item;
    @Mock private LineaDePedido linea;

    private GeneradorReporteVentas generador;
    private LocalDate desde;
    private LocalDate hasta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        generador = new GeneradorReporteVentas();
        desde = LocalDate.of(2025, 1, 1);
        hasta = LocalDate.of(2025, 12, 31);

        when(pedidoEntregado.getEstado()).thenReturn(new Entregado());
        when(pedidoEntregado.getFechaEntrega()).thenReturn(LocalDate.of(2025, 6, 15));
        when(pedidoEntregado.getLineas()).thenReturn(List.of(linea));

        when(pedidoNoEntregado.getEstado()).thenReturn(mock(ar.unq.edu.poo2.Pedido.Borrador.class));

        when(linea.getItem()).thenReturn(item);
        when(linea.getCantidad()).thenReturn(3);
        when(linea.getPrecioCobrado()).thenReturn(100.0);

        when(visitor.obtenerResultado()).thenReturn("reporte");
    }

    @Test
    void soloIncluirPedidosEntregadosEnElPeriodo() {
        generador.generar(
            List.of(pedidoEntregado, pedidoNoEntregado),
            List.of(item),
            desde, hasta, visitor);

        verify(item).registrarVenta(3, 100.0);
    }

    @Test
    void noIncluirPedidosFueraDelPeriodo() {
        when(pedidoEntregado.getFechaEntrega())
            .thenReturn(LocalDate.of(2024, 1, 1)); // fuera del período

        generador.generar(
            List.of(pedidoEntregado),
            List.of(item),
            desde, hasta, visitor);

        verify(item, never()).registrarVenta(anyInt(), anyDouble());
    }

    @Test
    void llamaAlVisitorPorCadaItemVendido() {
        when(item.getUnidadesVendidas()).thenReturn(3);

        generador.generar(
            List.of(pedidoEntregado),
            List.of(item),
            desde, hasta, visitor);

        verify(item).aceptar(visitor);
    }

    @Test
    void devuelveElResultadoDelVisitor() {
        String resultado = generador.generar(
            List.of(),
            List.of(),
            desde, hasta, visitor);

        assertEquals("reporte", resultado);
    }

    @Test
    void reiniciaVentasAntesDeGenerar() {
        generador.generar(
            List.of(),
            List.of(item),
            desde, hasta, visitor);

        verify(item).reiniciarVentas();
    }
}
