package it.gruppoinit.pal.gp.core.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ProcedimentoProcediMarche;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.CodiceDescrizione;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.IdDescrizione;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoGenerale;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoSpecifico;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.ProcediMarcheProxyService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.utils.CustomJsonSerializer;
import it.gruppoinit.pal.gp.core.utils.CustomJsonSerializer.JsonFormat;
import it.gruppoinit.proxy.TrustAllX509TrustManager;

/**
 * @author francol
 *
 */
@Service
public class ProcediMarcheProxyServiceImpl implements ProcediMarcheProxyService {

    private static final Logger log = LoggerFactory.getLogger(ProcediMarcheProxyServiceImpl.class);
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private AlberoprocService alberoprocService;

    @Override
    public List<ProcedimentoProcediMarche> getListaProcedimenti() {

	List<TipoProcedimentoGenerale> datiPM = this.getProcedimentiPM();
	//recupero dati stp_endo_tipo2 che hanno un riferimento all'id procedimarche settato
	List<StpEndoTipo2> datiStp = this.stpEndoTipo2Service.findByTipoSortByStpCodice(null, true);
	List<ProcedimentoProcediMarche> retList = new ArrayList<ProcedimentoProcediMarche>(datiPM.size());
	CodiceStpComparator cptor = new CodiceStpComparator();
	StpEndoTipo2 compareToMe = new StpEndoTipo2();
	//cerco match con stp_endo_tipo_2
	StpEndoTipo2 found = null;
	for (TipoProcedimentoGenerale procPM : datiPM) {
	    ProcedimentoProcediMarche ppm = new ProcedimentoProcediMarche();
	    ppm.setDatiProcedimento(procPM);
	    Integer idpm = procPM.getId();
	    if (idpm != null) {
		compareToMe.setCodiceStp(idpm);
		int foundAt = Collections.binarySearch(datiStp, compareToMe, cptor);
		if (foundAt > -1) {
		    found = datiStp.get(foundAt);
		    ppm.setDatiCollegamento(found);
		    //lettura delle prorpietà su cui si filtra e si ordina per evitare LazyInitializationException
		    if (found != null && found.getInventarioprocedimenti() != null) {
			Object val = found.getInventarioprocedimenti().getProcedimento();
			val = found.getInventarioprocedimenti().getId().getCodice();
		    }
		}
	    }
	    retList.add(ppm);
	}
	return retList;
    }

    @Override
    public ProcedimentoProcediMarche getProcedimento(Integer idProcedimarche) {

	ProcedimentoProcediMarche ppm = null;
	if (idProcedimarche != null) {
	    TipoProcedimentoGenerale tpg = this.getProcedimentoPM(idProcedimarche);
	    if (tpg != null) {
		ppm = new ProcedimentoProcediMarche();
		StpEndoTipo2 stp = this.stpEndoTipo2Service.findbyStpCodice(idProcedimarche);
		ppm.setDatiCollegamento(stp);
		ppm.setDatiProcedimento(tpg);
		if (stp != null) {
		    //se non è un record di un procedimento PM che è stato scollegato
		    if (stp.getInventarioprocedimenti() != null && stp.getInventarioprocedimenti().getId() != null
			    && stp.getInventarioprocedimenti().getId().getCodice() != null) {
			//popolo l'oggetto con i dati specifici dell'ente
			TipoProcedimentoSpecifico tps = new TipoProcedimentoSpecifico();
			tps.setIdTipoProcedimentoGenerico(idProcedimarche);
			if (stp.getOggetti() != null && stp.getOggetti().getId().getCodice() != null) {
			    // recupero i dati specifici dall'oggetto json serializzato in OGGETTI
			    Oggetti o = this.oggettiService.findById(new PkId(stp.getOggetti().getId().getCodice()));
			    if (o != null) {
				try {
				    String json = new String(stp.getOggetti().getOggetto(), "UTF-8");
				    if (StringUtils.isBlank(json)) {
					tps = this.popolaDatiSpecifici(stp, tps);
				    } else {
					CustomJsonSerializer cjs = getJsonSerializer();
					tps = (TipoProcedimentoSpecifico) cjs.deserialize(json, TipoProcedimentoSpecifico.class);
				    }
				} catch (UnsupportedEncodingException e) {
				    String message = "Errore nella deserializzazione del formato json dei dati specifici del procedimento.";
				    //log.error("getProcedimento -  " + message);
				    throw new RuntimeException(message, e);
				}
			    }
			}
			tps.setCfEnte(this.getCfEnte());
			ppm.setDatiSpecifici(tps);
		    }
		}
	    }
	}
	return ppm;
    }

    @Override
    public List<IdDescrizione> getSerieArchivistiche() {

	List<IdDescrizione> retList = null;
	try {
	    WebClient client = this.createWebClient("/SerieArchivistica");
	    Response r = client.accept("application/json").get();
	    String value = IOUtils.toString((InputStream) r.getEntity());
	    CustomJsonSerializer cjs = getJsonSerializer();
	    retList = (List<IdDescrizione>) cjs.deserialize(value, IdDescrizione.class);
	} catch (IOException e) {
	    throw new RuntimeException("Errore nella lettura dei dati json del servizio /SerieArchivistica", e);
	}
	return retList;
    }

    @Override
    public List<IdDescrizione> getTipiFascicolo() {

	List<IdDescrizione> retList = null;
	try {
	    WebClient client = this.createWebClient("/TipoFascicolo");
	    Response r = client.accept("application/json").get();
	    String value = IOUtils.toString((InputStream) r.getEntity());
	    CustomJsonSerializer cjs = getJsonSerializer();
	    retList = (List<IdDescrizione>) cjs.deserialize(value, IdDescrizione.class);
	} catch (IOException e) {
	    throw new RuntimeException("Errore nella lettura dei dati json del servizio /TipoFascicolo", e);
	}
	return retList;
    }

    @Override
    public ProcedimentoProcediMarche collegaProcedimento(TipoProcedimentoSpecifico datiSpecificiIn) {

	Integer idInventarioproc = null;
	ProcedimentoProcediMarche ppm = new ProcedimentoProcediMarche();
	if (StringUtils.isNotBlank(datiSpecificiIn.getIdProcedimentoEnte())) {
	    try {
		idInventarioproc = Integer.parseInt(datiSpecificiIn.getIdProcedimentoEnte());
	    } catch (NumberFormatException e) {
	    }
	}
	Integer idProcediMarche = datiSpecificiIn.getIdTipoProcedimentoGenerico();
	Inventarioprocedimenti invProc = this.inventarioprocedimentiService.findById(new PkId(idInventarioproc));
	if (invProc == null) {
	    throw new RuntimeException("Procedimento con id " + datiSpecificiIn.getIdProcedimentoEnte() + " inesistente");
	}
	if (invProc.getDisabilitato()) {
	    throw new RuntimeException("Impossibile collegare il procedimento selezionato perché disabilitato");
	}
	//TODO verificare che si tratti di un endo principale di competenza del comune enon di un endo di un ente terzo (amministrazione == amministrazione corrente di sessione)
	StpEndoTipo2 stp = this.stpEndoTipo2Service.findByInventarioproc(idInventarioproc);
	//se l'endoprocedimento é già collegato lancio eccezione 
	if (stp != null) {
	    throw new RuntimeException(
		    "Impossibile collegare il procedimento selezionato perchè é già collegato ad un altro procedimento di ProcediMarche");
	}
	//se il codice regionale è già collegato ad altro endo lancio eccezione
	stp = this.stpEndoTipo2Service.findbyStpCodice(idProcediMarche);
	if (stp != null && stp.getInventarioprocedimenti() != null && stp.getInventarioprocedimenti().getId() != null
		&& stp.getInventarioprocedimenti().getId().getCodice() != null) {
	    throw new RuntimeException("Il procedimento di ProcediMarche è già collegato ad altro procedimento locale");
	}
	//verifico se esiste già un record in stp_endo per collegare il procedimento PM con questo id 
	stp = this.stpEndoTipo2Service.findbyStpCodice(idProcediMarche, StpEndoTipo2Service.TIPO_ENDO);
	if (stp == null) {
	    //inserisco il collagemento in stp_endo_tipo2
	    stp = new StpEndoTipo2();
	}
	stp.setInventarioprocedimenti(invProc);
	stp.setCodiceStp(idProcediMarche);
	//stp.setCodiceEndoRegionale(datiSpecificiIn.getIdTipoProcedimentoSpecifico().toString());
	stp.setTipo(StpEndoTipo2Service.TIPO_ENDO);
	List<Integer> queryIdProc = new ArrayList<Integer>();
	queryIdProc.add(idInventarioproc);
	//recupero le info sul nodo dell'abero a cui è collegato il procediemnto selezionato
	List<AlberoprocEndo> nodiAlbero = this.alberoprocEndoService.findByEndoprocedimentiAndSoftware(queryIdProc, ORMHelper.getSoftware(), true);
	AlberoprocEndo myNodo = null;
	Iterator<AlberoprocEndo> endoIter = nodiAlbero.iterator();
	while (endoIter.hasNext() && myNodo == null) {
	    AlberoprocEndo nodo = endoIter.next();
	    if (!nodo.getFlagPrincipale()) {
		continue;
	    }
	    /*
	    if (nodo.getAlberoproc().getScPadre()) {
	    continue;
	    }
	    */
	    myNodo = nodo;
	}
	if (myNodo == null) {
	    String errMsg = "impossibile individuare il collegamento all'alberoproc per il procedimento con id " +
		    idInventarioproc +
		    " probabilmemte si tratta di un endo di competenza di enti terzi o di un endo non collegato all'albero.";
	    log.warn("collegaProcedimento - " + errMsg);
	    stp.setAlberoproc(new Alberoproc());
	    //per ora accettamo il collegamento anche con endo non principali o non collegati in ALBEROPROC_ENDO
	    //throw new RuntimeException(errMsg);
	} else {
	    stp.setAlberoproc(myNodo.getAlberoproc());
	}
	//carico eventuali dati specifici salvati in precedenza (il procedimento PM poteva essere già stato collegato ad altro procedimento VBG)
	Oggetti o = stp.getOggetti();
	TipoProcedimentoSpecifico datiSpecificiOut = this.caricaDatiSpecifici(o);
	if (datiSpecificiOut == null) {
	    datiSpecificiOut = datiSpecificiIn;
	} else {
	    datiSpecificiOut.setIdProcedimentoEnte(datiSpecificiIn.getIdProcedimentoEnte());
	    datiSpecificiOut.setIdTipoProcedimentoGenerico(datiSpecificiIn.getIdTipoProcedimentoGenerico());
	    datiSpecificiOut.setIdTipoProcedimentoSpecifico(datiSpecificiIn.getIdTipoProcedimentoSpecifico());
	}
	//popolo l'oggetto dei dati specifici dell'ente con i valori recuperati da VBG ove possibile
	this.popolaDatiSpecifici(stp, datiSpecificiOut);
	o = this.salvaDatiJson(datiSpecificiOut, o);
	stp.setOggetti(o);
	if (stp.getId().getCodice() == null) {
	    this.stpEndoTipo2Service.insert(stp);
	} else {
	    this.stpEndoTipo2Service.update(stp);
	}
	ppm.setDatiCollegamento(stp);
	ppm.setDatiSpecifici(datiSpecificiOut);
	return ppm;
    }

    @Override
    public void scollegaProcedimento(Integer idStpEndo) {

	StpEndoTipo2 stp = this.stpEndoTipo2Service.findById(new PkId(idStpEndo));
	if (stp == null) {
	    throw new RuntimeException("impossibile individuare il collegamento da cancellare.");
	}
	//scollego alberoproc
	stp.setAlberoproc(null);
	//scollego inventariproc
	stp.setInventarioprocedimenti(null);
	//non mantengo i vecchi dati JSON
	Oggetti o = stp.getOggetti();
	if (o != null && o.getId() != null && o.getId().getCodice() != null) {
	    stp.setOggetti(null);
	    this.oggettiService.delete(o);
	}
	this.stpEndoTipo2Service.update(stp);
    }

    @Override
    public TipoProcedimentoSpecifico getDatiLocaliProcedimento(StpEndoTipo2 endoTipo2) {

	TipoProcedimentoSpecifico tps = null;
	if (endoTipo2 != null) {
	    if (endoTipo2.getOggetti() == null || endoTipo2.getOggetti().getId() == null || endoTipo2.getOggetti().getId().getCodice() == null) {
		tps = this.popolaDatiSpecifici(endoTipo2, new TipoProcedimentoSpecifico());
	    } else {
		CustomJsonSerializer cjs = getJsonSerializer();
		byte[] jsonData = endoTipo2.getOggetti().getOggetto();
		try {
		    tps = (TipoProcedimentoSpecifico) cjs.deserialize(new String(jsonData, "UTF-8"), TipoProcedimentoSpecifico.class);
		} catch (Exception e) {
		    String err = "Errore nella deserializzazione dei dati json del procedimento specifici dell'ente: " + e;
		    throw new RuntimeException("getDatiLocaliProcedimento - " + err, e);
		}
	    }
	}
	return tps;
    }

    @Override
    public void salvaDatiLocaliProcedimento(ProcedimentoProcediMarche datiProc) {

	if (datiProc != null) {
	    if (datiProc.getDatiCollegamento() != null) {
		StpEndoTipo2 stp = datiProc.getDatiCollegamento();
		if (datiProc.getDatiSpecifici() != null && stp != null && stp.getId() != null && stp.getId().getCodice() != null) {
		    stp = this.stpEndoTipo2Service.findById(stp.getId());
		    this.salvaDatiJson(datiProc.getDatiSpecifici(), stp.getOggetti());
		    if (datiProc.getDatiSpecifici().getIdTipoProcedimentoSpecifico() != null) {
			stp.setCodiceEndoRegionale(datiProc.getDatiSpecifici().getIdTipoProcedimentoSpecifico().toString());
			this.stpEndoTipo2Service.update(stp);
		    }
		} else {
		    throw new RuntimeException("Operazione fallita. Non è presente nessun dato da salvare.");
		}
	    } else {
		throw new RuntimeException(
			"Impossibile salvare i dati locali del procedimento perché il procedimento di ProcediMarche non è collegato a nessun procedimento di VBG.");
	    }
	}
    }

    @Override
    public void pubblicaProcedimento(ProcedimentoProcediMarche datiProc) {

	// invio i dati locali a PM
	try {
	    if (NumberUtils.isDigits(datiProc.getDatiCollegamento().getCodiceEndoRegionale())) {
		datiProc.getDatiSpecifici().setIdTipoProcedimentoSpecifico(Integer.parseInt(datiProc.getDatiCollegamento().getCodiceEndoRegionale()));
	    } else if (StringUtils.isBlank(datiProc.getDatiCollegamento().getCodiceEndoRegionale())) {
		datiProc.getDatiSpecifici().setIdTipoProcedimentoSpecifico(null);
	    }
	    this.pubblicaProcedimentoSpecificoPM(datiProc.getDatiSpecifici());
	    if (datiProc.getDatiSpecifici() != null && datiProc.getDatiSpecifici().getIdTipoProcedimentoSpecifico() != null) {
		datiProc.getDatiCollegamento().setCodiceEndoRegionale(datiProc.getDatiSpecifici().getIdTipoProcedimentoSpecifico().toString());
	    }
	    // salvo i dati locali del procedimento nel db
	    this.salvaDatiLocaliProcedimento(datiProc);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void spubblicaProcedimento(ProcedimentoProcediMarche datiProc) {

	// invio i dati locali a PM
	try {
	    //elimino l'idProcedimento specifico che ho prcedentemente memorizzato in STP_ENDO_TIPO2.CODICE_ENDO_REGIONALE
	    //NON elimino l'idProcedimento generico, i dati json memorizzati localmente e il collegamento al procedimento di VBG
	    StpEndoTipo2 stp = this.stpEndoTipo2Service.findById(datiProc.getDatiCollegamento().getId());
	    if (stp != null) {
		this.spubblicaProcedimentoSpecificoPM(datiProc.getDatiSpecifici());
		stp.setCodiceEndoRegionale(null);
		this.stpEndoTipo2Service.update(stp);
		datiProc.setDatiCollegamento(stp);
	    }
	    //if (datiProc.getDatiSpecifici() != null) {
	    datiProc.getDatiSpecifici().setIdTipoProcedimentoSpecifico(null);
	    //}
	    // salvo i dati locali del procedimento nel db
	    this.salvaDatiLocaliProcedimento(datiProc);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public String buildUrlNuovaIstanzaFrontOffice(Integer idProc) {

	Alberoproc iproc = alberoprocService.findById(new PkId(idProc));
	if (iproc != null && iproc.getId() != null && iproc.getId().getCodice() != null) {
	    String arUrl = this.verticalizzazioneAreaRiservataService.isAttiva()
		    ? this.verticalizzazioneAreaRiservataService.getUrlAvvioProcedimento()
		    : null;
	    if (StringUtils.isNotBlank(arUrl)) {
		arUrl = arUrl.replace("{alias}", ORMHelper.getIdcomuneAlias());
		arUrl = arUrl.replace("{software}", ORMHelper.getSoftware());
		arUrl = arUrl.replace("{codiceintervento}", idProc.toString());
	    }
	    return arUrl;
	}
	return "";
    }

    private TipoProcedimentoSpecifico popolaDatiSpecifici(StpEndoTipo2 endoT2, TipoProcedimentoSpecifico datiSpecifici) {

	//prepopolo tutti i campi che possono essere recuperati dal db di vbg
	if (datiSpecifici == null) {
	    datiSpecifici = new TipoProcedimentoSpecifico();
	}
	//ids
	if (NumberUtils.isDigits(endoT2.getCodiceEndoRegionale())) {
	    datiSpecifici.setIdTipoProcedimentoSpecifico(new Integer(endoT2.getCodiceEndoRegionale()));
	}
	//dati da inventarioprocedimenti 
	Inventarioprocedimenti invProc = endoT2.getInventarioprocedimenti();
	if (invProc != null) {
	    if (StringUtils.isBlank(datiSpecifici.getIdProcedimentoEnte())) {
		datiSpecifici.setIdProcedimentoEnte(invProc.getId().getCodice().toString());
	    }
	    if (datiSpecifici.getIdTipoProcedimentoGenerico() == null) {
		datiSpecifici.setIdTipoProcedimentoGenerico(endoT2.getCodiceStp());
	    }
	    datiSpecifici.setNomeProcedimentoEnte(invProc.getProcedimento());
	    // dati da da inventarioprocedimenti.amministrazioni
	    if (invProc.getAmministrazioni() != null && invProc.getAmministrazioni().getId() != null
		    && invProc.getAmministrazioni().getId().getCodice() != null) {
		String uoComp = StringUtils.isNotBlank(invProc.getAmministrazioni().getDescrizioneEstesa())
			? invProc.getAmministrazioni().getDescrizioneEstesa()
			: invProc.getAmministrazioni().getAmministrazione();
		datiSpecifici.setUoCompetenzaIstruttoria(uoComp);
		AmministrazioniHelper ammH = new AmministrazioniHelper();
		ammH.setAmministrazioni(invProc.getAmministrazioni());
		datiSpecifici.setUoRecapitiIstruttoria(ammH.getRecapitiReferenteIstruttoria());
	    }
	    //dati da inventarioprocedimenti.
	    Tempificazioni temp = invProc.getTempificazione();
	    if (temp != null && temp.getId() != null && temp.getId().getCodice() != null) {
		datiSpecifici.setTermineConclusione(temp.getTempificazione());
	    }
	    //URL che attiva il procedimento sul front
	    datiSpecifici.setLinkServizio(this.buildUrlNuovaIstanzaFrontOffice(invProc.getId().getCodice()));
	}
	//dati da alberoproc
	Alberoproc aproc = endoT2.getAlberoproc();
	if (aproc != null) {
	    //i dati del responsabile esistono ma non vengono trascritti in automatico perché PM ha due campi distinti 
	    //uno per il nome e uno per il cognome mentre in VBG abbiamo un unico campo nome e cognome. Dalla UI è comunque possibile recuperare i valori.
	    Tipiprocedure tp = aproc.getTipoProcedura();
	    //ALBEROPROC.TIPIPROCEDURE.GIORNI 
	    if (tp != null && tp.getId() != null && tp.getId().getCodice() != null) {
		datiSpecifici.setMaxGiorniTermine(aproc.getTipoProcedura().getGiorni());
	    }
	}
	datiSpecifici.setCfEnte(getCfEnte());
	return datiSpecifici;
    }

    private TipoProcedimentoSpecifico caricaDatiSpecifici(Oggetti datiJson) {

	TipoProcedimentoSpecifico tps = null;
	if (datiJson != null && datiJson.getId() != null && datiJson.getId().getCodice() != null) {
	    datiJson = this.oggettiService.findById(new PkId(datiJson.getId().getCodice()));
	    try {
		String json = new String(datiJson.getOggetto(), "UTF-8");
		if (StringUtils.isNotBlank(json)) {
		    CustomJsonSerializer cjs = getJsonSerializer();
		    tps = (TipoProcedimentoSpecifico) cjs.deserialize(json, TipoProcedimentoSpecifico.class);
		}
	    } catch (UnsupportedEncodingException e) {
		String message = "Errore nella deserializzazione del formato json dei dati specifici del procedimento.";
		//log.error("getProcedimento -  " + message);
		throw new RuntimeException(message, e);
	    }
	}
	return tps;
    }

    private Oggetti salvaDatiJson(TipoProcedimentoSpecifico tps, Oggetti o) {

	if (tps != null) {
	    if (o == null)
		o = new Oggetti();
	    try {
		CustomJsonSerializer jser = getJsonSerializer();
		String json = jser.serialize(tps);
		byte[] jsonData = json.getBytes();
		o.setOggetto(jsonData);
		o.setNomefile("PM_dizionario_dati_locali_" + ORMHelper.getIdcomune() + "_" + tps.getIdTipoProcedimentoGenerico() + ".json");
		if (o.getId() == null || o.getId().getCodice() == null) {
		    this.oggettiService.insert(o);
		} else {
		    this.oggettiService.update(o);
		}
	    } catch (Exception e) {
		String errMsg = "Errore nella memorizzazione dei dati json del procedimento specifici dell'ente: " + e.toString();
		//log.error("collegaProcedimento - " + errMsg, e);
		throw new RuntimeException(errMsg, e);
	    }
	}
	return o;
    }

    @Override
    public boolean isVerticalizzazioneConfigurata() {

	return StringUtils.isNotBlank(this.verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_URL));
    }

    private String getServiceUrl() {

	String url = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_URL);
	if (StringUtils.isBlank(url)) {
	    throw new RuntimeException("Non è stata trovata la configurazione. Il parametro " +
		    WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_URL +
		    " della regola " +
		    WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE +
		    " non e' stato impostato correttamente.");
	}
	return url;
    }

    private String getServiceUsr() {

	String url = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_USR);
	return url;
    }

    private String getServicePwd() {

	String url = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_PWD);
	return url;
    }

    private String getCfEnte() {

	String cf = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE,
		WebConstants.VERTICALIZZAZIONE_PROCEDI_MARCHE_CF_ENTE);
	return cf;
    }

    private List<TipoProcedimentoGenerale> getProcedimentiPM() {

	List<TipoProcedimentoGenerale> retList = null;
	try {
	    WebClient client = this.createWebClient("/TipoProcedimentoGenerale");
	    Response r = client.accept("application/json").get();
	    Status rStatus = Status.fromStatusCode(r.getStatus());
	    if (rStatus.getFamily() != Family.SUCCESSFUL) {
		throw new RuntimeException("errore HTTP " + rStatus.getStatusCode() + " " + StringUtils.defaultString(rStatus.getReasonPhrase()));
	    }
	    String value = IOUtils.toString((InputStream) r.getEntity());
	    CustomJsonSerializer cjs = getJsonSerializer();
	    retList = (List<TipoProcedimentoGenerale>) cjs.deserialize(value, TipoProcedimentoGenerale.class);
	} catch (IOException e) {
	    throw new RuntimeException("Errore nella lettura dei dati json del servizio /TipoProcedimentoGenerale", e);
	}
	return retList;
    }

    private TipoProcedimentoGenerale getProcedimentoPM(Integer idPm) {

	TipoProcedimentoGenerale p = null;
	try {
	    WebClient client = this.createWebClient("/TipoProcedimentoGenerale");
	    Response r = client.path("/" + idPm).accept("application/json").get();
	    Status rStatus = Status.fromStatusCode(r.getStatus());
	    if (rStatus.getFamily() != Family.SUCCESSFUL) {
		throw new RuntimeException("errore HTTP " + rStatus.getStatusCode() + " " + StringUtils.defaultString(rStatus.getReasonPhrase()));
	    }
	    //String value = IOUtils.toString((InputStream) r.getEntity());
	    String value = IOUtils.toString((InputStream) r.getEntity(), "UTF-8");
	    if (log.isInfoEnabled()) {
		log.info("getProcedimentoPM - recuperati dati generali per il procedimento con id = {} {}", idPm, value);
	    }
	    CustomJsonSerializer cjs = getJsonSerializer();
	    p = (TipoProcedimentoGenerale) cjs.deserialize(value, TipoProcedimentoGenerale.class);
	} catch (IOException e) {
	    throw new RuntimeException("Errore nella lettura dei dati json del servizio /TipoProcedimentoGenerale", e);
	}
	return p;
    }

    private Integer pubblicaProcedimentoSpecificoPM(TipoProcedimentoSpecifico datiProc) {

	Integer idSpec = null;
	if (datiProc != null) {
	    try {
		WebClient client = this.createWebClient("/TipoProcedimentoSpecifico");
		CustomJsonSerializer cjs = getJsonSerializer();
		if (StringUtils.isBlank(datiProc.getCfEnte())) {
		    datiProc.setCfEnte(this.getCfEnte());
		}
		String json = cjs.serialize(datiProc);
		Response r = null;
		if (log.isInfoEnabled()) {
		    log.info("pubblicaProcedimentoSpecificoPM - pubblicazione su PM dei dati per il procedimento {}: {}",
			    datiProc.getIdTipoProcedimentoGenerico(), json);
		}
		if (datiProc.getIdTipoProcedimentoSpecifico() == null) {
		    r = client.path("/" + datiProc.getIdTipoProcedimentoGenerico()).type("application/json").accept("application/json").post(json);
		} else {
		    r = client.path("/" + datiProc.getIdTipoProcedimentoSpecifico()).type("application/json").accept("application/json").put(json);
		}
		String value = IOUtils.toString((InputStream) r.getEntity());
		Status httpStatus = Status.fromStatusCode(r.getStatus());
		if (log.isInfoEnabled()) {
		    log.info("pubblicaProcedimentoSpecificoPM - esito invocazione servizio: id_procedimento_specifico = " + value);
		}
		if (httpStatus.getFamily().equals(Family.SUCCESSFUL)) {
		    //mi aspetto di ricevere l'IdProcedimentoSpecifico e me lo memorizzo 
		    try {
			idSpec = Integer.parseInt(value);
			datiProc.setIdTipoProcedimentoSpecifico(idSpec);
		    } catch (NumberFormatException e) {
			throw new RuntimeException("L'id del procedimento specifico restituito da ProcediMarche non è in formato numerico: " + value);
		    }
		} else {
		    StringBuilder httpErr = new StringBuilder().append(httpStatus.getStatusCode()).append(" ").append(httpStatus.name());
		    if (StringUtils.isNotBlank(value)) {
			httpErr.append(" ");
			CodiceDescrizione cd = null;
			try {
			    cd = (CodiceDescrizione) cjs.deserialize(value, CodiceDescrizione.class);
			} catch (Exception e) {
			    // la response non contiene codice e descrizione --> la visualizzo così com'è
			}
			if (cd != null && StringUtils.isNotBlank(cd.getDescrizione())) {
			    if (StringUtils.isNotBlank(cd.getCodice())) {
				httpErr.append(cd.getCodice()).append('-');
			    }
			    httpErr.append(cd.getDescrizione());
			} else {
			    httpErr.append(value);
			}
		    }
		    log.error("pubblicaProcedimentoSpecificoPM - invocazione del servizio /TipoProcedimentoSpecifico fallita: " + httpErr);
		    throw new RuntimeException("Il servizio JSON ha restituito il seguente errore: " + httpErr);
		}
	    } catch (IOException e) {
		throw new RuntimeException("Errore nella lettura dei dati json del servizio /TipoProcedimentoGenerale", e);
	    }
	} else {
	    throw new RuntimeException("Impossibile pubblicare i dati specifici del procedimento: dati non presenti.");
	}
	return idSpec;
    }

    private Integer spubblicaProcedimentoSpecificoPM(TipoProcedimentoSpecifico datiProc) {

	Integer idSpec = null;
	if (datiProc != null && datiProc.getIdTipoProcedimentoSpecifico() != null) {
	    try {
		WebClient client = this.createWebClient("/TipoProcedimentoSpecifico");
		Response r = null;
		if (log.isInfoEnabled()) {
		    log.info("spubblicaProcedimentoSpecificoPM - cancellazione da PM dei dati del procedimento {}",
			    datiProc.getIdTipoProcedimentoGenerico());
		}
		r = client.path("/" + datiProc.getIdTipoProcedimentoSpecifico()).type("application/json").accept("application/json").delete();
		Status httpStatus = Status.fromStatusCode(r.getStatus());
		if (log.isInfoEnabled()) {
		    log.info("spubblicaProcedimentoSpecificoPM - esito invocazione servizio: status " +
			    httpStatus.getStatusCode() +
			    " " +
			    StringUtils.defaultString(httpStatus.getReasonPhrase()));
		}
		if (httpStatus.getFamily().equals(Family.SUCCESSFUL)) {
		    //mi cancello l'IdProcedimentoSpecifico mi aspetto che se dovessi pubblicarlo di nuovo me ne sarà restituito uno nuovo (verificare)
		    datiProc.setIdTipoProcedimentoSpecifico(null);
		} else {
		    StringBuilder httpErr = new StringBuilder().append(httpStatus.getStatusCode()).append(" ").append(httpStatus.name());
		    httpErr.append(" ").append(StringUtils.defaultString(httpStatus.getReasonPhrase()));
		    log.error("spubblicaProcedimentoSpecificoPM - invocazione del servizio /TipoProcedimentoSpecifico fallita: " + httpErr);
		    throw new RuntimeException("Il servizio JSON ha restituito il seguente errore: " + httpErr);
		}
	    } catch (IOException e) {
		throw new RuntimeException("Errore nella lettura dei dati json del servizio /TipoProcedimentoGenerale", e);
	    }
	} else {
	    throw new RuntimeException("Impossibile pubblicare i dati specifici del procedimento: dati non presenti.");
	}
	return idSpec;
    }

    private TipoProcedimentoGenerale getProcedimentoFake(int idx) {

	TipoProcedimentoGenerale tpg = new TipoProcedimentoGenerale();
	tpg.setId(new Integer(idx));
	tpg.setAmministrazioneCompetente("AmministrazioneCompetente" + idx);
	tpg.setCategoria("Categoria" + idx);
	List<String> list = new ArrayList<String>();
	list.add("CategorieAnticorruzione_1_" + idx);
	list.add("CategorieAnticorruzione_2_" + idx);
	tpg.setCategorieAnticorruzione(list);
	list = new ArrayList<String>();
	list.add("CategorieDestinatario_1_" + idx);
	list.add("CategorieDestinatario_2_" + idx);
	tpg.setCategorieDestinatario(list);
	/*
	 * tpg.setCategorieAnticorruzione(new String[] { "CategorieAnticorruzione_1_" + idx,
	 * "CategorieAnticorruzione_2_" + idx }); tpg.setCategorieDestinatario(new String[] { "CategorieDestinatario_1_"
	 * + idx, "CategorieDestinatario_2_" + idx });
	 */
	tpg.setDescrizione("descrizione_" + idx);
	tpg.setGiustificazioneRegime("GiustificazioneRegime " + idx);
	tpg.setMissione("missione " + idx);
	tpg.setModalitaConclusione("modalitaConclusione " + idx);
	tpg.setNome("nome " + idx);
	tpg.setPubblicato(true);
	tpg.setRiferimentiNormativi("riferimentiNormativi " + idx);
	tpg.setRischioCorruzione(false);
	tpg.setSettoreAttivita("settoreAttivita " + idx);
	tpg.setSpecificheDimensionali("specificheDimensionali " + idx);
	tpg.setStrumentiTutela("strumentiTutela " + idx);
	tpg.setTerminiConclusione("terminiConclusione " + idx);
	tpg.setTipologiaRegime("tipologiaRegime " + idx);
	return tpg;
    }

    class CodiceStpComparator implements Comparator<StpEndoTipo2> {

	@Override
	public int compare(StpEndoTipo2 o1, StpEndoTipo2 o2) {

	    if (o1 == null || o1.getCodiceStp() == null)
		return -1;
	    if (o2 == null || o2.getCodiceStp() == null)
		return 1;
	    return ((StpEndoTipo2) o1).getCodiceStp().compareTo(((StpEndoTipo2) o2).getCodiceStp());
	}
    }

    private WebClient createWebClient(String path) throws MalformedURLException {

	String user = this.getServiceUsr();
	String password = StringUtils.isNotBlank(this.getServiceUsr()) ? StringUtils.defaultString(this.getServicePwd()) : null;
	URL serviceUrl = new URL(getServiceUrl() + path);
	WebClient client = WebClient.create(serviceUrl.toExternalForm(), user, password, null);
	if (serviceUrl.getProtocol().equalsIgnoreCase("HTTPS")) {
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    TLSClientParameters params = conduit.getTlsClientParameters();
	    if (params == null) {
		params = new TLSClientParameters();
		conduit.setTlsClientParameters(params);
	    }
	    params.setTrustManagers(new TrustManager[] { new TrustAllX509TrustManager() });
	    params.setDisableCNCheck(true);
	}
	return client;
    }

    private CustomJsonSerializer getJsonSerializer() {

	CustomJsonSerializer jSer = new CustomJsonSerializer();
	jSer.setJsonFormat(JsonFormat.CAPITAL_CAMEL_CASE);
	jSer.setUseSuperclassFields(true);
	jSer.setDropEmptyValues(true);
	return jSer;
    }
}
