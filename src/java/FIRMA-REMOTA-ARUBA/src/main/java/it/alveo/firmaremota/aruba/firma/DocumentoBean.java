package it.alveo.firmaremota.aruba.firma;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentoBean {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("nuovonome")
    private String nuovoNome;
    @JsonProperty("firmato")
    private boolean firmato;
    @JsonProperty("nomefilefirmato")
    private String nomeFileFirmato;

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNuovoNome() {

	return nuovoNome;
    }

    public void setNuovoNome(String nuovoNome) {

	this.nuovoNome = nuovoNome;
    }

    public boolean isFirmato() {

	return firmato;
    }

    public void setFirmato(boolean firmato) {

	this.firmato = firmato;
    }

    public String getNomeFileFirmato() {

	return nomeFileFirmato;
    }

    public void setNomeFileFirmato(String nomeFileFirmato) {

	this.nomeFileFirmato = nomeFileFirmato;
    }
}
