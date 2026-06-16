package ar.unq.edu.poo2.EmpresaYBusqueda;

import java.util.ArrayList;
import java.util.List;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Sucursal.Sucursal;

public class Ecommerce {
	
	private List<ItemCatalogo> catalogoMaestro;
    private List<Sucursal> redDeSucursales;
    private BuscadorCatalogo buscador;

    public Ecommerce() {
        this.catalogoMaestro = new ArrayList<>();
        this.redDeSucursales = new ArrayList<Sucursal>();
        this.buscador = new BuscadorCatalogo();
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
    
    
    
}
