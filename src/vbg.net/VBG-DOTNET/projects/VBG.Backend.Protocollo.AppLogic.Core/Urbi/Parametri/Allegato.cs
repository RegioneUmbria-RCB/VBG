using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione;
using System.Collections.Specialized;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    public class Allegato : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public Allegato(int progressivo, AllegatoFascicolo allegato)
        {
            this.Parametro.Add($"PRCORE03_{progressivo}_Allegato_Classificazione_1", allegato.PrimaClassificazione);
            this.Parametro.Add($"PRCORE03_{progressivo}_Allegato_CodiceTDFS", allegato.CodiceTipoDocumento);
            this.Parametro.Add($"PRCORE03_{progressivo}_Allegato_Formato", allegato.Formato);
            this.Parametro.Add($"PRCORE03_{progressivo}_Allegato_PathFile", allegato.Percorso);
            this.Parametro.Add($"PRCORE03_{progressivo}_Allegato_Tipo", allegato.Tipologia);
        }
    }
}
