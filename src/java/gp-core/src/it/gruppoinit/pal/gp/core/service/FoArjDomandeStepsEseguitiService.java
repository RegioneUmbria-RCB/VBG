package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeStepsEseguiti;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author fabrizioc
 */
public interface FoArjDomandeStepsEseguitiService extends BaseService<FoArjDomandeStepsEseguiti, PkId> {

    public List<FoArjDomandeStepsEseguiti> findByFoArjDomande(FoArjDomande foArjDomande);

    public void insertEseguito(FoArjDomande foArjDomande, FoArjSteps step);

    public void deleteAll(List<FoArjDomandeStepsEseguiti> stepsToDelete);
}
