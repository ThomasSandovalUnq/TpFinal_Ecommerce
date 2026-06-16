package ar.unq.edu.poo2.Catalogo;

import java.util.HashMap;
import java.util.Map;

import ar.unq.edu.poo2.Reporte.ReporteVisitor;

public class Producto extends ItemCatalogo {
	private String sku;
	private String marca;
	private String categoria;
	private double precioBase;
	private double descuentoPromocional;
	
	private Map<String, Object> atributosDinamicos;
	
	public Producto(String n, String d, double pb, String sku, String m, String c, double descuento) {
		super(n, d);
		this.precioBase = pb;
		this.sku = sku;
		this.marca = m;
		this.categoria = c;
		this.descuentoPromocional = descuento;
		this.atributosDinamicos = new HashMap<>();
	}

	public String getSku() { return sku; }
	public String getMarca() { return marca; }
	public String getCategoria() { return categoria; }	
	
	public void agregarAtributoDinamico(String clave, Object valor) {
		this.atributosDinamicos.put(clave, valor);
	}

	@Override
	public double getPrecioBase() {
		return this.precioBase;
	}

	@Override
	public double precioFinal() {
		return this.precioBase * (1 - (this.descuentoPromocional / 100.0));
	}
	
	public void validar() {
		// Validar atributos fijos obligatorios
		if (this.getNombre() == null || this.getNombre().trim().isEmpty()) {
			throw new ValidacionProducto("El producto debe tener un nombre asignado.");
		}
		if (this.sku == null || this.sku.trim().isEmpty()) {
			throw new ValidacionProducto("El producto debe tener un SKU asignado.");
		}
		
		// Validar atributos dinámicos (que ninguno tenga valor nulo)
		for (Map.Entry<String, Object> entry : atributosDinamicos.entrySet()) {
			if (entry.getValue() == null) {
				throw new ValidacionProducto("El atributo dinámico '" + entry.getKey() + "' no tiene un valor asignado.");
			}
		}
	}
	
	@Override
	public void aceptar(ReporteVisitor visitor) {
	    visitor.visitar(this);
	}
	
	
}
