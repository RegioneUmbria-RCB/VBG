package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoVisuraCampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoVisuraCampiDAOImpl extends BaseDAOImpl<FoVisuraCampi, FoVisuraCampiId> implements FoVisuraCampiDAO {

    @Override
    public Class<FoVisuraCampi> getEntityClass() {

	return FoVisuraCampi.class;
    }

    @Override
    public List<FoVisuraCampi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "posizione", DAOOrderTypeEnum.ASC);
    }
}
