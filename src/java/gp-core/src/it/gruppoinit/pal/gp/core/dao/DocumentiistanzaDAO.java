package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;

public interface DocumentiistanzaDAO extends BaseDAO<Documentiistanza, PkId> {

    /**
     * Trova tutti i documenti di un'istanza ordinati per documento
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza);

    /**
     * Trova tutti i documenti di un'istanza che ha un oggetto
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Documentiistanza> findByIstanzaOggetto(Integer codiceistanza);

    /**
     * Trova tutti i documenti (DocumentiistanzaDTO) di un'istanza filtrando per flgDaModelloDinamico e ordinando per
     * documento ()
     * <ol>
     * <li>Se [flgDaModelloDinamico = true] ritorna la lista dei documentiistanza provenienti da modelli dinamici</li>
     * <li>Se [flgDaModelloDinamico = false] ritorna la lista dei documentiistanza che non provengono da modelli
     * dinamici</li>
     * <li>Se [flgDaModelloDinamico == null] ritorna la lista di tutti documentiistanza indipendentemente
     * </ol>
     * 
     * @param codiceIstanza
     * @param flgDaModelloDinamico
     * @return DocumentiistanzaDTO :oggetto contenente i campi esclusi gli oggetti lob dell'istanza oggetto
     */
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico);

    /**
     * Aggiorna il solo campo presente del documenti istanza passato
     * 
     * @param codiceDocIstanza
     * @param isCheked
     */
    public void updatePresente(Integer codiceDocIstanza, Boolean isCheked);

    /**
     * Aggiorna il solo campo necessario del documenti istanza passato
     * 
     * @param codiceDocIstanza
     * @param necessario
     */
    public void updateNecessario(Integer codiceDocIstanza, boolean necessario);

    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico, Boolean isCodiceOggetto);

    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza);
}
