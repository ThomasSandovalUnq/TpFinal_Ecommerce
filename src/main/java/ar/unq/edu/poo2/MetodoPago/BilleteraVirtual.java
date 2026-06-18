package ar.unq.edu.poo2.MetodoPago;

public class BilleteraVirtual extends MetodoPago {
    private ApiBilleteraVirtual api;

    public BilleteraVirtual(double monto, ApiBilleteraVirtual api) {
        super(monto);
        this.api = api;
    }

    @Override
    protected void validarDatos() {
        if (!api.tieneSaldo(monto)) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    @Override
    protected void reservarFondos() {
        api.bloquearSaldo(monto);
    }

    @Override
    protected void ejecutarTransaccion() {
        codigoTransaccion = api.acreditar(monto);
    }


    // Usa la implementación por defecto de notificarResultado()
}