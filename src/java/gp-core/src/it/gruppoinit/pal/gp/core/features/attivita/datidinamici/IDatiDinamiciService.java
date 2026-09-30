package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IAttivita;

public interface IDatiDinamiciService {

    void aggiungiSchedeDinamiche(Integer idAttivita, List<Integer> idSchede);

    void aggiungiSchedeDinamicheDaAlbero(IAttivita attivita);

    void gestisciSchedaDinamicaAggiuntaAdAttivita(Integer idAttivita, Integer idScheda);

    void gestisciSchedaDinamicaAttivitaSalvata(Integer idAttivita, Integer idScheda);

    void gestisciSchedaDinamicaAttivitaEliminata(Integer idAttivita, Integer idScheda, List<Integer> idCampiDinamiciDaEliminare);

    void gestisciSchedaDinamicaIstanzaSalvata(Integer idAttivita, Integer idIstanza, Integer idScheda, Date dataRicalcolo);

    void gestisciSchedaDinamicaIstanzaEliminata(Integer idAttivita, Integer idScheda, Date dataRicalcolo);

    void gestisciNuovoSnapshot(Integer idAttivita, Date dataRicalcolo);

    void gestisciRicalcoloDatiDinamici(Integer idAttivita, Date dataRicalcolo);
}
