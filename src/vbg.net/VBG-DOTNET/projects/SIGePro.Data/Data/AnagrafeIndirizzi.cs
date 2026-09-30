using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Xml.Serialization;

namespace SIGePro.Data.Data
{
    [DataTable("ANAGRAFE_INDIRIZZI")]
    [Serializable]
    public class AnagrafeIndirizzi : BaseDataClass
    {
        #region Key Fields

        [XmlElement(Order = 0)]
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal, KeyIdentity = true)]
        public int? Id { get; set; }

        [XmlElement(Order = 1)]
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IdComune { get; set; }

        #endregion

        protected Anagrafe anagrafeclass = null;
        [ForeignKey("IdComune,CodiceAnagrafe", "IDCOMUNE,CODICEANAGRAFE")]
        [XmlElement(Order = 2)]
        public Anagrafe AnagrafeClass
        {
            get { return this.anagrafeclass; }
            set { this.anagrafeclass = value; }
        }

        [DataField("CODICEANAGRAFE", Type = DbType.Int32)]
        [XmlElement(Order = 3)]
        public int? CodiceAnagrafe { get; set; }

        [DataField("IDENTIFICATIVO_SEDE", Size = 20, Type = DbType.String)]
        [XmlElement(Order = 4)]
        public string IdentificativoSede { get; set; }

        [DataField("PARTITAIVA", Size = 11, Type = DbType.String)]
        [XmlElement(Order = 5)]
        public string PartitaIVA { get; set; }

        [DataField("CODICEFISCALE", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 6)]
        public string CodiceFiscale { get; set; }

        [DataField("CITTA", Size = 50, Type = DbType.String)]
        [XmlElement(Order = 7)]
        public string Citta { get; set; }

        [DataField("INDIRIZZO", Size = 100, Type = DbType.String)]
        [XmlElement(Order = 8)]
        public string Indirizzo { get; set; }
    }
}
