using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOCUMENTI")]
	public class Documenti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinventario=null;
		[KeyField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string numerodocumento=null;
		[KeyField("NUMERODOCUMENTO", Type=DbType.Decimal)]
		public string NUMERODOCUMENTO
		{
			get { return numerodocumento; }
			set { numerodocumento = value; }
		}

		string documento=null;
		[DataField("DOCUMENTO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DOCUMENTO
		{
			get { return documento; }
			set { documento = value; }
		}

		string amministrazione=null;
		[DataField("AMMINISTRAZIONE", Type=DbType.Decimal)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string modello=null;
		[DataField("MODELLO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MODELLO
		{
			get { return modello; }
			set { modello = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string indirizzoweb=null;
		[DataField("INDIRIZZOWEB",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZOWEB
		{
			get { return indirizzoweb; }
			set { indirizzoweb = value; }
		}
	}
}