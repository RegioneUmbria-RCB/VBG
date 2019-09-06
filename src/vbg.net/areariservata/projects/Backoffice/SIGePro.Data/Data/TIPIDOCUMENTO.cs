using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIDOCUMENTO")]
	public class TipiDocumento : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string idtipodocumento=null;
		[KeyField("IDTIPODOCUMENTO", Type=DbType.Decimal)]
		public string IDTIPODOCUMENTO
		{
			get { return idtipodocumento; }
			set { idtipodocumento = value; }
		}

		string documento=null;
		[DataField("DOCUMENTO",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DOCUMENTO
		{
			get { return documento; }
			set { documento = value; }
		}

		string codletteratipo=null;
		[DataField("CODLETTERATIPO", Type=DbType.Decimal)]
		public string CODLETTERATIPO
		{
			get { return codletteratipo; }
			set { codletteratipo = value; }
		}

		string numggvalidita=null;
		[DataField("NUMGGVALIDITA", Type=DbType.Decimal)]
		public string NUMGGVALIDITA
		{
			get { return numggvalidita; }
			set { numggvalidita = value; }
		}
	}
}