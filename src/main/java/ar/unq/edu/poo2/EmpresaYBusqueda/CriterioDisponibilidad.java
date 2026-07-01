package ar.unq.edu.poo2.EmpresaYBusqueda;

import ar.unq.edu.poo2.Catalogo.ItemCatalogo;
import ar.unq.edu.poo2.Sucursal.DepositoGeneral;

public class CriterioDisponibilidad implements CriterioBusqueda {
    private DepositoGeneral deposito;

    public CriterioDisponibilidad(DepositoGeneral deposito) {
        this.deposito = deposito;
    }

    @Override
    public boolean satisface(ItemCatalogo item) {
        return deposito != null && deposito.tieneStock(item, 1);
    }
}