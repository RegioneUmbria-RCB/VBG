package it.gruppoinit.pal.gp.core.features.commissioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.ElencoSoggettiIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneListModel;

@SuppressWarnings("rawtypes")
public interface ICommissioniDAO extends BaseDAO {

    ElencoSoggettiIstanzaModel getElencoSoggettiIstanza(int idRiga);

    void updateConvocazione(Integer codiceCommissione, Integer codiceConvocazione);

    void updateDataOraByConvocazione(Integer codiceCommissione, Integer codiceConvocazione);

    List<CommissioneListModel> listaCommissioniPerOperatore(Integer codiceOperatore, Integer firstResult, Integer maxResults);
}
