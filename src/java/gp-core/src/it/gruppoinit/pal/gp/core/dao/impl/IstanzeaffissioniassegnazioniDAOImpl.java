package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniassegnazioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioniassegnazioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniassegnazioniId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeaffissioniassegnazioniDAOImpl extends BaseDAOImpl<Istanzeaffissioniassegnazioni, IstanzeaffissioniassegnazioniId> implements
	IstanzeaffissioniassegnazioniDAO {

    @Override
    public Class<Istanzeaffissioniassegnazioni> getEntityClass() {

	return Istanzeaffissioniassegnazioni.class;
    }

    @Override
    public List<Istanzeaffissioniassegnazioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
