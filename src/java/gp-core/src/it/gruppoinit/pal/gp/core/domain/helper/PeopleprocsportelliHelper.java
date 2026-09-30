package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Software;

public class PeopleprocsportelliHelper {

    private Set<String> codicecomune;
    private String peopleProc;
    private Software software;

    public PeopleprocsportelliHelper() {

	this.software = new Software();
    }

    public Set<String> getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(Set<String> codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getPeopleProc() {

	return peopleProc;
    }

    public void setPeopleProc(String peopleProc) {

	this.peopleProc = peopleProc;
    }

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }
}
