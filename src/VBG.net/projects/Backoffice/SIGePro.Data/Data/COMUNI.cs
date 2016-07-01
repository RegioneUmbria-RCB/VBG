using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("COMUNI")]
	[Serializable]
	public class Comuni : BaseDataClass
	{
		/// <summary>
		/// Identificativo univoco del comune
		/// </summary>
		[DataField("CODICECOMUNE", Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 0)]
		public string CODICECOMUNE{get;set;}

		/// <summary>
		/// Nome del comune
		/// </summary>
		[DataField("COMUNE",Size=128, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 1)]
		public string COMUNE{get;set;}

		/// <summary>
		/// Sigla della provincia 
		/// </summary>
		[DataField("SIGLAPROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 2)]
		public string SIGLAPROVINCIA{get;set;}

		/// <summary>
		/// Nome esteso della provincia
		/// </summary>
		[DataField("PROVINCIA",Size=20, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 3)]
		public string PROVINCIA{get;set;}

		/// <summary>
		/// Regione in cui si trova il comune
		/// </summary>
		[DataField("REGIONE",Size=25, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 4)]
		public string REGIONE{get;set;}

		/// <summary>
		/// Cap del comune (obsoleto)
		/// </summary>
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 5)]
		public string CAP{get;set;}

		/// <summary>
		/// Suffisso del codice fiscale associato al comune
		/// </summary>
		[DataField("CF",Size=5, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 6)]
		public string CF{get;set;}

		/// <summary>
		/// Codice istat del comune
		/// </summary>
		[DataField("CODICEISTAT",Size=6, Type=DbType.String)]
		[XmlElement(Order = 7)]
		public string CODICEISTAT{get;set;}


		[DataField("CODICEISTATREGIONE",Size=2, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string CODICEISTATREGIONE{get;set;}

		[DataField("CODICESTATOESTERO",Size=3, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 9)]
		public string CODICESTATOESTERO{get;set;}

		/// <summary>
		/// Risoluzione Foreign key: Provincia
		/// </summary>
        [ForeignKey("SIGLAPROVINCIA", "SiglaProvincia")]
		[XmlElement(Order = 10)]
        public VwProvince ProvinciaClass{get;set;}


		public override string ToString()
		{
			return COMUNE;
		}
	}
}