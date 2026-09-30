using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject.Web;
using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class nuovo_da_template_istanza_presentata : PageBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = Page.RouteData.Values["alias"].ToString();
            var software = Page.RouteData.Values["software"].ToString();
            var uuIdIstanza = Page.RouteData.Values["uuid-istanza"].ToString();

            if (!IsPostBack)
            {
                var url = UrlBuilder.Url("~/reserved/inserimentoistanza/copiadatemplate/copiadatemplate.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(alias));
                    qs.Add(new QsSoftware(software));
                    qs.Add(new QsUuidIstanza(uuIdIstanza));
                });

                Response.Redirect(url);
            }
        }
    }
}