package ar.unq.edu.poo2.Catalogo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductoTestCase {

    @Test
    public void testValidarLanzaExcepcionSiFaltaSKU() {

        Producto productoInvalido = new Producto("Teclado", "Mecánico", 5000.0, null, "Logitech", "Electrónica", 0.0);
        
        assertThrows(ValidacionProducto.class, () -> {
            productoInvalido.validar();
        }, "Debería lanzar excepción por no tener SKU.");
    }

    @Test
    public void testValidarLanzaExcepcionSiFaltaValorEnAtributoDinamico() {
        Producto producto = new Producto("Teclado", "Mecánico", 5000.0, "SKU123", "Logitech", "Electrónica", 0.0);

        producto.agregarAtributoDinamico("Peso", null);
        
        assertThrows(ValidacionProducto.class, () -> {
            producto.validar();
        }, "Debería lanzar excepción por tener un atributo dinámico nulo.");
    }

    @Test
    public void testValidarPasaCorrectamenteSiEstanTodosLosDatos() {
        Producto productoValido = new Producto("Teclado", "Mecánico", 5000.0, "SKU123", "Logitech", "Electrónica", 0.0);
        productoValido.agregarAtributoDinamico("Peso", "1.5kg");
        
        assertDoesNotThrow(() -> {
            productoValido.validar();
        });
    }
    
    @Test
    public void testGettersDevuelvenLosValoresAsignadosEnElConstructor() {
        Producto producto = new Producto("Teclado", "Mecánico", 5000.0, "SKU-999", "Logitech", "Periféricos", 10.0);
        
        assertEquals("Teclado", producto.getNombre());
        assertEquals("Mecánico", producto.getDescripcion());
        assertEquals(5000.0, producto.getPrecioBase());
        assertEquals("SKU-999", producto.getSku());
        assertEquals("Logitech", producto.getMarca());
        assertEquals("Periféricos", producto.getCategoria());
    }

    @Test
    public void testPrecioFinalCalculaElDescuentoPromocionalCorrectamente() {
        
        Producto productoConDescuento = new Producto("Teclado", "Mecánico", 5000.0, "SKU-999", "Logitech", "Periféricos", 10.0);
        assertEquals(4500.0, productoConDescuento.precioFinal(), "El precio final debe restar el 10% promocional.");
    }

    @Test
    public void testPrecioFinalSinDescuentoEsIgualAlPrecioBase() {

        Producto productoSinDescuento = new Producto("Mouse", "Óptico", 2000.0, "SKU-111", "Razer", "Periféricos", 0.0);
        assertEquals(2000.0, productoSinDescuento.precioFinal(), "Si no hay descuento, el precio final es el precio base.");
    }

    @Test
    public void testValidarLanzaExcepcionSiFaltaNombre() {

        Producto productoNulo = new Producto(null, "Mecánico", 5000.0, "SKU-999", "Logitech", "Periféricos", 0.0);
        assertThrows(ValidacionProducto.class, () -> {
            productoNulo.validar();
        }, "Debería lanzar excepción si el nombre es null.");

        Producto productoVacio = new Producto("   ", "Mecánico", 5000.0, "SKU-999", "Logitech", "Periféricos", 0.0);
        assertThrows(ValidacionProducto.class, () -> {
            productoVacio.validar();
        }, "Debería lanzar excepción si el nombre está en blanco.");
    }
    @Test
    public void testTieneCategoria() {
    	Producto teclado = new Producto("Teclado", "Periféricos", 5000.0, "SKU-999", "Logitech", "Periféricos", 10.0);
    	
        assertTrue(teclado.tieneCategoria("Periféricos"));
        assertTrue(teclado.tieneCategoria("periféricos"));
        assertFalse(teclado.tieneCategoria("Monitores"));
    }
}
