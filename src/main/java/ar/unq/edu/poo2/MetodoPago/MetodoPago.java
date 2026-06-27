package ar.unq.edu.poo2.MetodoPago;

public abstract class MetodoPago {
	protected double monto;  
	protected String codigoTransaccion;
	
	public MetodoPago(double monto) {
		this.setMonto(monto);
	}

	public void procesarPago() {
		validarDatos();
		reservarFondos();
		ejecutarTransaccion();
		notificarResultado();
	}

	protected  void notificarResultado() {  
		System.out.println("Transacción registrada: "
	            + this.codigoTransaccion);
	}

	protected abstract void ejecutarTransaccion();

	protected abstract void reservarFondos();

	protected abstract void validarDatos();
	
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public double getMonto(double monto) {
		return monto;
	}
	
}
