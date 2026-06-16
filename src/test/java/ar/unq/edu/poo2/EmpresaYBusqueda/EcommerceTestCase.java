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
}