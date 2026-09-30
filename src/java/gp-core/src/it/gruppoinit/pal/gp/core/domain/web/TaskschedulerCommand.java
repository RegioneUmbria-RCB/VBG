package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Taskbase;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.helper.TaskschedulerparametriHelper;

import java.util.List;

public class TaskschedulerCommand extends BaseCommand {

    private Taskscheduler entity;
    private Integer giorni;
    private Integer ore;
    private Integer minuti;
    private String hhmmss;
    private List<Taskbase> operazioneList;
    private List<TaskschedulerparametriHelper> taskschedulerparametriHelpers;

    public TaskschedulerCommand() {

	this.entity = new Taskscheduler();
    }

    public Taskscheduler getEntity() {

	return entity;
    }

    public void setEntity(Taskscheduler entity) {

	this.entity = entity;
    }

    public Integer getGiorni() {

	return giorni;
    }

    public void setGiorni(Integer giorni) {

	this.giorni = giorni;
    }

    public Integer getOre() {

	return ore;
    }

    public void setOre(Integer ore) {

	this.ore = ore;
    }

    public Integer getMinuti() {

	return minuti;
    }

    public void setMinuti(Integer minuti) {

	this.minuti = minuti;
    }

    public String getHhmmss() {

	return hhmmss;
    }

    public void setHhmmss(String hhmmss) {

	this.hhmmss = hhmmss;
    }

    public List<Taskbase> getOperazioneList() {

	return operazioneList;
    }

    public void setOperazioneList(List<Taskbase> operazioneList) {

	this.operazioneList = operazioneList;
    }

    public List<TaskschedulerparametriHelper> getTaskschedulerparametriHelpers() {

	return taskschedulerparametriHelpers;
    }

    public void setTaskschedulerparametriHelpers(List<TaskschedulerparametriHelper> taskschedulerparametriHelpers) {

	this.taskschedulerparametriHelpers = taskschedulerparametriHelpers;
    }
}
