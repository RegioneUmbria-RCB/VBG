package it.gruppoinit.pdd.ri.features.configurazione;

import java.util.ArrayList;
import java.util.List;

public class Configurazione {

    private List<Ente> enti = new ArrayList<>();
    private List<Certificato> certificati = new ArrayList<>();

    public List<Ente> getEnti() {

	return enti;
    }

    public void setEnti(List<Ente> enti) {

	this.enti = enti;
    }

    public List<Certificato> getCertificati() {

	return certificati;
    }

    public void setCertificati(List<Certificato> certificati) {

	this.certificati = certificati;
    }
}
