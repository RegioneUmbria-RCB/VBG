package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.math.BigDecimal;

public class PercentualeAssenzeBean {

    BigDecimal percentualeAssenze;
    Integer limite;

    public PercentualeAssenzeBean(BigDecimal percentualeAssenze, Integer limite) {

	this.percentualeAssenze = percentualeAssenze;
	this.limite = limite;
    }

    public BigDecimal getPercentualeAssenze() {

	return percentualeAssenze;
    }

    public void setPercentualeAssenze(BigDecimal percentualeAssenze) {

	this.percentualeAssenze = percentualeAssenze;
    }

    public Integer getLimite() {

	return limite;
    }

    public void setLimite(Integer limite) {

	this.limite = limite;
    }
}
