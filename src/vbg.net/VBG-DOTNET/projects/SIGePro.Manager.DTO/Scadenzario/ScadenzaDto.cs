using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class ScadenzaDto
    {
        [DataMember]
        public string CodEnte { get; set; }

        [DataMember]
        public string CodSportello { get; set; }

        [DataMember]
        public string CodiceIstanza { get; set; }


        [DataMember]
        public string NumeroProtocollo { get; set; }

        [DataMember]
        public string DataProtocollo { get; set; }

        [DataMember]
        public string NumeroIstanza { get; set; }

        [DataMember]
        public string CodStatoIstanza { get; set; }

        [DataMember]
        public string DescrStatoIstanza { get; set; }

        [DataMember]
        public string CodMovimento { get; set; }

        [DataMember]
        public string DatiMovimento { get; set; }

        [DataMember]
        public string CodMovimentoDaFare { get; set; }

        [DataMember]
        public string DescrMovimentoDaFare { get; set; }

        [DataMember]
        public string DataScadenza { get; set; }

        [DataMember]
        public string Responsabile { get; set; }

        [DataMember]
        public string Procedimento { get; set; }

        [DataMember]
        public string Procedura { get; set; }

        [DataMember]
        public string ModuloSoftware { get; set; }

        [DataMember]
        public string AmmAmministrazione { get; set; }

        [DataMember]
        public string AmmPartitaiva { get; set; }

        [DataMember]
        public string DatiRichiedente { get; set; }

        [DataMember]
        public string DatiTecnico { get; set; }

        [DataMember]
        public string DatiAzienda { get; set; }

        [DataMember]
        public string CodiceScadenza { get; set; }

        [DataMember]
        public string CodiceAmministrazione { get; set; }

        [DataMember]
        public string CodiceInventario { get; set; }

        [DataMember]
        public string UuidIstanza { get; set; }
    }
}
