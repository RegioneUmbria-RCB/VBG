package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.NotImplementedException;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayPosdebRifClientDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClient;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClientId;
import it.gruppoinit.pal.gp.pay.service.PayPosdebRifClientService;

@Service
public class PayPosdebRifClientServiceImpl extends BaseServiceImpl<PayPosdebRifClient, PayPosdebRifClientId> implements PayPosdebRifClientService {

    @Autowired
    private PayPosdebRifClientDAO payPosdebRifClientDAO;

    @Override
    public void insert(PayPosdebRifClient entity) {

	dataIntegration(entity);
	payPosdebRifClientDAO.insert(entity);
    }

    private void dataIntegration(PayPosdebRifClient entity) {

	// al momento non faccio niente
    }

    @Override
    public void update(PayPosdebRifClient entity) {

	dataIntegration(entity);
	payPosdebRifClientDAO.update(entity);
    }

    @Override
    public void delete(PayPosdebRifClient entity) {

	payPosdebRifClientDAO.delete(entity);
    }

    @Override
    public List<PayPosdebRifClient> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public PayPosdebRifClient findById(PayPosdebRifClientId id) {

	return payPosdebRifClientDAO.findById(id);
    }

    @Override
    public List<String> findRiferimentiByPosizioneDebitoria(Integer idPosizioneDebitoria) {

	List<String> ret = new ArrayList<>();
	List<PayPosdebRifClient> findByPosizioneDebitoria = payPosdebRifClientDAO.findByPosizioneDebitoria(idPosizioneDebitoria);
	for (PayPosdebRifClient r : findByPosizioneDebitoria) {
	    ret.add(r.getRiferimentoClient());
	}
	return ret;
    }

    @Override
    public List<PayPosdebRifClient> findByPosizioneDebitoria(Integer idPosizioneDebitoria) {

	return payPosdebRifClientDAO.findByPosizioneDebitoria(idPosizioneDebitoria);
    }

    @Override
    protected Class<PayPosdebRifClient> getEntityClass() {

	return PayPosdebRifClient.class;
    }
}
