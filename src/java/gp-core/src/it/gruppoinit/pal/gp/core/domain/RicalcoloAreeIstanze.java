package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RICALCOLO_AREE_ISTANZE")
public class RicalcoloAreeIstanze implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6545772748234097559L;
    private RicalcoloAreeIstanzeId pk;
    private String idRicalcoloAree;
    
    @EmbeddedId
    public RicalcoloAreeIstanzeId getPk() {
    
        return pk;
    }
    
    public void setPk(RicalcoloAreeIstanzeId pk) {
    
        this.pk = pk;
    }
    
    @Column(name = "ID_RICALCOLO_AREE")
    public String getIdRicalcoloAree() {
    
        return idRicalcoloAree;
    }
    
    public void setIdRicalcoloAree(String idRicalcoloAree) {
    
        this.idRicalcoloAree = idRicalcoloAree;
    }
    
}
