package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

public interface IStatiComunicazioniService<TConfig> {

    public void impostaStatoDettaglio(int idDettaglioComunicazione, String nuovoStato, TConfig configurazione);
}
