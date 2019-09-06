using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public class PCScheda
    {
        public int Id { get; set; }
        public string Titolo { get; set; }
        public string Link { get; set; }

        public IEnumerable<PCModello> Modelli { get; set; }
        public IEnumerable<PCAllegato> Allegati { get; set; }

        public PCScheda()
        {
            this.Modelli = new List<PCModello>();
            this.Allegati = new List<PCAllegato>();
        }
    }
}
