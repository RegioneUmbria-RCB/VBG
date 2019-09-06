using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    [XmlRoot(Namespace = "", ElementName = "postacert", IsNullable = false)]
    public class PostacertXML
    {
        [XmlAttribute("tipo")]
        public string Tipo { get; set; }

        [XmlAttribute("errore")]
        public string Errore { get; set; }

        [XmlElement("intestazione")]
        public IntestazionePostacertXML Intestazione { get; set; }

        [XmlElement("dati")]
        public DatiPostacertXML Dati { get; set; }
    }

    public class IntestazionePostacertXML
    {
        [XmlElement("mittente")]
        public string Mittente { get; set; }

        [XmlElement("destinatari")]
        public DestinatariPostacertXML Destinatari { get; set; }

        [XmlElement("risposte")]
        public string Risposte { get; set; }

        [XmlElement("oggetto")]
        public string Oggetto { get; set; }
    }

    public class DestinatariPostacertXML
    {
        [XmlAttribute("tipo")]
        public string Tipo { get; set; }

        [XmlText]
        public string Text { get; set; }
    }

    public class DatiPostacertXML
    {
        [XmlElement("gestore-emittente")]
        public string GestoreEmittente { get; set; }

        [XmlElement("data")]
        public DataPostacertXML Data { get; set; }

        [XmlElement("identificativo")]
        public string Identificativo { get; set; }

        [XmlElement("msgid")]
        public string MessageId { get; set; }

        [XmlElement("ricevuta")]
        public RicevutaPostacertXML Ricevuta { get; set; }

        [XmlElement("consegna")]
        public string Consegna { get; set; }
    }

    public class DataPostacertXML
    {
        [XmlAttribute("zona")]
        public string Zona { get; set; }

        [XmlAttribute("giorno")]
        public string Giorno { get; set; }

        [XmlAttribute("ora")]
        public string Ora { get; set; }
    }

    public class RicevutaPostacertXML
    {
        [XmlAttribute("tipo")]
        public string Tipo { get; set; }

        [XmlText]
        public string Text { get; set; }
    }
}
