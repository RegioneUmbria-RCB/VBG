using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede
{
    public static class IstanzeStradarioExtensions
    {
        public static LocalizzazioneIstanza ToLocalizzazioneIstanza(this IstanzeStradario source)
        {
            if (source == null) return null;

            var x = source;

            return new LocalizzazioneIstanza
            {
                Civico = x.CIVICO,
                Indirizzo = x.Stradario.PREFISSO + " " + x.Stradario.DESCRIZIONE,
                Coordinate = String.IsNullOrEmpty(x.Longitudine) ? null : new LocalizzazioneIstanza.Coordinata(x.Longitudine, x.Latitudine),
                Esponente = x.ESPONENTE,
                EsponenteInterno = x.ESPONENTEINTERNO,
                Interno = x.INTERNO,
                Km = x.Km,
                Note = x.NOTE,
                Mappali = null,
                Piano = x.Piano,
                Scala = x.SCALA,
                TipoLocalizzazione = x.TipoLocalizzazione == null ? String.Empty : x.TipoLocalizzazione.Descrizione,
                Uuid = x.Uuid
            };
        }

    }

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

    public class QueryLocalizzazioniFactory : IQueryLocalizzazioniFactory
    {
        private readonly Istanze _istanza;

        public QueryLocalizzazioniFactory(Istanze istanza)
        {
            this._istanza = istanza;
        }

        public IQueryLocalizzazioni GetQueryLocalizzazioni() => new QueryLocalizzazioni(this._istanza);
    }
}