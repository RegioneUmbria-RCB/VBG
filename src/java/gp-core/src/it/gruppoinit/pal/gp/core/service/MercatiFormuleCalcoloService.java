package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.MercatiFormuleCalcoloDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.LivelloServizioHelper;

/**
 * 
 * @author
 */
public interface MercatiFormuleCalcoloService extends BaseService<MercatiFormuleCalcolo, PkId> {

    /**
     * @see MercatiFormuleCalcoloDAO#findAll(Integer, Integer)
     */
    public List<MercatiFormuleCalcolo> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiFormuleCalcoloHelper> findByMecatoUso(Integer codiceuso);

    public List<MercatiFormuleCalcoloHelper> findByMecato(Integer codicemercato);

    public List<MercatiFormuleCalcolo> findByUso(Integer codiceuso);

    public void insert(MercatiFormuleCalcolo entity, MercatiContabilitaTributi mercatiContabilitaTributi);

    /**
     * Il metodo verifica se la formula inserita è corretta dal punto di vista del contenuto, verificando cioé che i
     * segnaposti siano corretti e che non finisca o inizi con simboli matematici non corretti.
     * 
     * @param formula
     */
    public List<LivelloServizioHelper> findLivelliAttiviByMercatoUso(Integer idUso);

    public void verificaFormulaCalcolo(Integer idUso, String formula);
}
