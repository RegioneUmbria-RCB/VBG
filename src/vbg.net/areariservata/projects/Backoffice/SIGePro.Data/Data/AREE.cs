using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Text;

namespace Init.SIGePro.Data
{
	[DataTable("AREE")]
	[Serializable]
	public class Aree : BaseDataClass
	{
		
		#region Key Fields

		/// <summary>
		/// Chiave primaria insieme a IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("CODICEAREA", Type=DbType.Decimal)]
		public string CODICEAREA{get;set;}

		/// <summary>
		/// Chiave primaria insieme a CODICEAREA
		/// </summary>
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE{get;set;}

		#endregion

		/// <summary>
		/// Denominazione dell'area
		/// </summary>
		[DataField("DENOMINAZIONE",Size=255, Type=DbType.String, CaseSensitive=false)]
		public string DENOMINAZIONE{get;set;}

		[DataField("PROPRIETA",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string PROPRIETA{get;set;}

		[DataField("LOCALITA",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string LOCALITA{get;set;}

		
		[DataField("NOTE",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string NOTE{get;set;}

		
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE{get;set;}

        /// <summary>
        /// Id tipo area (fk su TIPIAREE.CODICETIPOAREA)
        /// </summary>
        [DataField("CODICETIPOAREA", Size=4, Type=DbType.Int32)]
        public int? CODICETIPOAREA{get;set;}


		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();

			if (TipoArea != null)
				sb.Append( TipoArea.ToString() + " - " );
			
			sb.Append(DENOMINAZIONE);

			return sb.ToString();
		}

		/// <summary>
		/// Risoluzione FK: Tipo area
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICETIPOAREA", "Idcomune, Codicetipoarea")]
		public TipiAree TipoArea{get;set;}

	}
}