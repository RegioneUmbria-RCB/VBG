using Init.Sigepro.FrontEnd.Contenuti.GestioneContenuti;
using Ninject;

namespace Init.Sigepro.FrontEnd.Contenuti
{
    public class ContenutiBasePage : BasePage
    {
        [Inject]
        protected ConfigurazioneContenuti Configurazione { get; set; }

        public string AliasComune
        {
            get
            {
                return this.Request.QueryString["alias"];
            }
        }

        public override string IdComune
        {
            get
            {
                return this.Configurazione.DatiComune.IdComune;
            }
        }

        public override string Software
        {
            get
            {
                var sw = this.Request.QueryString["software"];

                if (string.IsNullOrEmpty(sw))
                    return "SS";

                return sw;
            }
        }
    }
}
