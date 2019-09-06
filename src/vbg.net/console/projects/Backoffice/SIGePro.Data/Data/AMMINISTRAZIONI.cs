using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    public partial class Amministrazioni
    {
        public string Mezzo { get; set; }
        public string ModalitaTrasmissione { get; set; }

        public string PROT_UO { get; set; }
        public string PROT_RUOLO { get; set; }

        public Comuni ComuneResidenza { get; set; }

    }
}
