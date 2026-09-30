package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.StradarioPK;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stradario")
@IdClass(StradarioPK.class)
public class Stradario {

    @Id
    private String idcomune;
    @Id
    private String codicestradario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE")
    private Comuni comune;
    private String descrizione;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

	this.codicestradario = codicestradario;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
