using System.Data;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MERCATI_DATTIVITAISTAT")]
	public class Mercati_DAttivitaIstat : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

        int? fkcodicemercato = null;
		[KeyField("FKCODICEMERCATO", Type=DbType.Decimal)]
        public int? FKCODICEMERCATO
		{
			get { return fkcodicemercato; }
			set { fkcodicemercato = value; }
		}

        int? fkidposteggio = null;
		[KeyField("FKIDPOSTEGGIO", Type=DbType.Decimal)]
        public int? FKIDPOSTEGGIO
		{
			get { return fkidposteggio; }
			set { fkidposteggio = value; }
		}

		string fkcodiceattivitaistat=null;
		[KeyField("FKCODICEATTIVITAISTAT",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FkCodiceAttivitaIstat
		{
			get { return fkcodiceattivitaistat; }
			set { fkcodiceattivitaistat = value; }
		}

		string flag_consentito=null;
		[DataField("FLAG_CONSENTITO", Type=DbType.Decimal)]
		public string Flag_Consentito
		{
			get { return flag_consentito; }
			set { flag_consentito = value; }
		}

        #region Foreign

        Attivita m_attivita = null;
        [ForeignKey("IDCOMUNE,FkCodiceAttivitaIstat", "IDCOMUNE,CodiceIstat")]
        public Attivita Attivita
        {
            get { return m_attivita; }
            set { m_attivita = value; }
        }

        #endregion
	}
}