using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloEsitatoResponseType")]
    public class DatiProtocolloEsitatoResponseType
    {
        public DatiProtocolloEsitatoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloEsitatoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloEsitatoResponseType()
        {

        }

        EnumEsitatoType _esitato = EnumEsitatoType.warning;

        [DataMember(Order = 0)]
        public EnumEsitatoType Esitato
        {
            get { return _esitato; }
            set { _esitato = value; }
        }

        /// <remarks/>
        [DataMember(Order = 1)]
        public ErroreProtocolloType Errore { get; set; }
    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumEsitatoType")]
    public enum EnumEsitatoType
    {
        [EnumMember()]
        si,
        [EnumMember()]
        no,
        [EnumMember()]
        warning
    };
}
