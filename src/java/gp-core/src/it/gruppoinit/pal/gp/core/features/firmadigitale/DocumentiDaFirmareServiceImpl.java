package it.gruppoinit.pal.gp.core.features.firmadigitale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoComunicazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.TipoComunicazionemovimentoEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author
 */
@Service
public class DocumentiDaFirmareServiceImpl extends BaseServiceImpl<DocumentiDaFirmare, PkId> implements DocumentiDaFirmareService {

    private static final Logger log = LoggerFactory.getLogger(DocumentiDaFirmareServiceImpl.class);
    private DocumentiDaFirmareDAO documentidafirmareDAO;
    private IstanzeService istanzeService;
    private IstanzeeventiService istanzeeventiService;
    private MovimentiallegatiService movimentiallegatiService;
    private OggettiService oggettiService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;
    private TipimovimentoComunicazioniService tipimovimentoComunicazioniService;
    @Autowired
    private IEventPublisher publisher;

    @Autowired
    public void setDocumentiDaFirmareDAO(DocumentiDaFirmareDAO documentidafirmareDAO) {

	this.documentidafirmareDAO = documentidafirmareDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setTipimovimentoComunicazioniService(TipimovimentoComunicazioniService tipimovimentoComunicazioniService) {

	this.tipimovimentoComunicazioniService = tipimovimentoComunicazioniService;
    }

    @Override
    protected Class<DocumentiDaFirmare> getEntityClass() {

	return DocumentiDaFirmare.class;
    }

    @Override
    public List<DocumentiDaFirmare> findAll(Integer firstResult, Integer maxResult) {

	return documentidafirmareDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DocumentiDaFirmare entity, Boolean isFirmatario) {

	dataIntegration(entity, isFirmatario);
	if (validateEntity(entity)) {
	    oggettiService.verificaConvertiPdf(entity.getOggetti());
	    documentidafirmareDAO.insert(entity);
	}
    }

    private void dataIntegration(DocumentiDaFirmare entity, Boolean isFirmatario) {

	if (entity.getDataRichiesta() == null) {
	    entity.setDataRichiesta(new Date());
	}
	if (StringUtils.isBlank(entity.getFlagDaFirmare())) {
	    entity.setFlagDaFirmare(StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name());
	}
	if (entity.getRichiedente() == null) {
	    Responsabili richiedente = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    entity.setRichiedente(richiedente);
	}
	if (entity.getFlagLetto() == null) {
	    entity.setFlagLetto(Boolean.FALSE);
	}
	if (Boolean.TRUE.equals(isFirmatario)) {
	    if (entity.getDataFirma() == null) {
		entity.setDataFirma(new Date());
	    }
	    if (entity.getFirmatario() == null) {
		Responsabili firmatario = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		entity.setFirmatario(firmatario);
	    }
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(DocumentiDaFirmare entity) {

	Oggetti doc = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(doc);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Movimentiallegati movimentiallegati = movimentiallegatiService.bindDomainObject(entity.getMovimentiallegati(), PkId.class, "id.codice");
	entity.setMovimentiallegati(movimentiallegati);
	Responsabili richiedente = responsabiliService.bindDomainObject(entity.getRichiedente(), PkId.class, "id.codice");
	entity.setRichiedente(richiedente);
	Responsabili firmatario = responsabiliService.bindDomainObject(entity.getFirmatario(), PkId.class, "id.codice");
	entity.setFirmatario(firmatario);
    }

    @Override
    public DocumentiDaFirmare findById(PkId id) {

	return documentidafirmareDAO.findById(id);
    }

    @Override
    public void update(DocumentiDaFirmare entity, Boolean isFirmatario) {

	dataIntegration(entity, isFirmatario);
	if (validateEntity(entity)) {
	    documentidafirmareDAO.update(entity);
	}
    }

    @Override
    public void delete(DocumentiDaFirmare entity) {

	if (isDeleteAllowed(entity)) {
	    documentidafirmareDAO.delete(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(DocumentiDaFirmare entity) {

	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return delete;
    }

    @Override
    public List<DocumentiDaFirmare> findDocumentiDaFirmare(Integer codiceresponsabile, int i, int maxResult) {

	return documentidafirmareDAO.findByResponsabile(codiceresponsabile);
    }

    @Override
    public List<DocumentiDaFirmare> findByIdOggetto(Integer codiceOggetto) {

	return documentidafirmareDAO.findByIdOggetto(codiceOggetto);
    }

    @Override
    public void insert(DocumentiDaFirmare entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(DocumentiDaFirmare entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<DocumentiDaFirmare> findByIdOggettoAndFirmatarioAndIstanza(Integer codiceOggetto, Integer codiceFirmatario, Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceoggettoId", codiceOggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("firmatarioId", codiceFirmatario, Integer.class));
	fr.addFilterField(FilterUtils.equals("codiceistanzaId", codiceIstanza, Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	return documentidafirmareDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Integer> findIdDocumentiDaFirmare(Integer codiceOggetto, Integer codiceFirmatario) {

	return this.documentidafirmareDAO.findIdDocumentiDaFirmare(codiceOggetto, codiceFirmatario);
    }

    @Override
    public boolean isFirmaCompleta(DocumentiDaFirmare doc) {

	return StringUtils.defaultString(doc.getFlagDaFirmare()).equalsIgnoreCase(StatiDocumentiDaFirmare.FIRMA_COMPLETA.name());
    }

    @Override
    public boolean isFirmaRichiesta(DocumentiDaFirmare doc) {

	return StringUtils.defaultString(doc.getFlagDaFirmare()).equalsIgnoreCase(StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name());
    }

    @Override
    public boolean isFirmaNegata(DocumentiDaFirmare doc) {

	return StringUtils.defaultString(doc.getFlagDaFirmare()).equalsIgnoreCase(StatiDocumentiDaFirmare.FIRMA_NEGATA.name());
    }

    @Override
    public int countDocumentiDafirmarePerOggetto(Integer codiceOggetto) {

	if (codiceOggetto == null) {
	    throw new RuntimeException("Non è stato specificato correttamente il parametro codiceoggetto");
	}
	return countDocumentiDafirmare(codiceOggetto, null);
    }

    @Override
    public int countDocumentiDafirmarePerOggettoIstanza(Integer codiceOggetto, Integer codiceIstanza) {

	if (codiceOggetto == null || codiceIstanza == null) {
	    throw new RuntimeException("non sono stati specificati correttamente i parametri codiceistanza o codiceoggetto");
	}
	return countDocumentiDafirmare(codiceOggetto, codiceIstanza);
    }

    private int countDocumentiDafirmare(Integer codiceOggetto, Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceOggetto != null) {
	    fr.addFilterField(FilterUtils.equals("codiceoggettoId", codiceOggetto, Integer.class));
	}
	if (codiceIstanza != null) {
	    fr.addFilterField(FilterUtils.equals("codiceistanzaId", codiceIstanza, Integer.class));
	}
	fr.addFilterField(FilterUtils.notEquals("flagDaFirmare", StatiDocumentiDaFirmare.FIRMA_COMPLETA.name(), String.class));
	filterTable.addRestriction(fr);
	return documentidafirmareDAO.countRecord(filterTable);
    }

    @Override
    public String findReportHTMLOggettoDaFirmare(Integer codiceOggetto) {

	return this.findReportOggettoDaFirmare(codiceOggetto, true);
    }

    private String findReportOggettoDaFirmare(Integer codiceOggetto, boolean isHTML) {

	String crlf = "\n";
	String boldStart = "";
	String boldEnd = "";
	String ulStart = "\n";
	String ulEnd = "";
	String liStart = "\t- ";
	String liEnd = "";
	String divStartSuccess = "";
	String divEndSuccess = "";
	String divStartError = "";
	String divEndError = "";
	if (isHTML) {
	    crlf = "<br />";
	    boldStart = "<b>";
	    boldEnd = "</b>";
	    ulStart = "<ul style=\"margin:0px;padding 2px;\">";
	    ulEnd = "</ul>";
	    liStart = "<li>";
	    liEnd = "</li>";
	    divStartSuccess = "<div style=\"background-color:#CDFAE0;margin: 2px;padding: 4px;\">";
	    divEndSuccess = "</div>";
	    divStartError = "<div style=\"background-color:#FAB9BA;margin: 2px;padding: 4px;\">";
	    divEndError = "</div>";
	}
	List<DocumentiDaFirmare> docs = findByIdOggetto(codiceOggetto);
	StringBuilder result = new StringBuilder("");
	if (docs.size() == 1) {
	    result.append("E' stata richiesta ");
	    result.append(boldStart + docs.size() + boldEnd + " firma per il documento." + crlf);
	} else {
	    result.append("Sono state richieste ");
	    result.append(boldStart + docs.size() + boldEnd + " firme per il documento." + crlf);
	}
	List<String> nonFirmati = new ArrayList<String>();
	List<String> firmaCompleta = new ArrayList<String>();
	List<String> firmaNegata = new ArrayList<String>();
	List<String> firmaConStatoAnomalo = new ArrayList<String>();
	for (DocumentiDaFirmare doc : docs) {
	    Responsabili r = doc.getFirmatario();
	    String descrizione = "Firmatario non presente per il record " + doc.getId().getCodice();
	    if (r != null) {
		descrizione = r.getResponsabile() + " (" + r.getId().getCodice() + ")";
	    }
	    if (isFirmaCompleta(doc)) {
		firmaCompleta.add(descrizione);
	    } else if (isFirmaRichiesta(doc)) {
		nonFirmati.add(descrizione);
	    } else if (isFirmaNegata(doc)) {
		firmaNegata.add(descrizione);
	    } else {
		firmaConStatoAnomalo.add(descrizione);
	    }
	}
	if (!firmaCompleta.isEmpty()) {
	    result.append(divStartSuccess + "I seguenti firmatari hanno apposto la firma:" + ulStart);
	    for (String f : firmaCompleta) {
		result.append(liStart + f + liEnd);
	    }
	    result.append(ulEnd + divEndSuccess);
	}
	if (!nonFirmati.isEmpty()) {
	    result.append(divStartError + "I seguenti firmatari " + boldStart + "NON" + boldEnd + " hanno apposto la firma:" + ulStart);
	    for (String f : nonFirmati) {
		result.append(liStart + f + liEnd);
	    }
	    result.append(ulEnd + divEndError);
	}
	if (!firmaNegata.isEmpty()) {
	    result.append(divStartError + "I seguenti firmatari hanno " + boldStart + "NEGATO" + boldEnd + " la firma:" + ulStart);
	    for (String f : firmaNegata) {
		result.append(liStart + f + liEnd);
	    }
	    result.append(ulEnd + divEndError);
	}
	if (!firmaConStatoAnomalo.isEmpty()) {
	    result.append(divStartError + "Le seguenti firme si trovano in un stato anomalo(contattare l'assistenza):" + ulStart);
	    for (String f : firmaConStatoAnomalo) {
		result.append(liStart + f + liEnd);
	    }
	    result.append(ulEnd + divEndError);
	}
	return result.toString();
    }

    @Override
    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerFirmatario(Integer codiceresponsabile, Integer firstResult, Integer maxResult) {

	return documentidafirmareDAO.findDocumentiDaFirmareDTOPerFirmatario(codiceresponsabile, firstResult, maxResult);
    }

    @Override
    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare, Integer firstResult, Integer maxResult) {

	return documentidafirmareDAO.findDocumentiDaFirmareDTOPerRichiedenteAndStato(codiceresponsabile, isMessiDallutenteLoggato, flagDaFirmare,
		firstResult, maxResult);
    }

    @Override
    public Integer countDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare) {

	return documentidafirmareDAO.countDocumentiDaFirmareDTOPerRichiedenteAndStato(codiceresponsabile, isMessiDallutenteLoggato, flagDaFirmare);
    }

    @Override
    public int countDocumentiDaFirmarePerFirmatario(Integer codiceresponsabile) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    FilterRestriction software = new FilterRestriction();
	    software.setAndOrRestriction(AndOrRestriction.OR);
	    software.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "istanze", String.class));
	    software.addFilterField(FilterUtils.isNull("software.codice", "istanze"));
	    ft.addRestriction(software);
	}
	fr.addFilterField(FilterUtils.equals("flagDaFirmare", StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name(), String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceresponsabile, "firmatario", Integer.class));
	ft.addRestriction(fr);
	return documentidafirmareDAO.countRecord(ft);
    }

    @Override
    public int countDocumentiDaFirmarePerRichiedenteAndStato(Integer codiceresponsabile, String flagDaFirmare) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    FilterRestriction software = new FilterRestriction();
	    software.setAndOrRestriction(AndOrRestriction.OR);
	    software.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "istanze", String.class));
	    software.addFilterField(FilterUtils.isNull("software.codice", "istanze"));
	    ft.addRestriction(software);
	}
	if (StringUtils.isNotBlank(flagDaFirmare)) {
	    fr.addFilterField(FilterUtils.equals("flagDaFirmare", flagDaFirmare, String.class));
	}
	fr.addFilterField(FilterUtils.equals("id.codice", codiceresponsabile, "richiedente", Integer.class));
	ft.addRestriction(fr);
	return documentidafirmareDAO.countRecord(ft);
    }

    @Override
    public int countDocumentiPerMovimentiAllegati(Integer codiceMovimentoAllegato, String stato) {

	if (StringUtils.isNotBlank(stato)) {
	    if (!(stato.equals(StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()) || stato.equals(StatiDocumentiDaFirmare.FIRMA_NEGATA.name())
		    || stato.equals(StatiDocumentiDaFirmare.FIRMA_COMPLETA.name()))) {
		throw new IllegalArgumentException("Stato richiesta non censito: [" + stato + "]");
	    }
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(stato)) {
	    fr.addFilterField(FilterUtils.equals("flagDaFirmare", stato, String.class));
	}
	fr.addFilterField(FilterUtils.equals("movimentiallegatiId", codiceMovimentoAllegato, Integer.class));
	ft.addRestriction(fr);
	return documentidafirmareDAO.countRecord(ft);
    }

    @Override
    public void updateMutiplo(String flagDaFirmare, String annotazioniFirmatario, List<Integer> idDocDaFirmare) {

	for (Integer cod : idDocDaFirmare) {
	    DocumentiDaFirmare doc = this.findById(new PkId(cod));
	    updateSingolo(doc, flagDaFirmare, annotazioniFirmatario);
	}
    }

    @Override
    public void updateSingolo(DocumentiDaFirmare doc, String flagDaFirmare, String annotazioniFirmatario) {

	doc.setFlagDaFirmare(flagDaFirmare);
	doc.setAnnotazioniFirmatario(annotazioniFirmatario);
	doc.setDataFirma(Calendar.getInstance().getTime());
	this.update(doc, Boolean.TRUE);
	// Inserisco l'evento di firma
	if (doc.getMovimentiallegati() != null) {
	    istanzeeventiService.insertEventoDiFirmaSuDocumento(doc);
	    //GIANPAOLO
	    List<Movimentiallegati> listAllegati = new ArrayList<Movimentiallegati>();
	    listAllegati.add(doc.getMovimentiallegati());
	    tipimovimentoComunicazioniService.eseguiComunicazione(doc.getMovimentiallegati().getMovimento(), listAllegati, true,
		    TipoComunicazionemovimentoEnum.FIRMA_DOC);
	}
	publisher.publish(new EventoDocumentoDaFirmareModificato(doc.getId().getCodice()));
    }

    @Override
    public List<DocumentiDaFirmare> findByMovimentiallegati(Integer codiceMovimentiallegati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentiallegatiId", codiceMovimentiallegati, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return documentidafirmareDAO.findByFilterTable(ft);
    }

    @Override
    public List<DocumentiDaFirmare> findByMovimentiallegatiDaFirmare(Integer codiceMovimentiallegati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentiallegatiId", codiceMovimentiallegati, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagDaFirmare", StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRichiesta"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return documentidafirmareDAO.findByFilterTable(ft);
    }

    @Override
    public void insertTrasformaInPdfDocumentiMessiAllaFirma(Integer codiceOggetto) {

	log.debug("insertTrasformaInPdf# Converto il file passato in pdf");
	Oggetti oggettoPdf = oggettiService.convertFileInPdf(codiceOggetto);
	log.debug("insertTrasformaInPdf# Recupero i documenti messi alla firma con codice oggetto: {}", codiceOggetto);
	List<DocumentiDaFirmare> documentiDaFirmares = this.findByIdOggetto(codiceOggetto);
	log.debug("insertTrasformaInPdf# Per ogni singolo record vado ad aggiornare l'oggetto associato sovrascrivendolo con quello pdf creato");
	for (DocumentiDaFirmare documentiDaFirmare : documentiDaFirmares) {
	    Oggetti oggetti = documentiDaFirmare.getOggetti();
	    oggetti.setDimensioneFile(oggettoPdf.getOggetto().length);
	    oggetti.setNomefile(oggettoPdf.getNomefile());
	    oggetti.setOggetto(oggettoPdf.getOggetto());
	    oggettiService.update(oggetti);
	}
    }

    @Override
    public DocumentiDaFirmare updateSegnaComeFirmato(Integer codiceDocumento) {

	DocumentiDaFirmare documentiDaFirmare = this.findById(new PkId(codiceDocumento));
	this.updateSingolo(documentiDaFirmare, StatiDocumentiDaFirmare.FIRMA_COMPLETA.name(), documentiDaFirmare.getAnnotazioniFirmatario());
	return documentiDaFirmare;
    }

    @Override
    public Integer insertMultipli(String[] codMovAll, Responsabili firmatario, String annotazioniRichiedente) {

	Integer codMov = null;
	DocumentiDaFirmare daFirmare = null;
	for (int i = 0; i < codMovAll.length; i++) {
	    Responsabili responsabili = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    Movimentiallegati movimentiallegati = movimentiallegatiService.findById(new PkId(Integer.parseInt(codMovAll[i])));
	    if (codMov == null) {
		codMov = movimentiallegati.getMovimento().getId().getCodice();
	    }
	    if (EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") != null) {
		daFirmare = new DocumentiDaFirmare();
		daFirmare.setAnnotazioniRichiedente(StringUtils.defaultIfEmpty(annotazioniRichiedente, ""));
		if (EntityUtils.getNestedProperty(firmatario, "id.codice") != null) {
		    daFirmare.setFirmatario(firmatario);
		}
		daFirmare.setMovimentiallegati(movimentiallegati);
		daFirmare.setOggetti(movimentiallegati.getOggetto());
		daFirmare.setRichiedente(responsabili);
		daFirmare.setDataRichiesta(new Date());
		daFirmare.setIstanze(movimentiallegati.getMovimento().getIstanza());
		this.insert(daFirmare, Boolean.FALSE);
	    }
	}
	return codMov;
    }

    @Override
    public void clear() {

	documentidafirmareDAO.clear();
    }
}
