using Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    public class IstanzaSigeproAdapterService : IIstanzaSigeproAdapterService
    {
        private readonly List<IIstanzaSigeproPartialAdapter> _adapters;
        private readonly IConfigurazioneVbgRepository _configurazioneVbgRepository;
        private IDomandaOnlineReadInterface? _ultimaDomandaAdattata;
        private Istanze? _ultimaIstanzaGenerata;

        public IstanzaSigeproAdapterService(IEnumerable<IIstanzaSigeproPartialAdapter> adapters, IConfigurazioneVbgRepository configurazioneVbgRepository)
        {
            this._adapters = adapters.ToList();
            this._configurazioneVbgRepository = configurazioneVbgRepository;
        }
        /*
        public IstanzaSigeproAdapterService(DatiGeneraliSigeproAdapter datiGeneraliSigeproAdapter, AnagraficheSigeproAdapter anagraficheSigeproAdapter,
            StradarioSigeproAdapter stradarioSigeproAdapter, ProcedimentiSigeproAdapter procedimentiSigeproAdapter,
            DocumentiSigeproAdapter documentiSigeproAdapter, OneriSigeproAdapter oneriSigeproAdapter,
            DatiCatastaliSigeproAdapter datiCatastaliSigeproAdapter, EventiSigeproAdapter eventiSigeproAdapter,
            DatiDinamiciSigeproAdapter datiDinamiciSigeproAdapter, IConfigurazioneVbgRepository configurazioneVbgRepository)
        {
            this._adapters.Add(datiGeneraliSigeproAdapter);
            this._adapters.Add(anagraficheSigeproAdapter);
            this._adapters.Add(stradarioSigeproAdapter);
            this._adapters.Add(procedimentiSigeproAdapter);
            this._adapters.Add(documentiSigeproAdapter);
            this._adapters.Add(oneriSigeproAdapter);
            this._adapters.Add(datiCatastaliSigeproAdapter);
            this._adapters.Add(eventiSigeproAdapter);
            this._adapters.Add(datiDinamiciSigeproAdapter);
            this._configurazioneVbgRepository = configurazioneVbgRepository;
        }
        */
        public Istanze ToIstanzaBackoffice(IDomandaOnlineReadInterface readInterface)
        {
            return this.ToIstanzaBackoffice(readInterface, IstanzaSigeproAdapterFlags.Default);
        }

        public Istanze ToIstanzaBackoffice([ValidatedNotNull] IDomandaOnlineReadInterface readInterface, IstanzaSigeproAdapterFlags flags)
        {
#if NET48_OR_GREATER
            if (readInterface == this._ultimaDomandaAdattata && this._ultimaIstanzaGenerata != null)
            {
                return this._ultimaIstanzaGenerata;
            }
#endif
            var istanza = new Istanze();

            foreach (var adapter in this._adapters)
            {
                adapter.Adatta(readInterface, istanza, flags);
            }

            istanza.ConfigurazioneComune = this._configurazioneVbgRepository.LeggiConfigurazioneComune(readInterface.AltriDati.Software);

            this._ultimaDomandaAdattata = readInterface;
            this._ultimaIstanzaGenerata = istanza;

            return istanza;
        }
    }
}
