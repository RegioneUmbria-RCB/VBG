package it.gruppoinit.pal.gp.core.features.attivita.snapshots;

import java.util.Calendar;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;

public class ParametriCalcoloSnapshotTest {

    @Test(expected = IllegalArgumentException.class)
    public void costruttoreConParametriVuotiRilanciaEccezioneSeTuttiParametriNulli() {

	new ParametriCalcoloSnapshot(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttoreConParametriVuotiRilanciaEccezioneSeAttivitaNlla() {

	new ParametriCalcoloSnapshot(null, Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttoreConParametriVuotiRilanciaEccezioneSeDataNulla() {

	new ParametriCalcoloSnapshot(Integer.valueOf(0), null, Calendar.getInstance().getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttoreConParametriVuotiRilanciaEccezioneSeComportamentoNullo() {

	new ParametriCalcoloSnapshot(Integer.valueOf(0), Calendar.getInstance().getTime(), null);
    }
}
