package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class MappaMercatoBean {

    private String mappa;
    private String contentType;
    private List<PosteggioMercatoBean> posteggi;

    public MappaMercatoBean() {

	this.contentType = "application/jpg";
    }

    public String getMappa() {

	return mappa;
    }

    public void setMappa(String mappa) {

	this.mappa = mappa;
    }

    public String getContentType() {

	return contentType;
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public List<PosteggioMercatoBean> getPosteggi() {

	return posteggi;
    }

    public void setPosteggi(List<PosteggioMercatoBean> posteggi) {

	this.posteggi = posteggi;
    }
}
