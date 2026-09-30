package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatipresenzeStoricoDAO extends BaseDAO<MercatipresenzeStorico, PkId> {

    /**
     * metodo per il recupero degli anni (colonna ANNO) presenti in MERCATIPRESENZE_STORICO
     * 
     * @return
     */
    public List<Integer> findAnniDaStorico();
}
