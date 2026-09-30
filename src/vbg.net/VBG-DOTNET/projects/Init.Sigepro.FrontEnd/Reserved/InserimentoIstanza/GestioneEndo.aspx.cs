using log4net;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneEndo : IstanzeStepPage
    {
        ILog _log = LogManager.GetLogger(typeof(GestioneEndo));

        protected void Page_Load(object sender, EventArgs e)
        {

        }



        public override bool CanEnterStep()
        {
            Response.Redirect("~/Reserved/InserimentoIstanza/GestioneEndoV2.aspx" + Request.QueryString);

            throw new Exception("Step non supportato, utilizzare GestioneEndoV2");
        }
    }
}
