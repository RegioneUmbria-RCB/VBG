using Init.SIGePro.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
{
    [DataContract]
    public class ProtocolloAmministrazioni
    {
        [XmlElement(Order = 1), DataMember(Order = 1)]
        public string CODICEAMMINISTRAZIONE { get; set; }

        [XmlElement(Order = 2), DataMember(Order = 2)]
        public string Mezzo { get; set; }

        [XmlElement(Order = 3), DataMember(Order = 3)]
        public string ModalitaTrasmissione { get; set; }

        [XmlElement(Order = 4), DataMember(Order = 4)]
        public string PEC { get; set; }

        [XmlElement(Order = 5), DataMember(Order = 5)]
        public string? PARTITAIVA { get; set; }

        [XmlElement(Order = 6), DataMember(Order = 6)]
        public string? AMMINISTRAZIONE { get; set; }

        [XmlElement(Order = 7), DataMember(Order = 7)]
        public string? INDIRIZZO { get; set; }

        [XmlElement(Order = 8), DataMember(Order = 8)]
        public string? EMAIL { get; set; }

        [XmlElement(Order = 9), DataMember(Order = 9)]
        public ProtocolloComune ComuneResidenza { get; set; }

        [XmlElement(Order = 10), DataMember(Order = 10)]
        public string CITTA { get; set; }

        [XmlElement(Order = 11), DataMember(Order = 11)]
        public string PROVINCIA { get; set; }

        [XmlElement(Order = 12), DataMember(Order = 12)]
        public string CAP { get; set; }

        [XmlElement(Order = 13), DataMember(Order = 13)]
        public string TELEFONO1 { get; set; }

        [XmlElement(Order = 14), DataMember(Order = 14)]
        public string FAX { get; set; }

        [XmlElement(Order = 15), DataMember(Order = 15)]
        public string PROT_UO { get; set; }

        [XmlElement(Order = 16), DataMember(Order = 16)]
        public string PROT_RUOLO { get; set; }

        [XmlIgnore, IgnoreDataMember]
        public bool HaUnitaOrganizzativaORuoloSettati => !String.IsNullOrEmpty(this.PROT_UO) || !String.IsNullOrEmpty(this.PROT_RUOLO);

        [XmlElement(Order = 17), DataMember(Order = 17)]
        public string TELEFONO2 { get; set; }
        [XmlElement(Order = 18), DataMember(Order = 18)]
        public string? UFFICIO { get; set; }

        [XmlElement(Order = 19), DataMember(Order = 19)]
        public string CodiceIPA { get; set; }

        [XmlElement(Order = 20), DataMember(Order = 20)]
        public string STC_IDSPORTELLO { get; set; }

        public ProtocolloAmministrazioni()
        {

        }

        private ProtocolloAmministrazioni(Amministrazioni amm)
        {
            this.CODICEAMMINISTRAZIONE = amm.CODICEAMMINISTRAZIONE;
            this.Mezzo = amm.Mezzo;
            this.ModalitaTrasmissione = amm.ModalitaTrasmissione;
            this.PEC = amm.PEC;
            this.PARTITAIVA = amm.PARTITAIVA;
            this.AMMINISTRAZIONE = amm.AMMINISTRAZIONE;
            this.INDIRIZZO = amm.INDIRIZZO;
            this.EMAIL = amm.EMAIL;
            this.ComuneResidenza = ProtocolloComune.FromComuni(amm.ComuneResidenza);
            this.CITTA = amm.CITTA;
            this.PROVINCIA = amm.PROVINCIA;
            this.CAP = amm.CAP;
            this.TELEFONO1 = amm.TELEFONO1;
            this.TELEFONO2 = amm.TELEFONO2;
            this.FAX = amm.FAX;
            this.PROT_UO = amm.PROT_UO;
            this.PROT_RUOLO = amm.PROT_RUOLO;
            this.UFFICIO = amm.UFFICIO;
            this.CodiceIPA = amm.CodiceIPA;
            this.STC_IDSPORTELLO = amm.STC_IDSPORTELLO;
        }

        public static ProtocolloAmministrazioni FromAmministrazione(Amministrazioni amm)
        {
            return new ProtocolloAmministrazioni(amm);
        }
    }
}
