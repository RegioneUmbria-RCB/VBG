package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniAnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniAnagrafeService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AmministrazioniAnagrafeServiceImpl extends BaseServiceImpl<AmministrazioniAnagrafe, PkId> implements AmministrazioniAnagrafeService {

    private static final Logger log = LoggerFactory.getLogger(AmministrazioniAnagrafeServiceImpl.class);
    private AmministrazioniAnagrafeDAO amministrazionianagrafeDAO;

    @Autowired
    public void setAmministrazioniAnagrafeDAO(AmministrazioniAnagrafeDAO amministrazionianagrafeDAO) {

	this.amministrazionianagrafeDAO = amministrazionianagrafeDAO;
    }

    @Override
    protected Class<AmministrazioniAnagrafe> getEntityClass() {

	return AmministrazioniAnagrafe.class;
    }

    @Override
    public List<AmministrazioniAnagrafe> findAll(Integer firstResult, Integer maxResult) {

	return amministrazionianagrafeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AmministrazioniAnagrafe entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    amministrazionianagrafeDAO.insert(entity);
	}
    }

    private void dataIntegration(AmministrazioniAnagrafe entity) {

	if (entity.getInoltraComunicazione() == null) {
	    entity.setInoltraComunicazione(false);
	}
	fixMergeEntityProperties(entity);
    }

    private boolean isInsertAllowed(AmministrazioniAnagrafe entity) {

	AmministrazioniAnagrafe amministrazioniAnagrafe = this.findByAnagrafeAndAmministrazione(entity.getAmministrazioni().getId().getCodice(),
		entity.getAnagrafe().getId().getCodice());
	String error = "";
	if (EntityUtils.getNestedProperty(amministrazioniAnagrafe, "id.codice") != null
		&& EntityUtils.equals(amministrazioniAnagrafe.getAmministrazioni(), entity.getAmministrazioni())) {
	    log.error("isInsertAllowed# Anagrafe {} già associata all'amministrazione {}", entity.getAnagrafe().getDescrizioneRichiedente(), entity
		    .getAmministrazioni().getDescrizioneEstesa());
	    error = getMessageFromBundle("service_error.anagrafe_presente_per_amministrazione_selezionata", new Object[] { entity.getAnagrafe()
		    .getDescrizioneRichiedente() });
	} else {
	    List<AmministrazioniAnagrafe> amministrazioniAnagrafes = this.findByAnagrafe(entity.getAnagrafe().getId().getCodice());
	    if (!amministrazioniAnagrafes.isEmpty()) {
		log.error("isInsertAllowed# Anagrafe {} già associata ad un altra amministrazione {}", entity.getAnagrafe()
			.getDescrizioneRichiedente(), amministrazioniAnagrafes.get(0).getAmministrazioni().getDescrizioneEstesa());
		error = getMessageFromBundle("service_error.anagrafe_presente_nella_amministrazione",
			new Object[] { entity.getAnagrafe().getDescrizioneRichiedente(),
				amministrazioniAnagrafes.get(0).getAmministrazioni().getDescrizioneEstesa() });
	    }
	}
	if (StringUtils.isNotBlank(error)) {
	    throw new BusinessValidationException(error);
	}
	return true;
    }

    @Override
    public AmministrazioniAnagrafe findById(PkId id) {

	return amministrazionianagrafeDAO.findById(id);
    }

    @Override
    public void update(AmministrazioniAnagrafe entity) {

	if (validateEntity(entity)) {
	    amministrazionianagrafeDAO.update(entity);
	}
    }

    @Override
    public void delete(AmministrazioniAnagrafe entity) {

	if (isDeleteAllowed(entity)) {
	    amministrazionianagrafeDAO.delete(entity);
	}
    }

    @Override
    public List<AmministrazioniAnagrafe> findAmministrazione(Integer codiceamministrazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceamministrazione, "amministrazioni", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("nominativo", "anagrafe"));
	return amministrazionianagrafeDAO.findByFilterTable(ft);
    }

    @Override
    public List<AmministrazioniAnagrafe> findByAnagrafe(Integer codiceanagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceanagrafe, "anagrafe", Integer.class));
	ft.addRestriction(fr);
	List<AmministrazioniAnagrafe> list = amministrazionianagrafeDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public AmministrazioniAnagrafe findByAnagrafeAndAmministrazione(Integer codiceamministrazione, Integer codiceanagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceamministrazione, "amministrazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceanagrafe, "anagrafe", Integer.class));
	ft.addRestriction(fr);
	List<AmministrazioniAnagrafe> list = amministrazionianagrafeDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    protected boolean isDeleteAllowed(AmministrazioniAnagrafe entity) {

	//
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
}
