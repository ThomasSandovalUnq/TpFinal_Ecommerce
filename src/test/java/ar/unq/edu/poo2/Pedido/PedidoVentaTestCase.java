package ar.unq.edu.poo2.Pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

@ExtendWith(MockitoExtension.class)
public class PedidoVentaTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
    }

    // ---------- Captura de precio al confirmar ----------

    @Test
    public void testAlConfirmarSeCapturaElPrecioDeLasLineas() {
        when(itemMock.precioFinal()).thenReturn(5000.0);

        pedido.agregarItem(itemMock);
        pedido.confirmar();

        assertEquals(5000.0, pedido.getLineas().get(0).getPrecioCobrado(),
                "El precio cobrado debe quedar congelado al confirmar.");
    }

    @Test
    public void testAntesDeConfirmarElPrecioCobradoEsCero() {
        pedido.agregarItem(itemMock);

        assertEquals(0.0, pedido.getLineas().get(0).getPrecioCobrado(),
                "Antes de confirmar, el precio aún no fue capturado.");
    }

    @Test
    public void testElPrecioCapturadoNoCambiaAunqueCambieElPrecioDelItem() {
        when(itemMock.precioFinal()).thenReturn(5000.0);

        pedido.agregarItem(itemMock);
        pedido.confirmar(); // congela en 5000

        // Aunque el ítem ahora valga otra cosa, el precio cobrado sigue congelado.
        assertEquals(5000.0, pedido.getLineas().get(0).getPrecioCobrado(),
                "El precio congelado no debe verse afectado por cambios posteriores.");
    }

    // ---------- Fecha de entrega ----------

    @Test
    public void testUnPedidoNuevoNoTieneFechaDeEntrega() {
        assertNull(pedido.getFechaEntrega(),
                "Un pedido que no fue entregado no tiene fecha de entrega.");
    }

    @Test
    public void testAlEntregarSeRegistraLaFechaDeEntrega() {
        pedido.confirmar();
        pedido.iniciarPreparacion();
        pedido.enviar();
        pedido.entregar();

        assertNotNull(pedido.getFechaEntrega(),
                "Al entregar debe quedar registrada la fecha de entrega.");
        assertEquals(LocalDate.now(), pedido.getFechaEntrega(),
                "La fecha de entrega debe ser la fecha actual.");
    }

    @Test
    public void testUnPedidoCanceladoNoTieneFechaDeEntrega() {
        pedido.cancelar(); // desde Borrador

        assertNull(pedido.getFechaEntrega(),
                "Un pedido cancelado nunca se entregó, no tiene fecha.");
    }
}

