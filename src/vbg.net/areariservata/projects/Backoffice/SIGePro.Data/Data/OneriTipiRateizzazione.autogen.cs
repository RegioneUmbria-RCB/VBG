

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
    /// File generato automaticamente dalla tabella ONERITIPIRATEIZZAZIONE il 20/07/2009 12.40.07
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
    [DataTable("ONERITIPIRATEIZZAZIONE")]
    [Serializable]
    public partial class OneriTipiRateizzazione : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_tiporateizzazione = null;

        private string m_descrizione = null;

        private string m_nrorate = null;

        private string m_ripartizionerate = null;

        private string m_frequenzarate = null;

        private int? m_scadenzarate = null;

        private string m_interessirate = null;

        private int? m_determdatainiziorate = null;

        private string m_fk_tipomov_determdatain = null;

        private string m_software = null;

        private int? m_flag_interessi_legali = null;

        private int? m_tipo_anatocismo = null;

        private decimal? m_spese_rateizzazione = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        //[KeyField("TIPORATEIZZAZIONE", Type = DbType.Decimal)]
        //[useSequence]
        //public int Tiporateizzazione
        //{
        //    get { return m_tiporateizzazione; }
        //    set { m_tiporateizzazione = value; }
        //}


        #endregion

        #region Data fields

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [isRequired]
        [DataField("NRORATE", Type = DbType.String, CaseSensitive = false, Size = 4)]
        public string Nrorate
        {
            get { return m_nrorate; }
            set { m_nrorate = value; }
        }

        [isRequired]
        [DataField("RIPARTIZIONERATE", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Ripartizionerate
        {
            get { return m_ripartizionerate; }
            set { m_ripartizionerate = value; }
        }

        [isRequired]
        [DataField("FREQUENZARATE", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Frequenzarate
        {
            get { return m_frequenzarate; }
            set { m_frequenzarate = value; }
        }

        [DataField("SCADENZARATE", Type = DbType.Decimal)]
        public int? Scadenzarate
        {
            get { return m_scadenzarate; }
            set { m_scadenzarate = value; }
        }

        [DataField("INTERESSIRATE", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Interessirate
        {
            get { return m_interessirate; }
            set { m_interessirate = value; }
        }

        [DataField("DETERMDATAINIZIORATE", Type = DbType.Decimal)]
        public int? Determdatainiziorate
        {
            get { return m_determdatainiziorate; }
            set { m_determdatainiziorate = value; }
        }

        [DataField("FK_TIPOMOV_DETERMDATAIN", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string FkTipomovDetermdatain
        {
            get { return m_fk_tipomov_determdatain; }
            set { m_fk_tipomov_determdatain = value; }
        }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("FLAG_INTERESSI_LEGALI", Type = DbType.Decimal)]
        public int? FlagInteressiLegali
        {
            get { return m_flag_interessi_legali; }
            set { m_flag_interessi_legali = value; }
        }

        [DataField("TIPO_ANATOCISMO", Type = DbType.Decimal)]
        public int? TipoAnatocismo
        {
            get { return m_tipo_anatocismo; }
            set { m_tipo_anatocismo = value; }
        }

        [DataField("SPESE_RATEIZZAZIONE", Type = DbType.Decimal)]
        public decimal? SpeseRateizzazione
        {
            get { return m_spese_rateizzazione; }
            set { m_spese_rateizzazione = value; }
        }

        #endregion

        #endregion
    }
}
				
				