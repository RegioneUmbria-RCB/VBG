
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
    /// File generato automaticamente dalla tabella O_CONFIGURAZIONE il 27/06/2008 13.01.35
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
    [DataTable("O_CONFIGURAZIONE")]
    [Serializable]
    public partial class OConfigurazione : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_fk_tipiaree_codice_zto = null;

        private int? m_fk_tipiaree_codice_prg = null;

        private int? m_fk_tum_umid_mq = null;

        private int? m_fk_tum_umid_mc = null;

        private string m_software = null;

        private int? m_usadettagliosup = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String,  Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("SOFTWARE", Type = DbType.String, CaseSensitive = true, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }


        #endregion

        #region Data fields

        [DataField("FK_TIPIAREE_CODICE_ZTO", Type = DbType.Decimal)]
        public int? FkTipiareeCodiceZto
        {
            get { return m_fk_tipiaree_codice_zto; }
            set { m_fk_tipiaree_codice_zto = value; }
        }

        [DataField("FK_TIPIAREE_CODICE_PRG", Type = DbType.Decimal)]
        public int? FkTipiareeCodicePrg
        {
            get { return m_fk_tipiaree_codice_prg; }
            set { m_fk_tipiaree_codice_prg = value; }
        }

        [isRequired]
        [DataField("FK_TUM_UMID_MQ", Type = DbType.Decimal)]
        public int? FkTumUmidMq
        {
            get { return m_fk_tum_umid_mq; }
            set { m_fk_tum_umid_mq = value; }
        }

        [isRequired]
        [DataField("FK_TUM_UMID_MC", Type = DbType.Decimal)]
        public int? FkTumUmidMc
        {
            get { return m_fk_tum_umid_mc; }
            set { m_fk_tum_umid_mc = value; }
        }

		[DataField("USADETTAGLIOSUP", Type = DbType.Decimal)]
		public int? Usadettagliosup
		{
			get { return m_usadettagliosup; }
			set { m_usadettagliosup = value; }
		}

        #endregion

        #endregion
    }
}
