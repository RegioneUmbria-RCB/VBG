package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

public class PayPosizioniDebitorieFilter {

    private Integer id;
    private Boolean pagato;
    private Boolean presenza;
    private String descrizione;
    private String richiedente;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Boolean getPagato() {

	return pagato;
    }

    public void setPagato(Boolean pagato) {

	this.pagato = pagato;
    }

    public Boolean getPresenza() {

	return presenza;
    }

    public void setPresenza(Boolean presenza) {

	this.presenza = presenza;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    private String orderBy;
    private OrderTypeEnum orderAscDesc = OrderTypeEnum.ASC;

    public String getOrderBy() {

	return orderBy;
    }

    public void setOrderBy(String orderBy) {

	this.orderBy = orderBy;
    }

    public void setOrderAscDesc(OrderTypeEnum orderAscDesc) {

	this.orderAscDesc = orderAscDesc;
    }

    public OrderTypeEnum getOrderAscDesc() {

	return orderAscDesc;
    }
}
