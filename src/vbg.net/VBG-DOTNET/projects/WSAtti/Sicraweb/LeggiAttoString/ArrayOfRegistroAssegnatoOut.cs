using System.Collections.Generic;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    [XmlRoot(ElementName = "Registri")]
    public class ArrayOfRegistroAssegnatoOut
    {
        [XmlElement("Registro")]
        public List<RegistroAssegnatoOut> Registri { get; set; }

    }
}
