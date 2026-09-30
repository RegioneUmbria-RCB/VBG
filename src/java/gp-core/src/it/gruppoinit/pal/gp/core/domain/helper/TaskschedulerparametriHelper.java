package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

public class TaskschedulerparametriHelper {

    private Taskschedulerparametri taskschedulerparametri;
    private Integer ordine;
    private String descrizione;

    public TaskschedulerparametriHelper() {

	this.taskschedulerparametri = new Taskschedulerparametri();
    }

    public Taskschedulerparametri getTaskschedulerparametri() {

	return taskschedulerparametri;
    }

    public void setTaskschedulerparametri(Taskschedulerparametri taskschedulerparametri) {

	this.taskschedulerparametri = taskschedulerparametri;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
