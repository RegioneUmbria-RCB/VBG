package it.alveo.ricalcoloaree.entities;

import java.math.BigDecimal;

import it.alveo.ricalcoloaree.entities.composefields.AreeDettagliPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "areedettagli")
@IdClass(AreeDettagliPK.class)
public class AreeDettagli {

    @Id
    private String id;
    @Id
    private String idcomune;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "CODICEAREA", referencedColumnName = "CODICEAREA", nullable = false, insertable = false, updatable = false) })
    private Aree aree;
    private Boolean paridispari;
    private String codicestradario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "CODICESTRADARIO", referencedColumnName = "CODICESTRADARIO", nullable = false, insertable = false, updatable = false) })
    private Stradario stradario;
    @Column(name = "CIVICO_DA")
    private Integer civicoDa;
    @Column(name = "CIVICO_A")
    private Integer civicoA;
    @Column(name = "KM_A", precision = 8, scale = 3)
    private BigDecimal kmA;
    @Column(name = "KM_DA", precision = 8, scale = 3)
    private BigDecimal kmDa;

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

    public Aree getAree() {

        return aree;
    }

    public void setAree(Aree aree) {

        this.aree = aree;
    }

    public Boolean getParidispari() {

        return paridispari;
    }

    public void setParidispari(Boolean paridispari) {

        this.paridispari = paridispari;
    }

    public Stradario getStradario() {

        return stradario;
    }

    public void setStradario(Stradario stradario) {

        this.stradario = stradario;
    }

    public String getCodicestradario() {

        return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

        this.codicestradario = codicestradario;
    }

    public Integer getCivicoDa() {

        return civicoDa;
    }

    public void setCivicoDa(Integer civicoDa) {

        this.civicoDa = civicoDa;
    }

    public Integer getCivicoA() {

        return civicoA;
    }

    public void setCivicoA(Integer civicoA) {

        this.civicoA = civicoA;
    }

    public BigDecimal getKmA() {

        return kmA;
    }

    public void setKmA(BigDecimal kmA) {

        this.kmA = kmA;
    }

    public BigDecimal getKmDa() {

        return kmDa;
    }

    public void setKmDa(BigDecimal kmDa) {

        this.kmDa = kmDa;
    }
}
