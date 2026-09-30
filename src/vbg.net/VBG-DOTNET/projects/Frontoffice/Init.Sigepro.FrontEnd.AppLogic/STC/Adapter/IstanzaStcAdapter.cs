using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Adapter
{
    public interface IIstanzaStcAdapter
    {
        DettaglioPraticaType Adatta(DomandaOnline domandaFo);
    }


    public class IstanzaStcAdapter : IIstanzaStcAdapter
    {
        private readonly List<IStcPartialAdapter> _partialAdapters = new List<IStcPartialAdapter>();

        public DettaglioPraticaType Adatta(DomandaOnline domandaFo)
        {
            var readInterface = domandaFo.ReadInterface;
            var dettaglioPratica = new DettaglioPraticaType();

            foreach (var adapter in this._partialAdapters)
                adapter.Adapt(readInterface, dettaglioPratica);

            return dettaglioPratica;
        }

        internal void RegisterPartialAdapter(IStcPartialAdapter adapterInstance)
        {
            this._partialAdapters.Add(adapterInstance);
        }
    }
}
