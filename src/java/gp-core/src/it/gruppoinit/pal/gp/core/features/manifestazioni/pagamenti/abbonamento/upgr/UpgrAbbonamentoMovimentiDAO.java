package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.upgr;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

@SuppressWarnings("rawtypes")
public interface UpgrAbbonamentoMovimentiDAO extends BaseDAO {
    public List<Object[]> findAllBorsellini();
    public void updateCreditiMovimento(String idcomune, Integer idmovimento, BigDecimal creditoiniziale, BigDecimal creditofinale);
}
