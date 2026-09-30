package it.gruppoinit.pal.gp.core.features.attivita.istanze;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.model.IstanzeAttivitaHelper;

@SuppressWarnings("rawtypes")
public interface IAttivitaIstanzeDAO extends BaseDAO {

    /**
     * Il metodo torna la lista dei codici istanza colletgati a quello passati
     * 
     * @param codiceIstanza
     * @return
     */
    List<Integer> findCatenaIstanzeDaCollegare(Integer codiceIstanza);

    void collegaIstanzeAdAttivita(Integer codiceAttivita, Integer codiceIstanza, Integer ordine);

    List<IstanzeAttivitaHelper> cercaIstanzeDaDataValidita(Integer idAttivita, Date dataValiditaRiferimento);

    /**
     * 
     * @param idAttivita
     * @param dataValidita
     * @return
     */
    boolean isAttivaAllaData(Integer idAttivita, Date dataValidita);

    boolean isPresenteUnaSolaIstanza(Integer idAttivita);

    List<Date> findSnapshotMancanti(Integer idAttivita);

    Date scambiaOrdine(Integer codiceIstanzaPrec, Integer codiceIstanzaSuc);

    void updateOrdine(Integer idIstanza, Integer ordine);

    Integer findIstanzaSenzaDataValiditaPiuRecente(Integer idAttivita);
}
