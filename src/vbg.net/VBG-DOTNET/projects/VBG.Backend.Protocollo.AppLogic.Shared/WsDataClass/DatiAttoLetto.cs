using System;
using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiAttoLettoResponseType")]
    public class DatiAttoLetto
    {

        /// <remarks/>
        [DataMember(Order = 0)]
        public string Warning { get; set; }

        /// <summary>
        /// Id dell'atto assegnato dal sistema di protocollazione
        /// </summary>
        [DataMember(Order = 1)]
        public string IdAtto { get; set; }

        /// <summary>
        /// Anno dell'atto
        /// </summary>
        [DataMember(Order = 2)]
        public int? AnnoAtto { get; set; }

        /// <summary>
        /// Numero dell'atto
        /// </summary>
        [DataMember(Order = 3)]
        public string NumeroAtto { get; set; }

        /// <summary>
        /// Data dell'atto
        /// </summary>
        [DataMember(Order = 4)]
        public DateTime? DataAtto { get; set; }

        /// <summary>
        /// Oggetto descrittivo dell'atto
        /// </summary>
        [DataMember(Order = 5)]
        public string Oggetto { get; set; }

        /// <remarks/>
        [DataMember(Order = 6)]
        public string NumeroFascicolo { get; set; }

        /// <remarks/>
        [DataMember(Order = 7)]
        public string AnnoFascicolo { get; set; }

        [DataMember(Order = 8)]
        public AllegatoResponseType[] Allegati { get; set; }
    }
}
