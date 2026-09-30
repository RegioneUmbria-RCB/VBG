package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneContiMercato;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MercatiContiService extends BaseService<MercatiConti, PkId> {

    public List<MercatiConti> findByMercati(MercatiConti entity);

    /**
     * Questo metodo serve per configurare velocemente i conti di un mercato per un anno. <br/>
     * I parametri anno, mercato,lista dei conti sono individuati dal bean configurazioneContiMercato
     * 
     * @param configurazioneContiMercato
     */
    public void sistemaContiPerAnno(ConfigurazioneContiMercato configurazioneContiMercato);

    /**
     * Lista di conti per il mercato mercato e anno selezionato
     */
    public List<MercatiConti> findByMercatiAndAnno(Mercati entity, Integer anno);

    public void adeguaPercentualeIstat(Mercati mercato, Integer anno_precedente, Integer anno_da_adeguare,
	    Map<Integer, Integer> vecchioContoNuovoConto, BigDecimal coefficiente_adeguamento);
}
