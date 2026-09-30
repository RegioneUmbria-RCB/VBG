package it.gruppoinit.pal.gp.core.features.attivita.istanze;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.model.IstanzeAttivitaHelper;

public interface IAttivitaIstanzeService {

    void collegaIstanze(IAttivita attivita);

    void collegaIstanze(IAttivita attivita, Istanze istanza);

    void scollegaIstanze(Istanze istanza) throws ScollegamentoUnicaIstanzaException;

    /**
     * RECUPERA LA lista delle istanze con dataValidita>= alla dataPassata. se l'istanza non ha data validita viene
     * considerata con data validita = 01/01/1900. le istanze sono ordinate per
     * 
     * <pre>
     *   coalesce(datavalidita,to_date('01/01/1900','dd/mm/yyyy')) desc
         , istanze.i_attivitaordine asc
     * </pre>
     * 
     * @param idAttivita
     * @param dataValiditaRiferimento
     * @return
     */
    List<IstanzeAttivitaHelper> cercaIstanzeDaDataValidita(Integer idAttivita, Date dataValiditaRiferimento);

    /**
     * Torna true se alla data è attiva
     * 
     * @param idAttivita
     * @param dataValidita
     * @return
     */
    public boolean isAttivaAllaData(Integer idAttivita, Date dataValidita);

    List<Date> findSnapshotMancanti(Integer idAttivita);

    void scambiaOrdine(Integer idAttivita, Integer codiceIstanzaPrec, Integer codiceIstanzaSuc);

    void updateOrdine(Integer idAttivita, Integer[] arCodici, Integer[] arOrdini);

    Istanze findIstanzaSenzaDataValiditaPiuRecente(Integer idAttivita);
}
