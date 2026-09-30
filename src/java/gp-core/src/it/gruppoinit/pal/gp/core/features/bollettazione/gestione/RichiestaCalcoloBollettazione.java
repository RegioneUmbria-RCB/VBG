package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;

public interface RichiestaCalcoloBollettazione {

    void setTipologiaPeriodo(PeriodiEnum periodo);

    PeriodiEnum getTipologiaPeriodo();

    void setCodiceResponsabile(Integer codiceResponsabile);

    Integer getCodiceResponsabile();

    void setDescrizione(String descrizione);

    String getDescrizione();

    void setIntervalloDate(IntervalloDate intervalloDate);

    IntervalloDate getIntervalloDate();
}
