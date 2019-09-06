using Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class LeMiePraticheSTAR : ReservedBasePage
    {
        [Inject]
        public STARUrlService _starUrlService { get; set; }

        ILog _log = LogManager.GetLogger(typeof(LeMiePraticheSTAR));

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack && this._starUrlService.StarAttivo())
            {
                var qs = Request.QueryString.ToString();

                var cf = UserAuthenticationResult.DatiUtente.Codicefiscale;
                var nome = UserAuthenticationResult.DatiUtente.Nome;
                var cognome = UserAuthenticationResult.DatiUtente.Nominativo;
                var sesso = UserAuthenticationResult.DatiUtente.Sesso;

                var url = _starUrlService.GetUrlLeMiePratiche(cf, nome, cognome, sesso);

                _log.DebugFormat("Trasferisco il controllo all'accettatore all'indirizzo {0}", url);

                Response.Redirect(url);


            }

        }
    }
}