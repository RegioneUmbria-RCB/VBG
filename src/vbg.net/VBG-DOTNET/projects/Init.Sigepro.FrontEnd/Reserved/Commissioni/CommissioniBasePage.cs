using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.Commissioni
{
    public class CommissioniBasePage: ReservedBasePage
    {
        protected void ErroreAccesso()
        {
            var url = UrlBuilder.Url("~/reserved/commissioni/errore-accesso.aspx", mp =>
            {
                mp.Add(new QsAliasComune(IdComune));
                mp.Add(new QsSoftware(Software));
            });

            Response.Redirect(url);
        }
    }
}