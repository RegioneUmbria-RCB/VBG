package it.gruppoinit.pal.gp.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.Response.Status;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.SoggettiIstanzaFilterCF;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiEnum;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ApiProducerService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.PraticheRestException;
import it.gruppoinit.pal.gp.core.service.helper.DownloadPraticaZipHelper;
import it.gruppoinit.pal.gp.core.service.helper.PraticaRestBean;
import it.gruppoinit.pal.gp.core.service.helper.PraticheRestBeanResult;
import it.gruppoinit.pal.gp.core.service.helper.ProblemResult;
import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.ProcuraType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class ApiProducerServiceImpl implements ApiProducerService {

    private static final Logger log = LoggerFactory.getLogger(ApiProducerServiceImpl.class);
    private final String tmp_base_dir = "API_SERVICE";
    private final String nomeFileDomanda = "pratica-mda.xml";
    private StcService stcService;
    private ExternalDBResolver externalDBResolver;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;
    private Map<String, String> idcomuneAliasMap;
    private SecurityWSClient securityWSClient;
    private IstanzeService istanzeService;
    private NlaHelperService nlaHelperService;
    private OggettiService oggettiService;
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AlberoprocMetadatiService alberoprocMetadatiService;

    @Autowired
    public void setSecurityWSClient(SecurityWSClient securityWSClient) {

	this.securityWSClient = securityWSClient;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setNlaHelperService(NlaHelperService nlaHelperService) {

	this.nlaHelperService = nlaHelperService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @Autowired
    public void setStcService(StcService stcService) {

	this.stcService = stcService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    public RiferimentiPraticaSTCRestBean creaPraticaDaZip(InputStream zipFile, String numeroProtocollo, Date dataProtocollo) {

	RiferimentiPraticaSTCRestBean result = new RiferimentiPraticaSTCRestBean();
	UUID id_temporaneo_domanda = UUID.randomUUID();
	String folderId = id_temporaneo_domanda.toString();
	// 1 UNZIP FILE IN TEMP FOLDER
	File tmpFolder = Utilities.createTmpDir(tmp_base_dir, folderId);
	File unzipTo = null;
	try {
	    try {
		unzipTo = Utilities.unzipTo(zipFile, tmpFolder);
	    } catch (IOException e) {
		log.error("Errore nella scompattazione del file {}", e.getMessage(), e);
		result.setErrore(populateErrore("500", "Errore nella verifica del file ZIP", e));
		return result;
	    }
	    // 2 VALIDA STRUTTURA FILE
	    CodiceDescrizioneBean errore = validaContenutoFile(unzipTo);
	    if (errore != null) {
		result.setErrore(errore);
		return result;
	    }
	    InserimentoPraticaRequest request = null;
	    // 3 ELABORA DOMANDA STC
	    // 3.1 estrae la domanda
	    try {
		request = getPraticaXml(tmpFolder);
	    } catch (Exception e) {
		log.error("Errore nel recupero del file della pratica [{}], {}, {}", new Object[] { nomeFileDomanda, unzipTo, e });
		result.setErrore(populateErrore("500", "Errore nel recupero del file " + nomeFileDomanda + ", rif " + id_temporaneo_domanda, e));
		return result;
	    }
	    // 3.2 setta ORMHelper
	    try {
		setORMHelper(request.getSportelloMittente());
	    } catch (Exception e) {
		log.error("Errore durante il settaggio di ORMHelper [{}], {}", new Object[] { unzipTo, e });
		result.setErrore(
			populateErrore("500", "Errore durante la configurazione delle variabili di ambiente. rif: " + id_temporaneo_domanda, e));
		return result;
	    }
	    // 3.3 imbustaAllegati come DataHandler
	    try {
		preparaDomanda(request, unzipTo, numeroProtocollo, dataProtocollo);
	    } catch (Exception e) {
		log.error("Errore durante il collegamento degli allegati della domanda [{}], {}", new Object[] { unzipTo, e });
		result.setErrore(populateErrore("500", "Errore il collegamento degli allegati della domanda. rif: " + id_temporaneo_domanda, e));
		return result;
	    }
	    // 4 INVIA DOMANDA
	    try {
		populateMittentiDestinatari(request);
		InserimentoPraticaResponse response = stcService.inviaPratica(request);
		if (response.getDettaglioErrore() != null && !response.getDettaglioErrore().isEmpty()) {
		    StringBuffer descrizioneErrore = new StringBuffer();
		    List<ErroreType> l = response.getDettaglioErrore();
		    for (ErroreType erroreType : l) {
			descrizioneErrore.append(erroreType.getDescrizione()).append("\n");
		    }
		    result.setErrore(populateErrore("500", descrizioneErrore.toString(), null));
		    return result;
		}
		RiferimentiPraticaType rifPratica = response.getDettaglioPratica();
		populateResult(result, rifPratica, request);
	    } catch (Exception e) {
		log.error("Errore durante l'invio della pratica STC [{}], {}", new Object[] { unzipTo, e });
		result.setErrore(populateErrore("500", "Errore l'invio della domanda. rif: " + id_temporaneo_domanda, e));
		return result;
	    }
	    // 5 TORNA RISPOSTA
	} finally {
	    if (unzipTo != null) {
		Utilities.deleteFolder(unzipTo);
	    }
	}
	// 6 GESTISCI ERRORE
	return result;
    }

    @Override
    public PraticheRestBeanResult findPraticheByCF(String cf, String software, Integer offset, Integer limit) throws PraticheRestException {

	offset = (offset == null) ? 0 : offset;
	limit = (limit == null) ? 50 : limit;
	software = (software == null) ? "TT" : software;
	IstanzeFilter filter = populateFilter(cf, software);
	try {
	    return getPraticheRestBean(filter, offset, limit);
	} catch (Exception e) {
	    ProblemResult problem = newProblemResult(
		    "Non sono state trovate pratiche per l'utente con Codice Fiscale: " + cf + ", dettaglio: " + e.getMessage(), "",
		    Status.INTERNAL_SERVER_ERROR.getStatusCode(), "", "");
	    throw new PraticheRestException(problem);
	}
    }

    private IstanzeFilter populateFilter(String cf, String software) {

	Set<String> idComuneAttivi = getIdComuneAttivi();
	IstanzeFilter istanzeFilter = new IstanzeFilter();
	istanzeFilter.setDefaultWhereCondition(DAOEnum.FIND_ALL);
	istanzeFilter.getListaIdcomuneFiltro().addAll(idComuneAttivi);
	Software modulo = new Software();
	modulo.setCodice(software);
	istanzeFilter.setModulo(modulo);
	SoggettiIstanzaFilterCF soggettoIstanzaFilterCF = newSoggettiIstanzaFilterCF(cf);
	istanzeFilter.setSoggettiIstanzaFilterCF(soggettoIstanzaFilterCF);
	return istanzeFilter;
    }

    private SoggettiIstanzaFilterCF newSoggettiIstanzaFilterCF(String cf) {

	SoggettiIstanzaFilterCF soggettiIstanzaFilterCF = new SoggettiIstanzaFilterCF();
	soggettiIstanzaFilterCF.setRichiedenteCF(cf);
	soggettiIstanzaFilterCF.setProfessionistaCF(cf);
	return soggettiIstanzaFilterCF;
    }

    private PraticheRestBeanResult getPraticheRestBean(IstanzeFilter filter, Integer offset, Integer limit) throws Exception {

	PraticheRestBeanResult resultBean = new PraticheRestBeanResult();
	List<IstanzeListHelper> istanzeHelper = null;
	int count = istanzeService.countIstanzeListHelperByFilter(filter);
	if (count > 0) {
	    istanzeHelper = istanzeService.findIstanzeListHelperByFilter(filter, offset, limit);
	}
	List<PraticaRestBean> praticheRest = new ArrayList<PraticaRestBean>();
	for (IstanzeListHelper istanzaHelper : istanzeHelper) {
	    PraticaRestBean praticaRestBean = new PraticaRestBean();
	    populatePraticaRestBean(praticaRestBean, istanzaHelper);
	    String idcomunealias = getIdcomuneAlias(istanzaHelper.getIdcomune());
	    if (idcomunealias != null) {
		praticheRest.add(praticaRestBean);
	    }
	}
	resultBean.setResults(praticheRest);
	resultBean.setTotal(count);
	resultBean.setOffset(offset);
	resultBean.setLimit(limit);
	return resultBean;
    }

    private void populatePraticaRestBean(PraticaRestBean praticaRestBean, IstanzeListHelper istanzaHelper) {

	praticaRestBean.setNumero_pratica(istanzaHelper.getNumeroistanza());
	praticaRestBean.setData_presentazione(istanzaHelper.getData());
	praticaRestBean.setOggetto(istanzaHelper.getOggettoistanza());
	praticaRestBean.setNumero_protocollo(istanzaHelper.getNumeroprotocollo());
	praticaRestBean.setData_protocollo(istanzaHelper.getDataprotocollo());
	praticaRestBean.setCodice_procedimento(istanzaHelper.getCodiceprocedura().toString());
	praticaRestBean.setDescrizione_procedimento(istanzaHelper.getProcedura());
	praticaRestBean.setStato(istanzaHelper.getStatoistanza());
	praticaRestBean.setLink(getLinkPratica(praticaRestBean, istanzaHelper, istanzaHelper.getIdcomune()));
	praticaRestBean.setEnte(istanzaHelper.getDescrizionesportello() + " - " + istanzaHelper.getComune());
	praticaRestBean.setCodice_ente(istanzaHelper.getCodicecomune());
	praticaRestBean.setId(getIdPratica(istanzaHelper));
	praticaRestBean.setUuid(istanzaHelper.getUuid());
	praticaRestBean.setAlias_ente(getIdcomuneAlias(istanzaHelper.getIdcomune()));
	if (istanzaHelper.getCodiceinterventoproc() != null) {
	    praticaRestBean.setCodice_intervento(String.valueOf(istanzaHelper.getCodiceinterventoproc().intValue()));
	}
	praticaRestBean.setDescrizione_intervento(istanzaHelper.getInterventoproc());
    }

    private ProblemResult newProblemResult(String detail, String instance, int status, String title, String type) {

	ProblemResult problem = new ProblemResult();
	problem.setDetail(detail);
	problem.setInstance(instance);
	problem.setStatus(status);
	problem.setTitle(title);
	problem.setType(type);
	return problem;
    }

    private String getIdPratica(IstanzeListHelper item) {

	return item.getIdcomune() + "-" + item.getCodiceistanza().longValue();
    }

    private String getLinkPratica(PraticaRestBean praticaRestBean, IstanzeListHelper istanzaHelper, String idcomunealias) {

	String linkPratica = "";
	Properties deployProperties = WebConstants.getDeployProperties();
	if (StringUtils.isNotBlank(istanzaHelper.getUuid())) {
	    String linkBase = deployProperties.getProperty("areariservata.url.visura");
	    if (StringUtils.isNotBlank(linkBase)) {
		linkPratica = linkBase.replaceAll("\\{IDCOMUNEALIAS\\}", idcomunealias).replaceAll("\\{SOFTWARE\\}", istanzaHelper.getSoftware())
			.replaceAll("\\{UUID\\}", istanzaHelper.getUuid());
	    } else {
		log.warn("getLinkPratica==>parametro areariservata.url.visura in API-BACKEND non impostato ");
	    }
	}
	return linkPratica;
    }

    private void populateMittentiDestinatari(InserimentoPraticaRequest req) {

	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA);
	String valore = "";
	if (vert != null) {
	    if (StringUtils.isNotBlank(vert.getValore())) {
		valore = StringUtils.trim(vert.getValore());
	    }
	}
	if (StringUtils.isBlank(valore) || !Utilities.isInteger(valore)) {
	    log.error("Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
		      WebConstants.VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA);
	    throw new InvalidConfigurationException("Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
						    WebConstants.VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA);
	}
	Amministrazioni amm = amministrazioniService.findById(new PkId(Integer.parseInt(valore)));
	boolean valido = false;
	if (amm != null && StringUtils.isNotBlank(amm.getStcIdnodo()) && StringUtils.isNotBlank(amm.getStcIdente())
		&& StringUtils.isNotBlank(amm.getStcIdsportello())) {
	    valido = true;
	}
	if (!valido) {
	    log.error("L'amministrazione configurata nella regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
		      WebConstants.VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA + " con valore " + valore + " non è valida");
	    throw new InvalidConfigurationException("L'amministrazione configurata nella regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
						    WebConstants.VERTICALIZZAZIONE_STC_AMM_DEST_INVIA_ZIP_PRATICA + " con valore " + valore +
						    " non è valida");
	}
	SportelloType sd = new SportelloType();
	sd.setIdNodo(amm.getStcIdnodo());
	sd.setIdEnte(amm.getStcIdente());
	sd.setIdSportello(amm.getStcIdsportello());
	req.setSportelloDestinatario(sd);
	// mittente dovrebbe essere selezionato correttamente
	vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO_ENTE_NON_LOCALE);
	valore = "";
	if (vert != null) {
	    if (StringUtils.isNotBlank(vert.getValore())) {
		valore = StringUtils.trim(vert.getValore());
	    }
	}
	if (StringUtils.isBlank(valore) || !Utilities.isInteger(valore)) {
	    log.error("Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
		      WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO_ENTE_NON_LOCALE);
	    throw new InvalidConfigurationException("Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." +
						    WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO_ENTE_NON_LOCALE);
	}
	req.getSportelloMittente().setIdNodo(valore);
    }

    private void populateResult(RiferimentiPraticaSTCRestBean result, RiferimentiPraticaType rifPratica, InserimentoPraticaRequest request) {

	result.setId_pratica(rifPratica.getIdPratica());
	result.setNumero_pratica(rifPratica.getNumeroPratica());
	result.setNumero_protocollo_generale(rifPratica.getNumeroProtocolloGenerale());
	XMLGregorianCalendar dataPratica = rifPratica.getDataPratica();
	if (dataPratica != null) {
	    Date d = dataPratica.toGregorianCalendar().getTime();
	    String data = Utilities.formatDate(d, false);
	    result.setData_pratica(data);
	}
	XMLGregorianCalendar dataProtocolloPratica = rifPratica.getDataProtocolloGenerale();
	if (dataProtocolloPratica != null) {
	    Date d = dataProtocolloPratica.toGregorianCalendar().getTime();
	    String data = Utilities.formatDate(d, false);
	    result.setData_protocollo_generale(data);
	}
	result.setDestinatario(request.getSportelloDestinatario());
	result.setMittente(request.getSportelloMittente());
    }

    private void preparaDomanda(InserimentoPraticaRequest request, File unzipTo, String numeroProtocollo, Date dataProtocollo) throws Exception {

	DettaglioPraticaType dp = request.getDettaglioPratica();
	List<DocumentiType> docs = dp.getDocumenti();
	// File fileFolder = new File(unzipTo);
	for (DocumentiType d : docs) {
	    impostaDocumento(d, unzipTo);
	}
	List<ProcedimentoType> procedimenti = dp.getProcedimenti();
	for (ProcedimentoType p : procedimenti) {
	    List<DocumentiType> documenti = p.getDocumenti();
	    for (DocumentiType d : documenti) {
		impostaDocumento(d, unzipTo);
	    }
	}
	List<ProcuraType> procure = dp.getProcure();
	for (ProcuraType pt : procure) {
	    DocumentiType documentoIdentita = pt.getDocumentoIdentita();
	    impostaDocumento(documentoIdentita, unzipTo);
	    DocumentiType procura = pt.getProcura();
	    impostaDocumento(procura, unzipTo);
	}
	//
	if (StringUtils.isNotBlank(numeroProtocollo)) {
	    ParametroType ns = new ParametroType();
	    ns.setNome(NlaHelperService.ALTRI_DATI_ISTANZE_NUMERO_PROTOCOLLO_MITTENTE);
	    ValoreParametroType vp = new ValoreParametroType();
	    vp.setCodice(numeroProtocollo);
	    vp.setDescrizione(numeroProtocollo);
	    ns.getValore().add(vp);
	    request.getDettaglioPratica().getAltriDati().add(ns);
	    dp.setNumeroProtocolloGenerale(numeroProtocollo);
	}
	if (dataProtocollo != null) {
	    ParametroType ns2 = new ParametroType();
	    ns2.setNome(NlaHelperService.ALTRI_DATI_ISTANZE_DATA_PROTOCOLLO_MITTENTE);
	    ValoreParametroType vp22 = new ValoreParametroType();
	    vp22.setCodice(Utilities.formatDate(dataProtocollo, false));
	    vp22.setDescrizione(vp22.getCodice());
	    ns2.getValore().add(vp22);
	    request.getDettaglioPratica().getAltriDati().add(ns2);
	    dp.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
	}
	//
    }

    private File getFileOggetto(String fileName, File fileFolder) {

	File[] listFiles = fileFolder.listFiles();
	for (File file : listFiles) {
	    if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
		return file;
	    }
	}
	return null;
    }
    //    private File getFileOggetto(String codiceOggetto, File fileFolder) {
    //
    //	File[] listFiles = fileFolder.listFiles();
    //	for (File file : listFiles) {
    //	    if (file.isDirectory() && file.getName().equals(codiceOggetto)) {
    //		File[] listFiles2 = file.listFiles();
    //		for (File file2 : listFiles2) {
    //		    if (file2.isFile()) {
    //			return file2;
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }

    private void impostaDocumento(DocumentiType d, File filesFolder) {

	if (d != null && d.getAllegati() != null) {
	    AllegatiType a = d.getAllegati();
	    String fName = a.getAllegato();
	    // String id = a.getId();
	    // id = id.replace(CODICEOGGETTO_HASH, "");
	    // cercare il file nella directory 
	    // files / codiceoggetto
	    File f = getFileOggetto(fName, filesFolder);
	    if (f == null || !f.exists()) {
		log.error("Il file con id {} del documento {} non è stato trovato nella cartella {}",
			new Object[] { a.getId(), d.getId(), filesFolder });
		throw new RuntimeException("Il File con id " + a.getId() + " del documento con identificativo " + d.getId() +
					   "non e' stato trovato tra i file della domanda");
	    }
	    AllegatoBinarioType file = a.getFile();
	    if (file == null) {
		file = new AllegatoBinarioType();
		file.setFileName(a.getAllegato());
	    }
	    file.setBinaryData(Utilities.fileToDataHandler(f));
	    a.setFile(file);
	    d.setAllegati(a);
	}
    }

    protected void setORMHelper(SportelloType sportello) throws Exception {

	Properties connProps = externalDBResolver.getConnectionProperties(sportello.getIdEnte());
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(sportello.getIdSportello());
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login();
    }

    protected void setORMHelper(String currSoftware) throws Exception {

	Properties connProps = externalDBResolver.getConnectionProperties(ORMHelper.getIdcomuneAlias());
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(currSoftware);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login();
    }

    private void login() {

	String userid = null;
	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    UserDetails user = null;
	    if (vParam != null) {
		userid = StringUtils.trim(vParam.getValore());
		log.debug("login(userid:{}) recuperato dalla verticalizzazione WS_LOGIN", userid);
		if (StringUtils.isNotBlank(userid)) {
		    user = userSecurityService.loadUserByUsername(userid);
		}
	    }
	    if (user == null) {
		user = userSecurityService.loadAdministratorUser();
		userid = user.getUsername();
	    }
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	    log.info("login(userid:{})", userid);
	} catch (Exception e) {
	    log.error("login(userid:{}) idcomunealias:{}", new Object[] { userid, ORMHelper.getIdcomuneAlias(), e });
	    throw new RuntimeException("Errore durante la login alle API: " + e.getMessage(), e);
	}
    }

    /**
     * cerca il file pratica-8087966e-6cfd-49b9-ba3f-41f38fc286be7055394449435710236.xml nella directory tmpFolder
     * 
     * @param tmpFolder
     * @return
     * @throws Exception
     */
    private InserimentoPraticaRequest getPraticaXml(File tmpFolder) throws Exception {

	File fileXml = null;
	File[] listFiles = tmpFolder.listFiles();
	for (File file : listFiles) {
	    if (file.isFile() && file.getName().startsWith("pratica-") && file.getName().endsWith(".xml")) {
		fileXml = file;
		break;
	    }
	}
	if (fileXml == null) {
	    throw new RuntimeException("File pratica xml non trovato");
	}
	FileInputStream fis = new FileInputStream(fileXml);
	InserimentoPraticaRequest result = (InserimentoPraticaRequest) Utilities.unMarshallFromStream(fis, InserimentoPraticaRequest.class);
	fis.close();
	return result;
    }

    private CodiceDescrizioneBean validaContenutoFile(File unzipTo) {

	CodiceDescrizioneBean errore = null;
	// 1 cerca il file della domanda
	// 2 verifica la presenza di tutti i file dichiarati nella domanda
	return errore;
    }

    private CodiceDescrizioneBean populateErrore(String codice, String descrizioneErrore, Exception e) {

	CodiceDescrizioneBean errore = new CodiceDescrizioneBean();
	errore.setCodice(codice);
	if (e != null) {
	    descrizioneErrore += "\n " + e.getMessage();
	}
	errore.setDescrizione(descrizioneErrore);
	return errore;
    }

    private Set<String> result = new HashSet<String>();

    private Set<String> getIdComuneAttivi() {

	if (result.isEmpty()) {
	    try {
		GetSecurityListRequest securityListRequest = securityListRequest(null);
		GetSecurityListResponse securityListResponse = securityListResponse(securityListRequest);
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			GetDbConnectionInfoRequest dbConnectionInfoRequest = dbConnectionInfoRequest(securityListRespItem);
			GetDbConnectionInfoResponse dbConnectionInfoResponse = dbConnectionInfoResponse(dbConnectionInfoRequest);
			String idcomune = dbConnectionInfoResponse.getIdComune();
			result.add(idcomune);
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return this.result;
    }

    public String getIdcomuneAlias(String idcomune) {

	if (idcomuneAliasMap == null) {
	    idcomuneAliasMap = new HashMap<String, String>();
	}
	String alias = idcomuneAliasMap.get(idcomune);
	if (StringUtils.isNotBlank(alias)) {
	    return alias;
	} else {
	    try {
		GetSecurityListRequest securityListRequest = securityListRequest(null);
		GetSecurityListResponse securityListResponse = securityListResponse(securityListRequest);
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			GetDbConnectionInfoRequest dbConnectionInfoRequest = dbConnectionInfoRequest(securityListRespItem);
			GetDbConnectionInfoResponse dbConnectionInfoResponse = dbConnectionInfoResponse(dbConnectionInfoRequest);
			String idcomunePerAlias = dbConnectionInfoResponse.getIdComune();
			if (idcomune.equalsIgnoreCase(idcomunePerAlias)) {
			    alias = dbConnectionInfoResponse.getAlias();
			}
			idcomuneAliasMap.put(idcomunePerAlias, securityListRespItem.getAlias());
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return alias;
    }

    private GetSecurityListRequest securityListRequest(String idComuneAlias) {

	GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
	securityListRequest.setAlias(idComuneAlias);
	return securityListRequest;
    }

    private GetSecurityListResponse securityListResponse(GetSecurityListRequest securityListRequest) throws Exception {

	return securityWSClient.getWsPort().getSecurityList(securityListRequest);
    }

    private GetDbConnectionInfoRequest dbConnectionInfoRequest(SecurityListType securityListItemType) {

	GetDbConnectionInfoRequest dbConnectionInfoRequest = new GetDbConnectionInfoRequest();
	dbConnectionInfoRequest.setAlias(securityListItemType.getAlias());
	dbConnectionInfoRequest.setAmbiente(AmbienteType.JAVA);
	return dbConnectionInfoRequest;
    }

    private GetDbConnectionInfoResponse dbConnectionInfoResponse(GetDbConnectionInfoRequest dbConnectionInfoRequest) throws Exception {

	return securityWSClient.getWsPort().getDbConnectionInfo(dbConnectionInfoRequest);
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	idcomuneAliasMap = new HashMap<String, String>();
    }

    @Override
    public DownloadPraticaZipHelper scaricaZipPratica(String uuid, boolean includiDocumentiDeiMovimenti) throws Exception {

	Istanze i = istanzeService.findByUiid(uuid);
	DownloadPraticaZipHelper hlp = new DownloadPraticaZipHelper();
	if (i == null) {
	    throw new FunzioneBusinessRemotaException("Istanza non trovata con id " + uuid);
	}
	setORMHelper(i.getSoftware().getCodice());
	hlp.setNomeFile(resolveNomeFileZip(i));
	RichiestaPraticaNLAResponse res = nlaHelperService.populateRichiestaPraticaNLAResponse(i, true, null);
	if (res == null || !res.getDettaglioErrore().isEmpty()) {
	    throw new FunzioneBusinessRemotaException("Errore nel caricamento della pratica id " + uuid);
	}
	String directoryTemporanea = Utilities.getSystemTempDir() + File.separator + "pratichezip" + File.separator + ORMHelper.getIdcomuneAlias() +
				     File.separator + uuid + File.separator + System.currentTimeMillis();
	String directoryTemporaneaFile = directoryTemporanea;
	File tmpFolder = new File(directoryTemporanea);
	tmpFolder.mkdirs();
	File tmpFolderFiles = new File(directoryTemporaneaFile);
	tmpFolderFiles.mkdirs();
	DettaglioPraticaType dettaglioPratica = res.getDettaglioPratica().getDettaglioPratica();
	Map<Integer, File> oggettiHash = new HashMap<Integer, File>();
	List<DocumentiType> documenti = dettaglioPratica.getDocumenti();
	String vMetadato = alberoprocMetadatiService.findValoreByInterventoRicorsivoEChiave(i.getAlberoproc().getId().getCodice(),
		AlberoprocMetadatiEnum.DOWNLOAD_PRATICA_ZIP_NOMEFILE.value());
	Set<String> nomiFilePresenti = new HashSet<String>();
	for (DocumentiType documentiType : documenti) {
	    gestAllegato(i, documentiType, oggettiHash, tmpFolderFiles, vMetadato, nomiFilePresenti);
	}
	List<ProcuraType> procure = dettaglioPratica.getProcure();
	for (ProcuraType procuraType : procure) {
	    DocumentiType documentoIdentita = procuraType.getDocumentoIdentita();
	    gestAllegato(i, documentoIdentita, oggettiHash, tmpFolderFiles, vMetadato, nomiFilePresenti);
	    DocumentiType procura = procuraType.getProcura();
	    gestAllegato(i, procura, oggettiHash, tmpFolderFiles, vMetadato, nomiFilePresenti);
	}
	List<ProcedimentoType> procedimenti = dettaglioPratica.getProcedimenti();
	for (ProcedimentoType p : procedimenti) {
	    List<DocumentiType> d = p.getDocumenti();
	    for (DocumentiType documentiType : d) {
		gestAllegato(i, documentiType, oggettiHash, tmpFolderFiles, vMetadato, nomiFilePresenti);
	    }
	}
	List<DettaglioAttivitaType> listaAttivita = res.getDettaglioPratica().getListaAttivita();
	for (DettaglioAttivitaType da : listaAttivita) {
	    if (da != null) {
		for (DocumentiType d : da.getDocumenti()) {
		    gestAllegato(i, d, oggettiHash, tmpFolderFiles, vMetadato, nomiFilePresenti);
		}
	    }
	}
	InserimentoPraticaRequest req = new InserimentoPraticaRequest();
	populateMittentiDestinatariZipPratica(req);
	req.setDettaglioPratica(dettaglioPratica);
	String pratica = Utilities.marshallObject(req);
	File in = File.createTempFile(resolveNomeFile(i, "pratica-" + uuid, vMetadato), ".xml", tmpFolder);
	OutputStream fileIn = new FileOutputStream(in);
	fileIn.write(pratica.getBytes());
	fileIn.close();
	File out = File.createTempFile("pratica-" + uuid, ".zip");
	OutputStream writeTo = new FileOutputStream(out);
	Utilities.zipTo(tmpFolder, writeTo);
	InputStream is = new FileInputStream(out);
	deleteTempFiles(tmpFolder);
	hlp.setContenuto(is);
	return hlp;
    }

    private void populateMittentiDestinatariZipPratica(InserimentoPraticaRequest req) {

	Verticalizzazioniparametri vert = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	String valore = "";
	if (vert != null && StringUtils.isNotBlank(vert.getValore())) {
	    valore = vert.getValore();
	}
	if (StringUtils.isBlank(valore)) {
	    log.error(
		    "Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." + WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	    throw new InvalidConfigurationException(
		    "Non è stata configurata la regola " + WebConstants.VERTICALIZZAZIONE_STC + "." + WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	}
	SportelloType sm = new SportelloType();
	sm.setIdNodo(valore);
	sm.setIdEnte(ORMHelper.getIdcomuneAlias());
	sm.setIdSportello(ORMHelper.getSoftware());
	req.setSportelloMittente(sm);
    }

    private void deleteTempFiles(File tmpFolder) {

	File[] f = tmpFolder.listFiles();
	for (File file : f) {
	    if (file.isDirectory()) {
		deleteTempFiles(file);
	    } else {
		file.delete();
	    }
	}
	tmpFolder.delete();
    }

    private void gestAllegato(Istanze i, DocumentiType documentiType, Map<Integer, File> oggettiHash, File tmpFolderFiles, String metadatoNomeFile,
	    Set<String> nomiFilePresenti) throws IOException {

	if (documentiType == null || documentiType.getAllegati() == null || StringUtils.isBlank(documentiType.getAllegati().getId())
		|| !Utilities.isInteger(documentiType.getAllegati().getId().trim())) {
	    return;
	}
	Integer codiceOggetto = Integer.parseInt(documentiType.getAllegati().getId().trim());
	if (oggettiHash.get(codiceOggetto) == null) { // altrimenti è già scritto e non lo riscrivo
	    String nomefile = resolveNomeFileAllegato(i, codiceOggetto, metadatoNomeFile); // 	    
	    if (nomiFilePresenti.contains(nomefile)) {
		nomefile = rendiUnivocoNomeFile(nomefile);
		nomiFilePresenti.add(nomefile);
	    }
	    InputStream is = oggettiService.getOggettoAsInputStream(codiceOggetto);
	    File tmpFolder = new File(tmpFolderFiles.getAbsolutePath());
	    tmpFolder.mkdirs();
	    File f = new File(tmpFolder, nomefile);
	    try {
		FileOutputStream fos = new FileOutputStream(f);
		IOUtils.copyLarge(is, fos);
		is.close();
		fos.close();
	    } catch (Exception e) {
	    }
	    oggettiHash.put(codiceOggetto, f);
	}
    }

    private String rendiUnivocoNomeFile(String nomeFile) {

	if (nomeFile.indexOf(".") < 0) {
	    return nomeFile + "-" + Utilities.generaPassword(3);
	}
	return nomeFile.substring(0, nomeFile.indexOf(".")) + "-" + Utilities.generaPassword(3) + nomeFile.substring(nomeFile.indexOf("."));
    }

    private String resolveNomeFileAllegato(Istanze i, Integer codiceOggetto, String vMetadato) {

	return resolveNomeFile(i, oggettiService.findNomeById(new PkId(codiceOggetto)), vMetadato);
    }

    private String resolveNomeFile(Istanze i, String nomeFile, String vMetadato) {

	if (StringUtils.isNotBlank(vMetadato)) {
	    nomeFile = sostituisciSegnapostoZipVariabili(vMetadato, i) + "-" + nomeFile;
	}
	return nomeFile;
    }

    private String resolveNomeFileZip(Istanze i) {

	String nomeFileZip = "pratica-" + i.getUuid() + ".zip";
	String vMetadato = alberoprocMetadatiService.findValoreByInterventoRicorsivoEChiave(i.getAlberoproc().getId().getCodice(),
		AlberoprocMetadatiEnum.DOWNLOAD_PRATICA_ZIP_NOMEFILE.value());
	if (StringUtils.isNotBlank(vMetadato)) {
	    nomeFileZip = sostituisciSegnapostoZipVariabili(vMetadato, i) + "-" + nomeFileZip;
	}
	return nomeFileZip;
    }

    private String sostituisciSegnapostoZipVariabili(String in, Istanze i) {

	String numeroprotocollo = StringUtils.defaultString(i.getNumeroprotocollo());
	if (StringUtils.isNotBlank(numeroprotocollo) && numeroprotocollo.length() < 8) {
	    numeroprotocollo = StringUtils.leftPad(numeroprotocollo, 8, "0");
	}
	String dataProtocollo = "";
	String annoProtocollo = "";
	if (i.getDataprotocollo() != null) {
	    dataProtocollo = Utilities.formatDate(i.getDataprotocollo(), "yyyyMMdd");
	    annoProtocollo = Utilities.formatDate(i.getDataprotocollo(), "yyyy");
	}
	in = in.replace("{protocollo_istanza}", numeroprotocollo);
	in = in.replace("{data_protocollo_istanza}", dataProtocollo);
	in = in.replace("{anno_protocollo_istanza}", annoProtocollo);
	in = in.replace("{uuid_pratica}", StringUtils.defaultString(i.getUuid()));
	if (in.startsWith("_")) {
	    in = in.substring(1);
	}
	if (in.endsWith("_")) {
	    in = in.substring(0, (in.length() - 1));
	}
	return Utilities.eliminaCaratteriNonAscii(in);
    }

    public static void main(String[] args) {

	ApiProducerServiceImpl t = new ApiProducerServiceImpl();
	String segnaposto = "{protocollo_istanza}_MUDE_REGIONALE";
	Istanze i = new Istanze();
	System.out.println(t.sostituisciSegnapostoZipVariabili(segnaposto, i));
	i.setNumeroprotocollo("345");
	i.setUuid("345345345-345345345345-345345345345");
	System.out.println(t.sostituisciSegnapostoZipVariabili(segnaposto, i));
	i.setNumeroprotocollo(null);
	System.out.println(t.sostituisciSegnapostoZipVariabili(segnaposto, i));
	i.setNumeroprotocollo("123456789");
	System.out.println(t.sostituisciSegnapostoZipVariabili(segnaposto, i));
	String linkBase = "{IDCOMUNEALIAS}_{SOFTWARE}_{UUID}_{IDCOMUNEALIAS}_{SOFTWARE}_{UUID}";
	System.out.println(linkBase.replace("{IDCOMUNEALIAS}", "E256").replace("{SOFTWARE}", "CO").replace("{UUID}", "213456789-987654321"));
	System.out.println(t.rendiUnivocoNomeFile("E256_CO_213456789-987654321_E256_CO_213456789-987654321"));
	for (int x = 0; x < 10; x++) {
	    System.out.println(t.rendiUnivocoNomeFile("E256_CO_213456789-987654321_E256_CO_213456789-987654321.zip.p7m"));
	}
    }
}
