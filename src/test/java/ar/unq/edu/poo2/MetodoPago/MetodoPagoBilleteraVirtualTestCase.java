package ar.unq.edu.poo2.MetodoPago;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MetodoPagoBilleteraVirtualTestCase {

    @Mock
    private ApiBilleteraVirtual api;
    private BilleteraVirtual billetera;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billetera = new BilleteraVirtual(1500, api); // sin saldo
    }

    @Test
    void procesarPagoDebeVerificarSaldoBloquearYAcreditar() {
        when(api.tieneSaldo(1500)).thenReturn(true);
        when(api.acreditar(1500)).thenReturn("BIV-103");

        billetera.procesarPago();

        verify(api).tieneSaldo(1500);
        verify(api).bloquearSaldo(1500);
        verify(api).acreditar(1500);
    }

    @Test
    void saldoInsuficienteDebeLanzarExcepcion() {
        when(api.tieneSaldo(anyDouble())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> billetera.procesarPago());

        verify(api, never()).bloquearSaldo(anyDouble());
        verify(api, never()).acreditar(anyDouble());
    }
}