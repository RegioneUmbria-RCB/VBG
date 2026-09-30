package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneRegoleParametroHelper implements Comparator<ConfigurazioneRegoleParametroHelper> {

    private String parametro;
    private String descrizioneParametro;

    public String getDescrizioneParametro() {

	return descrizioneParametro;
    }

    public void setDescrizioneParametro(String descrizioneParametro) {

	this.descrizioneParametro = descrizioneParametro;
    }

    private List<ConfigurazioneRegoleSoftwareHelper> valori = new ArrayList<ConfigurazioneRegoleSoftwareHelper>();

    public String getParametro() {

	return parametro;
    }

    public void setParametro(String parametro) {

	this.parametro = parametro;
    }

    public List<ConfigurazioneRegoleSoftwareHelper> getValori() {

	return valori;
    }

    public void setValori(List<ConfigurazioneRegoleSoftwareHelper> valori) {

	this.valori = valori;
    }

    @Override
    public int compare(ConfigurazioneRegoleParametroHelper o1, ConfigurazioneRegoleParametroHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = o1.getParametro();
	String descrizione2 = o2.getParametro();
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
