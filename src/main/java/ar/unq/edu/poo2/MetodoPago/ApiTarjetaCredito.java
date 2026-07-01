package ar.unq.edu.poo2.MetodoPago;

import java.time.LocalDate;

public interface ApiTarjetaCredito {
    public boolean validarTarjeta(
            String numero,
            String cvv,
            LocalDate vencimiento);

    public void preAutorizar(double monto);

    public String transferir(double monto);
}
