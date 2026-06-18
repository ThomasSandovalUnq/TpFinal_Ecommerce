package ar.unq.edu.poo2.MetodoPago;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MetodoPagoTransferenciaTestCase {

    @Mock
    private ApiTransferenciaBancaria api;

    private TransferenciaBancaria transferencia;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        transferencia =
                new TransferenciaBancaria(
                        5000,
                        "123456",
                        "MI_ALIAS",
                        api);
    }

    @Test
    void procesarPagoDebeValidarYTransferir() {

        when(api.validarCuenta(
                anyString(),
                anyString()))
                .thenReturn(true);

        when(api.transferir(5000))
                .thenReturn("TRA-003");

        transferencia.procesarPago();

        verify(api).validarCuenta(
                anyString(),
                anyString());

        verify(api).transferir(5000);
    }

    @Test
    void cuentaInvalidaDebeLanzarExcepcion() {

        when(api.validarCuenta(
                anyString(),
                anyString()))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> transferencia.procesarPago());
        verify(api, never()).transferir(anyDouble());
    }
}