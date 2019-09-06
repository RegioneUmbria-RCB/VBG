using System.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace SIGePro.Net.Data
{
	[DataTable("TMP_IMPORT")]
	public class TMP_IMPORT : DataClass
	{
		private string prg = null;

		[TextFileInfo(0, 6)]
		[DataField("PRG", Type=DbType.Int32)]
		public string PRG
		{
			get { return prg.Trim(); }
			set { prg = value; }
		}

		private string pv = null;

		[TextFileInfo(7, 2)]
		[DataField("PV", Size=2, Type=DbType.String)]
		public string PV
		{
			get { return pv.Trim(); }
			set { pv = value; }
		}

		private string n_reg_imp = null;

		[TextFileInfo(10, 21)]
		[DataField("N_REG_IMP", Size=21, Type=DbType.String, Compare="like")]
		public string N_REG_IMP
		{
			get { return n_reg_imp.Trim(); }
			set { n_reg_imp = value; }
		}

		private string n_rea = null;

		[TextFileInfo(32, 10)]
		[DataField("N_REA", Size=10, Type=DbType.String)]
		public string N_REA
		{
			get { return n_rea.Trim(); }
			set { n_rea = value; }
		}

		private string ul_sede = null;

		[TextFileInfo(43, 8)]
		[DataField("UL_SEDE", Size=8, Type=DbType.String)]
		public string UL_SEDE
		{
			get { return ul_sede.Trim(); }
			set { ul_sede = value; }
		}

		private string n_albo_aa = null;

		[TextFileInfo(52, 10)]
		[DataField("N_ALBO_AA", Size=10, Type=DbType.String)]
		public string N_ALBO_AA
		{
			get { return n_albo_aa.Trim(); }
			set { n_albo_aa = value; }
		}

		private string sez_reg_imp = null;

		[TextFileInfo(63, 12)]
		[DataField("SEZ_REG_IMP", Size=12, Type=DbType.String)]
		public string SEZ_REG_IMP
		{
			get { return sez_reg_imp.Trim(); }
			set { sez_reg_imp = value; }
		}

		private string ng = null;

		[TextFileInfo(76, 2)]
		[DataField("NG", Size=2, Type=DbType.String)]
		public string NG
		{
			get { return ng.Trim(); }
			set { ng = value; }
		}

		private string dt_iscr_ri = null;

		[TextFileInfo(79, 10)]
		[DataField("DT_ISCR_RI", Size=10, Type=DbType.String)]
		public string DT_ISCR_RI
		{
			get { return dt_iscr_ri.Trim(); }
			set { dt_iscr_ri = value; }
		}

		private string dt_iscr_rd = null;

		[TextFileInfo(90, 10)]
		[DataField("DT_ISCR_RD", Size=10, Type=DbType.String)]
		public string DT_ISCR_RD
		{
			get { return dt_iscr_rd.Trim(); }
			set { dt_iscr_rd = value; }
		}

		private string dt_iscr_aa = null;

		[TextFileInfo(101, 10)]
		[DataField("DT_ISCR_AA", Size=10, Type=DbType.String)]
		public string DT_ISCR_AA
		{
			get { return dt_iscr_aa.Trim(); }
			set { dt_iscr_aa = value; }
		}

		private string dt_aper_ul = null;

		[TextFileInfo(112, 10)]
		[DataField("DT_APER_UL", Size=10, Type=DbType.String)]
		public string DT_APER_UL
		{
			get { return dt_aper_ul.Trim(); }
			set { dt_aper_ul = value; }
		}

		private string dt_cessaz = null;

		[TextFileInfo(123, 10)]
		[DataField("DT_CESSAZ", Size=10, Type=DbType.String)]
		public string DT_CESSAZ
		{
			get { return dt_cessaz.Trim(); }
			set { dt_cessaz = value; }
		}

		private string dt_ini_at = null;

		[TextFileInfo(134, 10)]
		[DataField("DT_INI_AT", Size=10, Type=DbType.String)]
		public string DT_INI_AT
		{
			get { return dt_ini_at.Trim(); }
			set { dt_ini_at = value; }
		}

		private string dt_ces_at = null;

		[TextFileInfo(145, 10)]
		[DataField("DT_CES_AT", Size=10, Type=DbType.String)]
		public string DT_CES_AT
		{
			get { return dt_ces_at.Trim(); }
			set { dt_ces_at = value; }
		}

		private string dt_fallim = null;

		[TextFileInfo(156, 10)]
		[DataField("DT_FALLIM", Size=10, Type=DbType.String)]
		public string DT_FALLIM
		{
			get { return dt_fallim.Trim(); }
			set { dt_fallim = value; }
		}

		private string dt_liquid = null;

		[TextFileInfo(167, 10)]
		[DataField("DT_LIQUID", Size=10, Type=DbType.String)]
		public string DT_LIQUID
		{
			get { return dt_liquid.Trim(); }
			set { dt_liquid = value; }
		}

		private string denominazione = null;

		[TextFileInfo(178, 199)]
		[DataField("DENOMINAZIONE", Size=200, Type=DbType.String, Compare="like")]
		public string DENOMINAZIONE
		{
			get { return denominazione.Trim(); }
			set { denominazione = value; }
		}

		private string indirizzo = null;

		[TextFileInfo(379, 50)]
		[DataField("INDIRIZZO", Size=50, Type=DbType.String, Compare="like")]
		public string INDIRIZZO
		{
			get { return indirizzo.Trim(); }
			set { indirizzo = value; }
		}

		private string strad = null;

		[TextFileInfo(430, 5)]
		[DataField("STRAD", Size=5, Type=DbType.String)]
		public string STRAD
		{
			get { return strad.Trim(); }
			set { strad = value; }
		}

		private string cap = null;

		[TextFileInfo(436, 5)]
		[DataField("CAP", Size=5, Type=DbType.String)]
		public string CAP
		{
			get { return cap.Trim(); }
			set { cap = value; }
		}

		private string comune = null;

		[TextFileInfo(442, 45)]
		[DataField("COMUNE", Size=45, Type=DbType.String, Compare="like")]
		public string COMUNE
		{
			get { return comune.Trim(); }
			set { comune = value; }
		}

		private string frazione = null;

		[TextFileInfo(488, 30)]
		[DataField("FRAZIONE", Size=30, Type=DbType.String, Compare="like")]
		public string FRAZIONE
		{
			get { return frazione.Trim(); }
			set { frazione = value; }
		}

		private string altre_indicazioni = null;

		[TextFileInfo(519, 30)]
		[DataField("ALTRE_INDICAZIONI", Size=30, Type=DbType.String, Compare="like")]
		public string ALTRE_INDICAZIONI
		{
			get { return altre_indicazioni.Trim(); }
			set { altre_indicazioni = value; }
		}

		private string aa_add = null;

		[TextFileInfo(550, 4)]
		[DataField("AA_ADD", Size=4, Type=DbType.String)]
		public string AA_ADD
		{
			get { return aa_add.Trim(); }
			set { aa_add = value; }
		}

		private string ind = null;

		[TextFileInfo(555, 5)]
		[DataField("IND", Size=5, Type=DbType.String)]
		public string IND
		{
			get { return ind.Trim(); }
			set { ind = value; }
		}

		private string dip = null;

		[TextFileInfo(561, 5)]
		[DataField("DIP", Size=5, Type=DbType.String)]
		public string DIP
		{
			get { return dip.Trim(); }
			set { dip = value; }
		}

		private string c_fiscale = null;

		[TextFileInfo(567, 16)]
		[DataField("C_FISCALE", Size=16, Type=DbType.String)]
		public string C_FISCALE
		{
			get { return c_fiscale.Trim(); }
			set { c_fiscale = value; }
		}

		private string partita_iva = null;

		[TextFileInfo(584, 11)]
		[DataField("PARTITA_IVA", Size=11, Type=DbType.String)]
		public string PARTITA_IVA
		{
			get { return partita_iva.Trim(); }
			set { partita_iva = value; }
		}

		private string telefono = null;

		[TextFileInfo(596, 15)]
		[DataField("TELEFONO", Size=15, Type=DbType.String)]
		public string TELEFONO
		{
			get { return telefono.Trim(); }
			set { telefono = value; }
		}

		private string capitale = null;

		[TextFileInfo(612, 21)]
		[DataField("CAPITALE", Type=DbType.String)]
		public string CAPITALE
		{
			get { return capitale.Trim(); }
			set { capitale = value; }
		}

		private string attivita = null;

		[TextFileInfo(634, 200)]
		[DataField("ATTIVITA", Size=200, Type=DbType.String, Compare="like")]
		public string ATTIVITA
		{
			get { return attivita.Trim(); }
			set { attivita = value; }
		}

		private string codici_attivita = null;

		[TextFileInfo(835, 79)]
		[DataField("CODICI_ATTIVITA", Size=80, Type=DbType.String, Compare="like")]
		public string CODICI_ATTIVITA
		{
			get { return codici_attivita.Trim(); }
			set { codici_attivita = value; }
		}

		private string mad = null;

		[TextFileInfo(936, 100)]
		[DataField("MAD", Size=100, Type=DbType.String, Compare="like")]
		public string MAD
		{
			get { return mad.Trim(); }
			set { mad = value; }
		}

	}
}