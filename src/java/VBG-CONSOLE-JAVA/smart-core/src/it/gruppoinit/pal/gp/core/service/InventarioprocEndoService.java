package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface InventarioprocEndoService extends BaseService<InventarioprocEndo, PkId> {

    public List<InventarioprocEndo> findByInventarioprocT(String idcomune, Integer codiceInventarioprocT, Boolean flagPubblica, Integer firstResult,
	    Integer maxResult, String codiceComune, boolean soloAbilitati);

    /**
     * Verifica se l'endoprocedimento passato, è già stato usato come testata di un raggruppamento di
     * inventarioprocedimenti
     * 
     * @param codice
     * @return
     */
    public Boolean isUsatoComeRaggruppamento(String idcomune, Integer codice);

    /**
     * 
     * @param codice
     */
    public List<InventarioprocEndo> findByInventarioprocD(String idcomune, Integer codiceInventarioprocD, Boolean flagPubblica);
}
