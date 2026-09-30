package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LeggitipiService extends BaseService<Leggitipi, PkId> {

    public List<Leggitipi> findByFilter(Leggitipi entity);
}
