package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.QrxmlBase;

public interface QrxmlBaseService extends BaseService<QrxmlBase, PkId> {

    List<QrxmlBase> findByDescrizione(String term);
}
