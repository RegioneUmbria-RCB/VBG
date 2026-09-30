package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class ComuneBean {

    @JsonProperty("nome")
    private String nome;
    @JsonProperty("codiceIstat")
    private String codiceIstat;

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCodiceIstat() {

	return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {

	this.codiceIstat = codiceIstat;
    }
}
