package it.gruppoinit.domain;

import java.io.Serializable;

public class InventarioprocedimentoSoftwareBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2580948020691729226L;
    private String moduloSoftware;

    public InventarioprocedimentoSoftwareBean() {

	super();
    }

    public InventarioprocedimentoSoftwareBean(String moduloSoftware) {

	this();
	this.moduloSoftware = moduloSoftware;
    }

    public String getModuloSoftware() {

	return moduloSoftware;
    }

    public void setModuloSoftware(String moduloSoftware) {

	this.moduloSoftware = moduloSoftware;
    }
}
