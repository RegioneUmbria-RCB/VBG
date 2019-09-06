namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per ClassesBox.
	/// </summary>
	public class ClassesBox
	{
		private object m_class = null;

		public object CLASS
		{
			get { return m_class; }
			set { m_class = value; }
		}

		private IMPR_IMPRESA m_impresa = null;

		public IMPR_IMPRESA Impresa
		{
			get { return m_impresa; }
			set { m_impresa = value; }
		}

/*
		ATTV_ATTIVITA m_attivita = null;
		public ATTV_ATTIVITA Attivita
		{
			get { return m_attivita; }
			set { m_attivita = value; }
		}

		COMU_COMUNI m_comuni = null;
		public COMU_COMUNI Comuni
		{
			get { return m_comuni; }
			set { m_comuni = value; }
		}

		DECO_DECODIFICHE m_decodifiche = null;
		public DECO_DECODIFICHE Decodifiche
		{
			get { return m_decodifiche; }
			set { m_decodifiche = value; }
		}

		NAGI_NATURAGIURIDICA m_naturaGiuridica = null;
		public NAGI_NATURAGIURIDICA NaturaGiuridica
		{
			get { return m_naturaGiuridica; }
			set { m_naturaGiuridica = value; }
		}

		PFIM_PERSONEFISICHEIMPRESA m_personeFisiche = null;
		public PFIM_PERSONEFISICHEIMPRESA PersoneFisiche
		{
			get { return m_personeFisiche; }
			set { m_personeFisiche = value; }
		}

		PGIM_PGIMPRESA m_pgImpresa = null;
		public PGIM_PGIMPRESA PgImpresa
		{
			get { return m_pgImpresa; }
			set { m_pgImpresa = value; }
		}

		PROV_PROVINCE m_province = null;
		public PROV_PROVINCE Province
		{
			get { return m_province; }
			set { m_province = value; }
		}

		PSCA_CARICHEPERSIMPRESA m_cariche = null;
		public PSCA_CARICHEPERSIMPRESA Cariche
		{
			get { return m_cariche; }
			set { m_cariche = value; }
		}

		SEZI_SEZIONI m_sezioni = null;
		public SEZI_SEZIONI Sezioni
		{
			get { return m_sezioni; }
			set { m_sezioni = value; }
		}

		SZSP_SEZIONISPECIALI m_sezioniSpeciali = null;
		public SZSP_SEZIONISPECIALI SezioniSpeciali
		{
			get { return m_sezioniSpeciali; }
			set { m_sezioniSpeciali = value; }
		}
*/
		private UNLO_UNITALOCALE m_unitaLocale = null;

		public UNLO_UNITALOCALE UnitaLocale
		{
			get { return m_unitaLocale; }
			set { m_unitaLocale = value; }
		}

		private TMP_IMPORT m_tmpImport = null;

		public TMP_IMPORT TmpImport
		{
			get { return m_tmpImport; }
			set { m_tmpImport = value; }
		}

		public ClassesBox()
		{
		}
	}
}