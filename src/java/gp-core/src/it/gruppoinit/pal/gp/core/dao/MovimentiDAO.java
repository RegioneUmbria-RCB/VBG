package it.gruppoinit.pal.gp.core.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;

public interface MovimentiDAO extends BaseDAO<Movimenti, PkId> {

    /**
     * Metodo che filtra paasando un criterio
     */
    public List<Movimenti> findByCriteria(DetachedCriteria criteria);

    /**
     * Trova il movimento di avvio dell'istanza. Se la query torna più record allora torna quello con la data più
     * vecchia. Se non viene trovato torna null
     * 
     * @param codiceistanza
     * @return
     */
    public Movimenti findMovimentoAvvioIstanza(Integer codiceistanza);

    /**
     * Trova il movimento effettuato per l'istanza ed il tipo movimento individuato. Se la query torna più record allora
     * torna quello con la data più recente.
     * 
     * @param codiceistanza
     * @param tipimovimento
     * @return
     */
    public Movimenti findMovimentiByTipoMovimento(Integer codiceistanza, String tipimovimento);

    /**
     * Metodo per l'integrazione SIGePro/SIMO Deve recuperare tutti i movimenti filtrandoli per Intervallo di
     * data,Alberoprc e TipoMovimento
     * 
     * @return
     */
    public List<Movimenti> findMovimentiSimo(Calendar fromDate, Calendar toDate, Alberoproc alberoproc);

    /**
     * <pre>
     * Il metodo ritorna una lista di movimenti che possono essere assoaciati a una commissione
     * &#64;param filtroData : vengono selezionati solo i movimenti con data maggiore uguale a quella passata.
     * &#64;param commissioniedilizieT : permette di recuperare i tipi movimenti che definiscono un movimento associabile a una commissione 
     * &#64;return
     * I movimenti verranno restituiti secondo la logica:
     * 
     * 1- data >= filtroData
     * 2- Il tipo movimento associato è uno tra quelli contenuti nella tabella COMMEDILIZIE_TIPOLOGIEDETT
     *    (La tabella è collegata a COMMISSIONIEDILIZIE_T tramite la tabella COMMEDILIZIE_TIPOLOGIE ).
     * 3- Il movimento non deve avere già un riferimento nella tabella COMMISSIONIEDILIZIE_R
     * 
     * </pre>
     */
    public List<Movimenti> findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT commissioniedilizieT, Integer firstResult,
	    Integer maxResult);

    /**
     * Trova il progressivo di inserimento dei movimenti. Esegue una max sulla tabella per l'istanza
     * 
     * @param codiceistanza
     * @return
     */
    public int findProgressivoInserimento(Integer codiceistanza);

    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc);

    public List<MovimentiDTO> findMovimentiDTODaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult, Integer maxResult);

    public List<MovimentiDTO> findMovimentiDTOSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResults);

    public List<CodiceDescrizioneBean> findMetadatiMovimento(Integer codiceMovimento);

    public List<ChiaveValoreBean<String, Integer>> countMovimentiSTCConAnomalie();

    public String getUuid(Integer codiceMovimento);
}
