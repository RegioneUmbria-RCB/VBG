package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Modelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface ModelliService extends BaseService<Modelli, PkId> {

    /**
     * @see ModelliDAO#findByFilter(Set<Software> softwareList)
     */
    public List<Modelli> findByFilter(Set<Software> softwareList);

    public List<Modelli> findBySoftwareAndTipoModello(String codicesoftware, Integer codiceTipoModello);
}
