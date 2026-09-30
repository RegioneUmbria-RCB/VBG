package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

public interface Dyn2CampiService extends BaseService<Dyn2Campi, PkId> {

    public List<Dyn2Campi> findByFilterAndIdModello(Dyn2Campi entity, Integer idModello);

    public List<Dyn2Campi> findByFilterAndIdModelloForNumeric(Dyn2Campi entity, Integer idModello);

    /**
     * Ricerca una lista di Campi dinamici filtrati per nome campo (ilike) e per software, se il codice software nn è
     * passato, ricerca per software TT
     * 
     * @param textToSearch
     * @param codiceSofware
     * @return
     */
    public List<Dyn2Campi> findByDescrizioneAndSoftware(String textToSearch, String codiceSoftware);

    /**
     * Ricerca una lista di Campi dinamici filtrati per nome campo (ilike) per software e per modello passato, se il
     * codice software nn è passato, ricerca per software TT
     * 
     * @param textToSearch
     * @param codiceSofware
     * @return
     */
    public List<Dyn2Campi> findByDescrizioneAndSoftwareAndModello(String textToSearch, Integer codiceModello, String codiceSoftware);

    /**
     * Ritorna se esiste un campo con il nome passato (cerca per idcomune e software e se non trova nulla cerca per
     * indomune e software TT)
     * 
     * @param nomecampo
     * @return
     */
    public Dyn2Campi findByNomeCampo(String nomecampo);

    /**
     * Crea la lista di Dyn2Campiproprieta, se già esistono per il campo passato li recupera o altrimenti li crea
     * 
     * @param entity
     * @return
     */
    public Set<Dyn2Campiproprieta> createDyn2Campiproprieta(Dyn2Campi entity);

    /**
     * Il metodo effettua la ricerca per un campo dinamico identificato come {@link TipoControlloEnum#Ricerca}. La query
     * viene poolata direttamente dai parametri del campo
     * 
     * @param textToSearch
     * @param codiceCampo
     * @param numMaxResults
     * @return
     */
    public List<ChiaveValoreBean<String, String>> findValoriPerCampo(String textToSearch, Integer codiceCampo, int numMaxResults);

    /**
     * Controlla se il campo è usato nelle istanze nelle anagrafiche o nelle attività
     * 
     * @param codiceCampo
     * @return
     */
    public boolean isCampoUsedInIstanzeOrAttivitaOrAnagrafe(Integer codiceCampo);

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
     * @see Dyn2CampiDAO#ffindByIdModello(Integer idModello)
     */
    public List<Dyn2Campi> findByIdModello(Integer idModello);

    /**
     * Cerca tra i campi dinamici configurati per il mercato filtrando (ilike) per descrizione
     * 
     * @param textToSearch
     * @param codiceMercato
     * @return
     */
    public List<Dyn2Campi> findByDescrizioneAndMercato(String textToSearch, Integer codiceMercato);

    public boolean deleteDyn2CampiNonUsati();

    public void insert(Dyn2Campi dyn2Campi, Set<Dyn2Campiproprieta> dyn2Campiproprietas);

    public boolean isCampoUpload(Integer codiceCampo);

    /**
     * 
     * @param textToSearch
     * @return
     */
    public List<Dyn2Campi> findAllByDescrizione(String textToSearch);
}
