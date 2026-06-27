package ar.unq.edu.poo2.Catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}