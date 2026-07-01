package ar.unq.edu.poo2.Sucursal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import ar.unq.edu.poo2.Sucursal.DepositoGeneral;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

@ExtendWith(MockitoExtension.class)
public class SucursalTestCase {

    private Sucursal sucursalQuilmes;

    @Mock
    private ItemCatalogo productoMock;
    @Mock
    private Sucursal sucursalBernalMock;

    @BeforeEach
    public void setUp() {
        sucursalQuilmes = new Sucursal("Quilmes"); 
    }

    @Test
    public void testRegistrarStockYVerificarDisponibilidad() {
    	
        sucursalQuilmes.registrarStock(productoMock, 10);
        
        assertTrue(sucursalQuilmes.tieneStockLocal(productoMock, 5), "Debe tener stock para 5 unidades.");
        assertTrue(sucursalQuilmes.tieneStockLocal(productoMock, 10), "Debe tener stock justo para 10 unidades.");
        assertFalse(sucursalQuilmes.tieneStockLocal(productoMock, 11), "No debe tener stock si se piden 11.");
    }

    @Test
    public void testVerificarDisponibilidadDeUnProductoQueNoEstaRegistradoDaFalso() {
        assertFalse(sucursalQuilmes.tieneStockLocal(productoMock, 1), "Si el ítem nunca se agregó, el stock es 0.");
    }

    @Test
    public void testDecrementarStockRestaLasUnidadesCorrectamente() {
    	
        sucursalQuilmes.registrarStock(productoMock, 10);
        sucursalQuilmes.decrementarStock(productoMock, 4);
        
        assertTrue(sucursalQuilmes.tieneStockLocal(productoMock, 6), "Quedan 6 unidades.");
        assertFalse(sucursalQuilmes.tieneStockLocal(productoMock, 7), "Ya no quedan 7 unidades.");
    }

    @Test
    public void testDecrementarStockSinSuficienteCantidadLanzaExcepcion() {
    	
        sucursalQuilmes.registrarStock(productoMock, 2);
        
        assertThrows(RuntimeException.class, () -> {
            sucursalQuilmes.decrementarStock(productoMock, 5);
        }, "Debe lanzar excepción si se intenta descontar más stock del que hay.");
    }

    @Test
    public void testIncrementarStockSumaAlExistente() {
    	
        sucursalQuilmes.registrarStock(productoMock, 2);
        sucursalQuilmes.incrementarStock(productoMock, 5);
        
        assertTrue(sucursalQuilmes.tieneStockLocal(productoMock, 7), "El stock total debe ser 7.");
    }
    
    @Test
    public void testEstimarDiasDeRetiroConStockLocalDevuelveCero() {
    	
        sucursalQuilmes.registrarStock(productoMock, 1);
        
        int dias = sucursalQuilmes.estimarDiasDeRetiro(productoMock, 1, null);
        assertEquals(0, dias, "Si hay stock local, la demora debe ser 0 días.");
    }
    
    @Test
    public void testEstimarDiasDeRetiroConTrasladoInternoDevuelveTres() {
    	
        DepositoGeneral deposito = new DepositoGeneral();
        deposito.registrarStock(productoMock, 1);
        Sucursal sucursalQuilmesConRed = new Sucursal("Quilmes");

        int dias = sucursalQuilmesConRed.estimarDiasDeRetiro(productoMock, 1, deposito);
        
        assertEquals(3, dias, "Si requiere traslado interno desde otra sucursal, la demora debe ser de 3 días.");
    }

    @Test
    public void testEstimarDiasDeRetiroSinStockEnNingunLadoLanzaExcepcion() {
    	
        DepositoGeneral deposito = new DepositoGeneral();
        Sucursal sucursalQuilmesConRed = new Sucursal("Quilmes");

        assertThrows(RuntimeException.class, () -> {
            sucursalQuilmesConRed.estimarDiasDeRetiro(productoMock, 1, deposito);
        }, "Debe lanzar excepción si ninguna sucursal del país tiene stock.");
    }
}