/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RuoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.RuoliProtocollo;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniruoliService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;
import it.gruppoinit.pal.gp.core.service.RuoliProtocolloService;
import it.gruppoinit.pal.gp.core.service.RuoliService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Service
public class RuoliServiceImpl extends BaseServiceImpl<Ruoli, PkId> implements RuoliService {

    private RuoliDAO ruoliDAO;
    private RuoliProtocolloService ruoliProtocolloService;
    private ResponsabiliruoliService responsabiliruoliService;
    private AmministrazioniruoliService amministrazioniruoliService;
    private CommedilizieTipologieService commedilizieTipologieService;

    @Autowired
    public void setCommedilizieTipologieService(CommedilizieTipologieService commedilizieTipologieService) {

	this.commedilizieTipologieService = commedilizieTipologieService;
    }

    @Autowired
    public void setRuoliProtocolloService(RuoliProtocolloService ruoliProtocolloService) {

	this.ruoliProtocolloService = ruoliProtocolloService;
    }

    @Autowired
    public void setAmministrazioniruoliService(AmministrazioniruoliService amministrazioniruoliService) {

	this.amministrazioniruoliService = amministrazioniruoliService;
    }

    @Autowired
    public void setResponsabiliruoliService(ResponsabiliruoliService responsabiliruoliService) {

	this.responsabiliruoliService = responsabiliruoliService;
    }

    @Autowired
    public void setRuoliDAO(RuoliDAO ruoliDAO) {

	this.ruoliDAO = ruoliDAO;
    }

    @Override
    protected Class<Ruoli> getEntityClass() {

	return Ruoli.class;
    }

    /**
     * Il metodo controlla se può essere cancellato. Non c'è nessun controllo per la tabella Istanzeruoli. Vengono
     * cancellate automaticamente le righe su Istanzeruoli.
     */
    @Override
    public void delete(Ruoli entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    ruoliDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Ruoli entity) {

	if (entity != null) {
	    List<RuoliProtocollo> rps = ruoliProtocolloService.findByIdRuolo(entity.getId().getCodice());
	    for (RuoliProtocollo ruoliProtocollo : rps) {
		ruoliProtocolloService.delete(ruoliProtocollo);
	    }
	}
    }

    @Override
    public List<Ruoli> findAll(Integer firstResult, Integer maxResult) {

	return ruoliDAO.findAll(null, null);
    }

    @Override
    public Ruoli findById(PkId id) {

	return ruoliDAO.findById(id);
    }

    @Override
    public void insert(Ruoli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    ruoliDAO.insert(entity);
	}
    }

    @Override
    public void update(Ruoli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    ruoliDAO.update(entity);
	}
    }

    private void dataIntegration(Ruoli entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ruolo è nullo");
	}
	if (entity.getFlagDisgestmovamm() == null) {
	    entity.setFlagDisgestmovamm(Boolean.FALSE);
	}
	if (entity.getFlagGestmovimenti() == null) {
	    entity.setFlagGestmovimenti(Boolean.FALSE);
	}
    }

    @Override
    public List<Ruoli> findByFilter(Ruoli ruoli) {

	return ruoliDAO.findByFilter(ruoli);
    }

    @Override
    public void saveRuoliResponsabili(Ruoli ruolo, Set<Responsabiliruoli> responsabiliruolis) {

	Ruoli temp = this.findById(ruolo.getId());
	Set<Responsabiliruoli> tempRespRuolis = temp.getResponsabiliruolis();
	for (Responsabiliruoli responsabiliruoli : tempRespRuolis) {
	    boolean trovato = false;
	    for (Responsabiliruoli responsabiliruoli2 : responsabiliruolis) {
		if (responsabiliruoli.getId().getCodiceresponsabile().intValue() == responsabiliruoli2.getId().getCodiceresponsabile().intValue()) {
		    trovato = true;
		    break;
		}
	    }
	    if (!trovato) {
		responsabiliruoliService.delete(responsabiliruoli);
	    }
	}
	// temp.setResponsabiliruolis(null);
	for (Responsabiliruoli responsabiliruoliDaInserire : responsabiliruolis) {
	    responsabiliruoliService.insert(responsabiliruoliDaInserire);
	}
	// Commentato perchè vado ad aggiornare i responsabili non aggiornando il ruolo,
	// ma andando ad aggiornare i record su responsabiliruoli con l'i del ruolo passato.
	// Questa modalità riportava un errore di "No row with the given identifier exists exception"
	//ruolo.setResponsabiliruolis(responsabiliruolis);
	//this.update(ruolo);
    }

    @Override
    public void saveRuoliAmministrazioni(Ruoli ruolo, Set<Amministrazioniruoli> amministrazioniruolis) {

	List<Amministrazioniruoli> amr = amministrazioniruoliService.findByRuolo(ruolo.getId().getCodice());
	for (Amministrazioniruoli ammruoli : amr) {
	    amministrazioniruoliService.delete(ammruoli);
	}
	for (Amministrazioniruoli ammruoliDaInserire : amministrazioniruolis) {
	    amministrazioniruoliService.insert(ammruoliDaInserire);
	}
	// Commentato perchè vado ad aggiornare le amministrazioni non aggiornando il ruolo,
	// ma andando ad aggiornare i record su amministrazioniruoli con l'i del ruolo passato.
	// Questa modalità riportava un errore di "No row with the given identifier exists exception"
	//ruolo.setAmministrazioniruolis(responsabiliruolis);
	//this.update(ruolo);
    }

    @Override
    public List<Ruoli> findRuoliWithAccessInIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	if (istanza != null && istanza.getId() != null && istanza.getId().getCodice() != null)
	    criterio.addFilterField(FilterUtils.equals("istanze", istanza, "istanzeruoli", Istanze.class));
	ft.addRestriction(criterio);
	List<Ruoli> list = this.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Ruoli> findAllRuoliAndCheckAccessiInstaza(Istanze istanza) {

	List<Ruoli> listRuolitotali = this.findAll(null, null);
	List<Ruoli> listRuoliConAccessoIstanza = this.findRuoliWithAccessInIstanza(istanza);
	for (Ruoli ruoli : listRuolitotali) {
	    for (Ruoli ruoli2 : listRuoliConAccessoIstanza) {
		if (ruoli.getId().getCodice().equals(ruoli2.getId().getCodice())) {
		    ruoli.setRuoloIstanzaTransient(true);
		}
	    }
	}
	return listRuolitotali;
    }

    @Override
    public List<Ruoli> findByFilterTable(FilterTable filterTable) {

	return ruoliDAO.findByFilterTable(filterTable);
    }

    protected boolean isDeleteAllowed(Ruoli entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<AlberoprocRuoli> alberoprocRuolis = entity.getAlberoprocRuolis();
	if (!alberoprocRuolis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC_RUOLI", null));
	    delete = false;
	}
	Set<Responsabiliruoli> responsabiliruolis = entity.getResponsabiliruolis();
	if (!responsabiliruolis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "RESPONSABILIRUOLI", null));
	    delete = false;
	}
	Set<Amministrazioniruoli> amministrazioniruolis = entity.getRuoliamministrazionis();
	if (!amministrazioniruolis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "AMMINISTRAZIONIRUOLI", null));
	    delete = false;
	}
	if (countRuoliInIstanze(entity) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ISTANZERUOLI", null));
	    delete = false;
	}
	if (commedilizieTipologieService.findByRuolo(entity.getId().getCodice(), 0, 1).size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "COMMEDILIZIE_TIPOL_RUOLI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private int countRuoliInIstanze(Ruoli entity) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("id.codice", entity.getId().getCodice(), Integer.class));
	criterio.addFilterField(FilterUtils.isNotEmpty("istanzeruoli"));
	ft.addRestriction(criterio);
	int result = ruoliDAO.countRecord(ft);
	return result;
    }
}
