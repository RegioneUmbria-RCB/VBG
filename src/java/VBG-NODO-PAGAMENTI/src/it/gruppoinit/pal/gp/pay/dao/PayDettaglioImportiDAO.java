package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;

public interface PayDettaglioImportiDAO extends BaseDAO<PayDettaglioImporti, PkId> {

    List<Integer> findCausaliRaggruppate(List<Integer> idRcs);

    List<Integer> findCausaliRaggruppatePosizioneDebitoria(PayPosizioniDebitorie payPos);
}
