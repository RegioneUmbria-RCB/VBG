package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;

public class FakePayStatoPagamentiService implements PayStatoPagamentiService {

    private PayStatoPagamenti statoPagamento;

    public FakePayStatoPagamentiService() {

	this.statoPagamento = new PayStatoPagamenti();
    }

    @Override
    public void insert(PayStatoPagamenti entity) {

	//non necessario
    }

    @Override
    public void update(PayStatoPagamenti entity) {

	//non necessario
    }

    @Override
    public void delete(PayStatoPagamenti entity) {

	//non necessario
    }

    @Override
    public List<PayStatoPagamenti> findAll(Integer firstResult, Integer maxResult) {

	//non necessario
	return null;
    }

    @Override
    public PayStatoPagamenti findById(PkId id) {

	//non necessario
	return null;
    }

    @Override
    public PayStatoPagamenti bindDomainObject(PayStatoPagamenti entity, Class<?> idClass, String idPath) {

	//non necessario
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(PayStatoPagamenti entity) {

	//non necessario
	return null;
    }

    @Override
    public List<PayStatoPagamenti> getCronologiaPosizioneDebitoria(Integer posDebId) {

	//non necessario
	return null;
    }

    @Override
    public PayStatoPagamenti getStatoPosizioneDebitoria(PayPosizioniDebitorie posDeb) {

	//non necessario
	return null;
    }

    @Override
    public PayStatoPagamenti registraStatoPosizioneDebitoria(EsitoOperazionePosizioneDebitoriaType statoPosDeb, PayPosizioniDebitorie posDeb)
	    throws PayException {

	return statoPagamento;
    }

    @Override
    public void salvaStatoNativoSuStatoCorrente(PayPosizioniDebitorie payPos, String statoPagamentoNativo) {

	//non necessario
    }
}
