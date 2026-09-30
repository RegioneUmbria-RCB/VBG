package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DocumentiistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author francescop
 */
public interface AnagrafedocumentiService extends BaseService<Anagrafedocumenti, PkId> {

    /**
     * Ritorna tutti i documenti che hanno il riferimento all'istanza passata
     * 
     * @param istanza
     * @return
     */
    public List<Anagrafedocumenti> findByIstanza(Istanze istanza);

    /**
     * Ritorna tutti i documenti che hanno il riferimento all'istanza e all'anagrafica passata, il parametro
     * isDocumentoPresente ci permette eventualmnete di filtrare solo i record che contengono l'allegato fisico
     * 
     * @param istanza
     * @param anagrafe
     * @param isDocumentoPresente
     * 
     * @return List<Anagrafedocumenti>
     */
    public List<Anagrafedocumenti> findByIstanzaAndAnagrafe(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente);

    /**
     * @see DocumentiistanzaDAO#findByIstanzaAndAnagrafeDTO(Istanze istanza, Anagrafe anagrafe, boolean
     *      isDocumentoPresente)
     * 
     */
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTO(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente);

    /**
     * Restituisce la lista dei documenti dell'anagrafica che non sono presenti in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param istanza
     * @param anagrafe
     * @param codiceautorizzazione
     * @return
     */
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTONonInDocAutorizzazione(Istanze istanza, Anagrafe anagrafe,
	    Integer codiceautorizzazione);

    public void salvaDocumento(Oggetti filePDF, Integer codiceAnagrafe, Date dataStampa, String uuid, Integer codTipodocStampa);
}
