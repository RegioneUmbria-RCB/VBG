package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoRestBean;

import java.util.List;

public interface FoConsensiInformativiService extends BaseService<FoConsensiInformativi, PkId> {

    public List<FoConsensiInformativi> findByIdentificativoUtente(String identificativoUtente);

    public boolean isConsensoPresente(String identificativoUtente, Integer identificativoConsenso);

    public void inserisciConsenso(String userid, Integer identificativoConsenso);

    public ConsensoInformativoRestBean leggiConsenso(String userid, Integer identificativoConsenso);
}
