using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Name = "ProtocollazioneMovimentoXmlRequestType", Namespace = "http://it.gruppoinit/Protocollazione")]
    public class ProtocollazioneMovimentoXmlRequestType
    {
        [XmlElement("CodiceMovimento")]
        [DataMember(Order = 1)]
        public string CodiceMovimento { get; set; }

        [XmlElement("Dati")]
        [DataMember(Order = 2)]
        public DatiRequestType Dati { get; set; }

        [XmlElement("Metadati")]
        [DataMember(Order = 3)]
        public MetadatoType[] Metadati { get; set; }

        [XmlElement("Source")]
        [DataMember(Order = 4)]
        public int Source { get; set; }

        [XmlElement("Token")]
        [DataMember(Order = 5)]
        public string Token { get; set; }


    }
}
