package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;

import java.io.Serializable;

public class SchedaDinamicaRigheFilter implements Serializable {

    private static final long serialVersionUID = -401550362322327067L;
    private AndOrRestriction andOr;
    private String parentesiSx;
    private FieldOperationsEnum tipoConfronto;
    private String parentesiDx;
    private Dyn2Campi campo;
    private String valore;
    private String tipoDato;

    public SchedaDinamicaRigheFilter() {

	campo = new Dyn2Campi();
	andOr = AndOrRestriction.AND;
    }

    public AndOrRestriction getAndOr() {

	return andOr;
    }

    public void setAndOr(AndOrRestriction andOr) {

	this.andOr = andOr;
    }

    public String getParentesiSx() {

	return parentesiSx;
    }

    public void setParentesiSx(String parentesiSx) {

	this.parentesiSx = parentesiSx;
    }

    public String getParentesiDx() {

	return parentesiDx;
    }

    public void setParentesiDx(String parentesiDx) {

	this.parentesiDx = parentesiDx;
    }

    public Dyn2Campi getCampo() {

	return campo;
    }

    public void setCampo(Dyn2Campi campo) {

	this.campo = campo;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public FieldOperationsEnum getTipoConfronto() {

	return tipoConfronto;
    }

    public void setTipoConfronto(FieldOperationsEnum tipoConfronto) {

	this.tipoConfronto = tipoConfronto;
    }

    public String getTipoDato() {

	return tipoDato;
    }
}
