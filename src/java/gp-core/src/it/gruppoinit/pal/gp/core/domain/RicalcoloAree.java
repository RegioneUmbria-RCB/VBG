package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RICALCOLO_AREE")
public class RicalcoloAree implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5690666127717735308L;
    private RicalcoloAreeId pk;
    private String descrizione;
    private String stato;
    private Date datafine;
    private Integer dafare;
    private Integer fatti;
    private Integer totali;
    
    @EmbeddedId
    public RicalcoloAreeId getPk() {
    
        return pk;
    }

    
    public void setPk(RicalcoloAreeId pk) {
    
        this.pk = pk;
    }

    public String getDescrizione() {
    
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
    
        this.descrizione = descrizione;
    }
    
    public String getStato() {
    
        return stato;
    }
    
    public void setStato(String stato) {
    
        this.stato = stato;
    }
    
    public Date getDatafine() {
    
        return datafine;
    }
    
    public void setDatafine(Date datafine) {
    
        this.datafine = datafine;
    }
    
    public Integer getDafare() {
    
        return dafare;
    }
    
    public void setDafare(Integer dafare) {
    
        this.dafare = dafare;
    }
    
    public Integer getFatti() {
    
        return fatti;
    }
    
    public void setFatti(Integer fatti) {
    
        this.fatti = fatti;
    }
    
    public Integer getTotali() {
    
        return totali;
    }
    
    public void setTotali(Integer totali) {
    
        this.totali = totali;
    }
    
    
    
}
