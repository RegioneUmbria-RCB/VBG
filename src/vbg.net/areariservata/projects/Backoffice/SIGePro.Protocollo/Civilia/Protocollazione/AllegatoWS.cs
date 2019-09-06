using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Civilia.Protocollazione
{
    public class AllegatoWS
    {
        public string NomeFile { get; set; }
        public byte[] File { get; set; }
        public string MimeType { get; set; }
        public string Titolo { get; set; }
        public string Descrizione { get; set; }
        public bool IsPrincipale { get; set; }
        public string IdSingoloFattura { get; set; }

        public AllegatoWS()
        {

        }
    }
}
