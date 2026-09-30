package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;

import java.util.List;

public interface MovimentiallegatiDAO extends BaseDAO<Movimentiallegati, PkId> {

    /**
     * Trova tutti i documenti dei movimenti di un'istanza ordinati per MOVIMENTIALLEGATI.DESCRIZIONE,
     * MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Movimentiallegati> findByIstanza(int codiceIstanza);

    /**
     * Trova tutti i documenti di un movimento (Utilizzato anche in SIMO non togliere) ordinati per
     * MOVIMENTIALLEGATI.DESCRIZIONE, MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Movimentiallegati> findByMovimento(int codiceMovimento);

    /**
     * Trova tutti i documenti dei movimenti di un'istanza che hanno un oggetto ordinati per
     * MOVIMENTIALLEGATI.DESCRIZIONE, MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @param codicemovimento
     *            TODO
     * @return
     */
    public List<Movimentiallegati> findByIstanzaOggetto(int codiceIstanza, int codicemovimento);

    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto);

    /**
     * Ritorna tutta la lista dei documenti associati ai moviementi dell'istanza ordinati per data e descrizione
     * 
     * @param codiceIstanza
     * @return
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza);

    /**
     * Ritorna tutta la lista dei documenti associati al moviemento ordinati per data desc e descrizione asc e id desc
     * 
     * @param codiceIstanza
     * @return
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento);
}
