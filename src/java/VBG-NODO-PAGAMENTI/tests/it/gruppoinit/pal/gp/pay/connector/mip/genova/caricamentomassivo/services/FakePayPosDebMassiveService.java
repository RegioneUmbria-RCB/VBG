package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.service.PayPosDebMassiveService;

public class FakePayPosDebMassiveService implements PayPosDebMassiveService {

    private Map<String, List<Integer>> posizioniDaElaborare = new HashMap<>();

    public void setPosizioniDaElaborare(Map<String, List<Integer>> posizioniDaElaborare) {

	this.posizioniDaElaborare = posizioniDaElaborare;
    }

    public FakePayPosDebMassiveService() {

	//non necessario
    }

    public FakePayPosDebMassiveService(Map<String, List<Integer>> posizioniDaElaborare) {

	this.posizioniDaElaborare = posizioniDaElaborare;
    }

    @Override
    public void insert(PayPosDebMassive entity) {

	//non necessario
    }

    @Override
    public void update(PayPosDebMassive entity) {

	//non necessario
    }

    @Override
    public void delete(PayPosDebMassive entity) {

	//non necessario
    }

    @Override
    public List<PayPosDebMassive> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public PayPosDebMassive findById(PkId id) {

	return null;
    }

    @Override
    public PayPosDebMassive bindDomainObject(PayPosDebMassive entity, Class<?> idClass, String idPath) {

	return null;
    }

    @Override
    public PkId newIdFromSequencetable(PayPosDebMassive entity) {

	return null;
    }

    @Override
    public Map<String, List<Integer>> findPosizioniDaElaborare() {

	return this.posizioniDaElaborare;
    }

    @Override
    public PayPosDebMassive findByIdPosizioneDebitoriaAndOperazione(PayPosizioniDebitorie posElaborata, String idoperazione) {

	return new PayPosDebMassive();
    }
}
