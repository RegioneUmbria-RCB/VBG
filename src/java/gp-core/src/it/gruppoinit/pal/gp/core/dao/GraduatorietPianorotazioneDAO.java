package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.GiorniSettimanaEnum;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietPianoRotazioneDTO;

import java.util.List;

/**
 * 
 * @author
 */
public interface GraduatorietPianorotazioneDAO extends BaseDAO<GraduatorietPianorotazione, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<GraduatorietPianorotazione> findAll(Integer firstResult, Integer maxResult);

    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieT(Graduatoriet graduatoriat, MercatiD mercatiD,
	    GiorniSettimanaEnum giorniSettimanaEnum);

    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieTAndIstanza(Graduatoriet graduatoriat, Integer codiceIstanza);
}
