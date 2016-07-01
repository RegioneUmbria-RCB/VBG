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
    /// File generato automaticamente dalla tabella CANONI_TIPISUPERFICI il 11/11/2008 9.19.13
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
    [DataTable("CANONI_TIPISUPERFICI")]
    [Serializable]
    public partial class CanoniTipiSuperfici : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private string m_tiposuperficie = null;

        private string m_software = null;

        private int? m_pertinenza = null;

        private int? m_flagconteggiamq = null;

        private string m_tipocalcolo = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("TIPOSUPERFICIE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string TipoSuperficie
        {
            get { return m_tiposuperficie; }
            set { m_tiposuperficie = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("PERTINENZA", Type = DbType.Decimal)]
        public int? Pertinenza
        {
            get { return m_pertinenza; }
            set { m_pertinenza = value; }
        }

        [DataField("FLAG_CONTEGGIAMQ", Type = DbType.Decimal)]
        public int? FlagConteggiaMq
        {
            get { return m_flagconteggiamq; }
            set { m_flagconteggiamq = value; }
        }

        [isRequired]
        [DataField("TIPOCALCOLO", Type = DbType.String)]
        public string Tipocalcolo
        {
            get { return m_tipocalcolo; }
            set { m_tipocalcolo = value; }
        }

        #endregion

        #endregion
    }
}
