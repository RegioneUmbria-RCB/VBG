package it.gruppoinit.domain;

import java.util.HashSet;
import java.util.Set;

public class TipiCodiciCollegatiEndo {

    private Set<Integer> codiciEndoProcedimenti = new HashSet<Integer>();
    private Set<Integer> codiciFamiglieEndo = new HashSet<Integer>();
    private Set<Integer> codiciTipiEndo = new HashSet<Integer>();
    private Set<Integer> codiciSchedeEndo = new HashSet<Integer>();
    private Set<Integer> codiciAmministrazioniEndo = new HashSet<Integer>();

    public TipiCodiciCollegatiEndo() {

	super();
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
