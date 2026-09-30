using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EseguiAccettazioneResponseType")]
    public class EseguiAccettazioneResponseType
    {
        public EseguiAccettazioneResponseType(Exception ex)
        {
            ErroreProtocollo = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public EseguiAccettazioneResponseType(string messaggio, Exception ex)
        {
            ErroreProtocollo = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = ex.ToString() };
        }

        public EseguiAccettazioneResponseType()
        {

        }

        EnumStatusType _status = EnumStatusType.KO;

        [DataMember(Order = 0)]
        public EnumStatusType Status
        {
            get { return _status; }
            set { _status = value; }
        }

        [DataMember(Order = 1)]
        public ErroreProtocolloType ErroreProtocollo { get; set; }
    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumStatusType")]
    public enum EnumStatusType
    {
        [EnumMember()]
        OK,
        [EnumMember()]
        KO
    };
}
