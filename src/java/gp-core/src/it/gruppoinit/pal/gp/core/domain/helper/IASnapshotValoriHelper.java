package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class IASnapshotValoriHelper implements Serializable {

    private static final long serialVersionUID = 8936910275548064183L;
    private int id;
    private String denominazione;
    private Date data;
    private int codiceIstanzaUltima;
    private String numeroIstanzaUltima;
    private String softwareIstanzaUltima;
    private String descrizioneIntervento;
    private Boolean attiva;
    private Boolean operante;
    private Map<String, String> mapValori;
    private String tipologiaAttivita;
    private Integer codiceOsservatorio;

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public int getCodiceIstanzaUltima() {

	return codiceIstanzaUltima;
    }

    public void setCodiceIstanzaUltima(int codiceIstanzaUltima) {

	this.codiceIstanzaUltima = codiceIstanzaUltima;
    }

    public String getNumeroIstanzaUltima() {

	return numeroIstanzaUltima;
    }

    public void setNumeroIstanzaUltima(String numeroIstanzaUltima) {

	this.numeroIstanzaUltima = numeroIstanzaUltima;
    }

    public Boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(Boolean attiva) {

	this.attiva = attiva;
    }

    public Boolean getOperante() {

	return operante;
    }

    public void setOperante(Boolean operante) {

	this.operante = operante;
    }

    public Map<String, String> getMapValori() {

	return mapValori;
    }

    public void setMapValori(Map<String, String> mapValori) {

	this.mapValori = mapValori;
    }

    public String getSoftwareIstanzaUltima() {

	return softwareIstanzaUltima;
    }

    public void setSoftwareIstanzaUltima(String softwareIstanzaUltima) {

	this.softwareIstanzaUltima = softwareIstanzaUltima;
    }

    public String getTipologiaAttivita() {

	return tipologiaAttivita;
    }

    public void setTipologiaAttivita(String tipologiaAttivita) {

	this.tipologiaAttivita = tipologiaAttivita;
    }

    public static String builCodiceOggettoMappa(int idSnapshot, int codiceCampo, int indice, int indiceMolteplicita) {

	return idSnapshot + "_" + codiceCampo + "_" + indice + "_" + indiceMolteplicita;
    }

    public String getDescrizioneIntervento() {

	return descrizioneIntervento;
    }

    public void setDescrizioneIntervento(String descrizioneIntervento) {

	this.descrizioneIntervento = descrizioneIntervento;
    }

    public Integer getCodiceOsservatorio() {

	return codiceOsservatorio;
    }

    public void setCodiceOsservatorio(Integer codiceOsservatorio) {

	this.codiceOsservatorio = codiceOsservatorio;
    }
}
