
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
    /// File generato automaticamente dalla tabella TIPIBANDO il 01/04/2009 9.21.52
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
    [DataTable("TIPIBANDO")]
    [Serializable]
    public partial class TipiBando : BaseDataClass
    {
        #region Membri privati

        private int? m_id = null;

        private string m_descrizione = null;

        private int? m_fk_d2mt_id = null;

        private string m_attivo = null;

        private string m_software = null;

        private string m_idcomune = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [DataField("FK_D2MT_ID", Type = DbType.Decimal)]
        public int? FkD2mtId
        {
            get { return m_fk_d2mt_id; }
            set { m_fk_d2mt_id = value; }
        }

        [DataField("ATTIVO", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string Attivo
        {
            get { return m_attivo; }
            set { m_attivo = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        #endregion

        #endregion
    }
}
