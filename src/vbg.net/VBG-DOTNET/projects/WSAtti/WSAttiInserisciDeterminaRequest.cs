using System;

namespace WSAtti
{
    public class WSAttiInserisciDeterminaRequest
    {
        public string Oggetto { get; set; }
        public string Trattamento { get; set; }
        public string Proponente { get; set; }
        public string Dirigente { get; set; }
        public DateTime DataDocumento { get; set; }
        public string Classifica { get; set; }
        public bool Pubblicare { get; set; }
        public string Note { get; set; }
        public string CodiceAOO { get; internal set; }
        public string CodiceAmministrazione { get; set; }
        public string Utente { get; set; }
        public string Ruolo { get; set; }
    }
}
