package ar.unq.edu.poo2.Catalogo;

import ar.unq.edu.poo2.Reporte.ReporteVisitor;

public abstract class ItemCatalogo {
	private String nombre;
	private String descripcion;
	
	// Campos de venta 
	protected int unidadesVendidas = 0;
	protected double sumaPreciosCobrados = 0.0;
	
	public ItemCatalogo(String n, String d) {
		this.nombre = n;
		this.descripcion = d;
	}
	
	public String getNombre() { return this.nombre; }
	public String getDescripcion() { return this.descripcion; }
	
	public abstract double getPrecioBase(); 
	public abstract double precioFinal();
	
	public abstract void aceptar(ReporteVisitor visitor);
	
	// Acumula una venta de este ítem.
	public void registrarVenta(int cantidad, double precioCobradoUnitario) {
	    this.unidadesVendidas += cantidad;
	    this.sumaPreciosCobrados += precioCobradoUnitario * cantidad;
	}

	public int getUnidadesVendidas() {
	    return this.unidadesVendidas;
	}

	// Promedio ponderado por cantidad del precio efectivamente cobrado.
	public double getPrecioPromedio() {
	    if (this.unidadesVendidas == 0) return 0.0;
	    return this.sumaPreciosCobrados / this.unidadesVendidas;
	}

	// Reinicia los acumuladores antes de generar un reporte de un período.
	public void reiniciarVentas() {
	    this.unidadesVendidas = 0;
	    this.sumaPreciosCobrados = 0.0;
	}
}