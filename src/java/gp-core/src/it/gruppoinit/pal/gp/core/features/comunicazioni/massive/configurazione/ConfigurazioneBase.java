package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneBase {

    public int getValoreDaConfigurazione(List<ParametroConfigurazioneComunicazione> parametri, String nomeChiave, int defaultVal) {

	for (ParametroConfigurazioneComunicazione p : parametri) {
	    if (p.getChiave().equalsIgnoreCase(nomeChiave)) {
		return Integer.valueOf(p.getValore());
	    }
	}
	return defaultVal;
    }

    public boolean getValoreDaConfigurazione(List<ParametroConfigurazioneComunicazione> parametri, String nomeChiave, boolean defaultVal) {

	for (ParametroConfigurazioneComunicazione p : parametri) {
	    if (p.getChiave().equalsIgnoreCase(nomeChiave)) {
		return StringUtils.defaultString(p.getValore(), "0").equalsIgnoreCase("1");
	    }
	}
	return defaultVal;
    }

    public String getValoreDaConfigurazione(List<ParametroConfigurazioneComunicazione> parametri, String nomeChiave, String defaultVal) {

	for (ParametroConfigurazioneComunicazione p : parametri) {
	    if (p.getChiave().equalsIgnoreCase(nomeChiave)) {
		return p.getValore();
	    }
	}
	return defaultVal;
    }

    public SceltaTipoMailAnagrafeEnum getSceltaMailDaConfigurazione(List<ParametroConfigurazioneComunicazione> parametri, String nomeChiave,
	    SceltaTipoMailAnagrafeEnum defaultVal) {

	for (ParametroConfigurazioneComunicazione p : parametri) {
	    if (p.getChiave().equalsIgnoreCase(nomeChiave) && StringUtils.isNotBlank(p.getValore())) {
		return SceltaTipoMailAnagrafeEnum.valueOf(p.getValore());
	    }
	}
	return defaultVal;
    }
}
