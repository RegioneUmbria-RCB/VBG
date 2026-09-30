package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsLogDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DehorsLog;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DehorsLogService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class DehorsLogServiceImpl extends BaseServiceImpl<DehorsLog, PkId> implements DehorsLogService {

    private DehorsLogDAO dehorslogDAO;
    private AutorizzazioniService autorizzazioniService;
    private IstanzeService istanzeService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setDehorsLogDAO(DehorsLogDAO dehorslogDAO) {

	this.dehorslogDAO = dehorslogDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<DehorsLog> getEntityClass() {

	return DehorsLog.class;
    }

    @Override
    public List<DehorsLog> findAll(Integer firstResult, Integer maxResult) {

	return dehorslogDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DehorsLog entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorslogDAO.insert(entity);
	}
    }

    @Override
    public DehorsLog findById(PkId id) {

	return dehorslogDAO.findById(id);
    }

    @Override
    public void update(DehorsLog entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorslogDAO.update(entity);
	}
    }

    @Override
    public void delete(DehorsLog entity) {

	if (isDeleteAllowed(entity)) {
	    dehorslogDAO.delete(entity);
	}
    }

    private void dataIntegration(DehorsLog entity) {

	entity.setDataOraModifica(new Date());
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(DehorsLog entity) {

	Autorizzazioni autorizzazioni = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(autorizzazioni);
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Responsabili responsabili = responsabiliService.bindDomainObject(entity.getResponsabili(), PkId.class, "id.codice");
	entity.setResponsabili(responsabili);
    }

    @Override
    public DehorsLog populateDehorsLog(AutorizzazioniCommand autorizzazioniCommand, BigDecimal mqIniziali, BigDecimal mqvariati, String log) {

	DehorsLog dehorsLog = new DehorsLog();
	dehorsLog.setAutorizzazioni(autorizzazioniCommand.getEntity());
	dehorsLog.setDataOraModifica(new Date());
	dehorsLog.setMessaggioLog(log);
	dehorsLog.setIstanze(autorizzazioniCommand.getEntity().getIstanza());
	dehorsLog.setMqIniziali(mqIniziali);
	dehorsLog.setMqVariati(mqvariati);
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	dehorsLog.setResponsabili(responsabile);
	return dehorsLog;
    }

    @Override
    public List<DehorsLog> findByAutorizzazione(Integer codiceAut) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAut, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	List<DehorsLog> list = dehorslogDAO.findByFilterTable(ft);
	return list;
    }
    //	protected boolean isDeleteAllowed(DehorsLog entity) {
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
