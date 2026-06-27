package ar.unq.edu.poo2.Notificaciones;

import ar.unq.edu.poo2.Pedido.Confirmado;
import ar.unq.edu.poo2.Pedido.Entregado;
import ar.unq.edu.poo2.Pedido.Enviado;
import ar.unq.edu.poo2.Pedido.EstadoPedido;
import ar.unq.edu.poo2.Pedido.Pedido;

public class NotificadorMail implements ObserverPedido{
	private MailSender mail;
	public NotificadorMail(MailSender mail) {
		this.setMail(mail);
	}
	@Override
	public void notificar(EstadoPedido estadoViejo, EstadoPedido estadoNuevo, Pedido pedido) {
		if(estadoNuevo instanceof Confirmado || estadoNuevo instanceof Enviado || estadoNuevo instanceof Entregado) {
			mail.enviarMail(null, null, null, null);
		}
	}
	public void setMail(MailSender mail) {
		this.mail = mail;
	}
	public MailSender getMail() {
		return mail;
	}

}
