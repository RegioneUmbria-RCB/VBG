package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveTFirmatari;
import it.gruppoinit.pal.gp.core.domain.MassiveTLettere;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneAccountMailModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneAllegatiModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneFirmatariModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneProtocolloModel;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazione")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneMassivaGenModel {

    @XmlElement(name = "alias")
    private String alias;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "id")
    private Integer id;
    @XmlTransient
    private Date data;
    @XmlElement(name = "data_string")
    private String dataString;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "escludi_destinatari_senza_mail")
    private boolean escludiDestinatariSenzaMail;
    @XmlElement(name = "inva_a_pec_o_mail")
    private String inviaAPecOMail;
    @XmlElement(name = "ricerca_solo_pos_pagate")
    private boolean ricercaSoloPosPagate;
    @XmlElement(name = "allegati_fissi")
    private List<ComunicazioneAllegatiModel> allegatiFissi = new ArrayList<ComunicazioneAllegatiModel>();
    @XmlElement(name = "allegati_dinamici")
    private List<ComunicazioneAllegatiModel> allegatiDinamici = new ArrayList<ComunicazioneAllegatiModel>();
    @XmlElement(name = "allega_avviso_pagamento")
    private boolean allegaAvvisiPagamento;
    @XmlElement(name = "converti_in_pdf")
    private boolean convertiInPDF;
    @XmlElement(name = "firmatari")
    private List<ComunicazioneFirmatariModel> firmatari = new ArrayList<ComunicazioneFirmatariModel>(0);
    @XmlElement(name = "protocollazione")
    private ComunicazioneProtocolloModel protocollazione;
    @XmlElement(name = "account_mail")
    private ComunicazioneAccountMailModel accountMail;
    @XmlElement(name = "destinatari")
    private List<ComunicazioneMassivaRigaModel> destinatari = new ArrayList<ComunicazioneMassivaRigaModel>(0);
    @XmlElement(name = "servizio_appio")
    private String servizioAppio;
    @XmlElement(name = "tipo_comunicazione")
    private String tipocomunicazione;
    @XmlElement(name = "contesto")
    private String contesto;
    @XmlElement(name = "oggetto_comunicazione")
    private String oggettoComunicazione;
    @XmlElement(name = "body_comunicazione")
    private String bodyComunicazione;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
	this.dataString = Utilities.formatDate(this.data, false);
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public boolean isEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public void setEscludiDestinatariSenzaMail(boolean escludiDestinatariSenzaMail) {

	this.escludiDestinatariSenzaMail = escludiDestinatariSenzaMail;
    }

    public String getInviaAPecOMail() {

	return inviaAPecOMail;
    }

    public void setInviaAPecOMail(String inviaAPecOMail) {

	this.inviaAPecOMail = inviaAPecOMail;
    }

    public boolean isRicercaSoloPosPagate() {

	return ricercaSoloPosPagate;
    }

    public void setRicercaSoloPosPagate(boolean ricercaSoloPosPagate) {

	this.ricercaSoloPosPagate = ricercaSoloPosPagate;
    }

    public List<ComunicazioneAllegatiModel> getAllegatiFissi() {

	return allegatiFissi;
    }

    public void setAllegatiFissi(List<ComunicazioneAllegatiModel> allegatiFissi) {

	this.allegatiFissi = allegatiFissi;
    }

    public List<ComunicazioneAllegatiModel> getAllegatiDinamici() {

	return allegatiDinamici;
    }

    public void setAllegatiDinamici(List<ComunicazioneAllegatiModel> allegatiDinamici) {

	this.allegatiDinamici = allegatiDinamici;
    }

    public boolean isAllegaAvvisiPagamento() {

	return allegaAvvisiPagamento;
    }

    public void setAllegaAvvisiPagamento(boolean allegaAvvisiPagamento) {

	this.allegaAvvisiPagamento = allegaAvvisiPagamento;
    }

    public boolean isConvertiInPDF() {

	return convertiInPDF;
    }

    public void setConvertiInPDF(boolean convertiInPDF) {

	this.convertiInPDF = convertiInPDF;
    }

    public List<ComunicazioneFirmatariModel> getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(List<ComunicazioneFirmatariModel> firmatari) {

	this.firmatari = firmatari;
    }

    public ComunicazioneProtocolloModel getProtocollazione() {

	return protocollazione;
    }

    public void setProtocollazione(ComunicazioneProtocolloModel protocollazione) {

	this.protocollazione = protocollazione;
    }

    public ComunicazioneAccountMailModel getAccountMail() {

	return accountMail;
    }

    public void setAccountMail(ComunicazioneAccountMailModel accountMail) {

	this.accountMail = accountMail;
    }

    public List<ComunicazioneMassivaRigaModel> getDestinatari() {

	return destinatari;
    }

    public void setDestinatari(List<ComunicazioneMassivaRigaModel> destinatari) {

	this.destinatari = destinatari;
    }

    public String getServizioAppio() {

	return servizioAppio;
    }

    public void setServizioAppio(String servizioAppio) {

	this.servizioAppio = servizioAppio;
    }

    public String getTipocomunicazione() {

	return tipocomunicazione;
    }

    public void setTipocomunicazione(String tipocomunicazione) {

	this.tipocomunicazione = tipocomunicazione;
    }

    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    public String getOggettoComunicazione() {

	return oggettoComunicazione;
    }

    public void setOggettoComunicazione(String oggettoComunicazione) {

	this.oggettoComunicazione = oggettoComunicazione;
    }

    public String getBodyComunicazione() {

	return bodyComunicazione;
    }

    public void setBodyComunicazione(String bodyComunicazione) {

	this.bodyComunicazione = bodyComunicazione;
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
    public static ComunicazioneMassivaGenModel fromMassiveTestata(MassiveTestata testata, IWorkflowComunicazioniService workFlowService,
	    IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	if (testata == null || testata.getId() == null || testata.getId().getCodice() == null) {
	    return null;
	}
	ComunicazioneMassivaGenModel model = new ComunicazioneMassivaGenModel();
	if (testata.getSenderAccount() != null) {
	    ComunicazioneAccountMailModel accModel = new ComunicazioneAccountMailModel(testata.getSenderAccount().getDescrizione(),
		    testata.getFkidMailtipo() != null ? testata.getFkidMailtipo().getDescrizione() : "Personalizzato");
	    model.setAccountMail(accModel);
	}
	model.setAlias(ORMHelper.getIdcomuneAlias());
	model.setAllegaAvvisiPagamento("1".equals(testata.getParametro(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO)));
	for (MassiveTLettere lettera : testata.getLettereTipo()) {
	    model.getAllegatiDinamici().add(ComunicazioneAllegatiModel.fromMassiveTLettere(lettera));
	}
	for (MassiveTAllegati allegato : testata.getAllegati()) {
	    model.getAllegatiFissi().add(ComunicazioneAllegatiModel.fromMassiveTAllegati(allegato));
	}
	model.setConvertiInPDF("1".equals(testata.getParametro(ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF)));
	model.setData(testata.getDataComunicazione());
	model.setDescrizione(testata.getDescrizione());
	for (MassiveDettaglio destinatario : testata.getDettagli()) {
	    model.getDestinatari().add(ComunicazioneMassivaRigaModel.fromMassiveDettaglio(destinatario, workFlowService, appIoCodaMassiveDDAO));
	}
	model.setEscludiDestinatariSenzaMail("1".equals(testata.getParametro(ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL)));
	for (MassiveTFirmatari firmatario : testata.getFirmatari()) {
	    model.getFirmatari().add(ComunicazioneFirmatariModel.fromMassiveTFirmatari(firmatario));
	}
	model.setId(testata.getId().getCodice());
	model.setInviaAPecOMail(testata.getParametro(ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE));
	model.setServizioAppio(testata.getParametro(ParametriConstants.APPIO_SERVIZIO));
	StringBuilder sb = new StringBuilder();
	if (model.getInviaAPecOMail() != null) {
	    sb.append(" MAIL,");
	}
	if (model.getServizioAppio() != null) {
	    sb.append(" APP.IO,");
	}
	if (testata.getParametro(ParametriConstants.RICHIEDE_PROTOCOLLAZIONE) != null
		&& "1".equals(testata.getParametro(ParametriConstants.RICHIEDE_PROTOCOLLAZIONE))) {
	    sb.append(" PROTOCOLLAZIONE,");
	}
	if (sb.length() > 0) {
	    sb.deleteCharAt(sb.length() - 1);
	}
	model.setTipocomunicazione(sb.toString());
	model.setProtocollazione(ComunicazioneProtocolloModel.fromMailTipo(testata.getProtMailtipo()));
	model.setRicercaSoloPosPagate("1".equals(testata.getParametro(ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE)));
	model.setSoftware(ORMHelper.getSoftware());
	model.setOggettoComunicazione(testata.getParametro(ParametriConstants.OGGETTOMAIL_NAME));
	model.setBodyComunicazione(testata.getParametro(ParametriConstants.BODYMAIL_NAME));
	return model;
    }
}
