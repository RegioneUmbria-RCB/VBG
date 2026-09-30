package it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoModificaDataFVPosizioneDebitoriaSuNodo;

@Service
public class SottoscrittoreEventoModificaDataFVPosizioneDebitoriaServiceImpl
	implements IEventSubscriber<EventoModificaDataFVPosizioneDebitoriaSuNodo> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreEventoModificaDataFVPosizioneDebitoriaServiceImpl.class);
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private BlacklistMotiviService blacklistMotiviService;

    @Autowired
    public SottoscrittoreEventoModificaDataFVPosizioneDebitoriaServiceImpl(DettPosizioneDebitoriaService dettPosizioneDebitoriaService,
	    BlacklistMotiviService blacklistMotiviService) {

	super();
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
	this.blacklistMotiviService = blacklistMotiviService; 
    }

    @Override
    public void onEvent(EventoModificaDataFVPosizioneDebitoriaSuNodo e) {

	// aggiorna la data fine validita di dettPosizioneDebitoria 
	log.debug("Data fine validita modificata per la posizione {}==>({}) aggiorno il dato.", e.getIdDettPosizioneDebitoria(), e.getDataFineValidita());
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(e.getIdDettPosizioneDebitoria()));
	
//	GregorianCalendar gc = new GregorianCalendar();
//	gc.setTime(e.getDataFineValidita());
//	gc.set(Calendar.HOUR_OF_DAY, 0);
//	gc.set(Calendar.MINUTE, 0);
//	gc.set(Calendar.SECOND, 0);
//	gc.set(Calendar.MILLISECOND, 0);
//	gc.add(Calendar.DAY_OF_MONTH, -1);
//	
//	dett.setDataFineValidita(gc.getTime());
	dett.setDataFineValidita(e.getDataFineValidita());
	dettPosizioneDebitoriaService.update(dett);
	log.debug("Data fine validita modificata per la posizione {}==>({})",
		e.getIdDettPosizioneDebitoria(), e.getDataFineValidita());
	
	
	log.debug("Data accertamento modificata per la blacklist {}==>({}) aggiorno il dato.", e.getIdBlackListMotivi(), e.getDataFineValidita());
	BlacklistMotivi blacklist = blacklistMotiviService.findById(new PkId(e.getIdBlackListMotivi()));
//	GregorianCalendar gc2 = new GregorianCalendar();
//	gc2.setTime(e.getDataFineValidita());
//	gc2.set(Calendar.HOUR_OF_DAY, 0);
//	gc2.set(Calendar.MINUTE, 0);
//	gc2.set(Calendar.SECOND, 0);
//	gc2.set(Calendar.MILLISECOND, 0);
//
//	blacklist.setDataAccertamento(gc2.getTime());
	blacklist.setDataAccertamento(e.getDataFineValidita());
	blacklistMotiviService.update(blacklist);
	log.debug("Data fine validita modificata per la blacklist {}==>({})",
		e.getIdBlackListMotivi(), e.getDataFineValidita());

    }
}
