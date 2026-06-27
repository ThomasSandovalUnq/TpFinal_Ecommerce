package ar.unq.edu.poo2.Notificaciones;

public interface MailSender {
	public void enviarMail(String direcciónDestino, String título, String mensaje,String adjunto);

}
