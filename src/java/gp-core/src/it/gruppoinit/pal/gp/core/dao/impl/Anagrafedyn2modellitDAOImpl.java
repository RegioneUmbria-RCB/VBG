package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Anagrafedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2modellitId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class Anagrafedyn2modellitDAOImpl extends BaseDAOImpl<Anagrafedyn2modellit, Anagrafedyn2modellitId> implements Anagrafedyn2modellitDAO {

    @Override
    public Class<Anagrafedyn2modellit> getEntityClass() {

	return Anagrafedyn2modellit.class;
    }

    @Override
    public List<Anagrafedyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
