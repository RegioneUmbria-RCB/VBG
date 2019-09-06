using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEATTIVITA")]
	[Serializable]
	public class IstanzeAttivita : BaseDataClass
	{
		
		#region Key Fields

        int? id = null;
        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return id; }
            set { id = value; }
        }

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

        string codiceistanza = null;
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public string CODICEISTANZA
        {
            get { return codiceistanza; }
            set { codiceistanza = value; }
        }

        string idattivita = null;
        [DataField("IDATTIVITA", Type = DbType.Decimal)]
        public string IDATTIVITA
        {
            get { return idattivita; }
            set { idattivita = value; }
        }

		string codiceattivita=null;
		[isRequired]
		[DataField("CODICEATTIVITA",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CODICEATTIVITA
		{
			get { return codiceattivita; }
			set { codiceattivita = value; }
		}

        double? metriq = null;
		[DataField("METRIQ", Type=DbType.Decimal)]
		public double? METRIQ
		{
			get { return metriq; }
			set { metriq = value; }
		}

		string note=null;
		[DataField("NOTE",Size=1500, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
		
		string software=null;
		[isRequired]
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		#region Foreign key
		Attivita m_attivita;
		[ForeignKey("IDCOMUNE,CODICEATTIVITA", "IDCOMUNE,CodiceIstat")]
		public Attivita Attivita
		{
			get { return m_attivita; }
			set { m_attivita = value; }
		}

		#endregion
	}
}