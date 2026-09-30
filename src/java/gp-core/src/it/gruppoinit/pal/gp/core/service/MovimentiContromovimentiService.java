package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiContromovimentiService extends BaseService<MovimentiContromovimenti, PkId> {

    List<MovimentiContromovimenti> findByFilterTable(FilterTable filterTable);

    int countByFilterTable(FilterTable filterTable);

    List<MovimentiContromovimenti> findByMovimentoByFkFiglio(Movimenti movimentoByFkFiglio);

    List<MovimentiContromovimenti> findByMovimentoByFkPadreAndFkFiglio(Integer codiceMovPadre, Integer codiceMovFiglio);

    List<MovimentiContromovimenti> findByMovimentoByFkPadre(Movimenti movimentoByFkPadre);

    int countByMovimentoByFkPadreAndFkFiglio(Integer codiceMovimentoPadre, Integer codiceMovimentoFiglio);
}
