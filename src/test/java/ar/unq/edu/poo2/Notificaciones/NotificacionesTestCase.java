package ar.unq.edu.poo2.Notificaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ar.unq.edu.poo2.Pedido.*;

public class NotificacionesTestCase {

    @Mock private MailSender mailSender;
    @Mock private Pedido pedido;

    private NotificadorMail notificadorMail;
    private GeneradorFactura generadorFactura;
    private Fidelizacion fidelizacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificadorMail = new NotificadorMail(mailSender);
        generadorFactura = new GeneradorFactura();
        fidelizacion = new Fidelizacion();
    }

    // --- NotificadorMail ---
    @Test
    void notificadorMailEnviaMailAlConfirmar() {
        notificadorMail.notificar(new Borrador(), new Confirmado(), pedido);
        verify(mailSender).enviarMail(any(), any(), any(), any());
    }
    @Test
    void notificadorMailGetterAVerificar() {
    	assertEquals(mailSender,notificadorMail.getMail());
    }
    @Test
    void notificadorMailEnviaMailAlEnviar() {
        notificadorMail.notificar(new Confirmado(), new Enviado(), pedido);
        verify(mailSender).enviarMail(any(), any(), any(), any());
    }

    @Test
    void notificadorMailEnviaMailAlEntregar() {
        notificadorMail.notificar(new Enviado(), new Entregado(), pedido);
        verify(mailSender).enviarMail(any(), any(), any(), any());
    }

    @Test
    void notificadorMailNoEnviaMailAlCancelar() {
        notificadorMail.notificar(new Borrador(), new Cancelado(), pedido);
        verify(mailSender, never()).enviarMail(any(), any(), any(), any());
    }

    // --- GeneradorFactura ---
    @Test
    void generadorFacturaSoloActuaAlEntregar() {
        // no lanza excepcion y no hace nada en otros estados
        generadorFactura.notificar(new Enviado(), new Entregado(), pedido);
        generadorFactura.notificar(new Borrador(), new Confirmado(), pedido);
    }

    // --- Fidelizacion ---
    @Test
    void fidelizacionSoloActuaAlCancelar() {
        fidelizacion.notificar(new Borrador(), new Cancelado(), pedido);
        fidelizacion.notificar(new Borrador(), new Confirmado(), pedido);
    }
}