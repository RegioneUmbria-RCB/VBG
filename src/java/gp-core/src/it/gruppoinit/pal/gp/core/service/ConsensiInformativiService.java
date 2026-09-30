package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.ConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoDettaglioBean;

import java.util.List;

public interface ConsensiInformativiService extends BaseService<ConsensiInformativi, PkId> {

    public List<ConsensoInformativoDettaglioBean> findConsensiDaApprovare(String userId);
}
