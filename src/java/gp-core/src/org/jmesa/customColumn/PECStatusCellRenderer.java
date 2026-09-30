package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PecStatusHelper;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PECStatusCellRenderer extends HtmlCellRendererImpl {

    private static final Logger log = LoggerFactory.getLogger(PECStatusCellRenderer.class);
    public static final String OPERATORE_REQUEST_ATTRIBUTE = "operatore_corrente";
    public static final String ATTIVA_PROTOCOLLO_REQUEST_ATTRIBUTE = "attiva_protocollo";
    public static final String ATTIVA_PASSWORD_SBLOCCO_PEC = "pwd_sblocco_pec";

    @Override
    public Object render(Object item, int rowcount) {

	String propName = getColumn().getProperty();
	Object propVal = getCellEditor().getValue(item, propName, rowcount);
	PECMessageHelper pecMsg = (PECMessageHelper) item;
	Responsabili operatore = (Responsabili) getWebContext().getRequestAttribute(OPERATORE_REQUEST_ATTRIBUTE);
	Boolean protocolloAttivo = (Boolean) getWebContext().getRequestAttribute(ATTIVA_PROTOCOLLO_REQUEST_ATTRIBUTE);
	Boolean pwdSbloccoPecAttiva = (Boolean) getWebContext().getRequestAttribute(ATTIVA_PASSWORD_SBLOCCO_PEC);
	Boolean flussoEntrataAttivo = Boolean.FALSE;
	Set<ProtocolloFlusso> flussi = operatore.getProtocolloFlussos();
	for (ProtocolloFlusso flusso : flussi) {
	    if (flusso.getCodice().equals(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		flussoEntrataAttivo = Boolean.TRUE;
		break;
	    }
	}
	CustomHtmlBuilder html = new CustomHtmlBuilder();
	html.td(1);
	html.close();
	PecStatusHelper pecStatus = null;
	try {
	    pecStatus = (PecStatusHelper) propVal;
	} catch (Exception e) {
	    log.error("render() - il valore della proprietà {} non è di tipo PecStatusHelper. Impossibile renderizzare la cella di Jmesa table.");
	}
	if (pecStatus != null) {
	    String jsVoid = "javascript:void(0)";
	    StringBuilder sb = null;
	    html.div();
	    html.close();
	    html.a().name("ancora_pec_id_" + pecMsg.getIdentificativo()).close();
	    //	    Responsabili responsabilePEC = null;
	    boolean isAssegnataAMe = false;
	    //icona dell'utente che ha iin carico l'eleborazione della PEC
	    if (pecStatus.isAssegnata()) {
		if (operatore != null && operatore.getId() != null) {
		    isAssegnataAMe = operatore.getId().getCodice().equals(pecStatus.getIdOperatore());
		}
		sb = new StringBuilder("In carico a: ").append(pecStatus.getOperatore());
		StringBuilder sbJsCall = new StringBuilder("rilasciaPEC('").append(pecMsg.getIdentificativo());
		if (isAssegnataAMe) {
		    sbJsCall.append("',false)");
		    html.a().href(jsVoid).onclick(sbJsCall.toString()).close();
		} else if (pwdSbloccoPecAttiva) {
		    sbJsCall.append("',").append(pwdSbloccoPecAttiva).append(")");
		    html.a().href(jsVoid).onclick(sbJsCall.toString()).close();
		}
		html.img().src("../images/bob.png").title(sb.toString()).close();
		if (isAssegnataAMe || pwdSbloccoPecAttiva) {
		    html.aEnd();
		}
	    }
	    String cannotEditReason = pecStatus.isAssegnata() && !isAssegnataAMe ? "perché è già in carico ad un altro operatore" : "";
	    if (StringUtils.isEmpty(cannotEditReason)) {
		cannotEditReason = pecMsg.getProcessato() ? "perché il messaggio è già stato elaborato dal processamento automatico" : "";
		if (StringUtils.isEmpty(cannotEditReason)) {
		    cannotEditReason = pecStatus.getCancellata() ? "perché il messaggio originale è stato cancellato dal server di posta" : "";
		}
	    }
	    if (protocolloAttivo && flussoEntrataAttivo) {
		//lilnk al dettaglio del protocollo o alla funzione di protocollazione
		if (pecStatus.hasProtocollo()) {
		    html.a();
		    sb = new StringBuilder("visualizzaProtocolloPec('");
		    sb.append(pecMsg.getIdentificativo()).append("');");
		    //link all'url dettaglio del protocollo esistenete
		    html.href(jsVoid).onclick(sb.toString()).close();
		    sb = new StringBuilder("prot. N. ");
		    sb.append(pecStatus.getNumeroProtocollo());
		    sb.append(" del ").append(DateFormatUtils.format(pecStatus.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN));
		    html.img().src("../images/protocollo_s.gif").style("vertical-align:middle;");
		    html.title(sb.toString()).close();
		    html.aEnd();
		} else {
		    if (!pecStatus.isAssegnata() || isAssegnataAMe) {
			if (!pecMsg.getProcessato()
				&& (!pecStatus.getCancellata() || (pecStatus.getCancellata() && pecStatus.getAllegatoProtocolloPresente()))) {
			    html.a();
			    sb = new StringBuilder("protocollaPec('");
			    sb.append(pecMsg.getIdentificativo()).append("','").append(pecMsg.getIdAccountMailcfg().toString()).append("');");
			    html.href(jsVoid).onclick(sb.toString()).close();
			    html.img().src("../images/protocollo_n.gif").style("vertical-align:middle;");
			    html.title("protocolla la PEC").close();
			    html.aEnd();
			} else {
			    html.img().src("../images/protocollo_dis.gif").style("vertical-align:middle;");
			    html.title("impossibile protocollare la PEC " + cannotEditReason).close();
			}
		    } else {
			html.img().src("../images/protocollo_dis.gif").style("vertical-align:middle;");
			html.title("impossibile protocollare la PEC " + cannotEditReason).close();
		    }
		}
	    }
	    //lilnk al dettaglio dell'istanza o alla funzione di creazione rapida istanza
	    if (pecStatus.hasIstanza()) {
		html.a();
		sb = new StringBuilder("visualizzaIstanzaDaPec(");
		sb.append(pecStatus.getIdIstanza()).append(",'").append(StringUtils.defaultString(pecStatus.getSoftware())).append("','")
			.append(pecMsg.getIdentificativo()).append("');");
		//link all'url dettaglio dell'istanza
		html.href(jsVoid).onclick(sb.toString()).close();
		sb = new StringBuilder("istanza N. ");
		sb.append(pecStatus.getCodiceIstanza());
		html.img().src("../images/istanza_s.gif").style("vertical-align:middle;");
		html.title(sb.toString()).close();
		html.aEnd();
	    } else if (!pecStatus.hasMovimento()) {
		if (!pecStatus.isAssegnata() || isAssegnataAMe) {
		    if (!pecMsg.getProcessato() && (!pecStatus.getCancellata() || (pecStatus.getCancellata() && pecStatus.getAllegatiPresenti()))) {
			html.a();
			sb = new StringBuilder("creaIstanzaDaPec('");
			sb.append(pecMsg.getIdentificativo()).append("','").append(pecMsg.getIdAccountMailcfg().toString()).append("');");
			//link alla funzione di creazione istanza
			html.href(jsVoid).onclick(sb.toString()).close();
			html.img().src("../images/istanza_n.gif").style("vertical-align:middle;");
			html.title("crea un'istanza dalla PEC").close();
			html.aEnd();
		    } else {
			html.img().src("../images/istanza_dis.gif").style("vertical-align:middle;");
			html.title("impossibile creare l'istanza dalla PEC " + cannotEditReason).close();
		    }
		} else {
		    html.img().src("../images/istanza_dis.gif").style("vertical-align:middle;");
		    html.title("impossibile creare l'istanza dalla PEC " + cannotEditReason).close();
		}
	    }
	    //lilnk al dettaglio del movimento o alla funzione di creazione del movimento
	    if (pecStatus.hasMovimento()) {
		html.a();
		sb = new StringBuilder("visualizzaMovimentoDaPec('");
		sb.append(pecStatus.getIdMovimento()).append("','").append(StringUtils.defaultString(pecStatus.getSoftware())).append("','")
			.append(pecMsg.getIdentificativo()).append("');");
		//link al dettaglio del movimento
		html.href(jsVoid).onclick(sb.toString()).close();
		sb = new StringBuilder("movimento: ");
		sb.append(pecStatus.getCodiceMovimento());
		html.img().src("../images/movimento_s.gif").style("vertical-align:middle;");
		html.title(sb.toString()).close();
		html.aEnd();
	    } else if (!pecStatus.hasIstanza()) {
		if (!pecStatus.isAssegnata() || isAssegnataAMe) {
		    if (!pecMsg.getProcessato() && (!pecStatus.getCancellata() || (pecStatus.getCancellata() && pecStatus.getAllegatiPresenti()))) {
			html.a();
			sb = new StringBuilder("creaMovimentoDaPec('");
			sb.append(pecMsg.getIdentificativo()).append("','").append(pecMsg.getIdAccountMailcfg().toString()).append("');");
			//link alla funzione di creazione movimento
			html.href(jsVoid).onclick(sb.toString()).close();
			html.img().src("../images/movimento_n.gif").style("vertical-align:middle;");
			html.title("crea un movimento dalla PEC").close();
			html.aEnd();
		    } else {
			html.img().src("../images/movimento_dis.gif").style("vertical-align:middle;");
			html.title("impossibile creare il movimento dalla PEC " + cannotEditReason).close();
		    }
		} else {
		    html.img().src("../images/movimento_dis.gif").style("vertical-align:middle;");
		    html.title("impossibile creare il movimento dalla PEC " + cannotEditReason).close();
		}
	    }
	    //////////////////////////////////  REDMINE BUG #682 //////////////////////////////////////////////////
	    // Controllo lo stato della PEC, se è impostato come cancellato aggingo un icona nella sezione "Azioni"
	    // che mi permette di andare a mofificare il valore del campo PecInbox.flagcancellata da 1 a 0
	    if (pecStatus.getCancellata()) {
		html.a();
		sb = new StringBuilder("segnaPecComeNonCancellata('");
		sb.append(pecMsg.getIdentificativo()).append("');");
		//link alla funzione di creazione movimento
		html.href(jsVoid).onclick(sb.toString()).close();
		html.img().src("../images/clear.png").style("vertical-align:middle;");
		html.title("Segna PEC come non cancellata").close();
	    }
	    html.divEnd();
	} else {
	    html.append("Stato della PEC non disponibile.");
	}
	html.tdEnd();
	return html.toString();
    }
}
