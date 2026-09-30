package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

public interface MercatiResponsabiliService extends BaseService<MercatiResponsabili, PkId> {

    public List<MercatiResponsabili> findByResponsabile(Integer codiceResponsabile, Integer firstResult, Integer maxResult);

    public List<MercatiResponsabili> findByMercato(Integer codiceMercato, Integer firstResult, Integer maxResult);

    public List<MercatiResponsabili> findByResponsabileAndData(Integer codiceResponsabile, Date date);

    public List<MercatiResponsabili> findByResponsabileDallaDataAllaData(Integer codiceResponsabile, Date dallaData, Date allaData);

    public int countByResponsabile(Integer codiceResponsabile);

    public int countByResponsabileAndMercato(Integer codiceResponsabile, Integer codiceMercato);
}
