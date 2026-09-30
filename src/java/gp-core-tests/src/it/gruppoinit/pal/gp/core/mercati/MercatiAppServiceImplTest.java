package it.gruppoinit.pal.gp.core.mercati;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MercatiAppServiceImplTest {

    @Test()
    public void replaceDescrizioneGiornoSostituisceCorrettamenteIlGiorno() {

	Calendar c = Calendar.getInstance();
	c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	String desc = Utilities.replaceDescrizioneGiorno("CINCINNATO MAR 23/06/1973", c.getTime());
	Assert.assertTrue("Deve restituire CINCINNATO LUN 23/06/1973", desc.equals("CINCINNATO LUN 23/06/1973"));
    }
}
