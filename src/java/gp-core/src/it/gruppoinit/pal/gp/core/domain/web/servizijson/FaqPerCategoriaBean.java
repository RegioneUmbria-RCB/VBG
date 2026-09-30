package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class FaqPerCategoriaBean {

    private String id;
    private String descrizione;
    private List<FaqBean> faq = new ArrayList<FaqBean>();

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public List<FaqBean> getFaq() {

	return faq;
    }

    public void setFaq(List<FaqBean> faq) {

	this.faq = faq;
    }
}
