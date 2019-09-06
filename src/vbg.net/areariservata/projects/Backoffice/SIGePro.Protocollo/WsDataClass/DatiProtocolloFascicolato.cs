using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;
using System.Runtime.Serialization;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiProtocolloFascicolatoResponseType")]
    public class DatiProtocolloFascicolato
    {
        public DatiProtocolloFascicolato(Exception ex)
        {
            Errore = new ErroreProtocollo { Descrizione = ex.Message, StackTrace = ex.ToString() };
        }

        public DatiProtocolloFascicolato(string messaggio, string stackTrace)
        {
            Errore = new ErroreProtocollo { Descrizione = messaggio, StackTrace = stackTrace };
        }

        public DatiProtocolloFascicolato()
        {

        }

        EnumFascicolato _fascicolato = EnumFascicolato.nondefinito;

        [DataMember(Order = 0)]
        public EnumFascicolato Fascicolato
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
        public ErroreProtocollo Errore { get; set; }


    }

    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "EnumFascicolatoType")]
    public enum EnumFascicolato
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
