package ar.unq.edu.poo2.Catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Reporte.ReporteVisitor;

@ExtendWith(MockitoExtension.class)
public class PaqueteTestCase {

	    private Paquete packAudioMovil;

	    @Mock
	    private ItemCatalogo auricularesMock;
	    @Mock
	    private ItemCatalogo fundaMock;
	    @Mock
	    private ItemCatalogo cableMock;

	    @BeforeEach
	    public void setUp() {

	        packAudioMovil = new Paquete("Pack Audio Móvil", "Kit de accesorios", 15.0);
	        
	        packAudioMovil.agregarAPaquete(auricularesMock);
	        packAudioMovil.agregarAPaquete(fundaMock);
	        packAudioMovil.agregarAPaquete(cableMock);
	    }

	    @Test
	    public void testPrecioBaseEsLaSumaDeLosPreciosBaseDeSusItems() {

	        when(auricularesMock.getPrecioBase()).thenReturn(8000.0);
	        when(fundaMock.getPrecioBase()).thenReturn(1500.0);
	        when(cableMock.getPrecioBase()).thenReturn(800.0);

	        double precioBaseCalculado = packAudioMovil.getPrecioBase();

	        assertEquals(10300.0, precioBaseCalculado, "El precio base del paquete debe ser la suma exacta de sus partes.");
	    }

	    @Test
	    public void testPrecioFinalAplicaDescuentoSobreLaSumaDeLosPreciosFinalesDeSusItems() {

	        when(auricularesMock.precioFinal()).thenReturn(8000.0);
	        when(fundaMock.precioFinal()).thenReturn(1500.0);
	        when(cableMock.precioFinal()).thenReturn(800.0);

	        double precioFinalCalculado = packAudioMovil.precioFinal();

	        assertEquals(8755.0, precioFinalCalculado, "El precio final debe tener aplicado el 15% de descuento del paquete.");
	    }
	    @Test
	    public void testRegistrarVentaAcumulaUnidadesYPrecios() {
	        packAudioMovil.registrarVenta(2, 8755.0);
	        assertEquals(2, packAudioMovil.getUnidadesVendidas());
	        assertEquals(8755.0, packAudioMovil.getPrecioPromedio(), 0.01); // promedio unitario
	    }

	    @Test
	    public void testPrecioPromedioEsCeroSiNoHayVentas() {
	        assertEquals(0.0, packAudioMovil.getPrecioPromedio());
	    }

	    @Test
	    public void testReiniciarVentasVuelveAcumuladoresACero() {
	        packAudioMovil.registrarVenta(3, 8755.0);
	        packAudioMovil.reiniciarVentas();
	        assertEquals(0, packAudioMovil.getUnidadesVendidas());
	        assertEquals(0.0, packAudioMovil.getPrecioPromedio());
	    }
	    
	    @Test
	    public void testGetPesoSumaElPesoDeSusItems() {
	        when(auricularesMock.getPeso()).thenReturn(0.5f);
	        when(fundaMock.getPeso()).thenReturn(0.2f);
	        when(cableMock.getPeso()).thenReturn(0.1f);
	        
	        assertEquals(0.8f, packAudioMovil.getPeso(), 0.001f);
	    }
	    
	    @Test
	    public void testAceptarVisitor() {
	        ReporteVisitor visitorMock = mock(ReporteVisitor.class);
	        packAudioMovil.aceptar(visitorMock);
	        verify(visitorMock).visitar(packAudioMovil);
	    }
	    @Test
	    public void testTieneCategoria() {
	        // Como auriculares es el primero en la lista y devuelve true,
	        // el anyMatch() corta ahí y no evalúa a los demás.
	        when(auricularesMock.tieneCategoria("Periféricos")).thenReturn(true);
	        
	        assertTrue(packAudioMovil.tieneCategoria("Periféricos"), "Debería retornar true si al menos un ítem lo tiene");
	    }
 
	    @Test
	    public void testTieneCategoriaFalse() {
	        when(auricularesMock.tieneCategoria("Hogar")).thenReturn(false);
	        when(fundaMock.tieneCategoria("Hogar")).thenReturn(false);
	        when(cableMock.tieneCategoria("Hogar")).thenReturn(false);
	        
	        assertFalse(packAudioMovil.tieneCategoria("Hogar"), "Debería retornar false si ningún ítem lo tiene");
	    }
}