package it.gruppoinit.pal.gp.core.domain;

import java.util.ArrayList;
import java.util.List;

public class LettureContatoriCommand {

    private MercatidLetture mercatidLetture = new MercatidLetture();
    private List<MercatidLetture> mercatidLettureList = new ArrayList<MercatidLetture>();
    private Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
    private Conti conti = new Conti();

    public Oneritipirateizzazione getOneritipirateizzazione() {

	return oneritipirateizzazione;
    }

    public void setOneritipirateizzazione(Oneritipirateizzazione oneritipirateizzazione) {

	this.oneritipirateizzazione = oneritipirateizzazione;
    }

    public MercatidLetture getMercatidLetture() {

	return mercatidLetture;
    }

    public void setMercatidLetture(MercatidLetture mercatidLetture) {

	this.mercatidLetture = mercatidLetture;
    }

    public List<MercatidLetture> getMercatidLettureList() {

	return mercatidLettureList;
    }

    public void setMercatidLettureList(List<MercatidLetture> mercatidLettureList) {

	this.mercatidLettureList = mercatidLettureList;
    }

    public Conti getConti() {

	return conti;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }
}
