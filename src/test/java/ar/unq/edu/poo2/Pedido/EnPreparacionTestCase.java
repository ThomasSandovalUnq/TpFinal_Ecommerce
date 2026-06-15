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
public class EnPreparacionTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.confirmar();
        pedido.iniciarPreparacion(); // queda en EnPreparacion
    }

    // ---------- Verificación del estado de partida ----------

    @Test
    public void testElSetUpDejaElPedidoEnPreparacion() {
        assertInstanceOf(EnPreparacion.class, pedido.getEstado());
    }

    // ---------- Operaciones válidas ----------

    @Test
    public void testEnviarTransicionaAEnviado() {
        pedido.enviar();
        assertInstanceOf(Enviado.class, pedido.getEstado());
    }

    @Test
    public void testCancelarTransicionaACancelado() {
        pedido.cancelar();
        assertInstanceOf(Cancelado.class, pedido.getEstado());
    }
    
    // Faltaría comprobar que al cancelar el pedido se repone stock
    // y reembolsa el total. 

    // ---------- Operaciones inválidas ----------

    @Test
    public void testNoPuedeConfirmar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.confirmar());
    }

    @Test
    public void testNoPuedeIniciarPreparacion() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.iniciarPreparacion());
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