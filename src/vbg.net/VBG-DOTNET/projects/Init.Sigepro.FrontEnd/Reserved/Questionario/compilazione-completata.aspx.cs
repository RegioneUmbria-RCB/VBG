using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Questionario
{
    public partial class compilazione_completata : ReservedBasePage
    {
        private QsUuidIstanza Uuid => new QsUuidIstanza(Request.QueryString);

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/dettaglioistanzaex.aspx", qs =>
            {
                qs.Add(new QsAliasComune(IdComune));
                qs.Add(new QsSoftware(Software));
                qs.Add(Uuid);
            });

            Response.Redirect(url);
        }
    }
}