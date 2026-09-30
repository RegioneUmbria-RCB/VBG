package it.alveo.firmaremota.aruba.firma;

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
}