namespace VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses
{
    public class Esponente : Civico
    {
        public string esponente { get; set; }

        internal Data.Sit ToDatiLocalizzazione()
        {
            return new Data.Sit()
            {
                CodCivico = this.wkt,
                Longitudine = this.centerX.ToString(),
                Latitudine = this.centerY.ToString(),
                CodVia = this.codiceVia,
                Civico = this.numero,
                Esponente = this.esponente.TrimStart('0')
            };
        }
    }
}
