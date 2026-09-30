package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloSmistamentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtocolloSmistamentiDAOImpl extends BaseDAOImpl<ProtocolloSmistamenti, PkId> implements ProtocolloSmistamentiDAO {

    @Override
    public Class<ProtocolloSmistamenti> getEntityClass() {

	return ProtocolloSmistamenti.class;
    }

    @Override
    public List<ProtocolloSmistamenti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
