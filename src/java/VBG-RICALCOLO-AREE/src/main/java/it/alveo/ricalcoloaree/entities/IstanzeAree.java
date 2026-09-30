package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.IstanzeAreePk;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "istanzearee")
@IdClass(IstanzeAreePk.class)
public class IstanzeAree {

    @Id
    private String idcomune;
    @Id
    private Integer codiceistanza;
    @Id
    private Integer codicearea;
    private Integer autoins;
    private Boolean primario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	@JoinColumn(name = "CODICEISTANZA", referencedColumnName = "CODICEISTANZA", nullable = false, insertable = false, updatable = false),
	@JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    private Istanze istanza;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "CODICEAREA", referencedColumnName = "CODICEAREA", nullable = false, insertable = false, updatable = false),
	@JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    private Aree area;

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

    public Integer getCodicearea() {

	return codicearea;
    }

    public void setCodicearea(Integer codicearea) {

	this.codicearea = codicearea;
    }

    public Integer getAutoins() {

	return autoins;
    }

    public void setAutoins(Integer autoins) {

	this.autoins = autoins;
    }

    public Boolean getPrimario() {

	return primario;
    }

    public void setPrimario(Boolean primario) {

	this.primario = primario;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public Aree getArea() {

	return area;
    }

    public void setArea(Aree area) {

	this.area = area;
    }
}
