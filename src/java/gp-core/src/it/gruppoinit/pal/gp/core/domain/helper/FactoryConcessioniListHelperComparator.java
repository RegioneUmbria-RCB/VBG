package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.util.Comparator;

public class FactoryConcessioniListHelperComparator {

    private DAOOrderTypeEnum orderTypeEnum;
    private String campiDaOrdinare;

    /**
     * <spring-form:option value="CONC_NUMERO,CONC_DATARILASCIO"><fmt:message
     * key="label.numero_data_rilascio"/></spring-form:option> <spring-form:option
     * value="CONC_DATARILASCIO,CONC_NUMERO"><fmt:message key="label.data_rilascio_numero"/></spring-form:option>
     * <spring-form:option value="DATA_ISTANZA"><fmt:message key="label.data_presentazione"/></spring-form:option>
     * <spring-form:option value="CONC_CODICETITOLARE"><fmt:message key="label.richiedente"/></spring-form:option>
     * <spring-form:option value="IST_NUMEROISTANZA"><fmt:message key="label.num_istanza"/></spring-form:option>
     * <spring-form:option value="STRADARIO_DESCRIZIONE"><fmt:message key="label.localizzazione"/></spring-form:option>
     **/
    public FactoryConcessioniListHelperComparator(String campiDaOrdinare, DAOOrderTypeEnum orderTypeEnum) {

	this.campiDaOrdinare = campiDaOrdinare;
	this.orderTypeEnum = orderTypeEnum;
    }

    public Comparator createConcessioniListHelperComparator() {

	if (campiDaOrdinare.equals("CONC_NUMERO,CONC_DATARILASCIO")) {
	    return new ConcessioniListHelperNumeroAndRilascioComparator(orderTypeEnum);
	} else if (campiDaOrdinare.equals("CONC_DATARILASCIO,CONC_NUMERO")) {
	    return new ConcessioniListHelperRilascioAndNumeroComparator(orderTypeEnum);
	} else if (campiDaOrdinare.equals("DATA_ISTANZA")) {
	    return new ConcessioniListHelperDataPresentazioneComparator(orderTypeEnum);
	} else if (campiDaOrdinare.equals("CONC_CODICETITOLARE")) {
	    return new ConcessioniListHelperRichiedenteComparator(orderTypeEnum);
	} else if (campiDaOrdinare.equals("IST_NUMEROISTANZA")) {
	    return new ConcessioniListHelperNumeroIstanzaComparator(orderTypeEnum);
	} else if (campiDaOrdinare.equals("STRADARIO_DESCRIZIONE")) {
	    return new ConcessioniListHelperLocalizzazioneComparator(orderTypeEnum);
	}
	return null;
    }
}
