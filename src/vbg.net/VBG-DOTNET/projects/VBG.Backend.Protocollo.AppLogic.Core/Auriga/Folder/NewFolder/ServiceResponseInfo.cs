using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.NewFolder
{
    [XmlRoot(Namespace = "", IsNullable = false, ElementName = "IdFolder")]
    public class ServiceResponseInfo
    {
        [XmlText]
        public string IdFolder { get; set; }
    }
}
