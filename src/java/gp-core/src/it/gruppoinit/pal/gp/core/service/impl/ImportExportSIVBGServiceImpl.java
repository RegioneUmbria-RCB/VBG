package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.AlberoprocEndoDAO;
import it.gruppoinit.pal.gp.core.dao.AlberoprocLeggiDAO;
import it.gruppoinit.pal.gp.core.dao.AllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2BasetipitestoDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiproprietaDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidtestiDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2ModellitDAO;
import it.gruppoinit.pal.gp.core.dao.InventarioprocLeggiDAO;
import it.gruppoinit.pal.gp.core.dao.InventarioprocTipititoloDAO;
import it.gruppoinit.pal.gp.core.dao.Inventarioprocdyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentioneriDAO;
import it.gruppoinit.pal.gp.core.dao.LeggiDAO;
import it.gruppoinit.pal.gp.core.dao.LeggitipiDAO;
import it.gruppoinit.pal.gp.core.dao.NaturaendoDAO;
import it.gruppoinit.pal.gp.core.dao.NormativeDAO;
import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.dao.TipiMovimentoDAO;
import it.gruppoinit.pal.gp.core.dao.TipicausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.TipicontromovimentoDAO;
import it.gruppoinit.pal.gp.core.dao.TipiendoDAO;
import it.gruppoinit.pal.gp.core.dao.TipifamiglieendoDAO;
import it.gruppoinit.pal.gp.core.dao.TipiprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.TipiprocedureavvioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocChildrenCommand;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.ImportExportSIVBGCommand;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.RepositoryVersion;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ElementoAggregazione;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ElementoRepertorio;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.Repertorio;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AdempimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AllegatoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.AmministrazioneType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoDinamicoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoSchedaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CampoStaticoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.CausaliOneriType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ContenutoBusta;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.DeterminazioneEsitoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.FamigliaProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoDocumentiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoSchedeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.InterventoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.LeggeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.LeggiTipiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.NaturaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.NormativaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoAllegatiType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoLeggeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoOneriType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoSchedeType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProceduraType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProcedureAvvioType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ProprietaCampoDinamicoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.PubblicaDocumentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.PubblicaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.SchedaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoCampoStaticoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoContromovimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoFirmaType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoMovimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipoTitoloType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.TipologiaProcedimentoType;
import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ValoreParametroType;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ImportExportSIVBGService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.helper.ImportExportSIVBGServiceHelper;
import it.gruppoinit.pal.gp.core.ws.client.ImportExportSIVBGWsClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

@Service
public class ImportExportSIVBGServiceImpl implements ImportExportSIVBGService {

    private static final Logger log = LoggerFactory.getLogger(ImportExportSIVBGServiceImpl.class);
    @Autowired
    private ImportExportSIVBGWsClient importExportSIVBGWsClient;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocDAO alberoprocDAO;
    @Autowired
    private AlberoprocDocumentiDAO alberoprocDocumentiDAO;
    @Autowired
    private TipiprocedureDAO tipiprocedureDAO;
    @Autowired
    private AlberoprocLeggiDAO alberoprocLeggiDAO;
    @Autowired
    private AlberoprocEndoDAO alberoprocEndoDAO;
    @Autowired
    private AlberoprocDyn2modellitDAO alberoprocDyn2modellitDAO;
    @Autowired
    private InventarioprocedimentiDAO inventarioprocedimentiDAO;
    @Autowired
    private AmministrazioniDAO amministrazioniDAO;
    @Autowired
    private TipiMovimentoDAO tipimovimentoDAO;
    @Autowired
    private TipiprocedureavvioDAO tipiprocedureavvioDAO;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimenti;
    @Autowired
    private TipifamiglieendoDAO tipifamiglieendoDAO;
    @Autowired
    private TipiendoDAO tipiendoDAO;
    @Autowired
    private NaturaendoDAO naturaendoDAO;
    @Autowired
    private AllegatiDAO allegatiDAO;
    @Autowired
    private OggettiDAO oggettiDAO;
    @Autowired
    private InventarioprocedimentioneriDAO inventarioprocedimentioneriDAO;
    @Autowired
    private TipicausalioneriDAO tipicausalioneriDAO;
    @Autowired
    private InventarioprocLeggiDAO inventarioprocLeggiDAO;
    @Autowired
    private LeggiDAO leggiDAO;
    @Autowired
    private LeggitipiDAO leggitipiDAO;
    @Autowired
    private NormativeDAO normativeDAO;
    @Autowired
    private TipicontromovimentoDAO tipicontromovimentoDAO;
    @Autowired
    private InventarioprocTipititoloDAO inventarioprocTipititoloDAO;
    @Autowired
    private Dyn2ModellitDAO dyn2ModellitDAO;
    @Autowired
    private Dyn2ModellidDAO dyn2ModellidDAO;
    @Autowired
    private Dyn2ModellidtestiDAO dyn2ModellidtestiDAO;
    @Autowired
    private Dyn2BasetipitestoDAO dyn2BasetipitestoDAO;
    @Autowired
    private Dyn2CampiDAO dyn2CampiDAO;
    @Autowired
    private Inventarioprocdyn2modellitDAO inventarioprocdyn2modellitDAO;
    @Autowired
    private Dyn2CampiproprietaDAO dyn2CampiproprietaDAO;

    @Override
    public void getListaVersioni(String url, ImportExportSIVBGCommand command) {

	try {
	    ArrayList<RepositoryVersion> listaVersioniLocali = (ArrayList<RepositoryVersion>) importExportSIVBGWsClient.getVersione(url,
		    command.getDescrizioneServizioExport());
	    command.setListaVersioniLocali(listaVersioniLocali);
	} catch (Exception e) {
	}
	try {
	    ArrayList<RepositoryVersion> listaVersioniRegionali = (ArrayList<RepositoryVersion>) importExportSIVBGWsClient.getVersioneRemota(url,
		    command.getDescrizioneServizioExport());
	    command.setListaVersioniRegionali(listaVersioniRegionali);
	} catch (Exception e) {
	}
    }

    @Override
    public void exportSIVBG(String url, ImportExportSIVBGCommand command) {

	try {
	    // recupero informazioni da esportare relative all'albero dei procedimenti
	    ArrayList<ElementoAggregazione> listaElementiAggregazione = new ArrayList<ElementoAggregazione>();
	    ContenutoBusta contenuto = buildContenutoExport(null, listaElementiAggregazione);
	    // converto la struttura dati "contenuto" in stringa XML
	    String contenutoString = marshalObject(contenuto, ContenutoBusta.class, true);
	    contenutoString = contenutoString.replaceFirst("<ContenutoBusta xmlns=\"http://vbg.init.it/rte/types\">", "<ContenutoBusta>");
	    contenutoString = contenutoString.replaceAll("\\$", "\\\\\\$");
	    // contenutoString = "<![CDATA[" + contenutoString + "]]>";
	    // costruisco la busta XML con le informazioni recuperate
	    Repertorio repertorio = buildBustaRepertorio(command, contenuto, listaElementiAggregazione);
	    // converto l'elemento "repertorio" in string
	    String bustaXMLString = marshalObject(repertorio, Repertorio.class, false);
	    // faccio il merge dei 2 xml (BUSTA+CONTENUTO)
	    bustaXMLString = bustaXMLString.replaceFirst("</Repertorio>", "<Contenuto>" + contenutoString + "</Contenuto></Repertorio>");
	    // saveToFile(bustaXMLString, "c:\\tmp\\repertorio\\test.xml");
	    // invocazione web service
	    importExportSIVBGWsClient.creaNuovoRepository(url, bustaXMLString);
	} catch (Exception e) {
	    log.error("exportSIVBG: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    @Override
    public void exportSIVBGendo(String url, ImportExportSIVBGCommand command) {

	try {
	    Inventarioprocedimenti endo = inventarioprocedimenti.findById(new PkId(Integer.valueOf(command.getIstanzeFilter()
		    .getInventarioprocedimenti().getId().getCodice())));
	    if (endo != null) {
		String codiceEndo = String.valueOf(command.getIstanzeFilter().getInventarioprocedimenti().getId().getCodice());
		ArrayList<ElementoAggregazione> listaElementiAggregazione = new ArrayList<ElementoAggregazione>();
		ContenutoBusta contenuto = buildContenutoExport(codiceEndo, listaElementiAggregazione);
		// converto la struttura dati "contenuto" in stringa XML
		String contenutoString = marshalObject(contenuto, ContenutoBusta.class, true);
		contenutoString = contenutoString.replaceFirst("<ContenutoBusta xmlns=\"http://vbg.init.it/rte/types\">", "<ContenutoBusta>");
		// costruisco la busta XML con le informazioni recuperate
		Repertorio repertorio = buildBustaRepertorio(command, contenuto, null);
		// converto l'elemento "repertorio" in string
		String bustaXMLString = marshalObject(repertorio, Repertorio.class, false);
		// faccio il merge dei 2 xml (BUSTA+CONTENUTO)
		bustaXMLString = bustaXMLString.replaceFirst("</Repertorio>", "<Contenuto>" + contenutoString + "</Contenuto></Repertorio>");
		// saveToFile(bustaXMLString, "c:\\tmp\\repertorio\\test_endo.xml");
		// invocazione web service
		importExportSIVBGWsClient.uploadProcedimento(url, bustaXMLString);
	    }
	} catch (Exception e) {
	    log.error("exportSIVBGendo: {}", new Object[] { e });
	    throw new RuntimeException(e.getMessage());
	}
    }

    private void saveToFile(String bustaXMLString, String nomefile) {

	try {
	    FileOutputStream fos = new FileOutputStream(nomefile);
	    fos.write(bustaXMLString.getBytes());
	    fos.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static String getToFile(String nomeFile) {

	byte[] b = null;
	try {
	    File f = new File(nomeFile);
	    FileInputStream fis = new FileInputStream(f);
	    b = new byte[(int) f.length()];
	    fis.read(b);
	    fis.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return new String(b);
    }

    @Override
    public void importSIVBG(String url, ImportExportSIVBGCommand command) {

	String repository = getRemoteRepository(url, command);
	//String repository = getToFile("C:\\tmp\\repertorio\\test.xml");
	repository = repository.replaceFirst("<Contenuto>", "<Contenuto><![CDATA[");
	repository = repository.replaceFirst("</Contenuto>", "]]></Contenuto>");
	repository = repository.replaceFirst("<ContenutoBusta>", "<ContenutoBusta  xmlns=\"http://vbg.init.it/rte/types\">");
	Repertorio rep = (Repertorio) unmarshalObject(repository,
		it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory.class);
	Element elem = (Element) rep.getContenuto();
	String text = elem.getTextContent();
	ContenutoBusta contenutoBusta = (ContenutoBusta) unmarshalObject(text,
		it.gruppoinit.pal.gp.core.schema.importexport.sivbg.content.ObjectFactory.class);
	insertContenutoBusta(contenutoBusta, command.isAttivaUpdateDati());
    }

    private String getRemoteRepository(String url, ImportExportSIVBGCommand command) {

	String repository = "";
	if (command.getSceltaVers().startsWith("R")) {
	    // l'utente ha scelta una versione dal repository Regioanle
	    boolean trovato = false;
	    String[] versioni = command.getSceltaVers().split("\\.");
	    String versioneReg = versioni[1];
	    String versioneLoc = versioni[2];
	    RepositoryVersion rvScelta = null;
	    Iterator<RepositoryVersion> it = command.getListaVersioniRegionali().iterator();
	    while (it.hasNext() && !trovato) {
		rvScelta = it.next();
		if (rvScelta.getLocalVers() != null && rvScelta.getLocalVers().getValue() != null
			&& rvScelta.getLocalVers().getValue().equalsIgnoreCase(versioneLoc) && rvScelta.getRemoteVers() != null
			&& rvScelta.getRemoteVers().getValue() != null && rvScelta.getRemoteVers().getValue().equalsIgnoreCase(versioneReg)) {
		    trovato = true;
		}
	    }
	    if (trovato) {
		repository = importExportSIVBGWsClient.downloadRepositoryByVersion(url, rvScelta);
		//repository = importExportSIVBGWsClient.downloadRepository(url, rvScelta.getServizio().getValue());
	    } else {
		log.error("getRemoteRepository: Non è stato possibile individuare la versione di repository da scaricare");
		throw new RuntimeException("Nessuna versione di Repository individuata");
	    }
	} else {
	    String[] versioni = command.getSceltaVers().split("\\.");
	    String versioneReg = versioni[0];
	    String versioneLoc = versioni[1];
	    boolean trovato = false;
	    RepositoryVersion rvScelta = null;
	    Iterator<RepositoryVersion> it = command.getListaVersioniLocali().iterator();
	    while (it.hasNext() && !trovato) {
		rvScelta = it.next();
		if (rvScelta.getLocalVers() != null && rvScelta.getLocalVers().getValue() != null
			&& rvScelta.getLocalVers().getValue().equalsIgnoreCase(versioneLoc) && rvScelta.getRemoteVers() != null
			&& rvScelta.getRemoteVers().getValue() != null && rvScelta.getRemoteVers().getValue().equalsIgnoreCase(versioneReg)) {
		    trovato = true;
		}
	    }
	    if (trovato) {
		repository = importExportSIVBGWsClient.downloadRepositoryByVersionLocale(url, rvScelta);
		//repository = importExportSIVBGWsClient.downloadRepositoryLocale(url,rvScelta.getServizio().getValue());
	    } else {
		log.error("getRemoteRepository: Non è stato possibile individuare la versione di repository da scaricare");
		throw new RuntimeException("Nessuna versione di Repository individuata");
	    }
	}
	return repository;
    }

    private ElementoAggregazione creaElementoAggregazione(it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory fact,
	    Alberoproc alberoproc, String XPath) {

	ElementoAggregazione elementoAggr = new ElementoAggregazione();
	JAXBElement<String> id = fact.createElementoAggregazioneId(String.valueOf(alberoproc.getId().getCodice()));
	JAXBElement<String> nome = fact.createElementoAggregazioneNome(alberoproc.getScDescrizione());
	JAXBElement<String> xpath = fact.createElementoAggregazioneXpath(XPath);
	JAXBElement<String> descrizione = fact.createElementoAggregazioneDescrizione("...descrizione...");
	elementoAggr.getIdAndXpathAndNome().add(id);
	elementoAggr.getIdAndXpathAndNome().add(xpath);
	elementoAggr.getIdAndXpathAndNome().add(nome);
	elementoAggr.getIdAndXpathAndNome().add(descrizione);
	return elementoAggr;
    }

    private ContenutoBusta buildContenutoExport(String codiceEndo, ArrayList<ElementoAggregazione> listaElementiAggregazione) throws Exception {

	ImportExportSIVBGServiceHelper serviceHelper = new ImportExportSIVBGServiceHelper();
	ContenutoBusta contenuto = new ContenutoBusta();
	contenuto.setInterventi(new InterventiType());
	contenuto.setProcedimenti(new ProcedimentiType());
	HashMap<String, ProcedimentoType> mappaProcedimenti = new HashMap<String, ProcedimentoType>();//contenuto.getInterventi().getIntervento();
	java.util.List<InterventoType> listaInterventiSIVBG = contenuto.getInterventi().getIntervento();
	it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory fact = new it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory();
	if (codiceEndo == null) { // recupero tutto l'albero
	    java.util.List<Alberoproc> root = alberoprocService.findRootsAlberoProc();
	    int index = 1;
	    for (Iterator<Alberoproc> iterator = root.iterator(); iterator.hasNext();) {
		Alberoproc alberoproc = (Alberoproc) iterator.next();
		InterventoType interventoSIVBG = serviceHelper.populateSIVBGInterventoType(alberoproc);
		populateMappaProcedimenti(mappaProcedimenti, interventoSIVBG);
		ElementoAggregazione elemAggr = creaElementoAggregazione(fact, alberoproc, "//ContenutoBusta/interventi/intervento[" + index + "]");
		if (alberoproc.getScPadre() != null && alberoproc.getScPadre()) {
		    AlberoprocCommand listaFigli = alberoprocService.findAlberoprocFigli(alberoproc.getScCodice());
		    if (listaFigli.getChildren() != null) {
			interventoSIVBG.setFigli(new InterventiType());
			esploraRicorsivamente(interventoSIVBG.getFigli().getIntervento(), listaFigli, mappaProcedimenti, fact, elemAggr,
				"//ContenutoBusta/interventi/intervento[" + index + "]");
		    }
		}
		listaElementiAggregazione.add(elemAggr);
		listaInterventiSIVBG.add(interventoSIVBG);
		index++;
	    }
	} else { // recupero un endoprocedimento
	    Inventarioprocedimenti endo = inventarioprocedimenti.findById(new PkId(Integer.valueOf(codiceEndo)));
	    populateMappaProcedimenti(mappaProcedimenti, endo);
	}
	Set<String> keySet = mappaProcedimenti.keySet();
	Iterator<String> it = keySet.iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    contenuto.getProcedimenti().getProcedimento().add((ProcedimentoType) mappaProcedimenti.get(key));
	}
	return contenuto;
    }

    private void esploraRicorsivamente(java.util.List<InterventoType> figliInterventoSIVBG, AlberoprocCommand listaFigli,
	    HashMap<String, ProcedimentoType> mappaProcedimenti, it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory fact,
	    ElementoAggregazione elementAggregazione, String baseXPath) throws Exception {

	ImportExportSIVBGServiceHelper serviceHelper = new ImportExportSIVBGServiceHelper();
	int index = 1;
	for (Iterator iterator = listaFigli.getChildren().iterator(); iterator.hasNext();) {
	    AlberoprocChildrenCommand child = (AlberoprocChildrenCommand) iterator.next();
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(child.get_reference()));
	    if (alberoproc != null) {
		ElementoAggregazione subElementoAggr = creaElementoAggregazione(fact, alberoproc, baseXPath + "/figli/intervento[" + index + "]");
		InterventoType interventoSIVBG = serviceHelper.populateSIVBGInterventoType(alberoproc);
		log.debug(interventoSIVBG.getCodice()+" - "+interventoSIVBG.getDescrizione());
		populateMappaProcedimenti(mappaProcedimenti, interventoSIVBG);
		if (alberoproc.getScPadre()) {
		    AlberoprocCommand listaFigli_ = alberoprocService.findAlberoprocFigli(alberoproc.getScCodice());
		    if (listaFigli_.getChildren() != null) {
			interventoSIVBG.setFigli(new InterventiType());
			esploraRicorsivamente(interventoSIVBG.getFigli().getIntervento(), listaFigli_, mappaProcedimenti, fact, subElementoAggr,
				baseXPath + "/figli/intervento[" + index + "]");
		    }
		}
		JAXBElement<ElementoAggregazione> subElem = fact.createElementoAggregazioneElementoAggregazione(subElementoAggr);
		elementAggregazione.getIdAndXpathAndNome().add(subElem);
		index++;
		figliInterventoSIVBG.add(interventoSIVBG);
	    }
	}
    }

    private void populateMappaProcedimenti(HashMap<String, ProcedimentoType> mappaProcedimenti, InterventoType interventoSIVBG) {

	ImportExportSIVBGServiceHelper serviceHelper = new ImportExportSIVBGServiceHelper();
	if (interventoSIVBG != null && interventoSIVBG.getAdempimenti() != null) {
	    for (AdempimentoType adempimentoSIVBG : interventoSIVBG.getAdempimenti()) {
		String codiceProcedimento = adempimentoSIVBG.getCodiceProcedimento();
		if (!mappaProcedimenti.containsKey(codiceProcedimento)) {
		    Inventarioprocedimenti endo = inventarioprocedimenti.findById(new PkId(Integer.valueOf(codiceProcedimento)));
		    if (endo != null) {
			ProcedimentoType procedimentoSIVBG = serviceHelper.populateSIVBGProcedimentoType(endo);
			mappaProcedimenti.put(codiceProcedimento, procedimentoSIVBG);
		    }
		}
	    }
	}
    }

    private void populateMappaProcedimenti(HashMap<String, ProcedimentoType> mappaProcedimenti, Inventarioprocedimenti endo) {

	ImportExportSIVBGServiceHelper serviceHelper = new ImportExportSIVBGServiceHelper();
	if (endo != null && endo.getId() != null && endo.getId().getCodice() != null) {
	    String codiceProcedimento = String.valueOf(endo.getId().getCodice());
	    if (!mappaProcedimenti.containsKey(codiceProcedimento)) {
		ProcedimentoType procedimentoSIVBG = serviceHelper.populateSIVBGProcedimentoType(endo);
		mappaProcedimenti.put(codiceProcedimento, procedimentoSIVBG);
	    }
	}
    }

    private Repertorio buildBustaRepertorio(ImportExportSIVBGCommand command, ContenutoBusta contenuto,
	    ArrayList<ElementoAggregazione> listaElementiAggregazione) {

	// costruisco la busta XML con le informazioni recuperate
	Repertorio repertorio = new Repertorio();
	repertorio.setDataCreazione(command.getDataExport());
	repertorio.setNoteVersione(command.getNoteVersioneExport());
	repertorio.setServizio(command.getDescrizioneServizioExport());
	Repertorio.IndiceContenuti indiceContenuti = new Repertorio.IndiceContenuti();
	it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory fact = new it.gruppoinit.pal.gp.core.schema.importexport.sivbg.busta.ObjectFactory();
	int index = 1;
	for (ProcedimentoType proc : contenuto.getProcedimenti().getProcedimento()) {
	    ElementoRepertorio elementoRepertorio = new ElementoRepertorio();
	    JAXBElement<String> id = fact.createElementoRepertorioId(proc.getCodice());
	    JAXBElement<String> nome = fact.createElementoRepertorioNome(proc.getProcedimento());
	    JAXBElement<String> xpath = fact.createElementoRepertorioXpath("//ContenutoBusta/procedimenti/procedimento[" + index + "]");
	    JAXBElement<String> descrizione = fact.createElementoRepertorioDescrizione("...descrizione...");
	    elementoRepertorio.getIdAndXpathAndNome().add(id);
	    elementoRepertorio.getIdAndXpathAndNome().add(xpath);
	    elementoRepertorio.getIdAndXpathAndNome().add(nome);
	    elementoRepertorio.getIdAndXpathAndNome().add(descrizione);
	    indiceContenuti.getElementoRepertorioAndElementoAggregazione().add(elementoRepertorio);
	    index++;
	}
	if (listaElementiAggregazione != null) {
	    for (Iterator<ElementoAggregazione> iterator = listaElementiAggregazione.iterator(); iterator.hasNext();) {
		ElementoAggregazione elementoAggregazione = iterator.next();
		indiceContenuti.getElementoRepertorioAndElementoAggregazione().add(elementoAggregazione);
	    }
	}
	repertorio.setIndiceContenuti(indiceContenuti);
	return repertorio;
    }

    private String marshalObject(Object jaxbElement, Class clazz, boolean isFragment) {

	String ret = "";
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    StringWriter stringWriter = new StringWriter();
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment ? Boolean.TRUE : Boolean.FALSE);
	    marshaller.marshal(jaxbElement, stringWriter);
	    ret = stringWriter.toString();
	} catch (Exception e) {
	    log.error("Errore nel metodo marshalObject() per la classe {}: {}", clazz.getName(), e.getMessage());
	}
	return ret;
    }

    private static Object unmarshalObject(String xml, Class objectFactory) {

	Object obj = null;
	try {
	    InputStream is = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
	    JAXBContext context = JAXBContext.newInstance(objectFactory);
	    javax.xml.bind.Unmarshaller u = context.createUnmarshaller();
	    //u.setProperty("com.sun.xml.bind.ObjectFactory",new ObjectFactory());
	    obj = u.unmarshal(is);
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error("Errore nel metodo unmarshalObject() ");
	}
	return obj;
    }

    private void insertContenutoBusta(ContenutoBusta contenutoBusta, boolean attivaUpdateDati) {

	Software software = new Software();
	software.setCodice(ORMHelper.getSoftware());
	populateAndInsertProcedimenti(contenutoBusta, software, attivaUpdateDati);
	for (InterventoType interventoSIVBG : contenutoBusta.getInterventi().getIntervento()) {
	    Alberoproc alberoproc = populateAndInsertIntervento(interventoSIVBG, null, software, true, attivaUpdateDati);
	}
    }

    public Alberoproc populateAndInsertIntervento(InterventoType interventoSIVBG, Alberoproc alberoprocPadre, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Alberoproc alberoproc = null;
	if (interventoSIVBG != null) {
	    alberoproc = new Alberoproc();
	    alberoproc.getId().setCodice(Integer.valueOf(interventoSIVBG.getId()));
	    alberoproc.setScCodice(interventoSIVBG.getCodice());
	    alberoproc.setScDescrizione(interventoSIVBG.getDescrizione());
	    log.debug("Import intervento : "+interventoSIVBG.getCodice()+" - "+interventoSIVBG.getDescrizione());
	    alberoproc.setScNote(interventoSIVBG.getNote());
	    if (interventoSIVBG.getOrdine() != null) {
		alberoproc.setScOrdine(Integer.valueOf(interventoSIVBG.getOrdine()));
	    }
	    HashMap<String, String> mappaTipiMovInseriti = new HashMap<String, String>();
	    HashMap<String, Integer> mappaProcedureInserite = new HashMap<String, Integer>();
	    alberoproc.setTipoProcedura(populateAndInsertTipiprocedure(interventoSIVBG.getProcedura(), software, eseguiQuery, attivaUpdateDati,
		    mappaTipiMovInseriti, mappaProcedureInserite));
	    alberoproc.setScAttivo(interventoSIVBG.isDisabilitato());
	    alberoproc.setAzione(null);
	    alberoproc.setMercato(null);
	    alberoproc.setMercatoUso(null);
	    alberoproc.setRespistruttoria(null);
	    alberoproc.setResponsabile(null);
	    alberoproc.setSoftware(software);
	    alberoproc.setTipologiaregistro(null);
	    alberoproc.setAlberoprocDocumentis(populateAndInsertListaAlberoprocDocumenti(interventoSIVBG.getDocumenti(), alberoproc, software, false,
		    attivaUpdateDati));
	    alberoproc.setAlberoprocDyn2modellits(populateAndInsertListaAlberoprocDyn2modellit(interventoSIVBG.getSchedeDinamiche(), alberoproc,
		    software, false, attivaUpdateDati));
	    alberoproc.setAlberoprocLeggis(populateAndInsertListaAlberoprocLeggi(interventoSIVBG.getLeggi(), alberoproc, software, false,
		    attivaUpdateDati));
	    alberoproc.setAlberoprocEndos(populateAndInsertListaAlberoprocEndo(interventoSIVBG.getAdempimenti(), alberoproc, software, false,
		    attivaUpdateDati));
	    if (interventoSIVBG.getPubblica() != null) {
		if (interventoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.NON_PUBBLICARE.value())) {
		    alberoproc.setScPubblica(0);
		} else if (interventoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO_E_AREARISERVATA.value())) {
		    alberoproc.setScPubblica(1);
		} else if (interventoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_AREARISERVATA.value())) {
		    alberoproc.setScPubblica(2);
		} else if (interventoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO.value())) {
		    alberoproc.setScPubblica(3);
		}
	    }
	    if (interventoSIVBG.getFigli() != null && interventoSIVBG.getFigli().getIntervento() != null
		    && interventoSIVBG.getFigli().getIntervento().size() > 0) {
		alberoproc.setScPadre(true);
	    } else {
		alberoproc.setScPadre(false);
	    }
	    //
	    //
	    Set<AlberoprocDocumenti> sAlberoprocDocumenti = alberoproc.getAlberoprocDocumentis();
	    alberoproc.setAlberoprocDocumentis(null);
	    Set<AlberoprocLeggi> sAlberoprocLeggi = alberoproc.getAlberoprocLeggis();
	    alberoproc.setAlberoprocLeggis(null);
	    Set<AlberoprocEndo> sAlberoprocEndo = alberoproc.getAlberoprocEndos();
	    alberoproc.setAlberoprocEndos(null);
	    Set<AlberoprocDyn2modellit> sAlberoprocDyn2modellit = alberoproc.getAlberoprocDyn2modellits();
	    alberoproc.setAlberoprocDyn2modellits(null);
	    //
	    alberoprocDAO.insertOrUpdate(alberoproc, alberoproc.getId(), attivaUpdateDati);
	    alberoprocDAO.flush();
	    //
	    for (AlberoprocDocumenti alberoprocDocumenti : sAlberoprocDocumenti) {
		alberoprocDocumentiDAO.insertOrUpdate(alberoprocDocumenti, alberoprocDocumenti.getId(), attivaUpdateDati);
	    }
	    for (AlberoprocLeggi alberoprocLeggi : sAlberoprocLeggi) {
		alberoprocLeggiDAO.insertOrUpdate(alberoprocLeggi, alberoprocLeggi.getId(), attivaUpdateDati);
	    }
	    for (AlberoprocEndo alberoprocEndo : sAlberoprocEndo) {
		alberoprocEndoDAO.insertOrUpdate(alberoprocEndo, alberoprocEndo.getId(), attivaUpdateDati);
	    }
	    for (AlberoprocDyn2modellit alberoprocDyn2modellit : sAlberoprocDyn2modellit) {
		alberoprocDyn2modellitDAO.insertOrUpdate(alberoprocDyn2modellit, alberoprocDyn2modellit.getId(), attivaUpdateDati);
	    }
	    if (interventoSIVBG.getFigli() != null && interventoSIVBG.getFigli().getIntervento() != null) {
		for (InterventoType interventoFiglioSIVBG : interventoSIVBG.getFigli().getIntervento()) {
		    populateAndInsertIntervento(interventoFiglioSIVBG, alberoproc, software, eseguiQuery, attivaUpdateDati);
		}
	    }
	}
	return alberoproc;
    }

    private Set<AlberoprocDyn2modellit> populateAndInsertListaAlberoprocDyn2modellit(List<InterventoSchedeType> schedeDinamicheSIVBG,
	    Alberoproc alberoproc, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<AlberoprocDyn2modellit> set = null;
	if (schedeDinamicheSIVBG != null) {
	    set = new HashSet<AlberoprocDyn2modellit>();
	    for (InterventoSchedeType alberoprocDyn2modellitSIVBG : schedeDinamicheSIVBG) {
		AlberoprocDyn2modellit alberoprocDyn2modellit = populateAndInsertAlberoprocDyn2modellit(alberoprocDyn2modellitSIVBG, alberoproc,
			software, eseguiQuery, attivaUpdateDati);
		if (alberoprocDyn2modellit != null) {
		    set.add(alberoprocDyn2modellit);
		}
	    }
	}
	return set;
    }

    private AlberoprocDyn2modellit populateAndInsertAlberoprocDyn2modellit(InterventoSchedeType alberoprocDyn2modellitSIVBG, Alberoproc alberoproc,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	AlberoprocDyn2modellit alberoprocDyn2modellit = null;
	if (alberoprocDyn2modellitSIVBG != null) {
	    alberoprocDyn2modellit = new AlberoprocDyn2modellit();
	    AlberoprocDyn2modellitId id = new AlberoprocDyn2modellitId();
	    alberoprocDyn2modellit.setId(id);
	    alberoprocDyn2modellit.getId().setFkD2mtId(Integer.valueOf(alberoprocDyn2modellitSIVBG.getScheda().getId()));
	    alberoprocDyn2modellit.getId().setFkScId(alberoproc.getId().getCodice());
	    alberoprocDyn2modellit.setAlberoproc(alberoproc);
	    if (alberoprocDyn2modellitSIVBG.getTipoFirma() != null) {
		if (alberoprocDyn2modellitSIVBG.getTipoFirma().value().equalsIgnoreCase(TipoFirmaType.LA_SCHEDA_NON_NECESSITA_DI_FIRMA.value())) {
		    alberoprocDyn2modellit.setFlagTipofirma(0);
		} else if (alberoprocDyn2modellitSIVBG.getTipoFirma().value().equalsIgnoreCase(TipoFirmaType.LA_SCHEDA_RICHIEDE_LA_FIRMA.value())) {
		    alberoprocDyn2modellit.setFlagTipofirma(1);
		} else if (alberoprocDyn2modellitSIVBG.getTipoFirma().value().equalsIgnoreCase(TipoFirmaType.LA_SCHEDA_VA_FIRMATA_A_BLOCCHI.value())) {
		    alberoprocDyn2modellit.setFlagTipofirma(2);
		}
	    }
	    alberoprocDyn2modellit.setFlagFacoltativa(alberoprocDyn2modellitSIVBG.isNecessaria());
	    if (alberoprocDyn2modellitSIVBG.getPubblica() != null) {
		if (alberoprocDyn2modellitSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaType.NON_PUBBLICARE.value())) {
		    alberoprocDyn2modellit.setFlagPubblica(false);
		} else {
		    alberoprocDyn2modellit.setFlagPubblica(true);
		}
	    }
	    alberoprocDyn2modellit.setDyn2Modellit(populateAndInsertDyn2Modellit(alberoprocDyn2modellitSIVBG.getScheda(), software, true,
		    attivaUpdateDati));
	    if (eseguiQuery) {
		alberoprocDyn2modellitDAO.insertOrUpdate(alberoprocDyn2modellit, alberoprocDyn2modellit.getId(), attivaUpdateDati);
	    }
	}
	return alberoprocDyn2modellit;
    }

    private Dyn2Modellit populateAndInsertDyn2Modellit(SchedaType schedaSIVBG, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Dyn2Modellit dyn2Modellit = null;
	if (schedaSIVBG != null) {
	    dyn2Modellit = new Dyn2Modellit();
	    dyn2Modellit.getId().setCodice(Integer.valueOf(schedaSIVBG.getId()));
	    dyn2Modellit.setCodiceScheda(schedaSIVBG.getCodice());
	    dyn2Modellit.setDescrizione(schedaSIVBG.getNome());
	    dyn2Modellit.setSoftware(software);
	    dyn2Modellit
		    .setDyn2Modellids(populateAndInsertListaDyn2Modellid(schedaSIVBG.getCampi(), dyn2Modellit, software, false, attivaUpdateDati));
	    Set<Dyn2Modellid> set = dyn2Modellit.getDyn2Modellids();
	    dyn2Modellit.setDyn2Modellids(null);
	    //
	    if (eseguiQuery) {
		dyn2ModellitDAO.insertOrUpdate(dyn2Modellit, dyn2Modellit.getId(), attivaUpdateDati);
		dyn2ModellitDAO.flush();
	    }
	    //
	    for (Dyn2Modellid dyn2Modellid : set) {
		dyn2ModellidDAO.insertOrUpdate(dyn2Modellid, dyn2Modellid.getId(), attivaUpdateDati);
		dyn2ModellidDAO.flush();
	    }
	}
	return dyn2Modellit;
    }

    private Set<Dyn2Modellid> populateAndInsertListaDyn2Modellid(List<CampoSchedaType> campiSIVBG, Dyn2Modellit dyn2Modellit, Software software,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<Dyn2Modellid> set = null;
	if (campiSIVBG != null) {
	    set = new HashSet<Dyn2Modellid>();
	    for (CampoSchedaType campoSIVBG : campiSIVBG) {
		Dyn2Modellid dyn2Modellid = populateAndInsertDyn2Modellid(campoSIVBG, dyn2Modellit, software, false, attivaUpdateDati);
		if (dyn2Modellid != null) {
		    set.add(dyn2Modellid);
		}
	    }
	}
	return set;
    }

    private Dyn2Modellid populateAndInsertDyn2Modellid(CampoSchedaType campoSIVBG, Dyn2Modellit dyn2Modellit, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Dyn2Modellid dyn2Modellid = null;
	if (campoSIVBG != null) {
	    dyn2Modellid = new Dyn2Modellid();
	    dyn2Modellid.getId().setCodice(Integer.valueOf(campoSIVBG.getCodice()));
	    if (campoSIVBG.getPosizione() != null) {
		dyn2Modellid.setPosorizzontale(campoSIVBG.getPosizione().getColonna());
		dyn2Modellid.setPosverticale(campoSIVBG.getPosizione().getRiga());
	    }
	    dyn2Modellid.setDyn2Modellit(dyn2Modellit);
	    // campo dinamico
	    dyn2Modellid.setDyn2Campi(populateAndInsertDyn2Campi(campoSIVBG.getCampoDinamico(), software, true, attivaUpdateDati));
	    // oppure
	    // campo statico
	    dyn2Modellid.setDyn2Modellidtesti(populateAndInsertDyn2Modellidtesti(campoSIVBG.getCampoStatico(), software, true, attivaUpdateDati));
	    dyn2Modellid.setFlgMultiplo(false);
	    if (eseguiQuery) {
		dyn2ModellidDAO.insertOrUpdate(dyn2Modellid, dyn2Modellid.getId(), attivaUpdateDati);
		// dyn2ModellidDAO.flush();
	    }
	}
	return dyn2Modellid;
    }

    private Dyn2Campi populateAndInsertDyn2Campi(CampoDinamicoType campoDinamicoSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Dyn2Campi dyn2Campi = null;
	if (campoDinamicoSIVBG != null) {
	    dyn2Campi = new Dyn2Campi();
	    dyn2Campi.getId().setCodice(Integer.valueOf(campoDinamicoSIVBG.getCodice()));
	    dyn2Campi.setNomecampo(campoDinamicoSIVBG.getNome());
	    dyn2Campi.setDescrizione(campoDinamicoSIVBG.getDescrizione());
	    dyn2Campi.setEtichetta(campoDinamicoSIVBG.getEtichetta());
	    dyn2Campi.setDyn2Basecontesti(null);
	    dyn2Campi.setObbligatorio(null);
	    dyn2Campi.setScriptupdatecode(null);
	    dyn2Campi.setScrptcode(null);
	    if (campoDinamicoSIVBG.getProprieta() != null && campoDinamicoSIVBG.getProprieta().getTipoCampo() != null) {
		dyn2Campi.setTipodato(campoDinamicoSIVBG.getProprieta().getTipoCampo().value());
	    } else {
		dyn2Campi.setTipodato("Testo");
	    }
	    dyn2Campi.setDyn2Campiproprietas(populateAndInsertListaDyn2Campiproprieta(campoDinamicoSIVBG.getProprieta(), dyn2Campi, false,
		    attivaUpdateDati));
	    dyn2Campi.setSoftware(software);
	    Set<Dyn2Campiproprieta> dyn2Campiproprietas = dyn2Campi.getDyn2Campiproprietas();
	    dyn2Campi.setDyn2Campiproprietas(null);
	    if (eseguiQuery) {
		dyn2CampiDAO.insertOrUpdate(dyn2Campi, dyn2Campi.getId(), attivaUpdateDati);
		dyn2CampiDAO.flush();
	    }
	    for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2Campiproprietas) {
		dyn2CampiproprietaDAO.insertOrUpdate(dyn2Campiproprieta, dyn2Campiproprieta.getId(), attivaUpdateDati);
	    }
	}
	return dyn2Campi;
    }

    private Set<Dyn2Campiproprieta> populateAndInsertListaDyn2Campiproprieta(ProprietaCampoDinamicoType proprietaSIVBG, Dyn2Campi dyn2Campi,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<Dyn2Campiproprieta> set = null;
	if (proprietaSIVBG != null) {
	    set = new HashSet<Dyn2Campiproprieta>();
	    // proprietà "campo aobbligatorio"
	    if (proprietaSIVBG.isObbligatorio()) {
		Dyn2Campiproprieta dyn2CampiproprietaObbligatorio = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_obbligatorio = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_obbligatorio.setProprieta("Obbligatorio");
		dyn2CampiproprietaId_obbligatorio.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaObbligatorio.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaObbligatorio.setId(dyn2CampiproprietaId_obbligatorio);
		dyn2CampiproprietaObbligatorio.setValore("true");
		set.add(dyn2CampiproprietaObbligatorio);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaObbligatorio, dyn2CampiproprietaObbligatorio.getId(), attivaUpdateDati);
		}
	    }
	    //
	    if (proprietaSIVBG.isMultiplo()) {
		Dyn2Campiproprieta dyn2CampiproprietaMultiplo = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_multiplo = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_multiplo.setProprieta("MultiLine");
		dyn2CampiproprietaId_multiplo.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaMultiplo.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaMultiplo.setId(dyn2CampiproprietaId_multiplo);
		dyn2CampiproprietaMultiplo.setValore("true");
		set.add(dyn2CampiproprietaMultiplo);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaMultiplo, dyn2CampiproprietaMultiplo.getId(), attivaUpdateDati);
		}
	    }
	    //	    if (proprietaSIVBG.getCifreDecimali()!=null){
	    //		Dyn2Campiproprieta dyn2CampiproprietaCifreDecimali = new Dyn2Campiproprieta();
	    //		Dyn2CampiproprietaId dyn2CampiproprietaId_CifreDecimali = new Dyn2CampiproprietaId();
	    //		dyn2CampiproprietaId_CifreDecimali.setProprieta("...cifredecimali");
	    //		dyn2CampiproprietaId_CifreDecimali.setFkD2cId(dyn2Campi.getId().getCodice());
	    //		dyn2CampiproprietaCifreDecimali.setDyn2Campi(dyn2Campi);
	    //		dyn2CampiproprietaCifreDecimali.setId(dyn2CampiproprietaId_CifreDecimali);
	    //		dyn2CampiproprietaCifreDecimali.setValore("...");
	    //	    }
	    //
	    if (proprietaSIVBG.getLunghezza() != null) {
		Dyn2Campiproprieta dyn2CampiproprietaLunghezza = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_lunghezza = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_lunghezza.setProprieta("MaxLength");
		dyn2CampiproprietaId_lunghezza.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaLunghezza.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaLunghezza.setId(dyn2CampiproprietaId_lunghezza);
		dyn2CampiproprietaLunghezza.setValore(String.valueOf(proprietaSIVBG.getLunghezza()));
		set.add(dyn2CampiproprietaLunghezza);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaLunghezza, dyn2CampiproprietaLunghezza.getId(), attivaUpdateDati);
		}
	    }
	    //
	    if (proprietaSIVBG.getPosizione() != null) {
		// ???
	    }
	    //
	    if (proprietaSIVBG.getValoreLista() != null && proprietaSIVBG.getValoreLista().size() > 0) {
		Dyn2Campiproprieta dyn2CampiproprietaValoriLista = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_valoriLista = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_valoriLista.setProprieta("ElementiLista");
		dyn2CampiproprietaId_valoriLista.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaValoriLista.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaValoriLista.setId(dyn2CampiproprietaId_valoriLista);
		String valore = "";
		for (ValoreParametroType valoreParametroSIVBG : proprietaSIVBG.getValoreLista()) {
		    String codice = valoreParametroSIVBG.getCodice();
		    String desc = valoreParametroSIVBG.getDescrizione();
		    if (codice != null && desc != null && codice.equalsIgnoreCase(desc)) {
			valore += codice + ";";
		    } else {
			valore += codice + "$" + desc + ";";
		    }
		}
		if (valore.equalsIgnoreCase("")) {
		    dyn2CampiproprietaValoriLista.setValore("");
		} else {
		    dyn2CampiproprietaValoriLista.setValore(valore.substring(0, valore.length() - 1));
		}
		set.add(dyn2CampiproprietaValoriLista);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaValoriLista, dyn2CampiproprietaValoriLista.getId(), attivaUpdateDati);
		}
	    }
	    //
	    if (proprietaSIVBG.getValoriCheckBox() != null && proprietaSIVBG.getValoriCheckBox().getValoreSelezionato() != null) {
		Dyn2Campiproprieta dyn2CampiproprietaValoriCheckBoxTrue = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_valoriCheckBoxTrue = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_valoriCheckBoxTrue.setProprieta("ValoreTrue");
		dyn2CampiproprietaId_valoriCheckBoxTrue.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaValoriCheckBoxTrue.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaValoriCheckBoxTrue.setId(dyn2CampiproprietaId_valoriCheckBoxTrue);
		dyn2CampiproprietaValoriCheckBoxTrue.setValore("1");
		set.add(dyn2CampiproprietaValoriCheckBoxTrue);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaValoriCheckBoxTrue, dyn2CampiproprietaValoriCheckBoxTrue.getId(),
			    attivaUpdateDati);
		}
	    }
	    //
	    if (proprietaSIVBG.getValoriCheckBox() != null && proprietaSIVBG.getValoriCheckBox().getValoreNonSelezionato() != null) {
		Dyn2Campiproprieta dyn2CampiproprietaValoriCheckBoxFalse = new Dyn2Campiproprieta();
		Dyn2CampiproprietaId dyn2CampiproprietaId_valoriCheckBoxFalse = new Dyn2CampiproprietaId();
		dyn2CampiproprietaId_valoriCheckBoxFalse.setProprieta("ValoreFalse");
		dyn2CampiproprietaId_valoriCheckBoxFalse.setFkD2cId(dyn2Campi.getId().getCodice());
		dyn2CampiproprietaValoriCheckBoxFalse.setDyn2Campi(dyn2Campi);
		dyn2CampiproprietaValoriCheckBoxFalse.setId(dyn2CampiproprietaId_valoriCheckBoxFalse);
		dyn2CampiproprietaValoriCheckBoxFalse.setValore("1");
		set.add(dyn2CampiproprietaValoriCheckBoxFalse);
		if (eseguiQuery) {
		    dyn2CampiproprietaDAO.insertOrUpdate(dyn2CampiproprietaValoriCheckBoxFalse, dyn2CampiproprietaValoriCheckBoxFalse.getId(),
			    attivaUpdateDati);
		}
	    }
	}
	return set;
    }

    private Dyn2Modellidtesti populateAndInsertDyn2Modellidtesti(CampoStaticoType campoStaticoSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Dyn2Modellidtesti dyn2Modellidtesti = null;
	if (campoStaticoSIVBG != null) {
	    dyn2Modellidtesti = new Dyn2Modellidtesti();
	    dyn2Modellidtesti.getId().setCodice(Integer.valueOf(campoStaticoSIVBG.getCodice()));
	    dyn2Modellidtesti.setTesto(campoStaticoSIVBG.getTestoFisso());
	    dyn2Modellidtesti.setDyn2Basetipitesto(populateAndInsertDyn2Basetipitesto(campoStaticoSIVBG.getTipoCampo(), software, eseguiQuery,
		    attivaUpdateDati));
	    if (eseguiQuery) {
		dyn2ModellidtestiDAO.insertOrUpdate(dyn2Modellidtesti, dyn2Modellidtesti.getId(), attivaUpdateDati);
	    }
	}
	return dyn2Modellidtesti;
    }

    private Dyn2Basetipitesto populateAndInsertDyn2Basetipitesto(TipoCampoStaticoType tipoCampoSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Dyn2Basetipitesto dyn2Basetipitesto = null;
	if (tipoCampoSIVBG != null) {
	    dyn2Basetipitesto = new Dyn2Basetipitesto();
	    if (tipoCampoSIVBG.value().equalsIgnoreCase(TipoCampoStaticoType.TITOLO.value())) {
		dyn2Basetipitesto.setId("TI");
		dyn2Basetipitesto.setTipotesto("Titolo");
	    } else if (tipoCampoSIVBG.value().equalsIgnoreCase(TipoCampoStaticoType.TESTO_ESTESO.value())) {
		dyn2Basetipitesto.setId("TE");
		dyn2Basetipitesto.setTipotesto("Testo esteso");
	    }
	    if (eseguiQuery) {
		dyn2BasetipitestoDAO.insertOrUpdate(dyn2Basetipitesto, dyn2Basetipitesto.getId(), attivaUpdateDati);
	    }
	}
	return dyn2Basetipitesto;
    }

    private Set<AlberoprocEndo> populateAndInsertListaAlberoprocEndo(List<AdempimentoType> adempimentiSIVBG, Alberoproc alberoproc,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<AlberoprocEndo> set = null;
	if (adempimentiSIVBG != null) {
	    set = new HashSet<AlberoprocEndo>();
	    for (AdempimentoType alberoprocEndoSIVBG : adempimentiSIVBG) {
		AlberoprocEndo alberoprocEndo = populateAndInsertAlberoproEndo(alberoprocEndoSIVBG, alberoproc, software, eseguiQuery,
			attivaUpdateDati);
		if (alberoprocEndo != null) {
		    set.add(alberoprocEndo);
		}
	    }
	}
	return set;
    }

    private AlberoprocEndo populateAndInsertAlberoproEndo(AdempimentoType alberoprocEndoSIVBG, Alberoproc alberoproc, Software software,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	AlberoprocEndo endo = null;
	if (alberoprocEndoSIVBG != null) {
	    endo = new AlberoprocEndo();
	    endo.setAlberoproc(alberoproc);
	    if (alberoprocEndoSIVBG.getAzione() != null) {
		endo.getAzione().setAzId(Integer.valueOf(alberoprocEndoSIVBG.getAzione()));
	    } else {
		endo.setAzione(null);
	    }
	    //populateAndInsertAzione(alberoprocEndoSIVBG.getAzione(), software, eseguiQuery, attivaUpdateDati)); // TODO
	    endo.setFlagPrincipale(alberoprocEndoSIVBG.isPrincipale());
	    endo.setFlagRichiesto(alberoprocEndoSIVBG.isNecessario());
	    AlberoprocEndoId alberoprocEndoId = new AlberoprocEndoId();
	    alberoprocEndoId.setFkscid(alberoproc.getId().getCodice());
	    alberoprocEndoId.setCodiceinventario(Integer.valueOf(alberoprocEndoSIVBG.getCodiceProcedimento()));
	    alberoprocEndoId.setIdcomune(ORMHelper.getIdcomune());
	    endo.setId(alberoprocEndoId);
	    endo.getInventarioprocedimento().getId().setCodice(Integer.valueOf(alberoprocEndoSIVBG.getCodiceProcedimento()));
	    if (eseguiQuery) {
		alberoprocEndoDAO.insertOrUpdate(endo, endo.getId(), attivaUpdateDati);
	    }
	}
	return endo;
    }

    private Set<AlberoprocLeggi> populateAndInsertListaAlberoprocLeggi(List<LeggeType> leggiSIVBG, Alberoproc alberoproc, Software software,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<AlberoprocLeggi> set = null;
	if (leggiSIVBG != null) {
	    set = new HashSet<AlberoprocLeggi>();
	    for (LeggeType alberoprocLeggeSIVBG : leggiSIVBG) {
		AlberoprocLeggi legge = populateAndInsertAlberoprocLeggi(alberoprocLeggeSIVBG, alberoproc, software, eseguiQuery, attivaUpdateDati);
		if (legge != null) {
		    set.add(legge);
		}
	    }
	}
	return set;
    }

    private AlberoprocLeggi populateAndInsertAlberoprocLeggi(LeggeType alberoprocLeggeSIVBG, Alberoproc alberoproc, Software software,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	AlberoprocLeggi alberoprocLeggi = null;
	if (alberoprocLeggeSIVBG != null) {
	    alberoprocLeggi = new AlberoprocLeggi();
	    alberoprocLeggi.setAlberoproc(alberoproc);
	    alberoprocLeggi.getId().setCodice(Integer.valueOf(alberoprocLeggeSIVBG.getId()));
	    alberoprocLeggi.setLegge(populateAndInsertLeggi(alberoprocLeggeSIVBG, software, true, attivaUpdateDati));
	    if (eseguiQuery) {
		alberoprocLeggiDAO.insertOrUpdate(alberoprocLeggi, alberoprocLeggi.getId(), attivaUpdateDati);
	    }
	}
	return alberoprocLeggi;
    }

    private Set<AlberoprocDocumenti> populateAndInsertListaAlberoprocDocumenti(List<InterventoDocumentiType> documenti, Alberoproc alberoproc,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<AlberoprocDocumenti> set = null;
	if (documenti != null) {
	    set = new HashSet<AlberoprocDocumenti>();
	    for (InterventoDocumentiType alberoprocDocumentoSIVBG : documenti) {
		AlberoprocDocumenti alberoprocDocumento = populateAndInsertAlberoprocDocumenti(alberoprocDocumentoSIVBG, alberoproc, software,
			eseguiQuery, attivaUpdateDati);
		if (alberoprocDocumento != null) {
		    set.add(alberoprocDocumento);
		}
	    }
	}
	return set;
    }

    private AlberoprocDocumenti populateAndInsertAlberoprocDocumenti(InterventoDocumentiType alberoprocDocumentoSIVBG, Alberoproc alberoproc,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	AlberoprocDocumenti alberoprocDocumento = null;
	if (alberoprocDocumentoSIVBG != null) {
	    alberoprocDocumento = new AlberoprocDocumenti();
	    alberoprocDocumento.setAlberoproc(alberoproc);
	    alberoprocDocumento.getId().setCodice(Integer.valueOf(alberoprocDocumentoSIVBG.getId()));
	    alberoprocDocumento.setDescrizione(alberoprocDocumentoSIVBG.getDescrizione());
	    alberoprocDocumento.setNote(alberoprocDocumentoSIVBG.getNote());
	    if (alberoprocDocumentoSIVBG.getPubblica() != null) {
		if (alberoprocDocumentoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.NON_PUBBLICARE.value())) {
		    alberoprocDocumento.setPubblica(0);
		} else if (alberoprocDocumentoSIVBG.getPubblica().value()
			.equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO_E_AREARISERVATA.value())) {
		    alberoprocDocumento.setPubblica(1);
		} else if (alberoprocDocumentoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_AREARISERVATA.value())) {
		    alberoprocDocumento.setPubblica(2);
		} else if (alberoprocDocumentoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO.value())) {
		    alberoprocDocumento.setPubblica(3);
		}
	    }
	    alberoprocDocumento.setRichiesto(alberoprocDocumentoSIVBG.isNecessario());
	    if (alberoprocDocumentoSIVBG.getOrdine() != null) {
		alberoprocDocumento.setOrdine(Integer.valueOf(alberoprocDocumentoSIVBG.getOrdine()));
	    }
	    alberoprocDocumento.setFoRichiedefirma(alberoprocDocumentoSIVBG.isRichiedeFirma());
	    // alberoprocDocumento.setTipoDownloads(tipoDownloads) // TODO
	    alberoprocDocumento.setOggetto(populateAndInsertOggetti(alberoprocDocumentoSIVBG.getAllegato(), software, true, attivaUpdateDati));
	    if (eseguiQuery) {
		alberoprocDocumentiDAO.insertOrUpdate(alberoprocDocumento, alberoprocDocumento.getId(), attivaUpdateDati);
	    }
	}
	return alberoprocDocumento;
    }

    private Amministrazioni populateAndInsertAmministrazioni(AmministrazioneType amministrazioneSIVBG, boolean eseguiQuery, boolean attivaUpdateDati) {

	Amministrazioni amministrazione = null;
	if (amministrazioneSIVBG != null) {
	    amministrazione = new Amministrazioni();
	    amministrazione.getId().setCodice(Integer.valueOf(amministrazioneSIVBG.getId()));
	    amministrazione.setAmministrazione(amministrazioneSIVBG.getAmministrazione());
	    if (eseguiQuery) {
		amministrazioniDAO.insertOrUpdate(amministrazione, amministrazione.getId(), attivaUpdateDati);
	    }
	}
	return amministrazione;
    }

    private Tipiendo populateAndInsertTipiendo(TipologiaProcedimentoType tipologiaProcedimentoSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Tipiendo tipiendo = null;
	if (tipologiaProcedimentoSIVBG != null) {
	    tipiendo = new Tipiendo();
	    tipiendo.getId().setCodice(Integer.valueOf(tipologiaProcedimentoSIVBG.getId()));
	    tipiendo.setTipo(tipologiaProcedimentoSIVBG.getTipo());
	    tipiendo.setNote(tipologiaProcedimentoSIVBG.getNote());
	    if (tipologiaProcedimentoSIVBG.getOrdine() != null) {
		tipiendo.setOrdine(Integer.valueOf(tipologiaProcedimentoSIVBG.getOrdine()));
	    }
	    tipiendo.setSoftware(software);
	    tipiendo.setTipifamiglieendo(populateAndInsertTipifamigliaendo(tipologiaProcedimentoSIVBG.getFamiglia(), software, true, attivaUpdateDati));
	    if (eseguiQuery) {
		tipiendoDAO.insertOrUpdate(tipiendo, tipiendo.getId(), attivaUpdateDati);
	    }
	}
	return tipiendo;
    }

    private Tipifamiglieendo populateAndInsertTipifamigliaendo(FamigliaProcedimentoType famigliaprocedimentoSIVBG, Software software,
	    boolean eseguiQuery, boolean attivaUpdateDati) {

	Tipifamiglieendo tipifamigliaendo = null;
	if (famigliaprocedimentoSIVBG != null) {
	    tipifamigliaendo = new Tipifamiglieendo();
	    tipifamigliaendo.getId().setCodice(Integer.valueOf(famigliaprocedimentoSIVBG.getId()));
	    tipifamigliaendo.setNote(famigliaprocedimentoSIVBG.getNote());
	    if (famigliaprocedimentoSIVBG.getOrdine() != null) {
		tipifamigliaendo.setOrdine(Integer.valueOf(famigliaprocedimentoSIVBG.getOrdine()));
	    }
	    tipifamigliaendo.setTipo(famigliaprocedimentoSIVBG.getFamiglia());
	    tipifamigliaendo.setSoftware(software);
	    if (eseguiQuery) {
		tipifamiglieendoDAO.insertOrUpdate(tipifamigliaendo, tipifamigliaendo.getId(), attivaUpdateDati);
	    }
	}
	return tipifamigliaendo;
    }

    private Tipimovimento populateAndInsertTipimovimento(TipoMovimentoType tipoMovimentoSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati, HashMap<String, String> mappaTipiMovInseriti, HashMap<String, Integer> mappaProcedureInserite) {

	Tipimovimento tipimovimento = null;
	if (tipoMovimentoSIVBG != null && tipoMovimentoSIVBG.getId() != null) {
	    if (!mappaTipiMovInseriti.containsKey(tipoMovimentoSIVBG.getId())) {
		mappaTipiMovInseriti.put(tipoMovimentoSIVBG.getId(), tipoMovimentoSIVBG.getId());
		tipimovimento = new Tipimovimento();
		tipimovimento.getId().setTipomovimento(tipoMovimentoSIVBG.getId());
		tipimovimento.setMovimento(tipoMovimentoSIVBG.getDescrizione());
		tipimovimento.setFlagRichiestaintegrazione(tipoMovimentoSIVBG.isFlagSospensione());
		tipimovimento.setFlagInterruzione(tipoMovimentoSIVBG.isFlagInterruzione());
		tipimovimento.setFlagProroga(tipoMovimentoSIVBG.isFlagProroga());
		tipimovimento.setFlagPubblicaallegati(tipoMovimentoSIVBG.isFlagPubblicaAllegati());
		tipimovimento.setFlagPubblicamovimento(tipoMovimentoSIVBG.isFlagPubblicaMovimento());
		tipimovimento.setFlagPubblicaparere(tipoMovimentoSIVBG.isFlagPubblicaParere());
		tipimovimento.setFlagCds(tipoMovimentoSIVBG.isFlagCds());
		tipimovimento.setGgproroga(tipoMovimentoSIVBG.getGgProroga());
		tipimovimento.setTipologiaesito(Integer.valueOf(tipoMovimentoSIVBG.getTipologiaEsito()));
		tipimovimento.setFoSoggettiesterni(null);
		tipimovimento.setLetteretipo(null);
		tipimovimento.setMailtipoByFkTipimovcomTelMailtipo(null);
		tipimovimento.setMailtipoByFkTipimovricTelMailtipo(null);
		tipimovimento.setSoftware(software);
		tipimovimento.setStatoistanza(null);
		tipimovimento.setTipologiaregistri(null);
		tipimovimento.setTipicontromovimentos(populateAndInsertListaTipicontromovimenti(tipoMovimentoSIVBG.getMovimentiCollegati(),
			tipimovimento, software, false, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
		//
		Set<Tipicontromovimento> sTipicontromovimenti = tipimovimento.getTipicontromovimentos();
		tipimovimento.setTipicontromovimentos(null);
		if (eseguiQuery) {
		    tipimovimentoDAO.insertOrUpdate(tipimovimento, tipimovimento.getId(), attivaUpdateDati);
		    tipimovimentoDAO.flush();
		}
		for (Tipicontromovimento tipicontromovimento : sTipicontromovimenti) {
		    tipicontromovimentoDAO.insertOrUpdate(tipicontromovimento, tipicontromovimento.getId(), attivaUpdateDati);
		}
	    } else {
		
		tipimovimento = new Tipimovimento();
		tipimovimento.getId().setTipomovimento(tipoMovimentoSIVBG.getId());
		tipimovimento.setMovimento(tipoMovimentoSIVBG.getDescrizione());
		tipimovimento.setFlagRichiestaintegrazione(tipoMovimentoSIVBG.isFlagSospensione());
		tipimovimento.setFlagInterruzione(tipoMovimentoSIVBG.isFlagInterruzione());
		tipimovimento.setFlagProroga(tipoMovimentoSIVBG.isFlagProroga());
		tipimovimento.setFlagPubblicaallegati(tipoMovimentoSIVBG.isFlagPubblicaAllegati());
		tipimovimento.setFlagPubblicamovimento(tipoMovimentoSIVBG.isFlagPubblicaMovimento());
		tipimovimento.setFlagPubblicaparere(tipoMovimentoSIVBG.isFlagPubblicaParere());
		tipimovimento.setFlagCds(tipoMovimentoSIVBG.isFlagCds());
		tipimovimento.setGgproroga(tipoMovimentoSIVBG.getGgProroga());
		tipimovimento.setTipologiaesito(Integer.valueOf(tipoMovimentoSIVBG.getTipologiaEsito()));
		tipimovimento.setFoSoggettiesterni(null);
		tipimovimento.setLetteretipo(null);
		tipimovimento.setMailtipoByFkTipimovcomTelMailtipo(null);
		tipimovimento.setMailtipoByFkTipimovricTelMailtipo(null);
		tipimovimento.setSoftware(software);
		tipimovimento.setStatoistanza(null);
		tipimovimento.setTipologiaregistri(null);
		
		
		    tipimovimentoDAO.insertOrUpdate(tipimovimento, tipimovimento.getId(), attivaUpdateDati);
		    tipimovimentoDAO.flush();
		
		
//		TipimovimentoId id = new TipimovimentoId();
//		id.setTipomovimento(tipoMovimentoSIVBG.getId());
//		tipimovimento = tipimovimentoDAO.findById(id);
	    }
	}
	return tipimovimento;
    }

    private Naturaendo populateAndInsertNaturaendo(NaturaType naturaSIVBG, boolean eseguiQuery, boolean attivaUpdateDati) {

	Naturaendo naturaendo = null;
	if (naturaSIVBG != null) {
	    naturaendo = new Naturaendo();
	    naturaendo.getId().setCodice(Integer.valueOf(naturaSIVBG.getId()));
	    naturaendo.setNatura(naturaSIVBG.getNatura());
	    naturaendo.setBinariodipendenze(naturaSIVBG.getCompatibilita());
	    if (eseguiQuery) {
		naturaendoDAO.insertOrUpdate(naturaendo, naturaendo.getId(), attivaUpdateDati);
	    }
	}
	return naturaendo;
    }

    private InventarioprocTipititolo populateAndInsertInventarioprocTipititoli(TipoTitoloType tipoTitoloSIVBG,
	    Inventarioprocedimenti inventarioprocedimento, boolean eseguiQuery, boolean attivaUpdateDati) {

	InventarioprocTipititolo tipititolo = null;
	if (tipoTitoloSIVBG != null) {
	    tipititolo = new InventarioprocTipititolo();
	    tipititolo.getId().setCodice(Integer.valueOf(tipoTitoloSIVBG.getId()));
	    tipititolo.setTipotitolo(tipoTitoloSIVBG.getDescrizione());
	    tipititolo.setInventarioprocedimenti(inventarioprocedimento);
	    if (eseguiQuery) {
		inventarioprocTipititoloDAO.insertOrUpdate(tipititolo, tipititolo.getId(), attivaUpdateDati);
	    }
	}
	return tipititolo;
    }

    private Set<InventarioprocTipititolo> populateAndInsertListaInventarioprocTipititoli(List<TipoTitoloType> listaTipoTitoloSIVBG,
	    Inventarioprocedimenti inventarioprocedimento, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<InventarioprocTipititolo> set = null;
	if (listaTipoTitoloSIVBG != null) {
	    set = new HashSet<InventarioprocTipititolo>();
	    for (TipoTitoloType tipoTitoloSIVBG : listaTipoTitoloSIVBG) {
		InventarioprocTipititolo inventarioprocTipiTitolo = populateAndInsertInventarioprocTipititoli(tipoTitoloSIVBG,
			inventarioprocedimento, eseguiQuery, attivaUpdateDati);
		if (inventarioprocTipiTitolo != null) {
		    set.add(inventarioprocTipiTitolo);
		}
	    }
	}
	return set;
    }

    private Set<Tipicontromovimento> populateAndInsertListaTipicontromovimenti(List<TipoContromovimentoType> listaTipoContromovimentiSIVBG,
	    Tipimovimento tipimovimento, Software software, boolean eseguiQuery, boolean attivaUpdateDati,
	    HashMap<String, String> mappaTipiMovInseriti, HashMap<String, Integer> mappaProcedureInserite) {

	Set<Tipicontromovimento> set = null;
	if (listaTipoContromovimentiSIVBG != null) {
	    set = new HashSet<Tipicontromovimento>();
	    for (TipoContromovimentoType tipoContromovimentoSIVBG : listaTipoContromovimentiSIVBG) {
		Tipicontromovimento tipoconotromovimento = populateAndInsertTipicontromovimenti(tipoContromovimentoSIVBG, tipimovimento, software,
			eseguiQuery, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite);
		if (tipoconotromovimento != null) {
		    set.add(tipoconotromovimento);
		}
	    }
	}
	return set;
    }

    private Set<Tipiprocedureavvio> populateAndInsertListaTipiprocedureavvio(List<ProcedureAvvioType> movimentiAvvioSIVBG,
	    Tipiprocedure tipiprocedure, Software software, boolean eseguiQuery, boolean attivaUpdateDati,
	    HashMap<String, String> mappaTipiMovInseriti, HashMap<String, Integer> mappaProcedureInserite) {

	Set<Tipiprocedureavvio> set = null;
	if (movimentiAvvioSIVBG != null) {
	    set = new HashSet<Tipiprocedureavvio>();
	    for (ProcedureAvvioType tipiprocedureavvioSIVBG : movimentiAvvioSIVBG) {
		Tipiprocedureavvio tipiprocedureavvio = populateAndInsertTipiprocedureavvio(tipiprocedureavvioSIVBG, tipiprocedure, software,
			eseguiQuery, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite);
		if (tipiprocedureavvio != null) {
		    set.add(tipiprocedureavvio);
		}
	    }
	}
	return set;
    }

    private Tipiprocedureavvio populateAndInsertTipiprocedureavvio(ProcedureAvvioType tipiprocedureavvioSIVBG, Tipiprocedure tipiprocedure,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati, HashMap<String, String> mappaTipiMovInseriti,
	    HashMap<String, Integer> mappaProcedureInserite) {

	Tipiprocedureavvio tipiprocedureavvio = null;
	if (tipiprocedureavvioSIVBG != null && tipiprocedureavvioSIVBG.getTipoMovimento()!=null) {
	    tipiprocedureavvio = new Tipiprocedureavvio();
	    tipiprocedureavvio.getId().setTipomovimento(tipiprocedureavvioSIVBG.getTipoMovimento().getId());
	    tipiprocedureavvio.getId().setCodiceprocedura(tipiprocedure.getId().getCodice());
	    tipiprocedureavvio.setDefaultsn(tipiprocedureavvioSIVBG.isDefaultsn());
	    tipiprocedureavvio.setTipoMovimento(populateAndInsertTipimovimento(tipiprocedureavvioSIVBG.getTipoMovimento(), software, true,
		    attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
	    tipiprocedureavvio.setTipoProcedura(tipiprocedure);
	    if (eseguiQuery) {
		tipiprocedureavvioDAO.insertOrUpdate(tipiprocedureavvio, tipiprocedureavvio.getId(), attivaUpdateDati);
	    }
	}
	return tipiprocedureavvio;
    }

    private Tipiprocedure populateAndInsertTipiprocedure(ProceduraType proceduraSIVBG, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati, HashMap<String, String> mappaTipiMovInseriti, HashMap<String, Integer> mappaProcedureInserite) {

	Tipiprocedure tipiprocedure = null;
	if (proceduraSIVBG != null && proceduraSIVBG.getId() != null) {
	    if (!mappaProcedureInserite.containsKey(proceduraSIVBG.getId())) {
		tipiprocedure = new Tipiprocedure();
		tipiprocedure.getId().setCodice(Integer.valueOf(proceduraSIVBG.getId()));
		mappaProcedureInserite.put(proceduraSIVBG.getId(), Integer.valueOf(proceduraSIVBG.getId()));
		tipiprocedure.setProcedura(proceduraSIVBG.getProcedura());
		tipiprocedure.setNote(proceduraSIVBG.getNote());
		tipiprocedure.setGiorni(proceduraSIVBG.getDurata());
		tipiprocedure.setFlagattochiusura(proceduraSIVBG.isPrevedeAtto());
		tipiprocedure.setNaturaendo(populateAndInsertNaturaendo(proceduraSIVBG.getNatura(), eseguiQuery, attivaUpdateDati));
		tipiprocedure.setFlagprevedecds(proceduraSIVBG.isPrevedeCDS());
		tipiprocedure.setDeterminazioneinizioistanza(proceduraSIVBG.getDeterminazioneInizioIstanza());
		tipiprocedure.setDeterminazioneefficacia(proceduraSIVBG.getDeterminazioneEfficacia());
		tipiprocedure.setTipimovimentoDeterminazione(populateAndInsertTipimovimento(proceduraSIVBG.getDeterminazioneMovimento(), software,
			eseguiQuery, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
		if (proceduraSIVBG.getDeterminazioneEsito() != null) {
		    if (proceduraSIVBG.getDeterminazioneEsito().value().equalsIgnoreCase(DeterminazioneEsitoType.QUALSIASI.value())) {
			tipiprocedure.setDeterminazioneesito(0);
		    } else if (proceduraSIVBG.getDeterminazioneEsito().value().equalsIgnoreCase(DeterminazioneEsitoType.NEGATIVO.value())) {
			tipiprocedure.setDeterminazioneesito(1);
		    } else if (proceduraSIVBG.getDeterminazioneEsito().value().equalsIgnoreCase(DeterminazioneEsitoType.POSITIVO.value())) {
			tipiprocedure.setDeterminazioneesito(2);
		    }
		}
		tipiprocedure.setTipiProcedureavvios(populateAndInsertListaTipiprocedureavvio(proceduraSIVBG.getMovimentiAvvio(), tipiprocedure,
			software, false, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
		Set<Tipiprocedureavvio> sTipiProcAvvio = tipiprocedure.getTipiProcedureavvios();
		tipiprocedure.setTipiProcedureavvios(null);
		if (eseguiQuery) {
		    tipiprocedureDAO.insertOrUpdate(tipiprocedure, tipiprocedure.getId(), attivaUpdateDati);
		}
		for (Tipiprocedureavvio tipiprocedureavvio : sTipiProcAvvio) {
		    tipiprocedureavvioDAO.insertOrUpdate(tipiprocedureavvio, tipiprocedureavvio.getId(), attivaUpdateDati);
		}
	    } else {
		PkId pkId = new PkId();
		pkId.setCodice(Integer.valueOf(proceduraSIVBG.getId()));
		tipiprocedure = tipiprocedureDAO.findById(pkId);
	    }
	}
	return tipiprocedure;
    }

    private Tipicontromovimento populateAndInsertTipicontromovimenti(TipoContromovimentoType tipoContromovimentoSIVBG, Tipimovimento tipimovimento,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati, HashMap<String, String> mappaTipiMovInseriti,
	    HashMap<String, Integer> mappaProcedureInserite) {

	Tipicontromovimento tipicontromovimento = null;
	if (tipoContromovimentoSIVBG != null) {
	    tipicontromovimento = new Tipicontromovimento();
	    tipicontromovimento.getId().setCodice(Integer.valueOf(tipoContromovimentoSIVBG.getId()));
	    tipicontromovimento.setTipomovimento(tipimovimento);
	    tipicontromovimento.setTipocontromovimento(populateAndInsertTipimovimento(tipoContromovimentoSIVBG.getTipoContromovimento(), software,
		    true, attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
	    tipicontromovimento.setAmministrazioniTipiContromovimento(populateAndInsertAmministrazioni(
		    tipoContromovimentoSIVBG.getAmministrazioneControMovimento(), true, attivaUpdateDati));
	    if (tipoContromovimentoSIVBG.getDataInizioValidita() != null) {
		tipicontromovimento.setDatacreazione(tipoContromovimentoSIVBG.getDataInizioValidita().toGregorianCalendar().getTime());
	    }
	    tipicontromovimento.setFlagbase(tipoContromovimentoSIVBG.isObbligatorio());
	    if (tipoContromovimentoSIVBG.getComportamentoSTC() == null) {
		tipicontromovimento.setPropostostc(null);
	    } else {
		tipicontromovimento.setPropostostc(String.valueOf(tipoContromovimentoSIVBG.getComportamentoSTC()));
	    }
	    tipicontromovimento.setSeprecedente(tipoContromovimentoSIVBG.isComportamentoNonDuplicare());
	    tipicontromovimento.setSoloseesitonegativo(tipoContromovimentoSIVBG.getComportamento());
	    tipicontromovimento.setAmministrazioniTipiMovimento(populateAndInsertAmministrazioni(
		    tipoContromovimentoSIVBG.getAmministrazioneMovimento(), true, attivaUpdateDati));
	    tipicontromovimento.setTipiprocedure(populateAndInsertTipiprocedure(tipoContromovimentoSIVBG.getProcedura(), software, true,
		    attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
	    if (eseguiQuery) {
		tipicontromovimentoDAO.insertOrUpdate(tipicontromovimento, tipicontromovimento.getId(), attivaUpdateDati);
		tipicontromovimentoDAO.flush();
	    }
	}
	return tipicontromovimento;
    }

    private Oggetti populateAndInsertOggetti(AllegatoType allegatoSIVBG, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Oggetti oggetti = null;
	if (allegatoSIVBG != null) {
	    oggetti = new Oggetti();
	    oggetti.getId().setCodice(Integer.valueOf(allegatoSIVBG.getId()));
	    oggetti.setNomefile(allegatoSIVBG.getAllegato());
	    if (eseguiQuery) {
		oggettiDAO.insertOrUpdate(oggetti, oggetti.getId(), attivaUpdateDati);
	    }
	}
	return oggetti;
    }

    private Allegati populateAndInsertAllegati(ProcedimentoAllegatiType allegatoSIVBG, Inventarioprocedimenti inventarioprocedimenti,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Allegati allegato = null;
	if (allegatoSIVBG != null) {
	    allegato = new Allegati();
	    allegato.getId().setCodice(Integer.valueOf(allegatoSIVBG.getId()));
	    allegato.setAllegato(allegatoSIVBG.getDescrizione());
	    allegato.setAmministrazioni(null);
	    allegato.setCosto(null);
	    allegato.setFlagInserimentoAut(null);
	    allegato.setFoRichiedefirma(allegatoSIVBG.isRichiedeFirma());
	    allegato.setFoTipodownload(allegatoSIVBG.getTipoDownload());
	    allegato.setIndirizzoweb(allegatoSIVBG.getLink());
	    allegato.setInventarioprocedimento(inventarioprocedimenti);
	    allegato.setModello(null);
	    allegato.setOggetti(populateAndInsertOggetti(allegatoSIVBG.getAllegato(), software, true, attivaUpdateDati));
	    if (allegatoSIVBG.getOrdine() != null) {
		allegato.setOrdine(Integer.valueOf(allegatoSIVBG.getOrdine()));
	    }
	    if (allegatoSIVBG.getPubblica() != null) {
		if (allegatoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.NON_PUBBLICARE.value())) {
		    allegato.setPubblica(0);
		} else if (allegatoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO_E_AREARISERVATA.value())) {
		    allegato.setPubblica(1);
		} else if (allegatoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_AREARISERVATA.value())) {
		    allegato.setPubblica(2);
		} else if (allegatoSIVBG.getPubblica().value().equalsIgnoreCase(PubblicaDocumentoType.PUBBLICA_INFO.value())) {
		    allegato.setPubblica(3);
		}
	    }
	    allegato.setRichiesto(allegatoSIVBG.isRichiesto());
	    if (eseguiQuery) {
		allegatiDAO.insertOrUpdate(allegato, allegato.getId(), attivaUpdateDati);
	    }
	}
	return allegato;
    }

    private Set<Allegati> populateAndInserListaAllegati(List<ProcedimentoAllegatiType> listaProcedimentiAllegatiSIVBG,
	    Inventarioprocedimenti inventarioprocedimenti, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<Allegati> set = null;
	if (listaProcedimentiAllegatiSIVBG != null) {
	    set = new HashSet<Allegati>();
	    for (ProcedimentoAllegatiType allegatoSIVBG : listaProcedimentiAllegatiSIVBG) {
		Allegati allegato = populateAndInsertAllegati(allegatoSIVBG, inventarioprocedimenti, software, eseguiQuery, attivaUpdateDati);
		if (allegato != null) {
		    set.add(allegato);
		}
	    }
	}
	return set;
    }

    private Set<Inventarioprocdyn2modellit> populateAndInserListaSchedeDinamiche(List<ProcedimentoSchedeType> listaProcedimentiSchedeDinamicheSIVBG,
	    Inventarioprocedimenti inventarioprocedimento, Software software, boolean eseguiQuery, boolean attivaUpadteDati) {

	Set<Inventarioprocdyn2modellit> set = null;
	if (listaProcedimentiSchedeDinamicheSIVBG != null) {
	    set = new HashSet<Inventarioprocdyn2modellit>();
	    for (ProcedimentoSchedeType procedimentoSchedeType : listaProcedimentiSchedeDinamicheSIVBG) {
		Inventarioprocdyn2modellit inventarioprocdyn2modellit = populateAndInsertInventarioprocdyn2modellit(procedimentoSchedeType,
			inventarioprocedimento, software, eseguiQuery, attivaUpadteDati);
		if (inventarioprocdyn2modellit != null) {
		    set.add(inventarioprocdyn2modellit);
		}
	    }
	}
	return set;
    }

    private Inventarioprocdyn2modellit populateAndInsertInventarioprocdyn2modellit(ProcedimentoSchedeType procedimentoSchedeType,
	    Inventarioprocedimenti inventarioprocedimento, Software software, boolean eseguiQuery, boolean attivaUpadteDati) {

	Inventarioprocdyn2modellit inventarioprocdyn2modellit = null;
	if (procedimentoSchedeType != null) {
	    inventarioprocdyn2modellit = new Inventarioprocdyn2modellit();
	    inventarioprocdyn2modellit.getId().setCodiceinventario(inventarioprocedimento.getId().getCodice());
	    inventarioprocdyn2modellit.getId().setFkD2mtId(Integer.valueOf(procedimentoSchedeType.getScheda().getId()));
	    inventarioprocdyn2modellit.setFlagFacoltativa(procedimentoSchedeType.isFacoltativa());
	    inventarioprocdyn2modellit.setFlagTipofirma(procedimentoSchedeType.getTipoFirma());
	    inventarioprocdyn2modellit.setFlagPubblica(procedimentoSchedeType.isPubblica());
	    inventarioprocdyn2modellit.setInventarioprocedimenti(inventarioprocedimento);
	    if (procedimentoSchedeType.getOrdine() != null) {
		inventarioprocdyn2modellit.setOrdine(Integer.valueOf(procedimentoSchedeType.getOrdine()));
	    }
	    inventarioprocdyn2modellit.setDyn2Modellit(populateAndInsertDyn2Modellit(procedimentoSchedeType.getScheda(), software, true,
		    attivaUpadteDati));
	    if (eseguiQuery) {
		inventarioprocdyn2modellitDAO.insertOrUpdate(inventarioprocdyn2modellit, inventarioprocdyn2modellit.getId(), attivaUpadteDati);
	    }
	}
	return inventarioprocdyn2modellit;
    }

    private Set<Inventarioprocedimentioneri> populateAndInsertListaInventarioProcOneri(List<ProcedimentoOneriType> procedimentoOneriSIVBG,
	    Inventarioprocedimenti inventarioprocedimenti, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<Inventarioprocedimentioneri> set = null;
	if (procedimentoOneriSIVBG != null) {
	    set = new HashSet<Inventarioprocedimentioneri>();
	    for (ProcedimentoOneriType inventarioprocedimentioneriSIVBG : procedimentoOneriSIVBG) {
		Inventarioprocedimentioneri inventarioprocedimentioneri = populateAndInsertInventarioProcOneri(inventarioprocedimentioneriSIVBG,
			inventarioprocedimenti, software, eseguiQuery, attivaUpdateDati);
		if (inventarioprocedimentioneri != null) {
		    set.add(inventarioprocedimentioneri);
		}
	    }
	}
	return set;
    }

    private Inventarioprocedimentioneri populateAndInsertInventarioProcOneri(ProcedimentoOneriType inventarioprocedimentioneriSIVBG,
	    Inventarioprocedimenti inventarioprocedimenti, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Inventarioprocedimentioneri inventarioprocedimentioneri = null;
	if (inventarioprocedimentioneriSIVBG != null) {
	    inventarioprocedimentioneri = new Inventarioprocedimentioneri();
	    inventarioprocedimentioneri.getId().setCodice(Integer.valueOf(inventarioprocedimentioneriSIVBG.getId()));
	    inventarioprocedimentioneri.setInventarioprocedimenti(inventarioprocedimenti);
	    inventarioprocedimentioneri.setImporto(new BigDecimal(inventarioprocedimentioneriSIVBG.getImporto()));
	    inventarioprocedimentioneri.setTipicausalioneri(populateAndInsertTipicausalioneri(inventarioprocedimentioneriSIVBG.getCausale(),
		    software, true, attivaUpdateDati));
	    if (eseguiQuery) {
		inventarioprocedimentioneriDAO.insertOrUpdate(inventarioprocedimentioneri, inventarioprocedimentioneri.getId(), attivaUpdateDati);
	    }
	}
	return inventarioprocedimentioneri;
    }

    private Tipicausalioneri populateAndInsertTipicausalioneri(CausaliOneriType causale, Software software, boolean eseguiQuery,
	    boolean attivaUpdateDati) {

	Tipicausalioneri tipicausalioneri = null;
	if (causale != null) {
	    tipicausalioneri = new Tipicausalioneri();
	    tipicausalioneri.getId().setCodice(Integer.valueOf(causale.getId()));
	    tipicausalioneri.setCoDescrizione(causale.getDescrizione());
	    if (eseguiQuery) {
		tipicausalioneriDAO.insertOrUpdate(tipicausalioneri, tipicausalioneri.getId(), attivaUpdateDati);
	    }
	}
	return tipicausalioneri;
    }

    private Leggitipi populateAndInsertLeggitipi(LeggiTipiType leggiTipiSIVBG, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Leggitipi leggitipi = null;
	if (leggiTipiSIVBG != null) {
	    leggitipi = new Leggitipi();
	    leggitipi.getId().setCodice(Integer.valueOf(leggiTipiSIVBG.getId()));
	    leggitipi.setLtDescrizione(leggiTipiSIVBG.getDescrizione());
	    if (eseguiQuery) {
		leggitipiDAO.insertOrUpdate(leggitipi, leggitipi.getId(), attivaUpdateDati);
	    }
	}
	return leggitipi;
    }

    private Normative populateAndInsertNormative(NormativaType normativeSIVBG, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Normative normative = null;
	if (normativeSIVBG != null) {
	    normative = new Normative();
	    normative.getId().setCodice(Integer.valueOf(normativeSIVBG.getId()));
	    normative.setNormativa(normativeSIVBG.getNormativa());
	    if (eseguiQuery) {
		normativeDAO.insertOrUpdate(normative, normative.getId(), attivaUpdateDati);
	    }
	}
	return normative;
    }

    private Leggi populateAndInsertLeggi(LeggeType leggeSIVBG, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Leggi legge = null;
	if (leggeSIVBG != null) {
	    legge = new Leggi();
	    legge.getId().setCodice(Integer.valueOf(leggeSIVBG.getId()));
	    legge.setLeDescrizione(leggeSIVBG.getDescrizione());
	    legge.setLeLink(leggeSIVBG.getLink());
	    legge.setLeggitipi(populateAndInsertLeggitipi(leggeSIVBG.getTipoLegge(), software, eseguiQuery, attivaUpdateDati));
	    legge.setNormative(populateAndInsertNormative(leggeSIVBG.getTipoNormativa(), software, eseguiQuery, attivaUpdateDati));
	    if (eseguiQuery) {
		leggiDAO.insertOrUpdate(legge, legge.getId(), attivaUpdateDati);
	    }
	}
	return legge;
    }

    private InventarioprocLeggi populateAndInsertInventarioprocLeggi(ProcedimentoLeggeType leggeSIVBG, Inventarioprocedimenti inventarioprocedimenti,
	    Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	InventarioprocLeggi inventarioprocLeggi = null;
	if (leggeSIVBG != null) {
	    inventarioprocLeggi = new InventarioprocLeggi();
	    inventarioprocLeggi.getId().setCodice(Integer.valueOf(leggeSIVBG.getId()));
	    inventarioprocLeggi.setRiferimenti(leggeSIVBG.getRiferimenti());
	    inventarioprocLeggi.setInventarioprocedimenti(inventarioprocedimenti);
	    inventarioprocLeggi.setLeggi(populateAndInsertLeggi(leggeSIVBG.getLegge(), software, true, attivaUpdateDati));
	    if (eseguiQuery) {
		inventarioprocLeggiDAO.insertOrUpdate(inventarioprocLeggi, inventarioprocLeggi.getId(), attivaUpdateDati);
	    }
	}
	return inventarioprocLeggi;
    }

    private Set<InventarioprocLeggi> populateAndInsertListaInventarioprocLeggis(List<ProcedimentoLeggeType> procedimentoLeggiSIVBG,
	    Inventarioprocedimenti inventarioprocedimenti, Software software, boolean eseguiQuery, boolean attivaUpdateDati) {

	Set<InventarioprocLeggi> set = null;
	if (procedimentoLeggiSIVBG != null) {
	    set = new HashSet<InventarioprocLeggi>();
	    for (ProcedimentoLeggeType leggiSIVBG : procedimentoLeggiSIVBG) {
		InventarioprocLeggi inventarioprocLeggi = populateAndInsertInventarioprocLeggi(leggiSIVBG, inventarioprocedimenti, software,
			eseguiQuery, attivaUpdateDati);
		if (inventarioprocLeggi != null) {
		    set.add(inventarioprocLeggi);
		}
	    }
	}
	return set;
    }

    public void populateAndInsertProcedimenti(ContenutoBusta contenutoBusta, Software software, boolean attivaUpdateDati) {

	if (contenutoBusta.getProcedimenti() != null && contenutoBusta.getProcedimenti().getProcedimento() != null) {
	    for (ProcedimentoType procedimentoSIVBG : contenutoBusta.getProcedimenti().getProcedimento()) {
		Inventarioprocedimenti inventarioprocedimento = new Inventarioprocedimenti();
		inventarioprocedimento.getId().setCodice(Integer.valueOf(procedimentoSIVBG.getCodice()));
		inventarioprocedimento.setProcedimento(procedimentoSIVBG.getProcedimento());
		inventarioprocedimento.setAmministrazioni(populateAndInsertAmministrazioni(procedimentoSIVBG.getAmministrazione(), true,
			attivaUpdateDati));
		inventarioprocedimento.setDatigenerali(procedimentoSIVBG.getInfoGenerali());
		inventarioprocedimento.setAdempimenti(procedimentoSIVBG.getInfoAdempimenti());
		inventarioprocedimento.setCampoapplicazione(procedimentoSIVBG.getInfoRequisiti());
		inventarioprocedimento.setTipoendo(populateAndInsertTipiendo(procedimentoSIVBG.getTipologia(), software, true, attivaUpdateDati));
		HashMap<String, String> mappaTipiMovInseriti = new HashMap<String, String>();
		HashMap<String, Integer> mappaProcedureInserite = new HashMap<String, Integer>();
		inventarioprocedimento.setTipomovimento(populateAndInsertTipimovimento(procedimentoSIVBG.getMovimentoAttivazione(), software, true,
			attivaUpdateDati, mappaTipiMovInseriti, mappaProcedureInserite));
		inventarioprocedimento.setNaturaendo(populateAndInsertNaturaendo(procedimentoSIVBG.getNatura(), true, attivaUpdateDati));
		inventarioprocedimento.setInventarioprocTipititolos(populateAndInsertListaInventarioprocTipititoli(procedimentoSIVBG.getTipoTitolo(),
			inventarioprocedimento, false, attivaUpdateDati));
		inventarioprocedimento.setAllegatis(populateAndInserListaAllegati(procedimentoSIVBG.getAllegati(), inventarioprocedimento, software,
			false, attivaUpdateDati));
		inventarioprocedimento.setInventarioprocdyn2modellits(populateAndInserListaSchedeDinamiche(
			procedimentoSIVBG.getSchedeDinamiche(), inventarioprocedimento, software, false, attivaUpdateDati));
		inventarioprocedimento.setInventarioprocedimentioneris(populateAndInsertListaInventarioProcOneri(procedimentoSIVBG.getOneri(),
			inventarioprocedimento, software, false, attivaUpdateDati));
		inventarioprocedimento.setInventarioprocLeggis(populateAndInsertListaInventarioprocLeggis(procedimentoSIVBG.getLeggi(),
			inventarioprocedimento, software, false, attivaUpdateDati));
		// --------
		Set<InventarioprocLeggi> sInvProcLeggi = inventarioprocedimento.getInventarioprocLeggis();
		inventarioprocedimento.setInventarioprocLeggis(null);
		Set<Inventarioprocedimentioneri> sInvProcOneri = inventarioprocedimento.getInventarioprocedimentioneris();
		inventarioprocedimento.setInventarioprocedimentioneris(null);
		Set<Allegati> sInvProcAllegati = inventarioprocedimento.getAllegatis();
		inventarioprocedimento.setAllegatis(null);
		Set<Inventarioprocdyn2modellit> sInvProcDyn2modellit = inventarioprocedimento.getInventarioprocdyn2modellits();
		inventarioprocedimento.setInventarioprocdyn2modellits(null);
		Set<InventarioprocTipititolo> sInvProcTipiTitolo = inventarioprocedimento.getInventarioprocTipititolos();
		inventarioprocedimento.setInventarioprocTipititolos(null);
		// 
		//
		inventarioprocedimentiDAO.insertOrUpdate(inventarioprocedimento, inventarioprocedimento.getId(), attivaUpdateDati);
		//
		//
		if (sInvProcLeggi != null) {
		    for (InventarioprocLeggi procLeggi : sInvProcLeggi) {
			inventarioprocLeggiDAO.insertOrUpdate(procLeggi, procLeggi.getId(), attivaUpdateDati);
		    }
		}
		if (sInvProcOneri != null) {
		    for (Inventarioprocedimentioneri inventarioprocedimentioneri : sInvProcOneri) {
			inventarioprocedimentioneriDAO.insertOrUpdate(inventarioprocedimentioneri, inventarioprocedimentioneri.getId(),
				attivaUpdateDati);
		    }
		}
		if (sInvProcAllegati != null) {
		    for (Allegati allegati : sInvProcAllegati) {
			allegatiDAO.insertOrUpdate(allegati, allegati.getId(), attivaUpdateDati);
		    }
		}
		if (sInvProcTipiTitolo != null) {
		    for (InventarioprocTipititolo tipititolo : sInvProcTipiTitolo) {
			inventarioprocTipititoloDAO.insertOrUpdate(tipititolo, tipititolo.getId(), attivaUpdateDati);
		    }
		}
		if (sInvProcDyn2modellit != null) {
		    for (Inventarioprocdyn2modellit inventarioprocdyn2modellit : sInvProcDyn2modellit) {
			inventarioprocdyn2modellitDAO
				.insertOrUpdate(inventarioprocdyn2modellit, inventarioprocdyn2modellit.getId(), attivaUpdateDati);
		    }
		}
	    }
	    // --------
	}
    }
}
