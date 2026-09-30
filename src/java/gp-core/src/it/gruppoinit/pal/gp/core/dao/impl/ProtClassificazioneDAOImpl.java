package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtClassificazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtClassificazione;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class ProtClassificazioneDAOImpl extends BaseDAOImpl<ProtClassificazione, PkId> implements ProtClassificazioneDAO {

    @Override
    public Class<ProtClassificazione> getEntityClass() {

	return ProtClassificazione.class;
    }
}
