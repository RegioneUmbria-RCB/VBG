using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class PhantomjsBootstrapper
    {
        public static class Constants
        {
            public const string PhantomJs = "phantomjs.exe";
        }

        public void Bootstrap()
        {
            var phantomjsPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "phantomjs");

            if (!File.Exists(Path.Combine(phantomjsPath, Constants.PhantomJs)))
            {
                throw new InvalidOperationException("Phantomjs non trovato nel path");
            }

            try
            {
                new PhantomjsRenderer(phantomjsPath).RenderHtml("<html><body>It works!</body></html>");
            }
            catch(TargetInvocationException ex){

                var innerException = ex.InnerException == null ? "Nessuna inner exception" : ex.ToString();

                throw new InvalidOperationException(
                    String.Format("Il servizio di generazione files non è disponibile. Verificare i permessi di accesso. Inner exception: {0}", innerException), ex);
            }
            catch(Exception ex)
            {
                throw new InvalidOperationException("Il servizio di generazione files non è disponibile. Verificare i permessi di accesso", ex);
            }
        }
    }
}
