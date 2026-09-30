package it.alveo.ricalcoloaree.entities;

import java.sql.Timestamp;

import it.alveo.ricalcoloaree.entities.composefields.IstanzeEventiPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "istanzeeventi")
public class IstanzeEventi {

    @EmbeddedId
    private IstanzeEventiPK pk;
    private Integer codiceistanza;
    private String fkidcategoriaevento;
    private String descrizione;
    private Timestamp data;
    private Integer codiceanagrafe;
    private Integer codicemovimento;
    @Column(name = "flag_letto")
    private Boolean flagLetto;
    private String software;

    public IstanzeEventiPK getPk() {

	return pk;
    }

    public void setPk(IstanzeEventiPK pk) {

	this.pk = pk;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getFkidcategoriaevento() {

	return fkidcategoriaevento;
    }

    public void setFkidcategoriaevento(String fkidcategoriaevento) {

	this.fkidcategoriaevento = fkidcategoriaevento;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Timestamp getData() {

	return data;
    }

    public void setData(Timestamp data) {

	this.data = data;
    }

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public Boolean getFlagLetto() {

	return flagLetto;
    }

    public void setFlagLetto(Boolean flagLetto) {

	this.flagLetto = flagLetto;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
