using System.Collections.Generic;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb
{
    [XmlRoot(ElementName = "Allegati")]
    public class ArrayOfAllegato
    {
        [XmlElement("Allegato")]
        public List<Allegato> Allegati { get; set; }
    }
}
