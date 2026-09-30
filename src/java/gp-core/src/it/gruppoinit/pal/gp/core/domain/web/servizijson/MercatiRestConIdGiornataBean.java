package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;

public class MercatiRestConIdGiornataBean {

    private Integer idGiornata;
    private MercatiRestHelper mercatiRestHelper;

    public MercatiRestConIdGiornataBean() {

    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }

    public MercatiRestHelper getMercatiRestHelper() {

	return mercatiRestHelper;
    }

    public void setMercatiRestHelper(MercatiRestHelper mercatiRestHelper) {

	this.mercatiRestHelper = mercatiRestHelper;
    }
}
