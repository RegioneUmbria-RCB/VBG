package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

public interface IConfigurazioneComunicazione {

    public void inizializzaDaDatiDb(ConfigurazioneFlyweight configurazioneFlyweight);

    public ConfigurazioneFlyweight getParametriPerDb();
}
