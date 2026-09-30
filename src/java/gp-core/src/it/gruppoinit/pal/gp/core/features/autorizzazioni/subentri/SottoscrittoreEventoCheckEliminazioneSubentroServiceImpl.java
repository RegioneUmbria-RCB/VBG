package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckEliminazioneSubentro;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.presenze.IVerificaModificaAutorizzazioniSuPresenzeService;

@Service
public class SottoscrittoreEventoCheckEliminazioneSubentroServiceImpl implements IEventSubscriber<EventoCheckEliminazioneSubentro> {

    //    @Autowired
    //    private AutorizzazioniSubentriDAO autorizzazioniSubentriDAO;
    //    @Autowired
    //    private MercatipresenzeDDAO mercatipresenzeDDAO;
    @Autowired
    private IVerificaModificaAutorizzazioniSuPresenzeService presenzeService;

    /**
     * <pre>
     *  CASO D'USO: SUBENTRO DA MENDICHI A LEROY IN DATA 16/05
     
    -----------
    PRESENZA
    01/05 MENDICHI
    08/05 MENDICHI
    15/05 MENDICHI
    22/05 LEROY
    29/05 LEROY
    ----------
    
    CANCELLO IL SUBENTRO
    -> SE LEROY NON HA FATTO PRESENZE A PARTIRE DAL 16/05
          -> OK
    -> SE LEROY HA FATTO PRESENZE A PARTIRE DAL 16/05
         -> KO: NON POSSO CANCELLARE IL SUBENTRO IN QUANTO LEROY E' STATO SEGNATO PRESENTE ALLA MANIFESTAZIONE CON QUELL'AUTORIZZAZIONE
     * </pre>
     */
    @Override
    public void onEvent(EventoCheckEliminazioneSubentro e) throws EventAbortedException {

	EsitoElaborazioneEvento checkPosso = presenzeService.checkPossoEliminareUltimoPassaggioSubentriAutConc(e.getIdSubentroDaEliminare());
	if (checkPosso.isErroreOWarning()) {
	    throw new EventAbortedException(checkPosso, e);
	}
	//	AutorizzazioniSubentri autSub = autorizzazioniSubentriDAO.findById(new PkId(e.getIdSubentroDaEliminare()));
	//	List<MercatipresenzeDBean> presenze = mercatipresenzeDDAO
	//		.findPresenzePerAutorizzazioneDallaData(autSub.getAutorizzazioni().getId().getCodice(), autSub.getDataCessazione());
	//	Integer codiceOccupanteAutAttuale = autSub.getAutorizzazioni().getOccupante().getId().getCodice();
	//	Integer idAutOConc = autSub.getAutorizzazioni().getId().getCodice();
	//	StringBuilder presenzeEffettuate = new StringBuilder();
	//	for (MercatipresenzeDBean presenza : presenze) {
	//	    Integer idAutorizzazionePresenza = presenza.getIdautpresenza();
	//	    if (idAutOConc.equals(idAutorizzazionePresenza) // solo se presente
	//		    && codiceOccupanteAutAttuale.equals(presenza.getCodiceoccupante()) // se chi ha preso la presenza
	//		    && !codiceOccupanteAutAttuale.equals(autSub.getOccupante().getId().getCodice())) { // sull'ultimo passaggio il codice anagrafe potrebbe essere lo stesso allora posso fare la modifica
	//		presenzeEffettuate.append("\n<br/> - ").append(getInfoPresenza(presenza));
	//	    }
	//	}
	//	if (presenzeEffettuate.length() != 0) {
	//	    String messaggioErrore = "Non è possibile cancellare l'ultimo passaggio dell'atto in quanto ci sono presenze assegnate all'anagrafica " + //
	//				     autSub.getAutorizzazioni().getOccupante().getDescrizioneRichiedente() + ".\n<br/> Dettaglio delle presenze: " +
	//				     presenzeEffettuate.toString();
	//	    throw new EventAbortedException(messaggioErrore, this.getClass().getSimpleName(), e);
	//	}
    }
    //    private String getInfoPresenza(MercatipresenzeDBean presenza) {
    //
    //	StringBuilder sb = new StringBuilder();
    //	sb.append("Presenza ").append(" del <b>").append(Utilities.formatDate(presenza.getDatagiornata(), false)).append("</b> su manifestazione <b>")
    //		.append(presenza.getDescrizionegiorno()).append("</b>");
    //	if (presenza.getCodiceposteggio() != null) {
    //	    sb.append(" su posteggio <b>").append(presenza.getCodiceposteggio()).append("</b>");
    //	} else {
    //	    sb.append(". La presenza è senza assegnazione posteggio");
    //	}
    //	return sb.toString();
    //    }
}
