package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MenuHelper {

    public MenuHelper() {

	super();
    }

    public MenuHelper(ClmenuBean menu, List<Software> softwares) {

	this.id = menu.getId();
	this.nome = menu.getDescrizione();
	this.menulink = menu.getMenulink();
	this.softwaresAttivi = softwares;
    }

    private Integer id;
    private String nome;
    private String menulink;
    private List<MenuSoftware> software;
    private List<Software> softwaresAttivi;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public List<MenuSoftware> getSoftware() {

	return software;
    }

    public void setSoftware(List<MenuSoftware> software) {

	this.software = software;
    }

    public String getMenulink() {

	return menulink;
    }

    public void setMenulink(String menulink) {

	this.menulink = menulink;
    }

    public void aggiungiFigli(List<ClmenuBean> list) {

	this.software = new ArrayList<MenuSoftware>();
	for (Software s : softwaresAttivi) {
	    for (ClmenuBean clmenuBean : list) {
		if (clmenuBean.isAttivoIn(s.getCodice())) {
		    MenuSoftware ms = new MenuSoftware(s, clmenuBean);
		    software.add(ms);
		}
	    }
	}
	Collections.sort(this.software, new MenuSoftware());
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
