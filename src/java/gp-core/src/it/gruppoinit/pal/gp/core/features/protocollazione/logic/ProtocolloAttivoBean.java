package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

public class ProtocolloAttivoBean {

    private String ente;
    private String software;
    private String modulo;
    private boolean attivo;

    public String getEnte() {

	return ente;
    }

    public void setEnte(String ente) {

	this.ente = ente;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getModulo() {

	return modulo;
    }

    public void setModulo(String modulo) {

	this.modulo = modulo;
    }

    public boolean isAttivo() {

	return attivo;
    }

    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }
}
