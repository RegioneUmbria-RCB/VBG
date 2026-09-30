package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;

public class CfgSpResponsabile extends SegnapostoTestualeBaseConValoreSingolo {

    private ConfigurazioneService configurazioneservice;

    @Override
    public String getNome() {

	return "CFG_SP_RESPONSABILE";
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

	Configurazione config = getConfigurazione();
	if (EntityUtils.getNestedProperty(config.getResponsabili(), "id.codice") != null) {
	    return config.getResponsabili().getResponsabile();
	}
	config = getConfigurazione("TT");
	if (EntityUtils.getNestedProperty(config.getResponsabili(), "id.codice") != null) {
	    return config.getResponsabili().getResponsabile();
	}
	return "";
    }

    private Configurazione getConfigurazione() {

	return getConfigurazione(ORMHelper.getSoftware());
    }

    private Configurazione getConfigurazione(String software) {

	return configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), software));
    }
}