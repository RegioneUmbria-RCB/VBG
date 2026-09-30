package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ParametriesportazioneDAO;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ParametriesportazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 
 */
@Service
public class ParametriesportazioneServiceImpl extends
		BaseServiceImpl<Parametriesportazione, PkId> implements
		ParametriesportazioneService {

	private ParametriesportazioneDAO parametriesportazioneDAO;

	@Autowired
	public void setParametriesportazioneDAO(
			ParametriesportazioneDAO parametriesportazioneDAO) {

		this.parametriesportazioneDAO = parametriesportazioneDAO;
	}

	@Override
	protected Class<Parametriesportazione> getEntityClass() {

		return Parametriesportazione.class;
	}

	@Override
	public List<Parametriesportazione> findAll(Integer firstResult,
			Integer maxResult) {

		return parametriesportazioneDAO.findAll(firstResult, maxResult);
	}

	@Override
	public void insert(Parametriesportazione entity) {

		if (validateEntity(entity)) {
			parametriesportazioneDAO.insert(entity);
		}
	}

	@Override
	public Parametriesportazione findById(PkId id) {

		return parametriesportazioneDAO.findById(id);
	}

	@Override
	public void update(Parametriesportazione entity) {

		if (validateEntity(entity)) {
			parametriesportazioneDAO.update(entity);
		}
	}

	@Override
	public void delete(Parametriesportazione entity) {

		if (isDeleteAllowed(entity)) {
			parametriesportazioneDAO.delete(entity);
		}
	}

    //	protected boolean isDeleteAllowed(Parametriesportazione entity) {
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
