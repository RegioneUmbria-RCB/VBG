package it.gruppoinit.pal.gp.core.domain.web;

public class ChiaveValoreBean<T, E> {

    private T chiave;
    private E valore;

    public T getChiave() {

	return chiave;
    }

    public void setChiave(T chiave) {

	this.chiave = chiave;
    }

    public E getValore() {

	return valore;
    }

    public void setValore(E valore) {

	this.valore = valore;
    }

    @Override
    public String toString() {

	String ret = "";
	if (chiave != null) {
	    ret = "Chiave: " + chiave.toString();
	}
	if (valore != null) {
	    ret += " Valore: " + valore.toString();
	}
	return ret.trim();
    }
}
