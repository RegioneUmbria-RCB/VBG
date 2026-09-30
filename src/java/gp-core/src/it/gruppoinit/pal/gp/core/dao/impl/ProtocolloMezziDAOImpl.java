package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloMezziDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtocolloMezziDAOImpl extends BaseDAOImpl<ProtocolloMezzi, PkId> implements ProtocolloMezziDAO {

    @Override
    public Class<ProtocolloMezzi> getEntityClass() {

	return ProtocolloMezzi.class;
    }

    @Override
    public List<ProtocolloMezzi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
