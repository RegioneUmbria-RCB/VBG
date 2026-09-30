package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.dao.IBollCfgTipoRateDAO;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class BollCfgTipoRateServiceImpl extends BaseServiceImpl<BollCfgTipoRate, PkId> implements IBollCfgTipoRateService {

    @Autowired
    IBollCfgTipoRateDAO bollCfgTipoRateDAO;

    @Override
    public void insert(BollCfgTipoRate entity) {

	if (validateEntity(entity)) {
	    this.bollCfgTipoRateDAO.insert(entity);
	}
    }

    @Override
    public void update(BollCfgTipoRate entity) {

	if (validateEntity(entity)) {
	    this.bollCfgTipoRateDAO.update(entity);
	}
    }

    @Override
    public void delete(BollCfgTipoRate entity) {

	if (isDeleteAllowed(entity)) {
	    this.bollCfgTipoRateDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollCfgTipoRate entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public List<BollCfgTipoRate> findAll(Integer firstResult, Integer maxResult) {

	return bollCfgTipoRateDAO.findAll(firstResult, maxResult);
    }

    @Override
    public BollCfgTipoRate bindDomainObject(BollCfgTipoRate entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(BollCfgTipoRate entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BollCfgTipoRate findById(PkId id) {

	return bollCfgTipoRateDAO.findById(id);
    }

    @Override
    protected Class<BollCfgTipoRate> getEntityClass() {

	return BollCfgTipoRate.class;
    }

    @Override
    public List<BollCfgTipoRate> findByTipo(Integer codice) {

	return bollCfgTipoRateDAO.findByTipo(codice);
    }

    @Override
    public boolean verificaSePresente(Integer idBoll, Integer idRange) {

	return this.bollCfgTipoRateDAO.verificaSePresente(idBoll, idRange);
    }
}
