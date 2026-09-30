using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZERUOLI")]
	[Serializable]
	public class IstanzeRuoli : BaseDataClass
	{
		#region Key Fields

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string idruolo=null;
		[KeyField("IDRUOLO", Type=DbType.Decimal)]
		public string IDRUOLO
		{
			get { return idruolo; }
			set { idruolo = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		#region foreign keys
		Ruoli m_ruolo;
		[ForeignKey("IDCOMUNE, IDRUOLO", "IDCOMUNE, ID")]
		public Ruoli Ruolo
		{
			get { return m_ruolo; }
			set { m_ruolo = value; }
		}
	
		#endregion
	}
}