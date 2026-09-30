using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MAILTIPO")]
	[Serializable]
	public class MailTipo : BaseDataClass
	{
		#region Key Fields

		string codicemail=null;
		[useSequence]
		[KeyField("CODICEMAIL", Type=DbType.Int32)]
		public string CODICEMAIL
		{
			get { return codicemail; }
			set { codicemail= value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=128, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=512, Type=DbType.String, CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string corpo=null;
		[DataField("CORPO",Size=1000, Type=DbType.String, CaseSensitive=false)]
		public string CORPO
		{
			get { return corpo; }
			set { corpo = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}
	}
}
