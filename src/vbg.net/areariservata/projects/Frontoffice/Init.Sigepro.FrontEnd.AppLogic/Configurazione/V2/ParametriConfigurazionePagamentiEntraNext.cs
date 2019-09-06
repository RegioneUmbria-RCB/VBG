using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriConfigurazionePagamentiEntraNext : PaymentSettingsEntraNext, IParametriConfigurazione
    {
        public ParametriConfigurazionePagamentiEntraNext(string urlWs, string identificativoConnettore, string codiceFiscaleEnte, string versione, string identificativo, string username, string password, string urlRitorno, string urlNotifica, string codiceTipoPagamento) 
            : base(urlWs, username, password, identificativo, identificativoConnettore, codiceFiscaleEnte, versione, urlRitorno, urlRitorno, urlNotifica, codiceTipoPagamento)
        {

        }
    }
}
