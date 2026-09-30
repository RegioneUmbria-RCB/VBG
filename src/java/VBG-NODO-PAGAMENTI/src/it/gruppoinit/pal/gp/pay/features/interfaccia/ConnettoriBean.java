package it.gruppoinit.pal.gp.pay.features.interfaccia;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "connettori")
public class ConnettoriBean {

    private List<ConnettoreBean> connettore;

    public List<ConnettoreBean> getConnettore() {

	if (this.connettore == null) {
	    this.connettore = new ArrayList<>();
	}
	return connettore;
    }

    public void setConnettore(List<ConnettoreBean> connettore) {

	this.connettore = connettore;
    }
}
