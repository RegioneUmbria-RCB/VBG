using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Events
{
    public class SostituzioneDocumentaleEffettuata : Event
    {
        public string IdComune { get; set; }
        public int IdMovimento { get; set; }
        public OrigineDocumentoSostituzioneDocumentale OrigineDocumento { get; set; }
        public int CodiceOggettoOriginale { get; set; }
        public string NomeFileOriginale { get; set; }
        public int CodiceOggettoSostitutivo { get; set; }
        public string NomeFileSostitutivo { get; set; }
    }
}
