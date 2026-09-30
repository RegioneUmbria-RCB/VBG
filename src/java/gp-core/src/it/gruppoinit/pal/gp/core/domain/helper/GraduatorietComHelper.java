package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class GraduatorietComHelper {

    private Integer codice;
    /**
     * Data : GRADUATORIET_COM.DATA
     */
    private Date data;
    /**
     * descrizione : GRADUATORIET_COM.DESCRIZIONE
     */
    private String descrizione;
    /**
     * numeroDomande : Conteggio su GRADUATORIED_COM con IDCOMUNE e FK_TESTATA = GRADUATORIET_COM.ID
     */
    private Integer numeroDomande;
    /**
     * numeroDomandeConMovimento : Conteggio su GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID e
     * GRADUATORIED_COM.CODICEMOVIMENTO NOT NULL
     */
    private Integer numeroDomandeConMovimento;
    /**
     * numeroDomandeConAllegato: Conteggio su GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID e
     * GRADUATORIED_COM.IDALLEGATO NOT NULL
     */
    private Integer numeroDomandeConAllegato;
    /**
     * numeroDomandeConMailInviate: Conteggio su GRADUATORIED_COM con IDCOMUNE, FK_TESTATA = GRADUATORIET_COM.ID e
     * GRADUATORIED_COM.IDMAIL NOT NULL
     */
    private Integer numeroDomandeConMailInviate;

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getNumeroDomande() {

	return numeroDomande;
    }

    public void setNumeroDomande(Integer numeroDomande) {

	this.numeroDomande = numeroDomande;
    }

    public Integer getNumeroDomandeConMovimento() {

	return numeroDomandeConMovimento;
    }

    public void setNumeroDomandeConMovimento(Integer numeroDomandeConMovimento) {

	this.numeroDomandeConMovimento = numeroDomandeConMovimento;
    }

    public Integer getNumeroDomandeConAllegato() {

	return numeroDomandeConAllegato;
    }

    public void setNumeroDomandeConAllegato(Integer numeroDomandeConAllegato) {

	this.numeroDomandeConAllegato = numeroDomandeConAllegato;
    }

    public Integer getNumeroDomandeConMailInviate() {

	return numeroDomandeConMailInviate;
    }

    public void setNumeroDomandeConMailInviate(Integer numeroDomandeConMailInviate) {

	this.numeroDomandeConMailInviate = numeroDomandeConMailInviate;
    }
}
