package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadati;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati.IConfigurazioniMetadatiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class CfgMetadati extends SegnapostoTestualeBaseConValoreSingolo {

    private IConfigurazioniMetadatiService configurazioniMetadatiService;
    private VerticalizzazioniService verticalizzazioniService;
    private String name;
    private String nomeOriginale;

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.configurazioniMetadatiService = kernel.getBeanOfType(IConfigurazioniMetadatiService.class);
	this.verticalizzazioniService = kernel.getBeanOfType(VerticalizzazioniService.class);
    }

    @Override
    public String getNome() {

	return "CFG_";
    }

    public void setName(String nome) {

	this.nomeOriginale = "[-" + nome + "-]";
	nome = getStringAfterSubstringOrNull(nome, this.getNome());
	this.name = nome;
    }

    private String getStringAfterSubstringOrNull(String fullString, String targetSubstring) {

	int remainderIndex = fullString.indexOf(targetSubstring);
	if (remainderIndex == -1) {
	    return null; // Return null if the target substring isn't present
	}
	// Calculate the start of the remainder
	remainderIndex += targetSubstring.length();
	return fullString.substring(remainderIndex);
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	String isJava = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE,
		WebConstants.VERTICALIZZAZIONE_PARAM_PAGINA_DOCTIPO);
	if (StringUtils.equalsIgnoreCase(isJava, "JAVA")) {
	    ConfigurazioniMetadati confMetadati = this.configurazioniMetadatiService.findByChiaveAndIdcomune(name);
	    if (confMetadati == null) {
		return this.nomeOriginale;
	    }
	    return confMetadati.getValore();
	} else {
	    return this.nomeOriginale;
	}
    }
}
