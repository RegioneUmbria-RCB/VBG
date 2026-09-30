package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EliminaDebitoRequest {

    @XmlElement(required = true)
    private String codiceIPA;

    @XmlElement(required = true)
    private String codiceServizio;

    @XmlElement(required = true)
    private String codiceMotivoEliminazione;

    @XmlElement
    private String descrizioneMotivoEliminazione;

    @XmlElement(required = true)
    private NumeroAvviso numeroAvviso;

    /* getter / setter */

    public String getCodiceIPA() {
        return codiceIPA;
    }

    public void setCodiceIPA(String codiceIPA) {
        this.codiceIPA = codiceIPA;
    }

    public String getCodiceServizio() {
        return codiceServizio;
    }

    public void setCodiceServizio(String codiceServizio) {
        this.codiceServizio = codiceServizio;
    }

    public String getCodiceMotivoEliminazione() {
        return codiceMotivoEliminazione;
    }

    public void setCodiceMotivoEliminazione(String codiceMotivoEliminazione) {
        this.codiceMotivoEliminazione = codiceMotivoEliminazione;
    }

    public String getDescrizioneMotivoEliminazione() {
        return descrizioneMotivoEliminazione;
    }

    public void setDescrizioneMotivoEliminazione(String descrizioneMotivoEliminazione) {
        this.descrizioneMotivoEliminazione = descrizioneMotivoEliminazione;
    }

    public NumeroAvviso getNumeroAvviso() {
        return numeroAvviso;
    }

    public void setNumeroAvviso(NumeroAvviso numeroAvviso) {
        this.numeroAvviso = numeroAvviso;
    }
}
