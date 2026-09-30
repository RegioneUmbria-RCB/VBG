package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.ArrayList;
import java.util.List;

public class DettaglioErroreRestBean {

    private int status;
    private String code;
    private String title;
    private List<ChiaveValoreBean<String, String>> detail;
    private List<String> links;

    public int getStatus() {

	return status;
    }

    public void setStatus(int status) {

	this.status = status;
    }

    public String getCode() {

	return code;
    }

    public void setCode(String code) {

	this.code = code;
    }

    public String getTitle() {

	return title;
    }

    public void setTitle(String title) {

	this.title = title;
    }

    public List<ChiaveValoreBean<String, String>> getDetail() {

	if (detail == null) {
	    detail = new ArrayList<ChiaveValoreBean<String, String>>();
	}
	return detail;
    }

    public void setDetail(List<ChiaveValoreBean<String, String>> detail) {

	this.detail = detail;
    }

    public List<String> getLinks() {

	return links;
    }

    public void setLinks(List<String> links) {

	this.links = links;
    }
}
