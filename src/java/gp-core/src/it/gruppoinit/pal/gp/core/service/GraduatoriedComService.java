package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GraduatoriedComDAO;
import it.gruppoinit.pal.gp.core.dao.GraduatoriedDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.service.helper.GraduatoriedComHelper;

import java.util.List;

/**
 * 
 * @author
 */
public interface GraduatoriedComService extends BaseService<GraduatoriedCom, PkId> {

    /**
     * @see GraduatoriedComDAO#findAll(Integer, Integer)
     */
    public List<GraduatoriedCom> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see GraduatoriedDAO#countGraduatoriedComDomande(Integer codiceGraduatoriatCom)
     */
    public Integer countGraduatoriedComDomande(Integer codiceGraduatoriatCom);

    /**
     * @see GraduatoriedDAO#countGraduatoriedComMovimenti(Integer codiceGraduatoriatCom)
     */
    public Integer countGraduatoriedComMovimenti(Integer codiceGraduatoriatCom);

    /**
     * @see GraduatoriedDAO#countGraduatoriedComAllegati(Integer codiceGraduatoriatCom)
     */
    public Integer countGraduatoriedComAllegati(Integer codiceGraduatoriatCom);

    /**
     * @see GraduatoriedDAO#countGraduatoriedComMailInviate(Integer codiceGraduatoriatCom)
     */
    public Integer countGraduatoriedComMailInviate(Integer codiceGraduatoriatCom);

    public GraduatoriedCom insertComunicazioneDettaglio(GraduatorietCom graduatorietCom, GraduatoriedDTO graduatoriedDTO);

    /**
     * @see GraduatoriedDAO#findGraduatoriedComDTOByGraduatoriatCom(Integer codiceGraduatoriatCom)
     */
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatCom(Integer codiceGraduatoriatCom, Integer firstResult, Integer maxResult);

    /**
     * Recupera le GraduatoriedComDTO e popola anche l'eventuale istanze evento che è stato
     * 
     * @param codice
     * @return
     */
    public List<GraduatoriedComDTO> findGraduatoriedComDTOByGraduatoriatComWithEvent(Integer codice, Integer firstResult, Integer maxResult);

    /**
     * Inserisci il movimento per la comunicazione
     * 
     * @param graduatorietCom
     * 
     * @param graduatoriedCom
     * @param amministrazione
     * @param istanza
     * @return
     */
    public int inserimentoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom,
	    Amministrazioni amministrazione, Istanze istanza);

    /**
     * Protocolla il movimento per la comunicazione
     * 
     * @param graduatorietCom
     * 
     * @param graduatoriedCom
     * @param istanza
     * @param movimento
     * @return
     */
    public int inserimentoProtocolloMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento);

    /**
     * Crea l'allegato per il movimento della comunicazione
     * 
     * @param graduatorietCom
     * @param graduatoriedCom
     * @param istanza
     * @param movimento
     * @return
     */
    public int inserimentoAllegatoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento);

    /**
     * invia la mail per il movimento inserito della comunicazione
     * 
     * @param graduatorietCom
     * @param graduatoriedCom
     * @param istanza
     * @param movimento
     * @return
     */
    public int inserimentoMailInviataDalMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom,
	    Istanze istanza, Movimenti movimento);

    /**
     * 
     * @param graduatorietCom
     * @param graduatoriedCom
     * @param istanza
     * @param movimenti
     * @return
     */
    public int inserimentoMettiAllaFirmaPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimenti);

    public int conversioneInPDFAllegatoMovimentoPerLaComunicazione(GraduatorietCom graduatorietCom, GraduatoriedCom graduatoriedCom, Istanze istanza,
	    Movimenti movimento);

    public List<GraduatoriedComHelper> findByGraduatoried(Integer codicegraduatoriad);
}
