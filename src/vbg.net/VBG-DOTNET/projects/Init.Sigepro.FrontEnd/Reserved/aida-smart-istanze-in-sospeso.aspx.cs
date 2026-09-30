using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class aida_smart_istanze_in_sospeso : ReservedBasePage
    {
        //[Inject]
        //protected IAidaSmartService _aidaService { get; set; }

        protected string RedirUrl { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/vbg-istanze-in-sospeso.aspx", mp =>
            {
                mp.Add(new QsAliasComune(Request.QueryString));
                mp.Add(new QsSoftware(Request.QueryString));
            });

            Response.Redirect(url);
        }
    }
}