package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.filters.TipoRicercaEnum;

import java.io.Serializable;

public class AnagrafeFilter implements Serializable {

    private static final long serialVersionUID = -6775848859728222359L;
    private Anagrafe datiAnagrafe;
    private String[] orderBy;
    private OrderTypeEnum[] orderAscDesc;
    private DAOEnum defaultWhereCondition;
    private TipoRicercaEnum tipoRicercaEnum;

    public AnagrafeFilter() {

	this.datiAnagrafe = new Anagrafe();
	this.orderAscDesc = new OrderTypeEnum[] { OrderTypeEnum.ASC, OrderTypeEnum.ASC };
	this.defaultWhereCondition = DAOEnum.FIND_BY_IDCOMUNE;
	this.orderBy = new String[] { "nominativo", "nome" };
	this.tipoRicercaEnum = TipoRicercaEnum.LIKE;
    }

    public Anagrafe getDatiAnagrafe() {

	return datiAnagrafe;
    }

    public void setDatiAnagrafe(Anagrafe datiAnagrafe) {

	this.datiAnagrafe = datiAnagrafe;
    }

    public String[] getOrderBy() {

	return orderBy;
    }

    public void setOrderBy(String[] orderBy) {

	this.orderBy = orderBy;
    }

    public OrderTypeEnum[] getOrderAscDesc() {

	return orderAscDesc;
    }

    public void setOrderAscDesc(OrderTypeEnum[] orderAscDesc) {

	this.orderAscDesc = orderAscDesc;
    }

    public DAOEnum getDefaultWhereCondition() {

	return defaultWhereCondition;
    }

    public void setDefaultWhereCondition(DAOEnum defaultWhereCondition) {

	this.defaultWhereCondition = defaultWhereCondition;
    }

    public TipoRicercaEnum getTipoRicercaEnum() {

	return tipoRicercaEnum;
    }

    public void setTipoRicercaEnum(TipoRicercaEnum tipoRicercaEnum) {

	this.tipoRicercaEnum = tipoRicercaEnum;
    }
}
