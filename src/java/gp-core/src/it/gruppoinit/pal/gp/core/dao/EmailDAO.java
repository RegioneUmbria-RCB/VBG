package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Email;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface EmailDAO extends BaseDAO<Email, PkId> {

    /**
     * Ordima la lista delle mail di una amministrazione per data (asc o disc)
     * 
     * @param orderTypeEnum
     * @return
     */
    public List<Email> findAllOrderByData(Amministrazioni amministrazioni, DAOOrderTypeEnum orderTypeEnum);
}
