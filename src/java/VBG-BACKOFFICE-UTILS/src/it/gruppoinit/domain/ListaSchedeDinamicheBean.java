package it.gruppoinit.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ListaSchedeDinamicheBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5355194011177775002L;
    private List<SchedeDinamicheBean> schedes;
    @JsonIgnore
    private Set<Integer> codiciCampi;

    public ListaSchedeDinamicheBean() {

	super();
    }

    public List<SchedeDinamicheBean> getSchedes() {

	return schedes;
    }

    public void setSchedes(List<SchedeDinamicheBean> schedes) {

	this.schedes = schedes;
    }

    public Set<Integer> getCodiciCampi() {

	if (this.schedes != null && this.getSchedes().size() > 0) {
	    codiciCampi = new HashSet<Integer>();
	    for (SchedeDinamicheBean s : schedes) {
		if (s.getDettaglios() != null && s.getDettaglios().size() > 0) {
		    for (SchedeDinamicheDettaglioBean d : s.getDettaglios()) {
			if (d.getFkd2cid() != null) {
			    this.codiciCampi.add(d.getFkd2cid());
			}
		    }
		}
	    }
	    this.codiciCampi = new HashSet<Integer>();
	}
	return codiciCampi;
    }
}
