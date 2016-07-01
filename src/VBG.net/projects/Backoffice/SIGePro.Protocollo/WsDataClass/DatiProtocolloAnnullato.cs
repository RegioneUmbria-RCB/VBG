using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloAnnullatoResponseType")]
    public class DatiProtocolloAnnullato
    {
        public DatiProtocolloAnnullato(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloAnnullato(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloAnnullato()
        {

        }

        EnumAnnullato _annullato = EnumAnnullato.nondefinito;

        [DataMember(Order = 0)]
        public EnumAnnullato Annullato
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
        public ErroreProtocollo Errore { get; set; }
    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumAnnullatoType")]
    public enum EnumAnnullato
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
