package ar.unq.edu.poo2.Pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

@ExtendWith(MockitoExtension.class)
public class BorradorTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        // Un pedido nuevo arranca en Borrador, así que no hace falta llevarlo a ningún lado.
        pedido = new Pedido();
    }

    // ---------- Estado inicial ----------

    @Test
    public void testUnPedidoNuevoArrancaEnBorrador() {
        assertInstanceOf(Borrador.class, pedido.getEstado());
    }

    // ---------- Operaciones válidas ----------

    @Test
    public void testPermiteAgregarItems() {
        pedido.agregarItem(itemMock);
        assertEquals(1, pedido.getLineas().size());
        assertEquals(itemMock, pedido.getLineas().get(0).getItem());
        assertEquals(1, pedido.getLineas().get(0).getCantidad());
    }

    @Test
    public void testPermiteQuitarItems() {
        pedido.agregarItem(itemMock);
        pedido.quitarItem(itemMock);
        assertTrue(pedido.getLineas().isEmpty());
    }

    @Test
    public void testConfirmarTransicionaAConfirmado() {
        pedido.confirmar();
        assertInstanceOf(Confirmado.class, pedido.getEstado());
    }

    @Test
    public void testCancelarTransicionaACancelado() {
        pedido.cancelar();
        assertInstanceOf(Cancelado.class, pedido.getEstado());
    }

    // ---------- Operaciones inválidas ----------

    @Test
    public void testNoPuedeEnviar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.enviar());
    }

    @Test
    public void testNoPuedeEntregar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.entregar());
    }

    @Test
    public void testNoPuedeIniciarPreparacion() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.iniciarPreparacion());
    }
    
    @Test
    public void testAgregarElMismoItemDosVecesIncrementaLaCantidad() {
        pedido.agregarItem(itemMock);
        pedido.agregarItem(itemMock);
        assertEquals(1, pedido.getLineas().size(), "Debe haber una sola línea.");
        assertEquals(2, pedido.getLineas().get(0).getCantidad(), "La cantidad debe ser 2.");
    }
}