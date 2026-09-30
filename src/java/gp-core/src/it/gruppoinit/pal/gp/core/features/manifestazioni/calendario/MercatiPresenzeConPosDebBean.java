package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

public class MercatiPresenzeConPosDebBean {

    private Integer id;
    private Integer iddettposdeb;
    private String statopostdeb;
    private Integer codicemercato;
    private Integer codiceuso;

    public Integer getCodicemercato() {

	return codicemercato;
    }

    public void setCodicemercato(Integer codicemercato) {

	this.codicemercato = codicemercato;
    }

    public Integer getCodiceuso() {

	return codiceuso;
    }

    public void setCodiceuso(Integer codiceuso) {

	this.codiceuso = codiceuso;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getIddettposdeb() {

	return iddettposdeb;
    }

    public void setIddettposdeb(Integer iddettposdeb) {

	this.iddettposdeb = iddettposdeb;
    }

    public String getStatopostdeb() {

	return statopostdeb;
    }

    public void setStatopostdeb(String statopostdeb) {

	this.statopostdeb = statopostdeb;
    }
}
