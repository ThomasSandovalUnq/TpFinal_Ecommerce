package ar.unq.edu.poo2.Reporte;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Pedido.Pedido;
import ar.unq.edu.poo2.Pedido.LineaDePedido;

public class GeneradorReporteVentas {

    public String generar(List<Pedido> pedidos,
                          List<ItemCatalogo> items,
                          LocalDate desde,
                          LocalDate hasta,
                          ReporteVisitor visitor) {

        // 1. Reiniciar las ventas acumuladas de todos los ítems.
        items.forEach(ItemCatalogo::reiniciarVentas);

        // 2. Recorrer los pedidos ENTREGADOS dentro del período y registrar ventas.
        pedidos.stream()
               .filter(this::estaEntregado)
               .filter(pedido -> estaEnPeriodo(pedido, desde, hasta))
               .forEach(this::registrarVentasDelPedido);

        // 3. Quedarse solo con los ítems que se vendieron, ordenados por
        //    unidades vendidas (de mayor a menor).
        List<ItemCatalogo> masVendidos = items.stream()
                .filter(item -> item.getUnidadesVendidas() > 0)
                .sorted(Comparator.comparingInt(ItemCatalogo::getUnidadesVendidas).reversed())
                .toList();

        // 4. Disparar el Visitor sobre cada ítem (double dispatch).
        masVendidos.forEach(item -> item.aceptar(visitor));

        // 5. Devolver el resultado formateado.
        return visitor.obtenerResultado();
    }

    private boolean estaEntregado(Pedido pedido) {
        return pedido.getEstado().esEntregado();
    }

    private boolean estaEnPeriodo(Pedido pedido, LocalDate desde, LocalDate hasta) {
        LocalDate fecha = pedido.getFechaEntrega();
        return fecha != null
                && !fecha.isBefore(desde)
                && !fecha.isAfter(hasta);
    }

    private void registrarVentasDelPedido(Pedido pedido) {
        for (LineaDePedido linea : pedido.getLineas()) {
            linea.getItem().registrarVenta(linea.getCantidad(), linea.getPrecioCobrado());
        }
    }
} 