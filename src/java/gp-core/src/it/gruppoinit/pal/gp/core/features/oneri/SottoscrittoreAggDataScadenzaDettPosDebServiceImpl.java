package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDataScadenzaDettPosizioneDebitoriaModificata;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittoreAggDataScadenzaDettPosDebServiceImpl implements IEventSubscriber<EventoDataScadenzaDettPosizioneDebitoriaModificata> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreAggDataScadenzaDettPosDebServiceImpl.class);
    private IstanzeoneriService istanzeoneriService;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

    @Autowired
    public SottoscrittoreAggDataScadenzaDettPosDebServiceImpl(IstanzeoneriService istanzeoneriService,
	    DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	super();
	this.istanzeoneriService = istanzeoneriService;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Override
    public void onEvent(EventoDataScadenzaDettPosizioneDebitoriaModificata e) {

	// AGGIORNA LA DATA DI SCADENZA DEGLI ONERI
	String operazione = Utilities.generaPassword(10);
	log.debug("{} SottoscrittoreAggDataScadenzaDettPosDebService: {},{}",
		new Object[] { operazione, e.getIdDettPosizioneDebitoria(), e.getDataScadenza() });
	DettPosizioneDebitoria dettPd = dettPosizioneDebitoriaService.findById(new PkId(e.getIdDettPosizioneDebitoria()));
	if (dettPd == null) {
	    throw new IllegalArgumentException("Posizione debitoria con id " + e.getIdDettPosizioneDebitoria() + " non trovata");
	}
	List<IstanzeOneriNodoPagamentiHelper> findByIdPosizioneDebitoria = istanzeoneriService
		.findByIdPosizioneDebitoria(dettPd.getIdPosizioneDebitoria(), dettPd.getCfEnteCreditore()); // la ricerca viene fatta sul campo dett_posizione_debitoria.ID_POSIZIONE_DEBITORIA e non su dett_posizione_debitoria.id
	log.debug("{} SottoscrittoreAggDataScadenzaDettPosDebService: {},{} cerco la lista di istanzeoneri",
		new Object[] { operazione, e.getIdDettPosizioneDebitoria(), e.getDataScadenza() });
	for (IstanzeOneriNodoPagamentiHelper iph : findByIdPosizioneDebitoria) {
	    log.debug("{} SottoscrittoreAggDataScadenzaDettPosDebService: aggiorno istanzeoneri {}",
		    new Object[] { operazione, iph.getIdIstanzeOneri() });
	    Istanzeoneri io = istanzeoneriService.findById(new PkId(iph.getIdIstanzeOneri()));
	    io.setDatascadenza(e.getDataScadenza());
	    istanzeoneriService.update(io);
	    log.debug("{} SottoscrittoreAggDataScadenzaDettPosDebService: istanzeoneri {} aggiornato !",
		    new Object[] { operazione, iph.getIdIstanzeOneri() });
	}
    }
}
