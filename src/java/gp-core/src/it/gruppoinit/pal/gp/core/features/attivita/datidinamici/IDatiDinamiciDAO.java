package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface IDatiDinamiciDAO {

    List<Integer> findIdSchedeDinamicheDaAlberoProc(Integer codiceInterventoProc);

    List<Integer> findIdSchedeDinamicheAttivita(Integer idAttivita);

    void aggiungiSchedeDinamicheAdAttivita(Integer idAttivita, Integer idScheda, List<Integer> idSnapshots);

    void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, List<Integer> idSchede, List<Integer> idSnapshots);

    void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, List<Integer> idSchede, Integer idSnapshot);

    void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, Integer idScheda, List<Integer> idSnapshots);

    TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiIstanze(Integer idAttivita, Integer idScheda);

    TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiIstanze(Integer idAttivita, List<Integer> idCampi);

    Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiSnapshot(Integer idAttivita, Integer idScheda);

    List<CampoDinamicoAttivita> findCampiPresentiAttivita(Integer idAttivita, Integer idScheda);

    List<Integer> findIdCampiAttivitaGestibiliDaIstanze(Integer idAttivita);

    List<Integer> findIdCampiAttivitaGestibiliDaIstanze(Integer idAttivita, Integer idScheda);

    List<Integer> findIdCampiScheda(Integer idScheda);

    void deleteCampiDinamici(Integer idAttivita, List<Integer> idCampi);

    void deleteCampiDinamici(Integer idAttivita, List<Integer> idSnapshots, List<Integer> idCampi);

    void deleteCampiDinamiciDaSnapshot(Integer idSnapshot, Integer idScheda);

    void deleteSchedeDaSnapshots(Integer idAttivita, Integer idScheda, List<Integer> idCampi);

    void insertAutoIns(Integer idAttivita, Integer idSnapshot, Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot);

    void insertAutoIns(Integer idAttivita, Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot);

    Integer findIdSnapshotRappresentativo(Integer idAttivita);

    void sovrascriviSnapshot(Integer idAttivita, Integer idSnapshot, Integer idScheda, List<CampoDinamicoAttivita> elenco);

    List<Integer> findCampiPresentiIstanzaEAttivita(Integer idAttivita, Integer idScheda);
}
