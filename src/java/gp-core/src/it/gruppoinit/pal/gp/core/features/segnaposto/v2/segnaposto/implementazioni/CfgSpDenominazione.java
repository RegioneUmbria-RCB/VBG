package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;

public class CfgSpDenominazione extends SegnapostoTestualeBaseConValoreSingolo {

    private ConfigurazioneService configurazioneservice;

    @Override
    public String getNome() {

	return "CFG_SP_DENOMINAZIONE";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.configurazioneservice = kernel.getBeanOfType(ConfigurazioneService.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	Configurazione config = configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware()));
	if (config == null || StringUtils.isBlank(config.getDenominazione())) {
	    config = configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), "TT"));
	}
	return config.getDenominazione();
    }
}
