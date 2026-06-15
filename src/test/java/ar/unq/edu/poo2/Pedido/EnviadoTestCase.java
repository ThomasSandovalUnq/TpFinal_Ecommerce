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
public class EnviadoTestCase {

    private Pedido pedido;

    @Mock
    private ItemCatalogo itemMock;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        pedido.confirmar();
        pedido.iniciarPreparacion();
        pedido.enviar(); // queda en Enviado
    }

    // ---------- Verificación del estado de partida ----------

    @Test
    public void testElSetUpDejaElPedidoEnEnviado() {
        assertInstanceOf(Enviado.class, pedido.getEstado());
    }

    // ---------- Operaciones válidas ----------

    @Test
    public void testEntregarTransicionaAEntregado() {
        pedido.entregar();
        assertInstanceOf(Entregado.class, pedido.getEstado());
    }

    @Test
    public void testCancelarTransicionaACancelado() {
        pedido.cancelar();
        assertInstanceOf(Cancelado.class, pedido.getEstado());
        
    }
    
    // Faltaría comprobar que al cancelar se reembolsa solo el producto
    // (sin el costo del envío) y que NO se repone stock.

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
    public void testNoPuedeEnviar() {
        assertThrows(OperacionInvalidaException.class, () -> pedido.enviar());
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