package it.gruppoinit.pal.gp.core.service.helper;

public enum SitCampiAmmessi {

    STRADARIO_ID_HIDDEN("CodiceStradario"),
    CODVIARIO_ID("CodiceVia"),
    CODCIVICO_ID("CodCivico"),
    CIVICO_ID("Civico"),
    COLORE_ID("Colore"),
    ESPONENTE_ID("Esponente"),
    SCALA_ID("Scala"),
    PIANO_ID("Piano"),
    INTERNO_ID("Interno"),
    ESPONENTEINTERNO_ID("EsponenteInterno"),
    FABBRICATO_ID("Fabbricato"),
    TIPOCATASTO_ID("TipoCatasto"),
    SEZIONE_ID("Sezione"),
    FOGLIO_ID("Foglio"),
    PARTICELLA_ID("Particella"),
    SUB_ID("Sub"),
    STRADARIO_ID("Stradario"),
    ACCESSOTIPO_ID("AccessoTipo"),
    ACCESSONUMERO_ID("AccessoNumero"),
    ACCESSODESCRIZIONE_ID("AccessoDescrizione"),
    /**
     * unitaimmob
     */
    UNITAIMMOB_ID("UnitaImmob"),
    CAP_ID("Cap"),
    FRAZIONE_ID("Frazione"),
    QUARTIERE_ID("Quartiere"),
    CIRCOSCRIZIONE_ID("Circoscrizione"),
    KM("Km");

    private final String nome;

    SitCampiAmmessi(String nome) {

	this.nome = nome;
    }

    public String getNome() {

	return nome;
    }

    public static SitCampiAmmessi fromNome(String nome) {

	String confronto = nome.replaceAll("@dett[+]*", "");
	for (SitCampiAmmessi campo : SitCampiAmmessi.values()) {
	    if (confronto.equalsIgnoreCase(campo.getNome())) {
		return campo;
	    }
	}
	throw new IllegalArgumentException("Non esiste un valore corrispondente a [" + nome + "] nell'enumeration CampiAmmessi");
    }
}
