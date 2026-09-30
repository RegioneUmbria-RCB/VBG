package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.TemplateMessaggioAppIo;

public interface IAppIoCodaMovimentiService {

    void insert(AppIoCodaMovimenti codaMovimenti);

    List<AppIoCodaMovimenti> findByMovimento(Integer codiceMovimento);

    TemplateMessaggioAppIo findByIdServizioMovimentoAndIntervento(String identServizio, String tipomovimento, Integer codice);

    TemplateMessaggioAppIo findByIdServizioMovimentoAndEndo(String identServizio, String tipomovimento, Integer codice);
}
