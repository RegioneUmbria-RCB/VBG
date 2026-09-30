package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.List;

public class AtecoCommand implements Serializable {

    private static final long serialVersionUID = 3232761379865049238L;
    private Integer root;
    private Integer id;
    private String codice;
    private String titolo;
    private String descrizione;
    private String codiceDescrizione;
    private Boolean padre;
    private List<AtecoChildrenCommand> children;

    public AtecoCommand() {

	super();
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public Boolean getPadre() {

	return padre;
    }

    public void setPadre(Boolean padre) {

	this.padre = padre;
    }

    public List<AtecoChildrenCommand> getChildren() {

	return children;
    }

    public void setChildren(List<AtecoChildrenCommand> children) {

	this.children = children;
    }

    public String getCodiceDescrizione() {

	return codiceDescrizione;
    }

    public void setCodiceDescrizione(String codiceDescrizione) {

	this.codiceDescrizione = codiceDescrizione;
    }

    public Integer getRoot() {

	return root;
    }

    public void setRoot(Integer root) {

	this.root = root;
    }
}
