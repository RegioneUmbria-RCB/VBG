package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimentiId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.TemplateMessaggioAppIo;

public interface IAppIoCodaMovimentiDAO extends BaseDAO<AppIoCodaMovimenti, AppIoCodaMovimentiId> {

    public List<AppIoCodaMovimenti> findByMovimento(Integer codiceMovimento);

    public TemplateMessaggioAppIo findByIdServizioMovimentoAndIntervento(String identServizio, String tipomovimento, Integer codiceIntervento);

    public TemplateMessaggioAppIo findByIdServizioMovimentoAndEndo(String identServizio, String tipomovimento, Integer codiceEndo);
}
