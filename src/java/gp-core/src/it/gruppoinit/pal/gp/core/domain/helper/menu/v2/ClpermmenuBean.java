package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

public class ClpermmenuBean {

    private Integer id;
    private Integer fkidmenu;
    private Integer codiceresponsabile;
    private String idcomune;
    private String software;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getFkidmenu() {

	return fkidmenu;
    }

    public void setFkidmenu(Integer fkidmenu) {

	this.fkidmenu = fkidmenu;
    }

    public Integer getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(Integer codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
