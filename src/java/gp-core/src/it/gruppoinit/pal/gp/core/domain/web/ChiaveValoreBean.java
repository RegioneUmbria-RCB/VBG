package it.gruppoinit.pal.gp.core.domain.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bean")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChiaveValoreBean<T, E> {

    @XmlElement(name = "chiave")
    private T chiave;
    @XmlElement(name = "valore")
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
