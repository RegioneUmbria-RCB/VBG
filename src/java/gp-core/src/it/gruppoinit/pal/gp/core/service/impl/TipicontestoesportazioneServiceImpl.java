package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.TipicontestoesportazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontestoesportazione;
import it.gruppoinit.pal.gp.core.service.TipicontestoesportazioneService;

/**
 * 
 * @author
 */
@Service
public class TipicontestoesportazioneServiceImpl extends BaseServiceImpl<Tipicontestoesportazione, PkId> implements TipicontestoesportazioneService {

    private TipicontestoesportazioneDAO tipicontestoesportazioneDAO;

    @Autowired
    public void setTipicontestoesportazioneDAO(TipicontestoesportazioneDAO tipicontestoesportazioneDAO) {

	this.tipicontestoesportazioneDAO = tipicontestoesportazioneDAO;
    }

    @Override
    protected Class<Tipicontestoesportazione> getEntityClass() {

	return Tipicontestoesportazione.class;
    }

    @Override
    public List<Tipicontestoesportazione> findAll(Integer firstResult, Integer maxResult) {

	return tipicontestoesportazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipicontestoesportazione entity) {

	if (validateEntity(entity)) {
	    tipicontestoesportazioneDAO.insert(entity);
	}
    }

    @Override
    public Tipicontestoesportazione findById(PkId id) {

	return tipicontestoesportazioneDAO.findById(id);
    }

    @Override
    public void update(Tipicontestoesportazione entity) {

	if (validateEntity(entity)) {
	    tipicontestoesportazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipicontestoesportazione entity) {

	if (isDeleteAllowed(entity)) {
	    tipicontestoesportazioneDAO.delete(entity);
	}
    }

    @Override
    public Tipicontestoesportazione findByCodice(String codiceTipoContesto) {

	return tipicontestoesportazioneDAO.findByCodice(codiceTipoContesto);
    }
    //	protected boolean isDeleteAllowed(Tipicontestoesportazione entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
