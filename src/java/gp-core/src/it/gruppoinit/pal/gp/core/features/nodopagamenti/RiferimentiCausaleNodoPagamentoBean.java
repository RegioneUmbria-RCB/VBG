package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.apache.commons.lang.StringUtils;

public class RiferimentiCausaleNodoPagamentoBean implements Comparable<RiferimentiCausaleNodoPagamentoBean> {

    private String codiceVersamento;//--> mappaturaNodoPag
    public RiferimentiCausaleNodoPagamentoBean() {

	super();
    }

    public RiferimentiCausaleNodoPagamentoBean(String codiceVersamento, boolean b) {

	this();
	this.codiceVersamento = codiceVersamento;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 17;
	result = prime * result + ((codiceVersamento == null) ? 0 : codiceVersamento.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object ob) {

	if (ob == this) {
	    return true;
	}
	if (ob == null || ob.getClass() != getClass()) {
	    return false;
	}
	RiferimentiCausaleNodoPagamentoBean castOther = (RiferimentiCausaleNodoPagamentoBean) ob;
	return ((this.getCodiceVersamento() == castOther.getCodiceVersamento()) || (this.getCodiceVersamento() != null
		&& castOther.getCodiceVersamento() != null && this.getCodiceVersamento().equals(castOther.getCodiceVersamento())));
    }

    @Override
    public int compareTo(RiferimentiCausaleNodoPagamentoBean o2) {

	if (o2 == null) {
	    return -1;
	}
	String idCaus1 = StringUtils.defaultString(this.getCodiceVersamento());
	String idCaus2 = StringUtils.defaultString(o2.getCodiceVersamento());
	return idCaus1.compareTo(idCaus2);
    }
}
