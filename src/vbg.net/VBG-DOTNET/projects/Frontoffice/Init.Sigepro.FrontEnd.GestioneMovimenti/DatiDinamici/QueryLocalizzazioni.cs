using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Converters;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
    public class QueryLocalizzazioni : QueryLocalizzazioniBase
    {
        private readonly Istanze _istanza;

        public QueryLocalizzazioni(Istanze istanza)
        {
            this._istanza = istanza;
        }

        public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
        {
            return this.
                    _istanza.
                    Stradario.
                    Where(x =>
                    {
                        if (String.IsNullOrEmpty(tipoLocalizzazione))
                            return x.TipoLocalizzazione == null;

                        return x.TipoLocalizzazione.Descrizione.ToUpperInvariant() == tipoLocalizzazione.ToUpperInvariant();
                    })
                    .Select(x => x.ToLocalizzazioneIstanza());
        }
    }
}
