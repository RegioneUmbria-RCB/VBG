/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;

/**
 * @author Franco.Leone
 *
 */
public class RichiesteHelper {

    private List<PayRichieste> richieste;

    public RichiesteHelper(List<PayRichieste> richieste) {

	this.richieste = richieste;
    }

    public List<PayRichieste> getRichieste() {

	if (richieste == null) {
	    richieste = new ArrayList<PayRichieste>();
	}
	return richieste;
    }

    public void setRichieste(List<PayRichieste> richieste) {

	this.richieste = richieste;
    }

    public void addRichiesta(PayRichieste rich) {

	this.getRichieste().add(rich);
    }

    public List<PayRichieste> findRichiestePerPosizione(PkId idPos, TipiEvento filterBy) {

	List<PayRichieste> found = new ArrayList<PayRichieste>();
	if (idPos != null && idPos.getCodice() != null) {
	    for (PayRichieste r : richieste) {
		if (r.getPosizioneDebitoria() != null && idPos.equals(r.getPosizioneDebitoria().getId())) {
		    boolean matchType = true;
		    if (filterBy != null) {
			matchType = filterBy.name().equals(r.getTipoRichiesta());
		    }
		    if (matchType) {
			found.add(r);
		    }
		}
	    }
	}
	return found;
    }

    public List<PayRichieste> findRichiestePerPosizione(PkId idPos) {

	return this.findRichiestePerPosizione(idPos, null);
    }
}
