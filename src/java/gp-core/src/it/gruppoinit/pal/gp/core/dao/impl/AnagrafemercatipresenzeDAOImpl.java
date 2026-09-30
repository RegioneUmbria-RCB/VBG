package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafemercatipresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafemercatipresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AnagrafemercatipresenzeDAOImpl extends BaseDAOImpl<Anagrafemercatipresenze, PkId> implements AnagrafemercatipresenzeDAO {

    @Override
    public Class<Anagrafemercatipresenze> getEntityClass() {

	return Anagrafemercatipresenze.class;
    }

    @Override
    public List<Anagrafemercatipresenze> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
