package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiUsoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiUsoServiceImpl extends BaseServiceImpl<MercatiUso, PkId> implements MercatiUsoService {

    private MercatiUsoDAO mercatiUsoDAO;

    @Autowired
    public void setMercatiUsoDAO(MercatiUsoDAO mercatiUsoDAO) {

	this.mercatiUsoDAO = mercatiUsoDAO;
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_MERCATI_USO" })
    public void delete(MercatiUso entity) {

	if (isDeleteAllowed(entity))
	    mercatiUsoDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATI_USO" })
    public List<MercatiUso> findAll(Integer firstResult, Integer maxResult) {

	return mercatiUsoDAO.findAll(null, null);
    }

    @Override
    public MercatiUso findById(PkId id) {

	return mercatiUsoDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_MERCATI_USO" })
    public void insert(MercatiUso entity) {

	if (isInsertAllowed(entity)) {
	    if (entity.getDescrizione().equals("")) {
		String descrizione = createDescrizione(entity);
		entity.setDescrizione(descrizione);
	    }
	    if (validateEntity(entity)) {
		mercatiUsoDAO.insert(entity);
	    }
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_MERCATI_USO" })
    public void update(MercatiUso entity) {

	if (isInsertAllowed(entity)) {
	    if (entity.getDescrizione().equals("")) {
		String descrizione = createDescrizione(entity);
		entity.setDescrizione(descrizione);
	    }
	    if (validateEntity(entity)) {
		mercatiUsoDAO.update(entity);
	    }
	}
    }

    @Override
    public List<MercatiUso> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return mercatiUsoDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<MercatiUso> findDescrizioneAndMercato(String textToSearch, Mercati mercati) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("mercati", mercati, Mercati.class));
	filterRestriction.addFilterField(FilterUtils.like("descrizione", textToSearch));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("descrizione"));
	return this.findByFilterTable(filterTable, null, null);
    }

    @Override
    protected Class<MercatiUso> getEntityClass() {

	return MercatiUso.class;
    }

    public List<MercatiUso> findByMercato(Mercati mercati) {

	return mercatiUsoDAO.findByMercato(mercati);
    }

    protected boolean isDeleteAllowed(MercatiUso entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAlberoprocs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
	}
	if (entity.getAnagrafemercatipresenzes().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEMERCATIPRESENZE", null));
	}
	// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
	// if (entity.getConcessionis().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CONCESSIONI", null));
	// }
	if (entity.getMercatipresenzeStoricos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_STORICO", null));
	}
	if (entity.getMercatipresenzeTs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_T", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    protected boolean isInsertAllowed(MercatiUso entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getDescrizione().equals("")) {
	    if ((entity.getGiornisettimana().getGsValore() == null)
		    && (entity.getConcessioniuso() == null || (entity.getConcessioniuso() != null && entity.getConcessioniuso().getId() == null))) {
		_ivs.add(new InvalidValue("mercati.service_error.campi_mancanti", null, null, null, null));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    // crea la stringa descrizione quando vado ad inserire un mercato uso senza la descrizione
    private String createDescrizione(MercatiUso entity) {

	String descrizione = "";
	if (entity.getGiornisettimana() != null && !entity.getGiornisettimana().getGsDescrizione().equals(""))
	    descrizione += entity.getGiornisettimana().getGsDescrizione() + " ";
	if (entity.getConcessioniuso() != null && !entity.getConcessioniuso().getDescrizione().equals(""))
	    descrizione += entity.getConcessioniuso().getDescrizione();
	return descrizione;
    }
}
