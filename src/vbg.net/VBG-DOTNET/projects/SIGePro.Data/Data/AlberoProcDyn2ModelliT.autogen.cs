
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT il 01/04/2011 14.33.35
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
    [DataTable("ALBEROPROC_DYN2MODELLIT")]
    [Serializable]
    public partial class AlberoProcDyn2ModelliT : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_sc_id = null;

        private int? m_fk_d2mt_id = null;

        private int? m_flag_pubblica = null;

        private int? m_flag_tipofirma = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        [KeyField("FK_SC_ID", Type = DbType.Decimal)]
        public int? FkScId
        {
            get { return this.m_fk_sc_id; }
            set { this.m_fk_sc_id = value; }
        }

        [KeyField("FK_D2MT_ID", Type = DbType.Decimal)]
        public int? FkD2mtId
        {
            get { return this.m_fk_d2mt_id; }
            set { this.m_fk_d2mt_id = value; }
        }


        #endregion

        #region Data fields

        [DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
        public int? FlagPubblica
        {
            get { return this.m_flag_pubblica; }
            set { this.m_flag_pubblica = value; }
        }

        [DataField("FLAG_TIPOFIRMA", Type = DbType.Decimal)]
        public int? FlagTipofirma
        {
            get { return this.m_flag_tipofirma; }
            set { this.m_flag_tipofirma = value; }
        }

        #endregion

        #endregion
    }
}
