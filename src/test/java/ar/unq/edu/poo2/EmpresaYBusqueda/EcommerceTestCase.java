package ar.unq.edu.poo2.EmpresaYBusqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Sucursal.Sucursal;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.Pedido.LineaDePedido;
import ar.unq.edu.poo2.Envio.EnvioEstandar;
import ar.unq.edu.poo2.Envio.MetodoDeEnvio;
import ar.unq.edu.poo2.Envio.RetiroEnSucursal;

@ExtendWith(MockitoExtension.class)
public class EcommerceTestCase {

    private Ecommerce ecommerce;
    private List<Sucursal> sucs;
    
    @Mock
    private Sucursal sucursalMock;
    @Mock
    private ItemCatalogo itemMock;
    @Mock
    private CriterioBusqueda criterioMock;

    @BeforeEach
    public void setUp() {
        ecommerce = new Ecommerce();
        sucs = new ArrayList<Sucursal>();
    }

    @Test
    public void testRegistrarItemYBuscarloConExito() {

        ecommerce.registrarNuevoItemAlCatalogo(itemMock);
        
        when(criterioMock.satisface(itemMock)).thenReturn(true);
        
        List<ItemCatalogo> resultados = ecommerce.buscarEnCatalogo(criterioMock);
        
        assertEquals(1, resultados.size(), "Debe encontrar exactamente 1 resultado.");
        assertTrue(resultados.contains(itemMock), "El resultado debe ser el ítem que registramos.");
    }
    
    @Test
    public void testParaTodosLosGetters() {
    	ecommerce.registrarNuevoItemAlCatalogo(itemMock);
    	
    	assertTrue(ecommerce.getBuscador() != null);
    	assertEquals(1, ecommerce.getCatalogoMaestro().size());
    	assertEquals(0, ecommerce.getRedDeSucursales().size());
    }
    
    @Test
    public void testParaElSetYAgregarSucursal() {
    	ecommerce.setRedDeSucursales(sucs);
    	
    	assertEquals(0, ecommerce.getRedDeSucursales().size());
    	
    	ecommerce.agregarSucursal(sucursalMock);
    	
    	assertEquals(1, ecommerce.getRedDeSucursales().size());
    }

    @Test
    public void testRegistrarPedido() {
        Pedido pedidoMock = mock(Pedido.class);
        ecommerce.registrarPedido(pedidoMock);
    }

    @Test
    public void testConfirmarPedidoConRetiroEnSucursalDescuentaStockDeSucursalElegida() {
        Pedido pedidoMock = mock(Pedido.class);
        RetiroEnSucursal retiroMock = mock(RetiroEnSucursal.class);
        LineaDePedido lineaMock = mock(LineaDePedido.class);
        
        when(pedidoMock.getMetodoDeEnvio()).thenReturn(retiroMock);
        when(retiroMock.getSucursalElegida()).thenReturn(sucursalMock);
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(lineaMock));
        when(lineaMock.getItem()).thenReturn(itemMock);
        when(lineaMock.getCantidad()).thenReturn(2);
        
        org.mockito.Mockito.doCallRealMethod().when(retiroMock).procesarDescuentoDeStock(org.mockito.Mockito.any(Pedido.class), org.mockito.Mockito.any(Ecommerce.class));
        
        ecommerce.confirmarPedido(pedidoMock);
        
        verify(pedidoMock).confirmar();
        verify(sucursalMock).decrementarStock(itemMock, 2);
    }

    @Test
    public void testConfirmarPedidoConOtroEnvioDescuentaStockDeDepositoGeneral() {
        Pedido pedidoMock = mock(Pedido.class);
        EnvioEstandar otroEnvioMock = mock(EnvioEstandar.class);
        LineaDePedido lineaMock = mock(LineaDePedido.class);
        
        ecommerce.getDepositoGeneral().registrarStock(itemMock, 5);
        
        when(pedidoMock.getMetodoDeEnvio()).thenReturn(otroEnvioMock);
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(lineaMock));
        when(lineaMock.getItem()).thenReturn(itemMock);
        when(lineaMock.getCantidad()).thenReturn(2);
        
        org.mockito.Mockito.doCallRealMethod().when(otroEnvioMock).procesarDescuentoDeStock(org.mockito.Mockito.any(Pedido.class), org.mockito.Mockito.any(Ecommerce.class));

        ecommerce.confirmarPedido(pedidoMock);
        
        verify(pedidoMock).confirmar();
        assertTrue(ecommerce.getDepositoGeneral().tieneStock(itemMock, 3));
    }

    @Test
    public void testCancelarPedidoConRetiroEnSucursalReponeStockDeSucursalElegida() {
        Pedido pedidoMock = mock(Pedido.class);
        RetiroEnSucursal retiroMock = mock(RetiroEnSucursal.class);
        LineaDePedido lineaMock = mock(LineaDePedido.class);
        
        when(pedidoMock.getMetodoDeEnvio()).thenReturn(retiroMock);
        when(retiroMock.getSucursalElegida()).thenReturn(sucursalMock);
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(lineaMock));
        when(lineaMock.getItem()).thenReturn(itemMock);
        when(lineaMock.getCantidad()).thenReturn(2);
        
        org.mockito.Mockito.doCallRealMethod().when(retiroMock).procesarReposicionDeStock(org.mockito.Mockito.any(Pedido.class), org.mockito.Mockito.any(Ecommerce.class));

        ecommerce.cancelarPedido(pedidoMock);
        
        verify(pedidoMock).cancelar();
        verify(sucursalMock).incrementarStock(itemMock, 2);
    }

    @Test
    public void testCancelarPedidoConOtroEnvioReponeStockDeDepositoGeneral() {
        Pedido pedidoMock = mock(Pedido.class);
        EnvioEstandar otroEnvioMock = mock(EnvioEstandar.class);
        LineaDePedido lineaMock = mock(LineaDePedido.class);
        
        when(pedidoMock.getMetodoDeEnvio()).thenReturn(otroEnvioMock);
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(lineaMock));
        when(lineaMock.getItem()).thenReturn(itemMock);
        when(lineaMock.getCantidad()).thenReturn(2);
        
        org.mockito.Mockito.doCallRealMethod().when(otroEnvioMock).procesarReposicionDeStock(org.mockito.Mockito.any(Pedido.class), org.mockito.Mockito.any(Ecommerce.class));

        ecommerce.cancelarPedido(pedidoMock);
        
        verify(pedidoMock).cancelar();
        assertTrue(ecommerce.getDepositoGeneral().tieneStock(itemMock, 2));
    }
}