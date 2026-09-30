package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoVisuraContestiBaseDAOImpl extends BaseDAOImpl<FoVisuraContestiBase, String> implements FoVisuraContestiBaseDAO {

    @Override
    public Class<FoVisuraContestiBase> getEntityClass() {

	return FoVisuraContestiBase.class;
    }

    @Override
    public List<FoVisuraContestiBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "contesto", DAOOrderTypeEnum.ASC);
    }
}
