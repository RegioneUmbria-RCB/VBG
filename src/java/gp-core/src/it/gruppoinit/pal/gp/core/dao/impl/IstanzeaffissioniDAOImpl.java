package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeaffissioniDAOImpl extends BaseDAOImpl<Istanzeaffissioni, IstanzeaffissioniId> implements IstanzeaffissioniDAO {

    @Override
    public Class<Istanzeaffissioni> getEntityClass() {

	return Istanzeaffissioni.class;
    }

    @Override
    public List<Istanzeaffissioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
