using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Creazione
{

    [XmlRoot(ElementName = "insDocInProtocollo_Result")]
    public class InsDocInProtocolloResult
    {

        [XmlElement(ElementName = "RESULT")]
        public string Result { get; set; }

        [XmlElement(ElementName = "MESSAGE")]
        public string Message { get; set; }

        [XmlElement(ElementName = "ERRORCODE")]
        public string ErrorCode { get; set; }
    }

    [XmlRoot(ElementName = "xapirest")]
    public class WSFascicolazioneResponse
    {

        [XmlElement(ElementName = "insDocInProtocollo_Result")]
        public InsDocInProtocolloResult InsDocInProtocolloResult { get; set; }
    }

}
