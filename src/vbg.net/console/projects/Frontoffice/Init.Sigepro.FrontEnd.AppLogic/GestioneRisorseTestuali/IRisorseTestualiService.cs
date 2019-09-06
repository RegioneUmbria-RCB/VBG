using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali
{
    public interface IRisorseTestualiService
    {
        string GetRisorsa(string id, string valoreDefault = "");
        void AggiornaRisorsa(string id, string valore);
    }
}
