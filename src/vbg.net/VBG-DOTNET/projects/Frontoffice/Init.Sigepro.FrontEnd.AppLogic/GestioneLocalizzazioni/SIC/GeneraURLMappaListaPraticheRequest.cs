using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class GeneraURLMappaListaPraticheRequest : GeneraURLMappaRequest
    {
        public ICollection<string> UuidIstanze { get; set; }
    }
}
