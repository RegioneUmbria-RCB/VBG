using System.Collections.Generic;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.AggiungiAllegatiString
{
    [XmlRoot("AllegatiInseritiOut")]
    public class AllegatiInseritiResponse
    {
        [XmlElement("IdDocumento")]
        public int IdDocumento { get; set; }

        [XmlArray("Allegati")]
        public List<Allegato> Allegati { get; set; }

        [XmlElement("Messaggio")]
        public string Messaggio { get; set; }

        [XmlElement("Errore")]
        public string Errore { get; set; }
    }
}
