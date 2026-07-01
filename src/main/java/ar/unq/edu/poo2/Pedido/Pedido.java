package ar.unq.edu.poo2.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Envio.MetodoDeEnvio;
import ar.unq.edu.poo2.MetodoPago.MetodoPago;
import ar.unq.edu.poo2.Notificaciones.ObserverPedido;

public class Pedido {

    private EstadoPedido estado;
    private List<LineaDePedido> lineas;
    private LocalDate fechaEntrega; // se setea al entregar; null mientras no se entregó
    private ArrayList<ObserverPedido> observersNotis;
    private MetodoDeEnvio metodoDeEnvio;
    private MetodoPago metodoDePago;
    
    public Pedido() {
        this.estado = new Borrador();
        this.lineas = new ArrayList<>();
        this.fechaEntrega = null;
        this.observersNotis = new ArrayList<>();
    }

    // --- Delegación al estado (Patrón State) ---
    public void confirmar()            { this.estado.confirmar(this); }
    public void cancelar()             { this.estado.cancelar(this); }
    public void iniciarPreparacion()   { this.estado.iniciarPreparacion(this); }
    public void enviar()               { this.estado.enviar(this); }
    public void entregar()             { this.estado.entregar(this); }
    public void agregarItem(ItemCatalogo item) { this.estado.agregarItem(this, item); }
    public void quitarItem(ItemCatalogo item)  { this.estado.quitarItem(this, item); }

    // --- Acceso al estado ---
    public void setEstado(EstadoPedido nuevoEstado) { this.estado = nuevoEstado; }
    public EstadoPedido getEstado() { return this.estado; }

    // --- Manejo interno de ítems (lo usan los estados que lo permiten) ---

    // Agrega una unidad del ítem: si ya hay línea, incrementa cantidad; si no, crea una.
    public void agregarItemInterno(ItemCatalogo item) {
        LineaDePedido existente = this.buscarLinea(item);
        if (existente != null) {
            existente.incrementarCantidad();
        } else {
            this.lineas.add(new LineaDePedido(item, 1));
        }
    }

    // CONSULTAR: interpretamos que "quitar ítem" saca UNA unidad, no todo el ítem.
    // Si la cantidad llega a 0, se elimina la línea.
    public void quitarItemInterno(ItemCatalogo item) {
        LineaDePedido linea = this.buscarLinea(item);
        if (linea != null) {
            linea.decrementarCantidad();
            if (linea.getCantidad() == 0) {
                this.lineas.remove(linea);
            }
        }
    }

    private LineaDePedido buscarLinea(ItemCatalogo item) {
        return this.lineas.stream()
                .filter(l -> l.getItem().equals(item))
                .findFirst()
                .orElse(null);
    }

    public List<LineaDePedido> getLineas() { return this.lineas; }

    // --- Venta: captura de precios y fecha ---

    // Se invoca al confirmar: congela el precio de cada línea.
    public void capturarPrecios() {
        this.lineas.forEach(LineaDePedido::capturarPrecio);
    }

    // Se invoca al entregar: registra la fecha de entrega.
    public void registrarEntrega() {
        this.fechaEntrega = LocalDate.now();
    }

    public LocalDate getFechaEntrega() { return this.fechaEntrega; }

    // --- Acciones con efecto colateral (stubs - tercera vía) ---
    public void decrementarStock() { /* TODO: delegar a Sucursal */ }
    public void incrementarStock() { /* TODO: delegar a Sucursal */ }
    public void reembolsarTotal()  { /* TODO: generar NotaCredito */ }
    public void reembolsarParcial(){ /* TODO: reembolso solo producto */ }
    
    // metodo de aviso a los observers para las notificaciones
    public void notificarObservers(EstadoPedido estadoViejo, EstadoPedido estadoNuevo) {
    	observersNotis.forEach(o -> o.notificar(estadoViejo, estadoNuevo, this));;
    }
    public void agregarObserver(ObserverPedido obs) {
    	observersNotis.add(obs);
    }
    public void quitarObserver(ObserverPedido obs) {
    	observersNotis.remove(obs);
    }
    
    public MetodoDeEnvio getMetodoDeEnvio() {
    	return this.metodoDeEnvio;
    }
    
    public void setMetodoDeEnvio(MetodoDeEnvio metodo) {
        this.metodoDeEnvio = metodo;
    }
    
    public float getCostoEnvio() {
        if (this.metodoDeEnvio == null) throw new RuntimeException("Método de envío no seleccionado");
        return this.metodoDeEnvio.calcularCosto(this);
    }

    public float getPesoTotal() {
        float pesoTotal = 0;
        for (LineaDePedido linea : this.lineas) {
            pesoTotal += linea.getItem().getPeso() * linea.getCantidad(); 
        }
        return pesoTotal;
    }

    public double getTotal() {
        double costoEnvio = (this.metodoDeEnvio != null) ? this.getCostoEnvio() : 0.0;
        return this.getSubtotalProductos() + costoEnvio;
    }
    
    public double getSubtotalProductos() {
        return this.lineas.stream()
                .mapToDouble(l -> l.getItem().precioFinal() * l.getCantidad())
                .sum();
    }

    public void setMedioDePago(MetodoPago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public void setMetodoDePago(MetodoPago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public void procesarPago() {
        if (this.metodoDePago == null) {
            throw new PagoNoSeleccionadoException("Debe seleccionar un medio de pago antes de confirmar.");
        }
        this.metodoDePago.setMonto(this.getTotal());
        this.metodoDePago.procesarPago();
    }
    
    
}