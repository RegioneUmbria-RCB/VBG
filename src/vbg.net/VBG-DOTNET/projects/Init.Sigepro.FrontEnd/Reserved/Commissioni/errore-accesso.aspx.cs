using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Commissioni
{
    public partial class errore_accesso : ReservedBasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/default.aspx", mp =>
            {
                mp.Add(new QsAliasComune(this.IdComune));
                mp.Add(new QsSoftware(this.Software));
            });

            Response.Redirect(url);
        }
    }
}