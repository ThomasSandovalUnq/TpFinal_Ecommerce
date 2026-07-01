package ar.unq.edu.poo2.MetodoPago;

public interface ApiBilleteraVirtual {

    public boolean tieneSaldo(double monto);

    public void bloquearSaldo(double monto);

    public String acreditar(double monto);
}
