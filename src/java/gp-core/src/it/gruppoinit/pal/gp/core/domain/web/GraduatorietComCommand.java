package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class GraduatorietComCommand extends BaseCommand {

    private GraduatorietCom entity;
    private SchedaDinamicaFilter schedaDinamicaFilter;
    private String messageProtocolloNonAttivo;
    private CodiceDescrizioneBean firmatari;

    public GraduatorietComCommand() {

	this.entity = new GraduatorietCom();
	this.schedaDinamicaFilter = new SchedaDinamicaFilter();
	this.firmatari = new CodiceDescrizioneBean();
    }

    public GraduatorietCom getEntity() {

	return entity;
    }

    public void setEntity(GraduatorietCom entity) {

	this.entity = entity;
    }

    public SchedaDinamicaFilter getSchedaDinamicaFilter() {

	return schedaDinamicaFilter;
    }

    public void setSchedaDinamicaFilter(SchedaDinamicaFilter schedaDinamicaFilter) {

	this.schedaDinamicaFilter = schedaDinamicaFilter;
    }

    public String getMessageProtocolloNonAttivo() {

	return messageProtocolloNonAttivo;
    }

    public void setMessageProtocolloNonAttivo(String messageProtocolloNonAttivo) {

	this.messageProtocolloNonAttivo = messageProtocolloNonAttivo;
    }

    public CodiceDescrizioneBean getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(CodiceDescrizioneBean firmatari) {

	this.firmatari = firmatari;
    }
}
