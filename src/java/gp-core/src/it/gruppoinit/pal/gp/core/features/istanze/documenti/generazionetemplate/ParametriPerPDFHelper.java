package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

public class ParametriPerPDFHelper {

    private String alias;
    private String software;
    private String guid;
    private Integer idtemplate;
    private String chiaveMac;

    public ParametriPerPDFHelper(String alias, String software, String guid, Integer idtemplate, String chiaveMac) {

	this.alias = alias;
	this.software = software;
	this.guid = guid;
	this.idtemplate = idtemplate;
	this.chiaveMac = chiaveMac;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public Integer getIdtemplate() {

	return idtemplate;
    }

    public void setIdtemplate(Integer idtemplate) {

	this.idtemplate = idtemplate;
    }

    public String getChiaveMac() {

	return chiaveMac;
    }

    public void setChiaveMac(String chiaveMac) {

	this.chiaveMac = chiaveMac;
    }
}
