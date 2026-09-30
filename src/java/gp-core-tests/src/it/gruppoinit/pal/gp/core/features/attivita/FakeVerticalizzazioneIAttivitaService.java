package it.gruppoinit.pal.gp.core.features.attivita;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;

public class FakeVerticalizzazioneIAttivitaService implements IVerticalizzazioneIAttivitaService {

    private boolean attiva;
    private boolean aggiornaDenominazione;
    private Dyn2Campi campoDynFineAtt;
    private String gruppoSoftware;
    private boolean invertiRichiedenteStorico;
    private boolean nonConsiderareIstanzeCollegate;
    private boolean precompilaIndirizzoCivico;
    private String queryDenominazione;

    public FakeVerticalizzazioneIAttivitaService() {

    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public boolean isAggiornaDenominazione() {

	return this.aggiornaDenominazione;
    }

    @Override
    public Dyn2Campi getCampoDynFineAtt() {

	return this.campoDynFineAtt;
    }

    @Override
    public String getGruppoSoftware() {

	return this.gruppoSoftware;
    }

    @Override
    public boolean isInvertiRichiedenteStorico() {

	return this.invertiRichiedenteStorico;
    }

    @Override
    public boolean isNonConsiderareIstanzeCollegate() {

	return this.nonConsiderareIstanzeCollegate;
    }

    @Override
    public boolean isPrecompilaIndirizzoCivico() {

	return this.precompilaIndirizzoCivico;
    }

    @Override
    public String getQueryDenominazione() {

	return this.queryDenominazione;
    }
}