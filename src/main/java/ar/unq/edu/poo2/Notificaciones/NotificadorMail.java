package ar.unq.edu.poo2.Notificaciones;

import ar.unq.edu.poo2.Pedido.EstadoPedido;
import ar.unq.edu.poo2.Pedido.Pedido;

public class NotificadorMail implements ObserverPedido{
	private MailSender mail;
	public NotificadorMail(MailSender mail) {
		this.setMail(mail);
	}
	@Override
	public void notificar(EstadoPedido estadoViejo, EstadoPedido estadoNuevo, Pedido pedido) {
		if(estadoNuevo.esConfirmado() || estadoNuevo.esEnviado() || estadoNuevo.esEntregado()) {
			mail.enviarMail(null, "Estado actualizado: " + estadoNuevo, null, null);
		}
	}
	public void setMail(MailSender mail) {
		this.mail = mail;
	}
	public MailSender getMail() {
		return mail;
	}

}
