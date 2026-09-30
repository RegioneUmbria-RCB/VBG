package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;

@Service
public class SottoscrittoreEventoPosizioneDebitoriaPagataPerAnnullamentoServiceImpl implements IEventSubscriber<EventoPosizioneDebitoriaPagata> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreEventoPosizioneDebitoriaPagataPerAnnullamentoServiceImpl.class);
    private IstanzeoneriService istanzeoneriService;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private IstanzeeventiService istanzeeventiService;

    @Autowired
    public SottoscrittoreEventoPosizioneDebitoriaPagataPerAnnullamentoServiceImpl(IstanzeoneriService istanzeoneriService,
	    DettPosizioneDebitoriaService dettPosizioneDebitoriaService, IstanzeeventiService istanzeeventiService) {

	this.istanzeoneriService = istanzeoneriService;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
	this.istanzeeventiService = istanzeeventiService;
    }

    @Override
    public void onEvent(EventoPosizioneDebitoriaPagata e) {

	log.debug("{}", e);
	if (e.isPagamentoOffline()) {
	    // Se pagamento offline esco
	    log.debug("isPagamentoOffline() {}", e);
	    return;
	}
	// intercetto solo se si tratta di pagamento offline
	List<IstanzeOneriNodoPagamentiHelper> oneriPagati = istanzeoneriService
		.findByIdPosizioneDebitoria(e.getDatiPagamento().getIdPosizioneDebitoria(), e.getCfEnteCreditore());
	if (oneriPagati.isEmpty()) {
	    log.debug("oneriPagati.isEmpty() {} ", e);
	    return;
	}
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService
		.findByFkIdPosizioneDebitoriaAndCfEnteCreditore(e.getDatiPagamento().getIdPosizioneDebitoria(), e.getCfEnteCreditore());
	for (IstanzeOneriNodoPagamentiHelper ionph : oneriPagati) {
	    // 
	    log.debug("{} cerco di recuperare istanze oneri e dett posizione debitoria", e);
	    // È il caso di più posizioni debitorie registrate su una istanzeonere
	    // se ne pago una devo annullare le altre
	    Istanzeoneri io = istanzeoneriService.findById(new PkId(ionph.getIdIstanzeOneri()));
	    Set<IstoneriDettPosizioni> posizioni = io.getIstoneriDettPosizioni();
	    // verifica se presenti record in istoneridettposizionidebitorie
	    log.debug("{} verifico le posizioni da annullare", e);
	    StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	    for (IstoneriDettPosizioni istoneriDettPosizioni : posizioni) {
		if (!dett.getId().getCodice().equals(istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice())) {
		    log.debug("{} provo ad annullare la posizione {}", e, istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice());
		    // posso annullare solo le altre posizioni debitorie non passate come riferimento
		    if (c.isStatoAnnullamentoAmmesso(istoneriDettPosizioni.getDettPosizioneDebitoria().getStato())) {
			try {
			    Integer idDettaglioPosizione = istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice();
			    String messaggio = String.format("La posizione %d è stata annullata perché è stata pagata la posizione %d collegata",
				    istoneriDettPosizioni.getDettPosizioneDebitoria().getIdPosizioneDebitoria(),
				    e.getDatiPagamento().getIdPosizioneDebitoria());
			    dettPosizioneDebitoriaService.annullaPosizioneDebitoria(idDettaglioPosizione, messaggio);
			    log.debug("{} posizione annullata {}", e, istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice());
			} catch (RuntimeException e1) {
			    log.error("{} verifico le posizioni da annullare", e, e1);
			    istanzeeventiService.insertEventoErroreAnnullamentoPosizioneDebitoria(ionph.getIdIstanzeOneri(),
				    istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice(), e1.getMessage());
			}
		    }
		}
	    }
	}
    }
}
