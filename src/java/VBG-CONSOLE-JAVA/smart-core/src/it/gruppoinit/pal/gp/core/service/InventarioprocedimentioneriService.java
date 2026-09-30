package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;

import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface InventarioprocedimentioneriService extends BaseService<Inventarioprocedimentioneri, PkId> {

    public Inventarioprocedimentioneri findByCodiceInventarioCausaleCodiceComune(Integer codiceInventario, String idcomunecodiceinventario,
	    Integer codiceCausale, String codiceComune);

    public List<Inventarioprocedimentioneri> findByCodiceInventario(Integer codiceInventario, boolean escludiCausaliDisabilitate,
	    String idcomuneCodiceInventario, String codiceComune);

    //public List<Inventarioprocedimentioneri> findOneriAttiviRegionali(Integer codiceInventario);
    public List<Inventarioprocedimentioneri> findOneriPerProcedimenti(String idComune, String codiceComuneGruppo, List<PkId> pkProcedimenti,
	    boolean escludiDisattivi);

    public List<OneriPerCausaleHelper> findByCodiceInventarioGroupByCausale(Integer codiceInventario, String idcomuneCodiceInventario,
	    String codiceComune);
}
