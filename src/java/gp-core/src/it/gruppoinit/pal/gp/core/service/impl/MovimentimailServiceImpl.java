package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.exceptions.ValidationException;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MovimentimailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.MovimentimailallegatiService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.ZipLogicoLinkHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Service
public class MovimentimailServiceImpl extends BaseServiceImpl<Movimentimail, PkId> implements MovimentimailService {

    private static final Logger log = LoggerFactory.getLogger(MovimentimailServiceImpl.class);
    private MovimentiService movimentiService;
    private MovimentimailDAO movimentimailDAO;
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentimailallegatiService movimentimailallegatiService;
    private MailServiceWSClient mailServiceWSClient;
    private OggettiService oggettiService;
    private ContenttypesService contenttypesService;
    private LetteretipoService letteretipoService;
    private TempLinkallegatiService tempLinkallegatiService;
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentimailallegatiService(MovimentimailallegatiService movimentimailallegatiService) {

	this.movimentimailallegatiService = movimentimailallegatiService;
    }

    @Autowired
    public void setMovimentimailDAO(MovimentimailDAO movimentimailDAO) {

	this.movimentimailDAO = movimentimailDAO;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setTempLinkallegatiService(TempLinkallegatiService tempLinkallegatiService) {

	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected Class<Movimentimail> getEntityClass() {

	return Movimentimail.class;
    }

    @Override
    public List<Movimentimail> findAll(Integer firstResult, Integer maxResult) {

	return movimentimailDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Movimentimail entity) {

	boolean validated=true;
		
	//se non è bozza faccio la validazione
	if(!Boolean.TRUE.equals(entity.getBozza())) {
	    validated= validateEntity(entity);
	}
	
	dataIntegration(entity);
	if (validated) {
	    Set<Movimentimailallegati> allegati = entity.getMovimentimailallegatis();
	    entity.setMovimentimailallegatis(null);
	    movimentimailDAO.insert(entity);
	    childDataInsert(entity, allegati);
	}
    }

    private void childDataInsert(Movimentimail entity, Set<Movimentimailallegati> allegati) {

	if (allegati != null) {
	    for (Movimentimailallegati movimentimailallegati : allegati) {
		movimentimailallegati.setMovimentimail(entity);
		movimentimailallegatiService.insert(movimentimailallegati);
	    }
	}
    }

    @Override
    public Movimentimail findById(PkId id) {

	return movimentimailDAO.findById(id);
    }

    @Override
    public void update(Movimentimail entity) {
	
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    movimentimailDAO.update(entity);
	}
    }

    private void dataIntegration(Movimentimail entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("MovimentimailService#dataIntegration: movimentimail è nullo");
	}
	if (entity.getDatainvio() == null) {
	    entity.setDatainvio(Calendar.getInstance().getTime());
	}
	Movimenti movimento = movimentiService.bindDomainObject(entity.getMovimento(), PkId.class, "id.codice");
	entity.setMovimento(movimento);
    }

    @Override
    public void delete(Movimentimail entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    movimentimailDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Movimentimail entity) {

	Set<Movimentimailallegati> movimentimailallegatis = entity.getMovimentimailallegatis();
	if (movimentimailallegatis != null) {
	    for (Movimentimailallegati movimentimailallegati : movimentimailallegatis) {
		movimentimailallegatiService.delete(movimentimailallegati);
	    }
	}
    }

    @Override
    public List<Movimentimail> findByIstanza(Istanze istanza) {

	return movimentimailDAO.findByIstanza(istanza);
    }

    @Override
    public List<Movimentimail> findByMovimento(Movimenti movimento) {

	return movimentimailDAO.findByMovimento(movimento);
    }

    @Override
    public List<Movimentimail> findByMessageId(String messageId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction frMessageId = new FilterRestriction();
	frMessageId.addFilterField(FilterUtils.equals("messageId", messageId, String.class));
	frMessageId.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "movimento.istanza", String.class));
	ft.addRestriction(frMessageId);
	return movimentimailDAO.findByFilterTable(ft);
    }

    @Override
    public void insertChildMessage(Movimentimail entity, Movimentimail child) {

	Movimentimail root = this.findById(entity.getId());
	child.setMovimento(root.getMovimento());
	child.setMovimentimailPadre(root);
	this.insert(child);
	root.getMovimentimailFigli().add(child);
	this.update(root);
    }

    private MailMessageType populateMailMessage(Movimentimail entity, Boolean flgInvialinkallmail, Boolean flgZipLogico,
	    Integer codiceLetteraTipoAllegati) {

	MailMessageType mailMessageType = new MailMessageType();
	mailMessageType.setDestinatari(entity.getDestinatario());
	mailMessageType.setDestinatariInCopia(entity.getDestinatariocc());
	mailMessageType.setDestinatariInCopiaNascosta(entity.getDestinatariobcc());
	mailMessageType.setOggetto(entity.getOggetto());
	String messageId = entity.getMovimento().getId().getIdcomune() + "-" + entity.getMovimento().getIstanza().getSoftware().getCodice() + "-" +
			   entity.getMovimento().getIstanza().getId().getCodice() + "-" + entity.getMovimento().getId().getCodice() + "-" +
			   (new Date()).getTime();
	mailMessageType.setMessageID(messageId);
	mailMessageType.setInviaComeHtml(true);
	// Controllo se impostata la proprietà per inviare i domuneti come link
	if (!BooleanUtils.toBoolean(flgInvialinkallmail)) {
	    log.debug("populateMailMessage# Invio docuemnti in modalita standard come Attchement");
	    mailMessageType.setCorpoMail(entity.getCorpo());
	    //MessageID=IDCOMUNE-SOFTWARE-CODICEISTANZA-CODICEMOVIMENTO-TIMESTAMP
	    if (entity.getMovimentimailallegatis() != null && !entity.getMovimentimailallegatis().isEmpty()) {
		AttachmentsType attachmentsType = new AttachmentsType();
		for (Movimentimailallegati movimentimailallegati : entity.getMovimentimailallegatis()) {
		    if (!EntityUtils.isNestedPropertyBlank(movimentimailallegati, "oggetto.id.codice")) {
			AttachmentType att = new AttachmentType();
			att.setId(movimentimailallegati.getOggetto().getId().getCodice().toString());
			att.setDescrizione(movimentimailallegati.getDocumento());
			Oggetti ogg = oggettiService.findById(movimentimailallegati.getOggetto().getId());
			att.setFileName(ogg.getNomefile());
			String cType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
			att.setMimeType(cType);
			att.setBinaryData(Utilities.bytesToDataHandler(ogg.getOggetto()));
			attachmentsType.getAttachment().add(att);
		    }
		}
		mailMessageType.setAttachments(attachmentsType);
	    }
	} else {
	    log.debug("populateMailMessage# Invio docuemnti in modalita link");
	    if (flgZipLogico == null) {
		flgZipLogico = false;
	    }
	    // viene gestita la casistica in cui gli allegati non vengono passati in attach ma come link sul corpo della mail e/o 
	    // su un documento ad hoc.
	    // CASO : Genero link allegati da associare al corpo della mail
	    mailMessageType.setCorpoMail(inviaAllegatiComeLinkIntoBody(entity, codiceLetteraTipoAllegati, flgZipLogico));
	    // CASO : Genero documento da allegare alla mail contenete i link degli allegati.
	    mailMessageType.setAttachments(inviaAllegatiComeLinkIntoDocument(entity, codiceLetteraTipoAllegati, flgZipLogico));
	}
	return mailMessageType;
    }

    private String inviaAllegatiComeLinkIntoBody(Movimentimail entity, Integer codiceLetteraTipoAllegati, boolean isZipLogico) {

	String body = entity.getCorpo();
	// Verifico se sul corpo della mail è presente il TAG [LINKALLEGATI]
	if (entity.getCorpo().indexOf(WebConstants.LINKALLEGATI) != -1) {
	    log.debug("inviaAllegatiComeLinkIntoBody# Trovato tag [LINKALLEGATI] creo link per raggiungere i documenti fisici");
	    String link = creaLinkAllegati(entity, isZipLogico);
	    log.debug("inviaAllegatiComeLinkIntoBody# Faccio il repalce del [LINKALLEGATI] con i link creati");
	    body = body.replace(WebConstants.LINKALLEGATI, link);
	}
	/*if (entity.getCorpo().indexOf(WebConstants.LINKZIPLOGICO) != -1) {
	    log.debug("inviaAllegatiComeLinkIntoBody# Trivato tag [LINKZIPLOGICO] creo il link per lo zip logico");
	    String link = creaLinkZipLogico(entity.getMovimento().getId().getCodice());
	    log.debug("inviaAllegatiComeLinkIntoBody# Faccio il replace del [LINKZIPLOGICO] con il link creato");
	    body = body.replace(WebConstants.LINKZIPLOGICO, link);
	}*/
	return body;
    }

    /**
     * Il metodo a partire da un template crea un documento contenente i link per scaricare i file fisici: 1. Genera un
     * UUID univo per identificare la lista dei file 2. Crea per ogni oggetto un link (urlServizio+metodoEsposto)e lo
     * salva sulla tabella tempLinkallegati
     * 
     * @param entity
     * @param codiceLetteraTipoAllegati
     * @return
     */
    private AttachmentsType inviaAllegatiComeLinkIntoDocument(Movimentimail entity, Integer codiceLetteraTipoAllegati, boolean isZipLogico) {

	AttachmentsType attachments = new AttachmentsType();
	if (codiceLetteraTipoAllegati != null) {
	    String uuid = UUID.randomUUID().toString();
	    Letteretipo lettereTipo = letteretipoService.findById(new PkId(codiceLetteraTipoAllegati));
	    if ((entity.getMovimentimailallegatis() != null && !entity.getMovimentimailallegatis().isEmpty()) || isZipLogico) {
		if (isZipLogico) {
		    TempLinkallegati tempLinkallegati = new TempLinkallegati();
		    Integer codicemovimento = entity.getMovimento().getId().getCodice();
		    tempLinkallegati.setUuid(uuid);
		    ZipLogicoLinkHelper link = movimentiZipLogicoService.creaLinkZipLogico(codicemovimento);
		    tempLinkallegati.setLink(link.getUrl());
		    tempLinkallegati.setPin(link.getPin());
		    // Nuovo campo inserito
		    tempLinkallegati.setNomedocumento(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_NOMEFILE);
		    tempLinkallegati.setDescrizioneDocumento(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_DESCRIZIONE);
		    tempLinkallegatiService.insert(tempLinkallegati);
		}
		for (Movimentimailallegati movimentimailallegati : entity.getMovimentimailallegatis()) {
		    if (!EntityUtils.isNestedPropertyBlank(movimentimailallegati, "oggetto.id.codice")) {
			TempLinkallegati tempLinkallegati = new TempLinkallegati();
			Integer codiceOggetto = movimentimailallegati.getOggetto().getId().getCodice();
			tempLinkallegati.setUuid(uuid);
			String link = oggettiService.creaSingoloLinkAllegati(codiceOggetto);
			tempLinkallegati.setLink(link);
			tempLinkallegati.setCodiceoggetto(codiceOggetto);
			tempLinkallegati.setPin(codiceOggetto);
			// Nuovo campo inserito
			tempLinkallegati.setNomedocumento(movimentimailallegati.getOggetto().getNomefile());
			tempLinkallegati.setDescrizioneDocumento(movimentimailallegati.getDocumento());
			tempLinkallegatiService.insert(tempLinkallegati);
		    }
		}
		// Devo committare i link inseriti, altrimenti il servizio esterno che crea il documento non
		// li trova.
		movimentimailDAO.flush();
		movimentimailDAO.commit();
		log.debug("Creazione allegato dalla lettera tipo con codice {}", codiceLetteraTipoAllegati);
		Integer codiceOggettoRTF = oggettiService.creaDocumentoConLinkOggetti(lettereTipo,
			entity.getMovimento().getIstanza().getId().getCodice(), entity.getMovimento().getId().getCodice(),
			entity.getMovimento().getTipomovimento().getId().getTipomovimento(), uuid);
		// Converto il file RTF creato in PDF
		log.debug("Conversione dell'oggetto con codice {} in PDF", codiceOggettoRTF);
		Oggetti oggettoRtf = oggettiService.findById(new PkId(codiceOggettoRTF));
		byte[] b = oggettiService.trasformRtfInPdf(oggettoRtf);
		// Allego il documento alla mail
		AttachmentType att = new AttachmentType();
		att.setDescrizione(lettereTipo.getDescrizione());
		String nomeFile = oggettoRtf.getNomefile().replace(".rtf", ".pdf");
		att.setFileName(nomeFile);
		String cType = contenttypesService.findMimeTypeByFileName(nomeFile);
		att.setMimeType(cType);
		att.setBinaryData(Utilities.bytesToDataHandler(b));
		attachments.getAttachment().add(att);
		// Cancellazione documento da MovimentiAllegati
		log.debug("inviaAllegatiComeLinkIntoDocument# Vado a cancellare il file rtf codice: {} creato dal servizio esterno ASP",
			codiceOggettoRTF);
		Movimentiallegati movAll = movimentiallegatiService.findByOggetto(codiceOggettoRTF);
		movimentiallegatiService.delete(movAll);
	    }
	}
	if (attachments.getAttachment().isEmpty()) {
	    return null;
	}
	return attachments;
    }

    /**
     * Devo creare una struttura del genere:
     * <ul>
     * <li><b>Allegato 1</b>&nbsp;(Scarica "&nbsp;<a href=""><i>Allegato1.pdf</i></a>&nbsp;"&nbsp;PIN:123 )</li>
     * <li><b>Allegato 2</b>&nbsp;(Scarica "&nbsp;<a href=""><i>Allegato2.pdf</i></a>&nbsp;"&nbsp;PIN:1233 )</li>
     * <ul>
     *
     * @param entity
     * @return
     */
    private String creaLinkAllegati(Movimentimail entity, boolean isZipLogico) {

	Verticalizzazioniparametri senzapin = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
		WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	boolean pindisabilitato = false;
	if (senzapin != null) {
	    pindisabilitato = StringUtils.defaultString(senzapin.getValore(), "N").equalsIgnoreCase("S");
	}
	StringBuffer ret = new StringBuffer("<ul>");
	// Tipo Link da creare:
	// file: [link_allegato]  , PIN: [codiceoggetto]  <br />
	if (entity.getMovimentimailallegatis() != null && !entity.getMovimentimailallegatis().isEmpty()) {
	    // Per ogni allegato passato genero l'url da dove recuperare il file fisico
	    for (Movimentimailallegati movimentimailallegati : entity.getMovimentimailallegatis()) {
		if (!EntityUtils.isNestedPropertyBlank(movimentimailallegati, "oggetto.id.codice")) {
		    Integer codiceOggetto = movimentimailallegati.getOggetto().getId().getCodice();
		    String urlTmp = oggettiService.creaSingoloLinkAllegati(codiceOggetto);
		    // Dal singolo url creo il link secondo il tempalte
		    //		    ret = ret + "file: " + urlTmp + " , PIN: " + codiceOggetto + " <br />";
		    ret = ret.append("<li><b>").append(movimentimailallegati.getDocumento()).append("</b>&nbsp;(Scarica \"&nbsp;<a href=\"")
			    .append(urlTmp).append("\"><i>").append(movimentimailallegati.getOggetto().getNomefile())
			    .append("</i></a>&nbsp;\"&nbsp;");
		    if (!pindisabilitato) {
			ret = ret.append("PIN:&nbsp;").append(codiceOggetto).append("");
		    }
		    ret = ret.append(")</li>");
		}
	    }
	}
	if (isZipLogico) {
	    Integer codicemovimento = entity.getMovimento().getId().getCodice();
	    ZipLogicoLinkHelper link = movimentiZipLogicoService.creaLinkZipLogico(codicemovimento);
	    // Dal singolo url creo il link secondo il tempalte
	    //		    ret = ret + "file: " + urlTmp + " , PIN: " + codiceOggetto + " <br />";
	    ret = ret.append("<li><b>").append(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_DESCRIZIONE).append("</b>&nbsp;(Scarica \"&nbsp;<a href=\"")
		    .append(link.getUrl()).append("\"><i>").append(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_NOMEFILE).append("</i></a>&nbsp;\"&nbsp;");
	    if (!pindisabilitato) {
		ret = ret.append("PIN:&nbsp;").append(link.getPin()).append("");
	    }
	    ret = ret.append(")</li>");
	}
	ret.append("</ul>");
	return ret.toString();
    }

    private String creaLinkZipLogico(Integer codicemovimento) {

	Verticalizzazioniparametri senzapin = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
		WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	boolean pindisabilitato = false;
	if (senzapin != null) {
	    pindisabilitato = StringUtils.defaultString(senzapin.getValore(), "N").equalsIgnoreCase("S");
	}
	StringBuffer ret = new StringBuffer("<ul>");
	// Tipo Link da creare:
	// file: [link_allegato]  , PIN: [codiceoggetto]  <br />	
	ZipLogicoLinkHelper link = movimentiZipLogicoService.creaLinkZipLogico(codicemovimento);
	// Dal singolo url creo il link secondo il tempalte
	//		    ret = ret + "file: " + urlTmp + " , PIN: " + codiceOggetto + " <br />";
	ret = ret.append("<li><b>").append(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_DESCRIZIONE).append("</b>&nbsp;(Scarica \"&nbsp;<a href=\"")
		.append(link.getUrl()).append("\"><i>").append(WebConstants.CARTELLA_ZIP_DEI_DOCUMENTI_NOMEFILE).append("</i></a>&nbsp;\"&nbsp;");
	if (!pindisabilitato) {
	    ret = ret.append("PIN:&nbsp;").append(link.getPin()).append("");
	}
	ret = ret.append(")</li>");
	ret.append("</ul>");
	return ret.toString();
    }

    /* @Override
     public void sendMail(Movimentimail entity) {
    
    this.sendMail(entity, null, null);
     }*/
    @Override
    public String sendMail2(Movimentimail entity, Integer accountId, String codiceComune) throws FunzioneBusinessRemotaException {

	return sendMail2(entity, null, null, null, accountId, codiceComune);
    }

    @Override
    public String sendMail2(Movimentimail entity, Boolean flgInvialinkallmail, Boolean flgZipLogico, Integer codiceLetteraTipoAllegati,
	    Integer accountId, String codiceComune) throws FunzioneBusinessRemotaException {

	String ret = "";
	dataIntegration(entity);
	if(StringUtils.isBlank(entity.getDestinatario())) {
		log.error("il destinatario dev'essere valorizzato");
		throw new RuntimeException("il destinatario dev'essere valorizzato");
	    }
	if (validateEntity(entity) && isInvioAllowed(flgInvialinkallmail, codiceLetteraTipoAllegati, entity.getCorpo())) {
	    MailMessageType mailMessage = populateMailMessage(entity, flgInvialinkallmail, flgZipLogico, codiceLetteraTipoAllegati);
	    ret = mailServiceWSClient.sendMail2(entity.getMovimento().getId().getCodice(), ORMHelper.getSoftware(), accountId, codiceComune,
		    ORMHelper.getToken(), mailMessage);
	    if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE)
		    && StringUtils.isNotBlank(verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE,
			    WebConstants.VERTICALIZZAIONE_MAIL_SERVICE_POSTA_USCITA_FOLDERNAME))) {
		Movimentiallegati movimentiallegati = new Movimentiallegati();
		movimentiallegati.setFlagPubblica(false);
		movimentiallegati.setMovimento(entity.getMovimento());
		movimentiallegati.setDescrizione(mailMessage.getOggetto());
		movimentiallegati.setMessageId(mailMessage.getMessageID());
		movimentiallegatiService.insert(movimentiallegati);
	    }
	}
	return ret;
    }

    // Il metodo controlla se tutte le configurazione per l'invio mail sono rispettate, altrimenti rilancia gli errori a video
    private boolean isInvioAllowed(Boolean flgInvialinkallmail, Integer codiceLetteraTipoAllegati, String corpo) {

	boolean invio = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Controllo : Nel caso sia impostato l'invio dei documenti come link deve essere presente:
	// Il tag  [LINKALLEGATI] nel corpo della mail o un documento tipo per creare un allegato contenenti i link
	if (BooleanUtils.toBoolean(flgInvialinkallmail) && codiceLetteraTipoAllegati == null && corpo.indexOf(WebConstants.LINKALLEGATI) == -1) {
	    _ivs.add(new InvalidValue("service_error.configurazione_invio_allegati_errata", null, null, null, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return invio;
    }

    @Override
    public boolean existsByMovimento(Integer codiceMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	ft.addRestriction(fr);
	return movimentimailDAO.countRecord(ft) > 0;
    }

    @Override
    public List<Movimentimail> findByCodiceMovimento(Integer codicemovimento) {

	FilterTable ft = getFilterTableFor(codicemovimento, null);
	return movimentimailDAO.findByFilterTable(ft);
    }

    @Override
    public List<Movimentimail> findByCodiceIstanza(Integer codiceistanza) {

	FilterTable ft = getFilterTableFor(null, codiceistanza);
	return movimentimailDAO.findByFilterTable(ft);
    }

    @Override
    public List<Movimentimail> findByMailPadre(Integer codiceMovimentoMailPadre) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceMovimentoMailPadre, "movimentimailPadre", Integer.class));
	ft.addRestriction(fr);
	return movimentimailDAO.findByFilterTable(ft);
    }

    private FilterTable getFilterTableFor(Integer codiceMovimento, Integer codiceIstanza) {

	if (codiceMovimento == null && codiceIstanza == null) {
	    throw new RuntimeException("getFilterTableFor: codiceistanza o codice movimento obbligatori");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (codiceMovimento != null) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("movimentoId", codiceMovimento, Integer.class));
	    ft.addRestriction(fr);
	}
	if (codiceIstanza != null) {
	    FilterRestriction fr2 = new FilterRestriction();
	    fr2.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, "movimento", Integer.class));
	    ft.addRestriction(fr2);
	}
	ft.addOrder(FilterUtils.orderAsc("data", "movimento"));
	ft.addOrder(FilterUtils.orderAsc("movimento", "movimento"));
	ft.addOrder(FilterUtils.orderAsc("datainvio"));
	return ft;
    }

    @Override
    public List<Movimentimail> findByAccountId(Integer idAccount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAccount, "mailConfig", Integer.class));
	ft.addRestriction(fr);
	return movimentimailDAO.findByFilterTable(ft);
    }

    @Override
    public int countByAccountId(Integer idAccount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAccount, "mailConfig", Integer.class));
	ft.addRestriction(fr);
	return movimentimailDAO.countRecord(ft);
    }
}
