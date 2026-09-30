package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Normegenerali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormegeneraliBean;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface NormegeneraliService extends BaseService<Normegenerali, PkId> {

    /**
     * @see NormegeneraliDAO#findByFilter(Set<Software> softwareList)
     */
    public List<Normegenerali> findByFilter(Set<Software> softwareList);

    public List<NormegeneraliBean> findNormegenerali(Integer firstResult, Integer maxResults);
}
