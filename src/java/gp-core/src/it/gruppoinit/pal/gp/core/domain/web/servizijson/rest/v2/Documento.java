package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

public class Documento {

    private String id;
    private String nome;
    private String file;
    private String link;

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

    public String getFile() {

	return file;
    }

    public void setFile(String file) {

	this.file = file;
    }

    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }
}
