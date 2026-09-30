using System.Collections.Generic;
using System.Xml;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.AggiungiAllegatiString
{
    [XmlRoot("NuoviAllegati")]
    public class NuoviAllegatiStr
    {
        [XmlElement("idDoc")]
        public string IdDoc { get; set; }

        [XmlElement("annoProt")]
        public string AnnoProt { get; set; }

        [XmlElement("numProt")]
        public string NumProt { get; set; }

        [XmlElement("utente")]
        public string Utente { get; set; }

        [XmlElement("ruolo")]
        public string Ruolo { get; set; }

        [XmlArray("Allegati")]
        public List<Allegato> Allegati { get; set; }
    }
}