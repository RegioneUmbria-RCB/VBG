package it.gruppoinit.pal.gp.core.domain.web;

public class GiornataMercatoCommand {

    private Integer codiceMercato;
    private String descrizioneMercato;
    private Integer codiceUso;
    private String descrizioneUso;
    private String dataGiornata;
    private String linkGiornataGestionePresenze;

    public Integer getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(Integer codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public String getDescrizioneMercato() {

	return descrizioneMercato;
    }

    public void setDescrizioneMercato(String descrizioneMercato) {

	this.descrizioneMercato = descrizioneMercato;
    }

    public Integer getCodiceUso() {

	return codiceUso;
    }

    public void setCodiceUso(Integer codiceUso) {

	this.codiceUso = codiceUso;
    }

    public String getDescrizioneUso() {

	return descrizioneUso;
    }

    public void setDescrizioneUso(String descrizioneUso) {

	this.descrizioneUso = descrizioneUso;
    }

    public String getDataGiornata() {

	return dataGiornata;
    }

    public void setDataGiornata(String dataGiornata) {

	this.dataGiornata = dataGiornata;
    }

    public String getLinkGiornataGestionePresenze() {

	return linkGiornataGestionePresenze;
    }

    public void setLinkGiornataGestionePresenze(String linkGiornataGestionePresenze) {

	this.linkGiornataGestionePresenze = linkGiornataGestionePresenze;
    }
}
