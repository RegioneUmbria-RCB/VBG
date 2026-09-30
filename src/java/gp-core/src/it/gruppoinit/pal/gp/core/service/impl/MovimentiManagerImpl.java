package it.gruppoinit.pal.gp.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiManager;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

@Service
public class MovimentiManagerImpl implements MovimentiManager {

    private static final Logger log = LoggerFactory.getLogger(MovimentiManagerImpl.class);
    private MovimentiService movimentiService;
    private ProtocollazioneService protocollazioneService;
    private IstanzeeventiService istanzeeventiService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Override
    public MovimentiService getMovimentiService() {

	return this.movimentiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public void insert(Movimenti entity, boolean eseguiOperazioniProtocollo) {

	movimentiService.insert(entity);
	if (eseguiOperazioniProtocollo) {
	    entity = movimentiService.findById(entity.getId());
	    if (log.isDebugEnabled()) {
		log.debug("insert: procedo alla protocollazione del movimento dell'istanza {}", entity.getIstanza().getId());
	    }
	    try {
		DatiProtocolloResponseType dp = protocollazioneService.protocollaMovimento(entity, ORMHelper.getToken());
		if (dp == null) {
		    log.warn("Movimento protocollato per l'istanza codIstanza {}", entity.getIstanza().getId());
		    // Movimento non protocollato
		} else {
		    if (log.isDebugEnabled()) {
			log.debug("Movimento protocollato per l'istanza codIstanza {}", entity.getIstanza().getId());
		    }
		    String numProtMov = dp.getNumeroProtocollo();
		    String dataProtMov = dp.getDataProtocollo();
		    Date dataprotocollo = Utilities.parseDateString(dataProtMov, false);
		    entity.setNumeroprotocollo(numProtMov);
		    entity.setDataprotocollo(dataprotocollo);
		    entity.setFkidprotocollo(dp.getIdProtocollo());
		}
		protocollazioneService.fascicolaMovimento(ORMHelper.getToken(), entity);
	    } catch (Exception e) {
		log.error("insert: {}", e.getMessage());
		//
	    }
	}
    }

    @Override
    public void update(Movimenti entity) {

	if (entity.getData() != null) {
	    Movimenti copy = movimentiService.findById(new PkId(entity.getId().getCodice()));
	    try {
		checkProtocollo(entity, copy);
	    } catch (Exception e) {
		log.error("update: {}", e.getMessage());
	    }
	}
	movimentiService.update(entity);
	if (entity.getChangeNote()) {
	    Responsabili responsabili = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    String descResponsabile = (String) EntityUtils.getNestedProperty(responsabili, "responsabile");
	    String descrizioneMovimento = entity.toString();
	    LoggerCancellazioni.logAggiornaNoteMovimentoStc(descResponsabile, descrizioneMovimento);
	}
    }

    /**
     * Controllo se i dati di protocollazione sono stati modificati. Se si allora devo resettare il campo
     * fkidprotocollo, ricalcolarlo e aggiornare il campo fkidprotocollo dell'entity
     * 
     * @param entity
     * @param dbCopy
     */
    private void checkProtocollo(Movimenti entity, Movimenti dbCopy) {

	if (entity == null || dbCopy == null) {
	    return;
	}
	String codiceComune = dbCopy.getIstanza().getComune().getCodicecomune();
	String numeroprotocollo = StringUtils.defaultIfEmpty(entity.getNumeroprotocollo(), "");
	Date dataProtocollo = entity.getDataprotocollo();
	String oldNumeroprotocollo = StringUtils.defaultIfEmpty(dbCopy.getNumeroprotocollo(), "");
	Date oldDataProtocollo = dbCopy.getDataprotocollo();
	boolean isDataToCheck = false;
	if (dataProtocollo != null && oldDataProtocollo != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    String data = sdf.format(dataProtocollo);
	    String oldData = sdf.format(oldDataProtocollo);
	    if (!data.equals(oldData)) {
		isDataToCheck = true;
	    }
	} else {
	    if (dataProtocollo == null && oldDataProtocollo != null) {
		isDataToCheck = true;
	    }
	    if (!isDataToCheck) {
		if (dataProtocollo != null && oldDataProtocollo == null) {
		    isDataToCheck = true;
		}
	    }
	}
	// se la data di protocollazione è cambiata
	if ((!numeroprotocollo.equals(oldNumeroprotocollo)) || isDataToCheck) {
	    String messaggio = getMessaggioProtocolloModificato(dbCopy, numeroprotocollo, oldNumeroprotocollo, dataProtocollo, oldDataProtocollo);
	    LoggerModificheIstanze.log(messaggio);
	    if (protocollazioneService.isLeggiProtocollo(codiceComune)) {
		try {
		    DatiProtocolloLettoResponseType dp = protocollazioneService.leggiProtocollo(ORMHelper.getToken(), entity);
		    entity.setFkidprotocollo(dp.getIdProtocollo());
		} catch (RuntimeException e) {
		    log.error("checkProtocollo: {}", e.getMessage());
		    List<String> warnings = new ArrayList<String>();
		    warnings.add(e.getMessage());
		    if (e instanceof BaseValidationException) {
			List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
			for (InvalidValue invalidValue : ivs) {
			    warnings.add(invalidValue.getMessage());
			}
		    }
		    String descrizioneEvento = "";
		    for (String warning : warnings) {
			descrizioneEvento = descrizioneEvento.concat(warning).concat("\n");
		    }
		    try {
			istanzeeventiService.insert(descrizioneEvento, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, entity, null);
		    } catch (Exception ex) {
			log.error("gestProtocolloEFascicolo: non è stato possibile inserire l'evento a causa di={}", ex.getMessage());
		    }
		    FlashMessages.setWarnings(warnings);
		}
	    }
	}
    }
    //    @Override
    //    public void gestNotificaAutomatica(Movimenti entity) {
    //
    //	MovimentoDaNotificare isNotificaSTC = movimentiService.isMovimentoDaNotificareSTC(entity);
    //	// 1. verifica pa[rametri NOTIFICA STC
    //	if (isNotificaSTC.equals(MovimentoDaNotificare.AUTOMATICA)) {
    //	    // movimentiDAO.commit();
    //	    entity = movimentiService.findById(entity.getId());
    //	    // 2. CREAZIONE DEGLI ALLEGATI CONFIGURATI PER IL MOVIMENTO
    //	    log.debug("gestNotificaStc: creazione dell'allegato");
    //	    int allegati = movimentiallegatiService.countByMovimento(entity.getId().getCodice());
    //	    boolean eseguiNotifica = true;
    //	    if (allegati == 0) {
    //		String tipoMovimento = (String) EntityUtils.getNestedProperty(entity.getTipomovimento(), "id.tipomovimento");
    //		if (StringUtils.isNotBlank(tipoMovimento)) {
    //		    List<Tipimovimentodoctipo> tipimovimentodoctipos = tipimovimentodoctipoService.findByTipoMovimento(tipoMovimento);
    //		    for (Tipimovimentodoctipo tipimovimentodoctipo : tipimovimentodoctipos) {
    //			Integer codiceLettera = (Integer) EntityUtils.getNestedProperty(tipimovimentodoctipo, "id.codicelettera");
    //			if (codiceLettera != null) {
    //			    Letteretipo lettera = letteretipoService.findById(new PkId(codiceLettera));
    //			    if (lettera != null) {
    //				Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(lettera.getOggetto(), "id.codice");
    //				if (codiceOggetto != null) {
    //				    // TODO Invocare il servizio di creazione allegato
    //				    // in caso di errore eseguiNotifica = false;
    //				}
    //			    }
    //			}
    //		    }
    //		}
    //	    }
    //	    // 3. PROTOCOLLAZIONE DEL MOVIMENTO
    //	    log.debug("gestNotificaStc: protocollazione del movimento");
    //	    try {
    //		protocollazioneService.protocollaMovimento(entity, ORMHelper.getToken());
    //	    } catch (Exception e) {
    //		try {
    //		    String messaggioDiErrore = "[" + Utilities.getOrariosistema() + "] Errore nella protocollazione automatica. Errore:"
    //			    + e.getMessage();
    //		    istanzeeventiService.insert(StringUtils.left(messaggioDiErrore, 4000), IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, entity, null);
    //		} catch (Exception ex) {
    //		    log.error("gestNotificaStc: non è stato possibile inserire l'evento a causa di={}", ex.getMessage());
    //		    eseguiNotifica = false;
    //		}
    //	    }
    //	    // 4. NOTIFICA TRAMITE STC
    //	    log.debug("gestNotificaStc: notifica dell'attività");
    //	    if (eseguiNotifica) {
    //		try {
    //		    entity = movimentiService.findById(entity.getId());
    //		    stcService.notificaAutomaticaAttivita(entity.getId().getCodice());
    //		} catch (Exception e) {
    //		    try {
    //			String messaggioDiErrore = "[" + Utilities.getOrariosistema() + "] Errore nella notifica della attività. Errore: "
    //				+ e.getMessage();
    //			istanzeeventiService.insert(StringUtils.left(messaggioDiErrore, 4000), IstanzeeventiConstants.CATEGORIA_STC_IA, entity, null);
    //		    } catch (Exception ex) {
    //			log.error("gestNotificaStc: non è stato possibile inserire l'evento a causa di={}", ex.getMessage());
    //		    }
    //		}
    //	    }
    //	}
    //    }

    private String getMessaggioProtocolloModificato(Movimenti movimento, String numeroprotocollo, String oldNumeroprotocollo, Date dataProtocollo,
	    Date oldDataProtocollo) {

	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String message = "#MODIFICA_PROTOCOLLO_MOVIMENTO# L''Operatore {0} in data {1} ha modificato le informazioni di protocollo del movimento {2}. Il numero di protocollo precedente {3} è stato sostituito con {4}" +
			 ", la data di protocollo precedente {5} è stata sostituita con {6}";
	return Utilities.formatMessage(message, responsabile, Utilities.getToday(true), movimento, oldNumeroprotocollo, numeroprotocollo,
		oldDataProtocollo, dataProtocollo);
    }
}
