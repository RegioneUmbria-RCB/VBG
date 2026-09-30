package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_STATI_COMUNICAZIONI_D")
public class TmpStatiComunicazioniD implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3897560753003492633L;
    private Integer posizione;
    private Integer fkComunicazioniD;
    private String evento;
    private String stato;
    private String idcomune;

    public TmpStatiComunicazioniD() {

    }

    @Column(name = "POSIZIONE", nullable = false, precision = 2, scale = 0)
    public Integer getPosizione() {

	return this.posizione;
    }

    public void setPosizione(Integer posizione) {

	this.posizione = posizione;
    }

    @Id
    @Column(name = "FK_COMUNICAZIONI_D", nullable = false, precision = 10, scale = 0)
    public Integer getFkComunicazioniD() {

	return this.fkComunicazioniD;
    }

    public void setFkComunicazioniD(Integer fkComunicazioniD) {

	this.fkComunicazioniD = fkComunicazioniD;
    }

    @Column(name = "EVENTO", length = 500)
    public String getEvento() {

	return this.evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    @Column(name = "STATO", nullable = false, length = 50)
    public String getStato() {

	return this.stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }
}
