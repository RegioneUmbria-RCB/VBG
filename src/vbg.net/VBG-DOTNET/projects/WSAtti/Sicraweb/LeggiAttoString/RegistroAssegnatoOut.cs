using System;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    [XmlRoot("Registro")]
    public class RegistroAssegnatoOut
    {
        [XmlElement("TipoRegistro")]
        public string TipoRegistro { get; set; }

        [XmlElement("AnnoRegistro")]
        public int AnnoRegistro { get; set; }

        [XmlElement("NumeroRegistro")]
        public int NumeroRegistro { get; set; }

        [XmlElement("DataRegistro")]
        public DateTime? DataRegistro { get; set; }
    }
}
