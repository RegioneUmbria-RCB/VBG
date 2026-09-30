using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEONERI")]
    [Serializable]
    [DataContract]
    public partial class IstanzeOneri : BaseDataClass
    {

        #region Key Fields

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        private string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string ID
        {
            get { return this.id; }
            set { this.id = value; }
        }

        #endregion

        private string codiceinventario = null;
        [DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CODICEINVENTARIO
        {
            get { return this.codiceinventario; }
            set { this.codiceinventario = value; }
        }

        private string codiceistanza = null;
        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private double? prezzo = null;
        [DataField("PREZZO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public double? PREZZO
        {
            get { return this.prezzo; }
            set { this.prezzo = value; }
        }

        private string flentratauscita = null;
        [isRequired]
        [DataField("FLENTRATAUSCITA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string FLENTRATAUSCITA
        {
            get { return this.flentratauscita; }
            set { this.flentratauscita = value; }
        }

        private DateTime? data = null;
        [isRequired]
        [DataField("DATA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 7)]
        public DateTime? DATA
        {
            get { return this.data; }
            set { this.data = this.VerificaDataLocale(value); }
        }

        private string note = null;
        [DataField("NOTE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string NOTE
        {
            get { return this.note; }
            set { this.note = value; }
        }

        private string codiceutente = null;
        [DataField("CODICEUTENTE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string CODICEUTENTE
        {
            get { return this.codiceutente; }
            set { this.codiceutente = value; }
        }

        private string flribasso = null;
        [isRequired]
        [DataField("FLRIBASSO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string FLRIBASSO
        {
            get { return this.flribasso; }
            set { this.flribasso = value; }
        }

        private string percribasso = null;
        [DataField("PERCRIBASSO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 11)]
        public string PERCRIBASSO
        {
            get { return this.percribasso; }
            set { this.percribasso = value; }
        }

        private DateTime? datapagamento = null;
        [DataField("DATAPAGAMENTO", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 12)]
        public DateTime? DATAPAGAMENTO
        {
            get { return this.datapagamento; }
            set { this.datapagamento = this.VerificaDataLocale(value); }
        }

        private double? prezzoistruttoria = null;
        [DataField("PREZZOISTRUTTORIA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 13)]
        public double? PREZZOISTRUTTORIA
        {
            get { return this.prezzoistruttoria; }
            set { this.prezzoistruttoria = value; }
        }

        private string docriferimento = null;
        [DataField("DOCRIFERIMENTO", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string DOCRIFERIMENTO
        {
            get { return this.docriferimento; }
            set { this.docriferimento = value; }
        }

        private string fkidtipocausale = null;
        [isRequired]
        [DataField("FKIDTIPOCAUSALE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string FKIDTIPOCAUSALE
        {
            get { return this.fkidtipocausale; }
            set { this.fkidtipocausale = value; }
        }

        private DateTime? datascadenza = null;
        [DataField("DATASCADENZA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 16)]
        public DateTime? DATASCADENZA
        {
            get { return this.datascadenza; }
            set { this.datascadenza = this.VerificaDataLocale(value); }
        }

        private string fkmodalitapagamento = null;
        [DataField("FKMODALITAPAGAMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 17)]
        public string FKMODALITAPAGAMENTO
        {
            get { return this.fkmodalitapagamento; }
            set { this.fkmodalitapagamento = value; }
        }

        private string tipomovimento = null;
        [DataField("TIPOMOVIMENTO", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 18)]
        public string TIPOMOVIMENTO
        {
            get { return this.tipomovimento; }
            set { this.tipomovimento = value; }
        }

        private string codiceamministrazione = null;
        [DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 19)]
        public string CODICEAMMINISTRAZIONE
        {
            get { return this.codiceamministrazione; }
            set { this.codiceamministrazione = value; }
        }

        private string numerorata = null;
        [DataField("NUMERORATA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 20)]
        public string NUMERORATA
        {
            get { return this.numerorata; }
            set { this.numerorata = value; }
        }

        private string nr_documento = null;
        [DataField("NR_DOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 21)]
        public string NR_DOCUMENTO
        {
            get { return this.nr_documento; }
            set { this.nr_documento = value; }
        }

        [DataField("IMPORTOPAGATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 22)]
        public double? ImportoPagato
        {
            get;
            set;
        }

        [DataField("FLAG_ONERE_RATEIZZATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 23)]
        public string FlagOnereRateizzato
        {
            get;
            set;
        }

        [DataField("IMPORTO_INTERESSE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 24)]
        public decimal? ImportoInteressi
        {
            get;
            set;
        }

        #region Foreign keys
        private TipiCausaliOneri m_causaleOnere;
        [ForeignKey("IDCOMUNE,FKIDTIPOCAUSALE", "Idcomune,CoId")]
        [DataMember]
        [XmlElement(Order = 25)]
        public TipiCausaliOneri CausaleOnere
        {
            get { return this.m_causaleOnere; }
            set { this.m_causaleOnere = value; }
        }

        [DataField("FLAG_NONDOVUTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 26)]
        public int? FlagNonDovuto
        {
            get;
            set;
        }

        [ForeignKey("IDCOMUNE,FKMODALITAPAGAMENTO", "IDCOMUNE,MP_ID")]
        [DataMember]
        [XmlElement(Order = 27)]
        public TipiModalitaPagamento ModalitaPagamento
        {
            get;
            set;
        }

        #endregion


    }
}