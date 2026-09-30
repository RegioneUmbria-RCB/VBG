package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AUTH_HASH_USATI")
public class AuthHashUsati implements java.io.Serializable {

    private static final long serialVersionUID = 1108175260301061086L;
    private String hashCode;
    private String dati;
    private Date dataUso;

    @Id
    @Column(name = "HASH_CODE", nullable = true, length = 100)
    public String getHashCode() {

	return hashCode;
    }

    public void setHashCode(String hashCode) {

	this.hashCode = hashCode;
    }

    @Column(name = "DATI", nullable = true, length = 200)
    public String getDati() {

	return dati;
    }

    public void setDati(String dati) {

	this.dati = dati;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_USO", length = 7)
    public Date getDataUso() {

	return dataUso;
    }

    public void setDataUso(Date dataUso) {

	this.dataUso = dataUso;
    }
}
