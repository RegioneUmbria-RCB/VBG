package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocArendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class AlberoprocArendoDAOImpl extends BaseDAOImpl<AlberoprocArendo, PkId> implements AlberoprocArendoDAO {

    @Override
    public Class<AlberoprocArendo> getEntityClass() {

	return AlberoprocArendo.class;
    }

    @Override
    public List<AlberoprocArendo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
