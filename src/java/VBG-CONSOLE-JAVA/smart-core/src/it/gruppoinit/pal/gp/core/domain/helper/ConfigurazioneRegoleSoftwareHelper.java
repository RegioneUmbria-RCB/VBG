package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneRegoleSoftwareHelper implements Comparator<ConfigurazioneRegoleSoftwareHelper> {

    private String codiceSoftware;
    private String software;
    private String valore;

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getCodiceSoftware() {

	return codiceSoftware;
    }

    public void setCodiceSoftware(String codiceSoftware) {

	this.codiceSoftware = codiceSoftware;
    }

    @Override
    public int compare(ConfigurazioneRegoleSoftwareHelper o1, ConfigurazioneRegoleSoftwareHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = o1.getSoftware();
	String descrizione2 = o2.getSoftware();
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
