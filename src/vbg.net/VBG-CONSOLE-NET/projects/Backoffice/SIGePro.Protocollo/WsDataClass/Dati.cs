using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Init.SIGePro.Protocollo.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name="DatiRequestType")]
    public class Dati
    {
        [DataMember(Order=0)]
        public string TipoDocumento { get; set; }

        [DataMember(Order = 1)]
        public string TipoSmistamento { get; set; }

        [DataMember(Order = 2)]
        public string Oggetto { get; set; }

        [DataMember(Order = 3)]
        public string Flusso { get; set; }

        [DataMember(Order = 4)]
        public string Classifica { get; set; }

        [DataMember(Order = 5)]
        public string NumProtMitt { get; set; }

        [DataMember(Order = 6)]
        public string DataProtMitt { get; set; }

        [DataMember(Order = 7)]
        public DatiMittenti Mittenti = null;

        [DataMember(Order = 8)]
        public DatiDestinatari Destinatari = null;

        [DataMember(Order = 9)]
        public Allegato[] Allegati = null;
    }    
}
