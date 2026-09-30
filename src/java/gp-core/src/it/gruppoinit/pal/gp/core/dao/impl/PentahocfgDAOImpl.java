package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PentahocfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class PentahocfgDAOImpl extends BaseDAOImpl<Pentahocfg, String> implements PentahocfgDAO {

    @Override
    public Class<Pentahocfg> getEntityClass() {

	return Pentahocfg.class;
    }

    @Override
    public List<Pentahocfg> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }
}
