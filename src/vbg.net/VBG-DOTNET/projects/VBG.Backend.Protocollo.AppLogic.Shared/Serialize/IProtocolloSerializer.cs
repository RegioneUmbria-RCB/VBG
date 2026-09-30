using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Serialize
{
    public interface IProtocolloSerializer
    {
        T Deserialize<T>(string xml);
        T Deserialize<T>(byte[] buffer);
        object Deserialize(string xmlString, Type type);
        MemoryStream SerializeToStream<T>(T dataObject);
        string Serialize(string sFileName, object pProtocollo);
        string Serialize(string sFileName, object pProtocollo, string messaggio);
        string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione tipoValidazione = ProtocolloValidation.TipiValidazione.XSD);
        string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione eTipoValidazione, string sTipoValidazione, bool bValidazione);
        void SerializeAndValidateStream(object pProtocollo, string sFileName);
        void LogAndValidate(
            string fileName,
            object objectToSerialize,
            string messaggio = null,
            ProtocolloValidation.TipiValidazione tipoValidazione = ProtocolloValidation.TipiValidazione.XSD,
            string sTipoValidazione = "",
            bool validazione = false,
            string encoding = "");
        XmlAttributeOverrides GetOverrides();
    }
}
