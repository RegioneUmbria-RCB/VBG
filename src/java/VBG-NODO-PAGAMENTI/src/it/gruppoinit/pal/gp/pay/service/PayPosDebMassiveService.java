package it.gruppoinit.pal.gp.pay.service;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;

public interface PayPosDebMassiveService extends BaseService<PayPosDebMassive, PkId> {

    Map<String, List<Integer>> findPosizioniDaElaborare();

    PayPosDebMassive findByIdPosizioneDebitoriaAndOperazione(PayPosizioniDebitorie posElaborata, String idoperazione);
}
