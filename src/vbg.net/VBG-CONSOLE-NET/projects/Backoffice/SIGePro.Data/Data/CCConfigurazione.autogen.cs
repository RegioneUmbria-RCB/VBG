
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
    /// File generato automaticamente dalla tabella CC_CONFIGURAZIONE il 27/06/2008 13.01.37
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
    [DataTable("CC_CONFIGURAZIONE")]
    [Serializable]
    public partial class CCConfigurazione : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_tab1_fk_ts_id = null;

        private int? m_tab2_fk_ts_id = null;

        private int? m_art9su_fk_ts_id = null;

        private int? m_art9sa_fk_ts_id = null;

        private int? m_fk_co_id = null;

        private int? m_fk_tipiaree_codice = null;

        private string m_software = null;

		private int? m_usadettagliosup = null;

		private string m_fk_se_codicesettore = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("SOFTWARE", Type = DbType.String,  Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("TAB1_FK_TS_ID", Type = DbType.Decimal)]
        public int? Tab1FkTsId
        {
            get { return m_tab1_fk_ts_id; }
            set { m_tab1_fk_ts_id = value; }
        }

        [isRequired]
        [DataField("TAB2_FK_TS_ID", Type = DbType.Decimal)]
        public int? Tab2FkTsId
        {
            get { return m_tab2_fk_ts_id; }
            set { m_tab2_fk_ts_id = value; }
        }

        [isRequired]
        [DataField("ART9SU_FK_TS_ID", Type = DbType.Decimal)]
        public int? Art9suFkTsId
        {
            get { return m_art9su_fk_ts_id; }
            set { m_art9su_fk_ts_id = value; }
        }

        [isRequired]
        [DataField("ART9SA_FK_TS_ID", Type = DbType.Decimal)]
        public int? Art9saFkTsId
        {
            get { return m_art9sa_fk_ts_id; }
            set { m_art9sa_fk_ts_id = value; }
        }

        [DataField("FK_CO_ID", Type = DbType.Decimal)]
        public int? FkCoId
        {
            get { return m_fk_co_id; }
            set { m_fk_co_id = value; }
        }

        [DataField("FK_TIPIAREE_CODICE", Type = DbType.Decimal)]
        public int? FkTipiareeCodice
        {
            get { return m_fk_tipiaree_codice; }
            set { m_fk_tipiaree_codice = value; }
        }

		[DataField("USADETTAGLIOSUP", Type = DbType.Decimal)]
		public int? Usadettagliosup
		{
			get { return m_usadettagliosup; }
			set { m_usadettagliosup = value; }
		}

		[DataField("FK_SE_CODICESETTORE", Type = DbType.String , Size=6)]
		public string FkSeCodicesettore
		{
			get { return m_fk_se_codicesettore; }
			set { m_fk_se_codicesettore = value; }
		}

        #endregion

        #endregion
    }
}
