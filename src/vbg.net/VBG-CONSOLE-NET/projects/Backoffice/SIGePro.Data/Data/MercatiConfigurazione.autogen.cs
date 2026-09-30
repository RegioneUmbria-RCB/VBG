
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
	///
	/// File generato automaticamente dalla tabella MERCATI_CONFIGURAZIONE il 02/08/2010 13.01.17
	///
	///												ATTENZIONE!!!
	///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
	/// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
	///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	[DataTable("MERCATI_CONFIGURAZIONE")]
	[Serializable]
	public partial class MercatiConfigurazione : BaseDataClass
	{
		#region Membri privati

		private string m_software = null;

		private string m_fk_codicesettore = null;

		private int? m_fk_causale_canone = null;

		private int? m_fk_causale_aumento = null;

		private int? m_fk_causale_diminuzione = null;

		private int? m_fk_conto_default = null;

		private string m_idcomune = null;

		private int? m_fk_trid_con = null;

		private string m_fk_tipiconcessione = null;

		private int? m_durata = null;

		private string m_durata_tipo = null;

		private int? m_fk_codicecausale = null;

		private int? m_fk_trid_aut = null;

		private int? m_fk_codicelettera = null;

		private int? m_fk_dyn2modellit = null;

		private int? m_fk_causale_transazione = null;

		private int? m_fk_conto_interessi = null;

		private int? m_fk_d2cid_num_aut = null;

		private int? m_fk_d2cid_data_aut = null;

		private int? m_fk_d2cid_comune_aut = null;

		private int? m_fk_d2cid_cat_merc = null;

		private string m_cat_merc_uso = null;

		private int? m_fk_d2cid_cod_reg_aut = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("SOFTWARE", Type = DbType.String, Size = 2)]
		public string Software
		{
			get { return m_software; }
			set { m_software = value; }
		}

		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}


		#endregion

		#region Data fields

		[DataField("FK_CODICESETTORE", Type = DbType.String, CaseSensitive = false, Size = 10)]
		public string FkCodicesettore
		{
			get { return m_fk_codicesettore; }
			set { m_fk_codicesettore = value; }
		}

		[DataField("FK_CAUSALE_CANONE", Type = DbType.Decimal)]
		public int? FkCausaleCanone
		{
			get { return m_fk_causale_canone; }
			set { m_fk_causale_canone = value; }
		}

		[DataField("FK_CAUSALE_AUMENTO", Type = DbType.Decimal)]
		public int? FkCausaleAumento
		{
			get { return m_fk_causale_aumento; }
			set { m_fk_causale_aumento = value; }
		}

		[DataField("FK_CAUSALE_DIMINUZIONE", Type = DbType.Decimal)]
		public int? FkCausaleDiminuzione
		{
			get { return m_fk_causale_diminuzione; }
			set { m_fk_causale_diminuzione = value; }
		}

		[DataField("FK_CONTO_DEFAULT", Type = DbType.Decimal)]
		public int? FkContoDefault
		{
			get { return m_fk_conto_default; }
			set { m_fk_conto_default = value; }
		}

		[DataField("FK_TRID_CON", Type = DbType.Decimal)]
		public int? FkTridCon
		{
			get { return m_fk_trid_con; }
			set { m_fk_trid_con = value; }
		}

		[DataField("FK_TIPICONCESSIONE", Type = DbType.String, CaseSensitive = false, Size = 2)]
		public string FkTipiconcessione
		{
			get { return m_fk_tipiconcessione; }
			set { m_fk_tipiconcessione = value; }
		}

		[DataField("DURATA", Type = DbType.Decimal)]
		public int? Durata
		{
			get { return m_durata; }
			set { m_durata = value; }
		}

		[DataField("DURATA_TIPO", Type = DbType.String, CaseSensitive = false, Size = 1)]
		public string DurataTipo
		{
			get { return m_durata_tipo; }
			set { m_durata_tipo = value; }
		}

		[DataField("FK_CODICECAUSALE", Type = DbType.Decimal)]
		public int? FkCodicecausale
		{
			get { return m_fk_codicecausale; }
			set { m_fk_codicecausale = value; }
		}

		[DataField("FK_TRID_AUT", Type = DbType.Decimal)]
		public int? FkTridAut
		{
			get { return m_fk_trid_aut; }
			set { m_fk_trid_aut = value; }
		}

		[DataField("FK_CODICELETTERA", Type = DbType.Decimal)]
		public int? FkCodicelettera
		{
			get { return m_fk_codicelettera; }
			set { m_fk_codicelettera = value; }
		}

		[DataField("FK_DYN2MODELLIT", Type = DbType.Decimal)]
		public int? FkDyn2modellit
		{
			get { return m_fk_dyn2modellit; }
			set { m_fk_dyn2modellit = value; }
		}

		[DataField("FK_CAUSALE_TRANSAZIONE", Type = DbType.Decimal)]
		public int? FkCausaleTransazione
		{
			get { return m_fk_causale_transazione; }
			set { m_fk_causale_transazione = value; }
		}

		[DataField("FK_CONTO_INTERESSI", Type = DbType.Decimal)]
		public int? FkContoInteressi
		{
			get { return m_fk_conto_interessi; }
			set { m_fk_conto_interessi = value; }
		}

		[DataField("FK_D2CID_NUM_AUT", Type = DbType.Decimal)]
		public int? FkD2cidNumAut
		{
			get { return m_fk_d2cid_num_aut; }
			set { m_fk_d2cid_num_aut = value; }
		}

		[DataField("FK_D2CID_DATA_AUT", Type = DbType.Decimal)]
		public int? FkD2cidDataAut
		{
			get { return m_fk_d2cid_data_aut; }
			set { m_fk_d2cid_data_aut = value; }
		}

		[DataField("FK_D2CID_COMUNE_AUT", Type = DbType.Decimal)]
		public int? FkD2cidComuneAut
		{
			get { return m_fk_d2cid_comune_aut; }
			set { m_fk_d2cid_comune_aut = value; }
		}

		[DataField("FK_D2CID_CAT_MERC", Type = DbType.Decimal)]
		public int? FkD2cidCatMerc
		{
			get { return m_fk_d2cid_cat_merc; }
			set { m_fk_d2cid_cat_merc = value; }
		}

		[DataField("CAT_MERC_USO", Type = DbType.String, CaseSensitive = false, Size = 2)]
		public string CatMercUso
		{
			get { return m_cat_merc_uso; }
			set { m_cat_merc_uso = value; }
		}

		[DataField("FK_D2CID_COD_REG_AUT", Type = DbType.Decimal)]
		public int? FkD2cidCodRegAut
		{
			get { return m_fk_d2cid_cod_reg_aut; }
			set { m_fk_d2cid_cod_reg_aut = value; }
		}

		#endregion

		#endregion
	}
}
