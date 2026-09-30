package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Email;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface EmailService extends BaseService<Email, PkId> {

    /**
     * Ordima la lista delle mail di una amministrazione per data (asc o disc)
     * 
     * @param orderTypeEnum
     * @return
     */
    public List<Email> findAllOrderByData(Amministrazioni amministrazioni, DAOOrderTypeEnum orderTypeEnum);

    /**
     * Torna la lista delle Email di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Email> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
