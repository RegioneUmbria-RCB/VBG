package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.GraduatoriedComDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.GraduatoriedComHelper;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

/**
 * 
 * @author
 */
@Service
public class GraduatoriedComServiceImpl extends BaseServiceImpl<GraduatoriedCom, PkId> implements GraduatoriedComService {

    private static final Logger log = LoggerFactory.getLogger(GraduatoriedComServiceImpl.class);
    private static final Integer inserimentoTerminatoPasso1 = 1;
    private static final Integer inserimentoTerminatoPasso2 = 2;
    private static final Integer inserimentoTerminatoPasso3 = 3;
    private static final Integer inserimentoTerminatoPasso4 = 4;
    private static final Integer inserimentoTerminatoPasso5 = 5;
    private static final Integer inserimentoTerminatoPasso6 = 6;
    private GraduatoriedComDAO graduatoriedcomDAO;
    private AmministrazioniService amministrazioniService;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private GraduatorietComService graduatorietComService;
    private GraduatoriedService graduatoriedService;
    private IstanzeService istanzeService;
    private IstanzeeventiService istanzeeventiService;
    private MailConfigService mailConfigService;
    private MailtipoService mailtipoService;
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentimailService movimentimailService;
    private OggettiService oggettiService;
    private ProtocollazioneService protocollazioneService;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    private DocumentMergeService documentMergeService;

    @Autowired
    public void setDocumentMergeService(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Autowired
    public void setTipimovimentodoctipoService(TipimovimentodoctipoService tipimovimentodoctipoService) {

	this.tipimovimentodoctipoService = tipimovimentodoctipoService;
    }

    @Autowired
    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Autowired
    public void setGraduatoriedComDAO(GraduatoriedComDAO graduatoriedcomDAO) {

	this.graduatoriedcomDAO = graduatoriedcomDAO;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setGraduatorietComService(GraduatorietComService graduatorietComService) {

	this.graduatorietComService = graduatorietComService;
    }

    @Autowired
    public void setGraduatoriedcomDAO(GraduatoriedComDAO graduatoriedcomDAO) {

	this.graduatoriedcomDAO = graduatoriedcomDAO;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
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
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected Class<GraduatoriedCom> getEntityClass() {

	return GraduatoriedCom.class;
    }

    @Override
    public List<GraduatoriedCom> findAll(Integer firstResult, Integer maxResult) {

	return graduatoriedcomDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(GraduatoriedCom entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    graduatoriedcomDAO.insert(entity);
	}
    }

    @Override
    public GraduatoriedCom findById(PkId id) {

	return graduatoriedcomDAO.findById(id);
    }

    @Override
    public void update(GraduatoriedCom entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    graduatoriedcomDAO.update(entity);
	}
    }

    @Override
    public void delete(GraduatoriedCom entity) {

	if (isDeleteAllowed(entity)) {
	    //Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    childDelete(entity);
	    log.debug("childDelete# Cancello il record graduatoriedCom : {}....", entity.getId().getCodice());
	    // Cancello i vari oggetti referenziati in graduatoried
	    graduatoriedcomDAO.delete(entity);
	    // if (codiceOggettoDaCancellare != null) {
	    //	Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    //	oggettiService.delete(oggettoDaCancellare);
	    //  }
	}
    }

    @Override
    protected void childDelete(GraduatoriedCom entity) {

	log.debug("childDelete# Controllo se ha un movimento collegato....");
	Movimenti movimento = null;
	if (EntityUtils.getNestedProperty(entity.getMovimenti(), "id.codice") != null) {
	    movimento = entity.getMovimenti();
	    entity.setMovimenti(null);
	}
	if (EntityUtils.getNestedProperty(entity.getMovimentimail(), "id.codice") != null) {
	    entity.setMovimentimail(null);
	}
	this.update(entity);
	graduatoriedcomDAO.flush();
	if (movimento != null) {
	    log.debug("childDelete# Cancellazione movimento.....");
	    movimentiService.delete(movimento);
	    log.debug("childDelete# Cancellato.....");
	}
    }

    @Override
    public Integer countGraduatoriedComDomande(Integer codiceGraduatoriatCom) {

	return graduatoriedcomDAO.countGraduatoriedComDomande(codiceGraduatoriatCom);
    }

    @Override
    public Integer countGraduatoriedComMovimenti(Integer codiceGraduatoriatCom) {

	return graduatoriedcomDAO.countGraduatoriedComMovimenti(codiceGraduatoriatCom);
    }

    @Override
    public Integer countGraduatoriedComAllegati(Integer codiceGraduatoriatCom) {

	return graduatoriedcomDAO.countGraduatoriedComAllegati(codiceGraduatoriatCom);
    }

    @Override
    public Integer countGraduatoriedComMailInviate(Integer codiceGraduatoriatCom) {

	return graduatoriedcomDAO.countGraduatoriedComMailInviate(codiceGraduatoriatCom);
    }

    public int inserimentoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom,
	    Amministrazioni amministrazione, Istanze istanza) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	Movimenti movimento = null;
	try {
	    // Popolo il movimento che andrò ad inserire
	    movimento = popolateMovimento(graduatorietCom, istanza);
	    log.debug("inserimentoMovimentoPerLaComunicazione# Inserisco il movimento creato....");
	    movimentiService.insert(movimento);
	    log.debug(
		    "inserimentoMovimentoPerLaComunicazione# Movimento {} inserito correttamente sull' istanza {}, setto codice movimento {} su graduatoriedCom....",
		    new Object[] { movimento.getTipomovimento().getId().getTipomovimento(), movimento.getIstanza().getNumeroistanza(),
			    movimento.getId().getCodice() });
	    // Setto il codice movimento creato sull' oggetto graduatoriedCom
	    graduatoriedCom.setMovimenti(movimento);
	    // Committo perchè se si genera un errore successivo non deve essere fatto il roll-bak di questa operazione
	    log.debug(
		    "inserimentoMovimentoPerLaComunicazione# Commit di graduatoriedCom dopo inserimento del riferimento al movimento creato movimento");
	    this.update(graduatoriedCom);
	    graduatoriedcomDAO.flush();
	    graduatoriedcomDAO.commit();
	    log.debug("inserimentoMovimentoPerLaComunicazione# Fatta commit dell'inserimento del movimento");
	} catch (Exception e) {
	    log.debug("inserimentoMovimentoPerLaComunicazione# Inserisco evento di mancato inserimento del movimento per l'istanza {} ({})",
		    new Object[] { istanza.getNumeroistanza(), istanza.getId().getCodice() });
	    // Inserimento evento di mancato inserimento movimento
	    insertEventoIstanzaInserimentoMovimentoDaComunicazione(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza);
	    // Inserisco graduatoriedCom
	    this.insert(graduatoriedCom);
	    // Faccio flush e commit per salvare gli inserimenti fatti fino a qua
	    log.debug(
		    "inserimentoMovimentoPerLaComunicazione# Faccio la commit di graduatoriedCom, deve essere inserito anche se non è stato creato il movimento ");
	    graduatoriedcomDAO.flush();
	    graduatoriedcomDAO.commit();
	    log.debug(
		    "Errore inserimento dettaglio graduatoria comunicazione (graduatoriad : {}) bloccato al passo 1 (Inserimento movimento), impossibile " +
		      "inserire il movimento {}.Errore: {} [{}]",
		    new Object[] { graduatoriedCom.getGraduatoried().getId().getCodice(),
			    graduatorietCom.getTipimovimento().getId().getTipomovimento(), e.getMessage(), e });
	    //throw new GraduatorieComunicazioniValidationException();
	    return inserimentoTerminatoPasso1;
	}
	return risultato;
    }

    public int inserimentoProtocolloMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	if (graduatorietCom.getFlgProtocolla() == true) {
	    log.debug("insertComunicazioneDettaglio# Protocollo movimento {}......", movimento.getId().getCodice());
	    try {
		// BOCCI 2016-08-22 NON SCOMMMENTARE PROTOCOLLAMOVIMENTO PERCHE' SE NO è SEMPRE PROTOCOLLO IN INGRESSO
		// protocollazioneService.protocollaMovimento(movimento, ORMHelper.getToken(), null);
		//		ProtocollazioneCommand protocollazioneCommand = populateProtocollazioneCommand(istanza, movimento.getId().getCodice(),
		//			movimento.getAmministrazioni(), graduatoriedCom);		
		DatiProtocolloResponseType dpr = protocollazioneService.protocollaComunicazioneGraduatoria(movimento, ORMHelper.getToken());
		//		protocollazioneService.protocolla(protocollazioneCommand, movTemp.getIstanza().getSoftware().getCodice(), movTemp.getIstanza()
		//			.getComune().getCodicecomune());
		// Recupero il movimento in modo da fare una Commit e una flush, per mantenere le modifiche del protocollo in caso
		// si verifichino errori, nei passi successivi.
		verificaErroreProtocollo(dpr);		
		Movimenti movTemp = movimentiService.findById(new PkId(movimento.getId().getCodice()));
		movTemp.setNumeroprotocollo(dpr.getNumeroProtocollo());
		movTemp.setDataprotocollo(Utilities.getDate(dpr.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN).getTime());
		movTemp.setFkidprotocollo(dpr.getIdProtocollo());
		// Viene utilizzato il metodo deprecato perchè evitiamo cosi di fare operazioni unitile in quetso caso 
		// come chekprotocollo
		movimentiService.update(movTemp);
		graduatoriedCom.setMovimenti(movTemp);
		graduatoriedCom.setFlgProtocollato(Boolean.TRUE);
		this.update(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		graduatoriedcomDAO.flush();
	    } catch (Exception e) {
		log.error(
			"Movimento {}, non protocollato a causa dell'errore {}[{}], faccio insert e commit dell'oggetto graduatoriedCom per non perdere le informazioni già inserite ",
			new Object[] { movimento.getId().getCodice(), e.getMessage(), e });
		log.warn("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
		graduatoriedcomDAO.refreshEntity(movimento);
		graduatoriedCom = graduatoriedcomDAO.findById(new PkId(graduatoriedCom.getId().getCodice()));
		graduatoriedcomDAO.refreshEntity(graduatoriedCom);
		movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
		graduatoriedCom = graduatoriedcomDAO.findById(new PkId(graduatoriedCom.getId().getCodice()));
		StringBuffer message = new StringBuffer("Protocollazione ").append(" non avvenuta a causa di ").append(e.getMessage());
		insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
		this.insert(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		graduatoriedcomDAO.flush();
		return inserimentoTerminatoPasso2;
	    }
	}
	return risultato;
    }

    private void verificaErroreProtocollo(DatiProtocolloResponseType dpr) {

	if (dpr == null || // 
		StringUtils.isBlank(dpr.getNumeroProtocollo()) || // 
		(dpr.getErrore() != null && StringUtils.isNotBlank(dpr.getErrore().getDescrizione()))) {
	    String infoAggiuntive = "";
	    if (dpr != null && dpr.getErrore() != null && StringUtils.isNotBlank(dpr.getErrore().getDescrizione())) {
		infoAggiuntive = dpr.getErrore().getDescrizione();
	    }
	    log.error("verificaErroreProtocollo protocollazione non avvenuta DatiProtocolloResponseType {}", infoAggiuntive);
	    throw new RuntimeException("La protocollazione non è avvenuta correttamente: " + infoAggiuntive);
	}
    }

    @Override
    public int conversioneInPDFAllegatoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento) {

	int risultato = 0;
	if (BooleanUtils.isTrue(graduatorietCom.getFlgTrasformaPdf())) {
	    if (EntityUtils.getNestedProperty(graduatorietCom.getLetteretipo(), "id.codice") != null) {
		if (EntityUtils.getNestedProperty(graduatoriedCom.getOggetti(), "id.codice") != null) {
		    log.debug("insertComunicazioneDettaglio# Converto l'allegato in pdf....");
		    try {
			Oggetti o = oggettiService.findByIdLazy(new PkId(graduatoriedCom.getOggetti().getId().getCodice()));
			if (o != null) {
			    if (o.getNomefile().toLowerCase().endsWith(".rtf") || o.getNomefile().toLowerCase().endsWith(".odt")) {
				FileConverterWsClient fileConverterWService = new FileConverterWsClient();
				o = oggettiService.findById(new PkId(o.getId().getCodice()));
				String estensione = "RTF";
				if (o.getNomefile().toLowerCase().endsWith(".odt")) {
				    estensione = "ODT";
				}
				ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), o.getOggetto(), estensione, "PDF");
				ConvertBinaryResponse s = fileConverterWService.convertBinary(cbr);
				byte[] res = s.getBinaryData();
				o.setOggetto(res);
				o.setNomefile(o.getNomefile() + ".pdf");
				oggettiService.update(o);
				graduatoriedcomDAO.flush();
				graduatoriedcomDAO.commit();
			    }
			}
			return risultato;
		    } catch (Exception e) {
			log.error("insertComunicazioneDettaglio#Errore creazione Allegato {} ({}) ", new Object[] { e.getMessage(), e });
			log.debug("insertComunicazioneDettaglio#Inserisco e commit  graduatoriedCom ....... ");
			this.insert(graduatoriedCom);
			graduatoriedcomDAO.flush();
			graduatoriedcomDAO.commit();
			log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
			StringBuffer message = new StringBuffer("Conversione in PDF dell'allegato del movimento: '")
				.append(movimento.getTipomovimento().getMovimento()).append("[")
				.append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
			insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento,
				message.toString());
			return inserimentoTerminatoPasso4;
		    }
		} else {
		    log.error("insertComunicazioneDettaglio#Errore in conversione Allegato lettera tipo non generata ");
		    log.debug("insertComunicazioneDettaglio#Inserisco e commit  graduatoriedCom ....... ");
		    this.insert(graduatoriedCom);
		    graduatoriedcomDAO.flush();
		    graduatoriedcomDAO.commit();
		    log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		    StringBuffer message = new StringBuffer("Conversione dell' allegato  del movimento: '")
			    .append(movimento.getTipomovimento().getMovimento()).append("[")
			    .append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		    insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento,
			    message.toString());
		    return inserimentoTerminatoPasso4;
		}
	    }
	}
	return risultato;
    }

    public int inserimentoAllegatoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	if (EntityUtils.getNestedProperty(graduatorietCom.getLetteretipo(), "id.codice") != null) {
	    graduatorietCom = this.graduatorietComService.findById(new PkId(graduatorietCom.getId().getCodice()));
	    log.debug("insertComunicazioneDettaglio#Creo allegato e lo inserisco nel movimento....");
	    try {
		// Invoco un URL ASP NET che crea l'allegato
		Movimenti mov = movimentiService.findById(movimento.getId());
		createAllegato(graduatorietCom, mov);
		log.debug("insertComunicazioneDettaglio#Recupero l'allegato creato....... ");
		// Devo recuperare l'allegato creato per inserire l'oggetto dell'allegato in graduatoriedCom
		Movimentiallegati movimentiallegati = movimentiallegatiService
			.findUltimoMovimentiallegatiConOggettoByMovimenti(movimento.getId().getCodice());
		if (movimentiallegati != null) {
		    graduatoriedCom.setOggetti(movimentiallegati.getOggetto());
		    this.update(graduatoriedCom);
		    graduatoriedcomDAO.flush();
		    graduatoriedcomDAO.commit();
		} else {
		    log.debug("insertComunicazioneDettaglio#Allegato non trovato, inserisco e commit  graduatoriedCom ....... ");
		    this.insert(graduatoriedCom);
		    graduatoriedcomDAO.flush();
		    graduatoriedcomDAO.commit();
		    log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		    StringBuffer message = new StringBuffer("Creazione allegato  del movimento: '")
			    .append(movimento.getTipomovimento().getMovimento()).append("[")
			    .append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		    insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento,
			    message.toString());
		    return inserimentoTerminatoPasso3;
		}
	    } catch (Exception e) {
		log.error("insertComunicazioneDettaglio#Errore creazione Allegato {} ({}) ", new Object[] { e.getMessage(), e });
		log.debug("insertComunicazioneDettaglio#Inserisco e commit  graduatoriedCom ....... ");
		this.insert(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		StringBuffer message = new StringBuffer("Creazione allegato  del movimento: '").append(movimento.getTipomovimento().getMovimento())
			.append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
		return inserimentoTerminatoPasso3;
	    }
	} else {
	    log.debug("insertComunicazioneDettaglio#Lettera tipo non presente per graduatoriet com: \"{}\"({})",
		    new Object[] { graduatorietCom.getDescrizione(), graduatorietCom.getId().getCodice() });
	    log.debug("insertComunicazioneDettaglio#Inserisco e commit  graduatoriedCom ....... ");
	    this.insert(graduatoriedCom);
	    graduatoriedcomDAO.flush();
	    graduatoriedcomDAO.commit();
	    //	    log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
	    //	    StringBuffer message = new StringBuffer("Creazione allegato  del movimento: '").append(movimento.getTipomovimento().getMovimento())
	    //		    .append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
	    //insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
	    return inserimentoTerminatoPasso3;
	}
	return risultato;
    }

    public int inserimentoMailInviataDalMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	Movimentimail movimentimail = createMovimentiMail(graduatorietCom, istanza, movimento, graduatoriedCom);
	if (movimentimail != null) {
	    graduatorietCom = this.graduatorietComService.findById(new PkId(graduatorietCom.getId().getCodice()));
	    if (StringUtils.isBlank(istanza.getDomicilioElettronico())) {
		//		log.error("Mail non creata per il movimento {}, impossibile inviarla, Domicilio elettroico non presente", movimento.getId()
		//			.getCodice());
		log.debug("insertComunicazioneDettaglio# Salvo e commit delle modifiche su graduatoriedCom");
		this.update(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		StringBuffer message = new StringBuffer("Invio email per  movimento: '").append(movimento.getTipomovimento().getMovimento())
			.append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
		return inserimentoTerminatoPasso6;
	    }
	    try {
		log.debug("insertComunicazioneDettaglio# Invio mail.........");
		List<Movimentimail> movimentimails = movimentimailService.findByMovimento(movimento);
		Movimentimail movimentiailDB = null;
		String mailInviata = "";
		if (movimentimails.isEmpty()) {
		    log.debug("insertComunicazioneDettaglio# movimenti non trovati invio la mail");
		    mailInviata = movimentimailService.sendMail2(movimentimail, null, null);
		    log.debug("insertComunicazioneDettaglio# mailinviata esito ......{}", mailInviata);
		} else {
		    log.debug("insertComunicazioneDettaglio# Inserisco il riferimento d movimenti mail {} in graduatoriedCom",
			    movimentimails.get(0).getId().getCodice());
		    graduatoriedCom.setMovimentimail(movimentimails.get(0));
		    // inserisco l'oggetto graduatoriedCom modificato
		    this.update(graduatoriedCom);
		    graduatoriedcomDAO.flush();
		    graduatoriedcomDAO.commit();
		    return risultato;
		}
		if (StringUtils.defaultIfEmpty(mailInviata, "").equalsIgnoreCase("ok")) {
		    log.debug("insertComunicazioneDettaglio# Aspetto 2 secondi perché il mail service in modo asincrono invia");
		    // la ricezione è asincrona quindi aspetto un paio di secondi
		    Thread.sleep(2000);
		}
		log.debug("insertComunicazioneDettaglio# Recupero le mail del movimento......");
		movimentimails = movimentimailService.findByMovimento(movimento);
		movimentiailDB = null;
		if (!movimentimails.isEmpty()) {
		    log.debug("insertComunicazioneDettaglio# movimenti trovati recupero la mail inviata");
		    movimentiailDB = movimentimails.get(0);
		} else {
		    log.debug("insertComunicazioneDettaglio#Mail non trovato, inserisco e commit dell'oggetto graduatoriedCom ....... ");
		    this.update(graduatoriedCom);
		    graduatoriedcomDAO.flush();
		    graduatoriedcomDAO.commit();
		    log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		    StringBuffer message = new StringBuffer("Invio email per  movimento: '").append(movimento.getTipomovimento().getMovimento())
			    .append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		    insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento,
			    message.toString());
		    return inserimentoTerminatoPasso6;
		}
		log.debug("insertComunicazioneDettaglio# Inserisco il riferimento d movimenti mail {} in graduatoriedCom",
			movimentiailDB.getId().getCodice());
		graduatoriedCom.setMovimentimail(movimentiailDB);
		// inserisco l'oggetto graduatoriedCom modificato
		this.update(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
	    } catch (Exception e) {
		log.error("Errore nell'invio della mail per il movimento {}", movimento.getId().getCodice());
		// Salvo e committo le modifiche di graduatoried
		this.update(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
		StringBuffer message = new StringBuffer("Invio email per  movimento: '").append(movimento.getTipomovimento().getMovimento())
			.append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
		insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
		return inserimentoTerminatoPasso6;
	    }
	} else {
	    log.error("Mail non creata per il movimento {}, impossibile inviarla", movimento.getId().getCodice());
	    log.debug("insertComunicazioneDettaglio# Salvo e commit delle modifiche su graduatoriedCom");
	    this.update(graduatoriedCom);
	    graduatoriedcomDAO.flush();
	    graduatoriedcomDAO.commit();
	    //	    log.debug("insertComunicazioneDettaglio# Inserimento evento per il movimento {}", movimento.getId().getCodice());
	    //	    StringBuffer message = new StringBuffer("Invio email per  movimento: '").append(movimento.getTipomovimento().getMovimento()).append("[")
	    //		    .append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
	    //	    insertEventoMovimento(graduatorietCom, graduatoriedCom.getGraduatoried().getId().getCodice(), istanza, movimento, message.toString());
	    return inserimentoTerminatoPasso6;
	}
	return risultato;
    }

    @Override
    public GraduatoriedCom insertComunicazioneDettaglio(GraduatorietCom graduatorietCom, GraduatoriedDTO graduatoriedDTO) {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////// GESTIONE GENERAZIONE MOVIMENTO DELLA COMUNICAZIONE //////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	GraduatoriedCom graduatoriedCom = populateBase(graduatorietCom, graduatoriedDTO);
	this.insert(graduatoriedCom);
	return graduatoriedCom;
    }

    @Override
    public int inserimentoMettiAllaFirmaPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimenti) {

	if (BooleanUtils.isTrue(graduatorietCom.getFlagMettiallafirma())) {
	    // allegato è stato generato
	    // inserire record su metti alla firma con operatore correntemente loggato e lista dei responsabili presi da lista firmatari
	    try {
		if (graduatoriedCom.getOggetti() == null) {
		    throw new RuntimeException(
			    "La comunicazione prevede la firma degli allegati generati ma non è generato correttamente l'allegato.");
		}
		Integer codiceOggetto = null;
		if (graduatoriedCom.getOggetti().getId() != null) {
		    if (graduatoriedCom.getOggetti().getId().getCodice() != null) {
			codiceOggetto = graduatoriedCom.getOggetti().getId().getCodice();
		    }
		}
		if (codiceOggetto == null) {
		    throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non è stato trovato il codiceoggetto");
		}
		if (StringUtils.isBlank(graduatorietCom.getListaFirmatari())) {
		    throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non sono stati definiti i firmatari");
		}
		Movimentiallegati movallegato = movimentiallegatiService
			.findMovimentiallegatiConOggettoByMovimenti(graduatoriedCom.getMovimenti().getId().getCodice(), codiceOggetto);
		if (movallegato == null) {
		    throw new RuntimeException(
			    "La comunicazione prevede la firma degli allegati generati ma non è stato trovato l'allegato del movimento");
		}
		List<Responsabili> resp = graduatorietComService.findResponsabiliFirmatari(graduatorietCom.getListaFirmatari());
		if (resp.isEmpty()) {
		    throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non sono stati trovati i firmatari");
		}
		boolean success = true;
		if (resp.isEmpty()) {
		    success = false;
		}
		for (Responsabili responsabili : resp) {
		    List<DocumentiDaFirmare> docs = documentiDaFirmareService.findByIdOggettoAndFirmatarioAndIstanza(codiceOggetto,
			    responsabili.getId().getCodice(), istanza.getId().getCodice());
		    if (!docs.isEmpty()) {
			for (DocumentiDaFirmare doc : docs) {
			    if (!documentiDaFirmareService.isFirmaCompleta(doc)) {
				success = false;
			    }
			}
		    } else {
			success = false;
			DocumentiDaFirmare doc = new DocumentiDaFirmare();
			doc.setDataRichiesta(Calendar.getInstance().getTime());
			doc.setFirmatario(responsabili);
			doc.setRichiedente((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
			doc.setOggetti(graduatoriedCom.getOggetti());
			doc.setIstanze(istanza);
			doc.setMovimentiallegati(movallegato);
			doc.setFlagLetto(Boolean.FALSE);
			documentiDaFirmareService.insert(doc, Boolean.FALSE);
		    }
		}
		if (success) {
		    return 0; //TUTTO OK
		} else {
		    throw new RuntimeException("Non tutti i documenti sono stati firmati");
		}
	    } catch (Exception e) {
		log.error("inserimentoMettiAllaFirmaPerLaComunicazione#" + e.getMessage());
		this.insert(graduatoriedCom);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		return inserimentoTerminatoPasso5;
	    }
	}
	return 0;
    }

    @Override
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatCom(Integer codiceGraduatoriatCom, Integer firstResult, Integer maxResult) {

	List<GraduatoriedComDTO> list = graduatoriedcomDAO.findGraduatoriedComDTOByGraduatoriatCom(codiceGraduatoriatCom, firstResult, maxResult);
	return list;
    }

    private Movimentimail createMovimentiMail(GraduatorietCom graduatorietCom, Istanze istanza, Movimenti movimento,
	    GraduatoriedCom graduatoriedCom) {

	log.debug("createMovimentiMail# Popolo l'oggetto movimento mail.....");
	Movimentimail movimentimail = null;
	//Istanze istanze = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
	log.debug("createMovimentiMail# Controllo se è impostato l'indirizzo email destinatario");
	if (EntityUtils.getNestedProperty(graduatorietCom.getMailtipo(), "id.codice") != null) {
	    graduatorietCom = this.graduatorietComService.findById(new PkId(graduatorietCom.getId().getCodice()));
	    movimentimail = new Movimentimail();
	    if (StringUtils.isNotBlank(istanza.getDomicilioElettronico())) {
		//movimentimail = new Movimentimail();
		log.debug("createMovimentiMail# Controllo se è configurarto email mittente");
		String emailMittente = "";
		MailConfig mailConfig = mailConfigService.findMailConfig();
		if (mailConfig != null) {
		    emailMittente = mailConfig.getSenderaddress();
		    movimentimail.setMittente(emailMittente);
		} else {
		    log.error(
			    "createMovimentiMail# Non è stato possibile recuperare il mittente per l'invio mail.Controllare la mailtipo utilizzata");
		}
		movimentimail.setDestinatario(istanza.getDomicilioElettronico());
		log.debug("createMovimentiMail# Popolo campo data.....");
		movimentimail.setDatainvio(graduatorietCom.getData());
		log.debug("createMovimentiMail# Popolo campo movimento.....");
		movimentimail.setMovimento(movimento);
		Mailtipo mailtipoReplace = mailtipoService.replaceOggettoCorpo(graduatorietCom.getMailtipo(), istanza, movimento);
		log.debug("createMovimentiMail# Popolo campo oggetto e corpo mail.....");
		if (StringUtils.isBlank(mailtipoReplace.getOggetto()) || StringUtils.isBlank(mailtipoReplace.getCorpo())) {
		    log.error(
			    "createMovimentiMail# Non è stato possibile generare l'oggetto o il corpo della mail, i campi sono obbligatori per l'invio mail. " +
			      "Controllare la mailtipo utilizzata {} ",
			    mailtipoReplace.getId().getCodice());
		    return null;
		} else {
		    movimentimail.setOggetto(mailtipoReplace.getOggetto());
		    movimentimail.setCorpo(mailtipoReplace.getCorpo());
		}
		Oggetti o = graduatoriedCom.getOggetti();
		if (o != null) {
		    // Devo recuperare l'allegato creato per inserire l'oggetto dell'allegato in graduatoriedCom
		    Movimentiallegati movimentiallegatis = movimentiallegatiService
			    .findMovimentiallegatiConOggettoByMovimenti(movimento.getId().getCodice(), o.getId().getCodice());
		    if (movimentiallegatis != null) {
			Set<Movimentimailallegati> movimentimailallegatis = new HashSet<Movimentimailallegati>();
			Movimentimailallegati movimentimailallegati = new Movimentimailallegati();
			movimentimailallegati.setMovimentimail(movimentimail);
			Oggetti oggetto = oggettiService.findById(new PkId(movimentiallegatis.getOggetto().getId().getCodice()));
			movimentimailallegati.setOggetto(oggetto);
			movimentimailallegati.setDocumento(movimentiallegatis.getDescrizione());
			movimentimailallegatis.add(movimentimailallegati);
			movimentimail.setMovimentimailallegatis(movimentimailallegatis);
		    }
		}
	    } else {
		log.error("Mail non creata per il movimento {}, impossibile inviarla, Domicilio elettroico non presente",
			movimento.getId().getCodice());
	    }
	}
	return movimentimail;
    }

    private GraduatoriedCom populateBase(GraduatorietCom graduatorietCom, GraduatoriedDTO graduatoriedDTO) {

	log.debug("populateBase# Popolo le proprietà base di GraduatoriedCom....... ");
	GraduatoriedCom graduatoriedCom = new GraduatoriedCom();
	//graduatoriedCom.setFlgProtocolla(graduatorietCom.getFlgProtocolla());
	log.debug("populateBase# Popolo la proprietà graduatoried.....");
	Graduatoried graduatoried = graduatoriedService.findById(new PkId(graduatoriedDTO.getId().getCodice()));
	graduatoriedCom.setGraduatoried(graduatoried);
	log.debug("populateBase# Popolo la proprietà graduatorietCom.....");
	graduatoriedCom.setGraduatorietCom(graduatorietCom);
	log.debug("populateBase# Proprietà base di GraduatoriedCom popolate....... ");
	return graduatoriedCom;
    }

    private Movimenti popolateMovimento(GraduatorietCom graduatorietCom, Istanze istanza) {

	log.debug("popolateMovimento# Inizio a popolare il movimento da inserire....");
	Movimenti movimento = new Movimenti();
	log.debug("popolateMovimento# Inserisco tipo movimento {} ({}).....",
		new Object[] { graduatorietCom.getTipimovimento().getMovimento(), graduatorietCom.getTipimovimento().getId().getTipomovimento() });
	movimento.setTipomovimento(graduatorietCom.getTipimovimento());
	//Istanze istanze = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
	log.debug("popolateMovimento# Inserisco l'istanza {} ({}).....", new Object[] { istanza.getNumeroistanza(), istanza.getId().getCodice() });
	movimento.setIstanza(istanza);
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	log.debug("popolateMovimento# Inserisco il responsabile {} ({})....",
		new Object[] { responsabile.getResponsabile(), responsabile.getId().getCodice() });
	movimento.setResponsabile(responsabile);
	log.debug("popolateMovimento# Inserisco la data {}.... ", new Object[] { Utilities.formatDate(graduatorietCom.getData(), false) });
	movimento.setData(graduatorietCom.getData());
	log.debug("popolateMovimento# Inserisco l'amministrazione inserimento {} ......", new Object[] { Utilities.formatDate(new Date(), false) });
	if (graduatorietCom.getAmministrazioni() != null) {
	    log.debug("popolateMovimento# Inserisco da maschera web l'amministrazione: '{}' ......",
		    new Object[] { Utilities.formatDate(new Date(), false) });
	    movimento.setAmministrazioni(graduatorietCom.getAmministrazioni());
	} else {
	    Verticalizzazioniparametri amministrazioneprotocollo = verticalizzazioniService.getVerticalizzazioniparametri(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT);
	    if (amministrazioneprotocollo != null && amministrazioneprotocollo.getValore() != null) {
		log.debug("popolateMovimento# Amminstrazione default protocollo configuarta");
		Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(amministrazioneprotocollo.getValore())));
		log.debug("popolateMovimento# Inserisco l' amministrazione di default del protocollo: '{}' ......",
			new Object[] { Utilities.formatDate(new Date(), false) });
		movimento.setAmministrazioni(amministrazioni);
	    } else {
		log.debug("popolateMovimento# Nessuna amministrazione di default impostata tra i parametri del protocollo");
	    }
	}
	log.debug("popolateMovimento# Inserisco data inserimento {} ......", new Object[] { Utilities.formatDate(new Date(), false) });
	movimento.setDatainserimento(new Date());
	log.debug("insertComunicazioneDettaglio# Movimento popolato......");
	return movimento;
    }

    private void createAllegato(GraduatorietCom graduatorietCom, Movimenti movimento) {

	if (EntityUtils.getNestedProperty(graduatorietCom.getLetteretipo(), "id.codice") != null) {
	    log.debug("createAllegato#Dal modello {} ({}) creo allegato e lo inserisco nel movimento....",
		    new Object[] { graduatorietCom.getLetteretipo().getDescrizione(), graduatorietCom.getLetteretipo().getId().getCodice() });
	    // Creo l'url che chiam il servizio ASP NET
	    String urlcreaallegato = documentMergeService.getUrlGeneraAllegato() + "?codiceDocumento=" +
				     graduatorietCom.getLetteretipo().getId().getCodice() + "&codiceIstanza=" +
				     movimento.getIstanza().getId().getCodice() + "&codiceMovimento=" + movimento.getId().getCodice() +
				     "&TipoMovimento=" + movimento.getTipomovimento().getId().getTipomovimento() + "&idcomunicazione=" +
				     graduatorietCom.getId().getCodice() + "&" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware() + "&" +
				     WebConstants.TOKEN + "=" + ORMHelper.getToken();
	    log.debug("createAllegato# Url che chiama il servizio: {}", urlcreaallegato);
	    // Recupero tramite HTTP cliet il contenuto della pagina e lo metto su uno stream	
	    HttpClient cli = new HttpClient();
	    HttpMethod method = null;
	    int status = 0;
	    try {
		log.debug("createAllegato#Invoco l'url.... ");
		method = new GetMethod(urlcreaallegato);
		status = cli.executeMethod(method);
		graduatoriedcomDAO.flush();
		graduatoriedcomDAO.commit();
		if (status == 200) {
		    log.debug("createAllegato#Allegato creato.Risposta server status: {} ", status);
		} else {
		    log.error("createAllegato#Errore: Allegato {} non generato  per il movimento {}. Risposta server status: {}",
			    new Object[] { graduatorietCom.getLetteretipo().getId().getCodice(), movimento.getId().getCodice(), status });
		    throw new RuntimeException();
		}
	    } catch (Exception e) {
		log.error("createAllegato#Errore: Allegato {} non generato  per il movimento {}",
			new Object[] { graduatorietCom.getLetteretipo().getId().getCodice(), movimento.getId().getCodice() });
		throw new RuntimeException();
	    }
	}
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// METODI PER LA GESTIONE DEGLI EVENTI /////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void insertEventoIstanzaInserimentoMovimentoDaComunicazione(GraduatorietCom graduatorietCom, Integer codiceGraduatoriaD,
	    Istanze istanze) {

	log.debug(
		"insertEventoInserimentoMovimentoDaComunicazione# Inserimento istanza evento movimento {} [{}] non inserito per il record i graduatoria d: {}  ",
		new Object[] { graduatorietCom.getTipimovimento().getMovimento(), graduatorietCom.getTipimovimento().getId().getTipomovimento(),
			codiceGraduatoriaD });
	Istanzeeventi istanzeeventi = new Istanzeeventi();
	istanzeeventi.setIstanze(istanze);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	StringBuffer messaggio = new StringBuffer("Non è stato possibile creare il movimento: '")
		.append(graduatorietCom.getTipimovimento().getMovimento()).append("[")
		.append(graduatorietCom.getTipimovimento().getId().getTipomovimento()).append("]' durante l'invio della comunicazione ")
		.append(graduatorietCom.getDescrizione());
	istanzeeventi.setDescrizione(messaggio.toString());
	istanzeeventi.setSoftware(istanze.getSoftware());
	istanzeeventiService.insert(istanzeeventi);
	log.warn(messaggio.toString());
    }

    private void insertEventoMovimento(GraduatorietCom graduatorietCom, Integer codicegraduatoriad, Istanze istanze, Movimenti movimento,
	    String message) {

	log.debug("insertEventoMovimento# Inserimento  evento nel  movimento {} [{}]  per il record i graduatoria d: {}  ",
		new Object[] { movimento, movimento.getTipomovimento().getId().getTipomovimento(),
			codicegraduatoriad });
	Istanzeeventi istanzeeventi = new Istanzeeventi();
	istanzeeventi.setIstanze(istanze);
	istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE);
	istanzeeventi.setData(Calendar.getInstance().getTime());
	istanzeeventi.setMovimenti(movimento);
	//	StringBuffer messaggio = new StringBuffer("Non è stato possibile creare il movimento ")
	//		.append(graduatorietCom.getTipimovimento().getMovimento()).append("[")
	//		.append(graduatorietCom.getTipimovimento().getId().getTipomovimento()).append("] durante l'invio della comunicazione ")
	//		.append(graduatorietCom.getDescrizione());
	istanzeeventi.setDescrizione(message);
	istanzeeventi.setSoftware(istanze.getSoftware());
	istanzeeventiService.insert(istanzeeventi);
	graduatoriedcomDAO.flush();
	graduatoriedcomDAO.commit();
	log.warn(message);
    }

    private void dataIntegration(GraduatoriedCom entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il GraduatoriedCom passato è nullo");
	}
	if (entity.getFlgProtocollato() == null) {
	    entity.setFlgProtocollato(false);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(GraduatoriedCom entity) {

	Graduatoried graduatoried = graduatoriedService.bindDomainObject(entity.getGraduatoried(), PkId.class, "id.codice");
	entity.setGraduatoried(graduatoried);
	GraduatorietCom graduatorietCom = graduatorietComService.bindDomainObject(entity.getGraduatorietCom(), PkId.class, "id.codice");
	entity.setGraduatorietCom(graduatorietCom);
	Movimenti movimenti = movimentiService.bindDomainObject(entity.getMovimenti(), PkId.class, "id.codice");
	entity.setMovimenti(movimenti);
	Movimentimail movimentimail = movimentimailService.bindDomainObject(entity.getMovimentimail(), PkId.class, "id.codice");
	entity.setMovimentimail(movimentimail);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
    }

    protected boolean isDeleteAllowed(GraduatoriedCom entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatComWithEvent(Integer codice, Integer firstResult, Integer maxResult) {

	List<GraduatoriedComDTO> list = graduatoriedcomDAO.findGraduatoriedComDTOByGraduatoriatCom(codice, firstResult, maxResult);
	for (GraduatoriedComDTO graduatoriedComDTO : list) {
	    // Controllo se c'è il movimento, se significa se ho un evento è sul movimento, altrimenti è sull'istanza
	    List<Istanzeeventi> istanzeeventis = new ArrayList<Istanzeeventi>();
	    IstanzeeventiFilter istanzeeventiFilter = new IstanzeeventiFilter();
	    //  Categorieeventibase categorieeventibase = categorieeventibaseService.findById(IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE);
	    // istanzeeventiFilter.setCategorieeventibase(categorieeventibase);
	    if (graduatoriedComDTO.getMovimenti() != null) {
		Movimenti movimento = movimentiService.findById(new PkId(graduatoriedComDTO.getMovimenti()));
		istanzeeventiFilter.setMovimenti(movimento);
		istanzeeventiFilter.setIstanze(movimento.getIstanza());
		istanzeeventis = istanzeeventiService.findByFilter(istanzeeventiFilter, 0, 1);
		if (!istanzeeventis.isEmpty()) {
		    graduatoriedComDTO.setIstanzeeventi(istanzeeventis.get(0));
		}
		Istanze istanza = istanzeService.findById(new PkId(graduatoriedComDTO.getGraduatoried().getIstanza().getId().getCodice()));
		istanzeeventiFilter.setIstanze(istanza);
		istanzeeventis = istanzeeventiService.findByFilter(istanzeeventiFilter, 0, 1);
		if (!istanzeeventis.isEmpty()) {
		    graduatoriedComDTO.setIstanzeeventi(istanzeeventis.get(0));
		}
	    }
	    // Gestione controllo accettazione e consegna mail nel caso sia stata impostata una mail da inviare
	    if (graduatoriedComDTO.getMovimentimail() != null) {
		List<Movimentimail> listMailFigle = movimentimailService.findByMailPadre(graduatoriedComDTO.getMovimentimail());
		for (Movimentimail movimentimail : listMailFigle) {
		    if (movimentimail.getOggetto().contains("ACCETTAZIONE")) {
			graduatoriedComDTO.setAccettata(true);
		    }
		    if (movimentimail.getOggetto().contains("CONSEGNA")) {
			graduatoriedComDTO.setConsegnata(true);
		    }
		}
	    }
	}
	return list;
    }

    @Override
    public List<GraduatoriedComHelper> findByGraduatoried(Integer codicegraduatoriad) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("graduatoriedId", codicegraduatoriad, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("data", "graduatorietCom"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "graduatorietCom"));
	List<GraduatoriedCom> l = graduatoriedcomDAO.findByFilterTable(ft, null, null);
	List<GraduatoriedComHelper> result = new ArrayList<GraduatoriedComHelper>();
	for (GraduatoriedCom graduatoriedCom : l) {
	    GraduatoriedComHelper g = new GraduatoriedComHelper();
	    g.setGraduatoriedCom(graduatoriedCom);
	    List<Movimentiallegati> movimentiAllegatis = movimentiallegatiService.findByMovimento(graduatoriedCom.getMovimenti().getId().getCodice());
	    g.setMovimentiAllegatis(movimentiAllegatis);
	    List<Tipimovimentodoctipo> tipiMovdoctipos = tipimovimentodoctipoService
		    .findByTipoMovimento(graduatoriedCom.getMovimenti().getTipomovimento().getId().getTipomovimento());
	    g.setTipiMovdoctipos(tipiMovdoctipos);
	    result.add(g);
	}
	return result;
    }
}
