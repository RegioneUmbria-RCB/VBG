using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR
{
    internal class CertificatoFirmaSTAR
    {
        private static class Constants
        {
            public const string PathCertificato = "Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR.accettatorertunico.p12";
        }

        internal X509Certificate2 GetCertificato()
        {
            var certPassword = "regionetoscana";

            using (var stream = Assembly.GetExecutingAssembly().GetManifestResourceStream(Constants.PathCertificato))
            {
                var buffer = new byte[stream.Length];
                stream.Read(buffer, 0, buffer.Length);

                return new X509Certificate2(buffer, certPassword, X509KeyStorageFlags.DefaultKeySet); ;
            }
        }
    }
}
