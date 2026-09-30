package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

/**
 * 
 * @author gianpaolot Classe non appartenende al dominio utilizzata come model per Acquisire parametri da utilizzare
 *         come filtri
 */
public class AssenzeFilter {

    private Integer anno;
    private Mercati mercati;
    private MercatiUso mercatiUso;
    private BigDecimal assenze;

    public AssenzeFilter() {

	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public BigDecimal getAssenze() {

	return assenze;
    }

    public void setAssenze(BigDecimal assenze) {

	this.assenze = assenze;
    }
}
