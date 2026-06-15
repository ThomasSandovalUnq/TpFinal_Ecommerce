package ar.unq.edu.poo2.Pedido;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;

@ExtendWith(MockitoExtension.class)
public class CanceladoTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.cancelar(); // desde Borrador, camino más corto a Cancelado
    }

    // ---------- Verificación del estado de partida ----------

    @Test
    public void testElSetUpDejaElPedidoEnCancelado() {
        assertInstanceOf(Cancelado.class, pedido.getEstado());
    }

    // ---------- Estado terminal: ninguna operación es válida ----------

    @Test
    public void testNoPuedeConfirmar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.confirmar());
    }

    @Test
    public void testNoPuedeCancelar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.cancelar());
    }

    @Test
    public void testNoPuedeIniciarPreparacion() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.iniciarPreparacion());
    }

    @Test
    public void testNoPuedeEnviar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.enviar());
    }

    @Test
    public void testNoPuedeEntregar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.entregar());
    }

    @Test
    public void testNoPuedeAgregarItems() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.agregarItem(itemMock));
    }

    @Test
    public void testNoPuedeQuitarItems() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.quitarItem(itemMock));
    }
}