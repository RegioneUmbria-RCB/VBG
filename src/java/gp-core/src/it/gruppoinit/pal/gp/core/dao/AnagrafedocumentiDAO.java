package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;

import java.util.List;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
public interface AnagrafedocumentiDAO extends BaseDAO<Anagrafedocumenti, PkId> {

    /**
     * Ritorna la lista di tutti i docuenti delle anagrafiche
     * 
     */
    public List<Anagrafedocumenti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna tutti i documenti che hanno il riferimento all'istanza e all'anagrafica passata, il parametro
     * isDocumentoPresente ci permette eventualmnete di filtrare solo i record che contengono l'allegato fisico (In
     * questi caso non sarnno presenti tutti i campi contenuti nell'oggetto Anagrafedocumenti )
     * 
     * @param istanza
     * @param anagrafe
     * @param isDocumentoPresente
     * 
     * @return List<AnagrafedocumentiDTO>
     */
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTO(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente);
}
