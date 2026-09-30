package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Comuni;

public class ConfigurazioneRegoleComuneHelper implements Comparator<ConfigurazioneRegoleComuneHelper> {

    private Comuni comune;
    private List<ConfigurazioneRegoleParametroHelper> parametri = new ArrayList<ConfigurazioneRegoleParametroHelper>();

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public List<ConfigurazioneRegoleParametroHelper> getParametri() {

	return parametri;
    }

    public void setParametri(List<ConfigurazioneRegoleParametroHelper> parametri) {

	this.parametri = parametri;
    }

    @Override
    public int compare(ConfigurazioneRegoleComuneHelper o1, ConfigurazioneRegoleComuneHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = "";
	String descrizione2 = "";
	if (o1.getComune() != null) {
	    descrizione1 = o1.getComune().getComune();
	}
	if (o2.getComune() != null) {
	    descrizione2 = o2.getComune().getComune();
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return 0;
	}
	if (StringUtils.isNotBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return -1;
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isNotBlank(descrizione2)) {
	    return 1;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
