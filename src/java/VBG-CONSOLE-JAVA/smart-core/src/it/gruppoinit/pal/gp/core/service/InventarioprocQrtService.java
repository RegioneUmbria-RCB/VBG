package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface InventarioprocQrtService extends BaseService<InventarioprocQrt, PkId> {

    public static enum STATO_PUBBLICAZIONE {
	TUTTI, PUBBLICATI, NON_PUBBLICATI;
    };

    /**
     * Torna gli oggetti associati all'endoprocedimento ordinati per ORDINE, TITOLO.
     * 
     * @param codiceinventario
     *            il codice dell'endoprocedimento
     * @param stato
     *            se cercare tutti, quelli pubblicati o solo quelli non pubblicati
     * 
     * 
     * @return
     */
    public List<InventarioprocQrt> findByCodiceInventario(String idcomuneCodiceInventario, Integer codiceinventario, STATO_PUBBLICAZIONE stato);

    /**
     * chiama InventarioprocQrtService#findByCodiceInventario(Integer, STATO_PUBBLICAZIONE) con
     * {@link STATO_PUBBLICAZIONE#PUBBLICATI}
     * 
     * @param codiceinventario
     * @return
     */
    public List<InventarioprocQrt> findPubblicatiByCodiceInventario(String idcomuneCodiceInventario, Integer codiceinventario);
}
