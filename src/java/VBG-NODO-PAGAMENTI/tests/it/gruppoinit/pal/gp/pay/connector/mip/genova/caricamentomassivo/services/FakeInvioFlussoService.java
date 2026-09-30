package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import it.gruppoinit.pal.gp.pay.connector.mip.genova.BaseFolderCaricamento;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.IInvioFlussoService;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;

public class FakeInvioFlussoService implements IInvioFlussoService {

    @Override
    public void inviaFlusso(BaseFolderCaricamento config, TracciatoRecordSet<TracciatoRecordLotto> lotto,
	    TracciatoRecordSet<TracciatoRecordDebito> debiti, TracciatoRecordSet<TracciatoRecordRata> rate,
	    TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni, String connectorId, String idLotto) throws PayException {

	//non testabile
    }
}
