package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.attivita.FakeDenominazioneResolverDAO;
import it.gruppoinit.pal.gp.core.features.attivita.FakeVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;

public class DenominazioneResolverTests {

    private IVerticalizzazioneIAttivitaService getVerticalizzazioneService() {

	return new FakeVerticalizzazioneIAttivitaService();
    }

    private IDenominazioneResolverDAO getIDenominazioneResolverDAO() {

	return new FakeDenominazioneResolverDAO("TEST COMPANY SRL");
    }

    @Test(expected = IllegalArgumentException.class)
    public void NuovoDenominazioneResolverSenzaServiceVerticalizzazioneTornaIllegalArgumentException() {

	new DenominazioneResolver(null, this.getIDenominazioneResolverDAO(), "", new Istanze());
    }

    @Test(expected = IllegalArgumentException.class)
    public void NuovoDenominazioneResolverSenzaDAODenominazioneTornaIllegalArgumentException() {

	new DenominazioneResolver(this.getVerticalizzazioneService(), null, "", new Istanze());
    }

    @Test(expected = IllegalArgumentException.class)
    public void NuovoDenominazioneResolverSenzaIstanzaTornaIllegalArgumentException() {

	new DenominazioneResolver(this.getVerticalizzazioneService(), this.getIDenominazioneResolverDAO(), "", null);
    }
}
