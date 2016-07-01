
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
    /// File generato automaticamente dalla tabella ISTANZELAVORI_T il 31/07/2008 11.06.39
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
    [DataTable("ISTANZELAVORI_T")]
    [Serializable]
    public partial class IstanzeLavoriT : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_codiceistanza = null;

        private int? m_fk_isid = null;

        private int? m_fk_ltid = null;

        private string m_lavoro = null;

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
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza
        {
            get { return m_codiceistanza; }
            set { m_codiceistanza = value; }
        }

        [isRequired]
        [DataField("FK_ISID", Type = DbType.Decimal)]
        public int? FkIsid
        {
            get { return m_fk_isid; }
            set { m_fk_isid = value; }
        }

        [isRequired]
        [DataField("FK_LTID", Type = DbType.Decimal)]
        public int? FkLtid
        {
            get { return m_fk_ltid; }
            set { m_fk_ltid = value; }
        }

        [isRequired]
        [DataField("LAVORO", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Lavoro
        {
            get { return m_lavoro; }
            set { m_lavoro = value; }
        }

        #endregion

        #endregion
    }
}
