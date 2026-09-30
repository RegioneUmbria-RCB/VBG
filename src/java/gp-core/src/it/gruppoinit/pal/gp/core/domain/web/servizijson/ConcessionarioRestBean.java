package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class ConcessionarioRestBean extends BaseSoggettoMercatoRestBean {

    private Integer idConcessionario;
    private String nominativo;

    public Integer getIdConcessionario() {

	return idConcessionario;
    }

    public void setIdConcessionario(Integer idConcessionario) {

	this.idConcessionario = idConcessionario;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }
}
