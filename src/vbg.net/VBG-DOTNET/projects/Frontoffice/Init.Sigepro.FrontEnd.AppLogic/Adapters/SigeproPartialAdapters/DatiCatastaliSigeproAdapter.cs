using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class DatiCatastaliSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            dst.Mappali = src.Localizzazioni
                                                    .Indirizzi
                                                    .SelectMany(x => x.RiferimentiCatastali)
                                                    .Select(x => new IstanzeMappali
                                                    {
                                                        Fkcodiceistanza = int.MinValue,
                                                        Idmappale = int.MinValue,
                                                        Codicecatasto = x.TipoCatasto == RiferimentoCatastale.TipoCatastoEnum.Terreni ? "T" : "F",
                                                        Foglio = x.Foglio,
                                                        Particella = x.Particella,
                                                        Sub = x.Sub
                                                    })
                                                    .ToArray();
        }
    }
}
