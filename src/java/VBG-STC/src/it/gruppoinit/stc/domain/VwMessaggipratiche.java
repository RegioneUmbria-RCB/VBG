package it.gruppoinit.stc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VW_MESSAGGIPRATICHE")
public class VwMessaggipratiche implements Serializable {

    private static final long serialVersionUID = 8632599873643249273L;
    private Integer id;
    private String mittIdnodo;
    private String mittIdente;
    private String mittIdsportello;
    private String mittIdpratica;
    private String mittNumpratica;
    private Date mittDatapratica;
    private String destIdnodo;
    private String destIdente;
    private String destIdsportello;
    private String destIdpratica;
    private String destNumpratica;
    private Date destDatapratica;

    public VwMessaggipratiche() {

	super();
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @Column(name = "MITT_IDNODO")
    public String getMittIdnodo() {

	return mittIdnodo;
    }

    public void setMittIdnodo(String mittIdnodo) {

	this.mittIdnodo = mittIdnodo;
    }

    @Column(name = "MITT_IDENTE")
    public String getMittIdente() {

	return mittIdente;
    }

    public void setMittIdente(String mittIdente) {

	this.mittIdente = mittIdente;
    }

    @Column(name = "MITT_IDSPORTELLO")
    public String getMittIdsportello() {

	return mittIdsportello;
    }

    public void setMittIdsportello(String mittIdsportello) {

	this.mittIdsportello = mittIdsportello;
    }

    @Column(name = "MITT_IDPRATICA")
    public String getMittIdpratica() {

	return mittIdpratica;
    }

    public void setMittIdpratica(String mittIdpratica) {

	this.mittIdpratica = mittIdpratica;
    }

    @Column(name = "MITT_NUMPRATICA")
    public String getMittNumpratica() {

	return mittNumpratica;
    }

    public void setMittNumpratica(String mittNumpratica) {

	this.mittNumpratica = mittNumpratica;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MITT_DATAPRATICA")
    public Date getMittDatapratica() {

	return mittDatapratica;
    }

    public void setMittDatapratica(Date mittDatapratica) {

	this.mittDatapratica = mittDatapratica;
    }

    @Column(name = "DEST_IDNODO")
    public String getDestIdnodo() {

	return destIdnodo;
    }

    public void setDestIdnodo(String destIdnodo) {

	this.destIdnodo = destIdnodo;
    }

    @Column(name = "DEST_IDENTE")
    public String getDestIdente() {

	return destIdente;
    }

    public void setDestIdente(String destIdente) {

	this.destIdente = destIdente;
    }

    @Column(name = "DEST_IDSPORTELLO")
    public String getDestIdsportello() {

	return destIdsportello;
    }

    public void setDestIdsportello(String destIdsportello) {

	this.destIdsportello = destIdsportello;
    }

    @Column(name = "DEST_IDPRATICA")
    public String getDestIdpratica() {

	return destIdpratica;
    }

    public void setDestIdpratica(String destIdpratica) {

	this.destIdpratica = destIdpratica;
    }

    @Column(name = "DEST_NUMPRATICA")
    public String getDestNumpratica() {

	return destNumpratica;
    }

    public void setDestNumpratica(String destNumpratica) {

	this.destNumpratica = destNumpratica;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEST_DATAPRATICA")
    public Date getDestDatapratica() {

	return destDatapratica;
    }

    public void setDestDatapratica(Date destDatapratica) {

	this.destDatapratica = destDatapratica;
    }
}
