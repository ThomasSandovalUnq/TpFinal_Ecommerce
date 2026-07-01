package ar.unq.edu.poo2.Envio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Pedido.LineaDePedido;
import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.Sucursal.Sucursal;

@ExtendWith(MockitoExtension.class)
public class RetiroEnSucursalTestCase {

    private RetiroEnSucursal retiro;

    @Mock
    private Sucursal sucursalMock;

    @Mock
    private Pedido pedidoMock;

    @BeforeEach
    public void setUp() {
        retiro = new RetiroEnSucursal(sucursalMock, null);
    }

    @Test
    public void testCalcularCostoSiempreEsCero() {
        float costo = retiro.calcularCosto(pedidoMock);
        assertEquals(0.0f, costo);
    }

    @Test
    public void testEstimarDiasDeEntregaCalculaElMaximoDeDiasEntreLasLineas() {
        LineaDePedido linea1 = mock(LineaDePedido.class);
        LineaDePedido linea2 = mock(LineaDePedido.class);
        ItemCatalogo item1 = mock(ItemCatalogo.class);
        ItemCatalogo item2 = mock(ItemCatalogo.class);
        
        when(linea1.getItem()).thenReturn(item1);
        when(linea1.getCantidad()).thenReturn(1);
        when(linea2.getItem()).thenReturn(item2);
        when(linea2.getCantidad()).thenReturn(2);
        
        when(pedidoMock.getLineas()).thenReturn(Arrays.asList(linea1, linea2));
        
        when(sucursalMock.estimarDiasDeRetiro(item1, 1, null)).thenReturn(0);
        when(sucursalMock.estimarDiasDeRetiro(item2, 2, null)).thenReturn(3);

        int dias = retiro.estimarDiasDeEntrega(pedidoMock);

        assertEquals(3, dias);
    }

    @Test
    public void testGetSucursalElegida() {
        assertEquals(sucursalMock, retiro.getSucursalElegida());
    }
}
