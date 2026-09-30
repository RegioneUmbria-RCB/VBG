package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AnagrafeDAO extends BaseDAO<Anagrafe, PkId> {

    /**
     * ricerca tutti i record in ANAGRAFE con una LIKE della stringa passata nel campo NOMINATIVO. se la stringa
     * contiene degli spazi allora utilizza la sottostringa dopo lo spazio per eseguire una LIKE nel campo NOME.
     * 
     * <pre>
     * se AnagrafeEnum.ALL allora torna tutti i record
     * se AnagrafeEnum.ACTIVE allora torna tutti i record dell'anagrafica che risultano attivi
     * se AnagrafeEnum.DISABLED allora torna tutti i record dell'anagrafica che risultano disabilitati
     * </pre>
     * 
     * @param nominativo
     * @param anagrafeEnum
     * @return
     */
    public List<Anagrafe> findByNominativo(String nominativo, AnagrafeEnum anagrafeEnum);

    /**
     * Restituisce le anagrafiche (filtrando per idcomune) ordinandole per il campo nominativo
     */
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult);

    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult);

    public List<Anagrafe> findRichiedentiByIstanza(String descrizione, Integer codiceIstanza, String tipoAnagrafe);

    public List<Integer> findCodiciAnagrafe();
}
