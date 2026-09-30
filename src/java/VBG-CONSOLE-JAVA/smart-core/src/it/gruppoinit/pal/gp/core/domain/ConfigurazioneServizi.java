package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIGURAZIONE_SERVIZI")
public class ConfigurazioneServizi implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6745137375547723280L;
    private String idente;
    private Boolean flagApModulisticanazionale;

    @Id
    @Column(name = "IDENTE", unique = true, nullable = false, length = 15)
    public String getIdente() {

	return idente;
    }

    public void setIdente(String idente) {

	this.idente = idente;
    }

    @Column(name = "FILTRA_AP_MODULISTICANAZIONALE")
    public Boolean getFlagApModulisticanazionale() {

	return flagApModulisticanazionale;
    }

    public void setFlagApModulisticanazionale(Boolean flagApModulisticanazionale) {

	this.flagApModulisticanazionale = flagApModulisticanazionale;
    }
}
