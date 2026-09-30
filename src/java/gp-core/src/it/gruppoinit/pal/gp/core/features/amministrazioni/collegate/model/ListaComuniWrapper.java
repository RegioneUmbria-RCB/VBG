package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaComuniWrapper {

    private List<AmmCollComuneBean> comuni;

    public ListaComuniWrapper() {

    }

    public ListaComuniWrapper(List<AmmCollComuneBean> comuni) {

	this();
	this.comuni = comuni;
    }

    public List<AmmCollComuneBean> getComuni() {

	return comuni;
    }

    public void setComuni(List<AmmCollComuneBean> comuni) {

	this.comuni = comuni;
    }
}
