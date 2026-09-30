package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class Merceologie<E> extends SegnapostoTestualeBaseConValoreSingolo {

    @Override
    public String getNome() {

	return "MERCEOLOGIE";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	Set<Autorizzazioni> auts = data.getIstanza().getAutorizzazionis();
	StringBuffer sb = null;
	if (auts != null) {
	    for (Autorizzazioni autorizzazioni : auts) {
		sb = new StringBuffer();
		List<AutorizzazioniAttivita> attivta = new ArrayList<AutorizzazioniAttivita>(autorizzazioni.getAutAts());
		for (AutorizzazioniAttivita att : attivta) {
		    sb.append(att.getAttivita().getIstat());
		    sb.append(RtfConstants.RTF_CRLF);
		}
	    }
	}
	return StringUtils.removeEnd(sb.toString(), RtfConstants.RTF_CRLF);
    }
}
