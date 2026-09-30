package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione;

public class UpgrCausaliToNodoBean {

    private String idcomune;
    private Integer id;
    private String descrizione;
    private String codiceversamento;
    private String mappaturaclient;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCodiceversamento() {

	return codiceversamento;
    }

    public void setCodiceversamento(String codiceversamento) {

	this.codiceversamento = codiceversamento;
    }

    public String getMappaturaclient() {

	return mappaturaclient;
    }

    public void setMappaturaclient(String mappaturaclient) {

	this.mappaturaclient = mappaturaclient;
    }

    @Override
    public String toString() {

	return "[idcomune: " + idcomune + ", id: " + id + ", descrizione: " + descrizione + "]";
    }
}
