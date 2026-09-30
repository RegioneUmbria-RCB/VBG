package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsAreeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DehorsAreeDAOImpl extends BaseDAOImpl<DehorsAree, PkId> implements DehorsAreeDAO {

    @Override
    public Class<DehorsAree> getEntityClass() {

	return DehorsAree.class;
    }

    @Override
    public List<DehorsAree> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
