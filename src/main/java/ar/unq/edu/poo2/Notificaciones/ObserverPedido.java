package ar.unq.edu.poo2.Notificaciones;

import ar.unq.edu.poo2.Pedido.EstadoPedido;
import ar.unq.edu.poo2.Pedido.Pedido;

public interface ObserverPedido {
	public void notificar(EstadoPedido estadoViejo, EstadoPedido estadoNuevo, Pedido pedido);
}
