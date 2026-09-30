package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLogId;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiLogDAO extends BaseDAO<IstanzeAccessoAttiLog, IstanzeAccessoAttiLogId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<IstanzeAccessoAttiLog> findAll(Integer firstResult, Integer maxResult);

    public int countByFilter(IstanzeAccessoAttiFilter filter);

    public List<IstanzeAccessoAttiLog> findIstanzeAccessoAttiLogByFilter(IstanzeAccessoAttiFilter filter, Integer firstResult, Integer maxResult);
}
