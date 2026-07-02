package ar.unq.edu.poo2.EmpresaYBusqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Catalogo.Producto;
import ar.unq.edu.poo2.Sucursal.Sucursal;
import ar.unq.edu.poo2.Sucursal.DepositoGeneral;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class EmpresaYBusquedaTestCase {

    private BuscadorCatalogo buscador;

    @Mock
    private Producto tecladoMock;
    @Mock
    private Producto mouseMock;
    @Mock
    private ItemCatalogo paqueteMock; // Un paquete no es un producto
    
    @Mock
    private Sucursal sucursalQuilmesMock;
    @Mock
    private Sucursal sucursalBernalMock;

    @BeforeEach
    public void setUp() {
        buscador = new BuscadorCatalogo();
    }

    @Test
    public void testCriterioNombreSatisfaceIgnorandoMayusculas() {
        when(tecladoMock.getNombre()).thenReturn("Teclado Mecanico Logitech");
        CriterioBusqueda criterio = new CriterioNombre("mecanico");

        assertTrue(criterio.satisface(tecladoMock));
    }

    @Test
    public void testCriterioNombreNoSatisfaceSiNoContieneElTexto() {
        when(mouseMock.getNombre()).thenReturn("Mouse Optico");
        CriterioBusqueda criterio = new CriterioNombre("teclado");

        assertFalse(criterio.satisface(mouseMock));
    }

    @Test
    public void testCriterioPrecioMaximoSatisfaceSiEsMenorOIgual() {
        when(tecladoMock.getPrecioBase()).thenReturn(5000.0);
        CriterioBusqueda criterio = new CriterioPrecioMaximo(5000.0);

        assertTrue(criterio.satisface(tecladoMock));
    }

    @Test
    public void testCriterioCategoriaSatisfaceSiEsElMismo() {
        when(tecladoMock.getCategoria()).thenReturn("perifericos");
        CriterioBusqueda criterio = new CriterioCategoria("perifericos");

        assertTrue(criterio.satisface(tecladoMock));
    }

    @Test
    public void testCriterioCategoriaFallaSiEsUnPaquete() {
        CriterioBusqueda criterio = new CriterioCategoria("perifericos");
        assertFalse(criterio.satisface(paqueteMock));
    }

    @Test
    public void testCriterioDisponibilidadSatisfaceSiDepositoTieneStock() {
    	
        DepositoGeneral depositoMock = mock(DepositoGeneral.class);
        CriterioBusqueda criterio = new CriterioDisponibilidad(depositoMock);

        when(depositoMock.tieneStock(tecladoMock, 1)).thenReturn(true);

        assertTrue(criterio.satisface(tecladoMock));
    }

    @Test
    public void testCriterioAndRequiereQueAmbosSeCumplan() {
        when(tecladoMock.getNombre()).thenReturn("Teclado");
        when(tecladoMock.getPrecioBase()).thenReturn(3000.0);

        CriterioBusqueda nombre = new CriterioNombre("teclado");
        CriterioBusqueda precio = new CriterioPrecioMaximo(2000.0); // Falla porque sale 3000
        
        CriterioBusqueda criterioAnd = new CriterioAnd(nombre, precio);

        assertFalse(criterioAnd.satisface(tecladoMock), "Falla porque el precio supera el máximo.");
    }

    @Test
    public void testCriterioOrPasaSiAlMenosUnoSeCumple() {
        when(mouseMock.getNombre()).thenReturn("Mouse");
        when(mouseMock.getPrecioBase()).thenReturn(5000.0);

        CriterioBusqueda nombre = new CriterioNombre("teclado"); // Falla
        CriterioBusqueda precio = new CriterioPrecioMaximo(6000.0); // Pasa
        
        CriterioBusqueda criterioOr = new CriterioOr(nombre, precio);

        assertTrue(criterioOr.satisface(mouseMock), "Pasa porque cumple el criterio del precio.");
    }

    @Test
    public void testCriterioNotInvierteElResultado() {
        when(mouseMock.getNombre()).thenReturn("Mouse");
        
        CriterioBusqueda nombre = new CriterioNombre("teclado"); // Daría false
        CriterioBusqueda noEsTeclado = new CriterioNot(nombre);  // Invierte a true

        assertTrue(noEsTeclado.satisface(mouseMock));
    }

    @Test
    public void testBuscadorFiltraCorrectamenteUnaListaDeItems() {
        when(tecladoMock.getPrecioBase()).thenReturn(5000.0);
        when(mouseMock.getPrecioBase()).thenReturn(1500.0);
        when(paqueteMock.getPrecioBase()).thenReturn(8000.0);

        List<ItemCatalogo> catalogoCompleto = Arrays.asList(tecladoMock, mouseMock, paqueteMock);
        
        CriterioBusqueda criterioBarato = new CriterioPrecioMaximo(5000.0);

        List<ItemCatalogo> resultados = buscador.buscar(catalogoCompleto, criterioBarato);

        assertEquals(2, resultados.size());
        assertTrue(resultados.contains(tecladoMock));
        assertTrue(resultados.contains(mouseMock));
        assertFalse(resultados.contains(paqueteMock));
    }
    
    @Test
    public void testCriterioAndSatisfaceSiAmbosSonVerdaderos() {
        when(tecladoMock.getNombre()).thenReturn("Teclado");
        when(tecladoMock.getPrecioBase()).thenReturn(1000.0);
        
        CriterioBusqueda and = new CriterioAnd(new CriterioNombre("teclado"), new CriterioPrecioMaximo(2000.0));
        assertTrue(and.satisface(tecladoMock), "Debe pasar si ambas condiciones se cumplen.");
    }

    @Test
    public void testCriterioAndFallaRapidoSiElPrimeroEsFalso() {
        when(mouseMock.getNombre()).thenReturn("Mouse"); 
        
        CriterioBusqueda and = new CriterioAnd(new CriterioNombre("teclado"), new CriterioPrecioMaximo(2000.0));
        assertFalse(and.satisface(mouseMock), "Debe dar false y hacer cortocircuito en la primera condición.");
    }

    @Test
    public void testCriterioOrFallaSiAmbosSonFalsos() {
        when(mouseMock.getNombre()).thenReturn("Auriculares");
        when(mouseMock.getPrecioBase()).thenReturn(8000.0);
        
        CriterioBusqueda or = new CriterioOr(new CriterioNombre("teclado"), new CriterioPrecioMaximo(2000.0));
        assertFalse(or.satisface(mouseMock), "Debe dar false si ninguna de las dos se cumple.");
    }

    @Test
    public void testCriterioOrPasaRapidoSiElPrimeroEsVerdadero() {
        when(tecladoMock.getNombre()).thenReturn("Teclado");
        CriterioBusqueda or = new CriterioOr(new CriterioNombre("teclado"), new CriterioPrecioMaximo(2000.0));
        assertTrue(or.satisface(tecladoMock), "Debe dar true y hacer cortocircuito al cumplir la primera.");
    }

    @Test
    public void testCriterioNotFallaSiElInternoEsVerdadero() {
        when(mouseMock.getNombre()).thenReturn("Mouse");
        
        CriterioBusqueda nombre = new CriterioNombre("mouse"); // Daría true
        CriterioBusqueda noEsMouse = new CriterioNot(nombre);  // Invierte a false

        assertFalse(noEsMouse.satisface(mouseMock), "Debe invertir un true a false.");
    }
}