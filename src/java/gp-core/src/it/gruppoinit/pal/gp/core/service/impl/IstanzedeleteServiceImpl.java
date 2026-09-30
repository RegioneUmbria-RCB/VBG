package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzedeleteDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedelete;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.DatiIstanzaEliminataHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.IstanzedeleteService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzedeleteServiceImpl extends BaseServiceImpl<Istanzedelete, PkId> implements IstanzedeleteService {

    private IstanzedeleteDAO istanzedeleteDAO;
    private UserSecurityService userSecurityService;
    private ResponsabiliService responsabiliService;

    @Autowired
    public void setIstanzedeleteDAO(IstanzedeleteDAO istanzedeleteDAO) {

	this.istanzedeleteDAO = istanzedeleteDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Override
    protected Class<Istanzedelete> getEntityClass() {

	return Istanzedelete.class;
    }

    @Override
    public List<Istanzedelete> findAll(Integer firstResult, Integer maxResult) {

	return istanzedeleteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzedelete entity) {

	if (validateEntity(entity)) {
	    istanzedeleteDAO.insert(entity);
	}
    }

    @Override
    public Istanzedelete findById(PkId id) {

	return istanzedeleteDAO.findById(id);
    }

    @Override
    public void update(Istanzedelete entity) {

	if (validateEntity(entity)) {
	    istanzedeleteDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzedelete entity) {

	if (isDeleteAllowed(entity)) {
	    istanzedeleteDAO.delete(entity);
	}
    }

    @Override
    public Istanzedelete populateIstanzedelete(Istanze entity) {

	Istanzedelete istanzedelete = new Istanzedelete();
	istanzedelete.setCodiceistanza(entity.getId().getCodice());
	istanzedelete.setDatacancellazione(new Date());
	istanzedelete.setSoftware(entity.getSoftware().getCodice());
	String xmlCampi = populateDatiIstanzaEliminataHelper(entity);
	istanzedelete.setCampiIstanza(xmlCampi);
	return istanzedelete;
    }

    private String populateDatiIstanzaEliminataHelper(Istanze istanza) {

	DatiIstanzaEliminataHelper datiIstanzaEliminataHelper = new DatiIstanzaEliminataHelper();
	// Popolo comune
	if (istanza.getComune() != null && StringUtils.isNotBlank(istanza.getComune().getCodicecomune())) {
	    datiIstanzaEliminataHelper.setCodicecomune(istanza.getComune().getCodicecomune());
	    datiIstanzaEliminataHelper.setComune(istanza.getComune().getComune());
	}
	// Numero istanza ed codice istanza
	datiIstanzaEliminataHelper.setNumeroIstanza(istanza.getNumeroistanza());
	// Codice istanza
	datiIstanzaEliminataHelper.setCodiceistanza(istanza.getId().getCodice());
	// CODICE PRATICA TELEMATICA
	if (StringUtils.isNotBlank(istanza.getCodicepraticatel())) {
	    datiIstanzaEliminataHelper.setCodicePraticaTelematica(istanza.getCodicepraticatel());
	}
	//Operatore
	datiIstanzaEliminataHelper.setOperatore(istanza.getResponsabile().getResponsabile());
	datiIstanzaEliminataHelper.setCodiceOperatore(istanza.getResponsabile().getId().getCodice());
	//Richiedente
	datiIstanzaEliminataHelper.setRichiedente(istanza.getRichiedente().getDescrizioneRichiedente());
	datiIstanzaEliminataHelper.setCodiceRichiedente(istanza.getRichiedente().getId().getCodice());
	// In qualità di
	if (EntityUtils.getNestedProperty(istanza.getTipisoggetto(), "id.codice") != null) {
	    datiIstanzaEliminataHelper.setTipoSoggetto(istanza.getTipisoggetto().getTiposoggetto() + "["
		    + istanza.getTipisoggetto().getId().getCodice() + "]");
	}
	// Ragione sociale
	if (EntityUtils.getNestedProperty(istanza.getTitolarelegale(), "id.codice") != null) {
	    datiIstanzaEliminataHelper.setTitolareLegale(istanza.getTitolarelegale().getDescrizioneRichiedente());
	    datiIstanzaEliminataHelper.setCodiceTitolatelegale(istanza.getTitolarelegale().getId().getCodice());
	}
	// Intermediario
	if (EntityUtils.getNestedProperty(istanza.getProfessionista(), "id.codice") != null) {
	    datiIstanzaEliminataHelper.setProfessionista(istanza.getProfessionista().getDescrizioneRichiedente());
	    datiIstanzaEliminataHelper.setCodiceProfessionista(istanza.getProfessionista().getId().getCodice());
	}
	// DATA PRESENTAZIONE
	datiIstanzaEliminataHelper.setData(istanza.getData());
	//NUMERO PROT
	if (StringUtils.isNotBlank(istanza.getNumeroprotocollo())) {
	    datiIstanzaEliminataHelper.setNumeroProtocollo(istanza.getNumeroprotocollo());
	}
	// DATA PROT.
	if (istanza.getDataprotocollo() != null) {
	    datiIstanzaEliminataHelper.setDataProtocollo(istanza.getDataprotocollo());
	}
	// Intervento
	if (EntityUtils.getNestedProperty(istanza.getAlberoproc(), "id.codice") != null) {
	    datiIstanzaEliminataHelper.setIntervento(istanza.getAlberoproc().getVwAlberoproc().getScDescrizione());
	    datiIstanzaEliminataHelper.setCodiceintervento(istanza.getAlberoproc().getId().getCodice());
	}
	// lavori (Oggetto della pratica)
	if (StringUtils.isNotBlank(istanza.getLavori())) {
	    datiIstanzaEliminataHelper.setLavori(istanza.getLavori());
	}
	// Operatore che ha gestito la cancellazione
	Responsabili utenetLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	datiIstanzaEliminataHelper.setOperatoreGestioneCancellazione(utenetLoggato.getResponsabile() + "[" + utenetLoggato.getId().getCodice() + "]");
	String xml = Utilities.marshallObject(datiIstanzaEliminataHelper);
	return xml;
    }
    //    protected boolean isDeleteAllowed(Istanzedelete entity) {
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
