using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione
{
    public interface ILogicaSincronizzazioneTipiSoggetto
    {
        void Sincronizza(PresentazioneIstanzaDbV2.ANAGRAFERow row);
    }

    public class LogicaSincronizzazioneTipiSoggetto : ILogicaSincronizzazioneTipiSoggetto
    {
        ITipiSoggettoService _tipiSoggettoService;

        public LogicaSincronizzazioneTipiSoggetto(ITipiSoggettoService tipiSoggettoRepository)
        {
            if (tipiSoggettoRepository == null)
                throw new System.ArgumentNullException(nameof(tipiSoggettoRepository));
            //Condition.Requires(tipiSoggettoRepository, "tipiSoggettoRepository").IsNotNull();

            this._tipiSoggettoService = tipiSoggettoRepository;
        }

        public void Sincronizza(PresentazioneIstanzaDbV2.ANAGRAFERow row)
        {
            if (row.TIPOSOGGETTO == null)
                return;

            var tipoSoggetto = _tipiSoggettoService.GetById(row.TIPOSOGGETTO.Value);

            if (tipoSoggetto != null)
            {
                row.DescrSoggetto = tipoSoggetto.Descrizione;
                row.FlagTipoSoggetto = tipoSoggetto.FlagTipoDato;
                row.FlagRichiedeAnagraficaCollegata = tipoSoggetto.RichiedeAnagraficaCollegata;
            }
            else
            {
                row.DescrSoggetto = "Attenzione, il tipo soggetto precedentemente selezionato non è più valido, assegnare all'anagrafica un nuovo tipo soggetto";
                row.FlagTipoSoggetto = "";
            }
        }
    }

    public class NullLogicaSincronizzazioneTipiSoggetto : ILogicaSincronizzazioneTipiSoggetto
    {
        #region ILogicaSincronizzazioneTipiSoggetto Members

        public void Sincronizza(PresentazioneIstanzaDbV2.ANAGRAFERow row)
        {
            // non fa niente...
        }

        #endregion
    }
}
