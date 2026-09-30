package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MessaggiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Messaggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class MessaggiDAOImpl extends BaseDAOImpl<Messaggi, PkId> implements MessaggiDAO {

    @Override
    public Class<Messaggi> getEntityClass() {

	return Messaggi.class;
    }

    @Override
    public List<Messaggi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "dataMessaggio", DAOOrderTypeEnum.DESC);
    }
}
