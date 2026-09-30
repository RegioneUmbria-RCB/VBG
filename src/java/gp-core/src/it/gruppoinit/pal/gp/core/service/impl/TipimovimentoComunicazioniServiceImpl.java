package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.TipimovimentoComunicazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.DestinatariHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoComunicazioniService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.TipoComunicazionemovimentoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * 
 * @author
 */
@Service
public class TipimovimentoComunicazioniServiceImpl extends BaseServiceImpl<TipimovimentoComunicazioni, PkId>
	implements TipimovimentoComunicazioniService {

    private static final Logger log = LoggerFactory.getLogger(TipimovimentoComunicazioniServiceImpl.class);
    private TipimovimentoComunicazioniDAO tipimovimentocomunicazioniDAO;
    private TipiMovimentoService tipiMovimentoService;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private ContenttypesService contenttypesService;
    private OggettiService oggettiService;
    private IstanzeeventiService istanzeeventiService;
    private IstanzeService istanzeService;
    private MailConfigService mailConfigService;
    private ResponsabiliService responsabiliService;
    private MovimentiNoSecurityService movimentiNoSecurityService;

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setTipimovimentoComunicazioniDAO(TipimovimentoComunicazioniDAO tipimovimentocomunicazioniDAO) {

	this.tipimovimentocomunicazioniDAO = tipimovimentocomunicazioniDAO;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
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
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    @Override
    protected Class<TipimovimentoComunicazioni> getEntityClass() {

	return TipimovimentoComunicazioni.class;
    }

    @Override
    public List<TipimovimentoComunicazioni> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentocomunicazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipimovimentoComunicazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovimentocomunicazioniDAO.insert(entity);
	}
    }

    @Override
    public TipimovimentoComunicazioni findById(PkId id) {

	return tipimovimentocomunicazioniDAO.findById(id);
    }

    @Override
    public void update(TipimovimentoComunicazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovimentocomunicazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(TipimovimentoComunicazioni entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovimentocomunicazioniDAO.delete(entity);
	}
    }

    @Override
    public TipimovimentoComunicazioni findByTipoMovAndFunzione(String tipomovimento, String funzione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, "tipimovimento", String.class));
	fr.addFilterField(FilterUtils.equals("funzione", funzione, String.class));
	ft.addRestriction(fr);
	List<TipimovimentoComunicazioni> comunicazionis = tipimovimentocomunicazioniDAO.findByFilterTable(ft);
	if (!comunicazionis.isEmpty()) {
	    return comunicazionis.get(0);
	}
	return null;
    }

    @Override
    public List<TipimovimentoComunicazioni> findByTipoMov(String codiceTipmov) {

	return findByTipoMov(codiceTipmov, null);
    }

    public List<TipimovimentoComunicazioni> findByTipoMov(String codiceTipmov, TipoComunicazionemovimentoEnum tipoComunicazionemovimentoEnum) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", codiceTipmov, "tipimovimento", String.class));
	if (tipoComunicazionemovimentoEnum != null) {
	    fr.addFilterField(FilterUtils.equals("funzione", tipoComunicazionemovimentoEnum.getValue(), String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("funzione"));
	List<TipimovimentoComunicazioni> comunicazionis = tipimovimentocomunicazioniDAO.findByFilterTable(ft);
	return comunicazionis;
    }

    @Override
    public void eseguiComunicazione(Movimenti movimenti, List<Movimentiallegati> listAllegati, Boolean isGestioneEventi,
	    TipoComunicazionemovimentoEnum tipoComunicazionemovimentoEnum) {

	List<TipimovimentoComunicazioni> list = this.findByTipoMov(movimenti.getTipomovimento().getId().getTipomovimento(),
		tipoComunicazionemovimentoEnum);
	log.debug("eseguiComunicazione# eseguo il flush");
	tipimovimentocomunicazioniDAO.flush();
	log.debug("eseguiComunicazione# eseguo il clear");
	tipimovimentocomunicazioniDAO.clear();
	log.debug("eseguiComunicazione# cerco il movimento con id {}", movimenti.getId().getCodice());
	movimenti = movimentiNoSecurityService.findById(new PkId(movimenti.getId().getCodice())); // avoid  could not initialize proxy - no Session
	for (TipimovimentoComunicazioni tipimovimentoComunicazioni : list) {
	    log.debug("eseguiComunicazione# eseguo la tipocomunicazione {}", tipimovimentoComunicazioni.getId().getCodice());
	    eseguiTipocomunicazione(movimenti, listAllegati, tipimovimentoComunicazioni, isGestioneEventi);
	    log.debug("eseguiComunicazione# terminata la tipocomunicazione {}", tipimovimentoComunicazioni.getId().getCodice());
	}
    }

    private void eseguiTipocomunicazione(Movimenti movimenti, List<Movimentiallegati> listAllegati,
	    TipimovimentoComunicazioni tipimovimentoComunicazioni, Boolean isGestioneEventi) {

	Mailtipo mailtipo = tipimovimentoComunicazioni.getMailtipo();
	String tipoFunzione = tipimovimentoComunicazioni.getFunzione();
	log.debug("eseguiComunicazione# tipoFunzione {}", tipoFunzione);
	MailConfig mailConfig = tipimovimentoComunicazioni.getMailConfig();
	if (tipoFunzione.equalsIgnoreCase(TipoComunicazionemovimentoEnum.FIRMA_DOC.getValue())) {
	    eseguiComunicazioneDopoFirmaDocumento(movimenti, listAllegati, mailtipo, mailConfig, isGestioneEventi);
	}
	if (tipoFunzione.equalsIgnoreCase(TipoComunicazionemovimentoEnum.INSER_MOV.getValue())) {
	    eseguiComunicazioneDopoInserimentoMovimento(tipimovimentoComunicazioni, movimenti, mailtipo, mailConfig, isGestioneEventi);
	}
    }

    private boolean eseguiComunicazioneDopoInserimentoMovimento(TipimovimentoComunicazioni tipimovimentoComunicazioni, Movimenti movimenti,
	    Mailtipo mailtipo, MailConfig mailConfig, Boolean isGestioneEventi) {

	boolean result = false;
	log.debug("eseguiComunicazioneDopoInserimentoMovimento# Recupero i destinatari");
	boolean isInInserimento = BooleanUtils.toBoolean(tipimovimentoComunicazioni.getFlagInserimentoMov());
	if (StringUtils.isNotBlank(tipimovimentoComunicazioni.getDestinatariMail())) {
	    log.debug("eseguiComunicazioneDopoInserimentoMovimento# Recupero tutti i destinatari");
	    List<DestinatariHelper> dest = DestinatariHelper.getTipiDestinatari(tipimovimentoComunicazioni.getDestinatariMail());
	    List<String> indirizziEmail = getIndirizziEmailDestinatari(dest, movimenti);
	    log.debug(
		    "eseguiComunicazioneDopoInserimentoMovimento# Controllo se siamo in fase di inserimento movimento o inserimento scadenza/contromovimento ");
	    if (isInInserimento && movimenti.getData() != null) {
		log.debug("eseguiComunicazioneDopoInserimentoMovimento# Inserimento movimento");
		try {
		    invioEmailDopoInserimentoMovimento(indirizziEmail, mailtipo, movimenti, mailConfig);
		    result = true;
		} catch (Exception e) {
		    result = false;
		    log.error("Errore durante l'invio della comunicazione mail per l'inserimento del movimento {} : {}",
			    new Object[] { movimenti.getId().getCodice(), e.getMessage() });
		    if (isGestioneEventi) {
			String messaggio = "La notifica ai destinatari dell'inserimento del movimento " + movimenti.getMovimento() + "["
				+ movimenti.getId().getCodice() + "] non è andata a buon fine:" + e.getMessage();
			istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_MAIL, movimenti, movimenti.getIstanza());
		    }
		}
	    } // else {
	    if (!isInInserimento && movimenti.getData() == null) {
		log.debug("eseguiComunicazioneDopoInserimentoMovimento# Inserimento scandenza o contromovimento");
		try {
		    invioEmailDopoInserimentoMovimento(indirizziEmail, mailtipo, movimenti, mailConfig);
		    result = true;
		} catch (Exception e) {
		    e.printStackTrace();
		    result = false;
		    log.error("Errore durante l'invio della comunicazione mail per l'inserimento del movimento {} : {}",
			    new Object[] { movimenti.getId().getCodice(), e.getMessage() });
		    if (isGestioneEventi) {
			String messaggio = "La notifica ai destinatari dell'inserimento del movimento " + movimenti.getMovimento() + "["
				+ movimenti.getId().getCodice() + "] non è andata a buon fine:" + e.getMessage();
			istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_MAIL, movimenti, movimenti.getIstanza());
		    }
		}
		result = true;
	    }
	} else {
	    log.debug("eseguiComunicazioneDopoInserimentoMovimento# Non sono prenseti destinatari a cui inviare l'email");
	    if (isGestioneEventi) {
		String messaggio = "La notifica ai destinatari dell'inserimento del movimento " + movimenti.getMovimento() + "["
			+ movimenti.getId().getCodice() + "] non è andata a buon fine: Destinatari non presenti";
		istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_MAIL, movimenti, movimenti.getIstanza());
	    }
	}
	return result;
    }

    private boolean eseguiComunicazioneDopoFirmaDocumento(Movimenti movimenti, List<Movimentiallegati> listAllegati, Mailtipo mailtipo,
	    MailConfig mailConfig, Boolean isGestioneEventi) {

	boolean result = false;
	// Questo tipo di comunicazione prevede l'invio della mail al domicilio elettronico
	String domicilioElettronico = StringUtils.defaultString(movimenti.getIstanza().getDomicilioElettronico(), "");
	if (StringUtils.isBlank(movimenti.getIstanza().getDomicilioElettronico())) {
	    // Insetrimento evento
	    log.debug("eseguiComunicazioneDopoFirmaDocumento# Impossibile effettuare la comunicazione, domicilio elettronico non presente");
	    if (isGestioneEventi) {
		String messaggio = "La firma del documento non è stata comunicata al destinatario: domicilio elettronico non popolato";
		istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_MAIL, movimenti, movimenti.getIstanza());
		FlashMessages.getWarnings().add(messaggio);
	    }
	    return false;
	}
	try {
	    MailMessageType mailMessageType = populateMailMessage(domicilioElettronico, null, null, movimenti, listAllegati, mailtipo);
	    //	    mailServiceWSClient.sendMail(movimenti.getId().getCodice(), ORMHelper.getSoftware(), ORMHelper.getToken(), mailMessageType);
	    Istanze istanze = istanzeService.findById(new PkId(movimenti.getIstanza().getId().getCodice()));
	    if (mailConfig == null) {
		mailConfig = mailConfigService.findPrepopolaInvioEmailBySoftwareAndCodiceComune(istanze.getSoftware().getCodice(),
			istanze.getComune().getCodicecomune(), true);
	    }
	    /*MailConfig mcfg = mailConfigService.findPrepopolaInvioEmailBySoftwareAndCodiceComune(istanze.getSoftware().getCodice(),
	        istanze.getComune().getCodicecomune(), true);*/
	    mailServiceWSClient.sendMail2(movimenti.getId().getCodice(), ORMHelper.getSoftware(), mailConfig.getId().getCodice(),
		    istanze.getComune().getCodicecomune(), ORMHelper.getToken(), mailMessageType);
	    result = true;
	    FlashMessages.getInfos().add("Comunicazione inviata a :" + domicilioElettronico);
	} catch (Exception e) {
	    result = true;
	    log.error("Errore durante l'invio della comunicazione mail :" + e.getMessage());
	    if (isGestioneEventi) {
		String messaggio = "La firma del documento non è stata comunicata al destinatario:" + e.getMessage();
		FlashMessages.getWarnings().add(messaggio);
		istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_MAIL, movimenti, movimenti.getIstanza());
	    }
	}
	return result;
    }

    private MailMessageType populateMailMessage(String a, String cc, String ccn, Movimenti movimenti, List<Movimentiallegati> listAllegati,
	    Mailtipo mailtipo) {

	movimenti = movimentiNoSecurityService.findById(new PkId(movimenti.getId().getCodice()));
	mailtipo = mailtipoService.findById(new PkId(mailtipo.getId().getCodice()));
	Movimentiallegati movAll = null;
	if (listAllegati != null && !listAllegati.isEmpty()) {
	    movAll = listAllegati.get(0);
	}
	MailMessageType mailMessageType = new MailMessageType();
	mailMessageType.setDestinatari(a);
	mailMessageType.setDestinatariInCopia(cc);
	mailMessageType.setDestinatariInCopiaNascosta(ccn);
	//MessageID=IDCOMUNE-SOFTWARE-CODICEISTANZA-CODICEMOVIMENTO-TIMESTAMP
	String messageId = movimenti.getId().getIdcomune() + "-" + movimenti.getIstanza().getSoftware().getCodice()
		+ (movAll != null ? "-" + movAll.getMovimento().getIstanza().getId().getCodice() : "") + "-" + movimenti.getId().getCodice() + "-"
		+ (new Date()).getTime();
	mailMessageType.setMessageID(messageId);
	mailMessageType.setInviaComeHtml(true);
	AttachmentsType attachmentsType = new AttachmentsType();
	if (listAllegati != null && !listAllegati.isEmpty()) {
	    for (Movimentiallegati movimentiallegati : listAllegati) {
		if (!EntityUtils.isNestedPropertyBlank(movimentiallegati, "oggetto.id.codice")) {
		    AttachmentType att = new AttachmentType();
		    att.setId(movimentiallegati.getOggetto().getId().getCodice().toString());
		    att.setDescrizione(movimentiallegati.getDescrizione());
		    Oggetti ogg = oggettiService.findById(movimentiallegati.getOggetto().getId());
		    att.setFileName(ogg.getNomefile());
		    String cType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
		    att.setMimeType(cType);
		    att.setBinaryData(Utilities.bytesToDataHandler(ogg.getOggetto()));
		    attachmentsType.getAttachment().add(att);
		    mailMessageType.setAttachments(attachmentsType);
		}
	    }
	}
	mailtipo = mailtipoService.replaceOggettoCorpo(mailtipo, movimenti.getIstanza(), movimenti);
	mailMessageType.setCorpoMail(StringUtils.defaultIfEmpty(mailtipo.getCorpo(), ""));
	mailMessageType.setOggetto(StringUtils.defaultIfEmpty(mailtipo.getOggetto(), ""));
	return mailMessageType;
    }

    private List<String> getIndirizziEmailDestinatari(List<DestinatariHelper> dest, Movimenti movimento) {

	List<String> indirizzi = new ArrayList<String>();
	for (DestinatariHelper destinatariHelper : dest) {
	    //Richiedente. PEC
	    if (destinatariHelper.getCodice().equals(new Integer(0)) && destinatariHelper.getSelezionato()) {
		if (StringUtils.isNotBlank(movimento.getIstanza().getRichiedente().getPec())) {
		    indirizzi.add(movimento.getIstanza().getRichiedente().getPec());
		}
	    }
	    if (destinatariHelper.getCodice().equals(new Integer(1)) && destinatariHelper.getSelezionato()) {
		if (StringUtils.isNotBlank(movimento.getIstanza().getResponsabile().getEmail())) {
		    indirizzi.add(movimento.getIstanza().getResponsabile().getEmail());
		}
	    }
	    if (destinatariHelper.getCodice().equals(new Integer(2)) && destinatariHelper.getSelezionato()) {
		if (EntityUtils.getNestedProperty(movimento.getIstanza().getResponsabileProcedimento(), "id.codice") != null
			&& StringUtils.isNotBlank(movimento.getIstanza().getResponsabileProcedimento().getEmail())) {
		    indirizzi.add(movimento.getIstanza().getResponsabileProcedimento().getEmail());
		}
	    }
	    if (destinatariHelper.getCodice().equals(new Integer(3)) && destinatariHelper.getSelezionato()) {
		if (EntityUtils.getNestedProperty(movimento.getIstanza().getIstruttore(), "id.codice") != null
			&& StringUtils.isNotBlank(movimento.getIstanza().getIstruttore().getEmail())) {
		    indirizzi.add(movimento.getIstanza().getIstruttore().getEmail());
		}
	    }
	    if (destinatariHelper.getCodice().equals(new Integer(4)) && destinatariHelper.getSelezionato()) {
		if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") != null
			&& (StringUtils.isNotBlank(movimento.getAmministrazioni().getEmail())
				|| StringUtils.isNotBlank(movimento.getAmministrazioni().getPec()))) {
		    if (StringUtils.isNotBlank(movimento.getAmministrazioni().getPec())) {
			indirizzi.add(movimento.getAmministrazioni().getPec());
		    } else {
			indirizzi.add(movimento.getAmministrazioni().getEmail());
		    }
		}
	    }
	    // Istanza.domicilio elettronico
	    if (destinatariHelper.getCodice().equals(new Integer(5)) && destinatariHelper.getSelezionato()) {
		if (StringUtils.isNotBlank(movimento.getIstanza().getDomicilioElettronico())) {
		    indirizzi.add(movimento.getIstanza().getDomicilioElettronico());
		}
	    }
	    // responsabili attivi per comune e software dell'istanza
	    if (destinatariHelper.getCodice().equals(new Integer(6)) && destinatariHelper.getSelezionato()) {
		String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
		String codiceSoftware = movimento.getIstanza().getSoftware().getCodice();
		List<Responsabili> l = responsabiliService.findResponsabiliComuniAndSoftware(codiceComune, codiceSoftware);
		for (Responsabili responsabili : l) {
		    if (StringUtils.isNotBlank(responsabili.getEmail())) {
			indirizzi.add(responsabili.getEmail());
		    }
		}
	    }
	}
	return indirizzi;
    }

    private boolean invioEmailDopoInserimentoMovimento(List<String> indirizziEmail, Mailtipo mailtipo, Movimenti movimento, MailConfig mailConfig)
	    throws FunzioneBusinessRemotaException {

	boolean isInviata = false;
	for (String indirizzo : indirizziEmail) {
	    MailMessageType mailMessageType = populateMailMessage(indirizzo, null, null, movimento, null, mailtipo);
	    Istanze istanze = istanzeService.findById(new PkId(movimento.getIstanza().getId().getCodice()));
	    if (mailConfig == null) {
		mailConfig = mailConfigService.findPrepopolaInvioEmailBySoftwareAndCodiceComune(istanze.getSoftware().getCodice(),
			istanze.getComune().getCodicecomune(), true);
	    }
	    mailServiceWSClient.sendMail2(movimento.getId().getCodice(), ORMHelper.getSoftware(), mailConfig.getId().getCodice(),
		    istanze.getComune().getCodicecomune(), ORMHelper.getToken(), mailMessageType);
	    isInviata = true;
	}
	return isInviata;
    }

    private void dataIntegration(TipimovimentoComunicazioni entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(TipimovimentoComunicazioni entity) {

	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipimovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipimovimento(tipimovimento);
    }
    //    protected boolean isDeleteAllowed(TipimovimentoComunicazioni entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
