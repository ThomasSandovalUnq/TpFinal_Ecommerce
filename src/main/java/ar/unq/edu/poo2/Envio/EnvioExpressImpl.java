package ar.unq.edu.poo2.Envio;

import ar.unq.edu.poo2.EmpresaYBusqueda.Ecommerce;
import ar.unq.edu.poo2.Pedido.Pedido;

public class EnvioExpressImpl implements MetodoDeEnvio {
    private EnvioExpress apiExpress; // La librería del enunciado
    public EnvioExpressImpl(EnvioExpress apiExpress) {
        this.apiExpress = apiExpress;
    }
    @Override
    public float calcularCosto(Pedido pedido) {
        float precioTotal = (float) pedido.getPesoTotal();
        return this.apiExpress.calcularCosto(precioTotal);
    }
    @Override
    public int estimarDiasDeEntrega(Pedido pedido) {
        return 1; // El enunciado dice "se garantiza en 1 día hábil"
    }
	@Override
	public void procesarDescuentoDeStock(Pedido pedido, Ecommerce ecommerce) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void procesarReposicionDeStock(Pedido pedido, Ecommerce ecommerce) {
		// TODO Auto-generated method stub
		
	}
}