package ar.unq.edu.poo2.Main;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import ar.unq.edu.poo2.Catalogo.*;
import ar.unq.edu.poo2.EmpresaYBusqueda.Ecommerce;
import ar.unq.edu.poo2.Envio.CorreoArgentina;
import ar.unq.edu.poo2.Envio.Direccion;
import ar.unq.edu.poo2.Envio.EnvioEstandar;
import ar.unq.edu.poo2.MetodoPago.ApiTarjetaCredito;
import ar.unq.edu.poo2.MetodoPago.TarjetaCredito;
import ar.unq.edu.poo2.Notificaciones.Fidelizacion;
import ar.unq.edu.poo2.Notificaciones.GeneradorFactura;
import ar.unq.edu.poo2.Notificaciones.MailSender;
import ar.unq.edu.poo2.Notificaciones.NotificadorMail;
import ar.unq.edu.poo2.Pedido.Pedido;

public class MainEjemploTestCase {

    @Test
    public void testFlujoCompletoDeUnPedido() {
        // 1. Instanciar nuestro sistema central Ecommerce
        Ecommerce ecommerce = new Ecommerce();

        // 2. Crear productos reales
        Producto teclado = new Producto("Teclado", "Teclado mecánico", 5000.0, "SKU-001", "Logitech", "Electronica", 0.0);
        teclado.agregarAtributoDinamico("Peso", 0.8f);

        Producto mouse = new Producto("Mouse", "Mouse inalámbrico", 3000.0, "SKU-002", "Logitech", "Electronica", 10.0);
        mouse.agregarAtributoDinamico("Peso", 0.3f);
         
        Producto gabinete = new Producto("Gabinete", "Gabinete RGB", 10000.0, "SKU-003", "Kolink", "Electronica", 5.0);
        gabinete.agregarAtributoDinamico("altura", 60);
                
        // 3. Crear un paquete real con 2 productos
        Paquete pack = new Paquete("Kit Office", "Teclado + Mouse", 15.0);
        pack.agregarAPaquete(teclado);
        pack.agregarAPaquete(mouse);

        // Agregamos todo al catálogo maestro
        ecommerce.registrarNuevoItemAlCatalogo(teclado);
        ecommerce.registrarNuevoItemAlCatalogo(mouse);
        ecommerce.registrarNuevoItemAlCatalogo(gabinete);
        ecommerce.registrarNuevoItemAlCatalogo(pack);

        // Proveemos stock al depósito general para que el pedido se pueda descontar
        ecommerce.getDepositoGeneral().registrarStock(teclado, 10);
        ecommerce.getDepositoGeneral().registrarStock(mouse, 10);
        ecommerce.getDepositoGeneral().registrarStock(gabinete, 5);
        ecommerce.getDepositoGeneral().registrarStock(pack, 5);

        // 4. Crear el pedido real y agregar 1 producto y 1 paquete
        Pedido pedido = new Pedido();
        pedido.agregarItem(gabinete);
        pedido.agregarItem(pack);

        // 5. Configurar Envío (Estandar a domicilio) MOCKEANDO dependencias externas
        Direccion direccionDestino = mock(Direccion.class);
        CorreoArgentina correoMock = mock(CorreoArgentina.class);
        when(correoMock.estimarEnvio(anyFloat(), any(Direccion.class))).thenReturn(1500.0f);
        
        EnvioEstandar metodoDeEnvio = new EnvioEstandar(direccionDestino, correoMock);
        pedido.setMetodoDeEnvio(metodoDeEnvio);

        // 6. Registrar observers MOCKEANDO el servidor de mail externo
        MailSender mailSenderMock = mock(MailSender.class);
        NotificadorMail notificadorMail = new NotificadorMail(mailSenderMock);
        pedido.agregarObserver(notificadorMail);
        pedido.agregarObserver(new GeneradorFactura());
        pedido.agregarObserver(new Fidelizacion());

        // 7. Configurar Medio de Pago MOCKEANDO la API del Banco externa
        ApiTarjetaCredito apiTarjetaMock = mock(ApiTarjetaCredito.class);
        when(apiTarjetaMock.validarTarjeta(anyString(), anyString(), any())).thenReturn(true);
        when(apiTarjetaMock.transferir(anyDouble())).thenReturn("TAR-12345");

        String nroTarjeta = "1234567890123456";
        String cvv = "123";
        LocalDate vencimiento = LocalDate.of(2030, 12, 31);
        
        TarjetaCredito tarjeta = new TarjetaCredito(0.0, nroTarjeta, cvv, vencimiento, apiTarjetaMock);
        pedido.setMetodoDePago(tarjeta);
        
        ecommerce.registrarPedido(pedido);
        
        // 8. Ciclo de vida completo del Pedido (Asserts)
        assertTrue(pedido.getEstado().esBorrador(), "El pedido debería nacer en estado Borrador");
        
        // CONFIRMACIÓN
        ecommerce.confirmarPedido(pedido);
        assertTrue(pedido.getEstado().esConfirmado(), "El pedido debería estar Confirmado");
        
        // Verificamos que se descontó el stock del Gabinete (-1)
        assertTrue(ecommerce.getDepositoGeneral().tieneStock(gabinete, 4));
        
        // Verificamos que se llamaron a las APIs mockeadas
        verify(apiTarjetaMock).validarTarjeta(eq(nroTarjeta), eq(cvv), eq(vencimiento));
        verify(apiTarjetaMock).preAutorizar(org.mockito.Mockito.anyDouble());
        verify(apiTarjetaMock).transferir(org.mockito.Mockito.anyDouble());
        
        // PREPARACIÓN
        pedido.iniciarPreparacion();
        
        // ENVÍO
        pedido.enviar();
        assertTrue(pedido.getEstado().esEnviado());
        
        // Verificamos que se haya notificado por mail
        // Al pasar por Confirmado y luego Enviado, se llamó más de una vez.
        verify(mailSenderMock, org.mockito.Mockito.atLeastOnce()).enviarMail(any(), any(), any(), any());

        // ENTREGA
        pedido.entregar(); 
        assertTrue(pedido.getEstado().esEntregado());
    }
}