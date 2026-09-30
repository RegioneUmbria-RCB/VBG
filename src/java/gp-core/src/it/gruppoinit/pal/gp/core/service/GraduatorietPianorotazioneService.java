package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GraduatorietPianorotazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.GiorniSettimanaEnum;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietPianoRotazioneDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioHelper;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorietPianorotazioneFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface GraduatorietPianorotazioneService extends BaseService<GraduatorietPianorotazione, PkId> {

    /**
     * @see GraduatorietPianorotazioneDAO#findAll(Integer, Integer)
     */
    public List<GraduatorietPianorotazione> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera i record del piano di rotazione per la graduatoria passata
     * 
     * @param graduatoriaid
     * @return
     */
    public List<GraduatorietPianorotazione> findByGraduatoriaD(Integer graduatoriaid);

    public void creaPianoRotazione(Integer graduatoriat);

    public List<GraduatorietPianorotazione> findByGraduatorit(Integer codiceGraduatorit);

    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieT(Graduatoriet graduatoriat, MercatiD mercatiD,
	    GiorniSettimanaEnum giorniSettimanaEnum);

    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieTAndIstanza(Graduatoriet graduatoriat, Integer codiceIstanza);

    public List<PosteggioHelper> createMapPosteggiPianorotazione(Graduatoriet graduatoriat);

    /**
     * Cancella il piano rotazione associato alla graduatoria t e le relative aut e concessione associate alle istanze
     * in graduatoria
     * 
     * @param graduatorietPianorotaziones
     */
    public void deletePianoRotazione(List<GraduatorietPianorotazione> graduatorietPianorotaziones);

    /**
     * Ritorna una lista di oggetti "GraduatorietPianorotazione" filtrando per i campi presneti all'interno dell'oggetto
     * filter
     * 
     * @param filter
     * @return
     */
    public List<GraduatorietPianorotazione> findByGraduatorietPianorotazioneFilter(GraduatorietPianorotazioneFilter filter);
}
