package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Domandefront;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DomandefrontDAOImpl extends BaseDAOImpl<Domandefront, PkId> implements DomandefrontDAO {

    @Override
    public Class<Domandefront> getEntityClass() {

	return Domandefront.class;
    }

    @Override
    public List<Domandefront> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "domanda", DAOOrderTypeEnum.ASC);
    }
}
