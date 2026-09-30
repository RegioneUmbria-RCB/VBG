package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiTempisticaService extends BaseService<MovimentiTempistica, PkId> {

    public static enum TIPO_EVENTO {
	I, S, P
    };

    public List<MovimentiTempistica> findByFilterTable(FilterTable filterTable);

    public MovimentiTempistica findByMovimentoApertura(Movimenti entity);

    public MovimentiTempistica findByMovimentoChiusura(Movimenti entity);

    public List<MovimentiTempistica> findByMovimentiPerEvento(Istanze istanza, TIPO_EVENTO tipoEvento);

    public List<MovimentiTempistica> findByIstanza(Istanze istanza);

    /**
     * Torna la durata delle proroghe per il movimento di apertura
     * 
     * @param codiceMovimento
     * @return
     */
    public int findDurataProrogaPerIstanza(Integer codiceIstanza);

    public int countByFilterTable(FilterTable ft);

    public Date findDataUltimaInterruzione(Integer codice);

    public boolean isIstanzaInterrotta(Integer codiceIstanza);
}
