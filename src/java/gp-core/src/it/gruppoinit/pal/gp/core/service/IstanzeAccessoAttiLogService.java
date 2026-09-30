package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiLogDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLogId;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiLogService extends BaseService<IstanzeAccessoAttiLog, IstanzeAccessoAttiLogId> {

    /**
     * @see IstanzeAccessoAttiLogDAO#findAll(Integer, Integer)
     */
    public List<IstanzeAccessoAttiLog> findAll(Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiLog> findByIstanzeAccessoAttiT(Integer codice, Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiLog> findByIstanza(Integer codiceistanza, Integer firstResult, Integer maxResult);

    public int countByFilter(IstanzeAccessoAttiFilter filter);

    public List<IstanzeAccessoAttiLog> findIstanzeAccessoAttiLogByFilter(IstanzeAccessoAttiFilter filter, Integer firstResult, Integer maxResult);

    public void clear();
}
