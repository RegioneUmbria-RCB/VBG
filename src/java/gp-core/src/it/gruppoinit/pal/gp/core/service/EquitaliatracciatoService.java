package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDAO;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.Equitaliatracciato;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.helper.MessageTracciato450Helper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author
 */
public interface EquitaliatracciatoService extends BaseService<Equitaliatracciato, PkId> {

    /**
     * @see EquitaliatracciatoDAO#findAll(Integer, Integer)
     */
    public List<Equitaliatracciato> findAll(Integer firstResult, Integer maxResult);

    public ChiaveValoreBean<String, List<MessageTracciato450Helper>> validaInformazioniIstanza(Integer codiceIstanza,
	    EquitaliaTracciatiCfg equitaliaTracciatiCfg);

    public List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> validaInformazioniIstanze(List<Integer> codiceIstanze);

    public Integer insertTracciatoEquitalia450(EquitaliaTracciatiCfg equitaliaTracciatiCfg, List<Integer> codiceIstanze, String nomeResponsabile,
	    String cognomeResponsabile, boolean isForzaCreazioneTracciato) throws Exception;

    public Integer findProgressivoAnno(String anno);

    public void deleteTracciato(Equitaliatracciato equitaliatracciato);

    public Map<String, String> checkFileRowLenth(Equitaliatracciato equitaliatracciato);

    public ChiaveValoreBean<String, byte[]> downloadTracciatoEquitalia450Excel(EquitaliaTracciatiCfg equitaliaTracciatiCfg,
	    List<Integer> codiceIstanze, String nomeResponsabile, String cognomeResponsabile, boolean isForzaCreazioneTracciato) throws Exception;
    
    void aggiornaDataCreazioneTracciato(Integer idTracciato, Date nuovaData);
}
