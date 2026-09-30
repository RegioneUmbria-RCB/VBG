package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClmenuBean {

    private Integer id;
    private String descrizione;
    private String pagina;
    private String menulink;
    private String software;
    private String jsp;
    private String verticalizzazione;
    private String softwareesclusi;
    private String linkStandard;
    private String tipoFunzionalita;
    private String layouttesti;
    private String ordinamento;
    private List<ClmenuBean> childs = new ArrayList<ClmenuBean>();
    private Set<String> listasoftware = new HashSet<String>();

    public ClmenuBean() {

	super();
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getPagina() {

	return pagina;
    }

    public void setPagina(String pagina) {

	this.pagina = pagina;
    }

    public String getMenulink() {

	return menulink;
    }

    public void setMenulink(String menulink) {

	this.menulink = menulink;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getJsp() {

	return jsp;
    }

    public void setJsp(String jsp) {

	this.jsp = jsp;
    }

    public String getVerticalizzazione() {

	return verticalizzazione;
    }

    public void setVerticalizzazione(String verticalizzazione) {

	this.verticalizzazione = verticalizzazione;
    }

    public String getSoftwareesclusi() {

	return softwareesclusi;
    }

    public void setSoftwareesclusi(String softwareesclusi) {

	this.softwareesclusi = softwareesclusi;
    }

    public String getLinkStandard() {

	return linkStandard;
    }

    public void setLinkStandard(String linkStandard) {

	this.linkStandard = linkStandard;
    }

    public String getTipoFunzionalita() {

	return tipoFunzionalita;
    }

    public void setTipoFunzionalita(String tipoFunzionalita) {

	this.tipoFunzionalita = tipoFunzionalita;
    }

    public String getLayouttesti() {

	return layouttesti;
    }

    public void setLayouttesti(String layouttesti) {

	this.layouttesti = layouttesti;
    }

    public String getOrdinamento() {

	return ordinamento;
    }

    public void setOrdinamento(String ordinamento) {

	this.ordinamento = ordinamento;
    }

    public List<ClmenuBean> getChilds() {

	return childs;
    }

    public void setChilds(List<ClmenuBean> childs) {

	this.childs = childs;
    }

    public String getIdPadre() {

	if (menulink.length() == 1) {
	    return null;
	}
	return menulink.substring(0, (menulink.length() - 1));
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public void aggiungiSoftware(String codiceSoftware) {

	this.listasoftware.add(codiceSoftware);
    }

    public boolean isAttivoIn(String codiceSoftware) {

	return this.listasoftware.contains(codiceSoftware);
    }
}
