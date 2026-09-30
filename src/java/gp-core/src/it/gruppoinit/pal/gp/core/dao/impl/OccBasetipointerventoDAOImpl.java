package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OccBasetipointerventoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OccBasetipointervento;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OccBasetipointerventoDAOImpl extends BaseDAOImpl<OccBasetipointervento, String> implements OccBasetipointerventoDAO {

    @Override
    public Class<OccBasetipointervento> getEntityClass() {

	return OccBasetipointervento.class;
    }

    @Override
    public List<OccBasetipointervento> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "intervento", DAOOrderTypeEnum.ASC);
    }
}
