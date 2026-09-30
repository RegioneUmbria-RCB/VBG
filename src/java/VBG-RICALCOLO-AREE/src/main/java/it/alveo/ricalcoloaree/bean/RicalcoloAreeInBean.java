package it.alveo.ricalcoloaree.bean;

import java.util.Date;
import java.util.List;

public class RicalcoloAreeInBean {

    private Date dateDA;
    private Date dateA;
    private String software;
    private String idcomune;
    private String pk;
    private List<Integer> idAree;
    private String type;
    private List<Integer> codiciIstanze; //Fatto per le istanze

    public RicalcoloAreeInBean(Date dateDA, Date dateA, String software, String idcomune, String pk, List<Integer> idAree) {

	this.dateDA = dateDA;
	this.dateA = dateA;
	this.software = software;
	this.idcomune = idcomune;
	this.pk = pk;
	this.idAree = idAree;
    }

    public RicalcoloAreeInBean(Date dateDA, Date dateA, String software, String idcomune, String pk, List<Integer> idAree, String type,
            List<Integer> codiciIstanze) {

	this.dateDA = dateDA;
	this.dateA = dateA;
	this.software = software;
	this.idcomune = idcomune;
	this.pk = pk;
	this.idAree = idAree;
	this.type = type;
	this.codiciIstanze = codiciIstanze;
    }

    public Date getDateDA() {

	return dateDA;
    }

    public void setDateDA(Date dateDA) {

	this.dateDA = dateDA;
    }

    public Date getDateA() {

	return dateA;
    }

    public void setDateA(Date dateA) {

	this.dateA = dateA;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public String getPk() {

	return pk;
    }

    public void setPk(String pk) {

	this.pk = pk;
    }

    public List<Integer> getIdAree() {

	return idAree;
    }

    public void setIdAree(List<Integer> idAree) {

	this.idAree = idAree;
    }

    public String getType() {

	return type;
    }

    public void setType(String type) {

	this.type = type;
    }

    public List<Integer> getCodiciIstanze() {
        return codiciIstanze;
    }

    public void setCodiciIstanze(List<Integer> codiciIstanze) {
        this.codiciIstanze = codiciIstanze;
    }

}
