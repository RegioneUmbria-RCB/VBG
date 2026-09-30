package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClient;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClientId;

public interface PayPosdebRifClientDAO extends BaseDAO<PayPosdebRifClient, PayPosdebRifClientId> {

    public List<PayPosdebRifClient> findByPosizioneDebitoria(Integer idPosizioneDebitoria);
}
