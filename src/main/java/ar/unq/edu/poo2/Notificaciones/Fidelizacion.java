package ar.unq.edu.poo2.Notificaciones;

import ar.unq.edu.poo2.Pedido.Cancelado;
import ar.unq.edu.poo2.Pedido.EstadoPedido;
import ar.unq.edu.poo2.Pedido.Pedido;

public class Fidelizacion implements ObserverPedido {

	@Override
	public void notificar(EstadoPedido estadoViejo, EstadoPedido estadoNuevo, Pedido pedido) {
		if(estadoNuevo instanceof Cancelado) {
			System.out.println("cupon de descuento del 5%. El pedido"+pedido+"cambio su estado de "+estadoViejo+"a"+estadoNuevo);

		}		
	}

}
