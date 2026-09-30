package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

public interface IConfigurazioneComunicazioneService {

    public void getById(int idTestata, IConfigurazioneComunicazione configurazione);

    public void getByIdDettaglioComunicazione(int idDettaglio, IConfigurazioneComunicazione configurazione);
}
