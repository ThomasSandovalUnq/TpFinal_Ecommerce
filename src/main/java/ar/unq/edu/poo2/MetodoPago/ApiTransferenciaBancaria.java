package ar.unq.edu.poo2.MetodoPago;

public interface ApiTransferenciaBancaria {

    public boolean validarCuenta(
            String cbu,
            String alias);

    public String transferir(double monto);
}
