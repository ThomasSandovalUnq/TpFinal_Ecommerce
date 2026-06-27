package ar.unq.edu.poo2.MetodoPago;

import java.time.LocalDate;

public class TarjetaCredito extends MetodoPago {
	private String numero;
	private String cvv;
	private LocalDate vencimiento;
	private ApiTarjetaCredito api;
	@Override
	protected void validarDatos() {
        if (!api.validarTarjeta(
                numero,
                cvv,
                vencimiento)) {

            throw new RuntimeException("Tarjeta inválida");
        }
	}
	@Override
	protected void reservarFondos() {
		api.preAutorizar(monto);
		
	}
	@Override
	protected void ejecutarTransaccion() {
		codigoTransaccion = api.transferir(monto);
	}
	@Override
	protected void notificarResultado() {
		super.notificarResultado();
        System.out.println("Cupón generado");	// preguntar sobe como debemos notificar	
	}

    public TarjetaCredito(double monto, String numero, String cvv,
            LocalDate vencimiento, ApiTarjetaCredito api) {
    	super(monto);
    	this.setNumero(numero);
    	this.setCvv(cvv);
    	this.setVencimiento(vencimiento);
    	this.setApi(api);
    }
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public LocalDate getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(LocalDate vencimiento) {
		this.vencimiento = vencimiento;
	}

	public ApiTarjetaCredito getApi() {
		return api;
	}

	public void setApi(ApiTarjetaCredito api) {
		this.api = api;
	}


	
}
