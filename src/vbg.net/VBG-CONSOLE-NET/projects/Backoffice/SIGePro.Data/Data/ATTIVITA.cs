using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
	[DataTable("ATTIVITA")]
	[Serializable]
	public class Attivita : BaseDataClass, IClasseContestoModelloDinamico
	{
		
		#region Key Fields
		
		string codiceistat=null;
		[KeyField("CODICEISTAT",Size=6, Type=DbType.String)]
        public string CodiceIstat
		{
			get { return codiceistat; }
			set { codiceistat = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string istat=null;
		[DataField("ISTAT",Size=255, Type=DbType.String, CaseSensitive=false)]
		public string ISTAT
		{
			get { return istat; }
			set { istat = value; }
		}

		string codicesettore=null;
		[DataField("CODICESETTORE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CODICESETTORE
		{
			get { return codicesettore; }
			set { codicesettore = value; }
		}

		string note=null;
		[DataField("NOTE",Size=1500, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		public override string ToString()
		{
			return ISTAT;
		}


		#region foreign key
		Settori m_settore;
		[ForeignKey("IDCOMUNE,CODICESETTORE", "IDCOMUNE,CODICESETTORE")]
		public Settori Settore
		{
			get { return m_settore; }
			set { m_settore = value; }
		}

		#endregion
	}
}