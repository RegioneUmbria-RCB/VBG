package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClient;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClientId;

public interface PayPosdebRifClientService extends BaseService<PayPosdebRifClient, PayPosdebRifClientId> {

    /**
     * Torna tutti i record presenti nella tabella come stringhe
     * 
     * @param idPosizioneDebitoria
     * @return
     */
    public List<String> findRiferimentiByPosizioneDebitoria(Integer idPosizioneDebitoria);

    /**
     * Torna tutti i record presenti nella tabella come oggetti
     * 
     * @param idPosizioneDebitoria
     * @return
     */
    public List<PayPosdebRifClient> findByPosizioneDebitoria(Integer idPosizioneDebitoria);
}
