package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODOTTO")
public class Prodotto implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7372906848036591229L;
    private String codice;
    private String prodotto;
    private String identificativoMittente;
    private Boolean flagModMittente;

    @Id
    @Column(name = "CODICE", unique = true, nullable = false, length = 20)
    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    @Column(name = "PRODOTTO", unique = false, nullable = false, length = 100)
    public String getProdotto() {

	return prodotto;
    }

    public void setProdotto(String prodotto) {

	this.prodotto = prodotto;
    }

    @Column(name = "IDENTIFICATIVO_MITTENTE", unique = false, nullable = false, length = 10)
    public String getIdentificativoMittente() {

	return identificativoMittente;
    }

    public void setIdentificativoMittente(String identificativoMittente) {

	this.identificativoMittente = identificativoMittente;
    }

    @Column(name = "FLAG_MOD_MITTENTE", precision = 1, scale = 0)
    public Boolean getFlagModMittente() {

	return flagModMittente;
    }

    public void setFlagModMittente(Boolean flagModMittente) {

	this.flagModMittente = flagModMittente;
    }
}
