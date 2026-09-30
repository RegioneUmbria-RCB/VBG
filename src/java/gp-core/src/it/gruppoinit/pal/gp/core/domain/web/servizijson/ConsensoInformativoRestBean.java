package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Date;

public class ConsensoInformativoRestBean {

    private Integer id;
    private String contesto;
    private boolean flagConsenso;
    private Date dataConsenso;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    public boolean getFlagConsenso() {

	return flagConsenso;
    }

    public void setFlagConsenso(boolean flagConsenso) {

	this.flagConsenso = flagConsenso;
    }

    public Date getDataConsenso() {

	return dataConsenso;
    }

    public void setDataConsenso(Date dataConsenso) {

	this.dataConsenso = dataConsenso;
    }
}
