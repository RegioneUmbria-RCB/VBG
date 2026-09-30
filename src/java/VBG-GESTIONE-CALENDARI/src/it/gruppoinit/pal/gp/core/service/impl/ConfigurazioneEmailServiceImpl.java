package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneEmailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneEmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ConfigurazioneEmailServiceImpl extends BaseServiceImpl<ConfigurazioneEmail, PkId> implements ConfigurazioneEmailService {

    private ConfigurazioneEmailDAO configurazioneemailDAO;

    @Autowired
    public void setConfigurazioneEmailDAO(ConfigurazioneEmailDAO configurazioneemailDAO) {

	this.configurazioneemailDAO = configurazioneemailDAO;
    }

    @Override
    protected Class<ConfigurazioneEmail> getEntityClass() {

	return ConfigurazioneEmail.class;
    }

    @Override
    public List<ConfigurazioneEmail> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneemailDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ConfigurazioneEmail entity) {

	if (validateEntity(entity)) {
	    configurazioneemailDAO.insert(entity);
	}
    }

    @Override
    public ConfigurazioneEmail findById(PkId id) {

	return configurazioneemailDAO.findById(id);
    }

    @Override
    public void update(ConfigurazioneEmail entity) {

	if (validateEntity(entity)) {
	    configurazioneemailDAO.update(entity);
	}
    }

    @Override
    public void delete(ConfigurazioneEmail entity) {

	if (isDeleteAllowed(entity)) {
	    configurazioneemailDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ConfigurazioneEmail entity) {

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
    public ConfigurazioneEmail findByInstallazione() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	List<ConfigurazioneEmail> list = configurazioneemailDAO.findByFilterTable(ft);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }
}
