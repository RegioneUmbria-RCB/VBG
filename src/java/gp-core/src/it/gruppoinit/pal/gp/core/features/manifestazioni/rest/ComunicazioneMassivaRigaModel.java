package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettMessaggimail;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MessaggiMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliAppioComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazione_destinatario")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneMassivaRigaModel {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nominativo")
    private String nominativo;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "conclusa")
    private boolean conclusa;
    @XmlElement(name = "riferimenti_protocollo")
    private String riferimentiProtocollo;
    @XmlElement(name = "allegati")
    private List<ComunicazioneAllegatiModel> allegati = new ArrayList<ComunicazioneAllegatiModel>(0);
    @XmlElement(name = "firmatari")
    private List<ComunicazioneFirmatariModel> firmatari = new ArrayList<ComunicazioneFirmatariModel>(0);
    @XmlElement(name = "mail")
    private List<DettagliMailComunicazione> mailInviate = new ArrayList<DettagliMailComunicazione>(0);
    @XmlElement(name = "appio_comunicazioni")
    private List<DettagliMailComunicazione> appIoComunicazione = new ArrayList<DettagliMailComunicazione>(0);
    @XmlElement(name = "errore")
    private String errore;
    @XmlElement(name = "tipowarning")
    private String tipowarning;
    @XmlElement(name = "appio")
    private List<DettagliAppioComunicazione> comunicazioniAppIo = new ArrayList<DettagliAppioComunicazione>(0);
    @XmlElement(name = "codice_anagrafe")
    private Integer codiceAnagrafe;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "pec")
    private String pec;
    @XmlElement(name = "modificamail")
    private boolean modificaMail;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public boolean isConclusa() {

	return conclusa;
    }

    public void setConclusa(boolean conclusa) {

	this.conclusa = conclusa;
    }

    public String getRiferimenti_protocollo() {

	return riferimentiProtocollo;
    }

    public void setRiferimenti_protocollo(String riferimenti_protocollo) {

	this.riferimentiProtocollo = riferimenti_protocollo;
    }

    public List<ComunicazioneAllegatiModel> getAllegati() {

	return allegati;
    }

    public void setAllegati(List<ComunicazioneAllegatiModel> allegati) {

	this.allegati = allegati;
    }

    public List<ComunicazioneFirmatariModel> getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(List<ComunicazioneFirmatariModel> firmatari) {

	this.firmatari = firmatari;
    }

    public List<DettagliMailComunicazione> getMailInviate() {

	return mailInviate;
    }

    public void setMailInviate(List<DettagliMailComunicazione> mailInviate) {

	this.mailInviate = mailInviate;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public String getTipowarning() {

	return tipowarning;
    }

    public void setTipowarning(String tipowarning) {

	this.tipowarning = tipowarning;
    }

    public List<DettagliMailComunicazione> getAppIoComunicazione() {

	return appIoComunicazione;
    }

    public void setAppIoComunicazione(List<DettagliMailComunicazione> appIoComunicazione) {

	this.appIoComunicazione = appIoComunicazione;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public boolean isModificaMail() {

	return modificaMail;
    }

    public void setModificaMail(boolean modificaMail) {

	this.modificaMail = modificaMail;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    @SuppressWarnings("rawtypes")
    public static ComunicazioneMassivaRigaModel fromMassiveDettaglio(MassiveDettaglio dettaglio, IWorkflowComunicazioniService workFlowService,
	    IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	return fromMassiveDettaglio(dettaglio, workFlowService, new ComunicazioneMassivaRigaModel(), appIoCodaMassiveDDAO);
    }

    @SuppressWarnings("rawtypes")
    public static ComunicazioneMassivaRigaModel fromMassiveDettaglio(MassiveDettaglio dettaglio, IWorkflowComunicazioniService workFlowService,
	    ComunicazioneMassivaRigaModel model, IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	if (dettaglio == null || dettaglio.getId() == null || dettaglio.getId().getCodice() == null) {
	    return null;
	}
	model.setId(dettaglio.getId().getCodice());
	model.setNominativo(dettaglio.getDestinatari().toString());
	model.setStato(dettaglio.getUltimoStatoCompletato() + " (" + Utilities.formatDate(dettaglio.getUltimoStatoData(), false) + ")");
	model.setCodiceAnagrafe(dettaglio.getDestinatari().getAnagrafe().getId().getCodice());
	model.setEmail(dettaglio.getDestinatari().getAnagrafe().getEmail());
	model.setPec(dettaglio.getDestinatari().getAnagrafe().getPec());
	if (StringUtils.contains(dettaglio.getUltimoStatoCompletato(), "ERRORE_MAIL")) {
	    model.setModificaMail(true);
	} else {
	    model.setModificaMail(false);
	}
	if (workFlowService.getStatoConclusivo().toString().equalsIgnoreCase(dettaglio.getUltimoStatoCompletato())) {
	    model.setConclusa(true);
	} else {
	    model.setConclusa(false);
	}
	//	if(StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING.name().equals(dettaglio.getUltimoStatoCompletato())){
	//	    model.setTipowarning(StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING.name());
	//	}
	model.setRiferimenti_protocollo(dettaglio.getRiferimentiProtocollo());
	model.setErrore(dettaglio.getErrore());
	//allegati
	for (MassiveDAllegati allegato : dettaglio.getAllegati()) {
	    model.getAllegati().add(ComunicazioneAllegatiModel.fromMassiveDAllegati(allegato));
	}
	//firmatari
	Map<String, Boolean> firmatari = new HashMap<String, Boolean>();
	for (MassiveDettDocdafirmare documento : dettaglio.getDocDaFirmare()) {
	    String responsabile = documento.getDocumentiDaFirmare().getFirmatario().getResponsabile();
	    Boolean firmaCompleta = documento.getDocumentiDaFirmare().isFirmatoConSuccesso();
	    if (firmatari.get(responsabile) != null && firmatari.get(responsabile).equals(Boolean.FALSE)) {
		continue;
	    }
	    firmatari.put(responsabile, firmaCompleta);
	}
	for (Map.Entry<String, Boolean> firmatario : firmatari.entrySet()) {
	    model.getFirmatari().add(new ComunicazioneFirmatariModel(firmatario.getKey(), firmatario.getValue()));
	}
	//mail inviate
	for (MassiveDettMessaggimail mail : dettaglio.getMail()) {
	    MessaggiMail messaggiMail = mail.getMessaggiMail();
	    if (messaggiMail != null) {
		model.getMailInviate().add(DettagliMailComunicazione.fromMessaggiMail(messaggiMail));
	    }
	}
	// comunicazion appio invate
	//AppIoCodaMassiveDDAOImpl appIoCodaMassiveDDAOImpl = new AppIoCodaMassiveDDAOImpl();
	List<AppIoCodaMassiveD> appIoCodaMassiveDs = appIoCodaMassiveDDAO.findByIdDettaglioMassiveD(dettaglio.getId().getCodice());
	for (AppIoCodaMassiveD appIoCodaMassiveD : appIoCodaMassiveDs) {
	    DettagliMailComunicazione dettagliMailComunicazione = new DettagliMailComunicazione();
	    AppIoCoda appIoCoda = appIoCodaMassiveD.getAppIoCoda();
	    dettagliMailComunicazione.setOggetto(appIoCoda.getOggetto());
	    dettagliMailComunicazione.setDestinatario(appIoCoda.getCodiceFiscale());
	    dettagliMailComunicazione.setCorpo(appIoCoda.getStatoMessaggio());
	    model.getAppIoComunicazione().add(dettagliMailComunicazione);
	}
	return model;
    }
}
