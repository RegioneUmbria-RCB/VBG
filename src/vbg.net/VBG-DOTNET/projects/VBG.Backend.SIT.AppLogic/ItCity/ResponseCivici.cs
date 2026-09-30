using System.Collections.Generic;

namespace VBG.Backend.SIT.AppLogic.ItCity
{
    public class ResponseCivici
    {
        public bool Esito { get; set; }

        public string MessaggioErrore { get; set; }

        public List<CiviciJSON> Dati { get; set; }
    }
}
