package ar.unq.edu.poo2.MetodoPago;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MetodoPagoTarjetaTestCase {

    @Mock
    private ApiTarjetaCredito api;

    private TarjetaCredito tarjeta;
   
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tarjeta = new TarjetaCredito(
                1000,
                "123456789",
                "123",
                LocalDate.of(2030, 12, 31),
                api);
    }



    @Test
    void procesarPagoDebeValidarReservarYTransferir() {

        when(api.validarTarjeta(
                anyString(),
                anyString(),
                any(LocalDate.class)))
                .thenReturn(true);

        when(api.transferir(1000))
                .thenReturn("TAR-001");

        tarjeta.procesarPago();

        verify(api).validarTarjeta(
                anyString(),
                anyString(),
                any(LocalDate.class));

        verify(api).preAutorizar(1000);

        verify(api).transferir(1000);
    }
    @Test
    void tarjetaInvalidaDebeLanzarExcepcion() {

        when(api.validarTarjeta(
                anyString(),
                anyString(),
                any(LocalDate.class)))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> tarjeta.procesarPago());
    }
    @Test
    public void testGetters() {
    	
        assertEquals("123456789", tarjeta.getNumero());
        assertEquals("123", tarjeta.getCvv());
        assertEquals(LocalDate.of(2030, 12, 31), tarjeta.getVencimiento());
        assertEquals(api, tarjeta.getApi());
    }
    
    
    
    
    
    
    
    
    
    
    
}