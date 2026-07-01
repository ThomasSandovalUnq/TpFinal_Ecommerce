package ar.unq.edu.poo2.Pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Envio.MetodoDeEnvio;
import ar.unq.edu.poo2.MetodoPago.MetodoPago;
import ar.unq.edu.poo2.Notificaciones.ObserverPedido;

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

    // ---------- Totales y Pagos ----------

    @Test
    public void testGetTotalSinEnvioEsSoloElSubtotal() {
        when(itemMock.precioFinal()).thenReturn(1500.0);
        pedido.agregarItem(itemMock);
        pedido.agregarItem(itemMock); // 2 items = 3000

        assertEquals(3000.0, pedido.getTotal());
    }

    @Test
    public void testGetTotalConEnvioSumaAmbos() {
        MetodoDeEnvio envioMock = mock(MetodoDeEnvio.class);
        pedido.setMetodoDeEnvio(envioMock);

        when(itemMock.precioFinal()).thenReturn(1500.0);
        pedido.agregarItem(itemMock);

        when(envioMock.calcularCosto(pedido)).thenReturn(500.0f);

        assertEquals(2000.0, pedido.getTotal());
    }

    @Test
    public void testProcesarPagoDelegaEnMetodoDePago() {
        MetodoPago pagoMock = mock(MetodoPago.class);
        pedido.setMetodoDePago(pagoMock);

        when(itemMock.precioFinal()).thenReturn(1000.0);
        pedido.agregarItem(itemMock);

        pedido.procesarPago();

        verify(pagoMock).setMonto(1000.0);
        verify(pagoMock).procesarPago();
    }

    // ---------- Otros Métodos ----------

    @Test
    public void testGetPesoTotalSumaElPesoDeSusItems() {
        when(itemMock.getPeso()).thenReturn(2.5f);
        pedido.agregarItem(itemMock);
        pedido.agregarItem(itemMock);
        assertEquals(5.0f, pedido.getPesoTotal(), 0.001f);
    }

    @Test
    public void testGetMetodoDeEnvio() {
        MetodoDeEnvio envioMock = mock(MetodoDeEnvio.class);
        pedido.setMetodoDeEnvio(envioMock);
        assertEquals(envioMock, pedido.getMetodoDeEnvio());
    }

    @Test
    public void testAgregarYQuitarObserver() {
        ObserverPedido obsMock = mock(ObserverPedido.class);
        pedido.agregarObserver(obsMock);
        pedido.quitarObserver(obsMock);
        // Sólo validamos que no explote
    }
    
    @Test
    public void testProcesarPagoSinMetodoDePagoLanzaExcepcion() {
        assertThrows(PagoNoSeleccionadoException.class, () -> pedido.procesarPago());
    }
    @Test
    public void testGetCostoEnvioSinMetodoDeEnvioLanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> pedido.getCostoEnvio());
    }
    
    
}