using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.AidaSmart
{
    public interface IASCrossLoginClient
    {
        string GetCrossLginToken(string nome, string cognome, string codiceFiscale, string sesso);        
    }
}
