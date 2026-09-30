package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTService;

import java.util.List;

import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.springframework.web.context.ContextLoader;

public class MercatoDaElaborareCellEditor extends AbstractCellEditor {

    private MercatipresenzeTService mercatipresenzeTService;

    public MercatoDaElaborareCellEditor() {
	
	this.mercatipresenzeTService = (MercatipresenzeTService) ContextLoader.getCurrentWebApplicationContext().getBean(
		"mercatipresenzeTServiceImpl", MercatipresenzeTService.class);
	
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Mercati m = (Mercati) ItemUtils.getItemValue(item, "mercato");
	Integer annoI = (Integer) ItemUtils.getItemValue(item, "anno");
	String valueItem = m.getDescrizione();
	if (m.getTipoconteggioPresenze() != null && m.getTipoconteggioPresenze().equals(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA)) {
	    List<Integer> anniDaConsolidare = mercatipresenzeTService.findAnniDaConsolidare(m.getId().getCodice());
	    if (anniDaConsolidare != null) {
		for (Integer anno : anniDaConsolidare) {
		    if (annoI.equals(anno)) {
			valueItem += "&nbsp;<span id=\"da_consolidare_" + anno.intValue() + "_" + m.getId().getCodice()
				+ "\">(<a href=\"javascript:consolidaAnno(" + anno.intValue() + ",'" + m.getId().getCodice() + "')\">"
				+ anno.intValue() + "<image style=\"vertical-align:middle;\" border=\"0\" src=\""+getWebContext().getContextPath()+"/images/warning.gif\" /></a>)</span>";
		    }
		}
	    }
	}
	return valueItem;
    }
}
