package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.soggetti;

public interface ITipimovTipiSoggettoDao {

    public void elimina(int id);

    public int aggiungi(String idTipoMovimento, int idTipoSoggetto);
}
