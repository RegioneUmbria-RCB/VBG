using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.moduli_fvg.compilazione
{
    public partial class index : System.Web.UI.Page
    {
        // Testare l'url http://localhost:1137/AreaRiservata/moduli-fvg/compilazione/lista.aspx?idcomune=FVGSOL&software=SS&istanza=633424332223082&modulo=QIG

        private static class Constants
        {
            public const string UrlCompilazionePratica = "~/moduli-fvg/compilazione/lista.aspx";
            public const string UrlAnteprimaPdf = "~/moduli-fvg/compilazione/anteprima.aspx";
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // La richiesta arriva nel formato:
            // modulifvg/{alias}/{software}/compila/{istanza}/{cod-modulo}
            var alias = new QsAliasComune(Page.RouteData.Values["alias"].ToString());
            var software = new QsSoftware(Page.RouteData.Values["software"].ToString());
            var codModulo = new QsFvgIdModulo(Page.RouteData.Values["cod-modulo"].ToString());

            var istanzaStr = Page.RouteData.Values["istanza"].ToString();

            if (istanzaStr == "null")
            {
                var urlAnteprima = UrlBuilder.Url(Constants.UrlAnteprimaPdf, x =>
                {
                    x.Add(alias);
                    x.Add(software);
                    x.Add(codModulo);
                });

                Response.Redirect(urlAnteprima);

                return;
            }

            var istanza = new QsFvgCodiceIstanza(Convert.ToInt64(istanzaStr));
            
            var url = UrlBuilder.Url(Constants.UrlCompilazionePratica, x =>
            {
                x.Add(alias);
                x.Add(software);
                x.Add(istanza);
                x.Add(codModulo);
            });

            Response.Redirect(url);
        }
    }
}