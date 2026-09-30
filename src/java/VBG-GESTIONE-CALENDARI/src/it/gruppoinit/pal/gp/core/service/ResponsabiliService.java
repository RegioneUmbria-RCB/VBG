package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

public interface ResponsabiliService extends BaseService<Responsabili, PkId> {

    public Responsabili findByUserId(String userId);

    public void insertResponsabilePerComune(Responsabili entity, String codicecomune);

    public void deleteResponsabilePerComune(String codiceresponsabile, String codicecomune);

    public List<Responsabili> findAll();

    public void insertComunePerResponsabile(String codiceresp, String codicecomune);

    public void insertTuttiIComuniPerResponsabile(String codiceresp);

    public void eliminaComunePerResponsabile(String codiceresp, String codicecomune);
}
