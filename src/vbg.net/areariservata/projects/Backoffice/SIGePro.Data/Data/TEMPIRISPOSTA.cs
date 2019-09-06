using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
namespace Init.SIGePro.Data
{
	[DataTable("TEMPIRISPOSTA")]
	[Serializable]
	public class TempiRisposta : BaseDataClass
	{

		#region Key Fields

		string codiceprocedura=null;
		[KeyField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string codiceamministrazione=null;
		[KeyField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string tipocontromovimento=null;
		[KeyField("TIPOCONTROMOVIMENTO",Size=6, Type=DbType.String)]
		public string TIPOCONTROMOVIMENTO
		{
			get { return tipocontromovimento; }
			set { tipocontromovimento = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string attesa=null;
		[isRequired]
		[DataField("ATTESA", Type=DbType.Decimal)]
		public string ATTESA
		{
			get { return attesa; }
			set { attesa = value; }
		}
		
		string calcoladainizioistanza=null;
		[isRequired]
		[DataField("CALCOLADAINIZIOISTANZA", Type=DbType.Decimal)]
		public string CALCOLADAINIZIOISTANZA
		{
			get { return calcoladainizioistanza; }
			set { calcoladainizioistanza = value; }
		}

	}
}