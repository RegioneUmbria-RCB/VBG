package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;

public class ConfigurazioneBaseTest {

    @Test
    public void getValoreConfigurazioneRiportaIDatiDiDefaultInteroSeNonSpecificato() {

	ConfigurazioneBase base = new ConfigurazioneBase();
	List<ParametroConfigurazioneComunicazione> l = new ArrayList<ParametroConfigurazioneComunicazione>();
	Integer valoreDaConfigurazione = base.getValoreDaConfigurazione(l, ConfigurazioneComunicazioniBollettazione.ID_BOLLETTAZIONE, 1);
	Assert.assertTrue("Il valore da tornare è 1 ", valoreDaConfigurazione.equals(1));
    }

    @Test
    public void getValoreConfigurazioneRiportaIDatiDiDefaultBooleanoSeNonSpecificato() {

	ConfigurazioneBase base = new ConfigurazioneBase();
	List<ParametroConfigurazioneComunicazione> l = new ArrayList<ParametroConfigurazioneComunicazione>();
	Boolean b = base.getValoreDaConfigurazione(l, ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, false);
	Assert.assertTrue("Il valore da tornare è false ", b.equals(false));
    }

    @Test
    public void getValoreConfigurazioneRiportaIDatiBooleaniCorrettiSeSpecificati() {

	ConfigurazioneBase base = new ConfigurazioneBase();
	List<ParametroConfigurazioneComunicazione> l = new ArrayList<ParametroConfigurazioneComunicazione>();
	l.add(new ParametroConfigurazioneComunicazione(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, "1"));
	l.add(new ParametroConfigurazioneComunicazione(ConfigurazioneComunicazioniBollettazione.ID_BOLLETTAZIONE, "2"));
	Boolean b = base.getValoreDaConfigurazione(l, ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, false);
	Assert.assertTrue("Il valore da tornare è true ", b.equals(true));
    }

    @Test
    public void getValoreConfigurazioneRiportaIDatiInteriCorrettiSeSpecificati() {

	ConfigurazioneBase base = new ConfigurazioneBase();
	List<ParametroConfigurazioneComunicazione> l = new ArrayList<ParametroConfigurazioneComunicazione>();
	l.add(new ParametroConfigurazioneComunicazione(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, "true"));
	l.add(new ParametroConfigurazioneComunicazione(ConfigurazioneComunicazioniBollettazione.ID_BOLLETTAZIONE, "2"));
	Integer valoreDaConfigurazione = base.getValoreDaConfigurazione(l, ConfigurazioneComunicazioniBollettazione.ID_BOLLETTAZIONE, 1);
	Assert.assertTrue("Il valore da tornare è 1 ", valoreDaConfigurazione.equals(2));
    }
}
