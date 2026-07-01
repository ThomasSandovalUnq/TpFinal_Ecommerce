package ar.unq.edu.poo2.Envio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import ar.unq.edu.poo2.Pedido.*;
import ar.unq.edu.poo2.Sucursal.Sucursal;
import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
public class MetodosDeEnvioTestCase {
    @Test
    public void testEnvioExpressDelegaElCalculoALaLibreriaFalsa() {
    	
        Pedido pedidoMock = mock(Pedido.class);
        when(pedidoMock.getPesoTotal()).thenReturn((float) 1000.0);
        EnvioExpress apiMock = mock(EnvioExpress.class);
        when(apiMock.calcularCosto(1000.0f)).thenReturn(150.0f);
        EnvioExpressImpl strategy = new EnvioExpressImpl(apiMock);
        
        assertEquals(150.0f, strategy.calcularCosto(pedidoMock));
        assertEquals(1, strategy.estimarDiasDeEntrega(pedidoMock));
    }
    
    @Test
    public void testRetiroEnSucursalBuscaElItemQueMasDemoreEnLlegar() {

        Pedido pedidoMock = mock(Pedido.class);
        LineaDePedido linea1 = mock(LineaDePedido.class);
        LineaDePedido linea2 = mock(LineaDePedido.class);
        ItemCatalogo item1 = mock(ItemCatalogo.class);
        ItemCatalogo item2 = mock(ItemCatalogo.class);
        
        when(linea1.getItem()).thenReturn(item1);
        when(linea1.getCantidad()).thenReturn(1);
        when(linea2.getItem()).thenReturn(item2);
        when(linea2.getCantidad()).thenReturn(2);
        
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(linea1, linea2));
        
        Sucursal sucursalMock = mock(Sucursal.class);
        when(sucursalMock.estimarDiasDeRetiro(item1, 1, null)).thenReturn(0);
        when(sucursalMock.estimarDiasDeRetiro(item2, 2, null)).thenReturn(3);
        
        RetiroEnSucursal retiro = new RetiroEnSucursal(sucursalMock, null);
        
        assertEquals(0.0f, retiro.calcularCosto(pedidoMock));
        assertEquals(3, retiro.estimarDiasDeEntrega(pedidoMock));
    }
}