using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace VBG.AppLogic.SSU.STC
{
    public class SsuPartialAdapter : IStcPartialAdapter
    {
        private readonly ProcedimentiSsuPartialAdapter _procedimentiPartialAdapter;
        private readonly DatiDinamiciSsuPartialAdapter _datiDinamiciSsuPartialAdapter;

        public SsuPartialAdapter(ProcedimentiSsuPartialAdapter procedimentiPartialAdapter, DatiDinamiciSsuPartialAdapter datiDinamiciSsuPartialAdapter)
        {
            this._procedimentiPartialAdapter = procedimentiPartialAdapter;
            this._datiDinamiciSsuPartialAdapter = datiDinamiciSsuPartialAdapter;
        }

        public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
        {
            if (!readInterface.Ssu.ModalitaSsuAttiva)
            {
                return;
            }

            // Sostituisco il codice comune con il codice istat utilizzato internamente per le pratiche SSU
            dettaglioPratica.codiceComune = new ComuneType
            {
                Item = readInterface.Ssu.CodiceEnte,
                ItemElementName = ItemChoiceType.codiceIstat
            };

            // Adatto la lista di procedimenti SSU
            this._procedimentiPartialAdapter.Adapt(readInterface, dettaglioPratica);
            this._datiDinamiciSsuPartialAdapter.Adapt(readInterface, dettaglioPratica);
        }
    }
}
