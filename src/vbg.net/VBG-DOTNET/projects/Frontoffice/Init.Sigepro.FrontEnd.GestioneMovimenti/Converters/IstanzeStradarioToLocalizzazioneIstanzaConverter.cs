using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Converters
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
}
