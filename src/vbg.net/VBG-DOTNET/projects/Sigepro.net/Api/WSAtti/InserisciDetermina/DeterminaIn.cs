using System;
using System.Collections.Generic;

namespace Sigepro.net.Api.WSAtti.InserisciDetermina
{
    public class DeterminaIn
    {
        public string Oggetto { get; set; }
        public string Trattamento { get; set; }
        public string Proponente { get; set; }
        public string Dirigente { get; set; }
        public DateTime DataDocumento { get; set; }
        public string Classifica { get; set; }
        public bool DaPubblicare { get; set; }
        public string Utente { get; set; }
        public string Ruolo { get; set; }
        public IEnumerable<Allegato> Allegati { get; set; }
        public string Note { get; set; }
    }
}