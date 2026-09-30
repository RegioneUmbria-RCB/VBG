package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

public class SoggettoDebitorePagamentoHelper {

    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String cfPiva;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getCfPiva() {

	return cfPiva;
    }

    public void setCfPiva(String cfPiva) {

	this.cfPiva = cfPiva;
    }
}
