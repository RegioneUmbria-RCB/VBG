package it.gruppoinit.pal.gp.core.service.helper;

public class SpuntistiMercatiHelper {

    // 
    private String mercato;
    private Integer codiceMercato;
    private String uso;
    private Integer codiceUso;
    private boolean segnalazione;
    private String messSegnalazione;

    //
    public String getMercato() {

	return mercato;
    }

    public void setMercato(String mercato) {

	this.mercato = mercato;
    }

    public Integer getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(Integer codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public String getUso() {

	return uso;
    }

    public void setUso(String uso) {

	this.uso = uso;
    }

    public Integer getCodiceUso() {

	return codiceUso;
    }

    public void setCodiceUso(Integer codiceUso) {

	this.codiceUso = codiceUso;
    }

    public String getMessSegnalazione() {

	return messSegnalazione;
    }

    public void setMessSegnalazione(String messSegnalazione) {

	this.messSegnalazione = messSegnalazione;
    }

    public boolean isSegnalazione() {

	return segnalazione;
    }

    public void setSegnalazione(boolean segnalazione) {

	this.segnalazione = segnalazione;
    }
}
