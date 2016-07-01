using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LOGPERMESSI")]
	public class LogPermessi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string responsabile=null;
		[DataField("RESPONSABILE", Type=DbType.Decimal)]
		public string RESPONSABILE
		{
			get { return responsabile; }
			set { responsabile = value; }
		}

		string oldproprietario=null;
		[DataField("OLDPROPRIETARIO", Type=DbType.Decimal)]
		public string OLDPROPRIETARIO
		{
			get { return oldproprietario; }
			set { oldproprietario = value; }
		}

		string newproprietario=null;
		[DataField("NEWPROPRIETARIO", Type=DbType.Decimal)]
		public string NEWPROPRIETARIO
		{
			get { return newproprietario; }
			set { newproprietario = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string tabella=null;
		[DataField("TABELLA", Type=DbType.Decimal)]
		public string TABELLA
		{
			get { return tabella; }
			set { tabella = value; }
		}

		string tipo=null;
		[DataField("TIPO",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}
	}
}