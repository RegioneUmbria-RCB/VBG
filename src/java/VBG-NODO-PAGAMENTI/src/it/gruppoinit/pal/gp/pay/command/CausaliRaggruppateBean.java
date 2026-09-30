package it.gruppoinit.pal.gp.pay.command;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceTassonomia;

public class CausaliRaggruppateBean implements Comparable<CausaliRaggruppateBean> {

    private String codiceVersamento;
    private String codiceTassonomia;

    public String getCodiceTassonomia() {

	return codiceTassonomia;
    }

    public void setCodiceTassonomia(String codiceTassonomia) {

	this.codiceTassonomia = codiceTassonomia;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    @Override
    public int compareTo(CausaliRaggruppateBean castOther) {

	if (this.equals(castOther)) {
	    return 0;
	}
	if ((castOther == null)) {
	    return 1;
	}
	int compareVersamento = StringUtils.defaultString(this.getCodiceVersamento()).toLowerCase()
		.compareTo(StringUtils.defaultString(castOther.getCodiceVersamento()).toLowerCase());
	if (compareVersamento != 0) {
	    return compareVersamento;
	}
	return StringUtils.defaultString(this.getCodiceTassonomia()).toLowerCase()
		.compareTo(StringUtils.defaultString(castOther.getCodiceTassonomia()).toLowerCase());
    }

    @Override
    public boolean equals(Object castOther) {

	if ((this == castOther)) {
	    return true;
	}
	if ((castOther == null)) {
	    return false;
	}
	if (!(castOther instanceof CausaliRaggruppateBean)) {
	    return false;
	}
	if (!StringUtils.defaultString(this.getCodiceVersamento())
		.equalsIgnoreCase(StringUtils.defaultString(((CausaliRaggruppateBean) castOther).getCodiceVersamento()))) {
	    return false;
	}
	return StringUtils.defaultString(this.getCodiceTassonomia())
		.equalsIgnoreCase(StringUtils.defaultString(((CausaliRaggruppateBean) castOther).getCodiceTassonomia()));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getCodiceVersamento() == null ? 0 : this.getCodiceVersamento().hashCode());
	result = 37 * result + (getCodiceTassonomia() == null ? 0 : this.getCodiceTassonomia().hashCode());
	return result;
    }

    public static CausaliRaggruppateBean fromRegistrazioniCausali(PayRegistrazioniCausali prc) {

	CausaliRaggruppateBean ret = new CausaliRaggruppateBean();
	ret.setCodiceVersamento(prc.getCodiceVersamento());
	for (PayRegcausaliParametri rp : prc.getRegParams()) {
	    if (rp.getChiave().equalsIgnoreCase(new ParametroCodiceTassonomia().getNomeParametro())) {
		ret.setCodiceTassonomia(rp.getValore());
		break;
	    }
	}
	return ret;
    }
}
