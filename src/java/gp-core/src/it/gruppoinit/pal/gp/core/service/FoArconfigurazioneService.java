package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;

import java.util.List;

public interface FoArconfigurazioneService extends BaseService<FoArconfigurazione, FoArconfigurazioneId> {

    /**
     * Metodo che per la ricerca delle configurazioni per software
     * 
     * @param software
     * @return
     */
    public FoArconfigurazione findBySoftware(Software software);

    /**
     * Ricerca tutti i Foarconfigurazione che hanno un oggetto collegato. Il metodo ricerca nei campi codiceoggettoFirma
     * e codiceoggettoSottoscriz.
     * 
     * @param codiceOggetto
     * @return
     */
    public List<FoArconfigurazione> findByOggetto(Integer codiceOggetto);

    /**
     * 
     * @param codiceOggetto
     * @return
     */
    public List<FoArconfigurazione> findByFoArjStepsTestata(Integer codiceTestata);

    /**
     * lo stato iniziale dell'istanza per le domande FO si trova nella tabella
     * FO_ARCONFIGURAZIONE.STATO_INIZIALE_ISTANZA Se non esiste per il software corrente va cercato per TT
     * 
     * @return
     */
    public Statiistanza findStatoInizialeIstanzaOnline();
}
