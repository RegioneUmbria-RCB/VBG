namespace VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses
{
    public class RisultatoVerificaCivico : Civico
    {
        public Particella particella { get; set; }
        public string esponente { get; set; }
        public int? codiceFabbricato { get; set; }

        internal Data.Sit ToDatiLocalizzazione()
        {
            var dati = new Data.Sit()
            {
                CodVia = this.codiceVia,
                Civico = this.numero,
                CodCivico = this.wkt,
                Esponente = this.esponente,
                Longitudine = this.centerX.ToString(),
                Latitudine = this.centerY.ToString(),
                Fabbricato = this.codiceFabbricato.ToString(),
            };

            if (this.particella != null)
            {
                dati.TipoCatasto = this.particella.tipo;
                dati.Sezione = this.particella.sez;
                dati.Foglio = this.particella.foglio.TrimStart('0');
                dati.Particella = this.particella.numero.TrimStart('0');
            }

            return dati;
        }

    }
}
