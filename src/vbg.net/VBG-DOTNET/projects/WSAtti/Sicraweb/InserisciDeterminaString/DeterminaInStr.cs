using System.Xml.Serialization;

namespace WSAtti.Sicraweb.InserisciDeterminaString
{
    [XmlRoot("DeterminaIn")]
    public class DeterminaInStr
    {
        public string Note { get; set; }
        public string Oggetto { get; set; }
        public string Trattamento { get; set; }
        public string Proponente { get; set; }
        public string Dirigente { get; set; }
        public string DataDocumento { get; set; }
        public string Classifica { get; set; }
        public string DaPubblicare { get; set; }
        public string Utente { get; set; }
        public string Ruolo { get; set; }
    }
}
