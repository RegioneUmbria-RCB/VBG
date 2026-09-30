package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.SuperficiAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoFactory;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.utils.CheckboxUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IRtfSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISostituzioneSegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.SegnapostoParser;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.StrutturaSegnaposto;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.QRCodeBean;
import it.gruppoinit.pal.gp.core.service.helper.QrcodeHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAuthQRcodeEnum;
import it.gruppoinit.pal.gp.core.utils.DocumentMergeUtils;

@Service
public class GetPlaceholderValueByIfElseServiceImpl implements IGetPlaceholderValueByIfElseService {

    private final Logger log = LoggerFactory.getLogger(GetPlaceholderValueByIfElseServiceImpl.class);
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ISegnapostoService segnapostoService;
    private MovimentiService movimentiService;
    private AutorizzazioniService autorizzazioniService;
    private AmministrazioniService amministrazioniService;
    private IstanzeattivitaService istanzeAttivitaService;
    private QrcodeService qrcodeService;
    private TempLinkallegatiService tempLinkallegatiService;
    private VerticalizzazioniService verticalizzazioniService;
    private AttivitaService attivitaService;
    private IstanzeprocureService istanzeprocureService;
    private OggettiService oggettiService;
    private AlberoprocService alberoprocService;
    private IstanzeService istanzeService;
    private ISostituzioneSegnapostoService sostituzioneSegnapostoService;

    @Autowired
    public GetPlaceholderValueByIfElseServiceImpl(MovimentiZipLogicoService movimentiZipLogicoService, ISegnapostoService segnapostoService,
	    MovimentiService movimentiService, AutorizzazioniService autorizzazioniService, AmministrazioniService amministrazioniService,
	    IstanzeattivitaService istanzeAttivitaService, QrcodeService qrcodeService, TempLinkallegatiService tempLinkallegatiService,
	    VerticalizzazioniService verticalizzazioniService, AttivitaService attivitaService, IstanzeprocureService istanzeprocureService,
	    OggettiService oggettiService, AlberoprocService alberoprocService, IstanzeService istanzeService,
	    ISostituzioneSegnapostoService sostituzioneSegnapostoService) {

	super();
	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.segnapostoService = segnapostoService;
	this.movimentiService = movimentiService;
	this.autorizzazioniService = autorizzazioniService;
	this.amministrazioniService = amministrazioniService;
	this.istanzeAttivitaService = istanzeAttivitaService;
	this.qrcodeService = qrcodeService;
	this.tempLinkallegatiService = tempLinkallegatiService;
	this.verticalizzazioniService = verticalizzazioniService;
	this.attivitaService = attivitaService;
	this.istanzeprocureService = istanzeprocureService;
	this.oggettiService = oggettiService;
	this.alberoprocService = alberoprocService;
	this.istanzeService = istanzeService;
	this.sostituzioneSegnapostoService = sostituzioneSegnapostoService;
    }

    @Override
    public String esegui(String placeholder, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData,
	    TipoFileEnum typeLettereTipoEnum) {

	//Istanzio i resolver
	//Sostituisco in base ai resolver
	ISegnapostoResolver resolver = new SegnapostoFactory(this.movimentiZipLogicoService, this.segnapostoService, tempLinkallegatiService)
		.getResolvers(typeLettereTipoEnum, data.getMovimento(), userData).get(placeholder);
	if (resolver != null) {
	    return resolver.sostituisci();
	}
	// TODO: Estrarre gli argomenti del segnaposto se presenti
	StrutturaSegnaposto struttura = new SegnapostoParser().analizza(placeholder);
	//
	String retVal = null;
	//campi non presenti nel DB
	//data inserita dall'utente
	if (placeholder.equalsIgnoreCase("DATAINSERITADALLUTENTE")) {
	    return FormatUtils.dateFormat(userData.getDataStampa());
	}
	//amministrazione.web
	if (placeholder.equalsIgnoreCase("INDIRIZZOWEB")) {
	    return FormatUtils.stringFormat(data.getAmministrazione().getWeb());
	}
	//configurazione.responsabile
	if (placeholder.equalsIgnoreCase("OPERATORE")) {
	    return FormatUtils.stringFormat(data.getConfigurazione().getResponsabile());
	}
	//DESCRIZIONE INTERVENTO
	// [-ALBERO_LIVELLO(N)-]
	// DOVE N E' un numero che indica a quale livello risalire per tirare fuori la descrizione dell'albero 
	//'Es: se l'albero ha la seguente struttura :COMMERCIO --> AREA PUBBLICA --> VICINATO --> AVVIO
	// Il TAG [-ALBERO_LIVELLO(2)-] a partire da AVVIO tornerà la descrizione : VICINATO - AVVIO 
	// considerando AVVIO come 1 e VICINATO come 2
	if (placeholder.toUpperCase().startsWith("ALBERO_LIVELLO(")) {
	    String argument = placeholder.substring(15, placeholder.length() - 1).trim();
	    String val = "";
	    try {
		Integer _num = Integer.parseInt(argument);
		val = alberoprocService.findDescrizioneInterventoFromLivello(data.getIstanza().getAlberoproc().getId().getCodice(), _num);
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
		val = placeholder;
	    }
	    return val;
	}
	//[-NOTEINTERVENTO-]
	if (placeholder.equalsIgnoreCase("NOTEINTERVENTO")) {
	    String val = "";
	    if (data.getIstanza().getAlberoproc() != null) {
		val = data.getIstanza().getAlberoproc().getScNote();
	    }
	    return FormatUtils.stringFormat(val);
	}
	//configurazione.orario
	if (placeholder.equalsIgnoreCase("CFG_SP_ORARIO")) {
	    //	    retVal = new String[0];
	    //	    retVal[0] = RtfUtilities.stringFormat(data.getConfigurazione().getOrario());
	    //	    return retVal;
	    return FormatUtils.stringFormat(data.getConfigurazione().getOrario());
	}
	//configurazione.descrizione
	if (placeholder.equalsIgnoreCase("CFG_SP_DESCRAGG")) {
	    //	    retVal = new String[0];
	    //	    retVal[0] = RtfUtilities.stringFormat(data.getConfigurazione().getDescrizione());
	    //	    return retVal;
	    return FormatUtils.stringFormat(data.getConfigurazione().getDescrizione());
	}
	//configurazione.telefono
	/*
	if(placeholder.equalsIgnoreCase("TELEFONO")){
	    return RtfUtilities.stringFormat(data.getConfigurazione().getTelefono());
	}
	*/
	//istanza.richiedente.nominativo  + " " +  istanza.richiedente.nome
	if (placeholder.equalsIgnoreCase("RICHIEDENTE")) {
	    Anagrafe ana = data.getIstanza().getRichiedente();
	    StringBuilder val = new StringBuilder();
	    if (ana != null) {
		val.append(FormatUtils.stringFormat(ana.getNominativo()));
		if (StringUtils.isNotEmpty(ana.getNome())) {
		    if (val.length() > 0) {
			val.append(" ");
		    }
		    val.append(FormatUtils.stringFormat(ana.getNome()));
		}
	    }
	    //	    retVal = new String[0];
	    //	    retVal[0] = val.toString();
	    //	    return retVal;
	    return val.toString();
	}
	//istanza.richiedente.nominativo
	if (placeholder.equalsIgnoreCase("RICHIEDENTECOGNOME")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? richiedente.getNominativo() : "";
	    return val;
	}
	//istanza.richiedente.nome
	if (placeholder.equalsIgnoreCase("RICHIEDENTENOME")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? richiedente.getNome() : "";
	    return val;
	}
	//istanza.richiedente.formagiuridica.formagiuridica
	if (placeholder.equalsIgnoreCase("FORMAGIURIDICA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = "";
	    if (richiedente != null && richiedente.getFormagiuridica() != null) {
		val = FormatUtils.stringFormat(richiedente.getFormagiuridica().getFormagiuridica());
	    }
	    return val;
	}
	//istanza.richiedente.titolo.titolo
	if (placeholder.equalsIgnoreCase("TITOLO")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = "";
	    if (richiedente != null && richiedente.getTitolo() != null) {
		val = FormatUtils.stringFormat(richiedente.getTitolo().getTitolo());
	    }
	    return val;
	}
	//istanza.richiedente.indirizzocorrispondenza o istanza.richiedente.indirizzo
	if (placeholder.equalsIgnoreCase("INDIRIZZO")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null
		    ? FormatUtils.stringFormat(
			    richiedente.getIndirizzocorrispondenza() != null ? richiedente.getIndirizzocorrispondenza() : richiedente.getIndirizzo())
		    : "";
	    return val;
	}
	//istanza.richiedente.comunecorrispondenza.comune o istanza.richiedente.comuneresidenza.comune
	if (placeholder.equalsIgnoreCase("CITTA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = "";
	    if (richiedente != null) {
		Comuni comune = richiedente.getComunecorrispondenza() != null ? richiedente.getComunecorrispondenza()
			: richiedente.getComuneResidenza();
		val = comune != null ? comune.getComune() : "";
	    }
	    return val;
	}
	//istanza.richiedente.capcorrispondenza o istanza.richiedente.cap
	if (placeholder.equalsIgnoreCase("CAP")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null
		    ? FormatUtils.stringFormat(richiedente.getCapcorrispondenza() != null ? richiedente.getCapcorrispondenza() : richiedente.getCap())
		    : "";
	    return val;
	}
	//istanza.richiedente.provinciacorrispondenza o istanza.richiedente.provincia
	if (placeholder.equalsIgnoreCase("PROVINCIA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null
		    ? FormatUtils.stringFormat(
			    richiedente.getProvinciacorrispondenza() != null ? richiedente.getProvinciacorrispondenza() : richiedente.getProvincia())
		    : "";
	    return val;
	}
	//istanza.richiedente.cittacorrispondenza o istanza.richiedente.citta
	if (placeholder.equalsIgnoreCase("LOCALITA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null
		    ? FormatUtils.stringFormat(
			    richiedente.getCittacorrispondenza() != null ? richiedente.getCittacorrispondenza() : richiedente.getCitta())
		    : "";
	    return val;
	}
	//istanza.richiedente.indirizzo
	if (placeholder.equalsIgnoreCase("INDIRIZZORESIDENZA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getIndirizzo()) : "";
	    return val;
	}
	if (placeholder.equalsIgnoreCase("NUM_PRATICA_PADRE") || placeholder.equalsIgnoreCase("NUMISTMITTENTEBACK")) {
	    String val = "";
	    if (data.getIstanza() != null && data.getIstanza().getId() != null && data.getIstanza().getId().getCodice() != null) {
		val = StringUtils.defaultString(istanzeService.findNumeroIstanzaPadre(data.getIstanza().getId().getCodice()));
	    }
	    return val;
	}
	//istanza.richiedente.comuneresidenza.comune
	if (placeholder.equalsIgnoreCase("CITTARESIDENZA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = "";
	    if (richiedente != null) {
		Comuni comune = richiedente.getComuneResidenza();
		val = comune != null ? comune.getComune() : "";
	    }
	    return val;
	}
	//istanza.richiedente.cap
	if (placeholder.equalsIgnoreCase("CAPRESIDENZA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    return richiedente != null ? FormatUtils.stringFormat(richiedente.getCap()) : "";
	}
	//istanza.richiedente.provincia
	if (placeholder.equalsIgnoreCase("PROVINCIARESIDENZA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getProvincia()) : "";
	    return val;
	}
	//istanza.richiedente.citta
	if (placeholder.equalsIgnoreCase("LOCALITARESIDENZA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getCitta()) : "";
	    return val;
	}
	//istanza.richiedente.fax
	if (placeholder.equalsIgnoreCase("FAX")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getFax()) : "";
	    return val;
	}
	//istanza.richiedente.telefono
	if (placeholder.equalsIgnoreCase("TELEFONO")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getTelefono()) : "";
	    return val;
	}
	//istanza.richiedente.telefonocellulare
	if (placeholder.equalsIgnoreCase("TELEFONOCELLULARE")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getTelefonocellulare()) : "";
	    return val;
	}
	//istanza.richiedente.datanascita
	if (placeholder.equalsIgnoreCase("DATANASCITA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.dateFormat(richiedente.getDatanascita()) : "";
	    return val;
	}
	//istanza.richiedente.codicefiscale
	if (placeholder.equalsIgnoreCase("CODICEFISCALE")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getCodicefiscale()) : "";
	    return val;
	}
	//istanza.richiedente.comune
	if (placeholder.equalsIgnoreCase("COMUNE")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = "";//richiedente != null ? RtfUtilities.stringFormat(richiedente.getCodicefiscale()) : "";
	    if (richiedente != null) {
		Comuni com = richiedente.getComuneResidenza();
		if (com != null) {
		    val = FormatUtils.stringFormat(com.getComune(), true);
		}
	    }
	    return val;
	}
	//istanza.richiedente.partitaiva
	if (placeholder.equalsIgnoreCase("PARTITAIVA")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.stringFormat(richiedente.getPartitaiva()) : "";
	    return val;
	}
	//istanza.richiedente.datanominativo
	if (placeholder.equalsIgnoreCase("DATANOMINATIVO") || placeholder.equalsIgnoreCase("DATACOSTITUZIONE")) {
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    String val = richiedente != null ? FormatUtils.dateFormat(richiedente.getDatanominativo()) : "";
	    return val;
	}
	//istanza.richiedente.regditte
	if (placeholder.equalsIgnoreCase("CCIAANR")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getRegditte()) : "";
	    return val;
	}
	//istanza.richiedente.dataregditte
	if (placeholder.equalsIgnoreCase("CCIAADATA")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDataregditte()) : "";
	    return val;
	}
	//istanza.richiedente.comunecomregditte.comune
	if (placeholder.equalsIgnoreCase("CCIAACOMUNE")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = "";
	    if (tit != null && tit.getComunecomregditte() != null) {
		val = FormatUtils.stringFormat(tit.getComunecomregditte().getComune());
	    }
	    return val;
	}
	//istanza.richiedente.regtrib
	if (placeholder.equalsIgnoreCase("REGTRIBNR")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getRegtrib()) : "";
	    return val;
	}
	//istanza.richiedente.dataregtrib
	if (placeholder.equalsIgnoreCase("REGTRIBDATA")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDataregtrib()) : "";
	    return val;
	}
	//istanza.richiedente.comuneregtrib.comune
	if (placeholder.equalsIgnoreCase("REGTRIBCOMUNE")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = "";
	    if (tit != null && tit.getComuneregtrib() != null) {
		val = FormatUtils.stringFormat(tit.getComuneregtrib().getComune());
	    }
	    return val;
	}
	//istanza.richiedente.comunenascita.comune
	if (placeholder.equalsIgnoreCase("COMUNEDINASCITA")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = "";
	    if (tit != null && tit.getComuneNascita() != null) {
		val = FormatUtils.stringFormat(tit.getComuneNascita().getComune());
	    }
	    return val;
	}
	//istanza.richiedente.comunenascita.siglaprovincia (provincia di nascita)
	if (placeholder.equalsIgnoreCase("PROVINCIADINASCITA")) {
	    Anagrafe tit = data.getIstanza().getRichiedente();
	    String val = "";
	    if (tit != null && tit.getComuneNascita() != null) {
		val = FormatUtils.stringFormat(tit.getComuneNascita().getSiglaprovincia());
	    }
	    return val;
	}
	//istanza.richiedente.legalerappresentante -- DISMESSO
	if (placeholder.equalsIgnoreCase("GENERALITALR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.??? -- DISMESSO
	if (placeholder.equalsIgnoreCase("LUOGODINASCITALR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.??? -- DISMESSO
	if (placeholder.equalsIgnoreCase("DATANASCITALR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.INDIRIZZOLR -- DISMESSO
	if (placeholder.equalsIgnoreCase("RESIDENZAINDIRIZZOLR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.cittalr - DISMESSO
	if (placeholder.equalsIgnoreCase("RESIDENZACITTALR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.caplr -- DISMESO
	if (placeholder.equalsIgnoreCase("RESIDENZACAPLR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.provincialr -- DISMESSO
	if (placeholder.equalsIgnoreCase("RESIDENZAPROVINCIALR")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.
	//istanza.professionista.nome + " " + istanza.professionista.nominativo
	if (placeholder.equalsIgnoreCase("TECNICO") || placeholder.equalsIgnoreCase("TEC_RICHIEDENTE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    StringBuilder val = new StringBuilder();
	    if (ana != null) {
		val.append(FormatUtils.stringFormat(ana.getNome()));
		if (StringUtils.isNotEmpty(ana.getNominativo())) {
		    if (val.length() > 0) {
			val.append(" ");
		    }
		    val.append(FormatUtils.stringFormat(ana.getNominativo()));
		}
	    }
	    return val.toString();
	}
	//istanza.professionista.comunenascita.comune + istanza.professionista.comunenascita.siglaprovincia
	//(LUOGO DI NASCITA, comune di nascita e sigla della provincia separati da uno spazio)
	if (placeholder.equalsIgnoreCase("TEC_LUOGODINASCITA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		if (ana.getComuneNascita() != null)
		    val = FormatUtils.stringFormat(ana.getComuneNascita().getComune()) + " " +
			  FormatUtils.stringFormat(ana.getComuneNascita().getSiglaprovincia());
	    }
	    return val;
	}
	//istanza.professionista.comunenascita.comune
	//(COMUNE DI NASCITA comune di nascita)
	if (placeholder.equalsIgnoreCase("TEC_COMUNEDINASCITA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		if (ana.getComuneNascita() != null)
		    val = FormatUtils.stringFormat(ana.getComuneNascita().getComune());
	    }
	    return val;
	}
	////istanza.professionista.comunenascita.siglaprovincia
	//(PROVINCIA DI NASCITA sigla della provincia del comune di nascita)
	if (placeholder.equalsIgnoreCase("TEC_PROVINCIADINASCITA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		if (ana.getComuneNascita() != null)
		    val = FormatUtils.stringFormat(ana.getComuneNascita().getSiglaprovincia());
	    }
	    return val;
	}
	//istanza.professionista.email
	if (placeholder.equalsIgnoreCase("EMAILTECNICO")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getEmail());
	    }
	    return val;
	}
	//istanza.professionista.indirizzo
	if (placeholder.equalsIgnoreCase("TEC_INDIRIZZO")) {
	    //	    String val = richiedente != null ? RtfUtilities.stringFormat(richiedente.getIndirizzocorrispondenza() != null ? richiedente
	    //		    .getIndirizzocorrispondenza() : richiedente.getIndirizzo()) : "";
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getIndirizzocorrispondenza());
		if (StringUtils.isBlank(val)) {
		    val = FormatUtils.stringFormat(ana.getIndirizzo());
		}
	    }
	    return val;
	}
	//istanza.professionista.citta
	if (placeholder.equalsIgnoreCase("TEC_CITTA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getCittacorrispondenza());
		if (StringUtils.isBlank(val)) {
		    val = FormatUtils.stringFormat(ana.getCitta());
		}
	    }
	    return val;
	}
	//istanza.professionista.cap
	if (placeholder.equalsIgnoreCase("TEC_CAP")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getCapcorrispondenza());
		if (StringUtils.isBlank(val)) {
		    val = FormatUtils.stringFormat(ana.getCap());
		}
	    }
	    return val;
	}
	//istanza.professionista.provincia
	if (placeholder.equalsIgnoreCase("TEC_PROVINCIA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getProvincia());
		if (StringUtils.isBlank(val)) {
		    val = FormatUtils.stringFormat(ana.getProvincia());
		}
	    }
	    return val;
	}
	//istanza.professionista.codicefiscale
	if (placeholder.equalsIgnoreCase("TEC_CODICEFISCALE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getCodicefiscale());
	    }
	    return val;
	}
	//istanza.professionista.titolo.titolo
	if (placeholder.equalsIgnoreCase("TEC_TITOLO")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		Titoli titolo = ana.getTitolo();
		if (titolo != null) {
		    val = FormatUtils.stringFormat(titolo.getTitolo());
		}
	    }
	    return val;
	}
	//istanza.professionista.comuneresidenza.comune
	if (placeholder.equalsIgnoreCase("TEC_COMUNE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		if (ana.getComunecorrispondenza() != null) {
		    val = FormatUtils.stringFormat(ana.getComunecorrispondenza().getComune());
		}
		if (StringUtils.isBlank(val)) {
		    Comuni comres = ana.getComuneResidenza();
		    if (comres != null) {
			val = FormatUtils.stringFormat(comres.getComune());
		    }
		}
	    }
	    return val;
	}
	//istanza.professionista.fax
	if (placeholder.equalsIgnoreCase("TEC_FAX")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getFax());
	    }
	    return val;
	}
	//istanza.professionista.telefono
	if (placeholder.equalsIgnoreCase("TEC_TELEFONO")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getTelefono());
	    }
	    return val;
	}
	//istanza.professionista.partitaiva
	if (placeholder.equalsIgnoreCase("TEC_PARTIVA")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getPartitaiva());
	    }
	    return val;
	}
	//istanza.professionista.id.codice
	if (placeholder.equalsIgnoreCase("TEC_CODICE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.integerFormat(ana.getId().getCodice());
	    }
	    return val;
	}
	//istanza.professionista.password
	if (placeholder.equalsIgnoreCase("TEC_PASSWORD")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.professionista.regditte
	if (placeholder.equalsIgnoreCase("TEC_REGDITTE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getRegditte());
	    }
	    return val;
	}
	//istanza.professionista.dataregditt
	if (placeholder.equalsIgnoreCase("TEC_DATAREGDITTE")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.dateFormat(ana.getDataregditte());
	    }
	    return val;
	}
	//istanza.professionista.regtrib
	if (placeholder.equalsIgnoreCase("TEC_REGTRIB")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.stringFormat(ana.getRegtrib());
	    }
	    return val;
	}
	//istanza.professionista.dataregtrib
	if (placeholder.equalsIgnoreCase("TEC_DATAREGTRIB")) {
	    Anagrafe ana = data.getIstanza().getProfessionista();
	    String val = "";
	    if (ana != null) {
		val = FormatUtils.dateFormat(ana.getDataregtrib());
	    }
	    return val;
	}
	//istanza.civico -- DISMESSO
	if (placeholder.equalsIgnoreCase("NRCIVICO")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.numeroistanza
	if (placeholder.equalsIgnoreCase("CODICEISTANZA")) {
	    return FormatUtils.stringFormat(data.getIstanza().getNumeroistanza());
	}
	//istanza.data
	if (placeholder.equalsIgnoreCase("DATAPRESENTAZIONEISTANZA")) {
	    return FormatUtils.dateFormat(data.getIstanza().getData());
	}
	//istanza.numeroprotocollo
	if (placeholder.equalsIgnoreCase("NRPROTOCOLLOISTANZA")) {
	    return FormatUtils.stringFormat(data.getIstanza().getNumeroprotocollo());
	}
	//istanza.dataprotocollo
	if (placeholder.equalsIgnoreCase("DATAPROTOCOLLO")) {
	    return FormatUtils.dateFormat(data.getIstanza().getDataprotocollo());
	}
	//istanza.alberoproc.scdescrizione
	if (placeholder.equalsIgnoreCase("TIPOINTERVENTO")) {
	    return FormatUtils.stringFormat(data.getIstanza().getAlberoproc().getScDescrizione());
	}
	//istanza.alberoproc.scnote
	if (placeholder.equalsIgnoreCase("TIPOINTERVENTONOTE")) {
	    return FormatUtils.stringFormat(data.getIstanza().getAlberoproc().getScNote());
	}
	//istanza.posizionearchivio
	if (placeholder.equalsIgnoreCase("POSIZARCHIVIO")) {
	    return FormatUtils.stringFormat(data.getIstanza().getPosizionearchivio());
	}
	//istanza.codicelotto
	if (placeholder.equalsIgnoreCase("LOTTO")) {
	    return FormatUtils.integerFormat(data.getIstanza().getCodicelotto());
	}
	//istanza.lavori
	if (placeholder.equalsIgnoreCase("DESCRIZIONEDEILAVORI")) {
	    return FormatUtils.stringFormat(data.getIstanza().getLavori());
	}
	//istanza.lavoriestesa
	if (placeholder.equalsIgnoreCase("DESCRIZIONEPROGETTO")) {
	    return FormatUtils.stringFormat(data.getIstanza().getLavoriestesa());
	}
	//istanza.metriquadrati
	if (placeholder.equalsIgnoreCase("SUPERFICIE")) {
	    return FormatUtils.decimalFormat(data.getIstanza().getMetriquadrati());
	}
	//istanza.password
	if (placeholder.equalsIgnoreCase("PASSWORD")) {
	    return FormatUtils.stringFormat(data.getIstanza().getPassword());
	}
	//istanza.variantepr
	if (placeholder.equalsIgnoreCase("VARIANTEPRG")) {
	    return FormatUtils.booleanFormat(data.getIstanza().getVariantepr(), "Si", "No", "No");
	}
	//istanza.flagvia
	if (placeholder.equalsIgnoreCase("VALUTAZIMPATTOAMBIENTALE")) {
	    String val = "Non prevista";
	    short flagvia = data.getIstanza().getFlagvia() == null ? 0 : data.getIstanza().getFlagvia();
	    switch (flagvia) {
	    case 1:
		val = "V.I.A. Regionale";
		break;
	    case 2:
		val = "V.I.A. Nazionale";
		break;
	    }
	    return val;
	}
	// istanze.dominicilioElettronico
	if (placeholder.equalsIgnoreCase("DOMICILIOELETTRONICO")) {
	    return FormatUtils.stringFormat(data.getIstanza().getDomicilioElettronico());
	}
	//istanza.formato.fobase
	if (placeholder.equalsIgnoreCase("FORMATO")) {
	    PuFormati puf = data.getIstanza().getFormato();
	    String val = "";
	    if (puf != null) {
		val = FormatUtils.decimalFormat(puf.getFoBase());
	    }
	    return val;
	}
	//istanza.impianto.impianto
	if (placeholder.equalsIgnoreCase("TIPOIMPIANTO")) {
	    Impianti i = data.getIstanza().getImpianto();
	    String val = "";
	    if (i != null) {
		val = FormatUtils.stringFormat(i.getImpianto());
	    }
	    return val;
	}
	//istanza.tipiarchivioistanza.id.codice
	if (placeholder.equalsIgnoreCase("CODICEARCHIVIO")) {
	    Tipiarchivioistanze ta = data.getIstanza().getTipiarchivioistanza();
	    String val = "";
	    if (ta != null) {
		val = FormatUtils.integerFormat(ta.getId().getCodice());
	    }
	    return val;
	}
	//istanza.tipiarchivioistanza.archivio
	if (placeholder.equalsIgnoreCase("ARCHIVIOPRATICHE")) {
	    Tipiarchivioistanze ta = data.getIstanza().getTipiarchivioistanza();
	    String val = "";
	    if (ta != null) {
		val = FormatUtils.stringFormat(ta.getArchivio());
	    }
	    return val;
	}
	//istanza.tipologiaistanza.tidescrizione
	if (placeholder.equalsIgnoreCase("TIPOLOGIAISTANZA")) {
	    Tipologiaistanza ti = data.getIstanza().getTipologiaistanza();
	    String val = "";
	    if (ti != null) {
		val = FormatUtils.stringFormat(ti.getTiDescrizione());
	    }
	    return val;
	}
	//istanza.procedura.procedura
	if (placeholder.equalsIgnoreCase("TIPOPROCEDURA")) {
	    Tipiprocedure tp = data.getIstanza().getProcedura();
	    String val = "";
	    if (tp != null) {
		val = FormatUtils.stringFormat(tp.getProcedura());
	    }
	    return val;
	}
	//istanza.istanzetempistica.datafine
	if (placeholder.equalsIgnoreCase("DATASCADENZAISTANZA")) {
	    return FormatUtils.dateFormat(data.getIstanza().getIstanzeTempistica().getDatafine());
	}
	//istanza.codistanzacollegata -- DISMESSO
	if (placeholder.equalsIgnoreCase("NUMEROISTANZA_COLL")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.fkidregistro -- DISMESSO -- autorizregistro preso da autorizzazioni del movimento
	//	if (placeholder.equalsIgnoreCase("AUTORIZREGISTRO")) {
	//	    return getValueForUnsupportedPlaceholder(placeholder);
	//	}
	//istanza.tipisoggetto.tiposoggetto
	if (placeholder.equalsIgnoreCase("QUALITADI") || placeholder.equalsIgnoreCase("INQUALITADI")) {
	    Tipisoggetto ts = data.getIstanza().getTipisoggetto();
	    String val = "";
	    if (ts != null) {
		val = FormatUtils.stringFormat(ts.getTiposoggetto());
	    }
	    return val;
	}
	//istanza.tipisoggetto.tiposoggetto
	if (placeholder.equalsIgnoreCase("QUALITADI") || placeholder.equalsIgnoreCase("INQUALITADI")) {
	    Tipisoggetto ts = data.getIstanza().getTipisoggetto();
	    String val = "";
	    if (ts != null) {
		val = FormatUtils.stringFormat(ts.getTiposoggetto());
	    }
	    return val;
	}
	//istanza.comune.comune
	if (placeholder.equalsIgnoreCase("IST_COMUNE")) {
	    Comuni c = data.getIstanza().getComune();
	    return c != null ? c.getComune() : "";
	}
	//istanza.titolarelegale.nome + " " + istanza.titolarelegale.nominativo
	if (placeholder.equalsIgnoreCase("CO_RICHIEDENTE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    //	    ANAGRAFECO.NOMINATIVO || ' ' || ANAGRAFECO.NOME AS CONOMINATIVO
	    StringBuilder nominativo = new StringBuilder(tit != null ? FormatUtils.stringFormat(tit.getNominativo()) : "");
	    StringBuilder nome = new StringBuilder(tit != null ? FormatUtils.stringFormat(tit.getNome()) : "");
	    if (nome != null && StringUtils.isNotBlank(nome.toString())) {
		nominativo.append(" ");
		nominativo.append(tit.getNome());
	    }
	    //	    if (val.length() > 0 && tit != null) {
	    //		val.append(" ");
	    //		val.append(RtfUtilities.stringFormat(tit.getNome()));
	    //	    }
	    return nominativo.toString();
	}
	//istanza.titolarelegale.indirizzo
	if (placeholder.equalsIgnoreCase("CO_INDIRIZZO")) {
	    String val = "";
	    if (data.getIstanza().getTitolarelegale() != null) {
		val = FormatUtils.stringFormat(data.getIstanza().getTitolarelegale().getIndirizzocorrispondenza());
	    }
	    if (StringUtils.isBlank(val)) {
		Anagrafe tit = data.getIstanza().getTitolarelegale();
		val = tit != null ? FormatUtils.stringFormat(tit.getIndirizzo()) : "";
	    }
	    return val;
	}
	//istanza.titolarelegale.citta
	if (placeholder.equalsIgnoreCase("CO_CITTA")) {
	    String val = "";
	    if (data.getIstanza().getTitolarelegale() != null) {
		val = FormatUtils.stringFormat(data.getIstanza().getTitolarelegale().getCittacorrispondenza());
	    }
	    if (StringUtils.isBlank(val)) {
		Anagrafe tit = data.getIstanza().getTitolarelegale();
		val = tit != null ? FormatUtils.stringFormat(tit.getCitta()) : "";
	    }
	    return val;
	}
	//istanza.titolarelegale.cap
	if (placeholder.equalsIgnoreCase("CO_CAP")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getCapcorrispondenza()) : "";
	    if (StringUtils.isBlank(val)) {
		val = tit != null ? FormatUtils.stringFormat(tit.getCap()) : "";
	    }
	    return val;
	}
	//istanza.titolarelegale.comuneresidenza.comune
	if (placeholder.equalsIgnoreCase("CO_COMUNE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getComunecorrispondenza() != null) {
		val = tit.getComunecorrispondenza().getComune();
	    }
	    if (StringUtils.isBlank(val)) {
		if (tit != null && tit.getComuneResidenza() != null) {
		    val = tit.getComuneResidenza().getComune();
		}
	    }
	    return val;
	}
	//istanza.titolarelegale.provincia
	if (placeholder.equalsIgnoreCase("CO_PROVINCIA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getProvinciacorrispondenza()) : "";
	    if (StringUtils.isBlank(val)) {
		val = tit != null ? FormatUtils.stringFormat(tit.getProvincia()) : "";
	    }
	    return val;
	}
	//istanza.titolarelegale.fax
	if (placeholder.equalsIgnoreCase("CO_FAX")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getFax()) : "";
	    return val;
	}
	//istanza.titolarelegale.telefono
	if (placeholder.equalsIgnoreCase("CO_TELEFONO")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getTelefono()) : "";
	    return val;
	}
	//istanza.titolarelegale.datanascita
	if (placeholder.equalsIgnoreCase("CO_DATANASCITA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDatanascita()) : "";
	    return val;
	}
	//istanza.titolarelegale.codicefiscale
	if (placeholder.equalsIgnoreCase("CO_CODICEFISCALE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getCodicefiscale()) : "";
	    return val;
	}
	//istanza.titolarelegale.partitaiva
	if (placeholder.equalsIgnoreCase("CO_PARTITAIVA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getPartitaiva()) : "";
	    return val;
	}
	//istanza.titolarelegale.datanascita
	if (placeholder.equalsIgnoreCase("CO_DATACOSTITUZIONE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDatanominativo()) : "";
	    return val;
	}
	//istanza.titolarelegale.regditte
	if (placeholder.equalsIgnoreCase("CO_CCIAANR")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getRegditte()) : "";
	    return val;
	}
	//istanza.titolarelegale.dataregditte
	if (placeholder.equalsIgnoreCase("CO_CCIAADATA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDataregditte()) : "";
	    return val;
	}
	//istanza.titolarelegale.comunecomregditte.comune
	if (placeholder.equalsIgnoreCase("CO_CCIAACOMUNE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getComunecomregditte() != null) {
		val = FormatUtils.stringFormat(tit.getComunecomregditte().getComune());
	    }
	    return val;
	}
	//istanza.titolarelegale.regtrib
	if (placeholder.equalsIgnoreCase("CO_REGTRIBNR")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.stringFormat(tit.getRegtrib()) : "";
	    return val;
	}
	//istanza.titolarelegale.dataregtrib
	if (placeholder.equalsIgnoreCase("CO_REGTRIBDATA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = tit != null ? FormatUtils.dateFormat(tit.getDataregtrib()) : "";
	    return val;
	}
	//istanza.titolarelegale.comuneregtrib.comune
	if (placeholder.equalsIgnoreCase("CO_REGTRIBCOMUNE")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getComuneregtrib() != null) {
		val = FormatUtils.stringFormat(tit.getComuneregtrib().getComune());
	    }
	    return val;
	}
	//istanza.titolarelegale.comunenascita.comune
	if (placeholder.equalsIgnoreCase("CO_LUOGODINASCITA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getComuneNascita() != null) {
		val = FormatUtils.stringFormat(tit.getComuneNascita().getComune());
	    }
	    return val;
	}
	//istanza.titolarelegale.titolo.titolo
	if (placeholder.equalsIgnoreCase("CO_TITOLO")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getTitolo() != null) {
		val = FormatUtils.stringFormat(tit.getTitolo().getTitolo());
	    }
	    return val;
	}
	//istanza.titolarelegale.formagiuridica.formagiuridica
	if (placeholder.equalsIgnoreCase("CO_FORMAGIURIDICA")) {
	    Anagrafe tit = data.getIstanza().getTitolarelegale();
	    String val = "";
	    if (tit != null && tit.getFormagiuridica() != null) {
		val = FormatUtils.stringFormat(tit.getFormagiuridica().getFormagiuridica());
	    }
	    return val;
	}
	//istanza.responsabile.responsabile
	if (placeholder.equalsIgnoreCase("OPERATORESPORTELLO")) {
	    Responsabili resp = data.getIstanza().getResponsabile();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getResponsabile()) : "";
	    return val;
	}
	//istanza.responsabile.telefonolavoro
	if (placeholder.equalsIgnoreCase("TELEFONOPERATORE")) {
	    Responsabili resp = data.getIstanza().getResponsabile();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getTelefonolavoro()) : "";
	    return val;
	}
	//istanza.responsabile.email
	if (placeholder.equalsIgnoreCase("MAILOPERATORE")) {
	    Responsabili resp = data.getIstanza().getResponsabile();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getEmail()) : "";
	    return val;
	}
	//istanza.responsabileprocedimento.responsabile
	if (placeholder.equalsIgnoreCase("RESPONSPROCEDIMENTO")) {
	    Responsabili resp = data.getIstanza().getResponsabileProcedimento();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getResponsabile()) : "";
	    return val;
	}
	//istanza.responsabileprocedimento.titolo
	if (placeholder.equalsIgnoreCase("TITOLORESPONSABILEPROC")) {
	    String val = "";
	    if (data.getIstanza() != null && EntityUtils.getNestedProperty(data.getIstanza().getResponsabileProcedimento(), "id.codice") != null) {
		//val = data.getIstanza().getResponsabileProcedimento().getTitolo();
		val = data.getIstanza().getResponsabileProcedimento().getTitolo() != null
			? FormatUtils.stringFormat(data.getIstanza().getResponsabileProcedimento().getTitolo())
			: "";
	    }
	    return val;
	}
	//istanza.responsabileprocedimento.telefonolavoro
	if (placeholder.equalsIgnoreCase("TELEFONORESPONSABILE")) {
	    Responsabili resp = data.getIstanza().getResponsabileProcedimento();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getTelefonolavoro()) : "";
	    return val;
	}
	//istanza.responsabileprocedimento.email
	if (placeholder.equalsIgnoreCase("MAILRESPONSABILE")) {
	    Responsabili resp = data.getIstanza().getResponsabileProcedimento();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getEmail()) : "";
	    return val;
	}
	//istanza.istruttore.responsabile
	if (placeholder.equalsIgnoreCase("ISTRUTTORE")) {
	    Responsabili resp = data.getIstanza().getIstruttore();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getResponsabile()) : "";
	    return val;
	}
	//istanza.istruttore.responsabile.titolo
	if (placeholder.equalsIgnoreCase("TITOLOISTRUTTORE")) {
	    Responsabili resp = data.getIstanza().getIstruttore();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getTitolo()) : "";
	    return val;
	}
	//istanza.istruttore.email
	if (placeholder.equalsIgnoreCase("ISTRUTTOREEMAIL")) {
	    Responsabili resp = data.getIstanza().getIstruttore();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getEmail()) : "";
	    return val;
	}
	//istanza.istruttore.telefonolavoro
	if (placeholder.equalsIgnoreCase("ISTRUTTORETEL")) {
	    Responsabili resp = data.getIstanza().getIstruttore();
	    String val = resp != null ? FormatUtils.stringFormat(resp.getTelefonolavoro()) : "";
	    return val;
	}
	//istanza.i_attivita.nomeattivita
	if (placeholder.equalsIgnoreCase("DENOMINAZIONE_IATTIVITA")) {
	    IAttivita att = data.getIstanza().getAttivita();
	    String val = "";
	    if (att != null) {
		val = FormatUtils.stringFormat(att.getDenominazione());
	    }
	    return val;
	}
	//istanza.attivita.denominazione
	if (placeholder.equalsIgnoreCase("DENOMINAZIONEATTIVITA")) {
	    String val = "";
	    if (StringUtils.isNotBlank(data.getIstanza().getNomeattivita())) {
		val = FormatUtils.stringFormat(data.getIstanza().getNomeattivita());
	    }
	    return val;
	}
	//istanza.settore.settore
	if (placeholder.equalsIgnoreCase("SETTOREISTAT")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.attivita.istat
	if (placeholder.equalsIgnoreCase("ATTIVITAISTAT")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//istanza.richiedente.nome + istanza.richiedente.nominativo + istanza.tipisoggetto.tiposoggetto + istanza.titolarelegale.nominativo
	if (placeholder.equalsIgnoreCase("RICQUAAZI")) {
	    StringBuilder sb = new StringBuilder();
	    Anagrafe richiedente = data.getIstanza().getRichiedente();
	    sb.append(FormatUtils.stringFormat(richiedente.getNome()));
	    if (sb.length() > 0) {
		sb.append(" ");
	    }
	    sb.append(richiedente.getNominativo().trim());
	    Tipisoggetto tsRichiedente = data.getIstanza().getTipisoggetto();
	    if (tsRichiedente != null) {
		sb.append(" ").append(FormatUtils.stringFormat(tsRichiedente.getTiposoggetto()));
	    }
	    Anagrafe titLegale = data.getIstanza().getTitolarelegale();
	    if (titLegale != null) {
		sb.append(" ").append(titLegale.getNominativo().trim());
	    }
	    return sb.toString();
	}
	//istanza.intervento.intervento oppure ricostruito concatenando alberoproc.scdescrizione risalendo l'alberoproc
	if (placeholder.equalsIgnoreCase("INTERVENTODAALBERO")) {
	    StringBuilder sb = new StringBuilder();
	    Alberoproc alberoproc = data.getIstanza().getAlberoproc();
	    sb.append(alberoproc.getScDescrizione());
	    String codAlberoProc = alberoproc.getScCodice().length() > 2
		    ? alberoproc.getScCodice().substring(0, alberoproc.getScCodice().length() - 2)
		    : alberoproc.getScCodice();
	    while (codAlberoProc.length() > 2) {
		alberoproc = alberoprocService.findByScCodice(codAlberoProc);
		sb.insert(0, '-');
		sb.insert(0, FormatUtils.stringFormat(alberoproc.getScDescrizione()));
		codAlberoProc = codAlberoProc.substring(0, codAlberoProc.length() - 2);
	    }
	    return sb.toString();
	}
	//istanzaareaprimaria.area.denominazione
	if (placeholder.equalsIgnoreCase("AREAINDUSTRIALE")) {
	    Istanzearee ia = data.getIstanzaAreaPrimaria();
	    String val = "";
	    if (ia != null) {
		val = FormatUtils.stringFormat(ia.getArea().getDenominazione());
	    }
	    return val;
	}
	// "(" + istanzastradarioprimaria.cap + ") " + istanzastradarioprimaria.stradario.prefisso + " " + istanzastradarioprimaria.stradario.descrizione
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE")) {
	    String val = "";
	    Stradario s = data.getStradarioPrimario();
	    if (s != null) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(s.getCap())) {
		    sb.append("(").append(s.getCap()).append(") ");
		}
		sb.append(FormatUtils.stringFormat(s.getPrefisso()));
		sb.append(" ").append(FormatUtils.stringFormat(s.getDescrizione()));
		val = sb.toString();
	    }
	    return val;
	}
	//istanzastradarioprimaria.stradario.prefisso + " " + istanzastradarioprimaria.stradario.descrizione
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_INDIRIZZO")) {
	    String val = "";
	    Stradario s = data.getStradarioPrimario();
	    if (s != null) {
		StringBuilder sb = new StringBuilder(FormatUtils.stringFormat(s.getPrefisso()));
		sb.append(" ").append(FormatUtils.stringFormat(s.getDescrizione()));
		val = sb.toString();
	    }
	    return val;
	}
	//istanzastradarioprimaria.stradario.localita
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_LOCALITA")) {
	    String val = "";
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    if (is != null) {
		val = FormatUtils.stringFormat(is.getFrazione());
	    }
	    //	    Stradario s = data.getStradarioPrimario();
	    //	    if (s != null) {
	    //		val = RtfUtilities.stringFormat(s.getLocfraz());
	    //	    }
	    return val;
	}
	// istanzastradarioprimaria.stradario.cap
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_CAP")) {
	    String val = "";
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    if (is != null) {
		val = FormatUtils.stringFormat(is.getCap());
	    }
	    return val;
	}
	// istanzastradarioprimaria.civico
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_CIVICO")) {
	    String val = "";
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    if (is != null) {
		val = FormatUtils.stringFormat(is.getCivico());
	    }
	    return val;
	}
	// istanzastradarioprimaria.circoscrizione
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_CIRCOSCRIZIONE")) {
	    String val = "";
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    if (is != null) {
		val = FormatUtils.stringFormat(is.getCircoscrizione());
	    }
	    return val;
	}
	// istanzastradarioprimaria.stradariocolore.colore
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_COLORE")) {
	    String val = "";
	    Istanzestradario isp = data.getIstanzaStradarioPrimaria();
	    Stradariocolore sc = null;
	    if (isp != null) {
		sc = isp.getStradariocolore();
	    }
	    if (sc != null) {
		val = FormatUtils.stringFormat(sc.getColore());
	    }
	    return val;
	}
	// istanzastradarioprimaria.esponente
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_ESPONENTE")) {
	    String val = "";
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    if (is != null) {
		val = FormatUtils.stringFormat(is.getEsponente());
	    }
	    return val;
	}
	// istanzamappalepprimaria.foglio
	if (placeholder.equalsIgnoreCase("FOGLIO")) {
	    String val = "";
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    if (im != null) {
		val = FormatUtils.stringFormat(data.getIstanzaMappalePrimaria().getFoglio());
	    }
	    return val;
	}
	// istanzamappalepprimaria.particella
	if (placeholder.equalsIgnoreCase("PARTICELLA")) {
	    String val = "";
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    if (im != null) {
		val = FormatUtils.stringFormat(data.getIstanzaMappalePrimaria().getParticella());
	    }
	    return val;
	}
	// istanzamappalepprimaria.sub
	if (placeholder.equalsIgnoreCase("SUB")) {
	    String val = "";
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    if (im != null) {
		val = FormatUtils.stringFormat(data.getIstanzaMappalePrimaria().getSub());
	    }
	    return val;
	}
	//movimento.dataprotocollo
	if (placeholder.equalsIgnoreCase("MOVDATAPROTOCOLLO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.dateFormat(data.getMovimento().getDataprotocollo());
	    }
	    return val;
	}
	//movimento.dataprotocollo
	if (placeholder.equalsIgnoreCase("MOV_DATAPROT")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.dateFormat(data.getMovimento().getDataprotocollo());
	    }
	    return val;
	}
	//movimento.numeroprotocollo
	if (placeholder.equalsIgnoreCase("MOVNUMEROPROTOCOLLO") || placeholder.equalsIgnoreCase("MOV_NRPROT")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getNumeroprotocollo());
	    }
	    return val;
	}
	//movimento.tipomovimento.tipomovimento
	if (placeholder.equalsIgnoreCase("MOVCODICETIPOMOVIMENTO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getTipomovimento().getId().getTipomovimento());
	    }
	    return val;
	}
	//movimento.movimento
	if (placeholder.equalsIgnoreCase("MOVDESCRIZIONEMOVIMENTO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getMovimento());
	    }
	    return val;
	}
	//movimento.data
	if (placeholder.equalsIgnoreCase("MOV_DATA") || placeholder.equalsIgnoreCase("MOVDATAMOVIMENTO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.dateFormat(data.getMovimento().getData());
	    }
	    return val;
	}
	//movimento.id.codice
	if (placeholder.equalsIgnoreCase("MOV_CODICE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.integerFormat(data.getMovimento().getId().getCodice());
	    }
	    return val;
	}
	//movimento.endoprocedimento.procedimento
	if (placeholder.equalsIgnoreCase("MOV_ENDO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		if (EntityUtils.getNestedProperty(data.getMovimento().getEndoprocedimento(), "id.codice") != null) {
		    Inventarioprocedimenti ip = data.getMovimento().getEndoprocedimento();
		    val = FormatUtils.stringFormat(ip.getProcedimento());
		}
	    }
	    return val;
	}
	//movimento.esito
	if (placeholder.equalsIgnoreCase("MOV_ESITO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.booleanFormat(data.getMovimento().getEsito(), "positivo", "negativo", "non previsto");
	    }
	    return val;
	}
	//movimento.parere
	if (placeholder.equalsIgnoreCase("MOV_PARERE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getParere());
	    }
	    return val;
	}
	//movimento.note
	if (placeholder.equalsIgnoreCase("MOV_NOTE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getNote());
	    }
	    return val;
	}
	//movimento.parere
	if (placeholder.equalsIgnoreCase("MOV_PUBBLICARE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		val = FormatUtils.booleanFormat(data.getMovimento().getPubblica(), "Si", "No", "");
	    }
	    return val;
	}
	//movimento.?????
	if (placeholder.equalsIgnoreCase("MOVCLASSIFICA")) {
	    return getValueForUnsupportedPlaceholder(placeholder);
	}
	//movimento.amministrazioni.amministrazione
	if (placeholder.equalsIgnoreCase("MOV_AMMINISTRAZIONE") || placeholder.equalsIgnoreCase("MOVAMMINISTRAZIONE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		if (EntityUtils.getNestedProperty(data.getMovimento().getAmministrazioni(), "id.codice") != null) {
		    val = FormatUtils.stringFormat(data.getMovimento().getAmministrazioni().getAmministrazione());
		}
	    }
	    return val;
	}
	//movimento.amministrazionireferenti.ufficio
	if (placeholder.equalsIgnoreCase("MOV_UFFICIO") || placeholder.equalsIgnoreCase("MOVUFFICIO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		if (EntityUtils.getNestedProperty(data.getMovimento().getAmministrazionireferenti(), "id.codice") != null) {
		    val = FormatUtils.stringFormat(data.getMovimento().getAmministrazionireferenti().getUfficio());
		}
	    }
	    return val;
	}
	//movimento.amministrazioni indirizzo completo
	if (placeholder.equalsIgnoreCase("MOVAMMINISTRAZIONEINDIRIZZO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		Amministrazioni amm = data.getMovimento().getAmministrazioni();
		IndirizzoDestinatario ind = new IndirizzoDestinatario();
		ind.setIndirizzo(amm.getIndirizzo());
		ind.setCap(amm.getCap());
		ind.setCitta(amm.getCitta());
		ind.setProvincia(amm.getProvincia());
		val = ind.buildIndirizzo(" ").toString();
	    }
	    return val;
	}
	//movimento.amministrazionireferenti indirizzo completo
	if (placeholder.equalsIgnoreCase("MOVUFFICIOINDIRIZZO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		Amministrazionireferenti ammRef = data.getMovimento().getAmministrazionireferenti();
		IndirizzoDestinatario ind = new IndirizzoDestinatario();
		ind.setIndirizzo(ammRef.getIndirizzo());
		ind.setCap(ammRef.getCap());
		ind.setCitta(ammRef.getCitta());
		ind.setProvincia(ammRef.getProvincia());
		val = ind.buildIndirizzo(" ").toString();
	    }
	    return val;
	}
	//movimento.amministrazione indirizzo email di PEC
	if (placeholder.equalsIgnoreCase("MOV_AMMINISTRAZIONE_PEC")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		Amministrazioni amm = data.getMovimento().getAmministrazioni();
		if (EntityUtils.getNestedProperty(amm, "id.codice") != null) {
		    if (amm.getPec() != null) {
			val = FormatUtils.stringFormat(amm.getPec());
		    }
		}
	    }
	    return val;
	}
	//movimento.amministrazione indirizzo email
	if (placeholder.equalsIgnoreCase("MOV_AMMINISTRAZIONE_EMAIL")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		Amministrazioni amm = data.getMovimento().getAmministrazioni();
		if (EntityUtils.getNestedProperty(amm, "id.codice") != null) {
		    if (amm.getEmail() != null) {
			val = FormatUtils.stringFormat(amm.getEmail());
		    }
		}
	    }
	    return val;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////GIANPAOLO///////////////////////////////////////////////////
	//movimento.datascadenza
	if (placeholder.equalsIgnoreCase("MOV_DATASCADENZA")) {
	    String val = "";
	    if (data.getMovimento() != null && data.getMovimento().getDataScadenza() != null) {
		Date datascadenza = data.getMovimento().getDataScadenza();
		SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		val = FormatUtils.dateFormat(datascadenza, dateFormat);
	    }
	    return val;
	}
	if (placeholder.equalsIgnoreCase("MOV_OPERATORE")) {
	    String val = "";
	    if (data.getMovimento() != null && EntityUtils.getNestedProperty(data.getMovimento(), "id.codice") != null) {
		val = FormatUtils.stringFormat(data.getMovimento().getResponsabile().getResponsabile());
	    }
	    return val;
	}
	//DATA SCADENZA DEL MOVIMENTO ESEGUITO
	if (placeholder.toUpperCase().startsWith("MOV_DATASCADENZA_FATTO(")) {
	    String argument = placeholder.substring(23, placeholder.length() - 1).trim();
	    String val = "";
	    try {
		Integer codiceIstanza = data.getIstanza().getId().getCodice();
		String codTipomovimento = argument;
		List<Movimenti> movimentiFatti = movimentiService.findMovimentiIstanzaFattiByTipoMovimento(codTipomovimento, codiceIstanza);
		for (Movimenti movimenti : movimentiFatti) {
		    if (movimenti.getDataScadenza() != null) {
			Date datascadenza = movimenti.getDataScadenza();
			SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
			val = FormatUtils.dateFormat(datascadenza, dateFormat);
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	//DATA SCADENZA DEL MOVIMENTO DA ESEGUIRE
	if (placeholder.toUpperCase().startsWith("MOV_DATASCADENZA_DAFARE(")) {
	    String argument = placeholder.substring(24, placeholder.length() - 1).trim();
	    String val = "";
	    try {
		Integer codiceIstanza = data.getIstanza().getId().getCodice();
		String codTipomovimento = argument;
		List<Movimenti> movimentiDaFare = movimentiService.findMovimentiIstanzaDaFareByTipoMovimento(codTipomovimento, codiceIstanza);
		for (Movimenti movimenti : movimentiDaFare) {
		    if (movimenti.getDataScadenza() != null) {
			Date datascadenza = movimenti.getDataScadenza();
			SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
			val = FormatUtils.dateFormat(datascadenza, dateFormat);
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//descrizione della radice dell'alberoproc dell'istanza
	if (placeholder.equalsIgnoreCase("INTERVENTODAALBEROPRIMAVOCE")) {
	    String val = "";
	    Alberoproc alberoprocIstanza = data.getIstanza().getAlberoproc();
	    if (StringUtils.isNotBlank(alberoprocIstanza.getScCodice()) && alberoprocIstanza.getScCodice().length() > 1) {
		String codiceRoot = alberoprocIstanza.getScCodice().substring(0, 2);
		Alberoproc rootAlberoproc = data.getAlberoprocByScCodice(codiceRoot);
		if (rootAlberoproc != null) {
		    val = rootAlberoproc.getScDescrizione();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.siintestazione1
	if (placeholder.equalsIgnoreCase("CAS_INTESTAZIONE1")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione1())) {
		val = cas.getSiIntestazione1();
	    } else {
		cas = data.getDatiComuneassociatoTT();
		if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione1())) {
		    val = cas.getSiIntestazione1();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.siintestazione2
	if (placeholder.equalsIgnoreCase("CAS_INTESTAZIONE2")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione2())) {
		val = cas.getSiIntestazione2();
	    } else {
		cas = data.getDatiComuneassociatoTT();
		if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione2())) {
		    val = cas.getSiIntestazione2();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.siintestazione3
	if (placeholder.equalsIgnoreCase("CAS_INTESTAZIONE3")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione3())) {
		val = cas.getSiIntestazione3();
	    } else {
		cas = data.getDatiComuneassociatoTT();
		if (cas != null && StringUtils.isNotBlank(cas.getSiIntestazione3())) {
		    val = cas.getSiIntestazione3();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.sipdp1
	if (placeholder.equalsIgnoreCase("CAS_PIEPAGINA1")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && StringUtils.isNotBlank(cas.getSiPdp1())) {
		val = cas.getSiPdp1();
	    } else {
		cas = data.getDatiComuneassociatoTT();
		if (cas != null && StringUtils.isNotBlank(cas.getSiPdp1())) {
		    val = cas.getSiPdp1();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.sipdp2
	if (placeholder.equalsIgnoreCase("CAS_PIEPAGINA2")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && StringUtils.isNotBlank(cas.getSiPdp2())) {
		val = cas.getSiPdp2();
	    } else {
		cas = data.getDatiComuneassociatoTT();
		if (cas != null && StringUtils.isNotBlank(cas.getSiPdp2())) {
		    val = cas.getSiPdp2();
		}
	    }
	    return val;
	}
	//comuneassociatosoftware.sistemma
	if (placeholder.equalsIgnoreCase("CAS_STEMMA")) {
	    String val = "";
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    try {
		if (cas != null && cas.getOggetti() != null) {
		    Oggetti oggetto = oggettiService.findById(cas.getOggetti().getId());
		    byte[] binaryData = oggetto.getOggetto();
		    if (binaryData != null && binaryData.length > 0) {
			val = convertBytesToRtfImage(binaryData);
		    }
		}
		if (val.length() == 0) {
		    cas = data.getDatiComuneassociatoTT();
		    if (EntityUtils.getNestedProperty(cas, "oggetti.id.codice") != null) {
			Oggetti oggetto = oggettiService.findById(cas.getOggetti().getId());
			byte[] binaryData = oggetto.getOggetto();
			if (binaryData != null && binaryData.length > 0) {
			    val = convertBytesToRtfImage(binaryData);
			}
		    }
		}
	    } catch (IOException e) {
		log.error("Errore nella sostituzione del segnaposto " + placeholder, e);
	    }
	    return val;
	}
	/*
	 * autorizzazione associate al movimento. Viene presa la più recente.
	 * autorizzazioni.autoriznumero
	 */
	if (placeholder.equalsIgnoreCase("AUTORIZNUMERO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.stringFormat(auth.getAutoriznumero());
		}
	    }
	    return val;
	}
	/*
	 * autorizzazione associate al movimento. Viene presa la più recente.
	 * autorizzazioni.autorizdata
	 */
	if (placeholder.equalsIgnoreCase("AUTORIZDATA")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.dateFormat(auth.getAutorizdata());
		}
	    }
	    return val;
	}
	if (placeholder.equalsIgnoreCase("AUTORIZDATASCADENZA")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    if (auth.getDatascadenza() != null) {
			val = FormatUtils.dateFormat(auth.getDatascadenza());
		    }
		}
	    }
	    return val;
	}
	/*
	 * autorizzazione associate al movimento. Viene presa la più recente.
	 * autorizzazioni.autorizresponsabile
	 */
	if (placeholder.equalsIgnoreCase("AUTORIZRESPONSABILE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.stringFormat(auth.getAutorizresponsabile());
		}
	    }
	    return val;
	}
	/*
	 * autorizzazione associate al movimento. Viene presa la più recente.
	 * autorizzazioni.autorizdataregistr
	 */
	if (placeholder.equalsIgnoreCase("AUTORIZDATARESPONSABILE")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.dateFormat(auth.getAutorizdataregistr());
		}
	    }
	    return val;
	}
	/*
	 * autorizzazione associate al movimento. Viene presa la più recente.
	 * autorizzazioni.tipologiaregistro.trdescrizione
	 */
	if (placeholder.equalsIgnoreCase("AUTORIZREGISTRO")) {
	    String val = "";
	    if (data.getMovimento() != null) {
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaMovimento(data.getIstanza(), data.getMovimento());
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    Tipologiaregistri tr = auth.getTipologiaregistro();
		    val = FormatUtils.stringFormat(tr.getTrDescrizione());
		}
	    }
	    return val;
	}
	//PROPRIETA' DI TIPO LISTA
	//lista endo procedimenti attivati
	if (placeholder.equalsIgnoreCase("LISTAENDOATTIVATI")) {
	    List<Istanzeprocedimenti> endos = data.getEndoProcedimenti();
	    StringBuilder sbEndo = new StringBuilder();
	    for (Istanzeprocedimenti endo : endos) {
		if (BooleanUtils.isTrue(endo.getInventarioprocedimenti().getDisabilitato())) {
		    continue;
		}
		if (endo.getInventarioprocedimenti().getId().getCodice() == 0) {
		    continue;
		}
		Amministrazioni amm = endo.getInventarioprocedimenti().getAmministrazioni();
		if (amm != null) {
		    sbEndo.append(FormatUtils.stringFormat(amm.getAmministrazione()));
		    Amministrazionireferenti ammRef = endo.getInventarioprocedimenti().getAmministrazionireferente();
		    if (ammRef != null) {
			sbEndo.append(" (").append(FormatUtils.stringFormat(ammRef.getUfficio())).append(")");
		    }
		}
		sbEndo.append(" - ");
		sbEndo.append(FormatUtils.stringFormat(endo.getInventarioprocedimenti().getProcedimento()));
		sbEndo.append(RtfConstants.RTF_CRLF);
	    }
	    return sbEndo.toString();
	}
	//lista endo procedimenti attivati senza descrizione Amministrazione
	if (placeholder.equalsIgnoreCase("LISTAENDOATTIVATISENZAAMM")) {
	    List<Istanzeprocedimenti> endos = data.getEndoProcedimenti();
	    StringBuilder sbEndo = new StringBuilder();
	    for (Istanzeprocedimenti endo : endos) {
		if (BooleanUtils.isTrue(endo.getInventarioprocedimenti().getDisabilitato())) {
		    continue;
		}
		if (endo.getInventarioprocedimenti().getId().getCodice() == 0) {
		    continue;
		}
		sbEndo.append(FormatUtils.stringFormat(endo.getInventarioprocedimenti().getProcedimento()));
		sbEndo.append(RtfConstants.RTF_CRLF);
	    }
	    return sbEndo.toString();
	}
	//lista movimenti eseguiti per data decrescente 
	if (placeholder.equalsIgnoreCase("LISTAMOVIMENTI")) {
	    StringBuilder sbMov = new StringBuilder();
	    List<Movimenti> movimenti = data.getMovimentiEseguiti();
	    StringBuilder sbRiga = null;
	    for (Movimenti mov : movimenti) {
		sbRiga = new StringBuilder("In data ");
		sbRiga.append(FormatUtils.dateFormat(mov.getData()));
		sbRiga.append(" \\\\'e8 stato creato il movimento ").append(mov.getTipomovimento().getMovimento());
		Inventarioprocedimenti endo = mov.getEndoprocedimento();
		if (endo != null && endo.getId().getCodice() != 0) {
		    sbRiga.append(" (").append(mov.getEndoprocedimento().getProcedimento()).append(")");
		}
		sbRiga.append(" per ").append(FormatUtils.stringFormat(mov.getAmministrazioni().getAmministrazione()));
		sbRiga.append(". Protocollo: ").append(FormatUtils.stringFormat(mov.getNumeroprotocollo())).append(" ");
		sbRiga.append(FormatUtils.dateFormat(mov.getDataprotocollo()));
		sbRiga.append(". Esito: ").append(FormatUtils.booleanFormat(mov.getEsito(), "positivo", "negativo", "non previsto"));
		sbRiga.append(". Pubblica: ").append(FormatUtils.booleanFormat(mov.getPubblica(), "si", "no", ""));
		//"risposta nei termini" era appeso sempre come stringa vuota perciò l'ho tolto
		sbRiga.append(RtfConstants.RTF_CRLF);
		sbMov.insert(0, sbRiga.toString());
	    }
	    return sbMov.toString();
	}
	//cointestatari nome + " " + nominativo
	if (placeholder.equalsIgnoreCase("COINTESTATARI")) {
	    Set<Istanzerichiedenti> irSet = data.getIstanza().getIstanzerichiedentis();
	    StringBuilder sb = new StringBuilder();
	    for (Istanzerichiedenti ir : irSet) {
		sb.append(new DatiRichiedente(ir).buildCointestatarioString(false)).append(RtfConstants.RTF_CRLF);
	    }
	    if (sb.length() >= RtfConstants.RTF_CRLF.length()) {
		sb.delete(sb.length() - RtfConstants.RTF_CRLF.length(), sb.length());
	    }
	    return sb.toString();
	}
	//cointestatari estesi nome + " " + nominativo + dati residenza + dati nascita
	if (placeholder.equalsIgnoreCase("COINTESTATARIESTESO")) {
	    Set<Istanzerichiedenti> irSet = data.getIstanza().getIstanzerichiedentis();
	    StringBuilder sb = new StringBuilder();
	    for (Istanzerichiedenti ir : irSet) {
		sb.append(
			new DatiRichiedente(ir).buildCointestatarioEstesoString(false, true, true, "Residenza:", true, false, RtfConstants.RTF_CRLF))
			.append(RtfConstants.RTF_CRLF);
	    }
	    if (sb.length() >= RtfConstants.RTF_CRLF.length()) {
		sb.delete(sb.length() - RtfConstants.RTF_CRLF.length(), sb.length());
	    }
	    return sb.toString();
	}
	//cointestatari estesi nome + " " + nominativo + dati residenza + dati nascita + cf + pi
	if (placeholder.equalsIgnoreCase("COINTESTATARIESTESO_CFPI")) {
	    Set<Istanzerichiedenti> irSet = data.getIstanza().getIstanzerichiedentis();
	    StringBuilder sb = new StringBuilder();
	    for (Istanzerichiedenti ir : irSet) {
		sb.append(new DatiRichiedente(ir).buildCointestatarioEstesoString(false, true, true, "Residenza:", true, true, RtfConstants.RTF_CRLF))
			.append(RtfConstants.RTF_CRLF);
	    }
	    if (sb.length() >= RtfConstants.RTF_CRLF.length()) {
		sb.delete(sb.length() - RtfConstants.RTF_CRLF.length(), sb.length());
	    }
	    return sb.toString();
	}
	//istanzeallegati
	if (placeholder.equalsIgnoreCase("ALLEGATIRICHIESTI")) {
	    Set<Istanzeallegati> allegati = data.getIstanza().getIstanzeallegatis();
	    StringBuilder sb = new StringBuilder();
	    for (Istanzeallegati all : allegati) {
		sb.append(all.getAllegatoextra()).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//istanzeallegati presentati (selzionato == 1)
	if (placeholder.equalsIgnoreCase("ALLEGATIPRESENTATI")) {
	    Set<Istanzeallegati> allegati = data.getIstanza().getIstanzeallegatis();
	    StringBuilder sb = buildAllegatiPresentatiString(allegati);
	    return sb.toString();
	}
	//mappali: particelle raggruppate per tipo catasto, tutto in un unica riga
	if (placeholder.equalsIgnoreCase("PARTICELLEUNICO")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzemappali> mappali = data.getIstanzeMappali();
	    String catasto = null;
	    String catastoTemp = "";
	    for (Istanzemappali map : mappali) {
		catastoTemp = map.getCatasto() != null ? FormatUtils.stringFormat(map.getCatasto().getDescrizione()) : "";
		if (!catastoTemp.equals(catasto)) {
		    catasto = catastoTemp;
		    sb.append("Catasto: ").append(catasto).append(", ");
		}
		sb.append("Particella: ").append(FormatUtils.stringFormat(map.getParticella())).append(", ");
	    }
	    if (mappali.size() > 0) {
		sb.delete(sb.length() - 2, sb.length());
	    }
	    return sb.toString();
	}
	//mappali
	if (placeholder.equalsIgnoreCase("LISTAMAPPALI") || placeholder.equalsIgnoreCase("MAPPALIISTANZA")) {
	    List<Istanzemappali> mappali = data.getIstanzeMappali();
	    StringBuilder sb = new StringBuilder();
	    for (Istanzemappali map : mappali) {
		if (map.getCatasto() != null && StringUtils.isNotBlank(map.getCatasto().getDescrizione())) {
		    sb.append("Catasto: ").append(map.getCatasto().getDescrizione().trim()).append(", ");
		}
		sb.append("Foglio: ").append(FormatUtils.stringFormat(map.getFoglio())).append(", ");
		sb.append("Particella/e: ").append(FormatUtils.stringFormat(map.getParticella())).append(", ");
		sb.append("Sub: ").append(FormatUtils.stringFormat(map.getSub())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//oneri
	if (placeholder.equalsIgnoreCase("ONEQUA")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimenti();
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento()));
		    sb.append(", Pagato: ").append(currencyFormat(proc.getCostopagato()));
		    sb.append(", Richiesto: ").append(currencyFormat(proc.getCostorichiesto()));
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti autorizzativi
	if (placeholder.equalsIgnoreCase("ENDOAUTORIZZATIVI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, true, null, null);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti non autorizzativi
	if (placeholder.equalsIgnoreCase("ENDONONAUTORIZZATIVI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, false, null, null);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti acquisiti
	if (placeholder.equalsIgnoreCase("ENDOACQUISITI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, null, true, null);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti non acquisiti
	if (placeholder.equalsIgnoreCase("ENDONONACQUISITI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, null, false, null);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti autocertificabili
	if (placeholder.equalsIgnoreCase("ENDOAUTOCERTIFICABILI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, null, null, true);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//endoprocedimenti non autocertificabili
	if (placeholder.equalsIgnoreCase("ENDONONAUTOCERTIFICABILI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimentiAAAA(true, null, null, false);
	    for (Istanzeprocedimenti proc : procs) {
		if (proc.getInventarioprocedimenti().getId().getCodice() > 0) {
		    sb.append(FormatUtils.integerFormat(proc.getInventarioprocedimenti().getId().getCodice())).append(" ");
		    sb.append(FormatUtils.stringFormat(proc.getInventarioprocedimenti().getProcedimento())).append(RtfConstants.RTF_CRLF);
		}
	    }
	    return sb.toString();
	}
	//lista endoprocedimenti attivati con parere di ritorno
	if (placeholder.equalsIgnoreCase("LISTAENDOATTIVATICONPARERE")) {
	    StringBuilder val = new StringBuilder();
	    List<IstanzeprocedimentiHelper> procs = data.getRiepilogoEndo();
	    for (IstanzeprocedimentiHelper endoh : procs) {
		Inventarioprocedimenti inv = endoh.getIstanzeprocedimenti().getInventarioprocedimenti();
		Movimenti movRitorno = endoh.getMovimentoRitorno();
		Movimenti movTrasmesso = endoh.getMovimentoRitorno();
		if (inv.getId().getCodice().equals(0)) {
		    continue;
		}
		if (inv.getDisabilitato()) {
		    continue;
		}
		if (movRitorno == null) {
		    continue;
		}
		if (movTrasmesso == null || movimentiService.isEffettuato(movTrasmesso)) {
		    continue;
		}
		val.append(FormatUtils.stringFormat(inv.getAmministrazioni().getAmministrazione())).append(" - ");
		val.append(FormatUtils.stringFormat(inv.getProcedimento())).append(RtfConstants.RTF_CRLF);
		val.append(FormatUtils.stringFormat(movRitorno.getParere())).append(RtfConstants.RTF_CRLF);
	    }
	    return val.toString();
	}
	//lista endoprocedimenti attivati con numero e data protocollo
	if (placeholder.equalsIgnoreCase("LISTAENDOATTIVATIPROTOCOLLI")) {
	    StringBuilder val = new StringBuilder();
	    List<IstanzeprocedimentiHelper> procs = data.getRiepilogoEndo();
	    for (IstanzeprocedimentiHelper endoh : procs) {
		Inventarioprocedimenti inv = endoh.getIstanzeprocedimenti().getInventarioprocedimenti();
		Movimenti movRitorno = endoh.getMovimentoRitorno();
		Movimenti movTrasmesso = endoh.getMovimentoRitorno();
		if (inv.getId().getCodice().equals(0)) {
		    continue;
		}
		if (inv.getDisabilitato()) {
		    continue;
		}
		if (movRitorno == null) {
		    continue;
		}
		if (movTrasmesso == null || movimentiService.isEffettuato(movTrasmesso)) {
		    continue;
		}
		val.append(FormatUtils.stringFormat(inv.getAmministrazioni().getAmministrazione()));
		if (StringUtils.isNotBlank(movRitorno.getNumeroprotocollo())) {
		    val.append(" Nr. Prot. ").append(movRitorno.getNumeroprotocollo().trim());
		}
		if (movRitorno.getDataprotocollo() != null) {
		    val.append(" del ").append(FormatUtils.dateFormat(movRitorno.getDataprotocollo()));
		}
		val.append(RtfConstants.RTF_CRLF);
	    }
	    return val.toString();
	}
	//documentiistanza (necessario == true)
	if (placeholder.equalsIgnoreCase("DOCUMENTIRICHIESTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(true, null);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);//TODO whats \\par?
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == true && presente == false)
	if (placeholder.equalsIgnoreCase("DOCUMENTIMANCANTI") || placeholder.equalsIgnoreCase("DOCRICHIESTINONPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(true, false);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == true && presente == true)
	if (placeholder.equalsIgnoreCase("DOCUMENTIPRESENTATI") || placeholder.equalsIgnoreCase("LISTADOCRICHIESTIPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(true, true);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == false && presente == true)
	if (placeholder.equalsIgnoreCase("LISTADOCNONRICHIESTIPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(false, true);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == true && presente == false)
	if (placeholder.equalsIgnoreCase("LISTADOCRICHIESTINONPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(true, false);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == false && presente == false)
	if (placeholder.equalsIgnoreCase("LISTADOCNONRICHIESTINONPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(false, false);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	// documentiistanza validi
	if (placeholder.equalsIgnoreCase("LISTADOCISTANZAVALIDI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanzaValidi(true);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento()));
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documenti istanza non validi
	if (placeholder.equalsIgnoreCase("LISTADOCISTANZANONVALIDI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanzaValidi(false);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento()));
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//documentiistanza (necessario == true && presente == true) documento + data
	if (placeholder.equalsIgnoreCase("LISTADOCRICHIESTIPRESENTICONDATA")) {
	    StringBuilder sb = new StringBuilder();
	    List<Documentiistanza> docs = data.getDocumentiIstanza(true, true);
	    for (Documentiistanza doc : docs) {
		sb.append(FormatUtils.stringFormat(doc.getDocumento()));
		if (doc.getData() != null) {
		    sb.append(" ").append(FormatUtils.dateFormat(doc.getData()));
		}
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//istanzeallegati [documenti endo] (presente == true || presnete == false)
	if (placeholder.equalsIgnoreCase("LISTADOCENDO")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeallegati> ias = data.getDocumentiEndo(null);
	    for (Istanzeallegati ia : ias) {
		sb.append(FormatUtils.stringFormat(ia.getAllegatoextra()));
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//istanzeallegati [documenti endo] (presente == true)
	if (placeholder.equalsIgnoreCase("LISTADOCENDOPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeallegati> ias = data.getDocumentiEndo(true);
	    for (Istanzeallegati ia : ias) {
		sb.append(FormatUtils.stringFormat(ia.getAllegatoextra()));
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//istanzeallegati [documenti endo] (presente == false)
	if (placeholder.equalsIgnoreCase("LISTADOCENDONONPRESENTI") || placeholder.equalsIgnoreCase("ALLEGATINONPRESENTI")) {
	    StringBuilder sb = new StringBuilder();
	    List<Istanzeallegati> ias = data.getDocumentiEndo(false);
	    for (Istanzeallegati ia : ias) {
		sb.append(FormatUtils.stringFormat(ia.getAllegatoextra()));
		sb.append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	//cdsinvitati (cdsinvitati.amministrazioni.amministrazione)
	Integer codAmmSportelloUnico = data.getConfigurazioneTT().getCodammsportellounico();
	//elenco destinatari
	if (placeholder.equalsIgnoreCase("ELENCODESTINATARI")) {
	    List<IndirizzoDestinatario> destinatari = new ArrayList<IndirizzoDestinatario>();
	    Amministrazioni amm = null;
	    Anagrafe anag = null;
	    IndirizzoDestinatario id = null;
	    StringBuilder sbName = new StringBuilder();
	    if (userData.isInvioAmministrazioni()) {
		if (userData.getCodiceAmministrazione() == null) {
		    Set<Istanzeprocedimenti> procs = data.getIstanza().getIstanzeprocedimentis();
		    for (Istanzeprocedimenti proc : procs) {
			amm = proc.getInventarioprocedimenti().getAmministrazioni();
			if (amm.getId().getCodice() == codAmmSportelloUnico) {
			    continue;
			}
			id = new IndirizzoDestinatario();
			id.setNominativo(amm.getAmministrazione());
			id.setIndirizzo(amm.getIndirizzo());
			id.setCap(amm.getCap());
			id.setCitta(amm.getCitta());
			id.setProvincia(amm.getProvincia());
			destinatari.add(id);
		    }
		} else {
		    amm = amministrazioniService.findById(new PkId(userData.getCodiceAmministrazione()));
		    id = new IndirizzoDestinatario();
		    id.setNominativo(amm.getAmministrazione());
		    id.setIndirizzo(amm.getIndirizzo());
		    id.setCap(amm.getCap());
		    id.setCitta(amm.getCitta());
		    id.setProvincia(amm.getProvincia());
		    destinatari.add(id);
		}
	    }
	    if (userData.isInvioRichiedente()) {
		anag = data.getIstanza().getRichiedente();
		id = new IndirizzoDestinatario();
		sbName = new StringBuilder();
		if (StringUtils.isNotBlank(anag.getNome())) {
		    sbName.append(anag.getNome().trim()).append(" ");
		}
		sbName.append(anag.getNominativo());
		id.setNominativo(sbName.toString());
		if (StringUtils.isNotBlank(anag.getIndirizzocorrispondenza())) {
		    id.setIndirizzo(anag.getIndirizzocorrispondenza());
		    id.setCap(anag.getCapcorrispondenza());
		    id.setCitta(anag.getCittacorrispondenza());
		    id.setProvincia(anag.getProvinciacorrispondenza());
		} else {
		    id.setIndirizzo(anag.getIndirizzo());
		    id.setCap(anag.getCap());
		    id.setCitta(anag.getCitta());
		    id.setProvincia(anag.getProvincia());
		}
		destinatari.add(id);
	    }
	    if (userData.isInvioTecnico()) {
		anag = data.getIstanza().getProfessionista();
		id = new IndirizzoDestinatario();
		sbName = new StringBuilder();
		if (StringUtils.isNotBlank(anag.getNome())) {
		    sbName.append(anag.getNome().trim()).append(" ");
		}
		sbName.append(anag.getNominativo());
		id.setNominativo(sbName.toString());
		if (StringUtils.isNotBlank(anag.getIndirizzocorrispondenza())) {
		    id.setIndirizzo(anag.getIndirizzocorrispondenza());
		    id.setCap(anag.getCapcorrispondenza());
		    id.setCitta(anag.getCittacorrispondenza());
		    id.setProvincia(anag.getProvinciacorrispondenza());
		} else {
		    id.setIndirizzo(anag.getIndirizzo());
		    id.setCap(anag.getCap());
		    id.setCitta(anag.getCitta());
		    id.setProvincia(anag.getProvincia());
		}
		destinatari.add(id);
	    }
	    if (userData.isInvioSoggettiIstanza()) {
		Set<Istanzerichiedenti> richiedenti = data.getIstanza().getIstanzerichiedentis();
		for (Istanzerichiedenti rich : richiedenti) {
		    anag = rich.getAnagrafeCollegata();
		    id = new IndirizzoDestinatario();
		    sbName = new StringBuilder();
		    if (StringUtils.isNotBlank(anag.getNome())) {
			sbName.append(anag.getNome().trim()).append(" ");
		    }
		    sbName.append(anag.getNominativo());
		    id.setNominativo(sbName.toString());
		    if (StringUtils.isNotBlank(anag.getIndirizzocorrispondenza())) {
			id.setIndirizzo(anag.getIndirizzocorrispondenza());
			id.setCap(anag.getCapcorrispondenza());
			id.setCitta(anag.getCittacorrispondenza());
			id.setProvincia(anag.getProvinciacorrispondenza());
		    } else {
			id.setIndirizzo(anag.getIndirizzo());
			id.setCap(anag.getCap());
			id.setCitta(anag.getCitta());
			id.setProvincia(anag.getProvincia());
		    }
		    destinatari.add(id);
		}
	    }
	    return buildDestinatariString(destinatari);
	}
	//lista amministrazioni coinvolte
	if (placeholder.equalsIgnoreCase("LISTAAMMINISTRAZCOINVOLTE")) {
	    StringBuilder val = new StringBuilder();
	    List<Istanzeprocedimenti> procs = data.getEndoProcedimenti();
	    for (Istanzeprocedimenti endo : procs) {
		if (endo.getInventarioprocedimenti().getId().getCodice().equals(0)) {
		    continue;
		}
		Amministrazionireferenti ammRef = endo.getInventarioprocedimenti().getAmministrazionireferente();
		Amministrazioni amm = endo.getInventarioprocedimenti().getAmministrazioni();
		IndirizzoDestinatario ind = new IndirizzoDestinatario();
		ind.setNominativo(amm.getAmministrazione());
		if (ammRef != null && StringUtils.isNotBlank(ammRef.getUfficio())) {
		    ind.setUfficio(ammRef.getUfficio());
		    ind.setIndirizzo(ammRef.getIndirizzo());
		    ind.setCap(ammRef.getCap());
		    ind.setCitta(ammRef.getCitta());
		    ind.setProvincia(ammRef.getProvincia());
		} else {
		    ind.setUfficio(amm.getUfficio());
		    ind.setIndirizzo(amm.getIndirizzo());
		    ind.setCap(amm.getCap());
		    ind.setCitta(amm.getCitta());
		    ind.setProvincia(amm.getProvincia());
		}
		val.append(ind.buildIndirizzo(RtfConstants.RTF_CRLF)).append(RtfConstants.RTF_CRLF);
	    }
	    return val.toString();
	}
	//somma delle superfici delle istanzeattivita aggregate per attivita.istat (attivita.istat + attivita.settori.tipiunitamisura.umdescbreve + sum(istanzeattivita.metriq))
	if (placeholder.equalsIgnoreCase("LISTADETTAGLIOINFORMAZIONE")) {
	    List<SuperficiAttivitaHelper> datiSuperfici = istanzeAttivitaService.getSommaSuperficiAttivitaPerAttivita(data.getIstanza(), null);
	    return buildSuperficiAttivitaString(datiSuperfici, true, false);
	}
	//somma delle superfici delle istanzeattivita aggregate per settore (attivita.settori.settore + attivita.settori.tipiunitamisura.umdescbreve + sum(istanzeattivita.metriq))
	if (placeholder.equalsIgnoreCase("LISTATIPIINFORMAZIONE")) {
	    List<SuperficiAttivitaHelper> datiSuperfici = istanzeAttivitaService.getSommaSuperficiAttivitaPerSettore(data.getIstanza(), null);
	    return buildSuperficiAttivitaString(datiSuperfici, false, true);
	}
	//CAMPI PARAMETRICI TIPO [-NOMETAG-parametro-] o [-NOMETAG(parametro)-]
	//campi dinamici DYN2_CAMPI ISTANZE_DYN_DATI
	if (placeholder.toUpperCase().startsWith("DYN-") || placeholder.toUpperCase().startsWith("DATODINAMICO(")) {
	    String codCampoStr = "";
	    if (placeholder.toUpperCase().startsWith("DYN-")) {
		codCampoStr = placeholder.substring(4);
	    } else {
		//DATODINAMICO
		codCampoStr = placeholder.substring(13, placeholder.length() - 1);
	    }
	    String val = "";
	    Integer codCampo = null;
	    try {
		codCampo = Integer.parseInt(codCampoStr);
		Dyn2Campi campo = data.getCampoDinamico(codCampo);
		if (campo != null) {
		    List<Istanzedyn2dati> datiDyn = data.getValoriCampoDinamico(codCampo);
		    val = buildValoreCampoDinamico(datiDyn);
		} else {
		    val = getValueForUnexistentDynamicField(codCampoStr);
		}
	    } catch (NumberFormatException e) {
		val = getValueForUnexistentDynamicField(codCampoStr);
	    }
	    return val;
	}
	//checkbox di istanza.codiceinteventoproc 
	if (placeholder.toUpperCase().startsWith("CHECKINTERVENTO")) { //FIXME ??????????????????
	    String codCampoStr = placeholder.substring(15).trim();
	    String val = "";
	    Integer codAlberoproc = null;
	    try {
		codAlberoproc = Integer.parseInt(codCampoStr);
		Alberoproc alberoprocInst = data.getIstanza().getAlberoproc();
		Alberoproc alberoprocPh = data.getAlberoproc(codAlberoproc);
		if (alberoprocPh != null) {
		    if (alberoprocPh.getId().getCodice().equals(alberoprocInst.getId().getCodice())) {
			val = CheckboxUtils.getCheckedImg();
		    } else {
			val = CheckboxUtils.getUncheckedImg();
		    }
		} else {
		    val = getValueForUnexistentAlberoproc(codCampoStr);
		}
	    } catch (NumberFormatException e) {
		val = getValueForUnexistentAlberoproc(codCampoStr);
	    }
	    return val;
	} //
	/*
	 * nomi dei cointestatari filtrati per tipo soggetto e tiporuolo [-COINTESTATARINOMINATIVO(tipoRuolo,codTipoSoggetto)-]. tipoRuolo in(R,T,A)
	 */
	if (placeholder.toUpperCase().startsWith("COINTESTATARINOMINATIVO(")) {
	    StringBuilder sb = new StringBuilder();
	    List<DatiRichiedente> drList = parseCointestatariArguments(struttura.getArgomenti(), data);
	    for (DatiRichiedente dr : drList) {
		sb.append(dr.buildCointestatarioString(true)).append(", ");
	    }
	    if (sb.length() > 1) {
		sb.delete(sb.length() - 2, sb.length());
	    }
	    return sb.toString();
	}
	/*
	 * cointestatari filtrati per tipo soggetto e tiporuolo [-COINTESTATARIACAPO(tipoRuolo,codTipoSoggetto)-]. tipoRuolo in(R,T,A)
	 * titolo + nominativo + indirizzo
	 */
	if (placeholder.toUpperCase().startsWith("COINTESTATARIACAPO(")) {
	    StringBuilder sb = new StringBuilder();
	    List<DatiRichiedente> drList = parseCointestatariArguments(struttura.getArgomenti(), data);
	    for (DatiRichiedente dr : drList) {
		sb.append(dr.buildCointestatarioEstesoString(true, false, true, "", false, false, RtfConstants.RTF_CRLF))
			.append(RtfConstants.RTF_CRLF);
	    }
	    if (sb.length() > 1) {
		sb.delete(sb.length() - 2, sb.length());
	    }
	    return sb.toString();
	}
	/*
	 * cointestatari filtrati per tipo soggetto e tiporuolo [-COINTESTATARIACAPOBREVE(tipoRuolo,codTipoSoggetto)-]. tipoRuolo in(R,T,A)
	 *  + nominativo + tipo soggetto + cf\pi
	 */
	if (placeholder.toUpperCase().startsWith("COINTESTATARIACAPOBREVE(")) {
	    StringBuilder sb = new StringBuilder();
	    List<DatiRichiedente> drList = parseCointestatariArguments(struttura.getArgomenti(), data);
	    for (DatiRichiedente dr : drList) {
		sb.append(dr.buildCointestatarioEstesoString(true, true, false, "", false, true, RtfConstants.RTF_CRLF))
			.append(RtfConstants.RTF_CRLF);
	    }
	    if (sb.length() > 1) {
		sb.delete(sb.length() - 2, sb.length());
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * nome 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_NOME(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(12, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getNome()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * nominativo
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_COGNOME(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(15, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getNominativo()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * codicefiscale
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_CODICEFISCALE(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(21, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getCodicefiscale()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto 
	 * partitaiva
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_PARTITAIVA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(18, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getPartitaiva()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetti collegati per tipo soggetto. 
	 * datanascita
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_DATANASCITA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(19, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.dateFormat(ana.getDatanascita()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * counenascita.comune + " " + counenascita.siglaprovincia
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_LUOGONASCITA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			Comuni comNas = ana.getComuneNascita();
			String nomeComNas = comNas != null ? comNas.getComune() + " " : "";
			nomeComNas += comNas != null ? "(" + comNas.getSiglaprovincia().toUpperCase() + ")" : "";
			sb.append(WordUtils.capitalizeFully(nomeComNas));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * counenascita.comune 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_COMUNENASCITA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(21, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			Comuni comNas = ana.getComuneNascita();
			String nomeComNas = comNas != null ? comNas.getComune() : "";
			sb.append(WordUtils.capitalizeFully(nomeComNas));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * provincia 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_PROVNASCITA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(19, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			Comuni comNas = ana.getComuneNascita();
			String provNas = comNas != null ? comNas.getProvincia() : "";
			sb.append(FormatUtils.stringFormat(provNas));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * indirizzo 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_RESINDIRIZZO(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getIndirizzo()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * cap 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_RESCAP(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(14, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getCap()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * citta 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_RESCITTA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(16, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getCitta()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * comuneresidenza.comune 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_RESCOMUNE(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(17, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			Comuni comRes = ana.getComuneResidenza();
			String nomeComRes = comRes != null ? comRes.getComune() : "";
			sb.append(FormatUtils.stringFormat(nomeComRes, true));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * provincia 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_RESPROVINCIA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getProvincia()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * sesso 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_SESSO(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(13, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			sb.append(FormatUtils.stringFormat(ana.getSesso()));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * cittadinanza.cittadinanza 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_CITTADINANZA(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			Cittadinanza cittDnz = ana.getCittadinanza();
			String descCttDnz = cittDnz != null ? cittDnz.getCittadinanza() : "";
			sb.append(FormatUtils.stringFormat(descCttDnz));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * anagrafe.email 
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_EMAIL(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(13, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			String email = StringUtils.defaultIfEmpty(ana.getEmail(), "");
			sb.append(FormatUtils.stringFormat(email));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	/*
	 * soggetto collegato per tipo soggetto. Viene preso l'ultimo ossia quello con codiceinvitato più alto
	 * anagrafe.pec
	 */
	if (placeholder.toUpperCase().startsWith("SOGCOL_EMAILPEC(")) {
	    StringBuilder sb = new StringBuilder();
	    String argument = placeholder.substring(16, placeholder.length() - 1);
	    try {
		Integer codTipoSogg = Integer.parseInt(argument);
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    Anagrafe ana = ir.getRichiedente();
		    if (ana != null) {
			String pec = StringUtils.defaultIfEmpty(ana.getPec(), "");
			sb.append(FormatUtils.stringFormat(pec));
			break;
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return sb.toString();
	}
	//movimento.id.codice del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_CODICE(")) {
	    String argument = placeholder.substring(11, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.integerFormat(mov.getId().getCodice());
	    }
	    return val;
	}
	//movimento.endoprocedimento.procedimento del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_ENDO(")) {
	    String argument = placeholder.substring(9, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && mov.getEndoprocedimento() != null) {
		val = FormatUtils.stringFormat(mov.getEndoprocedimento().getProcedimento());
	    }
	    return val;
	}
	//movimento.amministrazioni.amminnistrazione del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_AMMINISTRAZIONE(")) {
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && mov.getAmministrazioni() != null) {
		val = FormatUtils.stringFormat(mov.getAmministrazioni().getAmministrazione());
	    }
	    return val;
	}
	//movimento.amministrazioni.Pec indirizzo email di PEC del tipo passato come argomento
	if (placeholder.toUpperCase().startsWith("MOV_AMMINISTRAZIONE_PEC(")) {
	    String argument = placeholder.substring(24, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && mov.getAmministrazioni() != null) {
		if (mov.getAmministrazioni().getPec() != null) {
		    val = FormatUtils.stringFormat(mov.getAmministrazioni().getPec());
		}
	    }
	    return val;
	}
	//movimento.amministrazioni.Email indirizzo email del tipo passato come argomento
	if (placeholder.toUpperCase().startsWith("MOV_AMMINISTRAZIONE_EMAIL(")) {
	    String argument = placeholder.substring(26, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && mov.getAmministrazioni() != null) {
		if (mov.getAmministrazioni().getEmail() != null) {
		    val = FormatUtils.stringFormat(mov.getAmministrazioni().getEmail());
		}
	    }
	    return val;
	}
	//movimento.amministrazionireferenti.ufficio del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_UFFICIO(")) {
	    String argument = placeholder.substring(12, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && mov.getAmministrazionireferenti() != null) {
		val = FormatUtils.stringFormat(mov.getAmministrazionireferenti().getUfficio());
	    }
	    return val;
	}
	//movimento.data del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_DATA(")) {
	    String argument = placeholder.substring(9, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.dateFormat(mov.getData());
	    }
	    return val;
	}
	//movimento.numeroprotocollo del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_NRPROT(")) {
	    String argument = placeholder.substring(11, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.stringFormat(mov.getNumeroprotocollo());
	    }
	    return val;
	}
	//movimento.dataprotocollo del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_DATAPROT(")) {
	    String argument = placeholder.substring(13, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.dateFormat(mov.getDataprotocollo());
	    }
	    return val;
	}
	//movimento.esito del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_ESITO(")) {
	    String argument = placeholder.substring(10, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.booleanFormat(mov.getEsito(), "positivo", "negativo", "non previsto");
	    }
	    return val;
	}
	//movimento.parere del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_PARERE(")) {
	    String argument = placeholder.substring(11, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.stringFormat(mov.getParere());
	    }
	    return val;
	}
	//movimento.note del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_NOTE(")) {
	    String argument = placeholder.substring(9, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.stringFormat(mov.getNote());
	    }
	    return val;
	}
	//movimento.flagpubblica del movimento del tipo passato come argomento  
	if (placeholder.toUpperCase().startsWith("MOV_PUBBLICARE(")) {
	    String argument = placeholder.substring(15, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null) {
		val = FormatUtils.booleanFormat(mov.getPubblica(), "Si", "No", "");
	    }
	    return val;
	}
	//movimento.responsabile.responsabile   
	if (placeholder.toUpperCase().startsWith("MOV_OPERATORE(")) {
	    String argument = placeholder.substring(14, placeholder.length() - 1);
	    String val = "";
	    Movimenti mov = data.getMovimentoPerTipo(argument);
	    if (mov != null && EntityUtils.getNestedProperty(mov.getResponsabile(), "id.codice") != null) {
		val = FormatUtils.stringFormat(mov.getResponsabile().getResponsabile());
	    }
	    return val;
	}
	//attivita.istat corrispondente al codiceistat passato come argomento
	if (placeholder.toUpperCase().startsWith("DETTAGLIOINFORMAZIONE_DESCRIZIONE(")) {
	    String argument = placeholder.substring(34, placeholder.length() - 1);
	    String val = "";
	    Attivita at = attivitaService.findById(new AttivitaId(argument));
	    if (at != null) {
		val = at.getIstat();
	    }
	    return val;
	}
	//somma di istanzeattivita.metriq per le attività corrispondenti al codiceistat passato come argomento
	if (placeholder.toUpperCase().startsWith("DETTAGLIOINFORMAZIONE_MQ(")) {
	    String argument = placeholder.substring(25, placeholder.length() - 1);
	    String val = "";
	    List<SuperficiAttivitaHelper> attList = istanzeAttivitaService.getSommaSuperficiAttivitaPerAttivita(data.getIstanza(), argument);
	    if (attList.size() > 0) {
		SuperficiAttivitaHelper ia = attList.get(0);
		val = FormatUtils.decimalFormat(ia.getSuperficieTotale());
	    }
	    return val;
	}
	/*
	 * somma delle superfici delle istanzeattivita aggregate per attivita.istat e filtrate per il codicesettore passato come argomento
	 * (attivita.istat + attivita.settori.tipiunitamisura.umdescbreve + sum(istanzeattivita.metriq))
	 */
	if (placeholder.toUpperCase().startsWith("LISTADETTAGLIOINFORMAZIONE(")) {
	    String argument = placeholder.substring(27, placeholder.length() - 1);
	    List<SuperficiAttivitaHelper> attList = istanzeAttivitaService.getSommaSuperficiAttivitaPerSettore(data.getIstanza(), argument);
	    return buildSuperficiAttivitaString(attList, true, false);
	}
	/*
	 * lista delle istanzeattivita dell'istanza che appartengono al settore passato come argomento del tag.
	 * istanzeattivita.attivita.istat + istanzeattivita.attivita.settori.tipiunitamisura.umdescbreve + istanzeattivita.metriq + istanzeattivita.note
	 */
	if (placeholder.toUpperCase().startsWith("LISTADETTAGLIOINFORMAZIONENORAGGRUPPATA(")) { //FIXME trovare soluzione la TAB???????????
	    String argument = placeholder.substring(40, placeholder.length() - 1);
	    Settori sett = new Settori();
	    sett.setId(new SettoriId(argument));
	    List<Istanzeattivita> attList = istanzeAttivitaService.findByIstanzaAndSettore(data.getIstanza(), sett);
	    return buildSuperficiAttivitaStringConNote(attList);
	}
	/*
	 * lista delle note delle istanzeattivita dell'istanza che appartengono al settore passato come argomento del tag.
	 * istanzeattivita.note
	 */
	if (placeholder.toUpperCase().startsWith("LISTADETTAGLIOINFORMAZIONESOLONOTE(")) {
	    String argument = placeholder.substring(35, placeholder.length() - 1);
	    Settori sett = new Settori();
	    sett.setId(new SettoriId(argument));
	    List<Istanzeattivita> attList = istanzeAttivitaService.findByIstanzaAndSettore(data.getIstanza(), sett);
	    StringBuilder sb = new StringBuilder();
	    for (Istanzeattivita ia : attList) {
		sb.append(FormatUtils.stringFormat(ia.getNote())).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.autoriznumero
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZNUMERO(")) {
	    String argument = placeholder.substring(14, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.stringFormat(auth.getAutoriznumero());
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.autorizdata
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZDATA(")) {
	    String argument = placeholder.substring(12, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.dateFormat(auth.getAutorizdata());
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.autorizdata
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZDATASCADENZA(")) {
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    if (auth.getDatascadenza() != null) {
			val = FormatUtils.dateFormat(auth.getDatascadenza());
		    }
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.autorizresponsabile
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZRESPONSABILE(")) {
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.stringFormat(auth.getAutorizresponsabile());
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.autorizdataregistr
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZDATARESPONSABILE(")) {
	    String argument = placeholder.substring(24, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.dateFormat(auth.getAutorizdataregistr());
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	/*
	 * autorizzazioni dell'istanza per tipologia registro. Viene presa solo la più recente.
	 * autorizzazioni.tipologiaregistro.trdescrizione
	 */
	if (placeholder.toUpperCase().startsWith("AUTORIZREGISTRO(")) {
	    String argument = placeholder.substring(16, placeholder.length() - 1);
	    String val = "";
	    try {
		Integer codRegistro = Integer.parseInt(argument);
		List<Autorizzazioni> auths = autorizzazioniService.findByIstanzaRegistro(data.getIstanza(), codRegistro);
		Autorizzazioni auth = null;
		if (auths.size() > 0) {
		    auth = auths.get(0);
		    val = FormatUtils.stringFormat(auth.getTipologiaregistro().getTrDescrizione());
		}
	    } catch (NumberFormatException e) {
		log.error("getPlaceholderValueByIfElse() - errore nel parsing del segnaposto {}", placeholder);
	    }
	    return val;
	}
	///////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////NUOVI TAG ///////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////// RICHIEDENTE ///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	//istanza.richiedente.indirizzo
	if (placeholder.equalsIgnoreCase("INDIRIZZORES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getIndirizzoResidenza(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.localita
	if (placeholder.equalsIgnoreCase("CITTARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCittaResidenza(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.cap
	if (placeholder.equalsIgnoreCase("CAPRES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCapResidenza(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.provincia
	if (placeholder.equalsIgnoreCase("PROVINCIARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getProvinciaResidenza(data.getIstanza().getRichiedente()));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("COMUNERES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getComuneResidenza(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.comuneNascita
	if (placeholder.equalsIgnoreCase("LUOGODINASCITA")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getComuneNascita(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.cittadinanza
	if (placeholder.equalsIgnoreCase("CITTADINANZA")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCittadinanza(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.sesso
	if (placeholder.equalsIgnoreCase("SESSO")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getSesso(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.email
	if (placeholder.equalsIgnoreCase("RIC_EMAIL")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getEmail(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.pec
	if (placeholder.equalsIgnoreCase("RIC_EMAILPEC")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPec(data.getIstanza().getRichiedente()));
	    return val;
	}
	//istanze.richiedente.password
	if (placeholder.equalsIgnoreCase("RIC_PASSWORD")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPassword(data.getIstanza().getRichiedente()));
	    return val;
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// DATI DELL' AZIENDA /////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	//istanza.titolarelegale.indirizzo
	if (placeholder.equalsIgnoreCase("CO_INDIRIZZORES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getIndirizzoResidenza(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.titolarelegale.localita
	if (placeholder.equalsIgnoreCase("CO_CITTARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCittaResidenza(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.titolarelegale.cap
	if (placeholder.equalsIgnoreCase("CO_CAPRES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCapResidenza(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.titolarelegale.provicia
	if (placeholder.equalsIgnoreCase("CO_PROVINCIARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getProvinciaResidenza(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.titolarelegale.comune
	if (placeholder.equalsIgnoreCase("CO_COMUNERES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getComuneResidenza(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.richiedente.pec
	if (placeholder.equalsIgnoreCase("CO_EMAIL")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getEmail(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.richiedente.email
	if (placeholder.equalsIgnoreCase("CO_EMAILPEC")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPec(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//istanze.richiedente.email
	if (placeholder.equalsIgnoreCase("CO_CODICEANAGRAFE")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCodiceAnagrafe(data.getIstanza().getTitolarelegale()));
	    return val;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////// DATI TECNICO /////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//istanza.professionista.indirizzo
	if (placeholder.equalsIgnoreCase("TEC_INDIRIZZORES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getIndirizzoResidenza(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanze.professionista.localita
	if (placeholder.equalsIgnoreCase("TEC_CITTARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCittaResidenza(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanze.professionista.cap
	if (placeholder.equalsIgnoreCase("TEC_CAPRES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCapResidenza(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanze.professionista.provicia
	if (placeholder.equalsIgnoreCase("TEC_PROVINCIARES")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getProvinciaResidenza(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanze.professionista.email
	if (placeholder.equalsIgnoreCase("TEC_EMAIL")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getEmail(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanze.professionista.email
	if (placeholder.equalsIgnoreCase("TEC_EMAILPEC")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPec(data.getIstanza().getProfessionista()));
	    return val;
	}
	//istanza.richiedente.datanascita
	if (placeholder.equalsIgnoreCase("TEC_DATANASCITA")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getDataNascita(data.getIstanza().getProfessionista()));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("TEC_FORMAGIURIDICA")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getFormaGiuridica(data.getIstanza().getProfessionista()));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("TEC_ELENCOPROFESSIONALE")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getElencoprofessionale(data.getIstanza().getProfessionista()));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("TEC_PROVINCIAELENCO")) {
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPrElencoprofessionale(data.getIstanza().getProfessionista()));
	    return val;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////// DATI DELL'ISTANZA/////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//istanzemappali.catasto.descrizione
	if (placeholder.equalsIgnoreCase("TIPOCATASTO")) {
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getTipoCatasto(im));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("SEZIONE")) {
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getSezione(im));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("UNITAIMMOB")) {
	    Istanzemappali im = data.getIstanzaMappalePrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getUnitaImmob(im));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_SCALA")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getScala(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_PIANO")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getPiano(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_ESPONENTEINTERNO")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getEsponeneteInterno(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_NOTE")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getNote(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_INTERNO")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getinterno(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOCALIZZAZIONE_FABBRICATO")) {
	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getFabbricato(is));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LOC_ESTESA")) {
	    String val = "";
	    if (data.getIstanzaStradarioPrimaria() != null) {
		Istanzestradario istanzestradario = data.getIstanzaStradarioPrimaria();
		if (EntityUtils.getNestedProperty(istanzestradario.getStradario(), "id.codice") != null) {
		    Stradario str = istanzestradario.getStradario();
		    // Concateno prefisso e descrizione dello strdario se presenti
		    if (StringUtils.isNotBlank(str.getPrefisso())) {
			val = val.concat(str.getPrefisso()).concat(" ");
		    }
		    if (StringUtils.isNotBlank(str.getDescrizione())) {
			val = val.concat(str.getDescrizione()).concat(" ");
		    }
		    // se esiste il civico e concateno il civico
		    if (StringUtils.isNotBlank(istanzestradario.getCivico())) {
			val = val.concat(istanzestradario.getCivico());
		    }
		    // Controllo se istanze stradario ha popolato il campo esponente , se si lo concateno
		    if (StringUtils.isNotBlank(istanzestradario.getEsponente())) {
			val = val.concat("/").concat(istanzestradario.getEsponente());
		    }
		    val = val.concat(" ");
		    // Controllo se istanze stradario ha popolato il campo colore , se si lo concateno
		    if (EntityUtils.getNestedProperty(istanzestradario.getStradariocolore(), "id.codicecolore") != null) {
			//			String colore = "";
			//			// Controllo se nella stringa già esiste il carattere "/" se no allora oltre al colore concateno il carattere mancante
			//			colore = StringUtils.contains(val, "/") ? " " + istanzestradario.getStradariocolore().getColore()
			//				: "/ " + istanzestradario.getStradariocolore().getColore();
			val = val.concat(istanzestradario.getStradariocolore().getColore());
		    }
		    // Se instanze strdario ha popolato il campo Km allora lo uso
		    if (StringUtils.isNotBlank(istanzestradario.getKm())) {
			val = val.concat(", ");
			val = val.concat("Km ").concat(istanzestradario.getKm());
		    }
		}
	    }
	    return val;
	}
	//////////////////////////////////////////////
	//	if (placeholder.equalsIgnoreCase("NUMEROISTANZA_PREC")) {
	//	    List<Istanzecollegate> istanzecollegates = data.getIstanzeCollegate();
	//	    String val = "";
	//	    //String val = RtfUtilities.stringFormat(DocumentMergeUtils.getFabbricato(is));
	//	    return val;
	//	}
	//	if (placeholder.equalsIgnoreCase("NUMEROISTANZA_SUCC")) {
	////	    Istanzestradario is = data.getIstanzaStradarioPrimaria();
	////	    String val = RtfUtilities.stringFormat(DocumentMergeUtils.getFabbricato(is));
	//	    return "";
	//	}
	//ok
	//istanza.aree2.denominazione
	if (placeholder.equalsIgnoreCase("ZONAPSC")) {
	    Istanze ist = data.getIstanza();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getAree2(ist));
	    return val;
	}
	//ok
	if (placeholder.equalsIgnoreCase("CODICEISTANZAPEOPLE") || placeholder.equalsIgnoreCase("CPT")) {
	    Istanze ist = data.getIstanza();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getCodiceIstanzaPeople(ist));
	    return val;
	}
	//	 if (domandestcs.size() > 0) {
	//		for (Domandestc domandestc : domandestcs) {
	//		    istanzeCommand.setCodiceIstanzaOnline(domandestc.getIdDomandamitt());
	//		    break;
	//		}
	//	    }
	//	if (placeholder.equalsIgnoreCase("IDDOMANDAMITTENTE")) {
	//	    //	    Istanze ist = data.getIstanza();
	//	    //	    String val = RtfUtilities.stringFormat(DocumentMergeUtils.getIdDomanfa(ist));
	//	    //	    return val;
	//	}
	//	if (placeholder.equalsIgnoreCase("SC_PROTCLASSIFICA")) {
	//	    Istanze is=data.getIstanza().
	//	    String val = RtfUtilities.stringFormat(DocumentMergeUtils.getFabbricato(is));
	//	    return val;
	//	}
	if (placeholder.equalsIgnoreCase("LISTADOCNONRICHIESTIPRESENTICONDATA")) {
	    Set<Documentiistanza> docIstanza = data.getIstanza().getDocumentiistanzas();
	    String val = FormatUtils.stringFormat(DocumentMergeUtils.getListaDocumentiIstanzaNonRichiestiConData(docIstanza));
	    return val;
	}
	if (placeholder.equalsIgnoreCase("LISTADOCPROCURAPRESENTATA")) {
	    StringBuilder sb = new StringBuilder();
	    if (EntityUtils.getNestedProperty(data.getIstanza(), "id.codice") != null) {
		List<IstanzeprocureDTO> istanzeprocureDTOs = istanzeprocureService
			.findIstanzeprocureDTOByIstanza(data.getIstanza().getId().getCodice());
		for (IstanzeprocureDTO istanzeprocureDTO : istanzeprocureDTOs) {
		    if (StringUtils.isNotEmpty(istanzeprocureDTO.getNomeFile())) {
			sb.append(FormatUtils.stringFormat(istanzeprocureDTO.getNomeFile())).append(RtfConstants.RTF_CRLF);
		    }
		    if (StringUtils.isNotEmpty(istanzeprocureDTO.getNomeFileDocId())) {
			sb.append(FormatUtils.stringFormat(istanzeprocureDTO.getNomeFileDocId())).append(RtfConstants.RTF_CRLF);
		    }
		}
	    }
	    return sb.toString();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////// MERCATI /////// ////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Riporta tutte i numeri concessione associati all'istanza
	if (placeholder.equalsIgnoreCase("NUM_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = DocumentMergeUtils.getNumeroConcessione(auts);
	    return FormatUtils.stringFormat(val);
	}
	//	Segna posto DATA_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("DATA_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getDataConcessione(auts));
	    return val;
	}
	//	Segna posto TITOLARE non esistente
	if (placeholder.equalsIgnoreCase("TITOLARE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getTitolare(auts));
	    return val;
	}
	//	Segna posto TIPO_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("TIPO_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getTipoConcessione(auts));
	    return val;
	}
	//	Segna posto SCAD_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("SCAD_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getDataScadenzaConcessione(auts));
	    return val;
	}
	//	Segna posto STAG_DA_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("STAG_DA_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getStagionaleDaConcessione(auts));
	    return val;
	}
	//	Segna posto STAG_A_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("STAG_A_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getStagionaleAConcessione(auts));
	    return val;
	}
	//	Segna posto CAUS_CONCESSIONE non esistente
	if (placeholder.equalsIgnoreCase("CAUS_CONCESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getCausaleConcessioneAcqu(auts));
	    return val;
	}
	//	Segna posto MERCATO non esistente
	if (placeholder.equalsIgnoreCase("MERCATO")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getMercato(auts));
	    return val;
	}
	//	Segna posto GIORNO_MERCATO non esistente
	if (placeholder.equalsIgnoreCase("GIORNO_MERCATO")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getGiornoMercato(auts));
	    return val;
	}
	//	Segna posto NUM_POSTEGGIO non esistente
	if (placeholder.equalsIgnoreCase("NUM_POSTEGGIO")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getNumPosteggio(auts));
	    return val;
	}
	//	Segna posto MQ_POSTEGGIO non esistente
	if (placeholder.equalsIgnoreCase("MQ_POSTEGGIO")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getMqPosteggio(auts));
	    return val;
	}
	//	Segna posto PREC_AUT_NUM 
	if (placeholder.equalsIgnoreCase("PREC_AUT_NUM")) {
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(struttura, data, userData);
	    if (sostituzione != null) {
		return sostituzione.getValore();
	    }
	}
	//	Segna posto PREC_AUT_DATA 
	if (placeholder.equalsIgnoreCase("PREC_AUT_DATA")) {
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(struttura, data, userData);
	    if (sostituzione != null) {
		return sostituzione.getValore();
	    }
	}
	//	Segna posto PREC_RESPONSABILE 
	if (placeholder.equalsIgnoreCase("PREC_RESPONSABILE")) {
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(struttura, data, userData);
	    if (sostituzione != null) {
		return sostituzione.getValore();
	    }
	}
	//	Segna posto PREC_TITOLARE 
	if (placeholder.equalsIgnoreCase("PREC_TITOLARE")) {
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(struttura, data, userData);
	    if (sostituzione != null) {
		return sostituzione.getValore();
	    }
	}
	//	Segna posto MERCEOLOGIE 
	if (placeholder.equalsIgnoreCase("MERCEOLOGIE")) {
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(struttura, data, userData);
	    if (sostituzione != null) {
		return sostituzione.getValore();
	    }
	}
	//	Segna posto POSTEGGIO_VIA non esistente
	if (placeholder.equalsIgnoreCase("POSTEGGIO_VIA")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getPosteggioVia(auts));
	    return val;
	}
	//	Segna posto DATACESSIONE non esistente
	if (placeholder.equalsIgnoreCase("DATACESSIONE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getDataCessazione(auts));
	    return val;
	}
	//	Segna posto POSTEGGIO_NOTE non esistente
	if (placeholder.equalsIgnoreCase("POSTEGGIO_NOTE")) {
	    Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	    String val = "";
	    val = (DocumentMergeUtils.getPosteggioNote(auts));
	    return val;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////// AMMINISTRAZIONI /////// ////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//.[-AMMINISTRAZIONE(InsCod)-] --> riporta il nome dell'amministrazione con il codice
	if (placeholder.toUpperCase().startsWith("AMMINISTRAZIONE(")) {
	    String argument = placeholder.substring(16, placeholder.length() - 1);
	    Amministrazioni amministrazioni = null;
	    try {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(argument)));
	    } catch (NumberFormatException ne) {
		log.error("getPlaceholderValueByIfElse#La stringa {} non rappresenta un codice intero ", argument);
	    }
	    String val = "";
	    val = (DocumentMergeUtils.getAmministrazione(amministrazioni));
	    return val;
	}
	//.[-AMMINISTRAZIONE_PEC(InsCod)-] --> il campo PEC
	if (placeholder.toUpperCase().startsWith("AMMINISTRAZIONE_PEC(")) {
	    String argument = placeholder.substring(20, placeholder.length() - 1);
	    Amministrazioni amministrazioni = null;
	    try {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(argument)));
	    } catch (NumberFormatException ne) {
		log.error("getPlaceholderValueByIfElse#La stringa {} non rappresenta un codice intero ", argument);
	    }
	    String val = "";
	    val = (DocumentMergeUtils.getAmministrazionePec(amministrazioni));
	    return val;
	}
	//.[-AMMINISTRAZIONE_MAIL(InsCod)-] --> il campo MAIL
	if (placeholder.toUpperCase().startsWith("AMMINISTRAZIONE_MAIL(")) {
	    String argument = placeholder.substring(21, placeholder.length() - 1);
	    Amministrazioni amministrazioni = null;
	    try {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(argument)));
	    } catch (NumberFormatException ne) {
		log.error("getPlaceholderValueByIfElse#La stringa {} non rappresenta un codice intero ", argument);
	    }
	    String val = "";
	    val = (DocumentMergeUtils.getAmministrazioneMail(amministrazioni));
	    return val;
	}
	//.[-AMMINISTRAZIONE_PIVA(InsCod)-] --> riporta il CAMPO PARTITA IVA
	if (placeholder.toUpperCase().startsWith("AMMINISTRAZIONE_PIVA(")) {
	    String argument = placeholder.substring(21, placeholder.length() - 1);
	    Amministrazioni amministrazioni = null;
	    try {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(argument)));
	    } catch (NumberFormatException ne) {
		log.error("getPlaceholderValueByIfElse#La stringa {} non rappresenta un codice intero ", argument);
	    }
	    String val = "";
	    val = (DocumentMergeUtils.getAmministrazionePI(amministrazioni));
	    return val;
	}
	//.[-AMMINISTRAZIONE_REFERENTE(InsCod)-] --> riporta il CAMPO PARTITA IVA
	if (placeholder.toUpperCase().startsWith("AMMINISTRAZIONE_REFERENTE(")) {
	    String argument = placeholder.substring(26, placeholder.length() - 1);
	    Amministrazioni amministrazioni = null;
	    try {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(argument)));
	    } catch (NumberFormatException ne) {
		log.error("getPlaceholderValueByIfElse#La stringa {} non rappresenta un codice intero ", argument);
	    }
	    String val = "";
	    val = (DocumentMergeUtils.getAmministrazioneReferente(amministrazioni));
	    return val;
	}
	// SEZIONE DEDICATA ALLA CREAZIONE DI QRCOE
	//.[-QRCODE-VISURA-GUEST-]
	//.[-QRCODE-VISURA-PIN-]
	//.[-QRCODE-VISURA-AUTH-]
	if (placeholder.equalsIgnoreCase("QRCODE-VISURA-GUEST") || placeholder.equalsIgnoreCase("QRCODE-VISURA-PIN")
		|| placeholder.equalsIgnoreCase("QRCODE-VISURA-AUTH")) {
	    String val = "";
	    if (data.getIstanza() != null && data.getIstanza().getId() != null && data.getIstanza().getId().getCodice() != null) {
		try {
		    QrcodeHelper qrHelper = new QrcodeHelper();
		    TipoAuthQRcodeEnum authQRcodeEnum = DocumentMergeUtils.getAuthQRcode(placeholder);
		    qrHelper.setAuthQRcodeEnum(authQRcodeEnum);
		    QRCodeBean b = qrcodeService.visurapratica(qrHelper, data.getIstanza().getId().getCodice());
		    byte[] binaryData = b.getImage();
		    if (binaryData != null && binaryData.length > 0) {
			val = convertBytesToRtfImage(binaryData);
		    }
		} catch (IOException e) {
		    log.error("Errore nella sostituzione del segnaposto " + placeholder, e);
		}
		return val;
	    }
	}
	//.[-QRCODE-DOWNLOAD-GUEST-]
	//.[-QRCODE-DOWNLOAD-PIN-]
	//.[-QRCODE-DOWNLOAD-AUTH-]
	if (placeholder.equalsIgnoreCase("QRCODE-DOWNLOAD-GUEST") || placeholder.equalsIgnoreCase("QRCODE-DOWNLOAD-PIN")
		|| placeholder.equalsIgnoreCase("QRCODE-DOWNLOAD-AUTH")) {
	    String val = "";
	    if (data.getMovimento() != null && data.getMovimento().getId() != null && data.getMovimento().getId().getCodice() != null) {
		try {
		    QrcodeHelper qrHelper = new QrcodeHelper();
		    TipoAuthQRcodeEnum authQRcodeEnum = DocumentMergeUtils.getAuthQRcode(placeholder);
		    qrHelper.setAuthQRcodeEnum(authQRcodeEnum);
		    QRCodeBean b = qrcodeService.downloadDocumentiMovimento(qrHelper, data.getMovimento().getId().getCodice());
		    byte[] binaryData = b.getImage();
		    if (binaryData != null && binaryData.length > 0) {
			val = convertBytesToRtfImage(binaryData);
		    }
		} catch (IOException e) {
		    log.error("Errore nella sostituzione del segnaposto " + placeholder, e);
		}
	    }
	    return val;
	}
	//	if (placeholder.equalsIgnoreCase("LINKALLEGATI")) {
	//	    //TODO ad oggi non gestisto per la sostituzione in modelli rtf, deve ritornare un valore per il funzionamento in odt
	//	    return "Non LINKALLEGATI gestito";
	//	}
	//	if (placeholder.equalsIgnoreCase("LINKALLEGATI_NO_HYPERLINK")) {
	//	    Verticalizzazioniparametri vp = null;
	//	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	//		vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
	//			WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	//	    }
	//	    StringBuilder sb = new StringBuilder();
	//	    String uuid = userData.getUuidLinkTemp();
	//	    List<TempLinkallegati> list = new ArrayList<TempLinkallegati>();
	//	    if (StringUtils.isNotBlank(uuid)) {
	//		list = tempLinkallegatiService.findByUuid(uuid);
	//		for (TempLinkallegati tempLinkallegati : list) {
	//		    sb.append(tempLinkallegati.getNomedocumento()).append(" ").append(FormatUtils.stringFormat(tempLinkallegati.getLink()));
	//		    if (vp != null && !"S".equals(vp.getValore())) {
	//			sb = sb.append(" PIN=").append(tempLinkallegati.getPin());
	//		    }
	//		    sb = sb.append(RtfConstants.RTF_CRLF);
	//		}
	//		retVal = sb.toString();
	//	    } else {
	//		retVal = "Impossibile sostituire il segna posto " + placeholder + " uuid non passato";
	//	    }
	//	}
	return retVal;
    }

    private String getValueForUnexistentAlberoproc(String idAlberoProc) {

	return MessageFormat.format("[- l'intervento {0} non esiste-]", idAlberoProc);
    }

    private String getValueForUnsupportedPlaceholder(String placeholder) {

	return MessageFormat.format("[-{0} non supportato-]", placeholder);
    }

    /*
     * Riceve in input la porzione del segnaposto compresa fra le parentesi
     * Restituisce una lista di DatiRichiedente utilizzata poi per costruire la stringa da sostituire al segnaposto
     */
    private List<DatiRichiedente> parseCointestatariArguments(String[] arguments, IUsefulDataForPlaceholderReplacement data) {

	List<DatiRichiedente> retData = new ArrayList<DatiRichiedente>();
	if (arguments.length == 0) {
	    return retData;
	}
	for (int i = 0; i < arguments.length; i++) {
	    String argomento = arguments[i];
	    if (argomento.equals("R")) {
		retData.add(new DatiRichiedente(data.getIstanza().getRichiedente(), "Richiedente"));
		continue;
	    }
	    if (argomento.equals("T")) {
		Anagrafe a = data.getIstanza().getProfessionista();
		if (a != null) {
		    retData.add(new DatiRichiedente(a, "Tecnico"));
		}
		continue;
	    }
	    if (argomento.equals("A")) {
		Anagrafe a = data.getIstanza().getTitolarelegale();
		if (a != null) {
		    retData.add(new DatiRichiedente(a, "Titolare legale"));
		}
		continue;
	    }
	    try {
		Integer codTipoSogg = null;
		if (argomento.length() > 0) {
		    codTipoSogg = Integer.parseInt(argomento);
		}
		List<Istanzerichiedenti> irs = data.getIstanzeRichiedenti(codTipoSogg);
		for (Istanzerichiedenti ir : irs) {
		    retData.add(new DatiRichiedente(ir));
		}
	    } catch (NumberFormatException e) {
		log.error(
			"parseCointestatariArguments() - il valore {} non può essere interpretato come un codice tipo soggetto perchè non è numerico",
			new Object[] { argomento });
	    }
	}
	return retData;
    }

    private String buildDestinatariString(List<IndirizzoDestinatario> destinatari) {

	StringBuilder sb = new StringBuilder();
	for (IndirizzoDestinatario dest : destinatari) {
	    sb.append(dest.buildIndirizzo(RtfConstants.RTF_CRLF));
	    sb.append(RtfConstants.RTF_CRLF);
	}
	return sb.toString();
    }

    private StringBuilder buildAllegatiPresentatiString(Iterable<Istanzeallegati> allegati) {

	StringBuilder sb = new StringBuilder();
	for (Istanzeallegati all : allegati) {
	    if (all.getSelezionato()) {
		sb.append(all.getAllegatoextra()).append(RtfConstants.RTF_CRLF);
	    }
	}
	return sb;
    }

    private String getValueForUnexistentDynamicField(String idCampo) {

	return MessageFormat.format("[- il campo dinamico {0} non esiste-]", idCampo);
    }

    private String convertBytesToRtfImage(byte[] binaryData) throws IOException {

	StringBuilder rtfsb = new StringBuilder(binaryData.length * 2);
	rtfsb.append("{\\\\*\\\\shppict{\\\\pict\\\\jpegblip\n");
	rtfsb.append(convertBytesToHexStringForRtf(binaryData).toString());
	rtfsb.append("}}");
	return rtfsb.toString();
    }

    private StringBuilder convertBytesToHexStringForRtf(byte[] binaryData) throws IOException {

	ByteArrayInputStream bais = new ByteArrayInputStream(binaryData);
	byte[] buffer = new byte[64];
	StringBuilder sbHex = new StringBuilder();
	int bytesRead = 0;
	//int hexDigitsCount = 0;
	while ((bytesRead = bais.read(buffer)) > -1) {
	    String hexString = null;
	    for (int i = 0; i < bytesRead; i++) {
		hexString = Integer.toHexString(buffer[i]);
		if (hexString.length() > 2) {
		    hexString = hexString.substring(hexString.length() - 2, hexString.length());
		} else if (hexString.length() < 2) {
		    hexString = StringUtils.leftPad(hexString, 2, '0');
		}
		sbHex.append(hexString);
	    }
	    if (bytesRead == buffer.length) {
		sbHex.append("\n");
	    }
	}
	return sbHex;
    }

    private String buildSuperficiAttivitaString(List<SuperficiAttivitaHelper> datiSup, boolean scriviCodiceIstat, boolean scriviSettore) {

	StringBuilder sb = new StringBuilder();
	for (SuperficiAttivitaHelper sup : datiSup) {
	    if (scriviCodiceIstat) {
		sb.append(FormatUtils.stringFormat(sup.getAttivitaIstat())).append(RtfConstants.RTF_TAB);
	    }
	    if (scriviSettore) {
		sb.append(FormatUtils.stringFormat(sup.getSettore())).append(RtfConstants.RTF_TAB);
	    }
	    sb.append(FormatUtils.stringFormat(sup.getUnitaMisura())).append(RtfConstants.RTF_TAB);
	    sb.append(FormatUtils.decimalFormat(sup.getSuperficieTotale(), RtfConstants.MINIMUM_NUMBER_OF_DECIMAL_DIGITS))
		    .append(RtfConstants.RTF_CRLF);
	}
	return sb.toString();
    }

    private String buildSuperficiAttivitaStringConNote(List<Istanzeattivita> datiSup) {

	StringBuilder sb = new StringBuilder();
	Settori sector = null;
	for (Istanzeattivita sup : datiSup) {
	    sb.append(FormatUtils.stringFormat(sup.getAttivita().getIstat())).append(RtfConstants.RTF_TAB);
	    sector = sup.getAttivita().getSettori();
	    if (sector != null && sector.getTipiunitamisura() != null) {
		sb.append(FormatUtils.stringFormat(sector.getTipiunitamisura().getUmDescrbreve())).append(RtfConstants.RTF_TAB);
		sb.append(FormatUtils.decimalFormat(sup.getMetriq(), RtfConstants.MINIMUM_NUMBER_OF_DECIMAL_DIGITS)).append(RtfConstants.RTF_TAB);
	    }
	    sb.append(FormatUtils.stringFormat(sup.getNote())).append(RtfConstants.RTF_CRLF);
	}
	return sb.toString();
    }

    private String currencyFormat(BigDecimal amount) {

	StringBuilder sb = new StringBuilder();
	if (amount != null) {
	    sb.append("€ ").append(FormatUtils.decimalFormat(amount, 2, true));
	}
	return sb.toString();
    }

    private String buildValoreCampoDinamico(List<Istanzedyn2dati> datiDyn) {

	StringBuilder sb = new StringBuilder();
	for (Istanzedyn2dati dato : datiDyn) {
	    sb.append(dato.getValoredecodificato()).append(", ");
	}
	if (sb.length() > 1) {
	    sb.delete(sb.length() - 2, sb.length());
	}
	return sb.toString();
    }
}
