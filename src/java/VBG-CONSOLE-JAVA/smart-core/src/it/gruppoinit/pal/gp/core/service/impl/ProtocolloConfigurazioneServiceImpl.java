package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ProtocolloConfigurazioneServiceImpl extends BaseServiceImpl<ProtocolloConfigurazione, ProtocolloConfigurazioneId> implements
	ProtocolloConfigurazioneService {

    private ProtocolloConfigurazioneDAO protocolloconfigurazioneDAO;

    @Autowired
    public void setProtocolloConfigurazioneDAO(ProtocolloConfigurazioneDAO protocolloconfigurazioneDAO) {

	this.protocolloconfigurazioneDAO = protocolloconfigurazioneDAO;
    }

    @Override
    protected Class<ProtocolloConfigurazione> getEntityClass() {

	return ProtocolloConfigurazione.class;
    }

    @Override
    public List<ProtocolloConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return protocolloconfigurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtocolloConfigurazione entity) {

	if (validateEntity(entity)) {
	    protocolloconfigurazioneDAO.insert(entity);
	}
    }

    @Override
    public ProtocolloConfigurazione findById(ProtocolloConfigurazioneId id) {

	return protocolloconfigurazioneDAO.findById(id);
    }

    @Override
    public void update(ProtocolloConfigurazione entity) {

	if (validateEntity(entity)) {
	    protocolloconfigurazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtocolloConfigurazione entity) {

	if (isDeleteAllowed(entity)) {
	    protocolloconfigurazioneDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ProtocolloConfigurazione entity) {

	boolean delete = true;
	// List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// // esempio:
	// // if (entity.getList().size() > 0) {
	// // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// // }
	// if (!_ivs.isEmpty()) {
	// this.throwValidationMessages(_ivs);
	// }
	return delete;
    }
}
