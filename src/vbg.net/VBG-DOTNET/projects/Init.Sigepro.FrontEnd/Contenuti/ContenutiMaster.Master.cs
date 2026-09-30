using Init.Sigepro.FrontEnd.Contenuti.GestioneContenuti;
using Ninject;
using System;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd.Contenuti
{
    public partial class ContenutiMaster : Ninject.Web.MasterPageBase
    {
        [Inject]
        protected ConfigurazioneContenuti _configurazione { get; set; }


        public bool SoloPagina
        {
            get
            {
                var qs = this.Request.QueryString["SoloPagina"];

                if (qs == null)
                    return false;

                return qs.ToUpper() == "TRUE";
            }
        }


        public string IdComune
        {
            get { return this._configurazione.DatiComune.IdComune; }
        }

        public string Software
        {
            get
            {
                var sw = this.Request.QueryString["software"];

                if (String.IsNullOrEmpty(sw))
                    return "SS";
                return sw;
            }
        }

        public string AliasComune
        {
            get { return this.Request.QueryString["alias"]; }
        }

        /// <summary>
        /// Ottiene o imposta quale fase si sta visualizzando
        /// </summary>
        public int StepId { get; set; }


        public bool MostraHelp { get; set; }

        public ContenutiMaster()
        {
            this.StepId = -1;
            this.MostraHelp = false;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.mainStyle.Href = this.ResolveClientUrl("~/css/contenuti/" + this._configurazione.Testi.NomeCss);
        }


    }
}
