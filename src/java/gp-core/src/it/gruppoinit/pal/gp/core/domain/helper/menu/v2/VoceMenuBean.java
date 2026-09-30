package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

public class VoceMenuBean {

    private String id;
    private String nome;
    private String link;
    private String paginaInternal;
    private String linkStandardInternal;
    private String jspInternal;

    public VoceMenuBean() {

	super();
    }

    public VoceMenuBean(ClmenuBean clmenuBean) {

	this.id = String.valueOf(clmenuBean.getId());
	this.nome = clmenuBean.getDescrizione();
	this.link = clmenuBean.getPagina();
	this.paginaInternal = clmenuBean.getPagina();
	this.linkStandardInternal = clmenuBean.getLinkStandard();
	this.jspInternal = clmenuBean.getJsp();
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    public String getPaginaInternal() {

	return paginaInternal;
    }

    public void setPaginaInternal(String paginaInternal) {

	this.paginaInternal = paginaInternal;
    }

    public String getLinkStandardInternal() {

	return linkStandardInternal;
    }

    public void setLinkStandardInternal(String linkStandardInternal) {

	this.linkStandardInternal = linkStandardInternal;
    }

    public String getJspInternal() {

	return jspInternal;
    }

    public void setJspInternal(String jspInternal) {

	this.jspInternal = jspInternal;
    }
}
