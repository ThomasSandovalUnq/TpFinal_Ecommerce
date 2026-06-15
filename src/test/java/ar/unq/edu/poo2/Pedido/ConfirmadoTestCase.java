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
public class ConfirmadoTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.confirmar(); // queda en Confirmado
    }

    // ---------- Verificación del estado de partida ----------

    @Test
    public void testElSetUpDejaElPedidoEnConfirmado() {
        assertInstanceOf(Confirmado.class, pedido.getEstado());
    }

    // ---------- Operaciones válidas ----------

    @Test
    public void testIniciarPreparacionTransicionaAEnPreparacion() {
        pedido.iniciarPreparacion();
        assertInstanceOf(EnPreparacion.class, pedido.getEstado());
    }

    @Test
    public void testCancelarTransicionaACancelado() {
        pedido.cancelar();
        assertInstanceOf(Cancelado.class, pedido.getEstado());
    }

    // ---------- Operaciones inválidas ----------

    @Test
    public void testNoPuedeConfirmar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.confirmar());
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