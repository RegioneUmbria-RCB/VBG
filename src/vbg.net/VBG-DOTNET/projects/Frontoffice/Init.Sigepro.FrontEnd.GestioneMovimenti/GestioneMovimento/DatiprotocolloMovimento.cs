using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento
{
    public class DatiProtocolloMovimento
    {
        public string Numero { get; set; }
        public DateTime? Data { get; set; }

        public bool DatiPresenti { get { return !String.IsNullOrEmpty(this.Numero) && this.Data.HasValue; } }
    }
}
