package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model;

public class QueryAmministrazioniFilter {

    private Integer codiceAmministrazione;
    private Integer codiceAmministrazioneCollegata;
    private String codiceComune;

    private QueryAmministrazioniFilter() {

    }

    public QueryAmministrazioniFilter(Integer codiceAmministrazione) {

	this();
	this.codiceAmministrazione = codiceAmministrazione;
    }

    public QueryAmministrazioniFilter(Integer codiceAmministrazione, Integer codiceAmministrazioneCollegata) {

	this(codiceAmministrazione);
	this.codiceAmministrazioneCollegata = codiceAmministrazioneCollegata;
    }

    public QueryAmministrazioniFilter(Integer codiceAmministrazione, Integer codiceAmministrazioneCollegata, String codiceComune) {

	this(codiceAmministrazione, codiceAmministrazioneCollegata);
	this.codiceComune = codiceComune;
    }

    public Integer getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(Integer codiceAmministrazione) {

	this.codiceAmministrazione = codiceAmministrazione;
    }

    public Integer getCodiceAmministrazioneCollegata() {

	return codiceAmministrazioneCollegata;
    }

    public void setCodiceAmministrazioneCollegata(Integer codiceAmministrazioneCollegata) {

	this.codiceAmministrazioneCollegata = codiceAmministrazioneCollegata;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }
}
