package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.InteressiLegali;

import java.util.Date;
import java.util.List;

public interface InteressiLegaliDAO extends BaseDAO<InteressiLegali, Integer> {

    public List<InteressiLegali> findByDataInizioFine(Date dataInizio, Date dataFine);
}
