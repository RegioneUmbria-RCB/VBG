package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;

public interface IRecuperaInformazioniGiornataService {

    public boolean isBattitore(String codiceIstatPresenza);

    // public InfoGiornataPresenzaBean getInformazioniPresenza(Integer idGiornata, Integer idPosteggio);

    public List<LivelloServizio> livelliDiServizioElencoCompletoDisponibili();

    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerGiornataEIdPosteggio(Integer idGiornata, Integer idConcessione);

    public Integer getIdContoAttivoDaFormulaEIdGiornata(MercatiFormuleCalcolo formula, Integer idGiornata);

    public BigDecimal getCoefficienteMercato(Integer idConto, Integer idGiornata, Integer idPosteggio);

    void resetObjectCached();
}
