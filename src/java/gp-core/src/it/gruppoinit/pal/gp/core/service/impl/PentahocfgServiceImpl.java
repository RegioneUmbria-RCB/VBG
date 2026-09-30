package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PentahocfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class PentahocfgServiceImpl extends BaseServiceImpl<Pentahocfg, String> implements PentahocfgService {

    private PentahocfgDAO pentahocfgDAO;
    private MailtipoService mailtipoService;

    @Autowired
    public void setPentahocfgDAO(PentahocfgDAO pentahocfgDAO) {

	this.pentahocfgDAO = pentahocfgDAO;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Override
    protected Class<Pentahocfg> getEntityClass() {

	return Pentahocfg.class;
    }

    @Override
    public List<Pentahocfg> findAll(Integer firstResult, Integer maxResult) {

	return pentahocfgDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Pentahocfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    pentahocfgDAO.insert(entity);
	}
    }

    @Override
    public Pentahocfg findById(String id) {

	return pentahocfgDAO.findById(id);
    }

    @Override
    public void update(Pentahocfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    pentahocfgDAO.update(entity);
	}
    }

    @Override
    public void delete(Pentahocfg entity) {

	if (isDeleteAllowed(entity)) {
	    pentahocfgDAO.delete(entity);
	}
    }

    private void dataIntegration(Pentahocfg entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il GraduatoriedCom passato è nullo");
	}
	entity.setIdcomune(ORMHelper.getIdcomune());
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Pentahocfg entity) {

	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
    }
    //	protected boolean isDeleteAllowed(Pentahocfg entity) {
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
