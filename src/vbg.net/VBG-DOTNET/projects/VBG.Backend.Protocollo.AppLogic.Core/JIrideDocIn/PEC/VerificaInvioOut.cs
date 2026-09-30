using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.PEC
{
    [XmlRoot(ElementName = "messaggioOut")]
    public class VerificaInvioOut
    {
        [XmlElement(ElementName = "inviato")]
        public string Inviato { get; set; }

        [XmlElement(ElementName = "numAccettazioni")]
        public int NumAccettazioni { get; set; }

        [XmlElement("accettazioni", IsNullable = false)]
        public Accettazioni AccettazioniItem { get; set; }

        [XmlElement(ElementName = "numConsegne")]
        public int NumConsegne { get; set; }

        [XmlElement("consegne", IsNullable = false)]
        public Consegne ConsegneItem { get; set; }

        [XmlElement(ElementName = "codice")]
        public string Codice { get; set; }

        [XmlElement(ElementName = "descrizione")]
        public string Descrizione { get; set; }
    }

    public class Accettazioni
    {
        [XmlArrayItem(ElementName = "idRepAccettazione")]
        public string[] IdRepAccettazione { get; set; }

        [XmlElement(ElementName = "datiAccettazioni")]
        public DatiAccettazioni DatiAccettazioniItem { get; set; }
    }

    public class Consegne
    {
        [XmlElement(ElementName = "idRepConsegna")]
        public string[] IdRepConsegna { get; set; }

        [XmlElement(ElementName = "datiConsegne")]
        public DatiConsegne DatiConsegneItem { get; set; }
    }

    public class DatiConsegne
    {
        [XmlElement(ElementName = "consegna")]
        public Consegna[] ConsegneItem { get; set; }
    }

    public class Consegna
    {
        [XmlElement(ElementName = "idRepConsegna")]
        public string IdRepConsegna { get; set; }

        [XmlElement(ElementName = "dataConsegna")]
        public string DataConsegna { get; set; }

        [XmlElement(ElementName = "emailDestinatario")]
        public string EmailDestinatario { get; set; }
    }

    public class DatiAccettazioni
    {
        [XmlElement(ElementName = "accettazione")]
        public Accettazione[] AccettazioneItem { get; set; }
    }

    public class Accettazione
    {
        [XmlElement(ElementName = "idRepAccettazione")]
        public string IdRepAccettazione { get; set; }

        [XmlElement(ElementName = "dataAccettazione")]
        public string DataAccettazione { get; set; }

        [XmlElement(ElementName = "emailDestinatario")]
        public string EmailDestinatario { get; set; }
    }
}

