package ar.unq.edu.poo2.Notificaciones;

import ar.unq.edu.poo2.Pedido.EstadoPedido;
import ar.unq.edu.poo2.Pedido.Pedido;

public class GeneradorFactura implements ObserverPedido {

	@Override
	public void notificar(EstadoPedido estadoViejo, EstadoPedido estadoNuevo, Pedido pedido) {
		if(estadoNuevo.esEntregado()) {
			System.out.println("comprobante fiscal. El pedido"+pedido+"cambio su estado de"+estadoViejo+"a"+estadoNuevo);
		}
		
	}

}
