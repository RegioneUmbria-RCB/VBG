package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.soggetti;

public interface ITipimovTipiSoggettoService {

    public void elimina(int id);

    public int aggiungi(String idTipoMovimento, int idTipoSoggetto);
}
