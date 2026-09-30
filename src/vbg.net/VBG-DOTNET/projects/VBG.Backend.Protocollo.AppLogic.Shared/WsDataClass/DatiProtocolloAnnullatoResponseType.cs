using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloAnnullatoResponseType")]
    public class DatiProtocolloAnnullatoResponseType
    {
        public DatiProtocolloAnnullatoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloAnnullatoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloAnnullatoResponseType()
        {

        }

        EnumAnnullatoType _annullato = EnumAnnullatoType.nondefinito;

        [DataMember(Order = 0)]
        public EnumAnnullatoType Annullato
        {
            get { return _annullato; }
            set { _annullato = value; }
        }

        [DataMember(Order = 1)]
        public string MotivoAnnullamento { get; set; }

        [DataMember(Order = 2)]
        public string NoteAnnullamento { get; set; }

        /// <remarks/>
        [DataMember(Order = 3)]
        public ErroreProtocolloType Errore { get; set; }
    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumAnnullatoType")]
    public enum EnumAnnullatoType
    {
        [EnumMember()]
        si,
        [EnumMember()]
        no,
        [EnumMember()]
        nondefinito,
        [EnumMember()]
        warning
    };
}
