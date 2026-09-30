using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloFascicolatoResponseType")]
    public class DatiProtocolloFascicolatoResponseType
    {
        public DatiProtocolloFascicolatoResponseType(Exception ex)
        {
            Errore = new ErroreProtocolloType { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloFascicolatoResponseType(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocolloType { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloFascicolatoResponseType()
        {

        }

        EnumFascicolatoType _fascicolato = EnumFascicolatoType.nondefinito;

        [DataMember(Order = 0)]
        public EnumFascicolatoType Fascicolato
        {
            get { return _fascicolato; }
            set { _fascicolato = value; }
        }

        [DataMember(Order = 1)]
        public string NumeroFascicolo { get; set; }

        [DataMember(Order = 2)]
        public string DataFascicolo { get; set; }

        [DataMember(Order = 3)]
        public string AnnoFascicolo { get; set; }

        [DataMember(Order = 4)]
        public string NoteFascicolo { get; set; }

        [DataMember(Order = 5)]
        public string Classifica { get; set; }

        [DataMember(Order = 6)]
        public string Oggetto { get; set; }

        [DataMember(Order = 7)]
        public ErroreProtocolloType Errore { get; set; }


    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumFascicolatoType")]
    public enum EnumFascicolatoType
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
