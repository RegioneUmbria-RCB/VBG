package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

public interface UpgrIstanzeCollegateDAO extends BaseDAO {

    List<CollegamentiDuplicatiBean> findCollegamentiDuplicati();

    List<ProgressivoOrdineDuplicatoBean> findProgressivoOrdineDuplicati();

    void deleteByPk(String idComune, int id);

    void deleteDoppioni(String idComune, int progressivo, int codiceIstanza, Integer codiceIstanzaCollegata, int idDaEscludere);

    void deleteCollegamentiStessaIstanza();

    List<IstanzeCollegateBean> findByIdcomuneEProgressivo(String idComune, int progressivo);

    void updateOrdine(String idComune, int id, int ordine);
}
