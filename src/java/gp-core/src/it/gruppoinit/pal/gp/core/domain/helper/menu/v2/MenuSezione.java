package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MenuSezione implements Comparator<MenuSezione> {

    private Integer id = Integer.valueOf(-1);
    private String nome;
    private String menulink = "";
    // private List<ClmenuBean> menu = new ArrayList<ClmenuBean>();
    private List<VoceMenuBean> voci = new ArrayList<VoceMenuBean>();

    public MenuSezione() {

	super();
    }

    public MenuSezione(String descrizione) {

	super();
	this.nome = descrizione;
    }

    public MenuSezione(ClmenuBean clmenuBean) {

	this.id = clmenuBean.getId();
	this.nome = clmenuBean.getDescrizione();
	this.menulink = clmenuBean.getMenulink();
	creaVoci(clmenuBean.getChilds());
    }

    public List<VoceMenuBean> getVoci() {

	return voci;
    }

    public void setVoci(List<VoceMenuBean> voci) {

	this.voci = voci;
    }

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

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int compare(MenuSezione o1, MenuSezione o2) {

	if (o1 == null || o1.menulink == null)
	    return 1;
	if (o2 == null || o2.menulink == null)
	    return -1;
	return o1.menulink.compareTo(o2.menulink);
    }

    private void creaVoci(List<ClmenuBean> childs) {

	for (ClmenuBean clmenuBean : childs) {
	    aggiungiVoce(clmenuBean);
	}
    }

    public void aggiungiVoce(ClmenuBean clmenuBean) {

	VoceMenuBean vm = new VoceMenuBean(clmenuBean);
	this.voci.add(vm);
    }
}
