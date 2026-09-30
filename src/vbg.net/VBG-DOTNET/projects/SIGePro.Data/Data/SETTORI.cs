using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SETTORI")]
	[Serializable]
	public class Settori : BaseDataClass
	{

		#region Key Fields

		string codicesettore=null;
		[KeyField("CODICESETTORE",Size=6, Type=DbType.String)]
		public string CODICESETTORE
		{
			get { return codicesettore; }
			set { codicesettore = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string settore=null;
		[DataField("SETTORE",Size=255, Type=DbType.String, CaseSensitive=false)]
		public string SETTORE
		{
			get { return settore; }
			set { settore = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string codiceunitamisura=null;
		[DataField("CODICEUNITAMISURA", Type=DbType.Decimal)]
		public string CODICEUNITAMISURA
		{
			get { return codiceunitamisura; }
			set { codiceunitamisura = value; }
		}

		string flag_contamqattivita=null;
		[DataField("FLAG_CONTAMQATTIVITA", Type=DbType.Decimal)]
		public string FLAG_CONTAMQATTIVITA
		{
			get { return flag_contamqattivita; }
			set { flag_contamqattivita = value; }
		}

		string flag_insmultiplo=null;
		[DataField("FLAG_INSMULTIPLO", Type=DbType.Decimal)]
		public string FLAG_INSMULTIPLO
		{
			get { return flag_insmultiplo; }
			set { flag_insmultiplo = value; }
		}

		string fo_richiesto=null;
		[DataField("FO_RICHIESTO", Type=DbType.Decimal)]
		public string FO_RICHIESTO
		{
			get { return fo_richiesto; }
			set { fo_richiesto = value; }
		}

		public override string ToString()
		{
			return SETTORE;
		}

		#region foreign keys
		TipiUnitaMisura m_unitaMisura;
		[ForeignKey("IDCOMUNE, CODICEUNITAMISURA", "Idcomune, UmId")]
		public TipiUnitaMisura UnitaDiMisura
		{
			get { return m_unitaMisura; }
			set { m_unitaMisura = value; }
		}

		#endregion
	}
}