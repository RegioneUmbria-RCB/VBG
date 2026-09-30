/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.helper.ProcedimentoProcediMarche;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.IdDescrizione;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoSpecifico;

/**
 * 
 * @author francol
 */
public interface ProcediMarcheProxyService {

    public List<ProcedimentoProcediMarche> getListaProcedimenti();

    public ProcedimentoProcediMarche getProcedimento(Integer idProcedimarche);

    public List<IdDescrizione> getSerieArchivistiche();

    public List<IdDescrizione> getTipiFascicolo();

    public ProcedimentoProcediMarche collegaProcedimento(TipoProcedimentoSpecifico datiSpecifici);

    public void scollegaProcedimento(Integer idStpEndoTipo2);

    public TipoProcedimentoSpecifico getDatiLocaliProcedimento(StpEndoTipo2 stpEndoTipo2);

    public void salvaDatiLocaliProcedimento(ProcedimentoProcediMarche datiProc);

    public void pubblicaProcedimento(ProcedimentoProcediMarche datiProc);

    public void spubblicaProcedimento(ProcedimentoProcediMarche datiProc);

    public String buildUrlNuovaIstanzaFrontOffice(Integer idProc);

    public boolean isVerticalizzazioneConfigurata();
}
