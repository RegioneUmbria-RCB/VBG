package it.gruppoinit.pal.gp.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.PayRequestType;

@Service
public class ConfigurazionePagamentiServiceImpl implements ConfigurazionePagamentiService {

    @Autowired
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;

    @Override
    public PayProfiliEntiCreditori configuraRequestPerEnteCreditore(PayRequestType request) throws PayConfigurationException {

	return this.configuraRequestPerEnteCreditore(request.getCfEnteCreditore());
    }

    @Override
    public PayConfigurationHelper getConfigurazioneEnteCorrente() {

	PayConfigurationHelper payCfg = new PayConfigurationHelper();
	//TODO popolare l'helper con le informazioni di configurazione generiche del nodo e specifiche del profilo ente creditore corrente
	return payCfg;
    }

    @Override
    public PayProfiliEntiCreditori configuraRequestPerEnteCreditore(String codiceProfilo) throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = this.payProfiliEntiCreditoriService.findByCfCodiceProfilo(codiceProfilo);
	if (profiloEnte != null) {
	    this.configuraRequestPerEnteCreditore(profiloEnte);
	} else {
	    throw new PayConfigurationException("Non esiste alcun ente creditore associato al profilo " + codiceProfilo);
	}
	return profiloEnte;
    }

    @Override
    public void configuraRequestPerEnteCreditore(PayProfiliEntiCreditori profiloEnte) {

	ORMHelper.setIdcomune(profiloEnte.getId().getIdcomune());
	if (profiloEnte.getComune() != null) {
	    ORMHelper.setCodiceComune(profiloEnte.getComune().getCodicecomune());
	}
	ORMHelper.setSoftware(profiloEnte.getSoftware());
	PayConfigurationHelper.setProfiloEnteCreditore(profiloEnte);
	PayConfigurationHelper.setDocumentiFilesystemPath(this.payConnectorConfigValuesService.getDocumentiSuFilesystemPath());
    }

    @Override
    public PayProfiliEntiCreditori configuraRequestIdAppPspAndIdPosizionePsp(String idAppPsp, String idPosizionePsp)
	    throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = this.payProfiliEntiCreditoriService.findByIdAppPspAndIdPosizionePsp(idAppPsp, idPosizionePsp);
	configuraRequestPerEnteCreditore(profiloEnte);
	return profiloEnte;
    }
}
