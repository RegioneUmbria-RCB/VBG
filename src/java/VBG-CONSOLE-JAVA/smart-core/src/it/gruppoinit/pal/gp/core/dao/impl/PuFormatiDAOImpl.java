package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PuFormatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class PuFormatiDAOImpl extends BaseDAOImpl<PuFormati, PkId> implements PuFormatiDAO {

    @Override
    public Class<PuFormati> getEntityClass() {

	return PuFormati.class;
    }

    @Override
    public List<PuFormati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
