package it.gruppoinit.pal.gp.backoffice.aop.istanzeeventi;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class IstanzeeventiAspect {

    private static final Logger log = LoggerFactory.getLogger(IstanzeeventiAspect.class);
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiService movimentiService;

    /**
     * il metodo gestisce gli eventi legati alla produzione degli allegati del mov secondo DPR160. Se l'istanza non è
     * protocollata inserisce un evento, se è protocollata ma non è presente il p7m inserisce un secondo evento, se è
     * protocollata ed il p7m è presente segna tutti gli eventi come letti.
     * 
     * @param movimentiallegati
     */
    public void gestioneEventiMovDPR160(JoinPoint jp) {

	Object[] args = jp.getArgs();
	Integer codMov = null;
	for (Object arg : args) {
	    if (arg instanceof Integer) {
		codMov = (Integer) arg;
		break;
	    }
	}
	if (codMov != null) {
	    Movimenti mov = movimentiService.findById(new PkId(codMov));
	    if (mov != null && isMovDPR160(mov)) {
		if (movimentiService.isEffettuato(mov)) {
		    if (StringUtils.isBlank(mov.getIstanza().getNumeroprotocollo()) && mov.getIstanza().getDataprotocollo() == null) {
			istanzeeventiService.insert("Non è possibile generare la ricevuta come da DPR 160 prima che sia protocollata l'istanza.",
				IstanzeeventiConstants.CATEGORIA_FIRMA, mov, null);
		    } else {
			Set<Movimentiallegati> movimentiallegatis = mov.getMovimentiallegatis();
			if (movimentiallegatis != null && !movimentiallegatis.isEmpty()) {
			    boolean isP7m = false;
			    for (Movimentiallegati movimentiallegati : movimentiallegatis) {
				if (EntityUtils.getNestedProperty(movimentiallegati, "oggetto.id.codice") != null) {
				    String nomeFile = movimentiallegati.getOggetto().getNomefile();
				    if ((nomeFile.indexOf("SUAP") != -1) && (nomeFile.indexOf(".p7m") != -1)) {
					istanzeeventiService.updateSegnaComeLettoTutti(IstanzeeventiConstants.CATEGORIA_FIRMA, mov);
					isP7m = true;
					break;
				    }
				}
			    }
			    if (!isP7m) {
				istanzeeventiService.insert("Firmare digitalmente il documento pdf ed allegarlo per invio tramite mail",
					IstanzeeventiConstants.CATEGORIA_FIRMA, mov, null);
			    }
			}
		    }
		}
	    }
	}
    }

    private boolean isMovDPR160(Movimenti mov) {

	boolean isDPR160 = false;
	if ((EntityUtils.getNestedProperty(mov.getTipomovimento().getMailtipoByFkTipimovricTelMailtipo(), "id.codice") != null)
		|| (EntityUtils.getNestedProperty(mov.getTipomovimento().getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") != null)) {
	    isDPR160 = true;
	}
	return isDPR160;
    }

    /**
     * Il metodo inserisce un evento di categoria {@link IstanzeeventiConstants#CATEGORIA_STC} all'inserimento di una
     * pratica proveniente da STC con testo {@link IstanzeeventiConstants#STC_MESSAGGIO_NUOVA_PRATICA}
     * 
     * @param retVal
     */
    public void inserisciEventiStcNuovaPratica(InserimentoPraticaNLAResponse retVal) {

	if (retVal != null) {
	    // 1. recuperare l'idPratica (codiceistanza da retVal.getDettaglioPratica().getIdPratica())
	    if (EntityUtils.isNestedPropertyBlank(retVal, "dettaglioPratica.idPratica") == false) {
		Integer codiceistanza = null;
		try {
		    codiceistanza = Integer.valueOf(retVal.getDettaglioPratica().getIdPratica());
		} catch (Exception e) {
		    log.error("inserisciEventiStcNuovaPratica: idPratica non è un numero valido {}", retVal.getDettaglioPratica().getIdPratica());
		    return;
		}
		// 2. eseguire una findById su istanzeService con quel codice istanza trovato
		Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
		if (istanza != null) {
		    // 3. inserire l'evento di categoria STC e descrizione "Nuova pratica proveniente da STC"
		    try {
			istanzeeventiService.insert(IstanzeeventiConstants.STC_MESSAGGIO_NUOVA_PRATICA, IstanzeeventiConstants.CATEGORIA_STC, null,
				istanza);
		    } catch (Exception e) {
			log.error("inserisciEventiStcNuovaPratica: {}", e.getMessage());
		    }
		}
	    }
	}
    }

    /**
     * Il metodo inserisce un evento di categoria {@link IstanzeeventiConstants#CATEGORIA_STC} all'inserimento di una
     * attività proveniente da STC con testo {@link IstanzeeventiConstants#STC_MESSAGGIO_NUOVA_ATTIVITA}
     * 
     * @param retVal
     */
    public void inserisciEventiStcNuovaAttivita(InserimentoAttivitaNLAResponse retVal) {

	if (retVal != null) {
	    if (EntityUtils.isNestedPropertyBlank(retVal, "dettaglioAttivita.idAttivita") == false) {
		// 1. recuperare l'idAttivita (codicemovimento da retVal.getDettaglioAttivita().getIdAttivita())
		Integer codicemovimento = null;
		try {
		    codicemovimento = Integer.valueOf(retVal.getDettaglioAttivita().getIdAttivita());
		} catch (Exception e) {
		    log.error("inserisciEventiStcNuovaAttivita: idAttivita non è un numero valido {}", retVal.getDettaglioAttivita().getIdAttivita());
		    return;
		}
		// 2. eseguire una findById su movimentiService con quel codice istanza trovato	   
		Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
		if (movimento != null) {
		    if (movimentiService.isEffettuato(movimento)) {
			// 3. inserire l'evento di categoria STC e descrizione "Nuova attività proveniente da STC"
			try {
			    istanzeeventiService.insert(IstanzeeventiConstants.STC_MESSAGGIO_NUOVA_ATTIVITA, IstanzeeventiConstants.CATEGORIA_STC,
				    movimento, null);
			} catch (Exception e) {
			    log.error("inserisciEventiStcNuovaAttivita: {}", e.getMessage());
			}
		    }
		}
	    }
	}
    }

    /**
     * Il metodo inserisce un evento di categoria {@link IstanzeeventiConstants#CATEGORIA_STC} e testo
     * {@link IstanzeeventiConstants#STC_MESSAGGIO_ATTIVITA_DA_NOTIFICARE} all'inserimento di un movimento che ha il
     * flag inviatoConStc={@link MovimentiService#STC_INVIATO}
     * 
     * @param retVal
     */
    public void insertEventoMovimentoStcDaNotificare(Movimenti movimento) {

	//	if (movimento != null) {
	//	    if (EntityUtils.isNestedPropertyBlank(movimento, "id.codice") == false) {
	//		Integer codicemovimento = movimento.getId().getCodice();
	//		// eseguire una findById su movimentiService con quel codice istanza trovato	   
	//		movimento = movimentiService.findById(new PkId(codicemovimento));
	//		if (movimento != null) {
	//		    if (movimentiService.isEffettuato(movimento)) {
	//			// 1. verificare che il movimento inserito abbia configurato il flag movimento.getTipomovimento().getFlagStc()=true
	//			boolean daNotificare = movimento.getInviatoConStc() == null ? false : movimento.getInviatoConStc().booleanValue();
	//			if (daNotificare) {
	//			    try {
	//				// 1.2 se true allora inserire un evento di categoria STC per quel movimento
	//				istanzeeventiService.insert(IstanzeeventiConstants.STC_MESSAGGIO_ATTIVITA_DA_NOTIFICARE,
	//					IstanzeeventiConstants.CATEGORIA_STC, movimento, null);
	//			    } catch (Exception e) {
	//				log.error("insertEventoMovimentoStcDaNotificare: {}", e.getMessage());
	//			    }
	//			}
	//		    }
	//		}
	//	    }
	//	}
    }

    /**
     * 
     * FIXME NON CORRETTO!!! il metodo deve aggiornare il movimento che si notifica non quello notificato Il metodo
     * aggiorna un evento di categoria {@link IstanzeeventiConstants#CATEGORIA_STC} alla notifica di un attività verso
     * STC
     * 
     * @param retVal
     */
    public void updateEventoMovimentoStcNotificato(String idAttivita) {

	//	if (StringUtils.isNotBlank(idAttivita)) {
	//	    Integer codiceMovimento = null;
	//	    try {
	//		codiceMovimento = Integer.valueOf(idAttivita);
	//	    } catch (Exception e) {
	//		log.error("updateEventoMovimentoStcNotificato: {}", e.getMessage());
	//		return;
	//	    }
	//	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	//	    if (movimento != null) {
	//		if (movimentiService.isEffettuato(movimento)) {
	//		    try {
	//			istanzeeventiService.updateSegnaComeLettoTutti(IstanzeeventiConstants.CATEGORIA_STC, movimento);
	//		    } catch (Exception e) {
	//			log.error("updateEventoMovimentoStcNotificato: {}", e.getMessage());
	//		    }
	//		}
	//	    }
	//	}
    }
}
