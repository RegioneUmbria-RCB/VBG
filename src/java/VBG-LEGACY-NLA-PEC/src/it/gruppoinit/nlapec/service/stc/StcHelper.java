package it.gruppoinit.nlapec.service.stc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.impresainungiorno.schema.AdempimentoSUAP;
import it.gruppoinit.impresainungiorno.schema.AllegatoGenerico;
import it.gruppoinit.impresainungiorno.schema.ModelloAttivita;
import it.gruppoinit.impresainungiorno.schema.RiepilogoPraticaSUAP;
import it.gruppoinit.nlapec.schema.PraticaSTC;
import it.gruppoinit.nlapec.schema.comunicari.ComunicazioneUnicaRI;
import it.gruppoinit.nlapec.schema.muta.SciaAvvioModificaAttivitaType;
import it.gruppoinit.nlapec.schema.muta.SciaSubCesSosRipCamAttivitaType;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.DateUtil;
import it.gruppoinit.nlapec.util.FileUtil;
import it.gruppoinit.nlapec.util.OriginalMessage;
import it.gruppoinit.nlapec.util.OriginalMessageAttachment;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.nlapec.util.ParsingXML_MUTA;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.SchedaType;

public class StcHelper {

    private static final Logger log = LoggerFactory.getLogger(StcHelper.class);

    public static DettaglioPraticaType populatePraticaDPR160(OriginalMessage om) {

	DettaglioPraticaType dtp = null;
	// gli allegati sono in un file zip, estrarre allegato (modello-attività) <Codice-pratica>.MDA.xml
	// (contiene il tracciato DettaglioPratica.xsd)
	try {
	    List<OriginalMessageAttachment> attachments = om.getAttachments();
	    if (attachments != null) {
		for (OriginalMessageAttachment oma : attachments) {
		    // ricerco l'unico allegato <codice-pratica>.SUAP.zip
		    String attachmentName = oma.getFilename();
		    int idx;
		    if ((idx = attachmentName.toUpperCase().indexOf(".SUAP.ZIP")) != -1) {
			String codicePratica = attachmentName.substring(0, idx);
			String modelloAttivitaName = codicePratica + ".MDA.xml";
			// estrarre allegato (modello-attività) <Codice-pratica>.MDA.xml
			File modelloAttivitaFile = FileUtil.extractFileFromZip(oma.getFile(), modelloAttivitaName);
			// parse <Codice-pratica>.MDA.xml to PraticaSTC
			PraticaSTC praticaSTC = parsePraticaXML(modelloAttivitaFile);
			dtp = praticaSTC.getDettaglioPratica();
		    }
		    // gestione allegati
		    DocumentiType dt = new DocumentiType();
		    dt.setDocumento(oma.getFilename());
		    dt.setId("");
		    AllegatiType at = new AllegatiType();
		    at.setAllegato(oma.getFilename());
		    at.setId("");
		    AllegatoBinarioType abt = new AllegatoBinarioType();
		    abt.setFileName(oma.getFilename());
		    abt.setMimeType(oma.getContentType());
		    FileDataSource fds = new FileDataSource(oma.getFile());
		    abt.setBinaryData(new DataHandler(fds));
		    at.setFile(abt);
		    dt.setAllegati(at);
		    dtp.getDocumenti().add(dt);
		}
	    }
	} catch (Exception e) {
	    log.error("populatePratica(): {}", e.getMessage());
	}
	return dtp;
    }

    public static boolean checkSubjectDPR160(OriginalMessage om) {

	String subject = om.getSubject();
	boolean success = false;
	if (subject != null && subject.startsWith("SUAP:")) {
	    success = true;
	}
	return success;
    }

    public static PraticaSTC parsePraticaXML(File file) throws Exception {

	try {
	    log.debug("parsePraticaXML(): {}", file);
	    InputStream is = new FileInputStream(file);
	    JAXBContext jc = JAXBContext.newInstance(PraticaSTC.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    PraticaSTC pratica = (PraticaSTC) u.unmarshal(is);
	    return pratica;
	} catch (Exception e) {
	    log.error("parsePraticaXML(): {}", e.getMessage());
	    throw e;
	}
    }

    public static RiepilogoPraticaSUAP parsePraticaXML(InputStream is) throws Exception {

	try {
	    JAXBContext jc = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    RiepilogoPraticaSUAP pratica = (RiepilogoPraticaSUAP) u.unmarshal(is);
	    return pratica;
	} catch (Exception e) {
	    log.error("parsePraticaXML(): {}", e.getMessage());
	    throw e;
	} finally {
	    try {
		is.close();
	    } catch (Exception e) {
	    }
	}
    }

    public static ComunicazioneUnicaRI parseComunicazioneUnicaRiXML(InputStream is) throws Exception {

	try {
	    JAXBContext jc = JAXBContext.newInstance(ComunicazioneUnicaRI.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    ComunicazioneUnicaRI comunicazioneUnicaRi = (ComunicazioneUnicaRI) u.unmarshal(is);
	    return comunicazioneUnicaRi;
	} catch (Exception e) {
	    log.error("parsePraticaXML(): {}", e.getMessage());
	    throw e;
	} finally {
	    try {
		is.close();
	    } catch (Exception e) {
	    }
	}
    }

    public static DettaglioPraticaType populatePratica(RiepilogoPraticaSUAP riepilogoPraticaSUAP, PECMessage pecMessage, String tmpPath,
	    ArrayList<String> listaFileAttachment) {

	DettaglioPraticaType dettaglioPrat = new DettaglioPraticaType();
	// ALTRI DATI
	//dettaglioPrat.getAltriDati().add(null);
	// ALTRI SOGGETTI
	//dettaglioPrat.getAltriSoggetti().add(null);
	// AZIENDA RICHIEDENTE
	if (riepilogoPraticaSUAP.getIntestazione() != null && riepilogoPraticaSUAP.getIntestazione().getImpresa() != null) {
	    PersonaGiuridicaType aziendaRichiedente = new PersonaGiuridicaType();
	    aziendaRichiedente.setCodiceFiscale(riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceFiscale());
	    if (riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA() != null
		    && riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA().getDataIscrizione() != null
		    && riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA().getProvincia() != null) {
		RegistroREAType registroREA = new RegistroREAType();
		registroREA.setSiglaProvincia(riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA().getProvincia());
		registroREA.setNumero(riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA().getValue());
		registroREA.setData(riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceREA().getDataIscrizione());
		aziendaRichiedente.setIscrizioneREA(registroREA);
	    } else {
		RegistroREAType registroREA = getRegistroREAfromXML(tmpPath, listaFileAttachment);
		if (registroREA != null) {
		    aziendaRichiedente.setIscrizioneREA(registroREA);
		}
	    }
	    PersonaFisicaType legaleRappresentante = new PersonaFisicaType();
	    legaleRappresentante.setCodiceFiscale(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getCodiceFiscale());
	    legaleRappresentante.setCognome(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getCognome());
	    legaleRappresentante.setNome(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getNome());
	    aziendaRichiedente.setLegaleRappresentante(legaleRappresentante);
	    aziendaRichiedente.setNaturaGiuridica(riepilogoPraticaSUAP.getIntestazione().getImpresa().getFormaGiuridica().getValue());
	    if (riepilogoPraticaSUAP.getIntestazione().getImpresa().getPartitaIva() == null
		    || "".equalsIgnoreCase(riepilogoPraticaSUAP.getIntestazione().getImpresa().getPartitaIva())) {
		aziendaRichiedente.setPartitaIva(riepilogoPraticaSUAP.getIntestazione().getImpresa().getCodiceFiscale());
	    } else {
		aziendaRichiedente.setPartitaIva(riepilogoPraticaSUAP.getIntestazione().getImpresa().getPartitaIva());
	    }
	    aziendaRichiedente.setRagioneSociale(riepilogoPraticaSUAP.getIntestazione().getImpresa().getRagioneSociale());
	    LocalizzazioneType localizzazione = new LocalizzazioneType();
	    localizzazione.setCap(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getCap());
	    localizzazione.setCivico(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getNumeroCivico());
	    ComuneType comune = new ComuneType();
	    //comune.setCodiceCatastale(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getComune().getCodiceCatastale());//
	    comune.setComune(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getComune().getValue());
	    localizzazione.setComune(comune);
	    localizzazione.setIndirizzo(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getToponimo() + " " +
					riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getDenominazioneStradale());
	    localizzazione.setProvincia(riepilogoPraticaSUAP.getIntestazione().getImpresa().getIndirizzo().getProvincia().getSigla());
	    aziendaRichiedente.setSedeLegale(localizzazione);
	    dettaglioPrat.setAziendaRichiedente(aziendaRichiedente);
	}
	// CODICE COMUNE
	ComuneType comune = new ComuneType();
	String ca = riepilogoPraticaSUAP.getIntestazione().getUfficioDestinatario().getCodiceAmministrazione();
	if (ca.length() < 4) {
	    comune.setCodiceCatastale(ca);
	} else {
	    comune.setCodiceCatastale(ca.substring(ca.length() - 4, ca.length()));
	}
	dettaglioPrat.setCodiceComune(comune);
	// DATA PRATICA
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(pecMessage.getDate().getTime());
	XMLGregorianCalendar xgc = null;
	try {
	    xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	} catch (DatatypeConfigurationException e) {
	    e.printStackTrace();
	}
	// new XMLGregorianCalendarImpl(gc);
	dettaglioPrat.setDataPratica(xgc);
	String ora = DateUtil.getOreMinuti(pecMessage.getDate());
	if (StringUtils.isNotBlank(ora)) {
	    dettaglioPrat.setOraDataPratica(ora);
	}
	// DATA PROTOCOLLO GENERALE
	dettaglioPrat.setDataProtocolloGenerale(null);
	// DOCUMENTI
	if (riepilogoPraticaSUAP.getStruttura() != null && riepilogoPraticaSUAP.getStruttura().getModulo() != null
		&& !riepilogoPraticaSUAP.getStruttura().getModulo().isEmpty()) {
	    ArrayList<String> listaFileInseriti = new ArrayList<String>();
	    Iterator<AdempimentoSUAP> it = riepilogoPraticaSUAP.getStruttura().getModulo().iterator();
	    while (it.hasNext()) {
		AdempimentoSUAP adempimentoSUAP = it.next();
		ModelloAttivita modelloAttivita = adempimentoSUAP.getDistintaModelloAttivita();
		if (modelloAttivita != null) {
		    DocumentiType documenti = new DocumentiType();
		    String descrizione = "";
		    if (modelloAttivita.getDescrizione() != null && !"".equalsIgnoreCase(modelloAttivita.getDescrizione())) {
			descrizione = modelloAttivita.getDescrizione();
		    } else {
			descrizione = "Modello attivita'";
		    }
		    documenti.setDocumento(descrizione);
		    documenti.setId(modelloAttivita.getNomeFile());
		    documenti.setTipoDocumento("MDA-PDF");
		    AllegatiType allegati = new AllegatiType();
		    allegati.setAllegato(descrizione);
		    AllegatoBinarioType binaryFile = new AllegatoBinarioType();
		    // File f = new File(tmpPath + modelloAttivita.getNomeFile());
		    DataHandler dataHandler = new DataHandler(
			    AllegatiUtil.getBinaryData(tmpPath, modelloAttivita.getNomeFile(), modelloAttivita.getDimensione()),
			    "application/octet-stream");
		    binaryFile.setBinaryData(dataHandler);
		    binaryFile.setFileName(modelloAttivita.getNomeFile());
		    listaFileInseriti.add(modelloAttivita.getNomeFile());
		    binaryFile.setMimeType(modelloAttivita.getMime());
		    allegati.setFile(binaryFile);
		    allegati.setId(modelloAttivita.getNomeFile());
		    documenti.setAllegati(allegati);
		    dettaglioPrat.getDocumenti().add(documenti);
		}
		if (adempimentoSUAP.getDocumentoAllegato() != null && !adempimentoSUAP.getDocumentoAllegato().isEmpty()) {
		    for (Iterator<AllegatoGenerico> iterator = adempimentoSUAP.getDocumentoAllegato().iterator(); iterator.hasNext();) {
			AllegatoGenerico allegatoGenerico = (AllegatoGenerico) iterator.next();
			DocumentiType documenti = new DocumentiType();
			String descrizione = "";
			if (allegatoGenerico.getDescrizione() != null && !"".equalsIgnoreCase(allegatoGenerico.getDescrizione())) {
			    descrizione = allegatoGenerico.getDescrizione();
			} else {
			    descrizione = allegatoGenerico.getNomeFile();
			}
			documenti.setDocumento(descrizione);
			documenti.setId(allegatoGenerico.getNomeFile());
			documenti.setTipoDocumento("Altro");
			AllegatiType allegati = new AllegatiType();
			allegati.setAllegato(descrizione);
			AllegatoBinarioType binaryFile = new AllegatoBinarioType();
			DataHandler dataHandler = new DataHandler(
				AllegatiUtil.getBinaryData(tmpPath, allegatoGenerico.getNomeFile(), allegatoGenerico.getDimensione()),
				"application/octet-stream");
			binaryFile.setBinaryData(dataHandler);
			binaryFile.setFileName(allegatoGenerico.getNomeFile());
			listaFileInseriti.add(allegatoGenerico.getNomeFile());
			binaryFile.setMimeType(allegatoGenerico.getMime());
			allegati.setFile(binaryFile);
			allegati.setId(allegatoGenerico.getNomeFile());
			documenti.setAllegati(allegati);
			dettaglioPrat.getDocumenti().add(documenti);
		    }
		}
	    }
	    if (listaFileInseriti.size() < listaFileAttachment.size()) {
		for (Iterator<String> iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		    String nomeFile = iterator.next();
		    Iterator<String> it_ = listaFileInseriti.iterator();
		    boolean trovato = false;
		    while (!trovato && it_.hasNext()) {
			if (nomeFile.equalsIgnoreCase(it_.next())) {
			    trovato = true;
			}
		    }
		    if (!trovato) {
			DocumentiType documenti = new DocumentiType();
			documenti.setDocumento(nomeFile);
			documenti.setId(nomeFile);
			documenti.setTipoDocumento("Altro");
			AllegatiType allegati = new AllegatiType();
			allegati.setAllegato(nomeFile);
			AllegatoBinarioType binaryFile = new AllegatoBinarioType();
			File f = new File(tmpPath + nomeFile);
			DataHandler dataHandler = new DataHandler(
				AllegatiUtil.getBinaryData(tmpPath, nomeFile, new BigInteger(String.valueOf(f.length()))),
				"application/octet-stream");
			binaryFile.setBinaryData(dataHandler);
			binaryFile.setFileName(nomeFile);
			MimetypesFileTypeMap m = new MimetypesFileTypeMap();
			String mimeType = m.getContentType(tmpPath + nomeFile);
			binaryFile.setMimeType(mimeType);
			allegati.setFile(binaryFile);
			allegati.setId(nomeFile);
			documenti.setAllegati(allegati);
			dettaglioPrat.getDocumenti().add(documenti);
		    }
		}
	    }
	}
	// ID PRATICA
	dettaglioPrat.setIdPratica(AllegatiUtil.getCodicePratica(listaFileAttachment));
	// INTERMEDIARIO
	AnagrafeType intermediario = null;
	if (riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("NOTAIO")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("DELEGATO")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("CONSULENTE")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("PROFESSIONISTA INCARICATO")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica()
			.equalsIgnoreCase("ALTRO PREVISTO DALLA VIGENTE NORMATIVA")) {
	    intermediario = new AnagrafeType();
	    PersonaFisicaType personaFisicaIntermediario = new PersonaFisicaType();
	    personaFisicaIntermediario.setCodiceFiscale(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getCodiceFiscale());
	    personaFisicaIntermediario.setCognome(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getCognome());
	    personaFisicaIntermediario.setNome(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getNome());
	    personaFisicaIntermediario.setPec(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getPec());
	    personaFisicaIntermediario.setTelefono(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getTelefono());
	    intermediario.setPersonaFisica(personaFisicaIntermediario);
	}
	dettaglioPrat.setIntermediario(intermediario);
	// INTERVENTO
	dettaglioPrat.setIntervento(null);
	// LOCALIZZAZIONE
	//    	if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo()!=null){
	//    		LocalizzazioneNelComuneType localizzazione = new LocalizzazioneNelComuneType();
	//    		localizzazione.setCivico(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getNumeroCivico());
	//    		localizzazione.setCodiceViario(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getCap());
	//    		localizzazione.setDenominazione(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getDenominazioneStradale());
	//    		if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getFrazione()!=null && !"".equalsIgnoreCase(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getFrazione())) {
	//    			FrazioneType frazione = new FrazioneType();
	//    			frazione.setDescrizione(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getFrazione());
	//    			localizzazione.setFrazione(frazione);
	//    		}
	//    		dettaglioPrat.getLocalizzazione().add(localizzazione);
	//    	}
	// NUMERO PRATICA
	dettaglioPrat.setNumeroPratica(dettaglioPrat.getIdPratica());
	// NUMERO PROTOCOLLO
	dettaglioPrat.setNumeroProtocolloGenerale(null);
	// OGGETTO
	dettaglioPrat.setOggetto(riepilogoPraticaSUAP.getIntestazione().getOggettoComunicazione().getValue());
	// ONERI    	
	//dettaglioPrat.getOneri().add(null);
	// PROCEDIMENTI
	//dettaglioPrat.getProcedimenti().add(null);
	// PROCEDURE
	//dettaglioPrat.getProcure().add(null);
	// RICHIEDENTE
	RichiedenteType richiedente = new RichiedenteType();
	PersonaFisicaType personaFisica = new PersonaFisicaType();
	if (riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("TITOLARE")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("LEGALE RAPPRESENTANTE")
		|| riepilogoPraticaSUAP.getIntestazione().getDichiarante().getQualifica().equalsIgnoreCase("AMMINISTRATORE")) {
	    personaFisica.setCodiceFiscale(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getCodiceFiscale());
	    personaFisica.setCognome(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getCognome());
	    personaFisica.setPec(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getPec());
	    personaFisica.setNome(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getNome());
	    personaFisica.setTelefono(riepilogoPraticaSUAP.getIntestazione().getDichiarante().getTelefono());
	} else {
	    personaFisica.setCodiceFiscale(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getCodiceFiscale());
	    personaFisica.setCognome(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getCognome());
	    personaFisica.setNome(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getNome());
	}
	richiedente.setAnagrafica(personaFisica);
	dettaglioPrat.setRichiedente(richiedente);
	// SCHEDE
	SchedaType[] schede = getSchedeXML(dettaglioPrat.getIdPratica(), tmpPath, listaFileAttachment);
	if (schede != null && schede.length > 0) {
	    for (SchedaType scheda : schede) {
		dettaglioPrat.getSchede().add(scheda);
	    }
	}
	return dettaglioPrat;
    }

    private static SchedaType[] getSchedeXML(String idPratica, String tmpPath, ArrayList<String> listaFileAttachment) {

	SchedaType[] schede = null;
	ArrayList<SchedaType> listaSchede = new ArrayList<SchedaType>();
	try {
	    for (Iterator<String> iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		String nomeFile = iterator.next();
		if (nomeFile != null && nomeFile.toUpperCase().startsWith(idPratica.toUpperCase()) && nomeFile.toUpperCase().endsWith(".MDA.XML")) {
		    File f = new File(tmpPath + nomeFile);
		    SchedaType[] schedeSTC = parseSchedeMuta(f);
		    if (schedeSTC != null && schedeSTC.length > 0) {
			for (SchedaType scheda : schedeSTC) {
			    listaSchede.add(scheda);
			}
		    }
		}
	    }
	} catch (Exception e) {
	}
	if (listaSchede.size() > 0) {
	    schede = new SchedaType[listaSchede.size()];
	    int index = 0;
	    for (Iterator<SchedaType> iterator = listaSchede.iterator(); iterator.hasNext();) {
		schede[index] = iterator.next();
		index++;
	    }
	}
	return schede;
    }

    private static SchedaType[] parseSchedeMuta(File f) {

	boolean error = false;
	SchedaType[] schede = null;
	InputStream is = null;
	try {
	    is = new FileInputStream(f);
	    JAXBContext jc = JAXBContext.newInstance(SciaSubCesSosRipCamAttivitaType.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    SciaSubCesSosRipCamAttivitaType schedaMUTA = (SciaSubCesSosRipCamAttivitaType) u.unmarshal(is);
	    schede = schedeMUTA2schdeSTC(f, schedaMUTA);
	    is.close();
	} catch (Exception e) {
	    error = true;
	    try {
		if (is != null) {
		    is.close();
		}
	    } catch (Exception ex1) {
	    }
	}
	if (error) {
	    error = false;
	    InputStream is2 = null;
	    try {
		is2 = new FileInputStream(f);
		JAXBContext jc = JAXBContext.newInstance(SciaAvvioModificaAttivitaType.class);
		Unmarshaller u = jc.createUnmarshaller();
		SciaAvvioModificaAttivitaType schedaMUTA = (SciaAvvioModificaAttivitaType) u.unmarshal(is2);
		schede = schedeMUTA2schdeSTC(f, schedaMUTA);
		is2.close();
	    } catch (Exception e) {
		log.error("parseSchedaMuta(): ", e);
		error = true;
		try {
		    if (is2 != null) {
			is2.close();
		    }
		} catch (Exception ex2) {
		}
	    }
	}
	if (error) {
	    log.error("parseSchedaMuta(): Parsing XML fallito");
	    return null;
	} else {
	    return schede;
	}
    }

    private static SchedaType[] schedeMUTA2schdeSTC(File f, SciaAvvioModificaAttivitaType schedaMUTA) {

	SchedaType[] schede = null;
	try {
	    String nomeRootElement = "sciaAvvioModificaAttivita";
	    ParsingXML_MUTA parsingMUTA = new ParsingXML_MUTA();
	    schede = parsingMUTA.parsaXML_SciaAvvioModifica(f, schedaMUTA, nomeRootElement);
	} catch (Exception e) {
	}
	return schede;
    }

    private static SchedaType[] schedeMUTA2schdeSTC(File f, SciaSubCesSosRipCamAttivitaType schedaMUTA) {

	SchedaType[] schede = null;
	try {
	    String nomeRootElement = "sciaSubCesSosRipCamAttivita";
	    ParsingXML_MUTA parsingMUTA = new ParsingXML_MUTA();
	    schede = parsingMUTA.parsaXML_SciaSubCesSosRipCam(f, schedaMUTA, nomeRootElement);
	} catch (Exception e) {
	}
	return schede;
    }

    private static RegistroREAType getRegistroREAfromXML(String tmpPath, ArrayList<String> listaFileAttachment) {

	RegistroREAType registroREA = null;
	boolean trovato = false;
	try {
	    Iterator<String> it = listaFileAttachment.iterator();
	    while (!trovato && it.hasNext()) {
		String nomeFile = it.next();
		if (nomeFile != null && nomeFile.toUpperCase().endsWith(".CUI.XML")) {
		    File f = new File(tmpPath + nomeFile);
		    InputStream is = new FileInputStream(f);
		    ComunicazioneUnicaRI comUnicaRI = parseComunicazioneUnicaRiXML(is);
		    if (comUnicaRI != null) {
			registroREA = new RegistroREAType();
			registroREA.setData(comUnicaRI.getProtocollazione().getDataProtocolloRi());
			registroREA.setNumero(comUnicaRI.getProtocollazione().getNumeroRea().toString());
			registroREA.setSiglaProvincia(comUnicaRI.getUfficioRi());
		    }
		    is.close();
		}
	    }
	} catch (Exception e) {
	}
	return registroREA;
    }

    public DettaglioPraticaType populatePratica(PECMessage pecMessage, String software, String tmpPath, ArrayList<String> listaFileAttachment,
	    Map<String, String> altriParametriVerticalizzazione, Properties connectionProps, SigeproService sigeproService) {

	DettaglioPraticaType dettaglioPrat = new DettaglioPraticaType();
	// ALTRI DATI
	//dettaglioPrat.getAltriDati().add(null);
	// ALTRI SOGGETTI
	//dettaglioPrat.getAltriSoggetti().add(null);
	// CODICE COMUNE
	dettaglioPrat.setCodiceComune(getCodiceComune(pecMessage, software, connectionProps, sigeproService));
	// DATA PRATICA
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(pecMessage.getDate().getTime());
	XMLGregorianCalendar xgc = null;
	try {
	    xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	} catch (DatatypeConfigurationException e) {
	    e.printStackTrace();
	}
	dettaglioPrat.setDataPratica(xgc);
	String ora = DateUtil.getOreMinuti(pecMessage.getDate());
	if (StringUtils.isNotBlank(ora)) {
	    dettaglioPrat.setOraDataPratica(ora);
	}
	// DATA PROTOCOLLO GENERALE
	dettaglioPrat.setDataProtocolloGenerale(null);
	// DOCUMENTI
	if (listaFileAttachment != null && listaFileAttachment.size() > 0) {
	    for (Iterator iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		String nomeFile = (String) iterator.next();
		DocumentiType documenti = new DocumentiType();
		documenti.setDocumento(nomeFile);
		documenti.setId(nomeFile);
		AllegatiType allegati = new AllegatiType();
		allegati.setAllegato(nomeFile);
		AllegatoBinarioType binaryFile = new AllegatoBinarioType();
		binaryFile.setBinaryData(AllegatiUtil.getDataHandler(tmpPath, nomeFile, null));
		binaryFile.setFileName(nomeFile);
		MimetypesFileTypeMap m = new MimetypesFileTypeMap();
		String mimeType = m.getContentType(tmpPath + nomeFile);
		binaryFile.setMimeType(mimeType);
		allegati.setFile(binaryFile);
		allegati.setId(nomeFile);
		documenti.setAllegati(allegati);
		dettaglioPrat.getDocumenti().add(documenti);
	    }
	}
	// ID PRATICA
	dettaglioPrat.setIdPratica(pecMessage.getId().length() > 60 ? pecMessage.getId().substring(0, 59) : pecMessage.getId());
	// INTERMEDIARIO
	// INTERVENTO
	dettaglioPrat.setIntervento(null);
	// LOCALIZZAZIONE
	// NUMERO PRATICA
	dettaglioPrat.setNumeroPratica(dettaglioPrat.getIdPratica());
	// NUMERO PROTOCOLLO
	dettaglioPrat.setNumeroProtocolloGenerale(null);
	// OGGETTO
	dettaglioPrat.setOggetto(pecMessage.getBody());
	// ONERI    	
	//dettaglioPrat.getOneri().add(null);
	// PROCEDIMENTI
	//dettaglioPrat.getProcedimenti().add(null);
	// PROCEDURE
	//dettaglioPrat.getProcure().add(null);
	// RICHIEDENTE
	String rich = altriParametriVerticalizzazione.get("RICHIEDENTE_DEFAULT");
	if (StringUtils.isNotBlank(rich)) {
	    RichiedenteType richiedente = new RichiedenteType();
	    PersonaFisicaType pf = sigeproService.getAnagrafePF(connectionProps, rich);
	    if (pf == null) {
		log.error("Richiedente di default non presente");
	    }
	    richiedente.setAnagrafica(pf);
	    dettaglioPrat.setRichiedente(richiedente);
	    dettaglioPrat.setAziendaRichiedente(getAziendaRichiedente(pecMessage));
	} else {
	    PersonaGiuridicaType pg = new PersonaGiuridicaType();
	    String[] token = pecMessage.getSubject().split("-");
	    if (token != null && token.length > 2) {
		pg.setPartitaIva(token[1].trim());
		String ragioneSociale = "";
		for (int i = 2; i < token.length; i++) {
		    String string = token[i];
		    ragioneSociale += string + "-";
		}
		ragioneSociale = ragioneSociale.substring(0, ragioneSociale.length() - 1);
		pg.setRagioneSociale(ragioneSociale);
	    } else {
		log.error("Non è stato possibile recuperare l'azienda richidente dall'oggetto della mail");
	    }
	    dettaglioPrat.setAziendaRichiedente(pg);
	}
	// SCHEDE
	return dettaglioPrat;
    }

    public DettaglioPraticaType populatePraticaNonFormttata(PECMessage pecMessage, String tmpPath, ArrayList<String> listaFileAttachment,
	    Map<String, String> altriParametriVerticalizzazione, Properties connectionProps, SigeproService sigeproService) {

	DettaglioPraticaType dettaglioPrat = new DettaglioPraticaType();
	// ALTRI DATI
	//dettaglioPrat.getAltriDati().add(null);
	// ALTRI SOGGETTI
	//dettaglioPrat.getAltriSoggetti().add(null);
	// CODICE COMUNE 
	//	ComuneType comune = new ComuneType();
	//	comune.setCodiceIstat("054024");
	//	comune.setComune("GUBBIO");
	//	dettaglioPrat.setCodiceComune(comune);
	// DATA PRATICA
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTimeInMillis(pecMessage.getDate().getTime());
	XMLGregorianCalendar xgc = null;
	try {
	    xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	} catch (DatatypeConfigurationException e) {
	    e.printStackTrace();
	}
	dettaglioPrat.setDataPratica(xgc);
	String ora = DateUtil.getOreMinuti(pecMessage.getDate());
	if (StringUtils.isNotBlank(ora)) {
	    dettaglioPrat.setOraDataPratica(ora);
	}
	// DATA PROTOCOLLO GENERALE
	dettaglioPrat.setDataProtocolloGenerale(null);
	// DOCUMENTI
	if (listaFileAttachment != null && listaFileAttachment.size() > 0) {
	    for (Iterator iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		String nomeFile = (String) iterator.next();
		DocumentiType documenti = new DocumentiType();
		documenti.setDocumento(nomeFile);
		documenti.setId(nomeFile);
		AllegatiType allegati = new AllegatiType();
		allegati.setAllegato(nomeFile);
		AllegatoBinarioType binaryFile = new AllegatoBinarioType();
		binaryFile.setBinaryData(AllegatiUtil.getDataHandler(tmpPath, nomeFile, null));
		binaryFile.setFileName(nomeFile);
		MimetypesFileTypeMap m = new MimetypesFileTypeMap();
		String mimeType = m.getContentType(tmpPath + nomeFile);
		binaryFile.setMimeType(mimeType);
		allegati.setFile(binaryFile);
		allegati.setId(nomeFile);
		documenti.setAllegati(allegati);
		dettaglioPrat.getDocumenti().add(documenti);
	    }
	}
	// INSERISCO COME ALLEGATO IL CORPO DELLA MAIL
	try {
	    String nomeFileCorpoMail = "CorpoMail.html";
	    File f = new File(tmpPath + nomeFileCorpoMail);
	    FileOutputStream fos = new FileOutputStream(f);
	    if (pecMessage.getBody() != null) {
		fos.write(pecMessage.getBody().getBytes());
	    }
	    fos.close();
	    DocumentiType docCorpoMail = new DocumentiType();
	    docCorpoMail.setDocumento(nomeFileCorpoMail);
	    docCorpoMail.setId(nomeFileCorpoMail);
	    AllegatiType allegatoCorpoMail = new AllegatiType();
	    allegatoCorpoMail.setAllegato("CorpoMail.html");
	    AllegatoBinarioType binaryFileCorpoMail = new AllegatoBinarioType();
	    binaryFileCorpoMail.setBinaryData(AllegatiUtil.getDataHandler(tmpPath, nomeFileCorpoMail, null));
	    binaryFileCorpoMail.setFileName(nomeFileCorpoMail);
	    MimetypesFileTypeMap m = new MimetypesFileTypeMap();
	    String mimeType = m.getContentType(tmpPath + nomeFileCorpoMail);
	    binaryFileCorpoMail.setMimeType(mimeType);
	    allegatoCorpoMail.setFile(binaryFileCorpoMail);
	    allegatoCorpoMail.setId(nomeFileCorpoMail);
	    docCorpoMail.setAllegati(allegatoCorpoMail);
	    dettaglioPrat.getDocumenti().add(docCorpoMail);
	} catch (Exception e) {
	    log.error("Errore nell'inserimento del corpo della mail come allegato : " + e.getMessage());
	}
	// ID PRATICA
	dettaglioPrat.setIdPratica(pecMessage.getId().length() > 60 ? pecMessage.getId().substring(0, 59) : pecMessage.getId());
	// INTERMEDIARIO
	// INTERVENTO
	dettaglioPrat.setIntervento(null);
	// LOCALIZZAZIONE
	// NUMERO PRATICA
	dettaglioPrat.setNumeroPratica(dettaglioPrat.getIdPratica());
	// NUMERO PROTOCOLLO
	dettaglioPrat.setNumeroProtocolloGenerale(null);
	// DOMICILIO ELETTRONICO
	try {
	    InternetAddress ia = (InternetAddress) pecMessage.getFrom()[0];
	    dettaglioPrat.setDomicilioElettronico(ia.getAddress());
	} catch (Exception exIa) {
	    log.error("Errore nel recupero del domicilio elettronico : " + exIa.getMessage());
	}
	// OGGETTO
	//dettaglioPrat.setOggetto(pecMessage.getBody());
	dettaglioPrat.setOggetto(pecMessage.getSubject());
	// ONERI    	
	//dettaglioPrat.getOneri().add(null);
	// PROCEDIMENTI
	//dettaglioPrat.getProcedimenti().add(null);
	// PROCEDURE
	//dettaglioPrat.getProcure().add(null);
	// RICHIEDENTE
	String rich = altriParametriVerticalizzazione.get("PEC_NON_FORMATTATA_ANAG_RIC");
	if (StringUtils.isNotBlank(rich)) {
	    RichiedenteType richiedente = new RichiedenteType();
	    PersonaFisicaType pf = sigeproService.getAnagrafePF(connectionProps, rich);
	    if (pf == null) {
		log.error("Richiedente di default non presente");
	    }
	    richiedente.setAnagrafica(pf);
	    dettaglioPrat.setRichiedente(richiedente);
	}
	// SCHEDE
	return dettaglioPrat;
    }

    private ComuneType getCodiceComune(PECMessage pecMessage, String software, Properties connectionProps, SigeproService sigeproService) {

	ComuneType comune = null;
	try {
	    String subject = pecMessage.getSubject();
	    String[] tokenPrincipali = subject.split("-");
	    if (tokenPrincipali != null && tokenPrincipali.length >= 3) {
		String head = tokenPrincipali[0];
		String[] tokenHead = head.split(":");
		if (tokenHead != null && tokenHead.length == 3) {
		    String codiceAccreditamento = tokenHead[2];
		    if (sigeproService.isComuneAssociato(connectionProps, connectionProps.getProperty("db_idcomune"))) {
			String codiceComuneAssociato = sigeproService.getCodiceComuneAssociato(connectionProps, software, codiceAccreditamento);
			comune = new ComuneType();
			comune.setCodiceCatastale(codiceComuneAssociato);
		    } else {
			comune = new ComuneType();
			comune.setCodiceCatastale(connectionProps.getProperty("db_idcomune"));
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("Individuazione del codice comune fallito");
	}
	return comune;
    }

    private PersonaGiuridicaType getAziendaRichiedente(PECMessage pecMessage) {

	String subject = pecMessage.getSubject();
	PersonaGiuridicaType pg = null;
	String[] tokenPrincipali = subject.split("-");
	if (tokenPrincipali != null && tokenPrincipali.length >= 3) {
	    String piva = tokenPrincipali[1];
	    String denominazione = "";
	    for (int i = 2; i < tokenPrincipali.length; i++) {
		denominazione += tokenPrincipali[i];
	    }
	    pg = new PersonaGiuridicaType();
	    if (piva != null && piva.trim().length() == 11) {
		pg.setPartitaIva(piva.trim());
	    } else {
		pg.setCodiceFiscale(piva.trim());
	    }
	    pg.setRagioneSociale(denominazione);
	}
	return pg;
    }
}
