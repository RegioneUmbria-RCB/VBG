package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

public interface Dyn2CampiDAO extends BaseDAO<Dyn2Campi, PkId> {

    public List<Dyn2Campi> findByFilterAndIdModello(Dyn2Campi entity, Integer idModello);

    /**
     * metodo da utilizzare per le ricerche ajax al posto di findByFilterAndIdModello. in questo modo evito di
     * recuperare i campi che non sono numerici
     * 
     * @param entity
     * @param idModello
     * @return
     */
    public List<Dyn2Campi> findByFilterAndIdModelloForNumeric(Dyn2Campi entity, Integer idModello);

    /**
     * metodo per ricercare i valori eseguendo una query definita dalle proprietà di un campo di tipo
     * {@link TipoControlloEnum#Ricerca} o {@link TipoControlloEnum#ListaSIGePro}
     * 
     * @param textToSearch
     * @param codiceCampo
     * @param numMaxResults
     * @return
     */
    public List<ChiaveValoreBean<String, String>> findValoriPerCampo(String textToSearch, Integer codiceCampo, int numMaxResults);

    /**
     * Controlla se il campo è usato nelle istanze
     * 
     * @param codiceCampo
     * @param codiceModello
     * @return
     */
    public boolean isCampoUsedInIstanze(Integer codiceCampo, Integer codiceModello);

    /**
     * Controlla se il campo è usato nelle attività
     * 
     * @param codiceCampo
     * @param codiceModello
     * @return
     */
    public boolean isCampoUsedInAttivita(Integer codiceCampo, Integer codiceModello);

    /**
     * Controlla se il campo è usato nelle anagrafiche
     * 
     * @param codiceCampo
     * @param codiceModello
     * @return
     */
    public boolean isCampoUsedInAnagrafe(Integer codiceCampo, Integer codiceModello);

    /**
     * Cerca se per un modello passato (Dyn2Modellit) è associato un campo dinamico (Dyn2Campi)
     * 
     * @param codiceScheda
     * @param codice
     * @return
     */
    public List<Dyn2Campi> findByIdModelloAndIdCampo(Integer codiceScheda, Integer codice);

    /**
     * Cerca se per un modello passato (Dyn2Modellit)la lista dei campi dimanici associati
     * 
     * @param idModello
     * 
     * @return
     */
    public List<Dyn2Campi> findByIdModello(Integer idModello);

    /**
     * 
     * @param textToSearch
     * @return
     */
    public List<Dyn2Campi> findAllByDescrizione(String textToSearch);
}
