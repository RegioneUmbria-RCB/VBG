package it.alveo.firmaremota.aruba.configurazione.params;

import it.alveo.firmaremota.aruba.model.Param;

public abstract class BaseParam implements IParam {

    protected String valore;

    @Override
    public String getValore() {

	return this.valore;
    }

    @Override
    public boolean isObbligatorio() {

	return true;
    }

    public BaseParam(String valore) {

	this.valore = valore;
    }

    public Param toParam() {

	Param p = new Param();
	p.chiave(getChiave());
	p.descrizione(getDescrizione());
	p.valore(getValore());
	p.obbligatorio(isObbligatorio());
	return p;
    }
}