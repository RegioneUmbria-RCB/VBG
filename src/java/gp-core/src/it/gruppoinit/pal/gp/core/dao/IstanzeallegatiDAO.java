package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.List;

public interface IstanzeallegatiDAO extends BaseDAO<Istanzeallegati, PkId> {

    /**
     * Torna la lista degli allegati degli endprocedimenti di un'istanza. I record sono ordinati per
     * ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanza(int codiceIstanza);

    /**
     * Torna la lista degli allegati di un endprocedimento attivato di un'istanza. I record sono ordinati per
     * ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @param codiceInventario
     *            il codice dell'endoprocedimento per il quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanzaAndEndo(int codiceIstanza, int codiceInventario);

    /**
     * Torna la lista degli allegati degli endprocedimenti di un'istanza che ha un oggetto. I record sono ordinati per
     * ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanzaOggetto(int codiceIstanza);

    public int updateResettaRiferimentoAllegatoEndo(Allegati allegato);

    /**
     * Lista dei documenti associati agli inventario procedimenti dell'istanza passata, ordinati per il cmpo extra
     * allegato
     * 
     * @param codiceIstanza
     * @return
     */
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza);

    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto);

    /**
     * <pre>
     * Lista dei documenti associati all'inventario procedimento e all'istanza passati, ordinati per il campo
     * extra allegato. Il parametro "tipoRicercaDocumentoEnum" permette di scegliere se recuperare o no i documenti
     * senza allegato associato
     * 
     * @param codiceIstanza
     * @param codicendo
     * @param tipoRicercaDocumentoEnum
     * @return
     * </pre>
     */
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanzaAndEndo(Integer codiceIstanza, Integer codicendo,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum);
}
