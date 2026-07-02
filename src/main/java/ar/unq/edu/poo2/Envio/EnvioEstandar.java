package ar.unq.edu.poo2.Envio;

import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.EmpresaYBusqueda.Ecommerce;

public class EnvioEstandar implements MetodoDeEnvio {
    private Direccion direccionDestino; // Clase ficticia (o String)
    private CorreoArgentina correo;     // La librería que te da el enunciado
    public EnvioEstandar(Direccion direccion, CorreoArgentina correo) {
        this.direccionDestino = direccion;
        this.correo = correo;
    }
    @Override
    public float calcularCosto(Pedido pedido) {
        // Le pedimos al Pedido que calcule su propio peso total
        float pesoTotal = pedido.getPesoTotal(); 
        return this.correo.estimarEnvio(pesoTotal, this.direccionDestino);
    }
    @Override
    public int estimarDiasDeEntrega(Pedido pedido) {
        return 7; // El enunciado dice "fija entre 5 y 7 días"
    }

    @Override
    public void procesarDescuentoDeStock(Pedido pedido, Ecommerce ecommerce) {
        ecommerce.descontarStockGeneral(pedido);
    }

    @Override
    public void procesarReposicionDeStock(Pedido pedido, Ecommerce ecommerce) {
        ecommerce.reponerStockGeneral(pedido);
    }
}