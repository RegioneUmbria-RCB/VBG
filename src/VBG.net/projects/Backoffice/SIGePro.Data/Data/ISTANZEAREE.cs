using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEAREE")]
	public class IstanzeAree : BaseDataClass
	{

		/// <summary>
		/// Chiave primaria insieme a CODICEISTANZA e CODICEAREA
		/// </summary>
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE{get;set;}

		/// <summary>
		/// Chiave primaria insieme a IDCOMUNE e CODICEAREA (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA{get;set;}

		/// <summary>
		/// Chiave primaria insieme a IDCOMUNE e CODICEISTANZA (fk su AREE.CODICEAREA insieme a IDCOMUNE)
		/// </summary>
		[KeyField("CODICEAREA", Type=DbType.Decimal)]
		public string CODICEAREA{get;set;}

		/// <summary>
		/// Flag primario
		/// </summary>
		[DataField("PRIMARIO", Type=DbType.Decimal)]
		public string PRIMARIO{get;set;}


		/// <summary>
		/// Flag inserimento automatico
		/// </summary>
		[DataField("AUTOINS", Type=DbType.Decimal)]
		public string AUTOINS{get;set;}


		#region foreign keys
		/// <summary>
		/// Risoluzione FK: Area
		/// </summary>
		[ForeignKey("IDCOMUNE, CODICEAREA", "IDCOMUNE, CODICEAREA")]
		public Aree Area{get;set;}

		#endregion
	}
}