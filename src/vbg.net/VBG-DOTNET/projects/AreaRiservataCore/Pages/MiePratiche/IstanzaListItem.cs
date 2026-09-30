using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;

namespace AreaRiservataCore.Pages.MiePratiche
{
    public class IstanzaListItem
    {
        public string Uuid { get; init; } = "";
        public int CodiceIstanza { get; init; }
        public string NumeroIstanza { get; init; } = "";
        public DateTime? DataPresentazione { get; init; }
        public string TipoIntervento { get; init; } = "";
        public string NumeroProtocollo { get; init; } = "";
        public DateTime? DataProtocollo { get; init; }
        public string Azienda { get; init; } = "";
        public string Localizzazione { get; init; } = "";
        public string Richiedente { get; init; } = "";
        public string Stato { get; init; } = "";
        public string Fascicolo { get; init; } = "";
        public string Oggetto { get; init; } = "";
    }

    public static class IstanzaListItemExtensionMethods
    {
        public static IEnumerable<IstanzaListItem> ToIstanzaListItem(this IEnumerable<VisuraListItem>? items)
        {
            if (items is null)
                return Enumerable.Empty<IstanzaListItem>();

            return items.Select(item => item.ToIstanzaListItem());
        }

        public static IstanzaListItem ToIstanzaListItem(this VisuraListItem? item)
        {
            if (item is null)
                return new IstanzaListItem();

            return new IstanzaListItem
            {
                Uuid = item.Uuid,
                CodiceIstanza = item.CodiceIstanza,
                NumeroIstanza = item.NumeroIstanza,
                DataPresentazione = item.DataPresentazione,
                TipoIntervento = item.TipoIntervento,
                NumeroProtocollo = item.NumeroProtocollo,
                DataProtocollo = item.DataProtocollo,
                Azienda = item.Azienda,
                Localizzazione = item.LocalizzazioneConCivico,
                Richiedente = item.Richiedente,
                Stato = item.Stato,
                Fascicolo = item.PosizioneArchivio,
                Oggetto = item.Oggetto
            };
        }
    }
}
