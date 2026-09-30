package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BandiAlberoprocDAO extends BaseDAO<BandiAlberoproc, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<BandiAlberoproc> findAll(Integer firstResult, Integer maxResult);

    public int findMaxOrdine(Integer codiceBando);

    /**
     * Il metodo recupera i codici mercati (distinc) associati agli interventi collegati al bando tramite la tabella
     * BandiAlberoproc
     * 
     * @param codiceBando
     * @return
     */
    public List<Integer> findDistinctMercatiByBando(Integer codiceBando);

    /**
     * Il metodo recupera i codici mercati uso (distinc) associati agli interventi collegati al bando tramite la tabella
     * BandiAlberoproc
     * 
     * @param codiceBando
     * @return
     */
    public List<Integer> findDistinctMercatiUsoByBando(Integer codiceBando);
}
