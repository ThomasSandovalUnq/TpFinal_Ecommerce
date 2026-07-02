package ar.unq.edu.poo2.EmpresaYBusqueda;
import java.util.ArrayList;
import java.util.List;
import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Sucursal.Sucursal;
import ar.unq.edu.poo2.Sucursal.DepositoGeneral;
import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.Pedido.LineaDePedido;
import ar.unq.edu.poo2.Envio.RetiroEnSucursal;
public class Ecommerce {
    private List<ItemCatalogo> catalogoMaestro;
    private List<Sucursal> redDeSucursales;
    private BuscadorCatalogo buscador;
    private DepositoGeneral depositoGeneral; 
    private List<Pedido> pedidos; 
    
    public Ecommerce() { 
        this.catalogoMaestro = new ArrayList<>();
        this.redDeSucursales = new ArrayList<>();
        this.buscador = new BuscadorCatalogo();
        this.depositoGeneral = new DepositoGeneral();
        this.pedidos = new ArrayList<>();
    }
    
    public void registrarNuevoItemAlCatalogo(ItemCatalogo item) {
        this.catalogoMaestro.add(item);
    }
    
    public List<ItemCatalogo> buscarEnCatalogo(CriterioBusqueda criterio) {
        return buscador.buscar(this.catalogoMaestro, criterio);
    }
    
    public void setRedDeSucursales(List<Sucursal> rs) {
        this.redDeSucursales = rs;
    }
    
    public void agregarSucursal(Sucursal s) {
        this.redDeSucursales.add(s);
    }
    
    public List<ItemCatalogo> getCatalogoMaestro() {
        return catalogoMaestro;
    }
    
    public List<Sucursal> getRedDeSucursales() {
        return redDeSucursales;
    }
    
    public BuscadorCatalogo getBuscador() {
        return buscador;
    }
    
    public void registrarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }
    
    public void confirmarPedido(Pedido pedido) {
     
        pedido.confirmar(); 
        pedido.procesarPago(); 
        
        pedido.getMetodoDeEnvio().procesarDescuentoDeStock(pedido, this);
    }
    
    public void cancelarPedido(Pedido pedido) {
        pedido.cancelar(); 
        pedido.getMetodoDeEnvio().procesarReposicionDeStock(pedido, this);
    }

    
    
    public void descontarStockDeSucursal(Pedido pedido, Sucursal sucursal) {
        for (LineaDePedido linea : pedido.getLineas()) {
            sucursal.decrementarStock(linea.getItem(), linea.getCantidad());
        }
    }
    
    public void descontarStockGeneral(Pedido pedido) {
        for (LineaDePedido linea : pedido.getLineas()) {
            this.depositoGeneral.decrementarStock(linea.getItem(), linea.getCantidad());
        }
    }
    
    public void reponerStockDeSucursal(Pedido pedido, Sucursal sucursal) {
        for (LineaDePedido linea : pedido.getLineas()) {
            sucursal.incrementarStock(linea.getItem(), linea.getCantidad());
        }
    }
    
    public void reponerStockGeneral(Pedido pedido) {
        for (LineaDePedido linea : pedido.getLineas()) {
            this.depositoGeneral.incrementarStock(linea.getItem(), linea.getCantidad());
        }
    }
    
    public DepositoGeneral getDepositoGeneral() {
        return this.depositoGeneral;
    }
}

