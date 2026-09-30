package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraContestiCampiBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiCampiBase;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoVisuraContestiCampiBaseDAOImpl extends BaseDAOImpl<FoVisuraContestiCampiBase, PkId> implements FoVisuraContestiCampiBaseDAO {

    @Override
    public Class<FoVisuraContestiCampiBase> getEntityClass() {

	return FoVisuraContestiCampiBase.class;
    }

    @Override
    public List<FoVisuraContestiCampiBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, DAOOrderTypeEnum.ASC);
    }
}
