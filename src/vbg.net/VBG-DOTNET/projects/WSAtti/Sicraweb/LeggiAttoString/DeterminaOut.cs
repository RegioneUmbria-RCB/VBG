using System;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    public class DeterminaOut
    {
        [XmlElement("Trattamento")]
        public string Trattamento { get; set; }

        [XmlElement("Trattamento_Descrizione")]
        public string Trattamento_Descrizione { get; set; }

        [XmlElement("Proponente")]
        public string Proponente { get; set; }

        [XmlElement("Proponente_Descrizione")]
        public string Proponente_Descrizione { get; set; }

        [XmlElement("Dirigente")]
        public string Dirigente { get; set; }

        [XmlElement("Dirigente_Descrizione")]
        public string Dirigente_Descrizione { get; set; }

        [XmlElement("Data")]
        public DateTime? Data { get; set; }

        [XmlElement("Anno")]
        public int Anno { get; set; }

        [XmlElement("Numero")]
        public int Numero { get; set; }

        [XmlElement("DataEsecutivita")]
        public DateTime? DataEsecutivita { get; set; }

        [XmlElement("DataPubblicazione")]
        public DateTime? DataPubblicazione { get; set; }

        [XmlElement("GiorniPubblicazione")]
        public int GiorniPubblicazione { get; set; }

    }
}
