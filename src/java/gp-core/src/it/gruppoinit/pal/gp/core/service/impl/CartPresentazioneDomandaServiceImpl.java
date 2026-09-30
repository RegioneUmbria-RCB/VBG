package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.modulistica.DestinatariETType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.CodificaEntiRfc53DAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CodificaEntiRfc53;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.DatiModulo;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.MappingIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.MessaggioErrore;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingException;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig;
import it.gruppoinit.pal.gp.core.domain.helper.CartFileCopyInfo;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.CartMappingService;
import it.gruppoinit.pal.gp.core.service.CartModulisticaService;
import it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandeFrontOfficeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.schema.mda.DatoType;
import it.gruppoinit.sigepro.cart.schema.mda.IdSemanticoType;
import it.gruppoinit.sigepro.cart.schema.mda.ModelloDatiDomandaType;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Allegato;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Allegato.Destinatari;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Impresa;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.IndiceZip;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.OggettoComunicazionePresentazione;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ProcuraSpeciale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Soggetto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ZipFileType;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183PresentazioneDomandaServiceClient;
import it.gruppoinit.sigepro.cart.service.fru.impl.CartRfc183PresentazioneDomandaServiceClientImpl;
import it.gruppoinit.sigepro.cart.service.helper.SoapAttachment;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CartPresentazioneDomandaServiceImpl implements CartPresentazioneDomandaService {

    private static final Logger log = LoggerFactory.getLogger(CartPresentazioneDomandaServiceImpl.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CartRfc183PresentazioneDomandaServiceClient presentazioneDomandaServiceClient;
    @Autowired
    private DomandeFrontOfficeService domandeFrontOfficeService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private CartMappingService cartMappingService;
    @Autowired
    private CartModulisticaService cartModulisticaService;
    @Autowired
    private IstanzeService istanzeService;
    private CodificaEntiRfc53DAO codificaEntiRfc53DAO;

    @Autowired
    public void setCodificaEntiRfc53DAO(CodificaEntiRfc53DAO codificaEntiRfc53DAO) {

	this.codificaEntiRfc53DAO = codificaEntiRfc53DAO;
    }

    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    /**
     * @return the presentazioneDomandaServiceClient
     */
    public CartRfc183PresentazioneDomandaServiceClient getPresentazioneDomandaServiceClient() {

	return presentazioneDomandaServiceClient;
    }

    /**
     * @param presentazioneDomandaServiceClient
     *            the presentazioneDomandaServiceClient to set
     */
    public void setPresentazioneDomandaServiceClient(CartRfc183PresentazioneDomandaServiceClient presentazioneDomandaServiceClient) {

	this.presentazioneDomandaServiceClient = presentazioneDomandaServiceClient;
    }

    @Override
    public void scriviFileMDADaDatiDomandaPresentata(DatiDomandaCart datiDomanda, File outputFile) throws Exception {

	scriviFileMDADaDatiDomandaPresentata(datiDomanda, outputFile, null);
    }

    @Override
    public void scriviFileMDADaDatiDomandaPresentata(DatiDomandaCart datiDomanda, File outputFile, String refModulo) throws Exception {

	// §§§BEGIN§§§
	Set<DatiModulo> datiModuli = datiDomanda.getDatiModuli();
	Iterator<DatiModulo> moduliIterator = datiModuli.iterator();
	ModelloDatiDomandaType mdaData = new ModelloDatiDomandaType();
	List<IdSemanticoType> valoriMda = mdaData.getIdSemantico();
	while (moduliIterator.hasNext()) {
	    DatiModulo datiModulo = moduliIterator.next();
	    if (StringUtils.isBlank(refModulo) || refModulo.equalsIgnoreCase(datiModulo.getIdModulo())) {
		Set<String> idSemantici = datiModulo.getElencoIdSemantici();
		for (String idSemantico : idSemantici) {
		    ValoreIdSemantico valore = datiDomanda.getValoreIdSemantico(datiModulo.getIdModulo(), idSemantico);
		    // IdSemanticoType idSemanticoMda = new IdSemanticoType();
		    if (!valore.hasNoValues()) {
			if (valore.isIndicizzato()) {
			    // gli id semantici dei campi indicizzati devono
			    // essere scritti come <idSemantico>:<indice>
			    List<IdSemanticoType> valoriIndicizzatiMda = buildIndexedIds(valore, idSemantico, datiDomanda);
			    valoriMda.addAll(valoriIndicizzatiMda);
			} else {
			    valoriMda.add(buildId(valore, idSemantico, datiDomanda));
			}
		    } else {
			// gli id semantici vuoti vengono comunque trasmessi
			// associati al valore ""
			valoriMda.add(buildId(valore, idSemantico, datiDomanda));
		    }
		}
	    }
	}
	// aggiungo l'idsemantico CUSTOM CHE INDIVIDUA LA VOCE DELL'ALBERO
	// SCELTA DALL'UTENTE
	if (datiDomanda.getDatiContestoDomanda() != null) {
	    if (datiDomanda.getDatiContestoDomanda().getIdAlberoProc() != null) {
		ValoreIdSemantico valoreAlbproc = new ValoreIdSemantico(String.valueOf(datiDomanda.getDatiContestoDomanda().getIdAlberoProc()));
		valoriMda.add(buildId(valoreAlbproc, FACCTConstants.ID_SEMANTICO_INIT_CODICE_INTERVENTO, datiDomanda));
	    }
	}
	// solo nell'MDA generale che comprende gli id semantici di tutti i
	// moduli
	// aggiungo l'id semantico CUSTOM che riporta l'elenco dei warning di
	// validazione della firma digitale
	if (refModulo == null) {
	    HashSet<AllegatoDaFirmare> dsWarnings = datiDomanda.getWarningsFirmeDigitali();
	    String[] warnings = new String[dsWarnings.size()];
	    int index = 0;
	    for (Iterator<AllegatoDaFirmare> iterator = dsWarnings.iterator(); iterator.hasNext();) {
		AllegatoDaFirmare allegatoDaFirmare = iterator.next();
		warnings[index] = allegatoDaFirmare.getWarningMessage();
		index++;
	    }
	    ValoreIdSemantico vis = new ValoreIdSemantico(warnings);
	    valoriMda.add(buildId(vis, FACCTConstants.ID_SEMANTICO_INIT_WARNINGS_FIRMADIGITALE, datiDomanda));
	}
	if (!outputFile.isFile()) {
	    outputFile.createNewFile();
	}
	String xml = XmlUtils.marshallObject(mdaData);
	Schema schema = XmlUtils.getSchemaForMessage("cart/schema/MDASchema.xsd");
	XmlUtils.validaXml(xml, schema);
	AttachmentsUtils.writeBytesToStream(xml.getBytes("UTF-8"), new FileOutputStream(outputFile));
	// §§§END§§§
    }

    //    @Override
    //    public DatiDomandaCart leggiDatiDomandaPresentataDaFileMDA(File inputFile) {
    //
    //	// TODO Auto-generated method stub
    //	return null;
    //    }
    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.cartfacct.service.PresentazioneDomandaService#
     * creaOggettoPresentazioneDomanda
     * (it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart, java.lang.String,
     * java.util.Properties)
     */
    @Override
    public PresentazioneDomanda creaOggettoPresentazioneDomanda(DatiDomandaCart dati, Properties mappaturaIdSemantici, String tipoAttivita,
	    String std2_std0, Date dataDomanda) throws Exception {

	PresentazioneDomanda retObj = new PresentazioneDomanda();
	// §§§BEGIN§§§
	// valorizzo le properietà di tipo oggetto con degli oggetti vuoti
	retObj.setImpresa(new Impresa());
	retObj.setOggettoComunicazione(new OggettoComunicazionePresentazione());
	retObj.setPresentatore(new Soggetto());
	retObj.setProcuraSpeciale(initProcuraSpeciale());
	// valorizzo i campi della domanda che non devono essere letti dalla
	// modulistica compilata
	retObj.setUfficioDestinatario("SUAP");
	processaMappature(dati, mappaturaIdSemantici, std2_std0, retObj);
	// il codice della attività nella BDR è già noto al sistema prima del
	// popolamento della modulistica ma può anche essere mappato sull'id
	// semantico CODICE_ATTIVITA_REGIONALE
	if (retObj.getOggettoComunicazione() != null) {
	    // se l'idsemantico non è stato popolato allora lo prendo da dati
	    // contesto domanda
	    if (StringUtils.isBlank(retObj.getOggettoComunicazione().getCodiceAttivita())) {
		retObj.getOggettoComunicazione().setCodiceAttivita(dati.getDatiContestoDomanda().getCodiceAttivitaBdr());
	    }
	    if (StringUtils.isBlank(retObj.getOggettoComunicazione().getAzioneAttivita())) {
		// AZIONE': anche questa informazione viene essere trasmessa
		// quando l'utente conferma la selezione degli endoprocedimenti
		AzioneType tipoAzioneEnum = AzioneType.valueOf(dati.getDatiContestoDomanda().getTipoAzione());
		// BOCCI 2013-02-28 se per qualche motivo mi arriva AvvioSt0
		// allora lo devo sostituire con avvio altrimenti la notifica
		// ASL va male
		if (tipoAzioneEnum.equals(AzioneType.AVVIO_ST_0)) {
		    retObj.getOggettoComunicazione().setAzioneAttivita(AzioneType.AVVIO.value());
		} else {
		    retObj.getOggettoComunicazione().setAzioneAttivita(tipoAzioneEnum.value()); // valorizzo
												// i
												// campi
												// della
												// domanda
												// che
												// devono
												// essere
												// letti
												// dalla
												// modulistica
												// compilata
												// utilizzando
												// le
												// mappature
												// specificate
												// nel
												// file
												// Properties
		}
	    } else {
		String tipoAzioneSettata = retObj.getOggettoComunicazione().getAzioneAttivita();
		// BOCCI 2013-02-28 se per qualche motivo mi arriva AvvioSt0
		// allora lo devo sostituire con avvio altrimenti la notifica
		// ASL va male
		if (tipoAzioneSettata.equalsIgnoreCase(AzioneType.AVVIO_ST_0.value())) {
		    retObj.getOggettoComunicazione().setAzioneAttivita(AzioneType.AVVIO.value());
		}
	    }
	}
	// valorizzo id domanda secondo DPR 160
	// <cf_richiedente>-<DDMMYYYY>-<HHmm>
	retObj.setIdDomanda(generaIdDomanda(retObj, dataDomanda));
	retObj.setCodiceComune(getSuapId(dati.getDatiContestoDomanda().getCodicecomune()));
	// §§§END§§§
	return retObj;
    }

    /**
     * BOCCI 2013-01-21: dopo la nota di regione toscana è emerso che per popolare il tag
     * presentazioneDomanda.procuraSpeciale.ilSottoscritto sia necessaria la concatenazione di due valori (nello
     * specifico "SOTTOSCRITTO.NOME SOTTOSCRITTO.COGNOME"). È stata aggiunta una regola nella creazione del file
     * properties. Se si vogliono accodare più valori allo stesso path si devono aggiungere i caratteri([+]) a fine
     * property. Es: se si volessero concatenare al path presentazioneDomanda.procuraSpeciale.ilSottoscritto gli
     * idsemantici
     * 
     * <pre>
     * 	SOTTOSCRITTO.NOME
     * 	SOTTOSCRITTO.COGNOME
     * 	SOTTOSCRITTO.CF
     * </pre>
     * 
     * nell'ordine definito le properties vanno configurate in questo modo:
     * 
     * <pre>
     *         SOTTOSCRITTO.NOME=procuraSpeciale.ilSottoscritto
     *         SOTTOSCRITTO.COGNOME[+]=procuraSpeciale.ilSottoscritto
     *         SOTTOSCRITTO.SOTTOSCRITTO.CF[+][+]=procuraSpeciale.ilSottoscritto
     * </pre>
     * 
     * aggiungendo per ogni posizione successiva un gruppo[+]. In questo modo nel path
     * <b>presentazioneDomanda.procuraSpeciale.ilSottoscritto</b> troveremo il valore "Riccardo Bocci BCCRCR73H23G888O".
     * Al momento funziona solamente per proprietà stringa.
     * 
     * @param dati
     * @param mappaturaIdSemantici
     * @param std2_std0
     * @param retObj
     * @throws ParseException
     * @throws DatatypeConfigurationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private void processaMappature(DatiDomandaCart dati, Properties mappaturaIdSemantici, String std2_std0, PresentazioneDomanda retObj)
	    throws ParseException, DatatypeConfigurationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

	Properties result = new Properties();
	Enumeration<String> idSemantici = (Enumeration<String>) mappaturaIdSemantici.propertyNames();
	PropertyUtilsBean propSetter = new PropertyUtilsBean();
	while (idSemantici.hasMoreElements()) {
	    String idSemantico = (String) idSemantici.nextElement();
	    if (idSemantico.endsWith("[+]")) {
		result.put(idSemantico.replaceFirst("\\[\\+\\]", ""), mappaturaIdSemantici.get(idSemantico));
		continue;
	    }
	    String propertyName = mappaturaIdSemantici.getProperty(idSemantico);
	    // verifico che l'id semantico esista fra i dati trasmessi
	    // i valori degli id semantici vengono sempre presi dal modulo
	    // principale relativo all'attività,
	    ValoreIdSemantico idSemanticoValue = dati.getValoreIdSemantico(std2_std0, idSemantico);
	    if (null != idSemanticoValue) {
		// si accettano solo id semantici associati a valori singoli
		if (idSemanticoValue.isScalare()) {
		    String valoreGiaPresente = "";
		    Object res = propSetter.getProperty(retObj, propertyName);
		    if (res instanceof String) {
			valoreGiaPresente = String.valueOf(res);
		    }
		    Object valore = idSemanticoValue.getValoreScalare();
		    if (StringUtils.isNotBlank(valoreGiaPresente)) {
			if (valore instanceof String) {
			    valore = valoreGiaPresente + " " + valore;
			}
		    }
		    if (idSemantico.contains("DATA") && null != valore) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date valoreDate = sdf.parse(valore.toString());
			GregorianCalendar valoreCalendar = new GregorianCalendar();
			valoreCalendar.setTime(valoreDate);
			XMLGregorianCalendar valoreXMLCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(valoreCalendar);
			valore = valoreXMLCalendar;
		    }
		    propSetter.setProperty(retObj, propertyName, valore);
		    if (log.isDebugEnabled()) {
			log.debug(
				"creaOggettoPresentazioneDomanda() - Popolamento messaggio di presentazione domanda. Il campo {} è stato popolato con il valore {} associato all'id semantico {}.",
				new Object[] { propertyName, valore, idSemantico });
		    }
		} else {
		    String valueType = idSemanticoValue.isIndicizzato() ? "indicizzato" : "vettoriale";
		    log.warn(
			    "creaOggettoPresentazioneDomanda() - Popolamento messaggio di presentazione domanda. Il campo {} non sarà popolato perché il valore associato all'id semantico {} è di tipo {} anziché di tipo scalare.",
			    new String[] { propertyName, idSemantico, valueType });
		}
	    } else {
		// if(log.isDebugEnabled()){
		log.warn(
			"creaOggettoPresentazioneDomanda() - Popolamento messaggio di presentazione domanda. Il campo {} non sarà popolato perché non esiste alcun valore associato all'id semantico {}",
			new String[] { propertyName, idSemantico });
		// }
	    }
	}
	if (result != null) {
	    if (result.size() > 0) {
		processaMappature(dati, result, std2_std0, retObj);
	    }
	}
    }

    public void gestisciAllegatiDomanda(DatiDomandaCart dati, PresentazioneDomanda domanda, String stdRef) {

	// §§§BEGIN§§§
	// popolamento di indiceZip con i riferimenti agli allegati
	IndiceZip indiceZip = new IndiceZip();
	List<Allegato> allegatiPratica = indiceZip.getAllegato();
	String codicePraticaEscaped = AttachmentsUtils.escapeForDirectoryName(domanda.getIdDomanda());
	// recupero i nomi dei riferimenti attivi (standard 2|standard 0 + tutti
	// i codici degli endo richiesti dal cittadino)
	Set<String> codiciRiferimenti = new HashSet<String>();
	Set<DatiModulo> datiModuli = dati.getDatiModuli();
	Map<String, List<DestinatariETType>> rifModulodest = new HashMap<String, List<DestinatariETType>>();
	for (DatiModulo datiModulo : datiModuli) {
	    codiciRiferimenti.add(datiModulo.getIdModulo());
	}
	List<ModuloType> s = dati.getModulistica().getModulistica().getModulo();
	for (ModuloType mt : s) {
	    List<DestinatariETType> dt = mt.getDestinatariET();
	    if (dt.size() > 0) {
		if (mt.getRiferimento() != null) {
		    if (StringUtils.isNotBlank(mt.getRiferimento().getCodiceEndoProcedimento())) {
			rifModulodest.put(mt.getRiferimento().getCodiceEndoProcedimento(), dt);
		    } else if (StringUtils.isNotBlank(mt.getRiferimento().getCodiceModello())) {
			rifModulodest.put(mt.getRiferimento().getCodiceModello(), dt);
		    }
		}
	    }
	}
	// analisi dei files da allegare:
	File tempDir = new File(AttachmentsUtils.getSystemTempDir(), FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_SUBDIR_NAME);
	tempDir = new File(tempDir, codicePraticaEscaped);
	// scarico nella directory temporanea glil allegati salvati nelle
	// tabelle del front office
	List<CartFileCopyInfo> userFiles = this.domandeFrontOfficeService.downloadAllegatiDomanda(dati, tempDir);
	// prima cerco tutti i files generati dalla procedura che iniziano con
	// il codice della pratica
	File[] files = tempDir.listFiles();
	// String nomeFileSUAPPDF = new
	// StringBuilder(codicePraticaEscaped).append(".SUAP").toString();
	for (File file : files) {
	    if (file.getName().startsWith(codicePraticaEscaped)) {
		Allegato all = new Allegato();
		// files PDF generati dalla procedura saranno messi nello zip
		// dopo essere stati firmati perciò nel nome file va aggiunto
		// .P7M
		String fileName = file.getName().toUpperCase().endsWith(".PDF") ? file.getName().concat(".P7M") : file.getName();
		all.setNomeFile(fileName);
		String codiceAll = "";
		String descrizioneAll = "";
		for (Iterator<String> iterator = codiciRiferimenti.iterator(); iterator.hasNext();) {
		    String refName = iterator.next();
		    StringBuilder refNameSb = new StringBuilder(refName);
		    refNameSb.append(file.getName().substring(file.getName().lastIndexOf('.')));
		    if (file.getName().toUpperCase().endsWith(refNameSb.toString().toUpperCase())) {
			// all.setDescrizione(refName);
			descrizioneAll = refName;
			break;
		    }
		}
		all.setDescrizione(descrizioneAll);
		all.setCodice(codiceAll);
		if (rifModulodest.get(StringUtils.defaultString(descrizioneAll)) != null) {
		    List<DestinatariETType> sdets = rifModulodest.get(StringUtils.defaultString(descrizioneAll));
		    Destinatari dest = new Allegato.Destinatari();
		    for (DestinatariETType destinatariETType : sdets) {
			dest.getDestinatario().add(destinatariETType.getTipo());
		    }
		    all.setDestinatari(dest);
		} else {
		    Destinatari dest = new Allegato.Destinatari();
		    dest.getDestinatario().add("");
		    all.setDestinatari(dest);
		}
		allegatiPratica.add(all);
	    } else {
		// si tratta di un allegato caricato dall'utente che deve essere
		// elaborato diversamente, viene messo in una lista perché sarà
		// trattato in seguito
		// userFiles.add(file);
	    }
	}
	// ora elaboro gli altri files, quelli uploadati dall'utente
	// Map<String, ErroreValidazione> firmeMancanti =
	// dati.getErroriValidazioneFirmaDigitale();
	for (CartFileCopyInfo userFile : userFiles) {
	    String cartFileName = userFile.buildNomeFilePresentazioneDomanda();
	    // elaborazione di allegato caricato dall'utente
	    // String fileName = file.getName();
	    Allegato allegatoUtente = new Allegato();
	    String idSemantico = userFile.getIdSemantico();
	    String riferimento = dati.getModuloAppartenenzaIdSemantico(idSemantico);
	    // recupero il ValoreIdSemantico
	    ValoreIdSemantico valoreFile = dati.getValoreIdSemantico(riferimento, idSemantico);
	    if (valoreFile == null) {
		log.warn(
			"gestisciAllegatiDomanda() - il file {} non è riconosciuto come file appartenente alla domanda perciò non sarà allegato alla domanda",
			cartFileName);
		continue;
	    }
	    // verifico se esiste un campo CODICE associato a questo file
	    String codiceAllegato = "";
	    int minusIndex = idSemantico.lastIndexOf('-');
	    if (minusIndex > -1) {
		String idSemanticoCodiceFile = new StringBuilder(idSemantico.substring(0, minusIndex)).append("-CODICE").toString();
		ValoreIdSemantico valoreCodice = dati.getValoreIdSemantico(riferimento, idSemanticoCodiceFile);
		if (null != valoreCodice) {
		    IndiceIdSemantico index = valoreFile.indexOfValue(userFile.getIdOggetto().toString());
		    if (index != null) {
			valoreCodice = dati.getValoreIdSemanticoPerIndice(riferimento, idSemanticoCodiceFile, index);
			codiceAllegato = StringUtils.defaultString(valoreCodice.getValoreScalare());
		    }
		}
	    }
	    allegatoUtente.setNomeFile(cartFileName);
	    allegatoUtente.setCodice(StringUtils.defaultString(codiceAllegato));
	    allegatoUtente.setDescrizione(StringUtils.defaultString(riferimento));
	    if (rifModulodest.get(StringUtils.defaultString(riferimento)) != null) {
		List<DestinatariETType> sdets = rifModulodest.get(StringUtils.defaultString(riferimento));
		Destinatari dest = new Allegato.Destinatari();
		for (DestinatariETType destinatariETType : sdets) {
		    dest.getDestinatario().add(destinatariETType.getTipo());
		}
		allegatoUtente.setDestinatari(dest);
	    } else {
		Destinatari dest = new Allegato.Destinatari();
		dest.getDestinatario().add("");
		allegatoUtente.setDestinatari(dest);
	    }
	    allegatiPratica.add(allegatoUtente);
	    /*
	     * } else { log.warn(
	     * "gestisciAllegatiDomanda() - il file {} non è stato generato dalla procedura e non è riconosciuto come file caricato dall'utente perciò non sarà allegato alla domanda"
	     * , cartFileName); }
	     */
	}
	/*
	 * AGGIUNGO ALL'INDICE ZIP ANCHE IL RIFERIMENTO AL FILE <codice
	 * pratica>.SUAP.PDF.P7M CHE CONTIENE LA VERSIONE STAMPABILE (PDF) DEL
	 * CORPO XML DEL MESSAGGIO. TALE FILE DEVE ANCORA ESSERE CREATO AGGIUNGO
	 * ANCHE LO STESSO FILE IN VERSIONE XML CHE NON E' RICHIESTO DALL
	 * RFC-183 MA LO E' PER IL DPR160 ANCHE QUESTO FILE DEVE ANCORA ESSERE
	 * GENERATO
	 */
	// PDF
	StringBuilder sbFileName = new StringBuilder(codicePraticaEscaped).append(".SUAP.PDF");
	String fileNamePdf = sbFileName.toString();
	sbFileName.append(".P7M");
	String filenameP7m = sbFileName.toString();
	Allegato distintaModello = new Allegato();
	distintaModello.setNomeFile(filenameP7m);
	distintaModello.setDescrizione(stdRef);
	distintaModello.setCodice("");
	Destinatari dest = new Allegato.Destinatari();
	dest.getDestinatario().add("");
	distintaModello.setDestinatari(dest);
	allegatiPratica.add(distintaModello);
	// XML
	String fileNameXml = new StringBuilder(codicePraticaEscaped).append(".SUAP.XML").toString();
	distintaModello = new Allegato();
	distintaModello.setNomeFile(fileNameXml);
	distintaModello.setDescrizione("");
	distintaModello.setCodice("");
	dest = new Allegato.Destinatari();
	dest.getDestinatario().add("");
	distintaModello.setDestinatari(dest);
	// il file generato per ora è uno solo <codice_pratica>.SUAP.PDF.P7M ma
	// in realtà il suo contenuto è in XML
	allegatiPratica.add(distintaModello);
	// AGGIUNGO ANCHE LA RICEVUTA CHE AVRA' UN NOME FISSO
	// ricevuta-<codice_pratica>.pdf
	String fileNameRicevuta = new StringBuilder("ricevuta-").append(codicePraticaEscaped).append(".pdf").toString();
	Allegato ricevuta = new Allegato();
	ricevuta.setNomeFile(fileNameRicevuta);
	ricevuta.setDescrizione("NONSPECIFICO");
	ricevuta.setCodice("");
	dest = new Allegato.Destinatari();
	dest.getDestinatario().add("");
	ricevuta.setDestinatari(dest);
	// il file generato per ora è uno solo <codice_pratica>.SUAP.PDF.P7M ma
	// in realtà il suo contenuto è in XML
	allegatiPratica.add(ricevuta);
	//
	domanda.setIndiceZip(indiceZip);
	/*
	 * aggiungo la sezione zipFile ma non valorizzo l'attributo datiFile che
	 * viene valorizzato in inviaMessaggioPresentazioneDomanda solo se è
	 * richiesto l'invio dell'allegato inline nel messaggio come campo base
	 * 64 binary
	 */
	ZipFileType zipFile = new ZipFileType();
	zipFile.setNomeFile(codicePraticaEscaped + FACCTConstants.ZIP_FILE_SUFFIX);
	zipFile.setContentType(FACCTConstants.ZIP_CONTENT_TYPE);
	domanda.setZipFile(zipFile);
	// creo l'allegato <codice pratica>.SUAP.PDF.P7M serializzando in XML
	// l'oggetto PresentazioneDomanda che sto creando
	// creazione del file XML che contiene il messaggio di
	// PresentazioneDomanda per allegarlo al messaggio stesso
	File distintaModelloFile = new File(tempDir, fileNameXml);
	try {
	    if (!distintaModelloFile.isFile()) {
		distintaModelloFile.createNewFile();
	    }
	    String xml = XmlUtils.marshallObject(domanda);
	    Schema schema = XmlUtils.getSchemaForMessage("cart/schema/RFC183_ComunicazioniInterneSUAP_presentazioneDomanda_FRU_e22.xsd");
	    XmlUtils.validaXml(xml, schema);
	    AttachmentsUtils.writeBytesToStream(xml.getBytes("UTF-8"), new FileOutputStream(distintaModelloFile));
	} catch (Exception e) {
	    String errMsg = "errore nella scrittura del file " + distintaModelloFile.getAbsolutePath();
	    log.error("gestisciAllegatiDomanda() - {}", errMsg);
	    throw new RuntimeException(errMsg, e);
	}
	// ne creo anche una versione in pdf
	// FileReader fr = new FileReader(distintaModelloFile);
	try {
	    byte[] xmlBytes = AttachmentsUtils.readBytesFromFile(distintaModelloFile);
	    String xmlString = new String(xmlBytes);
	    FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	    ConvertRequest cReq = new ConvertRequest(ORMHelper.getToken(), xmlString, FileConverterWsClient.ContentType.TXT.name(),
		    FileConverterWsClient.ConversionType.PDF.name());
	    ConvertResponse cResp = fileConverterWService.convert(cReq);
	    distintaModelloFile = new File(tempDir, fileNamePdf);
	    AttachmentsUtils.writeBytesToStream(cResp.getBinaryData(), new FileOutputStream(distintaModelloFile));
	} catch (Exception e) {
	    String errMsg = "errore nella scrittura del file " + distintaModelloFile.getAbsolutePath();
	    log.error("gestisciAllegatiDomanda() - {}", errMsg);
	    throw new RuntimeException(errMsg, e);
	}
	// §§§END§§§
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.cartfacct.service.PresentazioneDomandaService#
     * inviaMessaggioPresentazioneDomanda
     * (it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart, java.io.File,
     * boolean)
     */
    @Override
    public void inviaMessaggioPresentazioneDomanda(PresentazioneDomanda datiDomanda, File zipAllegati, boolean encodeAttachmentAs64Binary)
	    throws Exception {

	// §§§BEGIN§§§
	SoapAttachment attachment = null;
	if (encodeAttachmentAs64Binary) {
	    byte[] zipData = AttachmentsUtils.readBytesFromFile(zipAllegati);
	    ZipFileType zipFileInline = datiDomanda.getZipFile();
	    if (zipFileInline == null) {
		zipFileInline = new ZipFileType();
		zipFileInline.setNomeFile(zipAllegati.getName());
		zipFileInline.setContentType(FACCTConstants.ZIP_CONTENT_TYPE);
	    }
	    zipFileInline.setDatiFile(zipData);
	    datiDomanda.setZipFile(zipFileInline);
	} else {
	    attachment = new SoapAttachment();
	    attachment.setContent(zipAllegati);
	    attachment.setContentId(datiDomanda.getIdDomanda());
	    attachment.setContentType(FACCTConstants.ZIP_CONTENT_TYPE);
	}
	getPresentazioneDomandaServiceClient().presentazioneDomanda(datiDomanda, attachment);
	// §§§END§§§
    }

    @Override
    public Properties getMappaturaIdSemantici() throws IOException {

	// §§§BEGIN§§§
	Properties mappings = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream(FACCTConstants.CFG_FILE_MAPPING_IDSEMANTICI);
	if (is != null) {
	    mappings.load(is);
	    is.close();
	}
	return mappings;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService#findDocumentoIstanzaMessaggioPresentazioneDomanda(java.lang.Integer)
     */
    @Override
    public Documentiistanza findDocumentoIstanzaMessaggioPresentazioneDomanda(Istanze istanza) {

	if (istanza != null) {
	    Set<Documentiistanza> istDocs = istanza.getDocumentiistanzas();
	    for (Documentiistanza istDoc : istDocs) {
		if (istDoc.getOggetto() != null
			&& istDoc.getOggetto().getNomefile().toUpperCase().endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_RIEPILOGO_SUFFIX)) {
		    return istDoc;
		}
	    }
	}
	return null;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService#getMessaggioPresentazioneDomanda(java.lang.Integer)
     */
    @Override
    public PresentazioneDomanda getMessaggioPresentazioneDomanda(Integer codiceOggetto) {

	PresentazioneDomanda pd = null;
	InputStream is = this.oggettiService.getOggettoAsInputStream(codiceOggetto);
	if (is != null) {
	    Object obj = Utilities.unMarshallFromStream(is, PresentazioneDomanda.class);
	    pd = (PresentazioneDomanda) obj;
	}
	return pd;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService#getAllegatiMancantiPerNotificaEnteTerzo(it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public List<String> getAllegatiMancantiPerNotificaEnteTerzo(PresentazioneDomanda pd, Integer codiceIstanza, String std_2_0,
	    String codiceEndoRegionale) {

	List<String> missingDocs = new ArrayList<String>();
	IndiceZip indiceZip = pd.getIndiceZip();
	if (null != indiceZip) {
	    List<Allegato> allegatiDomanda = indiceZip.getAllegato();
	    if (allegatiDomanda != null) {
		for (Allegato allegato : allegatiDomanda) {
		    //TODO l'allegato deve essere notificato all'ente se appartiene al modulio principale o all'endo regionale se specificato
		}
	    }
	}
	return missingDocs;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService#generaDocumentiIstanzaperNotificaEnteTerzo(java.lang.Integer, it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig)
     */
    @Override
    public List<DocumentiistanzaDTO> generaDocumentiIstanzaperNotificaEnteTerzo(Integer codiceIstanza, CartMappingsConfig cartMappings,
	    ModulisticaContentType modulistica, List<DocumentiType> listaDocumenti) {

	List<DocumentiistanzaDTO> generatedDocs = new ArrayList<DocumentiistanzaDTO>();
	//popolo i dati di una domanda CART fittizia in base alla modulistica ottenuta ed alle mappature configurate
	DatiDomandaCart datiDomanda = null;
	String errMsg = null;
	List<CartMappingException> mappingCfgErrors = new ArrayList<CartMappingException>();
	Istanze istanza = null;
	istanza = this.istanzeService.findById(new PkId(codiceIstanza));
	if (istanza != null) {
	    try {
		datiDomanda = this.cartMappingService.popolaDomandaCart(codiceIstanza, modulistica, mappingCfgErrors, listaDocumenti);
		//popolo gli id semantici relativi algi allegati con una soecifica chiamata service
		this.cartMappingService.popolaAllegatiDomandaCart(istanza, datiDomanda, listaDocumenti);
		datiDomanda.setModulistica(modulistica);
	    } catch (Exception e) {
		errMsg = "cart.allegatinotifica.popoladomanda.error";
		errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { StringUtils.defaultString(e.getMessage()) });
		log.error("generaAllegatiNotifica - errore nel recupero dei dati dall'istanza " + codiceIstanza, e);
		throw new RuntimeException(errMsg, e);
	    }
	} else {
	    errMsg = "Impossibile individuare l'istanza " + codiceIstanza;
	    log.error("generaAllegatiNotifica - impossibile individuare l'istanza " + codiceIstanza);
	    throw new RuntimeException(errMsg);
	}
	if (datiDomanda != null) {
	    //validazione dei dati recuperati con le mappature
	    DatiDomandaCart datiDomandaClone = copyData(datiDomanda);
	    List<ErroreValidazione> validazErrs = this.cartModulisticaService.validazioneDomanda(datiDomandaClone);
	    Properties mappingMsgPresentazione = null;
	    try {
		mappingMsgPresentazione = this.getMappaturaIdSemantici();
	    } catch (IOException ioe) {
		errMsg = "cart.allegatinotifica.mappatureidsemantici.ioerror";
		errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { FACCTConstants.CFG_FILE_MAPPING_IDSEMANTICI, ioe });
		log.error(
			"generaAllegatiNotifica - errore nel caricamento delle mappature fra gli attributi del messaggio di presentazione e gli id semantici della domanda dal file "
				+ FACCTConstants.CFG_FILE_MAPPING_IDSEMANTICI, ioe);
	    }
	    if (null != mappingMsgPresentazione) {
		PresentazioneDomanda pd = null;
		List<File> files = new ArrayList<File>();
		try {
		    pd = this.creaOggettoPresentazioneDomanda(datiDomanda, mappingMsgPresentazione, null, FACCTConstants.STANDARD_0,
			    istanza.getData());
		} catch (Exception e1) {
		    errMsg = "cart.allegatinotifica.presentazionedomanda.creationerror";
		    errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { e1 });
		    log.error("generaAllegatiNotifica - errore nella creazione del messaggio di presentazione della domanda", e1);
		    throw new RuntimeException(errMsg, e1);
		}
		if (pd != null) {
		    //predispongo la directory temporanea
		    String idDomanda = AttachmentsUtils.escapeForDirectoryName(pd.getIdDomanda());
		    datiDomanda.getDatiContestoDomanda().setIdDomandaCart(idDomanda);
		    //scrivo MDA.XML x tutti gli id semantici anche in caso di errori per poter effettuare verifiche sul file generato
		    File tempDir = Utilities.getSystemTempDir();
		    tempDir = new File(tempDir, FACCTConstants.GENERA_ALLEGATI_NOTIFICA_TEMP_SUBDIR_NAME);
		    tempDir = new File(tempDir, idDomanda);
		    if (tempDir.isDirectory()) {
			Utilities.deleteFolder(tempDir);
		    }
		    tempDir.mkdirs();
		    File mdaXml = new File(tempDir, idDomanda + ".MDA.XML");
		    try {
			this.scriviFileMDADaDatiDomandaPresentata(datiDomanda, mdaXml);
			files.add(mdaXml);
		    } catch (Exception e) {
			errMsg = "cart.allegatinotifica.file.creationerror";
			errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { mdaXml.getName(), e });
			log.error("generaAllegatiNotifica - errore nella generazione del file MDA " + mdaXml.getName(), e);
			throw new RuntimeException(errMsg, e);
		    }
		    if (validazErrs.isEmpty()) {
			String STD_0 = FACCTConstants.STANDARD_0;
			ModuloType modStd0 = CartModuloHelper.findModuloByRiferimento(modulistica, STD_0);
			if (modStd0 != null) {
			    String suffix = ".MDA." + STD_0.toUpperCase();
			    //scrivo MDA:STANDARD_0.XML
			    mdaXml = new File(tempDir, idDomanda + suffix + ".XML");
			    try {
				this.scriviFileMDADaDatiDomandaPresentata(datiDomanda, mdaXml, STD_0);
				files.add(mdaXml);
			    } catch (Exception e) {
				errMsg = "cart.allegatinotifica.file.creationerror";
				errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { mdaXml.getName(), e });
				log.error("generaAllegatiNotifica - errore nella generazione del file MDA " + mdaXml.getName(), e);
				throw new RuntimeException(errMsg, e);
			    }
			    //scrivo MDA.PDF per std_0
			    File moduloPdf = new File(tempDir, idDomanda + suffix + ".PDF");
			    Map<Object, Object> contextData = new HashMap<Object, Object>();
			    //contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_CONTEXT_PATH, Utilities.buildAbsoluteURL(request));
			    //genero il file.MDA.standard_X.pdf
			    try {
				this.cartModulisticaService.renderModuloToFile(modStd0, datiDomanda, contextData, moduloPdf);
				files.add(moduloPdf);
			    } catch (Exception e) {
				errMsg = "cart.allegatinotifica.file.creationerror";
				errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { moduloPdf.getName(), e });
				log.error("generaAllegatiNotifica - errore nella generazione del file " + moduloPdf.getName(), e);
				throw new RuntimeException(errMsg, e);
			    }
			} else {
			    errMsg = "cart.allegatinotifica.modulostd0.missingerror";
			    errMsg = Utilities.getMessageFromBundle(context, errMsg, null);
			    log.error("generaAllegatiNotifica - Manca il modulo STANDARD_0");
			}
			//gestione degli allegati e completamento del messaggio di presentazione SUAP.XML
			File[] cartFiles = new File[0];
			try {
			    cartFiles = this.cartMappingService.completaMessaggioPresentazioneDomanda(datiDomanda, pd);
			} catch (Exception e) {
			    errMsg = "cart.allegatinotifica.presentazionedomanda.writeerror";
			    errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { e.toString() });
			    log.error(
				    "generaAllegatiNotifica - errore nella scrittura degli allegati nel messaggio di presentazione domanda "
					    + e.getMessage(), e);
			    throw new RuntimeException(errMsg, e);
			}
			if (StringUtils.isEmpty(errMsg)) {
			    File[] generatedFiles = new File[files.size() + cartFiles.length];
			    for (int i = 0; i < files.size(); i++) {
				File file = files.get(i);
				generatedFiles[i] = file;
			    }
			    for (int i = 0; i < cartFiles.length; i++) {
				generatedFiles[i + files.size()] = cartFiles[i];
			    }
			    try {
				//salvo gli allegati generati come allegati dell'istanza
				generatedDocs = this.cartMappingService.salvaAllegatiCart(codiceIstanza, generatedFiles);
			    } catch (Exception e) {
				errMsg = "cart.allegatinotifica.salvaallegati.error";
				errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { e.toString() });
				log.error("generaAllegatiNotifica - errore nel salvataggio dei files come documenti dell'istanza " + e.getMessage(),
					e);
				throw new RuntimeException(errMsg, e);
			    }
			}
		    } else {
			errMsg = "cart.allegatinotifica.validazionemodulo.error";
			errMsg = Utilities.getMessageFromBundle(context, errMsg, new Object[] { new Integer(validazErrs.size()) });
			for (ErroreValidazione e : validazErrs) {
			    String error = "";
			    for (MessaggioErrore err : e.getErrori()) {
				error += err.getMessage() + ", ";
			    }
			    FlashMessages.getWarnings().add(e.getIdQuadro() + "_" + e.getIdSemantico() + ": " + error);
			}
			throw new RuntimeException(errMsg);
		    }
		}
	    }
	}
	return generatedDocs;
    }

    private DatiDomandaCart copyData(DatiDomandaCart datiDomanda) {

	DatiDomandaCart copy = new DatiDomandaCart();
	copy.setAllegatiDaFirmare(datiDomanda.getAllegatiDaFirmare());
	copy.setDatiContestoDomanda(datiDomanda.getDatiContestoDomanda());
	copy.setDistintaModello(datiDomanda.getDistintaModello());
	TreeSet<DatiModulo> modClone = new TreeSet<DatiModulo>();
	SortedSet<DatiModulo> s = datiDomanda.getModuli();
	for (DatiModulo datiModulo : s) {
	    DatiModulo dmCopy = new DatiModulo(datiModulo.getIdModulo());
	    List<MappingIdSemantico> p = datiModulo.getValoreModulo();
	    List<MappingIdSemantico> pcopy = new ArrayList<MappingIdSemantico>();
	    for (MappingIdSemantico mis : p) {
		String id = new String(mis.getIdSemantico());
		ValoreIdSemantico val = mis.getValore();
		if (val != null) {
		    ValoreIdSemantico valoreCopy = copyValore(val);
		    MappingIdSemantico misCopy = new MappingIdSemantico(id, valoreCopy);
		    pcopy.add(misCopy);
		}
	    }
	    dmCopy.getValoreModulo().addAll(pcopy);
	    modClone.add(dmCopy);
	}
	copy.setModuli(modClone);
	copy.setModulistica(datiDomanda.getModulistica());
	return copy;
    }

    private ValoreIdSemantico copyValore(ValoreIdSemantico val) {

	ValoreIdSemantico valoreCopy = new ValoreIdSemantico();
	if (!val.isEmpty()) {
	    if (val.isScalare()) {
		if (StringUtils.isNotBlank(val.getValoreScalare())) {
		    valoreCopy.setValoreScalare(new String(val.getValoreScalare()));
		}
	    }
	    if (val.isVettoriale()) {
		if (val.getValoreVettoriale() != null && val.getValoreVettoriale().length > 0) {
		    valoreCopy.setValoreVettoriale(val.getValoreVettoriale());
		}
	    }
	    if (val.isIndicizzato()) {
		if (val.getValoreIndicizzato() != null) {
		    List<ValoreIdSemantico> vis = val.getValoreIndicizzato();
		    List<ValoreIdSemantico> viscopy = new ArrayList<ValoreIdSemantico>();
		    for (ValoreIdSemantico vid : vis) {
			ValoreIdSemantico vidCopy = copyValore(vid);
			viscopy.add(vidCopy);
		    }
		    valoreCopy.setValoreIndicizzato(viscopy);
		}
	    }
	    if (val.isFile()) {
		List<FileInfo> finfoco = new ArrayList<FileInfo>();
		List<FileInfo> finfo = val.getFileInfo();
		for (FileInfo fileInfo : finfo) {
		    FileInfo fico = new FileInfo();
		    fico.setDimensione(fileInfo.getDimensione());
		    fico.setFirmaValidata(fileInfo.isFirmaValidata());
		    fico.setIdOggetto(fileInfo.getIdOggetto());
		    fico.setIdSemantico(fileInfo.getIdSemantico());
		    fico.setNomeFile(fileInfo.getNomeFile());
		    finfoco.add(fico);
		}
		valoreCopy.getFileInfo().addAll(finfoco);
	    }
	}
	return valoreCopy;
    }

    @Override
    public String generaIdentificativoPraticaCART(Istanze istanza) {

	StringBuilder sb = new StringBuilder();
	String cfRich = "";
	if (istanza.getRichiedente() != null) {
	    cfRich = StringUtils.defaultString(istanza.getRichiedente().getCodicefiscale());
	}
	if (StringUtils.isNotBlank(cfRich)) {
	    sb.append(cfRich).append("-");
	} else {
	    log.warn("generaIdDomanda() - l'id domanda generato non sarà corretto perchè manca il codice fiscale del richiedente.");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmm", Locale.ITALY);
	Date dataDomanda = istanza.getData() != null ? istanza.getData() : new Date();
	sb.append(sdf.format(dataDomanda));
	return sb.toString();
    }

    private List<IdSemanticoType> buildIndexedIds(ValoreIdSemantico valoreIndicizzato, String idSemantico, DatiDomandaCart datiDomanda) {

	List<IdSemanticoType> retIds = new ArrayList<IdSemanticoType>();
	// §§§BEGIN§§§
	if (valoreIndicizzato.isIndicizzato()) {
	    int insertionPoint = idSemantico.indexOf(':');
	    StringBuilder sbIdSemanticoHead = new StringBuilder();
	    String idSemanticoEnd = "";
	    if (insertionPoint > -1) {
		sbIdSemanticoHead.append(idSemantico.substring(0, insertionPoint));
		idSemanticoEnd = idSemantico.substring(insertionPoint);
	    } else {
		sbIdSemanticoHead.append(idSemantico);
	    }
	    List<ValoreIdSemantico> valoriIndicizzati = valoreIndicizzato.getValoreIndicizzato();
	    StringBuilder sbIdSemantico = null;
	    ValoreIdSemantico valoreInterno = null;
	    for (int i = 0; i < valoriIndicizzati.size(); i++) {
		sbIdSemantico = new StringBuilder(sbIdSemanticoHead);
		sbIdSemantico.append(":").append(i + 1).append(idSemanticoEnd);
		valoreInterno = valoriIndicizzati.get(i);
		if (valoreInterno.isIndicizzato()) {
		    List<IdSemanticoType> innerIds = buildIndexedIds(valoreInterno, sbIdSemantico.toString(), datiDomanda);
		    retIds.addAll(innerIds);
		} else {
		    IdSemanticoType retId = buildId(valoreInterno, sbIdSemantico.toString(), datiDomanda);
		    retIds.add(retId);
		}
	    }
	}
	// §§§END§§§
	return retIds;
    }

    private static IdSemanticoType buildId(ValoreIdSemantico valoreNonIndicizzato, String idSemantico, DatiDomandaCart datiDomanda) {

	IdSemanticoType retId = new IdSemanticoType();
	// §§§BEGIN§§§
	retId.setNome(idSemantico);
	List<DatoType> innerValuesOut = retId.getDato();
	String[] innerValuesIn = new String[1];
	if (valoreNonIndicizzato.hasNoValues()) {
	    innerValuesIn = new String[] { "" };
	} else {
	    if (valoreNonIndicizzato.isScalare()) {
		innerValuesIn[0] = valoreNonIndicizzato.getValoreScalare();
	    } else if (valoreNonIndicizzato.isVettoriale()) {
		innerValuesIn = valoreNonIndicizzato.getValoreVettoriale();
		if (innerValuesIn == null || innerValuesIn.length == 0) {
		    innerValuesIn = new String[] { "" };
		}
	    }
	    if (valoreNonIndicizzato.isFile()) {
		FileInfo fInfo = null;
		String[] innerValuesFile = new String[innerValuesIn.length];
		for (int i = 0; i < innerValuesIn.length; i++) {
		    if (NumberUtils.isNumber(innerValuesIn[i])) {
			fInfo = datiDomanda.getAllegatoByCodiceOggetto(NumberUtils.toInt(innerValuesIn[i], 0));
			if (fInfo != null) {
			    if (!Utilities.isBackOffice()) {
				CartFileCopyInfo fCopy = new CartFileCopyInfo();
				fCopy.setNomeFileTemporaneo(fInfo.getNomeFile());
				fCopy.setIdOggetto(fInfo.getIdOggetto());
				fCopy.setIdComune(ORMHelper.getIdcomune());
				innerValuesFile[i] = fCopy.buildNomeFilePresentazioneDomanda();
			    } else {
				// nel caso di compilazione della modulistica
				// nel BO i nomi dei files vengono mantenuti
				// inalterati
				innerValuesFile[i] = fInfo.getNomeFile();
			    }
			} else {
			    log.warn("buildId - impossibile recuperare dai dati della domanda l'allegato avente codice oggetto = {}",
				    new Object[] { innerValuesIn });
			}
		    }
		}
		innerValuesIn = innerValuesFile;
	    }
	}
	for (int j = 0; j < innerValuesIn.length; j++) {
	    DatoType valueOut = new DatoType();
	    valueOut.setValore(innerValuesIn[j]);
	    innerValuesOut.add(valueOut);
	}
	// §§§END§§§
	return retId;
    }

    // @Override
    private String generaIdDomanda(PresentazioneDomanda datiPresentazione, Date dataDomanda) {

	// §§§BEGIN§§§
	StringBuilder sb = new StringBuilder();
	String cfRich = datiPresentazione.getPresentatore().getCf();
	if (StringUtils.isNotBlank(cfRich)) {
	    sb.append(cfRich).append("-");
	} else {
	    log.warn("generaIdDomanda() - l'id domanda generato non sarà corretto perchè manca il codice fiscale del richiedente.");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmm", Locale.ITALY);
	if (dataDomanda == null) {
	    dataDomanda = new Date();
	}
	sb.append(sdf.format(dataDomanda));
	return sb.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private String getSuapId(String codiceComune) {

	// §§§BEGIN§§§
	// BOCCI 2012-12-20 se viene passato il codicecomune allora compongo
	// l'identificativo suap con quel codice istat altrimenti lo prendo
	// dalle verticalizzazioni come prima
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni comune = comuniService.findById(StringUtils.defaultString(codiceComune));
	    if (comune != null) {
		String codiceIstat = comune.getCodiceistat();
		if (codiceIstat != null) {
		    CodificaEntiRfc53 cod = codificaEntiRfc53DAO.findById(codiceIstat);
		    if (cod != null) {
			String codifica = StringUtils.defaultString(cod.getCodifica()).trim();
			if (StringUtils.isNotBlank(codifica)) {
			    return codifica;
			}
		    }
		}
		return "13.13.1.M.000." + StringUtils.defaultString(codiceIstat);
	    }
	}
	CartRfc183PresentazioneDomandaServiceClientImpl presServiceImpl = (CartRfc183PresentazioneDomandaServiceClientImpl) this.presentazioneDomandaServiceClient;
	return presServiceImpl.getParametriConfigurazione().getSuapId();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private ProcuraSpeciale initProcuraSpeciale() {

	ProcuraSpeciale ps = new ProcuraSpeciale();
	ps.setAttivitaDi("");
	ps.setDaSvolgersiIn("");
	ps.setIlSignor("");
	ps.setIlSottoscritto("");
	ps.setInQualitaDi("");
	ps.setInterno("");
	ps.setNumero("");
	ps.setQualitaProcuraSpeciale("");
	ps.setVia("");
	return ps;
    }
}
