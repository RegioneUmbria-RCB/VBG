package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;

import java.util.List;

public interface RegistrazioniInOutDAO extends BaseDAO<RegistrazioniInOut, PkId> {

    /**
     * Ritorna una lista di di oggetti registrazionefilter con filtraggi opzionali
     * (dataInizio,dataFine,anagrafe,Mercato) e raggruppati per anagrafe conto posteggio mercato mercatouso
     */
    public List<RegistrazioniFilter> findByDataAndAnagrafeAndMercato(RegistrazioniFilter registrazioniFilter);

    /**
     * Ritorna una lista di di oggetti RegistrazioniInOut con filtraggi opzionali i filtri valutati sono:
     * <ul>
     * <li>anagrafe</li>
     * <li>conti</li>
     * <li>mercato</li>
     * <li>giorno</li>
     * <li>posteggio</li>
     * <li>data distinta (dalla data...alla data)</li>
     * </ul>
     */
    public List<RegistrazioniInOut> findByFilter(RegistrazioniFilter registrazioniFilter);
}
