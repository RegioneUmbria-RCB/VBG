package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoVisuraCampiBaseDAOImpl extends BaseDAOImpl<FoVisuraCampiBase, String> implements FoVisuraCampiBaseDAO {

    @Override
    public Class<FoVisuraCampiBase> getEntityClass() {

	return FoVisuraCampiBase.class;
    }

    @Override
    public List<FoVisuraCampiBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "campo", DAOOrderTypeEnum.ASC);
    }
}
