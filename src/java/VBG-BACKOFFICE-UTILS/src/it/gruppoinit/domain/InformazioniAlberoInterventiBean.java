package it.gruppoinit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InformazioniAlberoInterventiBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3235104652102017542L;
    private List<AlberoInterventiBean> alberoInterventiBean = new ArrayList<AlberoInterventiBean>();
    private Set<Integer> codiciEndoProcedimenti = new HashSet<Integer>();
    private Set<Integer> codiciFamiglieEndo = new HashSet<Integer>();
    private Set<Integer> codiciTipiEndo = new HashSet<Integer>();
    private Set<Integer> codiciSchedeEndo = new HashSet<Integer>();
    private Set<Integer> codiciAmministrazioniEndo = new HashSet<Integer>();

    public InformazioniAlberoInterventiBean() {

	super();
    }

    public List<AlberoInterventiBean> getAlberoInterventiBean() {

	return alberoInterventiBean;
    }

    public void setAlberoInterventiBean(List<AlberoInterventiBean> alberoInterventiBean) {

	this.alberoInterventiBean = alberoInterventiBean;
    }

    public Set<Integer> getCodiciEndoProcedimenti() {

	return codiciEndoProcedimenti;
    }

    public void setCodiciEndoProcedimenti(Set<Integer> codiciEndoProcedimenti) {

	this.codiciEndoProcedimenti = codiciEndoProcedimenti;
    }

    public Set<Integer> getCodiciFamiglieEndo() {

	return codiciFamiglieEndo;
    }

    public void setCodiciFamiglieEndo(Set<Integer> codiciFamiglieEndo) {

	this.codiciFamiglieEndo = codiciFamiglieEndo;
    }

    public Set<Integer> getCodiciTipiEndo() {

	return codiciTipiEndo;
    }

    public void setCodiciTipiEndo(Set<Integer> codiciTipiEndo) {

	this.codiciTipiEndo = codiciTipiEndo;
    }

    public Set<Integer> getCodiciSchedeEndo() {

	return codiciSchedeEndo;
    }

    public void setCodiciSchedeEndo(Set<Integer> codiciSchedeEndo) {

	this.codiciSchedeEndo = codiciSchedeEndo;
    }

    public Set<Integer> getCodiciAmministrazioniEndo() {

	return codiciAmministrazioniEndo;
    }

    public void setCodiciAmministrazioniEndo(Set<Integer> codiciAmministrazioniEndo) {

	this.codiciAmministrazioniEndo = codiciAmministrazioniEndo;
    }
}
