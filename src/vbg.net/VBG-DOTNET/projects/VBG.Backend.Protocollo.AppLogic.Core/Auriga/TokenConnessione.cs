using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{

    public class TokenConnessione
    {
        [XmlElement("TokenConnessione")]
        public TokenConnessioneElement Token { get; set; }
    }

    public class TokenConnessioneElement
    {
        [XmlAttribute]
        public string DesUser { get; set; }

        [XmlAttribute]
        public string IdDominio { get; set; }

        [XmlText]
        public string Valore { get; set; }
    }
}
