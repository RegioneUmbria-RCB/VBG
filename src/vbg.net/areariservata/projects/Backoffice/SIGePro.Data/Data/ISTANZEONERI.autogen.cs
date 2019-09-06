using System;
using System.Data;
using System.Xml.Serialization;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEONERI")]
    [Serializable]
    public partial class IstanzeOneri : BaseDataClass
    {

        #region Key Fields

        string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [XmlElement(Order = 2)]
        public string ID
        {
            get { return id; }
            set { id = value; }
        }

        #endregion

        string codiceinventario = null;
        [DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [XmlElement(Order = 3)]
        public string CODICEINVENTARIO
        {
            get { return codiceinventario; }
            set { codiceinventario = value; }
        }

        string codiceistanza = null;
        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [XmlElement(Order = 4)]
        public string CODICEISTANZA
        {
            get { return codiceistanza; }
            set { codiceistanza = value; }
        }

        double? prezzo = null;
        [DataField("PREZZO", Type = DbType.Decimal)]
        [XmlElement(Order = 5)]
        public double? PREZZO
        {
            get { return prezzo; }
            set { prezzo = value; }
        }

        string flentratauscita = null;
        [isRequired]
        [DataField("FLENTRATAUSCITA", Type = DbType.Decimal)]
        [XmlElement(Order = 6)]
        public string FLENTRATAUSCITA
        {
            get { return flentratauscita; }
            set { flentratauscita = value; }
        }

        DateTime? data = null;
        [isRequired]
        [DataField("DATA", Type = DbType.DateTime)]
        [XmlElement(Order = 7)]
        public DateTime? DATA
        {
            get { return data; }
            set { data = VerificaDataLocale(value); }
        }

        string note = null;
        [DataField("NOTE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 8)]
        public string NOTE
        {
            get { return note; }
            set { note = value; }
        }

        string codiceutente = null;
        [DataField("CODICEUTENTE", Type = DbType.Decimal)]
        [XmlElement(Order = 9)]
        public string CODICEUTENTE
        {
            get { return codiceutente; }
            set { codiceutente = value; }
        }

        string flribasso = null;
        [isRequired]
        [DataField("FLRIBASSO", Type = DbType.Decimal)]
        [XmlElement(Order = 10)]
        public string FLRIBASSO
        {
            get { return flribasso; }
            set { flribasso = value; }
        }

        string percribasso = null;
        [DataField("PERCRIBASSO", Type = DbType.Decimal)]
        [XmlElement(Order = 11)]
        public string PERCRIBASSO
        {
            get { return percribasso; }
            set { percribasso = value; }
        }

        DateTime? datapagamento = null;
        [DataField("DATAPAGAMENTO", Type = DbType.DateTime)]
        [XmlElement(Order = 12)]
        public DateTime? DATAPAGAMENTO
        {
            get { return datapagamento; }
            set { datapagamento = VerificaDataLocale(value); }
        }

        double? prezzoistruttoria = null;
        [DataField("PREZZOISTRUTTORIA", Type = DbType.Decimal)]
        [XmlElement(Order = 13)]
        public double? PREZZOISTRUTTORIA
        {
            get { return prezzoistruttoria; }
            set { prezzoistruttoria = value; }
        }

        string docriferimento = null;
        [DataField("DOCRIFERIMENTO", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 14)]
        public string DOCRIFERIMENTO
        {
            get { return docriferimento; }
            set { docriferimento = value; }
        }

        string fkidtipocausale = null;
        [isRequired]
        [DataField("FKIDTIPOCAUSALE", Type = DbType.Decimal)]
        [XmlElement(Order = 15)]
        public string FKIDTIPOCAUSALE
        {
            get { return fkidtipocausale; }
            set { fkidtipocausale = value; }
        }

        DateTime? datascadenza = null;
        [DataField("DATASCADENZA", Type = DbType.DateTime)]
        [XmlElement(Order = 16)]
        public DateTime? DATASCADENZA
        {
            get { return datascadenza; }
            set { datascadenza = VerificaDataLocale(value); }
        }

        string fkmodalitapagamento = null;
        [DataField("FKMODALITAPAGAMENTO", Type = DbType.Decimal)]
        [XmlElement(Order = 17)]
        public string FKMODALITAPAGAMENTO
        {
            get { return fkmodalitapagamento; }
            set { fkmodalitapagamento = value; }
        }

        string tipomovimento = null;
        [DataField("TIPOMOVIMENTO", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 18)]
        public string TIPOMOVIMENTO
        {
            get { return tipomovimento; }
            set { tipomovimento = value; }
        }

        string codiceamministrazione = null;
        [DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        [XmlElement(Order = 19)]
        public string CODICEAMMINISTRAZIONE
        {
            get { return codiceamministrazione; }
            set { codiceamministrazione = value; }
        }

        string numerorata = null;
        [DataField("NUMERORATA", Type = DbType.Decimal)]
        [XmlElement(Order = 20)]
        public string NUMERORATA
        {
            get { return numerorata; }
            set { numerorata = value; }
        }

        string nr_documento = null;
        [DataField("NR_DOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 21)]
        public string NR_DOCUMENTO
        {
            get { return nr_documento; }
            set { nr_documento = value; }
        }

        [DataField("IMPORTOPAGATO", Type = DbType.Decimal)]
        [XmlElement(Order = 22)]
        public int? ImportoPagato
        {
            get;
            set;
        }

        [DataField("FLAG_ONERE_RATEIZZATO", Type = DbType.Decimal)]
        [XmlElement(Order = 23)]
        public string FlagOnereRateizzato
        {
            get;
            set;
        }

        [DataField("IMPORTO_INTERESSE", Type = DbType.Decimal)]
        [XmlElement(Order = 24)]
        public decimal? ImportoInteressi
        {
            get;
            set;
        }

        #region Foreign keys
        TipiCausaliOneri m_causaleOnere;
        [ForeignKey("IDCOMUNE,FKIDTIPOCAUSALE", "Idcomune,CoId")]
        [XmlElement(Order = 25)]
        public TipiCausaliOneri CausaleOnere
        {
            get { return m_causaleOnere; }
            set { m_causaleOnere = value; }
        }

        #endregion
    }
}