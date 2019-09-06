using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Events
{
    public class SostituzioneDocumentaleAnnullata : Event
    {
        public string IdComune { get; set; }
        public int IdMovimento { get; set; }
        public int CodiceOggettoSostitutivo { get; set; }
    }
}
