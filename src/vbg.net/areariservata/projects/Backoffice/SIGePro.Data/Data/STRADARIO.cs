using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("STRADARIO")]
	[Serializable]
	public class Stradario : BaseDataClass
	{

		#region Key Fields

		string codicestradario = null;
		[useSequence]
		[KeyField("CODICESTRADARIO", Type = DbType.Decimal, KeyIdentity = true)]
		[XmlElement(Order=0)]
		public string CODICESTRADARIO
		{
			get { return codicestradario; }
			set { codicestradario = value; }
		}

		string idcomune = null;
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		[XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string prefisso = null;
		[isRequired]
		[DataField("PREFISSO", Size = 20, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 2)]
		public string PREFISSO
		{
			get { return prefisso; }
			set { prefisso = value; }
		}

		string descrizione = null;
		[isRequired]
		[DataField("DESCRIZIONE", Size = 128, Type = DbType.String, CaseSensitive = false, Compare = "like")]
		[XmlElement(Order = 3)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string cap = null;
		[isRequired]
		[DataField("CAP", Size = 5, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 4)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string locfraz = null;
		[DataField("LOCFRAZ", Size = 128, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 5)]
		public string LOCFRAZ
		{
			get { return locfraz; }
			set { locfraz = value; }
		}

		string fkidzona = null;
		[DataField("FKIDZONA", Type = DbType.Decimal)]
		[XmlElement(Order = 6)]
		public string FKIDZONA
		{
			get { return fkidzona; }
			set { fkidzona = value; }
		}

		string codicecomune = null;
		[DataField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 7)]
		public string CODICECOMUNE
		{
			get { return codicecomune; }
			set { codicecomune = value; }
		}

		string cs_date = null;
		[DataField("CS_DATE", Size = 8, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 8)]
		public string CS_DATE
		{
			get { return cs_date; }
			set { cs_date = value; }
		}

		string codviario = null;
		[DataField("CODVIARIO", Size = 25, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 9)]
		public string CODVIARIO
		{
			get { return codviario; }
			set { codviario = value; }
		}

		DateTime? datavalidita = null;
		[DataField("DATAVALIDITA", Type = DbType.DateTime)]
		[XmlElement(Order = 10)]
		public DateTime? DATAVALIDITA
		{
			get { return datavalidita; }
			set { datavalidita = VerificaDataLocale(value); }
		}

		Comuni m_comune;
		[ForeignKey("CODICECOMUNE", "CODICECOMUNE")]
		[XmlElement(Order = 11)]
		public Comuni Comune
		{
			get { return m_comune; }
			set { m_comune = value; }
		}

        [DataField("COMUNE_LOCALIZZAZIONE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 12)]
        public string CodiceComuneLocalizzazione
        {
            get { return codicecomune; }
            set { codicecomune = value; }
        }

        [ForeignKey("CodiceComuneLocalizzazione", "CODICECOMUNE")]
        [XmlElement(Order = 13)]
        public Comuni ComuneLocalizzazione
        {
            get;
            set;
        }



	}
}