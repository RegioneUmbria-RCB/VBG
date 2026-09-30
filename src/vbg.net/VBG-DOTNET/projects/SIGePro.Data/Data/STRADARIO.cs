using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("STRADARIO")]
    [Serializable]
    [DataContract]
    public class Stradario : BaseDataClass, IEquatable<Stradario>
    {

        #region Key Fields

        private string codicestradario = null;
        [useSequence]
        [KeyField("CODICESTRADARIO", Type = DbType.Decimal, KeyIdentity = true)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICESTRADARIO
        {
            get { return this.codicestradario; }
            set { this.codicestradario = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string prefisso = null;
        [isRequired]
        [DataField("PREFISSO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string PREFISSO
        {
            get { return this.prefisso; }
            set { this.prefisso = value; }
        }

        private string descrizione = null;
        [isRequired]
        [DataField("DESCRIZIONE", Size = 128, Type = DbType.String, CaseSensitive = false, Compare = "like")]
        [DataMember]
        [XmlElement(Order = 3)]
        public string DESCRIZIONE
        {
            get { return this.descrizione; }
            set { this.descrizione = value; }
        }

        private string cap = null;
        [isRequired]
        [DataField("CAP", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string CAP
        {
            get { return this.cap; }
            set { this.cap = value; }
        }

        private string locfraz = null;
        [DataField("LOCFRAZ", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string LOCFRAZ
        {
            get { return this.locfraz; }
            set { this.locfraz = value; }
        }

        private string fkidzona = null;
        [DataField("FKIDZONA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string FKIDZONA
        {
            get { return this.fkidzona; }
            set { this.fkidzona = value; }
        }

        private string codicecomune = null;
        [DataField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string CODICECOMUNE
        {
            get { return this.codicecomune; }
            set { this.codicecomune = value; }
        }

        private string cs_date = null;
        [DataField("CS_DATE", Size = 8, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string CS_DATE
        {
            get { return this.cs_date; }
            set { this.cs_date = value; }
        }

        private string codviario = null;
        [DataField("CODVIARIO", Size = 25, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string CODVIARIO
        {
            get { return this.codviario; }
            set { this.codviario = value; }
        }

        private DateTime? datavalidita = null;
        [DataField("DATAVALIDITA", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 10)]
        public DateTime? DATAVALIDITA
        {
            get { return this.datavalidita; }
            set { this.datavalidita = this.VerificaDataLocale(value); }
        }

        private Comuni m_comune;
        [ForeignKey("CODICECOMUNE", "CODICECOMUNE")]
        [DataMember]
        [XmlElement(Order = 11)]
        public Comuni Comune
        {
            get { return this.m_comune; }
            set { this.m_comune = value; }
        }

        [DataField("COMUNE_LOCALIZZAZIONE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 12)]
        public string CodiceComuneLocalizzazione { get; set; }

        [ForeignKey("CodiceComuneLocalizzazione", "CODICECOMUNE")]
        [DataMember]
        [XmlElement(Order = 13)]
        public Comuni ComuneLocalizzazione
        {
            get;
            set;
        }

        public bool Equals(Stradario other)
        {
            if (Object.ReferenceEquals(other, null)) return false;
            if (Object.ReferenceEquals(this, other)) return true;

            return this.IDCOMUNE.Equals(other.IDCOMUNE) && this.CODICESTRADARIO.Equals(other.CODICESTRADARIO);
        }

        public override int GetHashCode()
        {

            //Get hash code for the Name field if it is not null.
            int hashIdComune = this.IDCOMUNE == null ? 0 : this.IDCOMUNE.GetHashCode();

            //Get hash code for the Code field.
            int hashCodiceStradario = this.CODICESTRADARIO == null ? 0 : this.CODICESTRADARIO.GetHashCode();

            //Calculate the hash code for the product.
            return hashIdComune ^ hashCodiceStradario;
        }
    }
}