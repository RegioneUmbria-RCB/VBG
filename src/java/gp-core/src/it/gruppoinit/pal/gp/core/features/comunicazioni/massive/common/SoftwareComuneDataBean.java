package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

public class SoftwareComuneDataBean implements ISoftwareComuneData {

    private String codiceComune;
    private String software;

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    @Override
    public String getCodiceComune() {

	return this.codiceComune;
    }

    @Override
    public String getSoftware() {

	return this.software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
