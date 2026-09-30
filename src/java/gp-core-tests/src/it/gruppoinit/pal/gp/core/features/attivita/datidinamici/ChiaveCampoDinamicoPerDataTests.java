package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ChiaveCampoDinamicoPerDataTests {

    @Test
    public void AggiungendoDueChiaviIdenticheLaMappaNonCreaDueElementi() {

	Map<ChiaveCampoDinamicoPerData, Boolean> mappa = new HashMap<ChiaveCampoDinamicoPerData, Boolean>();
	Calendar dataSnapshot = new GregorianCalendar();
	dataSnapshot.set(1983, 6, 26);
	mappa.put(new ChiaveCampoDinamicoPerData(dataSnapshot.getTime(), 125, 0, 5), true);
	mappa.put(new ChiaveCampoDinamicoPerData(dataSnapshot.getTime(), 125, 0, 5), false);
	assertEquals("Gli elementi della mappa aspettati erano 1 e invece sono più", 1, mappa.keySet().size());
	assertFalse("Doveva ritornare false invece è tornato true", mappa.entrySet().iterator().next().getValue());
    }

    @Test
    public void AggiungoChiaviValorizzandoleSempreNuove() {

	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	Calendar dataSnapshot = new GregorianCalendar();
	dataSnapshot.set(1983, 6, 26);
	for (int i = 0; i < 20; i++) {
	    ChiaveCampoDinamicoPerData retChiave = new ChiaveCampoDinamicoPerData(dataSnapshot.getTime(), 1, 0, i);
	    mappa.put(retChiave, new ValoreCampoPresente(String.valueOf(i), String.valueOf(i)));
	}
	assertEquals("Gli elementi della mappa aspettati erano 1 e invece sono più", 20, mappa.keySet().size());
	ChiaveCampoDinamicoPerData val1 = null;
	ChiaveCampoDinamicoPerData val2 = null;
	for (ChiaveCampoDinamicoPerData chiave : mappa.keySet()) {
	    if (val1 == null) {
		val1 = chiave;
	    } else {
		if (val2 == null) {
		    val2 = chiave;
		} else {
		    break;
		}
	    }
	}
	assertNotEquals("Le prime due chiavi aspettate dovevano essere diverse e invece sono uguali", val1.hashCode(), val2.hashCode());
	assertNotEquals("Le prime due chiavi aspettate dovevano essere diverse e invece sono uguali", val1, val2);
    }
}
