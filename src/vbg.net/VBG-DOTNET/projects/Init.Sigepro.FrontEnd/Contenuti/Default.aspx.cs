using Init.Sigepro.FrontEnd.Contenuti.GestioneContenuti;
using Ninject;
using System;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.Contenuti
{
    public partial class Default : ContenutiBasePage
    {
        [Inject]
        protected ConfigurazioneContenuti _configurazione { get; set; }

        public bool MostraRicercaAteco
        {
            get
            {
                string s = ConfigurationManager.AppSettings["MostraRicercaAtecoNeiContenuti"];

                bool mostraRicerca = true;

                if (bool.TryParse(s, out mostraRicerca))
                    return mostraRicerca;

                return true;
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected string GetUrlLink1()
        {
            var defaultUrl = this.ResolveClientUrl("~/Contenuti/Step2.aspx") + "?alias=" + this.AliasComune + "&Software=" + this.Software;

            if (String.IsNullOrEmpty(this._configurazione.Testi.FrameCentrale.Link1.Href))
            {
                return defaultUrl;
            }

            return this._configurazione.Testi.FrameCentrale.Link1.Href;
        }

        protected string GetUrlLink2()
        {
            var defaultUrl = this.ResolveClientUrl("~/Contenuti/Step1.aspx") + "?alias=" + this.AliasComune + "&Software=" + this.Software;

            if (String.IsNullOrEmpty(this._configurazione.Testi.FrameCentrale.Link2.Href))
            {
                return defaultUrl;
            }

            return this._configurazione.Testi.FrameCentrale.Link2.Href;
        }
    }
}
