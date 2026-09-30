package it.gruppoinit.pal.gp.pay.ws.rest;

import javax.xml.bind.annotation.XmlElement;

public class BasePagamentiRestRequest {

    @XmlElement()
    private String topic;
    @XmlElement()
    private String dataInizioRicerca;
    @XmlElement()
    private Integer limit;
    @XmlElement()
    private Integer offset;

    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }

    public String getDataInizioRicerca() {

	return dataInizioRicerca;
    }

    public void setDataInizioRicerca(String dataInizioRicerca) {

	this.dataInizioRicerca = dataInizioRicerca;
    }

    public Integer getLimit() {

	return limit;
    }

    public void setLimit(Integer limit) {

	this.limit = limit;
    }

    public Integer getOffset() {

	return offset;
    }

    public void setOffset(Integer offset) {

	this.offset = offset;
    }
}
