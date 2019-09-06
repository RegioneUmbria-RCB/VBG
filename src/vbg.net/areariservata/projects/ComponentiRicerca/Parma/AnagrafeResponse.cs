using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Parma
{
    public class AnagrafeResponse
    {
        public string cod_comune { get; set; }
        public string cod_comune_nascita { get; set; }
        public string cod_comune_presso { get; set; }
        public string telefono { get; set; }    
        public string cellulare { get; set; }
        public string mail { get; set; }
        public string mailpec { get; set; }
        public string comune_presso { get; set; }
        public string comune_nascita { get; set; }
        public string codiceparentela { get; set; }
        public string descrizioneparentela { get; set; }
        public string cdn { get; set; }
        public string nome { get; set; }
        public string cognome { get; set; }
        public string comune { get; set; }
        public DateTime? datanascita { get; set; }
        public string codicefiscale { get; set; }
        public string codicevia { get; set; }
        public string indirizzo { get; set; }
        public string civico { get; set; }
        public string esponente { get; set; }
        public string cap { get; set; }
        public string codicefamiglia { get; set; }

        public AnagrafeResponse()
        {

        }
    }
}



