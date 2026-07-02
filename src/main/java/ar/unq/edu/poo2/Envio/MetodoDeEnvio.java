package ar.unq.edu.poo2.Envio;

import ar.unq.edu.poo2.Pedido.Pedido;

import ar.unq.edu.poo2.EmpresaYBusqueda.Ecommerce;

public interface MetodoDeEnvio {
	
    float calcularCosto(Pedido pedido);
    int estimarDiasDeEntrega(Pedido pedido);
    
    void procesarDescuentoDeStock(Pedido pedido, Ecommerce ecommerce);
    void procesarReposicionDeStock(Pedido pedido, Ecommerce ecommerce);
}
