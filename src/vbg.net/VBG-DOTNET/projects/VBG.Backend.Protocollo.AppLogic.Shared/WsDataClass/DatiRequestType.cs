using System.Runtime.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass
{
    [DataContract(Namespace = "http://it.gruppoinit/Protocollazione", Name = "DatiRequestType")]
    public class DatiRequestType
    {
        [DataMember(Order = 0)]
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
        public DatiMittentiXmlType Mittenti;

        [DataMember(Order = 8)]
        public DatiDestinatariXmlType Destinatari;

        [DataMember(Order = 9)]
        public AllegatoType[] Allegati;

        [DataMember(Order = 10)]
        public MetadatoType[] Metadati;

        [DataMember(Order = 11)]
        public DatiMailType Mail;
    }
}
