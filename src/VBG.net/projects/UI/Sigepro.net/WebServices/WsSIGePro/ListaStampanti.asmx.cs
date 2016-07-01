using System;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Collection;
using System.Drawing.Printing;
using Init.SIGePro.Authentication;
using System.Data;
using System.Security.Principal;
using System.Runtime.InteropServices;
using System.Configuration;
using Init.SIGePro.Exceptions.Token;
using System.Collections.Generic;


namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for ListaStampanti
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class ListaStampanti : System.Web.Services.WebService
    {

        public ListaStampanti()
        {

            //Uncomment the following line if using designed components 
            //InitializeComponent(); 
        }


        [WebMethod]
        public List<string> ListPrinters(string sToken)
        {
            return list(sToken);
        }

        private List<string> list(string sToken)
        {
            List<string> listPrinters = new List<string>();

            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(sToken);

            if (authInfo == null)
                throw new InvalidTokenException(sToken);

			if (PrinterSettings.InstalledPrinters.Count == 0)
				throw new InvalidOperationException("Non sono state trovate stampanti installate nella macchina. Potrebbe essere un problema di permessi. Provare a far girare l'application pool con un utente con privilegi più elevati");

            foreach (string printerName in PrinterSettings.InstalledPrinters)
            {
                listPrinters.Add(printerName);
            }

            return listPrinters;
        }

    }
}

