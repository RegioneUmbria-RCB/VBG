using System;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    [XmlRoot("AttoOut")]
    public class AttoOut
    {
        [XmlElement("IdDocumento")]
        public int IdDocumento { get; set; }

        [XmlElement("TipoDocumento")]
        public string TipoDocumento { get; set; }

        [XmlElement("TipoDocumento_Descrizione")]
        public string TipoDocumento_Descrizione { get; set; }

        [XmlElement("Oggetto")]
        public string Oggetto { get; set; }

        [XmlElement("Classifica")]
        public string Classifica { get; set; }

        [XmlElement("Classifica_Descrizione")]
        public string Classifica_Descrizione { get; set; }

        [XmlElement("DataDocumento")]
        public DateTime? DataDocumentoField { get; set; }

        [XmlElement("Conservabile")]
        public string Conservabile { get; set; }

        [XmlElement("Determina")]
        public DeterminaOut Determina { get; set; }

        [XmlElement("Allegati")]
        public ArrayOfAllegato Allegati { get; set; }

        [XmlElement("Registri")]
        public ArrayOfRegistroAssegnatoOut Registri { get; set; }

        [XmlElement("Messaggio")]
        public string Messaggio { get; set; }

        [XmlElement("Errore")]
        public string Errore { get; set; }
    }
}
