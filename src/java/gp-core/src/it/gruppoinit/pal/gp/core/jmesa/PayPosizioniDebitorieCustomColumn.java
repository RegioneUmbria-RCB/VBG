package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.domain.helper.PayPosizionidebitorieListHelper;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class PayPosizioniDebitorieCustomColumn extends AbstractCellEditor {

    private HttpServletRequest request;
    private String propertyName;

    public PayPosizioniDebitorieCustomColumn(HttpServletRequest request, String propertyName) {

	super();
	this.request = request;
	this.propertyName = propertyName;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	PayPosizionidebitorieListHelper oggetto = (PayPosizionidebitorieListHelper) item;
	if (oggetto == null) {
	    return "";
	}
	if (propertyName.equalsIgnoreCase("presenzaspuntista")) {
	    if (oggetto.getPresenzaspuntista() == null || oggetto.getPresenzaspuntista().intValue() == 0) {
		return "No";
	    }
	    return "Sì";
	} else if (propertyName.equalsIgnoreCase("statopagato")) {
	    if (oggetto.getStatopagato() == null || oggetto.getStatopagato().intValue() == 0) {
		return "No";
	    }
	    return "Sì";
	} else if (propertyName.equalsIgnoreCase("statopagamentoattivo")) {
	    if (oggetto.getStatopagamentoattivo() == null || oggetto.getStatopagamentoattivo().intValue() == 0) {
		return "No";
	    }
	    return "Sì";
	} else if (propertyName.equalsIgnoreCase("idposizionedebitoria")) {
	    return "<a class='dettaglioColumn azioni_posizioni_debitorie' data-id='" + oggetto.getIdposizionedebitoria() + "' ><label>"
		    + oggetto.getIdposizionedebitoria() + "</label></a>&nbsp;" + oggetto.getIdposizionedebitoria();
	}
	return "";
    }
}
