package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;

public class CfgSpEmailPec extends SegnapostoTestualeBaseConValoreSingolo {

    private ConfigurazioneService configurazioneservice;
    private ComuniassociatisoftwareService comuniAssociatiSoftwareService;

    @Override
    public String getNome() {

	return "CFG_SP_EMAILPEC";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.comuniAssociatiSoftwareService = kernel.getBeanOfType(ComuniassociatisoftwareService.class);
	this.configurazioneservice = kernel.getBeanOfType(ConfigurazioneService.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	Comuniassociatisoftware comuneAssociato = getDatiComuneassociato(data);
	if (comuneAssociato != null && StringUtils.isNotBlank(comuneAssociato.getMailpec())) {
	    return comuneAssociato.getMailpec();
	}
	comuneAssociato = getDatiComuneassociatoTT(data);
	if (comuneAssociato != null && StringUtils.isNotBlank(comuneAssociato.getMailpec())) {
	    return comuneAssociato.getMailpec();
	}
	Configurazione cfg = getConfigurazione();
	if (cfg != null && StringUtils.isNotBlank(cfg.getEmailresponsabilepec())) {
	    return cfg.getEmailresponsabilepec();
	}
	return getConfigurazioneTT().getEmailresponsabilepec();
    }

    private Comuniassociatisoftware getDatiComuneassociato(IUsefulDataForPlaceholderReplacement data) {

	return comuniAssociatiSoftwareService.findByComune(new Comuni(data.getIstanza().getComune().getCodicecomune()));
    }

    private Comuniassociatisoftware getDatiComuneassociatoTT(IUsefulDataForPlaceholderReplacement data) {

	String currentSW = ORMHelper.getSoftware();
	try {
	    ORMHelper.setSoftware("TT");
	    return comuniAssociatiSoftwareService.findByComune(new Comuni(data.getIstanza().getComune().getCodicecomune()));
	} finally {
	    ORMHelper.setSoftware(currentSW);
	}
    }

    private Configurazione getConfigurazione() {

	return configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware()));
    }

    private Configurazione getConfigurazioneTT() {

	return configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), "TT"));
    }
}
