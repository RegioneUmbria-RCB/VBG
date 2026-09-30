package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PermcdsinvitatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Permcdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PermcdsinvitatiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class PermcdsinvitatiDAOImpl extends BaseDAOImpl<Permcdsinvitati, PermcdsinvitatiId> implements PermcdsinvitatiDAO {

    @Override
    public Class<Permcdsinvitati> getEntityClass() {

	return Permcdsinvitati.class;
    }

    @Override
    public List<Permcdsinvitati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
