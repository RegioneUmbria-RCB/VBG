package it.gruppoinit.pal.gp.core.features.commissioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.DettaglioCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.ElencoSoggettiIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneListModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;

public interface ICommissioniService {

    CommissioneModel getCommissione(int idCommissione);

    void insert(CommissioneModel commissione);

    void updateCommissione(CommissioneModel commissione);

    DettaglioCommissioneModel getDettaglioCommissione(int idCommissione);

    ElencoSoggettiIstanzaModel getElencoSoggettiIstanza(int idRiga);

    void updateCommissioniedilizieTAndChild(DettaglioCommissioneModel model);

    void delete(Integer idCommissione);

    void updateConvocazione(Integer codiceCommissione, Integer codiceConvocazione);

    void riapriCommissioneChiusa(Integer codiceCommissione);

    void updateOrario(Integer idCommissione, String oraInizio, String oraFine);

    CommissioneModel populateModelFromMovimento(Integer codiceMovimento);

    /**
     * Recupera la lista delle commissioni edilizie/cds per le quali l'utente loggato ha i ruoli associati o quelle che
     * non hanno configurato ruoli
     */
    List<CommissioneListModel> listaCommissioniPerOperatore(Integer codiceOperatore, Integer firstResult, Integer maxResults);
}
