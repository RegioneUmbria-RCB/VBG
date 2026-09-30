package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MenuSoftware implements Comparator<MenuSoftware> {

    private Integer codice;
    private String nome;
    private String id;
    private int ordine;
    private List<MenuSezione> sezioni;
    private MenuSezione sezioneBase = null;

    public MenuSoftware() {

	super();
    }

    public MenuSoftware(Software s, ClmenuBean menu) {

	this.codice = menu.getId();
	this.nome = menu.getDescrizione().replaceAll("SOFTWARE", s.getDescrizione()).replaceAll("Software", "software");
	this.sezioni = new ArrayList<MenuSezione>();
	this.id = s.getCodice();
	this.ordine = s.getOrdine();
	this.aggiungiSezione(menu.getChilds());
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public List<MenuSezione> getSezioni() {

	return sezioni;
    }

    public void setSezioni(List<MenuSezione> sezioni) {

	this.sezioni = sezioni;
    }

    public void aggiungiSezione(List<ClmenuBean> childs) {

	for (ClmenuBean clmenuBean : childs) {
	    if (clmenuBean.isAttivoIn(id)) {
		if (clmenuBean.getChilds().isEmpty()) {
		    // tutte le sezioni che non hanno figli
		    if (sezioneBase == null) {
			sezioneBase = new MenuSezione("Sezione Base");
			sezioni.add(sezioneBase);
		    }
		    sezioneBase.aggiungiVoce(clmenuBean);
		} else {
		    MenuSezione sezione = new MenuSezione(clmenuBean);
		    sezioni.add(sezione);
		}
	    }
	}
	Collections.sort(sezioni, new MenuSezione());
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int compare(MenuSoftware o1, MenuSoftware o2) {

	return o1.ordine - o2.ordine;
    }
}
