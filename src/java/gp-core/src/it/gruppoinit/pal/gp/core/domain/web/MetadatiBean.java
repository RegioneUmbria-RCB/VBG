package it.gruppoinit.pal.gp.core.domain.web;

public class MetadatiBean extends ChiaveValoreBean<String, String> {

    public MetadatiBean() {

    }

    public MetadatiBean(String chiave, String valore) {

	this.setChiave(chiave);
	this.setValore(valore);
    }
}
