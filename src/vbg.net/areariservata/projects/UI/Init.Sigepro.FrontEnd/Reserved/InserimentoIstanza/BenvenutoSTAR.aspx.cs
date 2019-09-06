using Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class BenvenutoSTAR : IstanzeStepPage
    {
        [Inject]
        protected STARUrlService _starService { get; set; }

        ILog _log = LogManager.GetLogger(typeof(BenvenutoSTAR));

        protected void Page_Load(object sender, EventArgs e)
        {
            var cf = UserAuthenticationResult.DatiUtente.Codicefiscale;
            var nome = UserAuthenticationResult.DatiUtente.Nome;
            var cognome = UserAuthenticationResult.DatiUtente.Nominativo;
            var sesso = UserAuthenticationResult.DatiUtente.Sesso;

            var url = _starService.GetUrlNuovaDomanda(cf, nome, cognome, sesso);

            _log.DebugFormat("Trasferisco il controllo all'accettatore all'indirizzo {0}", url);

            Response.Redirect(url);
        }

    }
}