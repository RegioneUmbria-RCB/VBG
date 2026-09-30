package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloModalitainvioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtocolloModalitainvioDAOImpl extends BaseDAOImpl<ProtocolloModalitainvio, PkId> implements ProtocolloModalitainvioDAO {

    @Override
    public Class<ProtocolloModalitainvio> getEntityClass() {

	return ProtocolloModalitainvio.class;
    }

    @Override
    public List<ProtocolloModalitainvio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
