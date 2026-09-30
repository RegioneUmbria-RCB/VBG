package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.TmpEsportazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;

/**
 * 
 * @author
 */
@Service
public class TmpEsportazioniServiceImpl extends BaseServiceImpl<TmpEsportazioni, Integer> implements TmpEsportazioniService {

    private TmpEsportazioniDAO tmpesportazioniDAO;

    @Autowired
    public void setTmpEsportazioniDAO(TmpEsportazioniDAO tmpesportazioniDAO) {

	this.tmpesportazioniDAO = tmpesportazioniDAO;
    }

    @Override
    protected Class<TmpEsportazioni> getEntityClass() {

	return TmpEsportazioni.class;
    }

    @Override
    public List<TmpEsportazioni> findAll(Integer firstResult, Integer maxResult) {

	return tmpesportazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TmpEsportazioni entity) {

	if (validateEntity(entity)) {
	    tmpesportazioniDAO.insert(entity);
	}
    }

    @Override
    public TmpEsportazioni findById(Integer id) {

	return tmpesportazioniDAO.findById(id);
    }

    @Override
    public void update(TmpEsportazioni entity) {

	if (validateEntity(entity)) {
	    tmpesportazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(TmpEsportazioni entity) {

	if (isDeleteAllowed(entity)) {
	    tmpesportazioniDAO.delete(entity);
	}
    }

    @Override
    public void deleteBysessionId(String sessionId) {

	tmpesportazioniDAO.deleteBysessionId(sessionId);
    }

    @Override
    public String exportModalitaPentaho(Integer codice, String email, boolean isInvioMail, String codiceComune) {

	String sessionId = ORMHelper.getToken();
	this.deleteBysessionId(sessionId);
	TmpEsportazioni entity = new TmpEsportazioni();
	entity.setCodice(codice);
	entity.setCodicecomune(codiceComune);
	entity.setData(Calendar.getInstance().getTime());
	entity.setIdcomune(ORMHelper.getIdcomune());
	entity.setSessionid(ORMHelper.getToken());
	this.insert(entity);
	return sessionId;
    }

    @Override
    public String exportModalitaPentaho(Integer codice, String email, boolean isInvioMail) {

	return this.exportModalitaPentaho(codice, email, isInvioMail,  null);
    }
}
