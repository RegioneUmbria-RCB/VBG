package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidtestiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2ModellidtestiDAOImpl extends BaseDAOImpl<Dyn2Modellidtesti, PkId> implements Dyn2ModellidtestiDAO {

    @Override
    public Class<Dyn2Modellidtesti> getEntityClass() {

	return Dyn2Modellidtesti.class;
    }

    @Override
    public List<Dyn2Modellidtesti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "testo", DAOOrderTypeEnum.ASC);
    }
}
