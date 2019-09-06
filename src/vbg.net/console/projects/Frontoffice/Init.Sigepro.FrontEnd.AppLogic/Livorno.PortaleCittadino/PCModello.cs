using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public class PCModello
    {
        public int Id { get; set; }
        public string Descrizione { get; set; }
        public bool Obbligatorio { get; set; }
        public string Link { get; set; }
        public bool RichiedeFirma { get; set; }
        public string NomeFile { get; set; }
    }
}
