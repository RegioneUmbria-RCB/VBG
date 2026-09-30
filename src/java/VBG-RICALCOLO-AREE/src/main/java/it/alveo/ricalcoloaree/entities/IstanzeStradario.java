package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.IstanzeStradarioPK;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "istanzestradario")
@IdClass(IstanzeStradarioPK.class)
public class IstanzeStradario {

    @Id
    private String id;
    @Id
    private String idcomune;
    private Integer codiceistanza;
    private String codicestradario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	@JoinColumn(name = "CODICESTRADARIO", referencedColumnName = "CODICESTRADARIO", nullable = false, insertable = false, updatable = false) })
    private Stradario stradario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	@JoinColumn(name = "CODICEISTANZA", referencedColumnName = "CODICEISTANZA", nullable = false, insertable = false, updatable = false) })
    private Istanze istanza;
    private String civico;
    private String km;
    private String uuid;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public Stradario getStradario() {

	return stradario;
    }

    public void setStradario(Stradario stradario) {

	this.stradario = stradario;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public String getKm() {

	return km;
    }

    public void setKm(String km) {

	this.km = km;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

	this.codicestradario = codicestradario;
    }
}
