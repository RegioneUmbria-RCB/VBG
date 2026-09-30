package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CODIFICA_ENTI_RFC53")
public class CodificaEntiRfc53 implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1041535140983818467L;
    private String codiceistat;
    private String ente;
    private String codifica;

    @Id
    @Column(name = "CODICEISTAT", unique = true, nullable = false, length = 6)
    public String getCodiceistat() {

	return codiceistat;
    }

    public void setCodiceistat(String codiceistat) {

	this.codiceistat = codiceistat;
    }

    @Column(name = "ENTE", unique = false, nullable = false, length = 250)
    public String getEnte() {

	return ente;
    }

    public void setEnte(String ente) {

	this.ente = ente;
    }

    @Column(name = "CODIFICA", unique = false, nullable = false, length = 30)
    public String getCodifica() {

	return codifica;
    }

    public void setCodifica(String codifica) {

	this.codifica = codifica;
    }
}
