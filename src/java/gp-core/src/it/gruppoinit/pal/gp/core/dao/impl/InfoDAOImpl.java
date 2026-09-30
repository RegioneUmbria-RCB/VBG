package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InfoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Info;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class InfoDAOImpl extends BaseDAOImpl<Info, PkId> implements InfoDAO {

    @Override
    public Class<Info> getEntityClass() {

	return Info.class;
    }

    @Override
    public List<Info> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
