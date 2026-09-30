using System;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda
{
    public class TemplateRiepilogoSingolaScheda
    {
        // Reso pubblico per poter essere utilizzato nei test
        public static class Constants
        {
            public const string DefaultHtmlTemplate = @"
<!DOCTYPE html>
<html lang=""it"">
    <head>
        <meta charset=""utf-8"">
        <title>Riepilogo scheda</title>
        <meta name=""viewport"" content=""width=device-width, initial-scale=1"">
        <style type=""text/css"" media=""all"">
            * { font-family: arial, sans-serif; font-size: 12px; }
        </style>
        <cssScheda/>
    </head>
    <body>
        <schedaDinamica/>
    </body>
</html>";
        }

        private static readonly Regex _regexCss = new Regex(@"<cssscheda\s*/>", RegexOptions.IgnoreCase | RegexOptions.Compiled);
        private static readonly Regex _regexScheda = new Regex(@"<schedadinamica\s*/>", RegexOptions.IgnoreCase | RegexOptions.Compiled);

        // Reso pubblico per poter essere utilizzato nei test
        public string Html { get; }

        public TemplateRiepilogoSingolaScheda(string html)
        {
            html = html.Replace("{", "{{").Replace("}", "}}");

            var tmp = _regexCss.Replace(html, "{0}");
            tmp = _regexScheda.Replace(tmp, "{1}");

            this.Html = tmp;
        }

        public static readonly TemplateRiepilogoSingolaScheda Default = new TemplateRiepilogoSingolaScheda(Constants.DefaultHtmlTemplate);

        public string ApplicaAdHtmlScheda(string cssSchedeMovimento, string htmlSchedaMovimento)
        {
            return String.Format(this.Html, cssSchedeMovimento, htmlSchedaMovimento);
        }
    }
}
