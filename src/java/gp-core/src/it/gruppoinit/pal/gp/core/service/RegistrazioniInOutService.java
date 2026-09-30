package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RegistrazioniInOutService extends BaseService<RegistrazioniInOut, PkId> {

    public void updateScadenze(RegistrazioniInOutCommand command);

    /**
     * recupera i record in REGISTRAZIONI_IN_OUT con importi da assegnare, filtrati per anagrafe
     * 
     * @param anagrafe
     * @return
     */
    public List<RegistrazioniInOut> findRegistrazioniInOutDaAssegnare(Anagrafe anagrafe);

    /**
     * Ritorna una lista di di oggetti registrazionefilter con filtraggi opzionali
     * (dataInizio,dataFine,anagrafe,Mercato) e raggruppati per anagrafe conto posteggio mercato mercatouso
     */
    public List<RegistrazioniFilter> findByDataAndAnagrafeAndMercato(RegistrazioniFilter registrazioniFilter);

    /**
     * Torna la lista delle RegistrazioniInOut di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<RegistrazioniInOut> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle RegistrazioniInOut di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<RegistrazioniInOut> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    public void insertPagamentoRata(Software software, BigDecimal importo, Responsabili responsabile, Integer codiceRegistrazione, Integer nrRata,
	    Date dataDistinta, Date dataIncasso, Integer tipimodalitapagamentoId, String riferimentiPagamento, String note);
}
