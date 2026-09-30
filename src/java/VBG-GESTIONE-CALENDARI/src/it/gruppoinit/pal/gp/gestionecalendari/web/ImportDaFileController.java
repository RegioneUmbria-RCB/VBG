package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.domain.FiereMostre;
import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.FesteSagreService;
import it.gruppoinit.pal.gp.core.service.FiereMostreService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.ComuneType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.FesteSagreType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.FiereMostreType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.ListaCalendari;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.MerceologiaEnumType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.MerceologiaType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.OrganizzatoreType;
import it.gruppoinit.pal.gp.gestionecalendari.schema.types.PeriodoType;
import it.gruppoinit.pal.gp.gestionecalendari.utils.Utils;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FileUploadCommand;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImportDaFileController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ImportDaFileController.class);
    @Autowired
    private FiereMostreService fiereMostreService;
    @Autowired
    private FesteSagreService festeSagreService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;

    @RequestMapping
    public String start(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("start");
	return "importdafile/form";
    }

    @RequestMapping
    public String upload(Model model, @ModelAttribute("fileUploadCommand") FileUploadCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	log.info("upload");
	MultipartFile multipartFile = command.getFile();
	if (multipartFile != null) {
	    if (command.getFile().getSize() == 0) {
		result.rejectValue("file", "validation.required.fileUpload");
		log.info("upload: file non specificato");
		return "importdafile/form";
	    }
	    String fileName = multipartFile.getOriginalFilename();
	    log.info("upload: fileName={}", fileName);
	    try {
		ListaCalendari lista = unmarshall(multipartFile.getInputStream());
		insertCalendari(lista, getComuniAttiviResponsabile());
	    } catch (Exception e) {
		String msg = e.getMessage() == null ? "" : e.getMessage();
		String msgCause = "";
		if (e.getCause() != null) {
		    msgCause = e.getCause().getMessage() == null ? "" : e.getCause().getMessage();
		}
		result.rejectValue("file", "error.fileUpload", new Object[] { msg, msgCause }, msg);
		log.error("upload", e);
		return "importdafile/form";
	    }
	} else {
	    result.rejectValue("file", "validation.required.fileUpload");
	    log.info("upload: file non specificato");
	    return "importdafile/form";
	}
	return "redirect:start.htm?ret=0";
    }

    private ListaCalendari unmarshall(InputStream is) throws Exception {

	try {
	    JAXBContext jc = JAXBContext.newInstance(ListaCalendari.class);
	    Unmarshaller m = jc.createUnmarshaller();
	    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = schemaFactory.newSchema(this.getClass().getClassLoader().getResource("tracciato.xsd"));
	    m.setSchema(schema);
	    return (ListaCalendari) m.unmarshal(is);
	} catch (Exception e) {
	    throw e;
	}
    }

    private void insertCalendari(ListaCalendari lista, List<String> codiceCatastaleList) {

	for (FiereMostreType fmt : lista.getListaFiereMostre()) {
	    validateFiereMostre(fmt);
	    FiereMostre fm = new FiereMostre();
	    List<FiereMostrePeriodi> listPeriodi = new ArrayList<FiereMostrePeriodi>();
	    List<FiereMostreMerceologie> listMerceologie = new ArrayList<FiereMostreMerceologie>();
	    // populate entity
	    fm.setComune(getComune(fmt.getComuneSvolgimento(), codiceCatastaleList));
	    fm.setComuneSvolgimento(getComune(fmt.getComuneSvolgimento(), codiceCatastaleList));
	    if (fmt.getDataAutorizzazione() != null) {
		fm.setDataAutorizzazione(Utilities.getDate(fmt.getDataAutorizzazione()));
	    }
	    if (fmt.getDataIstanza() != null) {
		fm.setDataIstanza(Utilities.getDate(fmt.getDataIstanza()));
	    }
	    fm.setDenominazione(fmt.getDenominazione());
	    fm.setIdIstanza(fmt.getIdIstanza());
	    fm.setLuogoSvolgimento(fmt.getLuogoSvolgimento());
	    fm.setNumAutorizzazione(fmt.getNumAutorizzazione());
	    fm.setNumeroIstanza(fmt.getNumeroIstanza());
	    fm.setNumProtocolloIstanza(fmt.getNumProtocolloIstanza());
	    fm.setOrganizzatore(formatOrganizzatore(fmt.getOrganizzatore()));
	    for (PeriodoType pt : fmt.getPeriodi()) {
		FiereMostrePeriodi fmp = new FiereMostrePeriodi();
		fmp.setDal(Utilities.getDate(pt.getDal()));
		fmp.setAl(Utilities.getDate(pt.getAl()));
		listPeriodi.add(fmp);
	    }
	    for (MerceologiaType merceologiaType : fmt.getMerceologie()) {
		FiereMostreMerceologie fmm = new FiereMostreMerceologie();
		fmm.setMerceologia(decodeMerceologia(merceologiaType));
		listMerceologie.add(fmm);
	    }
	    fm.setClassificazione(fmt.getClassificazione().value());
	    fiereMostreService.insert(fm, listPeriodi, listMerceologie);
	}
	for (FesteSagreType fst : lista.getListaFesteSagre()) {
	    validateFesteSagre(fst);
	    FesteSagre fs = new FesteSagre();
	    // populate entity
	    fs.setAl(Utilities.getDate(fst.getPeriodo().getAl()));
	    fs.setComune(getComune(fst.getComuneSvolgimento(), codiceCatastaleList));
	    fs.setComuneSvolgimento(getComune(fst.getComuneSvolgimento(), codiceCatastaleList));
	    fs.setDal(Utilities.getDate(fst.getPeriodo().getDal()));
	    if (fst.getDataAutorizzazione() != null) {
		fs.setDataAutorizzazione(Utilities.getDate(fst.getDataAutorizzazione()));
	    }
	    if (fst.getDataIstanza() != null) {
		fs.setDataIstanza(Utilities.getDate(fst.getDataIstanza()));
	    }
	    fs.setDenominazione(fst.getDenominazione());
	    fs.setIdIstanza(fst.getIdIstanza());
	    fs.setLuogoSvolgimento(fst.getLuogoSvolgimento());
	    fs.setNumAutorizzazione(fst.getNumAutorizzazione());
	    fs.setNumeroIstanza(fst.getNumeroIstanza());
	    fs.setNumProtocolloIstanza(fst.getNumProtocolloIstanza());
	    fs.setOrganizzatore(formatOrganizzatore(fst.getOrganizzatore()));
	    fs.setTipologia(fst.getTipologia().value());
	    festeSagreService.insert(fs);
	}
    }

    private Comuni getComune(ComuneType ct, List<String> codiceCatastaleList) {

	Comuni comune = new Comuni();
	boolean isComunePermesso = false;
	for (String codiceCatastale : codiceCatastaleList) {
	    if (codiceCatastale.equals(ct.getCodiceCatastale())) {
		isComunePermesso = true;
		break;
	    }
	}
	if (!isComunePermesso) {
	    log.error("getComune: il codicecatastale='{}' non è presente nella lista dei comuni attivi per l'operatore", ct.getCodiceCatastale());
	    throw new RuntimeException(getMessageFromBundle("error.fileUpload.codicecatastale-errato"));
	}
	comune.setCodicecomune(ct.getCodiceCatastale());
	comune = comuniService.findByCodiceComune(comune);
	return comune;
    }

    private List<String> getComuniAttiviResponsabile() {

	Responsabili loggedUser = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> respComList = responsabilicomuniService.findByResponsabile(loggedUser);
	List<String> comList = new ArrayList<String>();
	for (Responsabilicomuni responsabilicomuni : respComList) {
	    String codiceCatastale = responsabilicomuni.getComune().getCodicecomune();
	    comList.add(codiceCatastale);
	}
	return comList;
    }

    private String formatOrganizzatore(OrganizzatoreType o) {

	return Utils.formatOrganizzatore(o.getCognome(), o.getNome(), o.getCf(), o.getPIva());
    }

    private void validateFiereMostre(FiereMostreType fmt) {

	String co = "Campo obbligatorio: ";
	if (StringUtils.isEmpty(fmt.getDenominazione())) {
	    throw new RuntimeException(co + "denominazione");
	}
	if (fmt.getComuneSvolgimento() == null || StringUtils.isEmpty(fmt.getComuneSvolgimento().getCodiceCatastale())) {
	    throw new RuntimeException(co + "comuneSvolgimento codiceCatastale");
	}
	if (StringUtils.isEmpty(fmt.getLuogoSvolgimento())) {
	    throw new RuntimeException(co + "luogoSvolgimento");
	}
	if (fmt.getOrganizzatore() == null || StringUtils.isEmpty(fmt.getOrganizzatore().getNome())) {
	    throw new RuntimeException(co + "organizzatore nome");
	}
	if (fmt.getClassificazione() == null || StringUtils.isEmpty(fmt.getClassificazione().value())) {
	    throw new RuntimeException(co + "classificazione");
	}
	if (fmt.getMerceologie() == null) {
	    throw new RuntimeException(co + "merceologie");
	} else {
	    if (fmt.getMerceologie().isEmpty()) {
		throw new RuntimeException(co + "merceologie");
	    } else {
		for (MerceologiaType mt : fmt.getMerceologie()) {
		    if (mt.getMerceologia() == null || StringUtils.isEmpty(mt.getMerceologia().value())) {
			throw new RuntimeException(co + "merceologie");
		    } else if (mt.getMerceologia().value().equals(MerceologiaEnumType.N_28_ALTRO.value())) {
			if (StringUtils.isBlank(mt.getMerceologiaAltro())) {
			    throw new RuntimeException(co + "merceologia altro");
			}
		    }
		}
	    }
	}
	if (fmt.getPeriodi() == null || fmt.getPeriodi().isEmpty()) {
	    throw new RuntimeException(co + "periodi");
	} else {
	    for (PeriodoType p : fmt.getPeriodi()) {
		if (p.getDal() == null) {
		    throw new RuntimeException(co + "periodi dal");
		}
		if (p.getAl() == null) {
		    throw new RuntimeException(co + "periodi al");
		}
	    }
	}
    }

    private void validateFesteSagre(FesteSagreType fst) {

	String co = "Campo obbligatorio: ";
	if (StringUtils.isEmpty(fst.getDenominazione())) {
	    throw new RuntimeException(co + "denominazione");
	}
	if (fst.getComuneSvolgimento() == null || StringUtils.isEmpty(fst.getComuneSvolgimento().getCodiceCatastale())) {
	    throw new RuntimeException(co + "comuneSvolgimento codiceCatastale");
	}
	if (StringUtils.isEmpty(fst.getLuogoSvolgimento())) {
	    throw new RuntimeException(co + "luogoSvolgimento");
	}
	if (fst.getOrganizzatore() == null || StringUtils.isEmpty(fst.getOrganizzatore().getNome())) {
	    throw new RuntimeException(co + "organizzatore nome");
	}
	if (fst.getTipologia() == null || StringUtils.isEmpty(fst.getTipologia().value())) {
	    throw new RuntimeException(co + "tipologia");
	}
	if (fst.getPeriodo() == null) {
	    throw new RuntimeException(co + "periodo");
	} else {
	    if (fst.getPeriodo().getDal() == null) {
		throw new RuntimeException(co + "periodo dal");
	    }
	    if (fst.getPeriodo().getAl() == null) {
		throw new RuntimeException(co + "periodo al");
	    }
	}
    }

    private String decodeMerceologia(MerceologiaType m) {

	String _m = "";
	switch (m.getMerceologia()) {
	case N_01_AGRICOLTURA_SILVICOLTURA_ZOOTECNIA:
	    _m = "(01) Agricoltura,Silvicoltura,Zootecnia";
	    break;
	case N_02_FOOD_BEVANDE_OSPITALITA:
	    _m = "(02) Food, Bevande, Ospitalità";
	    break;
	case N_03_SPORT_HOBBY_INTRATTENIMENTO_ARTE:
	    _m = "(03) Sport, Hobby, Intrattenimento, Arte";
	    break;
	case N_04_SERVIZI_BUSINESS_COMMERCIO:
	    _m = "(04) Servizi Business, Commercio";
	    break;
	case N_05_COSTRUZIONI_INFRASTRUTTURE:
	    _m = "(05) Costruzioni, Infrastrutture";
	    break;
	case N_06_VIAGGI_TRASPORTI:
	    _m = "(06) Viaggi, trasporti";
	    break;
	case N_07_SICUREZZA_ANTINCENDIO_DIFESA:
	    _m = "(07) Sicurezza, Antincendio, Difesa";
	    break;
	case N_08_FORMAZIONE_EDUCAZIONE:
	    _m = "(08) Formazione, Educazione";
	    break;
	case N_09_ENERGIA_COMBUSTIBILI_GAS:
	    _m = "(09) Energia, Combustibili, Gas";
	    break;
	case N_10_PROTEZIONE_DELL_AMBIENTE:
	    _m = "(10) Protezione dell'ambiente";
	    break;
	case N_11_STAMPA_PACKAGING_IMBALLAGGI:
	    _m = "(11) Stampa, Packaging, Imballaggi";
	    break;
	case N_12_ARREDAMENTO_DESIGN_D_INTERNI:
	    _m = "(12) Arredamento, Design d'interni";
	    break;
	case N_13_CASALINGHI_GIOCHI_REALISTICA:
	    _m = "(13) Casalinghi, giochi, realistica";
	    break;
	case N_14_BELLEZZA_COSMETICA:
	    _m = "(14) Bellezza, Cosmetica";
	    break;
	case N_15_REAL_ESTATE_IMMOBILIARE:
	    _m = "(15) Real Estate, Immobiliare";
	    break;
	case N_16_AUTOMOBILI_MOTOCICLI:
	    _m = "(16) Automobili, Motocicli";
	    break;
	case N_17_CHIMICA:
	    _m = "(17) Chimica";
	    break;
	case N_18_ELETTRONICA_COMPONENTI:
	    _m = "(18) Elettronica, Componenti";
	    break;
	case N_19_INDUSTRIA_TECNOLOGIA_MECCANICA:
	    _m = "(19) Industria, Tecnologia, Meccanica";
	    break;
	case N_20_AVIAZIONE_AEROSPAZIALE:
	    _m = "(20) Aviazione, Aerospaziale";
	    break;
	case N_21_IT_E_TELECOMUNICAZIONI:
	    _m = "(21) IT e Telecomunicazioni";
	    break;
	case N_22_SALUTE_ATTREZZATURE_OSPEDALIERE:
	    _m = "(22) Salute, Attrezzature Ospedaliere";
	    break;
	case N_23_OTTICA:
	    _m = "(23) Ottica";
	    break;
	case N_24_GIOIELLI_OROLOGI_ACCESSORI:
	    _m = "(24) Gioielli, Orologi, Accessori";
	    break;
	case N_25_TESSILE_ABBIGLIAMENTO_MODA:
	    _m = "(25) Tessile, Abbigliamento, Moda";
	    break;
	case N_26_TRASPORTI_LOGISTICA_NAVIGAZIONE:
	    _m = "(26) Trasporti, Logistica, Navigazione";
	    break;
	case N_27_CAMPIONARIE_GENERALI:
	    _m = "(27) Campionarie Generali";
	    break;
	case N_28_ALTRO:
	    _m = m.getMerceologiaAltro();
	    break;
	default:
	    break;
	}
	return _m;
    }
}
