using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DumpXmlDomanda : IstanzeStepPage
    {
        [Inject]
        protected ISalvataggioDomandaStrategy SalvataggioDomandaStrategy { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            Response.Clear();


            var domanda = SalvataggioDomandaStrategy.GetAsXml(IdDomanda);

            Response.ContentType = "text/plain;charset=UTF-8";

            Response.BinaryWrite(domanda);

            Response.End();
        }
    }
}