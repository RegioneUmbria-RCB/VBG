package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtTprofilassegnazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class ProtTprofilassegnazioneDAOImpl extends BaseDAOImpl<ProtTprofilassegnazione, PkId> implements ProtTprofilassegnazioneDAO {

    @Override
    public Class<ProtTprofilassegnazione> getEntityClass() {

	return ProtTprofilassegnazione.class;
    }
}
