using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class EventiSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            dst.Eventi = src.AltriDati
                            .Eventi
                            .Select(x => new IstanzeEventi
                            {
                                Data = x.Data,
                                Fkidcategoriaevento = x.Codice,
                                Descrizione = x.Descrizione
                            })
                            .ToArray();
        }
    }
}
