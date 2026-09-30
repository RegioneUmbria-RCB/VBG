package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.CatastoService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Component
public class IstanzeFilterUtils {

    private static final Logger log = LoggerFactory.getLogger(IstanzeFilterUtils.class);
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AreeService areeService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private CatastoService catastoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SettoriService settoriService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private StradariocoloreService stradariocoloreService;
    @Autowired
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private StradarioService stradarioService;

    public IstanzeFilter createIstanzeFilter(byte[] bdata) {

	String filtroString = new String(bdata);
	IstanzeFilter istanzeFilter = new IstanzeFilter();
	String[] filtri = filtroString.split("&");
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> mapSchedeFilter = new HashMap<String, String>();
	for (String string : filtri) {
	    String[] campi = string.split("=");
	    if (!campi[0].contains("schedaDinamicaFilter")) {
		if (campi.length > 1) {
		    map.put(campi[0], campi[1]);
		} else {
		    map.put(campi[0], "");
		}
	    } else {
		String key = StringUtils.remove(campi[0], "istanzeFilter.schedaDinamicaFilter.");
		if (campi.length > 1) {
		    mapSchedeFilter.put(key, campi[1]);
		} else {
		    mapSchedeFilter.put(key, "");
		}
	    }
	}
	// 
	//////////Setto il comune //////////////////////////////
	String codiceComune = map.get("istanzeFilter.comune.codicecomune");
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni comune = comuniService.findById(codiceComune);
	    istanzeFilter.setComune(comune);
	}
	//////////Setto numero istanza //////////////////////////////
	String numeroIstanza = map.get("istanzeFilter.numeroistanza");
	if (StringUtils.isNotBlank(numeroIstanza)) {
	    istanzeFilter.setNumeroistanza(numeroIstanza);
	}
	// codicedomandastc
	String codicedomandastc = map.get("istanzeFilter.codicedomandastc");
	if (StringUtils.isNotBlank(codicedomandastc)) {
	    istanzeFilter.setCodicedomandastc(codicedomandastc);
	}
	//cercasolodomandestc
	String cercasolodomandestc = map.get("istanzeFilter.cercasolodomandestc");
	if (StringUtils.isNotBlank(cercasolodomandestc)) {
	    istanzeFilter.setCercasolodomandestc(BooleanUtils.toBoolean(cercasolodomandestc));
	}
	//codicepraticatel
	String codicepraticatel = map.get("istanzeFilter.codicepraticatel");
	if (StringUtils.isNotBlank(codicepraticatel)) {
	    istanzeFilter.setCodicepraticatel(codicepraticatel);
	}
	// istanzeFilter.dallaData
	String dallaData = map.get("istanzeFilter.dallaData");
	if (StringUtils.isNotBlank(dallaData)) {
	    Date _dallaData = Utilities.parseDateString(dallaData, false);
	    istanzeFilter.setDallaData(_dallaData);
	}
	// istanzeFilter.allaData
	String allaData = map.get("istanzeFilter.allaData");
	if (StringUtils.isNotBlank(allaData)) {
	    Date _allaData = Utilities.parseDateString(allaData, false);
	    istanzeFilter.setAllaData(_allaData);
	}
	//istanzeFilter.numeroprotocollo
	String numeroprotocollo = map.get("istanzeFilter.numeroprotocollo");
	if (StringUtils.isNotBlank(numeroprotocollo)) {
	    istanzeFilter.setNumeroprotocollo(numeroprotocollo);
	}
	//istanzeFilter.cercaprotocolloinmovimenti=true
	//_istanzeFilter.cercaprotocolloinmovimenti=on
	String cercaprotocolloinmovimenti = map.get("istanzeFilter.cercaprotocolloinmovimenti");
	if (StringUtils.isNotBlank(cercaprotocolloinmovimenti)) {
	    istanzeFilter.setCercaprotocolloinmovimenti(BooleanUtils.toBoolean(cercaprotocolloinmovimenti));
	}
	//istanzeFilter.dallaDataProtocollo
	String dallaDataProtocollo = map.get("istanzeFilter.dallaDataProtocollo");
	if (StringUtils.isNotBlank(dallaDataProtocollo)) {
	    Date _dallaDataProtocollo = Utilities.parseDateString(dallaDataProtocollo, false);
	    istanzeFilter.setDallaDataProtocollo(_dallaDataProtocollo);
	}
	//istanzeFilter.allaDataProtocollo
	String allaDataProtocollo = map.get("istanzeFilter.allaDataProtocollo");
	if (StringUtils.isNotBlank(allaDataProtocollo)) {
	    Date _allaDataProtocollo = Utilities.parseDateString(allaDataProtocollo, false);
	    istanzeFilter.setAllaDataProtocollo(_allaDataProtocollo);
	}
	//istanzeFilter.soggettiistanza=FAGIANO MARCO
	String soggettiistanza = map.get("istanzeFilter.soggettiistanza");
	if (StringUtils.isNotBlank(soggettiistanza)) {
	    istanzeFilter.setSoggettiistanza(soggettiistanza);
	}
	//istanzeFilter.cercaInAnagrafestorico=true
	//_istanzeFilter.cercaInAnagrafestorico=on
	String cercaInAnagrafestorico = map.get("istanzeFilter.cercaInAnagrafestorico");
	if (StringUtils.isNotBlank(cercaInAnagrafestorico)) {
	    istanzeFilter.setCercaInAnagrafestorico(BooleanUtils.toBoolean(cercaInAnagrafestorico));
	}
	//istanzeFilter.richiedente.descrizioneRichiedente=
	// istanzeFilter.richiedente.id.codice=
	String codiceRichiedente = map.get("istanzeFilter.richiedente.id.codice");
	if (StringUtils.isNotBlank(codiceRichiedente)) {
	    Anagrafe rich = null;
	    try {
		rich = anagrafeService.findById(new PkId(Integer.parseInt(codiceRichiedente)));
	    } catch (NumberFormatException nfe) {
		log.error("createIstanzeFilter# richiedente: il codice non è numerico");
	    }
	    istanzeFilter.setRichiedente(rich);
	}
	//istanzeFilter.soggettiistanzaPivaCF=00000000001
	String soggettiistanzaPivaCF = map.get("istanzeFilter.soggettiistanzaPivaCF");
	if (StringUtils.isNotBlank(soggettiistanzaPivaCF)) {
	    istanzeFilter.setSoggettiistanzaPivaCF(soggettiistanzaPivaCF);
	}
	//istanzeFilter.domicilioElettronico=
	String domicilioElettronico = map.get("istanzeFilter.domicilioElettronico");
	if (StringUtils.isNotBlank(domicilioElettronico)) {
	    istanzeFilter.setDomicilioElettronico(domicilioElettronico);
	}
	//istanzeFilter.tipiarchivioistanza.archivio=ARCHIVIO SPORTELLO
	//istanzeFilter.tipiarchivioistanza.id.codice=18
	String tipiarchivioistanza = map.get("istanzeFilter.tipiarchivioistanza.id.codice");
	if (StringUtils.isNotBlank(tipiarchivioistanza)) {
	    Tipiarchivioistanze tipiarchivioistanze = null;
	    try {
		tipiarchivioistanze = tipiarchivioistanzeService.findById(new PkId(Integer.parseInt(tipiarchivioistanza)));
	    } catch (NumberFormatException nfe) {
		log.error("createIstanzeFilter# tipiarchivioistanze: il codice non è numerico");
	    }
	    istanzeFilter.setTipiarchivioistanza(tipiarchivioistanze);
	}
	//istanzeFilter.posizionearchivio=12
	String posizionearchivio = map.get("istanzeFilter.posizionearchivio");
	if (StringUtils.isNotBlank(posizionearchivio)) {
	    istanzeFilter.setPosizionearchivio(posizionearchivio);
	}
	//istanzeFilter.tuttiResponsabili.responsabile=Palenga Francesco
	//istanzeFilter.tuttiResponsabili.id.codice=11
	String tuttiResponsabili = map.get("istanzeFilter.tuttiResponsabili.id.codice");
	if (StringUtils.isNotBlank(tuttiResponsabili)) {
	    Responsabili tuttiResp = null;
	    try {
		tuttiResp = responsabiliService.findById(new PkId(Integer.parseInt(tuttiResponsabili)));
	    } catch (NumberFormatException nfe) {
		log.error("createIstanzeFilter# tuttiResponsabili: il codice non è numerico");
	    }
	    istanzeFilter.setTuttiResponsabili(tuttiResp);
	}
	//istanzeFilter.professionista.descrizioneRichiedente=
	//istanzeFilter.professionista.descrizioneRichiedente=
	//istanzeFilter.professionista.id.codice=61
	String professionista = map.get("istanzeFilter.professionista.id.codice");
	if (StringUtils.isNotBlank(professionista)) {
	    Anagrafe prof = null;
	    try {
		prof = anagrafeService.findById(new PkId(Integer.parseInt(professionista)));
	    } catch (NumberFormatException nfe) {
		log.error("createIstanzeFilter# richiedente: il codice non è numerico");
	    }
	    istanzeFilter.setProfessionista(prof);
	}
	//istanzeFilter.datiAutorizzazione.autoriznumero=456
	Autorizzazioni datiAutorizzazioni = new Autorizzazioni();
	String autoriznumero = map.get("istanzeFilter.datiAutorizzazione.autoriznumero");
	if (StringUtils.isNotBlank(autoriznumero)) {
	    datiAutorizzazioni.setAutoriznumero(autoriznumero);
	}
	// istanzeFilter.datiAutorizzazione.autorizdata=08/11/2017
	String autorizdata = map.get("istanzeFilter.datiAutorizzazione.autorizdata");
	if (StringUtils.isNotBlank(autorizdata)) {
	    Date _autorizdata = Utilities.parseDateString(autorizdata, false);
	    datiAutorizzazioni.setAutorizdata(_autorizdata);
	}
	String dataRilascio = map.get("istanzeFilter.datiAutorizzazione.dataRilascio");
	if (StringUtils.isNotBlank(dataRilascio)) {
	    Date _dataRilascio = Utilities.parseDateString(dataRilascio, false);
	    datiAutorizzazioni.setDataRilascio(_dataRilascio);
	}
	//istanzeFilter.datiAutorizzazione.autorizcomune.descrizioneEstesa=
	//istanzeFilter.datiAutorizzazione.autorizcomune.codicecomune=E256
	String autorizcomune = map.get("istanzeFilter.datiAutorizzazione.autorizcomune.codicecomune");
	if (StringUtils.isNotBlank(autorizcomune)) {
	    VwEntilocali entilocali = vwEntilocaliService.findById(autorizcomune);
	    datiAutorizzazioni.setAutorizcomune(entilocali);
	}
	//istanzeFilter.datiAutorizzazione.tipologiaregistro.trDescrizione=Registro demanio
	//istanzeFilter.datiAutorizzazione.tipologiaregistro.id.codice=33
	String tipologiaregistroId = map.get("istanzeFilter.datiAutorizzazione.tipologiaregistro.id.codice");
	if (StringUtils.isNotBlank(tipologiaregistroId)) {
	    Tipologiaregistri tipologiaregistro = null;
	    try {
		tipologiaregistro = tipologiaregistriService.findById(new PkId(Integer.parseInt(tipologiaregistroId)));
	    } catch (NumberFormatException nfe) {
		log.error("createIstanzeFilter# tipologiaregistro: il codice non è numerico");
	    }
	    datiAutorizzazioni.setTipologiaregistro(tipologiaregistro);
	}
	istanzeFilter.setDatiAutorizzazione(datiAutorizzazioni);
	//istanzeFilter.alberoproc.id.codice=228
	//istanzeFilter.alberoproc.vwAlberoproc.scDescrizione=Demanio marittimo - Rilascio nuova concessione
	String codiceAlberoProc = map.get("istanzeFilter.alberoproc.id.codice");
	if (StringUtils.isNotBlank(codiceAlberoProc)) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(Integer.parseInt(codiceAlberoProc)));
	    istanzeFilter.setAlberoproc(alberoproc);
	}
	//istanzeFilter.procedura.procedura=Procedura del demanio (23)
	//istanzeFilter.procedura.id.codice=23
	String codiceprocedura = map.get("istanzeFilter.procedura.id.codice");
	if (StringUtils.isNotBlank(codiceprocedura)) {
	    Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(Integer.parseInt(codiceprocedura)));
	    istanzeFilter.setProcedura(tipiprocedure);
	}
	//istanzeFilter.tipoMovimento.descrizioneEstesa=
	//istanzeFilter.tipoMovimento.descrizioneEstesa=
	//istanzeFilter.tipoMovimento.id.tipomovimento=DM000001
	String codice_tipo_mov = map.get("istanzeFilter.tipoMovimento.id.tipomovimento");
	if (StringUtils.isNotBlank(codice_tipo_mov)) {
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(codice_tipo_mov));
	    istanzeFilter.setTipoMovimento(tipimovimento);
	}
	//istanzeFilter.dallaDataTipoMov=01/09/2017
	String dallaDataTipoMov = map.get("istanzeFilter.dallaDataTipoMov");
	if (StringUtils.isNotBlank(dallaDataTipoMov)) {
	    Date _dallaDataTipoMov = Utilities.parseDateString(dallaDataTipoMov, false);
	    istanzeFilter.setDallaDataTipoMov(_dallaDataTipoMov);
	    //istanzeFilter.allaDataTipoMov=30/09/2017
	}
	String allaDataTipoMov = map.get("istanzeFilter.allaDataTipoMov");
	if (StringUtils.isNotBlank(allaDataTipoMov)) {
	    Date _allaDataTipoMov = Utilities.parseDateString(allaDataTipoMov, false);
	    istanzeFilter.setAllaDataTipoMov(_allaDataTipoMov);
	}
	//istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo=Test
	//istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice=529
	String codice_tipifamiglieendo = map.get("istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice");
	if (StringUtils.isNotBlank(codice_tipifamiglieendo)) {
	    Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.findById(new PkId(Integer.parseInt(codice_tipifamiglieendo)));
	    istanzeFilter.setTipifamiglieendo(tipifamiglieendo);
	}
	//istanzeFilter.inventarioprocedimenti.tipoendo.tipo=categoria di prova (9787)
	//istanzeFilter.inventarioprocedimenti.tipoendo.id.codice=9787
	String codice_tipoendo = map.get("istanzeFilter.inventarioprocedimenti.tipoendo.id.codice");
	if (StringUtils.isNotBlank(codice_tipoendo)) {
	    Tipiendo tipoendo = tipiendoService.findById(new PkId(Integer.parseInt(codice_tipoendo)));
	    istanzeFilter.setTipiendo(tipoendo);
	}
	//istanzeFilter.inventarioprocedimenti.procedimento=Rilascio autorizzazione scarichi idrici
	//istanzeFilter.inventarioprocedimenti.id.codice=20926
	String codice_inventarioprocedimenti = map.get("istanzeFilter.inventarioprocedimenti.id.codice");
	if (StringUtils.isNotBlank(codice_inventarioprocedimenti)) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService
		    .findById(new PkId(Integer.parseInt(codice_inventarioprocedimenti)));
	    if (inventarioprocedimenti != null) {
		istanzeFilter.setInventarioprocedimenti(inventarioprocedimenti);
	    }
	}
	//istanzeFilter.istanzeattivita.attivita.istat=
	//istanzeFilter.istanzeattivita.attivita.id.codiceistat=
	it.gruppoinit.pal.gp.core.domain.Attivita attivita = new it.gruppoinit.pal.gp.core.domain.Attivita();
	String codiceistat = map.get("istanzeFilter.istanzeattivita.attivita.id.codiceistat");
	if (StringUtils.isNotBlank(codiceistat)) {
	    attivita = attivitaService.findById(new AttivitaId(codiceistat));
	}
	//istanzeFilter.istanzeattivita.attivita.settori.settore=Settore configurato per il conteggio (TEST01)
	//istanzeFilter.istanzeattivita.attivita.settori.id.codicesettore=TEST01
	String codicesettore = map.get("istanzeFilter.istanzeattivita.attivita.settori.id.codicesettore");
	if (StringUtils.isNotBlank(codicesettore)) {
	    Settori settori = settoriService.findById(new SettoriId(codicesettore));
	    attivita.setSettori(settori);
	}
	Istanzeattivita istanzeattivita = new Istanzeattivita();
	istanzeattivita.setAttivita(attivita);
	istanzeFilter.setIstanzeattivita(istanzeattivita);
	// CI SONO ANCHE I MQ????
	//istanzeFilter.lavori=test
	String lavori = map.get("istanzeFilter.lavori");
	if (StringUtils.isNotBlank(lavori)) {
	    istanzeFilter.setLavori(lavori);
	}
	//istanzeFilter.nomeattivita=casa
	String nomeattivita = map.get("istanzeFilter.nomeattivita");
	if (StringUtils.isNotBlank(nomeattivita)) {
	    istanzeFilter.setNomeattivita(nomeattivita);
	}
	// istanzeFilter.lavoriestesa=prova salvataggio 
	String lavoriestesa = map.get("istanzeFilter.lavoriestesa");
	if (StringUtils.isNotBlank(lavoriestesa)) {
	    istanzeFilter.setLavoriestesa(lavoriestesa);
	}
	//    	istanzeFilter.chiusura.id.codicestato=stato_aperte
	String codiceStato = map.get("istanzeFilter.chiusura.id.codicestato");
	if (StringUtils.isNotBlank(codiceStato)) {
	    Statiistanza chiusura = new Statiistanza();
	    if (codiceStato.equals("stato_tutte") || codiceStato.equals("stato_aperte") || codiceStato.equals("stato_chiuse")) {
		chiusura.setId(new StatiistanzaId(codiceStato));
	    } else {
		chiusura = statiistanzaService.findById(new StatiistanzaId(codiceStato));
	    }
	    istanzeFilter.setChiusura(chiusura);
	}
	//istanzeFilter.orderBy=dataprotocollo
	String orderBy = map.get("istanzeFilter.orderBy");
	if (StringUtils.isNotBlank(orderBy)) {
	    istanzeFilter.setOrderBy(orderBy);
	}
	//istanzeFilter.orderAscDesc=ASC
	String orderAscDesc = map.get("istanzeFilter.orderAscDesc");
	if (StringUtils.isNotBlank(orderAscDesc)) {
	    OrderTypeEnum orderTypeEnum = "ASC".equals(orderAscDesc) ? OrderTypeEnum.ASC : OrderTypeEnum.DESC;
	    istanzeFilter.setOrderAscDesc(orderTypeEnum);
	}
	//istanzeFilter.ricercaVeloce=on
	String ricercaVeloce = map.get("istanzeFilter.ricercaVeloce");
	if (StringUtils.isNotBlank(ricercaVeloce)) {
	    Boolean _ricercaVeloce = "on".equals(ricercaVeloce) ? true : false;
	    istanzeFilter.setRicercaVeloce(_ricercaVeloce);
	}
	////////////////////////////////////////////////// LOCALIZZAZIONE ////////////////////////////////////////////////////////////////////
	//istanzeFilter.istanzearee.area.denominazione=Area non definita
	//istanzeFilter.istanzearee.id.codicearea=2
	Istanzearee istanzearee = new Istanzearee();
	String codiceArea = map.get("istanzeFilter.istanzearee.id.codicearea");
	if (StringUtils.isNotBlank(codiceArea)) {
	    Aree aree = areeService.findById(new PkId(Integer.parseInt(codiceArea)));
	    istanzearee.setArea(aree);
	}
	istanzeFilter.setIstanzearee(istanzearee);
	Istanzestradario istanzestradario = new Istanzestradario();
	//istanzeFilter.istanzestradario.stradario.descrizione=Borgo Allegri (400)
	//istanzeFilter.istanzestradario.stradario.id.codice=64
	String codiceStradario = map.get("istanzeFilter.istanzestradario.stradario.id.codice");
	if (StringUtils.isNotBlank(codiceStradario)) {
	    Stradario stradario = stradarioService.findById(new PkId(Integer.parseInt(codiceStradario)));
	    istanzestradario.setStradario(stradario);
	}
	//istanzeFilter.istanzestradario.civico=
	String civico = map.get("istanzeFilter.istanzestradario.civico");
	if (StringUtils.isNotBlank(civico)) {
	    istanzestradario.setCivico(civico);
	}
	//istanzeFilter.cercarangecivici
	String cercarangecivici = map.get("istanzeFilter.cercarangecivici");
	boolean iscercarangecivici = false;
	if (StringUtils.isNotBlank(cercarangecivici)) {
	    iscercarangecivici = BooleanUtils.toBoolean(Integer.parseInt(cercarangecivici));
	}
	istanzeFilter.setCercarangecivici(iscercarangecivici);
	//istanzeFilter.civicoDa=1
	//istanzeFilter.civicoA=5
	String civicoDa = map.get("istanzeFilter.civicoDa");
	if (StringUtils.isNotBlank(civicoDa)) {
	    istanzeFilter.setCivicoDa(civicoDa);
	}
	String civicoA = map.get("istanzeFilter.civicoA");
	if (StringUtils.isNotBlank(civicoA)) {
	    istanzeFilter.setCivicoA(civicoA);
	}
	//istanzeFilter.istanzestradario.esponente=23
	String esponente = map.get("istanzeFilter.istanzestradario.esponente");
	if (StringUtils.isNotBlank(esponente)) {
	    istanzestradario.setEsponente(esponente);
	}
	//istanzeFilter.istanzestradario.scala=4
	String scala = map.get("istanzeFilter.istanzestradario.scala");
	if (StringUtils.isNotBlank(scala)) {
	    istanzestradario.setScala(scala);
	}
	//istanzeFilter.istanzestradario.piano=10
	String piano = map.get("istanzeFilter.istanzestradario.piano");
	if (StringUtils.isNotBlank(piano)) {
	    istanzestradario.setPiano(piano);
	}
	//istanzeFilter.istanzestradario.interno=5
	String interno = map.get("istanzeFilter.istanzestradario.interno");
	if (StringUtils.isNotBlank(interno)) {
	    istanzestradario.setInterno(interno);
	}
	//istanzeFilter.istanzestradario.esponenteinterno=78
	String esponenteinterno = map.get("istanzeFilter.istanzestradario.esponenteinterno");
	if (StringUtils.isNotBlank(esponenteinterno)) {
	    istanzestradario.setEsponenteinterno(esponenteinterno);
	}
	//istanzeFilter.istanzestradario.fabbricato=45678
	String fabbricato = map.get("istanzeFilter.istanzestradario.fabbricato");
	if (StringUtils.isNotBlank(fabbricato)) {
	    istanzestradario.setFabbricato(fabbricato);
	}
	//istanzeFilter.istanzestradario.frazione=yh
	String frazione = map.get("istanzeFilter.istanzestradario.frazione");
	if (StringUtils.isNotBlank(frazione)) {
	    istanzestradario.setFrazione(frazione);
	}
	//istanzeFilter.istanzestradario.cap=01038
	String cap = map.get("istanzeFilter.istanzestradario.cap");
	if (StringUtils.isNotBlank(cap)) {
	    istanzestradario.setCap(cap);
	}
	//istanzeFilter.istanzestradario.quartiere=rm
	String quartiere = map.get("istanzeFilter.istanzestradario.quartiere");
	if (StringUtils.isNotBlank(quartiere)) {
	    istanzestradario.setQuartiere(quartiere);
	}
	//istanzeFilter.istanzestradario.note=note stradario
	String noteStrada = map.get("istanzeFilter.istanzestradario.note");
	if (StringUtils.isNotBlank(noteStrada)) {
	    istanzestradario.setNote(noteStrada);
	}
	//istanzeFilter.istanzestradario.circoscrizione=circoscr
	String circoscrizione = map.get("istanzeFilter.istanzestradario.circoscrizione");
	if (StringUtils.isNotBlank(circoscrizione)) {
	    istanzestradario.setCircoscrizione(circoscrizione);
	}
	//istanzeFilter.istanzestradario.stradariocolore.id.codicecolore=B
	String codicecolore = map.get("istanzeFilter.istanzestradario.stradariocolore.id.codicecolore");
	if (StringUtils.isNotBlank(codicecolore)) {
	    Stradariocolore stradariocolore = stradariocoloreService.findById(new StradariocoloreId(codicecolore));
	    istanzestradario.setStradariocolore(stradariocolore);
	}
	istanzeFilter.setIstanzestradario(istanzestradario);
	// istanzeFilter.cercalocalizzazioneinaltri=1
	String cercalocalizzazioneinaltri = map.get("istanzeFilter.cercalocalizzazioneinaltri");
	istanzeFilter.setCercalocalizzazioneinaltri(BooleanUtils.toBoolean(cercalocalizzazioneinaltri));
	Istanzemappali istanzemappali = new Istanzemappali();
	// istanzeFilter.istanzemappali.catasto.codice=F
	String codicecatasto = map.get("istanzeFilter.istanzemappali.catasto.codice");
	if (StringUtils.isNotBlank(codicecatasto)) {
	    Catasto catasto = catastoService.findById(codicecatasto);
	    istanzemappali.setCatasto(catasto);
	}
	// istanzeFilter.istanzemappali.foglio=12
	String foglio = map.get("istanzeFilter.istanzemappali.foglio");
	if (StringUtils.isNotBlank(foglio)) {
	    istanzemappali.setFoglio(foglio);
	}
	// istanzeFilter.istanzemappali.particella=245
	String particella = map.get("istanzeFilter.istanzemappali.particella");
	if (StringUtils.isNotBlank(particella)) {
	    istanzemappali.setParticella(particella);
	}
	// istanzeFilter.istanzemappali.sub=12
	String sub = map.get("istanzeFilter.istanzemappali.sub");
	if (StringUtils.isNotBlank(sub)) {
	    istanzemappali.setSub(sub);
	}
	istanzeFilter.setIstanzemappali(istanzemappali);
	// ufficio titolare
	String codiceAmministrazione = map.get("istanzeFilter.amministrazioni.id.codice");
	if (Utilities.isInteger(codiceAmministrazione)) {
	    Amministrazioni amm = amministrazioniService.findById(new PkId(Integer.valueOf(codiceAmministrazione)));
	    istanzeFilter.setAmministrazioni(amm);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////// GESTIONE DELLA SEZIONE DI RICERCA DELLE SCHEDE ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la scheda associata alla ricera sui campi dinamici
	String codice_scheda_dyn = mapSchedeFilter.get("scheda.id.codice");
	if (StringUtils.isNotBlank(codice_scheda_dyn)) {
	    // recupero il modello e lo setto al filtro
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(Integer.parseInt(codice_scheda_dyn)));
	    SchedaDinamicaFilter schedaDinamicaFilter = new SchedaDinamicaFilter();
	    schedaDinamicaFilter.setScheda(dyn2Modellit);
	    // Conto il numero delle righe
	    Set<Integer> numerorighe = getNumeroRighe(mapSchedeFilter);
	    List<SchedaDinamicaRigheFilter> righe = new ArrayList<SchedaDinamicaRigheFilter>();
	    SchedaDinamicaRigheFilter dinamicaRigheFilter = null;
	    for (Integer riga_num : numerorighe) {
		dinamicaRigheFilter = populateRiga(mapSchedeFilter, riga_num);
		righe.add(dinamicaRigheFilter);
	    }
	    schedaDinamicaFilter.setRighe(righe);
	    istanzeFilter.setSchedaDinamicaFilter(schedaDinamicaFilter);
	}
	return istanzeFilter;
    }

    /**
     * <pre>
     * Controlla tutta la mappa e veriffica il numero di righe: 
     * 	    1. Verfifica che la key della mappa contenga la stringa "righe[" 
     * 	       1.1 si : estrae il valore copreso tra "righe[" e "]" 
     *         1.2 no : passa al sucessivo 
     *      2. Aggiunge il valore sul set (Usato un set inmodo che non duplica i valori)
     * 
     * &#64;param mapSchedeFilter
     * &#64;return
     * </pre>
     */
    private Set<Integer> getNumeroRighe(Map<String, String> mapSchedeFilter) {

	Set<String> keySchede = mapSchedeFilter.keySet();
	Set<Integer> numerorighe = new HashSet<Integer>();
	for (String string : keySchede) {
	    if (StringUtils.contains(string, "righe[")) {
		String numString = StringUtils.substring(string, string.indexOf("righe[") + 6, string.indexOf("]"));
		numerorighe.add(Integer.parseInt(numString));
	    }
	}
	return numerorighe;
    }

    /**
     * Popola un oggetto SchedaDinamicaRigheFilter per la riga passata
     * 
     * @param mapSchedeFilter
     * @param riga_num
     * @return
     */
    private SchedaDinamicaRigheFilter populateRiga(Map<String, String> mapSchedeFilter, int riga_num) {

	SchedaDinamicaRigheFilter dinamicaRigheFilter = new SchedaDinamicaRigheFilter();
	String and_or = mapSchedeFilter.get("righe[" + riga_num + "].andOr");
	if (StringUtils.isNotBlank(and_or)) {
	    dinamicaRigheFilter.setAndOr(AndOrRestriction.valueOf(and_or));
	}
	String parentesiSx = mapSchedeFilter.get("righe[" + riga_num + "].parentesiSx");
	dinamicaRigheFilter.setParentesiSx(parentesiSx);
	String campoid = mapSchedeFilter.get("righe[" + riga_num + "].campo.id.codice");
	if (StringUtils.isNotBlank(campoid)) {
	    Dyn2Campi campo = dyn2CampiService.findById(new PkId(Integer.parseInt(campoid)));
	    dinamicaRigheFilter.setCampo(campo);
	}
	String tipo_confr = mapSchedeFilter.get("righe[" + riga_num + "].tipoConfronto");
	if (StringUtils.isNotBlank(tipo_confr)) {
	    dinamicaRigheFilter.setTipoConfronto(FieldOperationsEnum.valueOf(tipo_confr));
	}
	String valore = mapSchedeFilter.get("righe[" + riga_num + "].valore");
	dinamicaRigheFilter.setValore(valore);
	String parentesidx = mapSchedeFilter.get("righe[" + riga_num + "].parentesiDx");
	dinamicaRigheFilter.setParentesiDx(parentesidx);
	return dinamicaRigheFilter;
    }
}
