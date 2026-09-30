package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsLogDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DehorsLogDAOImpl extends BaseDAOImpl<DehorsLog, PkId> implements DehorsLogDAO {

    @Override
    public Class<DehorsLog> getEntityClass() {

	return DehorsLog.class;
    }

    @Override
    public List<DehorsLog> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
