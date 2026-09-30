using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class nuovo_come_copia : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Esempio di url per effettuare i test: http://localhost:1137/AreaRiservata/nuovo-da-copia/E256/SS/938/98137c0d-848c-482c-aad8-04124782dccb

            var alias = Page.RouteData.Values["alias"].ToString();
            var software = Page.RouteData.Values["software"].ToString();
            var idDomanda = Page.RouteData.Values["idDomanda"].ToString();


            Response.Write($@"
<b>alias:</b> {alias}<br />
<b>software:</b> {software}<br />
<b>idDomanda:</b> {idDomanda}<br />
");

            var url = UrlBuilder.Url("~/reserved/nuovaistanza.aspx", qs =>
            {
                qs.Add(new QsAliasComune(alias));
                qs.Add(new QsSoftware(software));
                qs.Add(new QsCopiaDa(Convert.ToInt32(idDomanda)));
            });

            Response.Redirect(url);
        }
    }
}