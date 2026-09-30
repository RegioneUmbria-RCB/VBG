package it.gruppoinit.pal.gp.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface MercatiCfgContiService extends BaseService<MercatiCfgConti, PkId> {

    /**
     * Esistono configurazioni per il software corrente
     * 
     * @return
     */
    public boolean existsDati();

    /**
     * Ritorna le configurazioni valide per il software corrente e la data di validita - presenza nel mercato. Torna le
     * configurazioni di mercato dove è nulla la colonna fkidmercato
     * 
     * @param dataPresenza
     * @return
     */
    public List<MercatiCfgConti> findByDataPresenza(Date dataPresenza);

    //    /**
    //     * @deprecated Ritorna le configurazioni valide per il mercato e la data di validita - presenza nel mercato -
    //     * 
    //     * @param codiceMercato
    //     * @param dataPresenza
    //     * @return
    //     */
    //    public List<MercatiCfgConti> findByMercatoAndDataPresenza(Integer codiceMercato, Date dataPresenza);
    /**
     * Ritorna le configurazioni valide per il mercato e la data di validita - presenza nel mercato -
     * 
     * @param codiceCategoriaMercato
     * @param dataPresenza
     * @return
     */
    public List<MercatiCfgConti> findByMercatoCategoriaAndDataPresenza(Integer codiceCategoriaMercato, Date dataPresenza);

    public List<MercatiCfgConti> findByMercatoCategoriaAndPosteggiSettoriAndDataPresenza(Integer codice, Integer posteggioSettoreId,
	    Date dataPresenza);

    public BigDecimal getCoefficienteMercatoByContoGiornataEPosteggio(Integer idConto, Integer idGiornata, Integer idPosteggio);
}
