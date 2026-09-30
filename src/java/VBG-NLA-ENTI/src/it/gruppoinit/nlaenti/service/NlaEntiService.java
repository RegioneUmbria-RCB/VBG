package it.gruppoinit.nlaenti.service;

import it.gov.impresainungiorno.schema.base.CodiceREA;
import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.InfoSchema;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.FormaGiuridica;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.gruppoinit.nlaenti.service.mailservice.MailServiceWSClient;
import it.gruppoinit.nlaenti.service.sigeprosecurity.SigeproSecurityWebServiceClient;
import it.gruppoinit.schema.mailservice.AttachmentType;
import it.gruppoinit.schema.mailservice.AttachmentsType;
import it.gruppoinit.schema.mailservice.MailMessageType;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.utils.Utilities;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.MetaDatoType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NlaEntiService {

    private static final String identificativo_suap = "identificativo-suap";
    private static final String codice_aoo = "codice-aoo";
    private static final String codice_amministrazione = "codice-amministrazione";
    private static final String descrizione_suap = "descrizione-suap";
    private static final String ente_destinatario_nome = "ente-destinatario-nome";
    private static final Logger log = LoggerFactory.getLogger(NlaEntiService.class);
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private MailServiceWSClient mailServiceWSClient;

    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    public RiferimentiAttivitaType gestioneComunicazioneEnte(InserimentoAttivitaNLARequest request, DettaglioPraticaType pratica) {

	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	//1. Formatta PEC
	//2. Prapara allegati
	//3. Invio PEC
	String token = sigeproSecurityWebServiceClient.loginAPP(request.getSportelloMittente().getIdEnte());
	Map<String, String> params = sigeproSecurityWebServiceClient.getParams("WSHOSTURL_MAILSERVICE");
	String mailSenderUrl = params.get("WSHOSTURL_MAILSERVICE");
	if (StringUtils.isBlank(mailSenderUrl)) {
	    log.error("Il parametro WSHOSTURL_MAILSERVICE di sigeprosecurity è vuoto");
	    throw new RuntimeException("Il parametro WSHOSTURL_MAILSERVICE è vuoto");
	}
	log.debug("gestioneComunicazioneEnte# Recupero altri dati dalla pratica...");
	Map<String, String> altridati = getAltriParametri(request);
	MailMessageType message = new MailMessageType();
	// il mittente l'host del server mail e le credenziali per accedere le inserisce il ws mailService
	message.setOggetto(getOggettoMessaggio(request.getSportelloMittente(), pratica, altridati));
	message.setCorpoMail(getCorpoMessaggio(request.getSportelloMittente(), pratica, request.getDatiAttivita().getIdAttivita(), altridati));
	message.setAttachments(getAttachments(pratica, request, altridati));
	String emailDestinatario = request.getSportelloDestinatario().getPecSportello();
	if (StringUtils.isBlank(emailDestinatario)) {
	    log.error("Il campo pecsportello dello sportelloDestinatario è vuoto");
	    throw new RuntimeException("Specificare l'indirizzo pec del destinatario");
	}
	message.setDestinatari(emailDestinatario);
	Integer codMov;
	try {
	    codMov = Integer.valueOf(request.getDatiAttivita().getIdAttivita());
	} catch (NumberFormatException e) {
	    log.error("L'idAttività dell'xml InserimentoAttivitaNLARequest non è un numero");
	    throw new RuntimeException("Specificare l'id attività");
	}
	message.setMessageID(this.getMessageID(request));
	mailServiceWSClient.sendMail(mailSenderUrl, codMov, request.getSportelloMittente().getIdSportello(), token, message);
	rifAtt.setIdAttivita(request.getDatiAttivita().getIdAttivita());
	rifAtt.setIdPratica(request.getDatiAttivita().getIdPratica());
	return rifAtt;
    }

    private Map<String, String> getAltriParametri(InserimentoAttivitaNLARequest request) {

	log.debug("getAltriParametri# start....");
	Map<String, String> map = new HashMap<String, String>();
	if (request.getDatiAttivita() != null && request.getDatiAttivita().getAltriDati() != null
		&& !request.getDatiAttivita().getAltriDati().isEmpty()) {
	    List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	    for (ParametroType parametroType : altriDati) {
		log.debug("getAltriParametri# nome parametro = {}", parametroType.getNome());
		if (parametroType.getValore() != null && !parametroType.getValore().isEmpty()) {
		    log.debug("getAltriParametri# add key = {}, value = {}", parametroType.getNome(), parametroType.getValore().get(0).getCodice()
			    .trim());
		    map.put(parametroType.getNome(), parametroType.getValore().get(0).getCodice().trim());
		}
	    }
	}
	log.debug("getAltriParametri# end....");
	return map;
    }

    /**
     * "&lt;ufficio ente mittente&gt; - &lt;codice fiscale impresa&gt; - &lt;denominazione impresa&gt;"
     * 
     * @return
     */
    private String getOggettoMessaggio(SportelloType sportelloMittente, DettaglioPraticaType pratica, Map<String, String> altridati) {

	// se azienda è nullo utilizzo richiedente
	StringBuffer buf = new StringBuffer();
	//buf.append(getIdentificativoSUAP(pratica));
	buf.append(StringUtils.defaultIfEmpty(altridati.get(identificativo_suap), ""));
	buf.append(" - ");
	if (pratica.getAziendaRichiedente() != null) {
	    if (StringUtils.isNotBlank(pratica.getAziendaRichiedente().getCodiceFiscale())) {
		buf.append(pratica.getAziendaRichiedente().getCodiceFiscale());
	    } else {
		buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getPartitaIva()));
	    }
	    buf.append(" - ");
	    buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getRagioneSociale()));
	} else {
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCodiceFiscale()));
	    buf.append(" - ");
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getNome()));
	    buf.append(" ");
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCognome()));
	}
	return buf.toString();
    }

    /**
     * "SUAP: &lt;identificativo SUAP competente&gt;" <br />
     * "Pratica: &lt;codice pratica SUAP&gt;" <br />
     * "Impresa: &lt;codice fiscale impresa&gt; - &lt;denominazione impresa&gt;" <br />
     * "Protocollo RI &lt;identificativo protocollo della comunicazione unica&gt;" <br />
     * "Protocollo Ente: &lt;identificativo protocollo dell’ente&gt;" <br />
     * "Tipo messaggio: &lt;codice che identifica il tipo di messaggio&gt;"
     * 
     * @return
     */
    private String getCorpoMessaggio(SportelloType sportelloMittente, DettaglioPraticaType pratica, String idAttivita, Map<String, String> altridati) {

	StringBuffer buf = new StringBuffer();
	// TODO verificare corpo messaggio
	buf.append("SUAP: ");
	//buf.append(getIdentificativoSUAP(pratica));
	buf.append(StringUtils.defaultIfEmpty(altridati.get(identificativo_suap), ""));
	buf.append("\n");
	buf.append("Pratica: ");
	buf.append(StringUtils.defaultIfEmpty(pratica.getCodicePraticaTelematica(), pratica.getIdPratica()));
	buf.append("\n");
	if (pratica.getAziendaRichiedente() != null) {
	    buf.append("Impresa: ");
	    if (StringUtils.isNotBlank(pratica.getAziendaRichiedente().getCodiceFiscale())) {
		buf.append(pratica.getAziendaRichiedente().getCodiceFiscale());
	    } else {
		buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getPartitaIva()));
	    }
	    buf.append(" - ");
	    buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getRagioneSociale()));
	} else {
	    buf.append("Richiedente: ");
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCodiceFiscale()));
	    buf.append(" - ");
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getNome()));
	    buf.append(" ");
	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCognome()));
	}
	buf.append("\n");
	buf.append("Protocollo RI: ");
	buf.append(StringUtils.defaultString(pratica.getNumeroProtocolloGenerale()));
	buf.append("\n");
	buf.append("Protocollo Ente: ");
	buf.append(StringUtils.defaultString(pratica.getNumeroProtocolloGenerale()));
	buf.append("\n");
	buf.append("Tipo messaggio: ");
	buf.append(idAttivita);
	return buf.toString();
    }

    /**
     * recupera tutti gli allegati del messaggio in ingresso più il file SUAP-ENTE.xml
     * 
     * @return
     */
    private AttachmentsType getAttachments(DettaglioPraticaType pratica, InserimentoAttivitaNLARequest request, Map<String, String> altridati) {

	AttachmentsType atts = new AttachmentsType();
	atts.getAttachment().add(getAllegatoSUAPENTE(pratica, request, altridati));
	List<DocumentiType> listDocs = request.getDatiAttivita().getDocumenti();
	if (listDocs != null) {
	    for (DocumentiType doc : listDocs) {
		AllegatiType all = doc.getAllegati();
		if (all != null) {
		    AllegatoBinarioType allBin = all.getFile();
		    if (allBin != null) {
			AttachmentType att = new AttachmentType();
			att.setDescrizione(doc.getDocumento());
			att.setFileName(allBin.getFileName());
			att.setMimeType(allBin.getMimeType());
			att.setBinaryData(dataHandlerToBytes(allBin.getBinaryData()));
			atts.getAttachment().add(att);
		    }
		}
	    }
	}
	return atts;
    }

    private byte[] dataHandlerToBytes(DataHandler dh) {

	try {
	    return IOUtils.toByteArray(dh.getInputStream());
	} catch (IOException e) {
	    log.error("dataHandlerToBytes: ", e);
	    throw new RuntimeException(e);
	}
    }

    private DataHandler bytesToDataHandler(byte[] content) {

	try {
	    DataSource ds = new ByteArrayDataSource(content, "application/octet-stream");
	    return new DataHandler(ds);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private AttachmentType getAllegatoSUAPENTE(DettaglioPraticaType pratica, InserimentoAttivitaNLARequest request, Map<String, String> altridati) {

	AttachmentType att = new AttachmentType();
	CooperazioneSUAPEnte cooperazioneSUAPEnte = new CooperazioneSUAPEnte();
	populateCooperazioneSUAPEnte(cooperazioneSUAPEnte, pratica, request, altridati);
	String xml = Utilities.marshallObject(cooperazioneSUAPEnte);
	try {
	    att.setBinaryData(xml.getBytes("UTF-8"));
	} catch (UnsupportedEncodingException e) {
	    log.error("getAllegatoSUAPENTE(): {}", e.getMessage());
	}
	att.setFileName("SUAP-ENTE.xml");
	att.setMimeType("text/xml");
	return att;
    }

    private void populateCooperazioneSUAPEnte(CooperazioneSUAPEnte cooperazioneSUAPEnte, DettaglioPraticaType pratica,
	    InserimentoAttivitaNLARequest request, Map<String, String> altridati) {

	String idComuneAlias = request.getSportelloMittente().getIdEnte();
	// Sezione INFO SCHEMA
	log.debug("populateCooperazioneSUAPEnte# info schema....");
	InfoSchema infoSchema = new InfoSchema();
	GregorianCalendar c = new GregorianCalendar();
	infoSchema.setData(Utilities.getXMLGregorianCalendarWithoutTimeZone(c));
	infoSchema.setVersione("1.1.0");
	cooperazioneSUAPEnte.setInfoSchema(infoSchema);
	// SEZIONE INTESTAZIONE
	log.debug("populateCooperazioneSUAPEnte# Intestazione....");
	Intestazione intestazione = new Intestazione();
	intestazione.setTotale(1);
	intestazione.setProgressivo(1);
	// SEZIONE ENTE DESTINATARIO 
	log.debug("populateCooperazioneSUAPEnte# EstremiEnte....");
	EstremiEnte estremiEnte = new EstremiEnte();
	log.debug("populateCooperazioneSUAPEnte# pec = indirizzo a cui spediamo");
	estremiEnte.setPec(request.getSportelloDestinatario().getPecSportello());
	log.debug("populateCooperazioneSUAPEnte# nome ente = dal parametro altri dati '{}'", ente_destinatario_nome);
	estremiEnte.setValue(StringUtils.defaultIfEmpty(altridati.get(ente_destinatario_nome), ""));
	intestazione.getEnteDestinatario().add(estremiEnte);
	// SSEZIONE CODICE PRATICA
	log.debug("populateCooperazioneSUAPEnte# CodicePratica = IsPratica");
	intestazione.setCodicePratica(StringUtils.defaultIfEmpty(pratica.getCodicePraticaTelematica(), pratica.getIdPratica()));
	// SEZIONE suap-competente
	log.debug("populateCooperazioneSUAPEnte# EstremiSuap = Altri dati: {}, {}, {}, {}", new Object[] { identificativo_suap, codice_aoo,
		codice_amministrazione, descrizione_suap });
	EstremiSuap estremiSuap = new EstremiSuap();
	String _IdentificativoSuap = StringUtils.defaultIfEmpty(altridati.get(identificativo_suap), "");
	if (StringUtils.isNotBlank(_IdentificativoSuap)) {
	    estremiSuap.setIdentificativoSuap(new BigInteger(_IdentificativoSuap));
	}
	estremiSuap.setCodiceAoo(StringUtils.defaultIfEmpty(altridati.get(codice_aoo), ""));
	estremiSuap.setCodiceAmministrazione(StringUtils.defaultIfEmpty(altridati.get(codice_amministrazione), ""));
	estremiSuap.setValue(StringUtils.defaultIfEmpty(altridati.get(descrizione_suap), ""));
	intestazione.setSuapCompetente(estremiSuap);
	// SEZIONE IMPRESA
	log.debug("populateCooperazioneSUAPEnte# AnagraficaImpresa....");
	AnagraficaImpresa anagraficaImpresa = new AnagraficaImpresa();
	populateAnagrafeImpresa(anagraficaImpresa, pratica, idComuneAlias);
	log.debug("populateCooperazioneSUAPEnte# Impresa....");
	intestazione.setImpresa(anagraficaImpresa);
	// SEZIONE OGGETTO-PRATICA
	log.debug("populateCooperazioneSUAPEnte# oggetto-pratica.... (descrizione intervento pratica)");
	OggettoComunicazione oggettoComunicazione = new OggettoComunicazione();
	Map<String, String> interventoMap = findQUERY_INTERVENTO(Integer.parseInt(pratica.getIntervento().getCodice()), idComuneAlias);
	if (interventoMap != null && !interventoMap.isEmpty()) {
	    log.debug("populateCooperazioneSUAPEnte# Mapping intervento vbg --> intervento RI presente ");
	    for (Map.Entry<String, String> entry : interventoMap.entrySet()) {
		oggettoComunicazione.setValue(entry.getValue());
		TipoIntervento ti_ri = decodeTipoIntervento(entry.getKey());
		oggettoComunicazione.setTipoIntervento(ti_ri);
	    }
	} else {
	    log.debug("populateCooperazioneSUAPEnte# Mapping intervento vbg --> intervento RI non presente. Uso come descrione la voce dell'albero di vbg ");
	    oggettoComunicazione.setValue(pratica.getIntervento().getDescrizione());
	}
	intestazione.setOggettoPratica(oggettoComunicazione);
	// SEZIONE PROTOCOLLO PRATICA SUAP
	if (StringUtils.isNotBlank(pratica.getNumeroProtocolloGenerale())) {
	    log.debug("populateCooperazioneSUAPEnte# protocollo-pratica-suap....");
	    ProtocolloSUAP protocolloSUAP = new ProtocolloSUAP();
	    protocolloSUAP.setNumeroRegistrazione(pratica.getNumeroProtocolloGenerale());
	    if (pratica.getDataProtocolloGenerale() != null) {
		protocolloSUAP.setDataRegistrazione(pratica.getDataProtocolloGenerale());
	    }
	    protocolloSUAP.setCodiceAoo(StringUtils.defaultIfEmpty(altridati.get(codice_aoo), ""));
	    protocolloSUAP.setCodiceAmministrazione(StringUtils.defaultIfEmpty(altridati.get(codice_amministrazione), ""));
	    intestazione.setProtocolloPraticaSuap(protocolloSUAP);
	}
	// SEZIONE OGGETTO COMUNICAZIONE
	log.debug("populateCooperazioneSUAPEnte# oggetto-comunicazione = istanze lavori (oggetto) di istanza {}", pratica.getNumeroPratica());
	OggettoCooperazione oggettoCooperazione = new OggettoCooperazione();
	oggettoCooperazione.setTipoCooperazione("SENDENT2");
	oggettoCooperazione.setValue(StringUtils.defaultIfEmpty(pratica.getOggetto(), ""));
	intestazione.setOggettoComunicazione(oggettoCooperazione);
	// SEZIONE TESTO COMUNICAZIONE
	log.debug("populateCooperazioneSUAPEnte# testo-comunicazione = parere movimento {}", request.getDatiAttivita().getTipoAttivita().getCodice());
	intestazione.setTestoComunicazione(StringUtils.defaultIfEmpty(request.getDatiAttivita().getNote(), ""));
	// SEZIONE PROTOCOLLO
	if (StringUtils.isNotBlank(request.getDatiAttivita().getNumeroProtocolloGenerale())) {
	    log.debug("populateCooperazioneSUAPEnte# protocollo = protocollo movimento {}", request.getDatiAttivita().getTipoAttivita().getCodice());
	    ProtocolloSUAP protocolloSUAPMovimento = new ProtocolloSUAP();
	    protocolloSUAPMovimento.setCodiceAmministrazione(StringUtils.defaultIfEmpty(altridati.get(codice_amministrazione), ""));
	    protocolloSUAPMovimento.setCodiceAoo(StringUtils.defaultIfEmpty(altridati.get(codice_aoo), ""));
	    protocolloSUAPMovimento.setNumeroRegistrazione(request.getDatiAttivita().getNumeroProtocolloGenerale());
	    if (request.getDatiAttivita().getDataProtocolloGenerale() != null) {
		protocolloSUAPMovimento.setDataRegistrazione(request.getDatiAttivita().getDataProtocolloGenerale());
	    }
	    intestazione.setProtocollo(protocolloSUAPMovimento);
	}
	cooperazioneSUAPEnte.setIntestazione(intestazione);
	List<DocumentiType> documentiTypes = pratica.getDocumenti();
	log.debug("populateCooperazioneSUAPEnte# popola allegati.....");
	AllegatoCooperazione allegatoCooperazioneRiepilogo = null;
	AllegatoCooperazione allegatoCooperazioneMDA_Pratica = null;
	AllegatoCooperazione allegatoPratica = null;
	int i = 1;
	for (DocumentiType documentiType : documentiTypes) {
	    if ("RiepilogoDomanda".equals(documentiType.getTipoDocumento())) {
		String nomeFileRiepilogo = "";
		if (StringUtils.isNotBlank(pratica.getCodicePraticaTelematica())) {
		    String ext = Utilities.extractExtension(documentiType.getAllegati().getAllegato());
		    nomeFileRiepilogo = pratica.getCodicePraticaTelematica().trim() + ".SUAP." + ext;
		    allegatoCooperazioneRiepilogo = populateAllegatoCooperazione(documentiType, "SUDOC", nomeFileRiepilogo);
		    String nomeFileRiepilogoMDA = "";
		    //06884680726-05062018-1554.001.MDA.PDF.P7M
		    String ext1 = Utilities.extractExtension(documentiType.getAllegati().getAllegato());
		    nomeFileRiepilogoMDA = pratica.getCodicePraticaTelematica().trim() + ".001.MDA." + ext1;
		    allegatoCooperazioneMDA_Pratica = populateAllegatoCooperazione(documentiType, "MDAAD", nomeFileRiepilogoMDA);
		}
		cooperazioneSUAPEnte.getAllegato().add(allegatoCooperazioneRiepilogo);
		cooperazioneSUAPEnte.getAllegato().add(allegatoCooperazioneMDA_Pratica);
	    } else {
		//06884680726-05062018-1554.010.PDF.P7M
		String nomeFileAllegato = "";
		if (StringUtils.isNotBlank(pratica.getCodicePraticaTelematica())) {
		    String num = StringUtils.leftPad(String.valueOf(i), 3, "0");
		    String ext = Utilities.extractExtension(documentiType.getAllegati().getAllegato());
		    nomeFileAllegato = pratica.getCodicePraticaTelematica().trim() + "." + num + "." + ext;
		}
		allegatoPratica = populateAllegatoCooperazione(documentiType, "ALLEG", nomeFileAllegato);
		cooperazioneSUAPEnte.getAllegato().add(allegatoPratica);
		i++;
	    }
	}
    }

    private TipoIntervento decodeTipoIntervento(String codiceIntervetoRI) {

	/**
	 * apertura subentro trasformazione modifiche cessazione altro
	 */
	if ("apertura".equals(codiceIntervetoRI)) {
	    return TipoIntervento.APERTURA;
	} else if ("subentro".equals(codiceIntervetoRI)) {
	    return TipoIntervento.SUBENTRO;
	} else if ("trasformazione".equals(codiceIntervetoRI)) {
	    return TipoIntervento.TRASFORMAZIONE;
	} else if ("modifiche".equals(codiceIntervetoRI)) {
	    return TipoIntervento.MODIFICHE;
	} else if ("cessazione".equals(codiceIntervetoRI)) {
	    return TipoIntervento.CESSAZIONE;
	} else if ("altro".equals(codiceIntervetoRI)) {
	    return TipoIntervento.ALTRO;
	} else {
	    log.debug("decodeTipoIntervento# codfica non trovata, riporto altro");
	    return TipoIntervento.ALTRO;
	}
    }

    private AllegatoCooperazione populateAllegatoCooperazione(DocumentiType documentiType, String codiceDocumento, String fileNome) {

	AllegatoCooperazione allegatoCooperazione = new AllegatoCooperazione();
	allegatoCooperazione.setCod(codiceDocumento);
	allegatoCooperazione.setDescrizione(StringUtils.defaultIfEmpty(documentiType.getDocumento(), ""));
	if (documentiType.getAllegati() != null) {
	    List<MetaDatoType> list = documentiType.getAllegati().getMetaDati();
	    for (MetaDatoType metaDatoType : list) {
		if ("FILE_CONTENT_TYPE".equals(metaDatoType.getCodice())) {
		    allegatoCooperazione.setMime(StringUtils.defaultIfEmpty(metaDatoType.getValore(), ""));
		}
		if ("FILE_SIZE".equals(metaDatoType.getCodice())) {
		    allegatoCooperazione.setDimensione(new BigInteger(StringUtils.defaultIfEmpty(metaDatoType.getValore(), "0")));
		}
	    }
	    //allegatoCooperazione.setMimeBase(value);
	    allegatoCooperazione.setNomeFile(StringUtils.defaultIfEmpty(fileNome, documentiType.getAllegati().getAllegato()));
	    allegatoCooperazione.setNomeFileOriginale(StringUtils.defaultIfEmpty(documentiType.getAllegati().getAllegato(), ""));
	}
	//allegatoCooperazione.setTipo(value);
	return allegatoCooperazione;
    }

    private void populateAnagrafeImpresa(AnagraficaImpresa anagraficaImpresa, DettaglioPraticaType pratica, String idcomuneAlias) {

	if (pratica.getAziendaRichiedente() != null) {
	    log.debug("populateAnagrafeImpresa# AziendaRichiedente = {}", pratica.getAziendaRichiedente().getRagioneSociale());
	    anagraficaImpresa.setCodiceFiscale(StringUtils.defaultIfEmpty(pratica.getAziendaRichiedente().getCodiceFiscale(), ""));
	    anagraficaImpresa.setPartitaIva(StringUtils.defaultIfEmpty(pratica.getAziendaRichiedente().getPartitaIva(), ""));
	    anagraficaImpresa.setRagioneSociale(pratica.getAziendaRichiedente().getRagioneSociale());
	    log.debug("populateAnagrafeImpresa# populare rea....");
	    if (pratica.getAziendaRichiedente().getIscrizioneREA() != null) {
		CodiceREA codiceREA = new CodiceREA();
		if (pratica.getAziendaRichiedente().getIscrizioneREA().getData() != null) {
		    codiceREA.setDataIscrizione(pratica.getAziendaRichiedente().getIscrizioneREA().getData());
		}
		codiceREA.setProvincia(StringUtils.defaultString(pratica.getAziendaRichiedente().getIscrizioneREA().getSiglaProvincia(), ""));
		codiceREA.setValue(StringUtils.defaultString(pratica.getAziendaRichiedente().getIscrizioneREA().getNumero(), ""));
		anagraficaImpresa.setCodiceREA(codiceREA);
	    }
	    log.debug("populateAnagrafeImpresa# populate forma giuridica...");
	    FormaGiuridica formaGiuridica = new FormaGiuridica();
	    if (StringUtils.isNotBlank(pratica.getAziendaRichiedente().getNaturaGiuridica())) {
		Map<String, String> r = findRI_FORMEGIURIDICHE(pratica.getAziendaRichiedente().getNaturaGiuridica().trim(), idcomuneAlias);
		if (r != null && !r.isEmpty()) {
		    for (Map.Entry<String, String> entry : r.entrySet()) {
			formaGiuridica.setValue(entry.getValue());
			formaGiuridica.setCodice(entry.getKey());
		    }
		} else {
		    log.debug("populateAnagrafeImpresa# codifica forma giuridica non presente per valore descrizione = {}", pratica
			    .getAziendaRichiedente().getNaturaGiuridica());
		    formaGiuridica.setValue("NON PRECISATA");
		    formaGiuridica.setCodice("XX");
		}
	    } else {
		log.debug("populateAnagrafeImpresa# Forma giuridica non presente");
		formaGiuridica.setValue("NON PRECISATA");
		formaGiuridica.setCodice("XX");
	    }
	    anagraficaImpresa.setFormaGiuridica(formaGiuridica);
	    // condice di impresa in un giorno non ce l'ho
	    //formaGiuridica.setCodice(value);
	    if (pratica.getAziendaRichiedente().getSedeLegale() != null) {
		log.debug("populateAnagrafeImpresa# populate sede legale");
		IndirizzoConRecapiti indirizzoConRecapiti = new IndirizzoConRecapiti();
		populateIndirizzoConRecapiti(indirizzoConRecapiti, pratica.getAziendaRichiedente().getSedeLegale());
		anagraficaImpresa.setIndirizzo(indirizzoConRecapiti);
		it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante anagraficaRappresentante = new it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante();
		if (pratica.getRichiedente().getAnagrafica() != null) {
		    log.debug("populateAnagrafeImpresa# populate legale rappresentante...");
		    PersonaFisicaType pft = pratica.getRichiedente().getAnagrafica();
		    anagraficaRappresentante.setCodiceFiscale(StringUtils.defaultIfEmpty(pft.getCodiceFiscale(), ""));
		    // anagraficaRappresentante.setPartitaIva(StringUtils.defaultIfEmpty(pft.get, ""));
		    anagraficaRappresentante.setCognome(StringUtils.defaultIfEmpty(pft.getCognome(), ""));
		    anagraficaRappresentante.setNome(StringUtils.defaultIfEmpty(pft.getNome(), ""));
		    if (pft.getCittadinanza() != null) {
			Stato stato = new Stato();
			stato.setValue(StringUtils.defaultIfEmpty(pft.getCittadinanza().getDescrizione(), ""));
			//stato.setCodice(StringUtils.defaultIfEmpty(pft.getCittadinanza().getId(), ""));
			stato.setCodiceCatastale(StringUtils.defaultIfEmpty(pft.getCittadinanza().getCodiceCatastale(), ""));
			anagraficaRappresentante.setNazionalita(stato);
		    }
		    if (pratica.getRichiedente().getRuolo() != null && pratica.getRichiedente().getRuolo().getIdRuolo() != null) {
			Map<String, String> r = findQUERY_FORME_TIPISOGGETTO(Integer.parseInt(pratica.getRichiedente().getRuolo().getIdRuolo()),
				idcomuneAlias);
			Carica carica = new Carica();
			if (r != null && !r.isEmpty()) {
			    for (Map.Entry<String, String> entry : r.entrySet()) {
				carica.setValue(entry.getValue());
				carica.setCodice(entry.getKey());
			    }
			    anagraficaRappresentante.setCarica(carica);
			} else {
			    log.debug("populateAnagrafeImpresa# codifica carica non non tovata per codice: {}", pratica.getRichiedente().getRuolo()
				    .getIdRuolo());
			}
		    } else {
			log.debug("populateAnagrafeImpresa# carica non settata sulla pratica");
		    }
		    anagraficaImpresa.setLegaleRappresentante(anagraficaRappresentante);
		}
	    }
	} else {
	    log.debug("populateAnagrafeImpresa# Richiedente = {} {}", pratica.getRichiedente().getAnagrafica().getCognome(),
		    StringUtils.defaultIfEmpty(pratica.getRichiedente().getAnagrafica().getNome(), ""));
	    log.debug("populateAnagrafeImpresa# non ha forma giuridica....");
	    FormaGiuridica formaGiuridica = new FormaGiuridica();
	    formaGiuridica.setValue("NON PRECISATA");
	    formaGiuridica.setCodice("XX");
	    anagraficaImpresa.setFormaGiuridica(formaGiuridica);
	    PersonaFisicaType pft = pratica.getRichiedente().getAnagrafica();
	    anagraficaImpresa.setCodiceFiscale(StringUtils.defaultIfEmpty(pft.getCodiceFiscale(), ""));
	    StringBuffer nominativo_nome = new StringBuffer(pft.getCognome());
	    nominativo_nome = nominativo_nome.append(" ").append(StringUtils.defaultIfEmpty(pft.getNome(), ""));
	    anagraficaImpresa.setRagioneSociale(nominativo_nome.toString().trim());
	    if (pft.getResidenza() != null) {
		IndirizzoConRecapiti indirizzoConRecapiti = new IndirizzoConRecapiti();
		populateIndirizzoConRecapiti(indirizzoConRecapiti, pft.getResidenza());
		anagraficaImpresa.setIndirizzo(indirizzoConRecapiti);
	    }
	    // /////////////////////////// legale rappresentate
	    it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante anagraficaRappresentante = new it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante();
	    log.debug("populateAnagrafeImpresa# populate legale rappresentante...");
	    anagraficaRappresentante.setCodiceFiscale(StringUtils.defaultIfEmpty(pft.getCodiceFiscale(), ""));
	    // anagraficaRappresentante.setPartitaIva(StringUtils.defaultIfEmpty(pft.get, ""));
	    anagraficaRappresentante.setCognome(StringUtils.defaultIfEmpty(pft.getCognome(), ""));
	    anagraficaRappresentante.setNome(StringUtils.defaultIfEmpty(pft.getNome(), ""));
	    if (pft.getCittadinanza() != null) {
		Stato stato = new Stato();
		stato.setValue(StringUtils.defaultIfEmpty(pft.getCittadinanza().getDescrizione(), ""));
		//stato.setCodice(StringUtils.defaultIfEmpty(pft.getCittadinanza().getId(), ""));
		stato.setCodiceCatastale(StringUtils.defaultIfEmpty(pft.getCittadinanza().getCodiceCatastale(), ""));
		anagraficaRappresentante.setNazionalita(stato);
	    }
	    if (pratica.getRichiedente().getRuolo() != null && pratica.getRichiedente().getRuolo().getIdRuolo() != null) {
		Map<String, String> r = findQUERY_FORME_TIPISOGGETTO(Integer.parseInt(pratica.getRichiedente().getRuolo().getIdRuolo()),
			idcomuneAlias);
		Carica carica = new Carica();
		if (r != null) {
		    for (Map.Entry<String, String> entry : r.entrySet()) {
			carica.setValue(entry.getValue());
			carica.setCodice(entry.getKey());
		    }
		    anagraficaRappresentante.setCarica(carica);
		} else {
		    log.debug("populateAnagrafeImpresa# codifica carica non non tovata per codice: {}", pratica.getRichiedente().getRuolo()
			    .getIdRuolo());
		}
	    } else {
		log.debug("populateAnagrafeImpresa# carica non settata sulla pratica");
	    }
	    anagraficaImpresa.setLegaleRappresentante(anagraficaRappresentante);
	    /////////////////////////////////
	}
    }

    private void populateIndirizzoConRecapiti(IndirizzoConRecapiti indirizzoConRecapiti, LocalizzazioneType localizzazioneType) {

	log.debug("populateIndirizzoConRecapiti#populateIndirizzoConRecapiti... ");
	indirizzoConRecapiti.setCap(StringUtils.defaultIfEmpty(localizzazioneType.getCap(), ""));
	// non ho il toponimo separato
	//indirizzoConRecapiti.setToponimo();
	indirizzoConRecapiti.setDenominazioneStradale(StringUtils.defaultIfEmpty(localizzazioneType.getIndirizzo(), ""));
	indirizzoConRecapiti.setNumeroCivico(StringUtils.defaultIfEmpty(localizzazioneType.getCivico(), ""));
	if (localizzazioneType.getComune() != null) {
	    ComuneType comuneType = localizzazioneType.getComune();
	    Comune comune = new Comune();
	    comune.setCodiceCatastale(StringUtils.defaultIfEmpty(comuneType.getCodiceCatastale(), ""));
	    comune.setCodiceIstat(StringUtils.defaultIfEmpty(comuneType.getCodiceIstat(), ""));
	    comune.setValue(StringUtils.defaultIfEmpty(comuneType.getComune(), ""));
	    indirizzoConRecapiti.setComune(comune);
	}
	if (StringUtils.isNotBlank(localizzazioneType.getProvincia())) {
	    Provincia provincia = new Provincia();
	    provincia.setValue(localizzazioneType.getProvincia());
	    indirizzoConRecapiti.setProvincia(provincia);
	}
	// FIME non ho lo stato della sede legale
	//indirizzoConRecapiti.setStato(value);
    }

    /**
     * crea il file SUAP-ENTE.xml
     * 
     * @return
     */
    //    private AttachmentType getAllegatoSUAPENTE(DettaglioPraticaType pratica, String idAttivita) {
    //
    //	AttachmentType att = new AttachmentType();
    //	StringBuffer buf = new StringBuffer();
    //	// TODO creare xml (manca xsd)
    //	buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    //	buf.append("<suap-ente>");
    //	buf.append("<suap-id>");
    //	buf.append(getIdentificativoSUAP(pratica));
    //	buf.append("</suap-id>");
    //	buf.append("<pratica-id>");
    //	buf.append(pratica.getIdPratica());
    //	buf.append("</pratica-id>");
    //	buf.append("<impresa>");
    //	if (pratica.getAziendaRichiedente() != null) {
    //	    if (StringUtils.isNotBlank(pratica.getAziendaRichiedente().getCodiceFiscale())) {
    //		buf.append(pratica.getAziendaRichiedente().getCodiceFiscale());
    //	    } else {
    //		buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getPartitaIva()));
    //	    }
    //	    buf.append(" - ");
    //	    buf.append(StringUtils.defaultString(pratica.getAziendaRichiedente().getRagioneSociale()));
    //	} else {
    //	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCodiceFiscale()));
    //	    buf.append(" - ");
    //	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getNome()));
    //	    buf.append(" ");
    //	    buf.append(StringUtils.defaultString(pratica.getRichiedente().getAnagrafica().getCognome()));
    //	}
    //	buf.append("</impresa>");
    //	buf.append("<protocollo-ri>");
    //	buf.append(StringUtils.defaultString(pratica.getNumeroProtocolloGenerale()));
    //	buf.append("</protocollo-ri>");
    //	buf.append("<protocollo-ente>");
    //	buf.append(StringUtils.defaultString(pratica.getNumeroProtocolloGenerale()));
    //	buf.append("</protocollo-ente>");
    //	buf.append("<tipo-messaggio>");
    //	buf.append(idAttivita);
    //	buf.append("</tipo-messaggio>");
    //	buf.append("</suap-ente>");
    //	try {
    //	    att.setBinaryData(buf.toString().getBytes("UTF-8"));
    //	} catch (UnsupportedEncodingException e) {
    //	    log.error("getAllegatoSUAPENTE(): {}", e.getMessage());
    //	}
    //	att.setFileName("SUAP-ENTE.xml");
    //	att.setMimeType("text/xml");
    //	return att;
    //    }
    private String getIdentificativoSUAP(DettaglioPraticaType pratica) {

	String id = "";
	List<ParametroType> altriDati = pratica.getAltriDati();
	if (altriDati != null) {
	    for (ParametroType parametroType : altriDati) {
		if ("IDENTIFICATIVO_SUAP".equalsIgnoreCase(parametroType.getNome())) {
		    List<ValoreParametroType> valori = parametroType.getValore();
		    if (valori != null) {
			id = valori.get(0).getCodice();
		    }
		}
	    }
	}
	return id;
    }

    /**
     * compone il MessageID da inserire nella mail. l'id è composto da:
     * idente-idsportello-idpratica-idattività-timestamp
     * 
     * @param request
     * @return
     */
    private String getMessageID(InserimentoAttivitaNLARequest request) {

	String messageID = request.getSportelloMittente().getIdEnte() + "-" + request.getSportelloMittente().getIdSportello() + "-"
		+ request.getDatiAttivita().getIdPratica() + "-" + request.getDatiAttivita().getIdAttivita() + "-" + (new Date()).getTime();
	return messageID;
    }

    // String idComuneAlias = request.getSportelloMittente().getIdEnte();
    //////////////////////////////////////////////////////// SEZIONE QUERY SUL DB /////////////////////////////////////////////////////////
    private Map<String, String> findRI_FORMEGIURIDICHE(String descFORMEGIURIDICHE, String idComuneAlias) {

	Map<String, String> map = new HashMap<String, String>();
	GetDbConnectionInfoResponse dbconninfo = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias);
	String idcomune = dbconninfo.getIdComune();
	String dbowner = dbconninfo.getDbOwner();
	Connection c = sigeproSecurityWebServiceClient.getConnection(idComuneAlias);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// trovo se ho già inserito i dettagli della comunicazione ri
	String id = null;
	String descrizione = "";
	try {
	    pstmt = c.prepareStatement(QUERY_FORME_GIURIDICHE.replaceAll("DB_SCHEMA", dbowner));
	    pstmt.setString(1, idcomune);
	    pstmt.setString(2, descFORMEGIURIDICHE);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		id = rs.getString(1);
		descrizione = rs.getString(2);
		if (StringUtils.isNotBlank(id)) {
		    map.put(id.toString(), descrizione);
		}
	    }
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante la query findRI_FORMEGIURIDICHE", e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, rs);
	}
	return map;
    }

    private Map<String, String> findQUERY_FORME_TIPISOGGETTO(Integer codicetipisoggetto, String idComuneAlias) {

	Map<String, String> map = new HashMap<String, String>();
	GetDbConnectionInfoResponse dbconninfo = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias);
	String idcomune = dbconninfo.getIdComune();
	String dbowner = dbconninfo.getDbOwner();
	Connection c = sigeproSecurityWebServiceClient.getConnection(idComuneAlias);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// trovo se ho già inserito i dettagli della comunicazione ri
	String id = null;
	String descrizione = "";
	try {
	    pstmt = c.prepareStatement(QUERY_FORME_TIPISOGGETTO.replaceAll("DB_SCHEMA", dbowner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codicetipisoggetto);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		id = rs.getString(1);
		descrizione = rs.getString(2);
		if (StringUtils.isNotBlank(id)) {
		    map.put(id.toString(), descrizione);
		}
	    }
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante la query findQUERY_FORME_TIPISOGGETTO", e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, rs);
	}
	return map;
    }

    private Map<String, String> findQUERY_INTERVENTO(Integer codiceIntervento, String idComuneAlias) {

	Map<String, String> map = new HashMap<String, String>();
	GetDbConnectionInfoResponse dbconninfo = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias);
	String idcomune = dbconninfo.getIdComune();
	String dbowner = dbconninfo.getDbOwner();
	Connection c = sigeproSecurityWebServiceClient.getConnection(idComuneAlias);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// trovo se ho già inserito i dettagli della comunicazione ri
	String id = null;
	String descrizione = "";
	try {
	    pstmt = c.prepareStatement(QUERY_INTERVENTO.replaceAll("DB_SCHEMA", dbowner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIntervento);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		id = rs.getString(1);
		descrizione = rs.getString(2);
		if (StringUtils.isNotBlank(id)) {
		    map.put(id.toString(), descrizione);
		}
	    }
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante la query findQUERY_INTERVENTO", e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(c, pstmt, rs);
	}
	return map;
    }

    private String QUERY_FORME_GIURIDICHE = "SELECT RI_FORMEGIURIDICHE.CODICE,RI_FORMEGIURIDICHE.DESCRIZIONE FROM DB_SCHEMA.FORMEGIURIDICHE LEFT JOIN DB_SCHEMA.RI_FORMEGIURIDICHE ON FORMEGIURIDICHE.CODICECCIAA = RI_FORMEGIURIDICHE.CODICE WHERE IDCOMUNE = ? AND FORMAGIURIDICA = ? ";
    private String QUERY_FORME_TIPISOGGETTO = "SELECT RI_CARICHE.CODICE, RI_CARICHE.DESCRIZIONE FROM DB_SCHEMA.TIPISOGGETTO LEFT JOIN RI_CARICHE ON DB_SCHEMA.TIPISOGGETTO.FK_RICA_CODICE = RI_CARICHE.CODICE WHERE IDCOMUNE = ? AND CODICETIPOSOGGETTO = ?";
    private String QUERY_INTERVENTO = "SELECT RI_TIPIINTERVENTO.CODICE, RI_TIPIINTERVENTO.DESCRIZIONE FROM DB_SCHEMA.ALBEROPROC LEFT JOIN DB_SCHEMA.RI_TIPIINTERVENTO ON ALBEROPROC.FK_RITI_CODICE = RI_TIPIINTERVENTO.CODICE WHERE IDCOMUNE = ? AND SC_ID = ?";
}
