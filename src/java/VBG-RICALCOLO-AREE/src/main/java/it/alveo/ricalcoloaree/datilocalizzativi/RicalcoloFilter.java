package it.alveo.ricalcoloaree.datilocalizzativi;

import java.util.Date;

public class RicalcoloFilter {

    private String software;
    private Date dallaData;
    private Date allaData;

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }
}
